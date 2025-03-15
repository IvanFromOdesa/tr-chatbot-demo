import * as yup from 'yup';
import {Bundle} from "../../common/types/ui.ts";
import {QaValidation} from "../types/model.ts";

export const qaUploadValidation = (bundle: Bundle, qaValidation: QaValidation) => {
    return yup.object().shape({
        qas: yup.array().of(
            yup.object().shape({
                q: yup.string()
                    .required(bundle?.['page.view.qa.modal.q.validation.required'])
                    .max(qaValidation.questionMaxSize, bundle?.['page.view.qa.modal.q.validation.size']),
                a: yup.string()
                    .required(bundle?.['page.view.qa.modal.a.validation.required'])
                    .max(qaValidation.answerMaxSize, bundle?.['page.view.qa.modal.a.validation.size']),
            })
        ),
        translateToSupported: yup.boolean(),
    });
}