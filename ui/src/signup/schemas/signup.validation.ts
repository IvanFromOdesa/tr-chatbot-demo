import * as yup from 'yup';
import {Bundle} from "../../common/types/ui.ts";

export const signupValidation = (bundle: Bundle) => {
    return yup.object().shape({
        firstName: yup.string()
            .matches(
                /^\p{Lu}\p{L}*(?:[ '-]\p{Lu}?\p{L}*)*$/u,
                bundle?.['validation.name.regex']
            )
            .required(bundle?.['validation.firstName']),
        lastName: yup.string()
            .matches(
                /^\p{Lu}\p{L}*(?:[ '-]\p{Lu}?\p{L}*)*$/u,
                bundle?.['validation.name.regex']
            )
            .required(bundle?.['validation.lastName']),
        email: yup.string()
            .email(bundle?.['validation.email'])
            .required(bundle?.['validation.email']),
        pwdForm: yup.object().shape({
            password: yup.string()
                .matches(
                    /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?])\S{8,}$/,
                    bundle?.['validation.password']
                )
                .required(bundle?.['validation.password']),
            confirmPassword: yup.string()
                .oneOf([yup.ref('password'), undefined], bundle?.['validation.confirmPassword'])
                .required(bundle?.['validation.confirmPassword'])
        }),
        tosChecked: yup.bool().isTrue(bundle?.['validation.tos']).required(bundle?.['validation.tos'])
    });
}