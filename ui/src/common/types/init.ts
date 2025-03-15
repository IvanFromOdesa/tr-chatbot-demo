import {
    AnonymousPath,
    Assets, AuthenticatedPath,
    BasePath,
    Bundle, EmployeePath,
    HomeAnonymousPath,
    LanguageCode,
    NavigationMenu,
    PathNavigation, VisitorPath
} from "./ui.ts";
import {User} from "./user.ts";

export abstract class BaseInitResponse<P extends BasePath | AnonymousPath | EmployeePath | VisitorPath> {
    bundle: Bundle;
    navMenu: NavigationMenu;
    pathNavigation: PathNavigation<P>;
    assets: Assets | null;
    locale: LanguageCode;

    protected constructor(bundle: Bundle,
                          navMenu: NavigationMenu,
                          pathNavigation: PathNavigation<P>,
                          assets: Assets | null,
                          locale: LanguageCode) {
        this.bundle = bundle;
        this.navMenu = navMenu;
        this.pathNavigation = pathNavigation;
        this.assets = assets;
        this.locale = locale;
    }
}

export class InitResponse extends BaseInitResponse<HomeAnonymousPath> {
    constructor(bundle: Bundle,
                navMenu: NavigationMenu,
                pathNavigation: PathNavigation<HomeAnonymousPath>,
                assets: Assets | null,
                locale: LanguageCode) {
        super(bundle, navMenu, pathNavigation, assets, locale);
    }
}

export abstract class AuthenticatedInitResponse<P extends AuthenticatedPath | EmployeePath | VisitorPath> extends BaseInitResponse<P> {
    userData: User;

    protected constructor(bundle: Bundle,
                          navMenu: NavigationMenu,
                          pathNavigation: PathNavigation<P>,
                          assets: Assets | null,
                          userData: User,
                          locale: LanguageCode) {
        super(bundle, navMenu, pathNavigation, assets, locale);
        this.userData = userData;
    }
}

export class EmployeeInitResponse extends AuthenticatedInitResponse<EmployeePath> {
    constructor(bundle: Bundle,
                navMenu: NavigationMenu,
                pathNavigation: PathNavigation<EmployeePath>,
                assets: Assets | null,
                userData: User,
                locale: LanguageCode) {
        super(bundle, navMenu, pathNavigation, assets, userData, locale);
    }
}

export class VisitorInitResponse extends AuthenticatedInitResponse<VisitorPath> {
    constructor(bundle: Bundle,
                navMenu: NavigationMenu,
                pathNavigation: PathNavigation<VisitorPath>,
                assets: Assets | null,
                userData: User,
                locale: LanguageCode) {
        super(bundle, navMenu, pathNavigation, assets, userData, locale);
    }
}