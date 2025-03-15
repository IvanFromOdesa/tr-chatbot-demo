import {action, makeObservable} from "mobx";
import {UiStore} from "./ui.store.ts";
import {BaseRootStore, baseRootStoreProps} from "./baseRoot.store.ts";
import {HomeAnonymousPath} from "../types/ui.ts";
import {InitResponse} from "../types/init.ts";

export class AnonymousRootStore extends BaseRootStore<HomeAnonymousPath, InitResponse> {

    constructor(initCall: () => Promise<InitResponse>) {
        super();
        makeObservable(this, {
            ...baseRootStoreProps,
            init: action
        })
        this.initThis(initCall);
    }

    initThis = (initCall: () => Promise<InitResponse>) => {
        this.init(initCall, (res) => {
            this.uiStore = new UiStore(
                res.navMenu,
                res.locale,
                res.assets,
                res.bundle,
                res.pathNavigation
            );
        });
    }
}