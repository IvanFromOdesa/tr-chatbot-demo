import * as yup from 'yup';
import {Bundle} from "../../common/types/ui.ts";

export const pdfUploadValidation = (bundle: Bundle, maxFileSizeBytes: number, maxFileSizeStr: string) => {
    const msg = bundle?.["page.pdf.validation.size"] || "";
    const maxFileSizeMsg = msg.replace("{0}", maxFileSizeStr);

    return yup.object().shape({
        files: yup
            .mixed<FileList>()
            .test("fileType", bundle?.["page.pdf.validation.format"] || "", (files) =>
                files instanceof FileList ? Array.from(files).every((file) => file.type === "application/pdf") : false
            )
            .test("fileSize", maxFileSizeMsg, (files) =>
                files instanceof FileList ? Array.from(files).every((file) => file.size <= maxFileSizeBytes) : false
            )
            .required(bundle?.["page.pdf.validation.required"]),
        translate: yup.boolean(),
    });
};