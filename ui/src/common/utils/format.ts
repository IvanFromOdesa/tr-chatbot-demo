import {Bundle} from "../types/ui.ts";

export const formatFileSize = (bytes: number, bundle: Bundle): string => {
    if (bytes < 0) {
        throw new Error("Bytes value cannot be negative");
    }

    const units = [
        bundle?.['page.pdf.fileSize.b'],
        bundle?.['page.pdf.fileSize.kb'],
        bundle?.['page.pdf.fileSize.mb']
    ];

    const threshold = 1024;

    let index = 0;
    let size = bytes;

    while (size >= threshold && index < units.length - 1) {
        size /= threshold;
        index++;
    }

    return `${size.toFixed(2)} ${units[index]}`;
}

export const formatDate = (timestamp: number | null | undefined, locale: string) => {
    return timestamp ? new Date(timestamp * 1000).toLocaleDateString(locale) : "";
}

export const formatDateTime = (timestamp: number | null | undefined, locale: string) => {
    if (timestamp) {
        const date = new Date(timestamp * 1000);
        return date.toLocaleDateString(locale) + " " + formatTime(date.getHours()) + ":" + formatTime(date.getMinutes()) + ":" + formatTime(date.getSeconds());
    } else {
        return "";
    }
}

export const formatSeconds = (seconds: number): number => {
    return Math.floor(seconds * 10) / 10;
}

function formatTime(time: number) {
    return (time < 10 ? '0' : '') + time;
}

export const formatDateString = (timestamp: number | undefined | null) => {
    if (timestamp) {
        const date = new Date(timestamp * 1000);
        return `${date.getFullYear()}-${formatTime(date.getMonth())}-${formatTime(date.getDay())}`;
    }
    return '';
}

export const formatPageInfo = (message: string | undefined, pageNumber: number,
                               pageSize: number, totalElements: number): string => {
    const start = pageNumber * pageSize + 1;
    const end = Math.min((pageNumber + 1) * pageSize, totalElements);
    const text = message ?? '';
    return text
        .replace('{0}', `${start}`)
        .replace('{1}', `${end}`)
        .replace('{2}', `${totalElements}`);
}

export const parseFileSize = (size: string) => {
    const unit = size.replace(/[0-9]/g, "").trim().toLowerCase();
    const value = parseInt(size.replace(/[a-zA-Z]/g, "").trim(), 10);
    switch (unit) {
        case "kb":
            return value * 1024;
        case "mb":
            return value * 1024 * 1024;
        default:
            return value;
    }
};