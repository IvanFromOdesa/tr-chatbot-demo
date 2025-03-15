import {baseAxios} from "../../common/api/base.ts";
import {QAFormValues} from "../types/model.ts";
import {WithPagination} from "../../common/types/pagination.ts";

export const getData = async <T extends WithPagination<any>>(
    path: string | undefined,
    page?: number,
    size?: number
): Promise<T> => {
    if (!path) {
        return Promise.reject();
    }
    const res = await baseAxios.get<T>(getUrl(path, page, size));
    return res.data;
};

const getUrl = (baseUrl: string, page?: number, size?: number) => {
    let url = baseUrl;
    if (page && size) {
        url = url.concat(`?p=${page}&size=${size}`)
    } else if (page) {
        url = url.concat(`?p=${page}`);
    } else if (size) {
        url = url.concat(`?size=${size}`);
    }
    return url;
}

export const uploadPdfs = async (
    files: FileList | null,
    translate: boolean,
    path: string | undefined
) => {
    if (path && files) {
        const formData = new FormData();

        Array.from(files).forEach((file) => {
            formData.append("files", file);
        });

        formData.append("translate", String(translate));
        return await baseAxios.post(path, formData, {
            headers: {
                "Content-Type": "multipart/form-data",
            },
        });
    }
};

export const uploadQAs = async (
    qaFormValues: QAFormValues,
    path: string | undefined
) => {
    if (path) {
        return baseAxios.post(path, qaFormValues)
    }
}