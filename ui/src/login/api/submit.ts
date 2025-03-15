import React from "react";
import {LoginData} from "../types/login.data.ts";
import {baseAxios} from "../../common/api/base.ts";

export const submit = async (e: React.FormEvent, data: LoginData, url: string | undefined) => {
    e.preventDefault();

    const formData = new URLSearchParams();
    formData.append("username", data.username);
    formData.append("password", data.password);

    if (url) {
        return await baseAxios.post(url, formData.toString(), {
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
        });
    }
}