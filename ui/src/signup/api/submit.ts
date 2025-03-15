import {SignupData} from "../types/signup.data.ts";
import {baseAxios} from "../../common/api/base.ts";

export const submitSignup = async (data: SignupData, url: string | undefined) => {
    if (url) {
        return baseAxios.post(url, data);
    }
}