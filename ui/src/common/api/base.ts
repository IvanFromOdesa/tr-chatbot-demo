import axios from "axios";
import {plainToInstance} from "class-transformer";
import {BaseInitResponse} from "../types/init.ts";
import {SessionMsg} from "../types/ui.ts";

export const csrfToken = document.querySelector('meta[name="_csrf"]')?.getAttribute("content");
export const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.getAttribute("content");

export const baseAxios = axios.create({
    headers: {
        [csrfHeader!]: csrfToken!,
    },
});

export const init = async <T extends BaseInitResponse<any>>(path: string,  cls: new (...args: any[]) => T) => {
    const res = await baseAxios.get<T>(path);
    return plainToInstance(cls, res.data as object);
}

export const setLanguagePreference = async (path: string | undefined, lang: string) => {
    if (path) {
        await baseAxios.post(`${path}?key=${lang}`);
    }
}

export const logout = async (path: string | undefined) => {
    if (path) {
        return await baseAxios.post(path, {}, { withCredentials: true });
    }
}

export const getSessionMsg = async (path: string | undefined) => {
    if (path) {
        const res = await baseAxios.get<SessionMsg>(path);
        return res.data;
    }
}