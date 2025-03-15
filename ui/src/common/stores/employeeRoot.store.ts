import {AuthenticatedRootStore, authenticatedRootStoreProps} from "./authenticatedRoot.store.ts";
import {EmployeePath} from "../types/ui.ts";
import {action, makeObservable} from "mobx";
import {baseRootStoreProps} from "./baseRoot.store.ts";
import {AuthStore} from "./auth.store.ts";
import {EmployeeInitResponse} from "../types/init.ts";

export class EmployeeRootStore extends AuthenticatedRootStore<EmployeePath, EmployeeInitResponse, AuthStore> {
    constructor(initCall: () => Promise<EmployeeInitResponse>) {
        super(new AuthStore(null));
        makeObservable(this, {
            ...baseRootStoreProps,
            ...authenticatedRootStoreProps,
            init: action
        });
        this.initThis(initCall);
    }
}