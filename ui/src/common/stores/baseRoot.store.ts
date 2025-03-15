import {computed, observable, runInAction} from "mobx";
import {UiStore} from "./ui.store.ts";
import {AnonymousPath, BasePath, EmployeePath, VisitorPath} from "../types/ui.ts";

export abstract class BaseRootStore<P extends BasePath | AnonymousPath | EmployeePath | VisitorPath, T> {
    _loading = false;
    uiStore: UiStore<P> = new UiStore(null, null, null);

    init = async (httpCall: () => Promise<T>, callback: (res: T) => void) => {
        this.loading = true;
        const res = await httpCall();
        runInAction(() => {
            callback(res);
            this.loading = false;
        });
    };

    set loading(loading: boolean) {
        this._loading = loading;
    }

    get loading(): boolean {
        return this._loading;
    }

    protected abstract initThis(initCall: () => Promise<T>): void;
}

export const baseRootStoreProps = {
    _loading: observable,
    loading: computed,
    uiStore: observable
}