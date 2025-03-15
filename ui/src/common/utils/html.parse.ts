import DOMPurify from 'dompurify';
import parse from 'html-react-parser';

export const sanitize = (htmlString: string) => {
    return DOMPurify.sanitize(htmlString, {USE_PROFILES: {html: true}});
}

export const htmlFrom = (htmlString: string | undefined | null) => {
    if (htmlString) {
        const cleanHtmlString = sanitize(htmlString);
        return parse(cleanHtmlString);
    }
    return "";
}