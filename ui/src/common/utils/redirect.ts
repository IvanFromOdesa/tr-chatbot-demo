import {AxiosResponse} from "axios";

export const redirect = (res: AxiosResponse<any>) => {
    const redirectUrl = res?.request?.responseURL || res?.headers?.location;

    if (redirectUrl) {
        window.location.href = redirectUrl;
    }
}