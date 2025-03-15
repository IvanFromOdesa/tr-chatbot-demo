import {User} from "../types/user.ts";
import {makeObservable, observable} from "mobx";

export class AuthStore {
    user: User | null = null;

    constructor(user: User | null) {
        this.user = user || this.user;
        makeObservable(this, {
            user: observable
        });
    }

    getUserName(): string {
        return this.user?.firstName + ' ' + this.user?.lastName;
    }
}