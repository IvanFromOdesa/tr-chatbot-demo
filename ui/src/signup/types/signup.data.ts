export interface SignupData {
    firstName: string;
    lastName: string;
    email: string;
    pwdForm: PasswordData;
    tosChecked: boolean;
}

export interface PasswordData {
    password: string;
    confirmPassword: string;
}

export const getDefault = (): SignupData => {
    return {
        firstName: '',
        lastName: '',
        email: '',
        pwdForm: {
            password: '',
            confirmPassword: ''
        },
        tosChecked: false
    };
}