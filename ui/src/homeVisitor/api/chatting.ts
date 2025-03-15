import {baseAxios} from "../../common/api/base.ts";
import {ChatAvatar, ChatFeedback, Conversation, ConversationStatus} from "../types/chat.ts";
import {PUBLIC_URL} from "../../common/config.ts";

export const getChatAvatars = async (path: string | undefined) => {
    if (path) {
        const res = await baseAxios.get<ChatAvatar[]>(PUBLIC_URL + path);
        return res.data as ChatAvatar[];
    }
}

export const getOngoingConversation = async (path: string | undefined) => {
    if (path) {
        const res = await baseAxios.get<Conversation>(path);
        return res.data as Conversation;
    }
}

export const changeConversationStatus = async (path: string | undefined, feedback: ChatFeedback) => {
    if (path) {
        const res = await baseAxios.post(`${path}?res=${feedback}`);
        return res.data as ConversationStatus;
    }
}

