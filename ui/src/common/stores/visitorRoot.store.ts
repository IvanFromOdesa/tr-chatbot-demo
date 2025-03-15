import {AuthenticatedRootStore, authenticatedRootStoreProps} from "./authenticatedRoot.store.ts";
import {VisitorPath} from "../types/ui.ts";
import {VisitorInitResponse} from "../types/init.ts";
import {AuthStore} from "./auth.store.ts";
import {action, makeObservable} from "mobx";
import {baseRootStoreProps} from "./baseRoot.store.ts";

export class VisitorRootStore extends AuthenticatedRootStore<VisitorPath, VisitorInitResponse, AuthStore> {
    constructor(initCall: () => Promise<VisitorInitResponse>) {
        super(new AuthStore(null));
        makeObservable(this, {
            ...baseRootStoreProps,
            ...authenticatedRootStoreProps,
            init: action
        });
        this.initThis(initCall);
    }
}