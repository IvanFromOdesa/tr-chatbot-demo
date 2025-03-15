import {User} from "../../common/types/user.ts";
import {Language} from "../../common/types/ui.ts";
import {WithPagination} from "../../common/types/pagination.ts";

export interface PdfModel {
    id: number;
    uploadedAt: number;
    fileName: string;
    originalFileName: string;
    size: number;
    translatedToSupported: boolean;
    uploadedBy: User;
}

export interface QAModel {
    dbId: number;
    q: string;
    a: string;
    vectorStoreId: string;
    uploadedAt: number;
    language: Language;
    uploadedBy: User;
    translateStatus: TranslateStatus;
    originalQdbId: number | null;
}

export type TranslateStatus = 0 | 1;

export interface PdfModelPage extends WithPagination<PdfModel> {}

export interface QAModelPage extends WithPagination<QAModel> {}

export interface QaValidation {
    questionMaxSize: number;
    answerMaxSize: number;
}

export interface PdfFormValues {
    files: FileList | null;
    translate: boolean;
}

export interface QAFormValues {
    qas: { q: string; a: string }[];
    translateToSupported: boolean;
}