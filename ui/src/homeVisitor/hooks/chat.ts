import { useState, useCallback } from 'react';
import {ChatInteraction} from "../types/chat.ts";
import {sanitize} from "../../common/utils/html.parse.ts";
import {csrfHeader, csrfToken} from "../../common/api/base.ts";

export const useChat = (path: string | undefined, retries: number) => {
    const [chatInteractions, setChatInteractions] = useState<ChatInteraction[]>([]);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState<Error | null>(null);

    const fetchStreamWithRetry = useCallback(async (url: string) => {
        for (let i = 0; i < retries; i++) {
            try {
                return await fetch(url, {
                    method: "GET",
                    headers: {
                        [csrfHeader!]: csrfToken!,
                    },
                });
            } catch (e: any) {
                console.error(`Attempt ${i + 1} failed: ${e.message}`);
                if (i === retries - 1) {
                    throw e;
                }
                await new Promise(resolve => setTimeout(resolve, 1000));
            }
        }
        throw new Error("Retry limit reached");
    }, [retries]);

    const processStream = useCallback(async (reader: ReadableStreamDefaultReader<Uint8Array>) => {
        const decoder = new TextDecoder("utf-8");
        let buffer = '';
        try {
            while (true) {
                const { done, value } = await reader.read();
                if (done) {
                    break;
                }
                const decoded = decoder.decode(value, { stream: true });
                buffer += decoded;

                const events = buffer.split('\n\n');

                buffer = events.pop() || '';

                for (const event of events) {
                    if (event.startsWith('data:')) {
                        const jsonData = event.substring(5);
                        try {
                            const res = JSON.parse(jsonData) as ChatInteraction;
                            res.answer = sanitize(res.answer);
                            setChatInteractions((prev) => {
                                const updatedMessages = [...prev];
                                const length = updatedMessages.length;

                                const current = updatedMessages[length - 1];

                                if (length > 0 && current.id === res.id) {
                                    updatedMessages[length - 1] = {
                                        ...current,
                                        answer: current.answer + res.answer,
                                        timeToProcess: res.timeToProcess,
                                    };
                                } else {
                                    updatedMessages.push(res);
                                }
                                return updatedMessages;
                            });
                        } catch (parseError) {
                            console.error('Error parsing JSON:', parseError);
                        }
                    } else if (event.trim() !== '') {
                        console.warn(`Unexpected event format: ${event}`);
                    }
                }
            }
        } catch (e: any) {
            console.error('Error processing stream:', e);
            setError(e);
        } finally {
            setIsLoading(false);
        }
    }, [setChatInteractions]);

    const sendMessage = useCallback(
        async (input: string) => {
            if (!path) {
                return;
            }
            setIsLoading(true);
            setError(null);
            try {
                const encoded = encodeURIComponent(input);
                const res = await fetchStreamWithRetry(`${path}?prompt=${encoded}`);
                if (!res.body) {
                    throw new Error("Response body is null");
                }
                const reader = res.body.getReader();
                await processStream(reader);
            } catch (e: any) {
                setError(e);
                setIsLoading(false);
            }
        },
        [path, fetchStreamWithRetry, processStream]
    );

    return { chatInteractions, sendMessage, isLoading, error };
};