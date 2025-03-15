import {BaseRootStore} from "./baseRoot.store.ts";
import {AuthenticatedPath, EmployeePath, VisitorPath} from "../types/ui.ts";
import {AuthStore} from "./auth.store.ts";
import {observable} from "mobx";
import {AuthenticatedInitResponse} from "../types/init.ts";
import {UiStore} from "./ui.store.ts";
import {User} from "../types/user.ts";

export abstract class AuthenticatedRootStore<P extends AuthenticatedPath | EmployeePath | VisitorPath,
    T extends AuthenticatedInitResponse<P>,
    A extends AuthStore> extends BaseRootStore<P, T> {
    authStore: A;

    protected constructor(authStore: A) {
        super();
        this.authStore = authStore;
    }

    protected createAuthStore(userData: User): A {
        return new AuthStore(userData) as A;
    }

    protected initThis(initCall: () => Promise<T>): void {
        this.init(initCall, (res) => {
            this.uiStore = new UiStore<P>(
                res.navMenu,
                res.locale,
                res.assets,
                res.bundle,
                res.pathNavigation
            );
            this.authStore = this.createAuthStore(res.userData);
        });
    }
}

export const authenticatedRootStoreProps = {
    authStore: observable
}