import {AuthenticatedRootStore, authenticatedRootStoreProps} from "../../common/stores/authenticatedRoot.store.ts";
import {EmployeePath} from "../../common/types/ui.ts";
import {KnowledgeBaseInitResponse} from "../types/init.ts";
import {action, makeObservable} from "mobx";
import {baseRootStoreProps} from "../../common/stores/baseRoot.store.ts";
import {UiStore} from "../../common/stores/ui.store.ts";
import {KnowledgeBaseAuthStore} from "./knowledgeBase.auth.store.ts";
import {QaValidation} from "../types/model.ts";

export class KnowledgeBaseRootStore extends AuthenticatedRootStore<EmployeePath, KnowledgeBaseInitResponse, KnowledgeBaseAuthStore> {
    maxUploadFileSize: string;
    qaValidation: QaValidation;

    constructor(initCall: () => Promise<KnowledgeBaseInitResponse>) {
        super(new KnowledgeBaseAuthStore(null));

        this.maxUploadFileSize = '1MB';
        this.qaValidation = {
            questionMaxSize: 100,
            answerMaxSize: 1000
        };

        makeObservable(this, {
            ...baseRootStoreProps,
            ...authenticatedRootStoreProps,
            init: action
        });
        this.initThis(initCall);
    }

    protected initThis(initCall: () => Promise<KnowledgeBaseInitResponse>): void {
        this.init(initCall, res => {
            this.uiStore = new UiStore<EmployeePath>(
                res.navMenu,
                res.locale,
                res.assets,
                res.bundle,
                res.pathNavigation
            );
            this.authStore = new KnowledgeBaseAuthStore(res.userData);
            this.maxUploadFileSize = res.maxUploadFileSize;
            this.qaValidation = res.qaValidation;
        })
    }
}