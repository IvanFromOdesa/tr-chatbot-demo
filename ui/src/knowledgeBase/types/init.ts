import {Assets, Bundle, EmployeePath, LanguageCode, NavigationMenu, PathNavigation} from "../../common/types/ui.ts";
import {User} from "../../common/types/user.ts";
import {AuthenticatedInitResponse} from "../../common/types/init.ts";
import {QaValidation} from "./model.ts";

export class KnowledgeBaseInitResponse extends AuthenticatedInitResponse<EmployeePath> {
    maxUploadFileSize: string;
    qaValidation: QaValidation;

    constructor(bundle: Bundle, navMenu: NavigationMenu,
                pathNavigation: PathNavigation<EmployeePath>,
                assets: Assets | null, userData: User,
                locale: LanguageCode, maxUploadFileSize: string,
                qaValidation: QaValidation) {
        super(bundle, navMenu, pathNavigation, assets, userData, locale);
        this.maxUploadFileSize = maxUploadFileSize;
        this.qaValidation = qaValidation;
    }
}