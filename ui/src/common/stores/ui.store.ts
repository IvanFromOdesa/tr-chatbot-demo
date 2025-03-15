import {AllPaths, Assets, Bundle, LanguageCode, NavigationMenu, PathNavigation} from "../types/ui.ts";
import {makeAutoObservable} from "mobx";

export class UiStore<P extends AllPaths> {
    bundle: Bundle = {};
    pathNavigation: PathNavigation<P> | null = null;
    navMenu: NavigationMenu | null;
    assets: Assets | null;
    locale: LanguageCode | null;

    constructor(navMenu: NavigationMenu | null,
                locale: LanguageCode | null,
                assets: Assets | null,
                bundle?: Bundle,
                pathNavigation?: PathNavigation<P>) {
        makeAutoObservable(this);
        this.navMenu = navMenu;
        this.locale = locale;
        this.pathNavigation = pathNavigation || this.pathNavigation;
        this.bundle = bundle || this.bundle;
        this.assets = assets || null;
    }
}