import {useEffect, useState} from "react";

export const useNavbarPadding = () => {
    useEffect(() => {
        function adjustPadding() {
            requestAnimationFrame(() => {
                if (document.body.classList.contains('has-navbar-padding')) {
                    const navbar = document.querySelector('.navbar-custom') as HTMLElement;
                    if (navbar) {
                        const navbarHeight = navbar.offsetHeight;
                        document.body.style.paddingTop = `${navbarHeight}px`;
                    }
                }
            });
        }

        adjustPadding();
        window.addEventListener('resize', adjustPadding);

        return () => {
            window.removeEventListener('resize', adjustPadding);
        };
    }, []);
};

export const useUrlErrorMessage = (message: string | undefined) => {
    const [error, setError] = useState<string | undefined>(undefined);

    useEffect(() => {
        const params = new URLSearchParams(window.location.search);
        if (params.has("error")) {
            setError(message);
        }
    }, [message]);

    return error;
};

export const useSSE = <T, A>(url: string, input?: A) => {
    const [data, setData] = useState<T | null>(null);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        let eventSourceUrl = url;

        if (input) {
            const queryParams = new URLSearchParams();
            queryParams.append('input', JSON.stringify(input));
            eventSourceUrl = `${url}?${queryParams.toString()}`;
        }

        const eventSource = new EventSource(eventSourceUrl);

        eventSource.onmessage = (event) => {
            try {
                const parsedData = JSON.parse(event.data) as T;
                setData(parsedData);
            } catch (err) {
                setError("Error parsing SSE data.");
            }
        };

        eventSource.onerror = () => {
            setError("SSE connection error.");
            eventSource.close();
        };

        return () => {
            eventSource.close();
        };
    }, [url, input]);

    return { data, error };
};

export const useScrollToElement = (element: HTMLDivElement | null, delay: number = 0) => {
    useEffect(() => {
        if (element) {
            const timeoutId = setTimeout(() => {
                history.replaceState(null, "", `#${element.id}`);
                element.scrollIntoView({ behavior: "smooth", block: "start" });
            }, delay);

            return () => clearTimeout(timeoutId);
        }
    }, [element, delay]);
};