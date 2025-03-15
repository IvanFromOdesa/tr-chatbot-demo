import {Language} from "../../common/types/ui.ts";

export interface Conversation {
    startedAt: number | null;
    status: ConversationStatus;
    chatInteractions: ChatInteraction[];
}

export interface ChatInteraction {
    id: string;
    question: string;
    questionLanguage: Language;
    answer: string;
    askedAt: number;
    timeToProcess: number;
}

export interface ChatAvatar {
    name: string;
    path: string;
    type: AvatarType;
}

export type AvatarType = 'Bot' | 'User';
export type ConversationStatus = 'initialized' | 'not_yet_completed' |
    'completed_explicitly' | 'completed_implicitly';
export type ChatFeedback = 0 | 1;