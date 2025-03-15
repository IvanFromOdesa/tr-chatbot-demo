import {AuthStore} from "../../common/stores/auth.store.ts";
import {User} from "../../common/types/user.ts";
import {computed, makeObservable, observable} from "mobx";

export class KnowledgeBaseAuthStore extends AuthStore {
    _ctxUploaded: boolean = false;

    constructor(user: User | null) {
        super(user);
        makeObservable(this, {
            _ctxUploaded: observable,
            ctxUploaded: computed
        });
    }

    set ctxUploaded(ctxUploaded: boolean) {
        this._ctxUploaded = ctxUploaded;
    }

    get ctxUploaded(): boolean {
        return this._ctxUploaded;
    }
}