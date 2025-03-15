import {Variant} from "react-bootstrap/types";

export type Bundle = Record<string, string>;
export type Assets = Record<string, string>;

export type BasePath = "home" | "homeInit" | "sessionMsg" | "langPreference" | "avatarIcons";
export type AnonymousPath = "login" | "loginInit" | "loginSubmit" | "signup" | "signupInit" | "signupSubmit" | "tos";

export type HomeAnonymousPath = BasePath | AnonymousPath;
export type AuthenticatedPath = BasePath | "logout";

export type EmployeePath = AuthenticatedPath | "knowledgeBase" | "knowledgeBaseInit" | "knowledgeBaseGetQAs" |
    "knowledgeBasePdfPreview" | "knowledgeBasePdfDownload" | "knowledgeBaseGetPdfs" |
    "knowledgeBaseUploadPdf" | "knowledgeBaseUploadJson" | "report" | "reportInit" | "reportDownload" | "howTo";
export type VisitorPath = AuthenticatedPath | "chat" | "chatFeedback" | "currentConversation";

export type AllPaths = BasePath | AnonymousPath | AuthenticatedPath | EmployeePath | VisitorPath;

export type PathNavigation<T extends AllPaths> = {
    [K in T]: string;
};

export interface NavigationMenu {
    logoPath: string;
    languages: Language[];
}

export interface Language {
    alias: LanguageCode;
    name: string;
    helpText: string;
    imagePath: string;
}

export interface SessionMsg {
    msg: string;
    variant: Variant;
}

export type LanguageCode = 'en-US' | 'nb' | 'uk' | 'ru' | '';