import './Signup.css';
import {Button, Col, Container, Form, Row} from "react-bootstrap";
import {useState} from "react";
import {getDefault, SignupData} from "../types/signup.data.ts";
import {Formik, FormikTouched, FormikValues} from 'formik';
import {useStoreContext} from "../signup.tsx";
import {PUBLIC_URL} from "../../common/config.ts";
import {signupValidation} from "../schemas/signup.validation.ts";
import {submitSignup} from "../api/submit.ts";
import PasswordToggleIcon from "../../common/components/PasswordToggleIcon.tsx";
import {redirect} from "../../common/utils/redirect.ts";

const getFeedbackInvalid = (errorMsg: string | undefined) => {
    return <Form.Control.Feedback
        type="invalid"
        tooltip
    >
        {errorMsg}
    </Form.Control.Feedback>
}

const Signup = () => {
    const anonymousRootStore = useStoreContext();
    const { uiStore: { bundle, pathNavigation, assets } } = anonymousRootStore;

    const [firstNameFocused, setFirstNameFocused] = useState(false);
    const [lastNameFocused, setLastNameFocused] = useState(false);
    const [emailFocused, setEmailFocused] = useState(false);
    const [passwordFocused, setPasswordFocused] = useState(false);
    const [confirmPasswordFocused, setConfirmPasswordFocused] = useState(false);

    const [showPassword, setShowPassword] = useState(false);
    const [showConfirmPassword, setShowConfirmPassword] = useState(false);

    const submit = (values: FormikValues) => {
        anonymousRootStore.loading = true;
        submitSignup(values as SignupData, pathNavigation?.signupSubmit).then(res => {
            anonymousRootStore.loading = false;
            if (res) {
                redirect(res);
            }
        });
    }

    const shouldHideVisibilityIcon = <T extends Record<string, any>>(
        touched: FormikTouched<T> | undefined,
        fieldPath: keyof T
    ): boolean => {
        return Boolean(touched?.[fieldPath]);
    };

    return (
        <Container className="form-container">
            <Row className="form-content">
                <Col className="d-flex justify-content-center align-items-center" style={{backgroundColor: "#f8f9fa"}} md={6}>
                    <div className="p-3">
                        <img src={`${PUBLIC_URL}${assets?.['chatBot']}`} alt="ChatBot" className="chatbot-img"/>
                    </div>
                </Col>
                <Col className="p-3">
                    <Row>
                        <h3 className="signup-title">{bundle?.['page.createAccount']}</h3>
                    </Row>
                    <Row>
                        <Formik
                            initialValues={getDefault()}
                            validationSchema={signupValidation(bundle)}
                            onSubmit={(values) => {submit(values);}}
                        >
                            {({handleSubmit, handleChange, values, touched, errors }) => (
                                <Form noValidate onSubmit={handleSubmit}>
                                    <Row>
                                        <Form.Group className={`col-lg-6 input-wrapper ${firstNameFocused ? 'focused' : ''}`}
                                                    controlId="firstName">
                                            <Form.Label className="floating-label">{bundle?.['page.enterFirstName']}</Form.Label>
                                            <Form.Control
                                                name="firstName"
                                                type="text"
                                                value={values.firstName}
                                                onChange={handleChange}
                                                onFocus={() => setFirstNameFocused(true)}
                                                onBlur={() => setFirstNameFocused(values.firstName !== '')}
                                                isValid={touched.firstName && !errors.firstName}
                                                isInvalid={touched.firstName && !!errors.firstName}
                                            />
                                            {getFeedbackInvalid(errors?.firstName)}
                                        </Form.Group>
                                        <Form.Group className={`col-lg-6 input-wrapper ${lastNameFocused ? 'focused' : ''}`}
                                                    controlId="lastName">
                                            <Form.Label className="floating-label">{bundle?.['page.enterLastName']}</Form.Label>
                                            <Form.Control
                                                name="lastName"
                                                type="text"
                                                value={values.lastName}
                                                onChange={handleChange}
                                                onFocus={() => setLastNameFocused(true)}
                                                onBlur={() => setLastNameFocused(values.lastName !== '')}
                                                isValid={touched.lastName && !errors.lastName}
                                                isInvalid={touched.lastName && !!errors.lastName}
                                            />
                                            {getFeedbackInvalid(errors?.lastName)}
                                        </Form.Group>
                                    </Row>
                                    <Row>
                                        <Form.Group className={`input-wrapper ${emailFocused ? 'focused' : ''}`}
                                                    controlId="email">
                                            <Form.Label
                                                className="floating-label">{bundle?.['page.enterEmail']}</Form.Label>
                                            <Form.Control
                                                name="email"
                                                type="email"
                                                value={values.email}
                                                onChange={handleChange}
                                                onFocus={() => setEmailFocused(true)}
                                                onBlur={() => setEmailFocused(values.email !== '')}
                                                isValid={touched.email && !errors.email}
                                                isInvalid={touched.email && !!errors.email}
                                            />
                                            {getFeedbackInvalid(errors?.email)}
                                        </Form.Group>
                                    </Row>
                                    <Row>
                                        <Form.Group className={`input-wrapper ${passwordFocused ? 'focused' : ''}`}
                                                    controlId="password">
                                            <Form.Label
                                                className="floating-label">{bundle?.['page.enterPassword']}</Form.Label>
                                            <Form.Control
                                                name="pwdForm.password"
                                                type={showPassword ? "text" : "password"}
                                                value={values.pwdForm?.password || ''}
                                                onChange={handleChange}
                                                onFocus={() => setPasswordFocused(true)}
                                                onBlur={() => setPasswordFocused(values.pwdForm.password !== '')}
                                                isValid={touched.pwdForm?.password && !errors.pwdForm?.password}
                                                isInvalid={touched.pwdForm?.password && !!errors.pwdForm?.password}
                                            />
                                            {
                                                !shouldHideVisibilityIcon(touched.pwdForm, "password") &&
                                                <PasswordToggleIcon
                                                    showPassword={showPassword}
                                                    setShowPassword={setShowPassword}
                                                    rightPadding={20}
                                                />
                                            }
                                            {getFeedbackInvalid(errors?.pwdForm?.password)}
                                        </Form.Group>
                                    </Row>
                                    <Row>
                                        <Form.Group
                                            className={`input-wrapper ${confirmPasswordFocused ? 'focused' : ''}`}
                                            controlId="confirmPassword">
                                            <Form.Label
                                                className="floating-label">{bundle?.['page.confirmPassword']}</Form.Label>
                                            <Form.Control
                                                name="pwdForm.confirmPassword"
                                                type={showConfirmPassword ? "text" : "password"}
                                                value={values.pwdForm?.confirmPassword || ''}
                                                onChange={handleChange}
                                                onFocus={() => setConfirmPasswordFocused(true)}
                                                onBlur={() => setConfirmPasswordFocused(values.pwdForm.confirmPassword !== '')}
                                                isValid={touched.pwdForm?.confirmPassword && !errors.pwdForm?.confirmPassword}
                                                isInvalid={touched.pwdForm?.confirmPassword && !!errors.pwdForm?.confirmPassword}
                                            />
                                            {
                                                !shouldHideVisibilityIcon(touched.pwdForm, "confirmPassword") &&
                                                <PasswordToggleIcon
                                                    showPassword={showConfirmPassword}
                                                    setShowPassword={setShowConfirmPassword}
                                                    rightPadding={20}
                                                />
                                            }
                                            {getFeedbackInvalid(errors?.pwdForm?.confirmPassword)}
                                        </Form.Group>
                                    </Row>
                                    <Row>
                                        <Form.Group controlId="tosChecked">
                                            <Form.Check
                                                name="tosChecked"
                                                type="checkbox"
                                                label={
                                                    <p>
                                                        {bundle?.['page.tosStart']}&nbsp;
                                                        <a href={pathNavigation?.tos || ''}>{bundle?.['page.tosEnd']}</a>
                                                    </p>
                                                }
                                                checked={values.tosChecked}
                                                onChange={handleChange}
                                                isValid={touched.tosChecked && !errors.tosChecked}
                                                isInvalid={touched.tosChecked && !!errors.tosChecked}
                                            />
                                            {getFeedbackInvalid(errors?.tosChecked)}
                                        </Form.Group>
                                    </Row>
                                    <Row>
                                        <Form.Group>
                                            <Button variant="primary" type="submit" className="form-button">
                                                {bundle?.['page.signupButton']}
                                            </Button>
                                        </Form.Group>
                                    </Row>
                                    <Row>
                                        <Form.Group>
                                            <p className="mt-3 text-center">
                                                {bundle?.['page.alreadyHaveAccount']}&nbsp;
                                                <a href={pathNavigation?.login || ''}>{bundle?.['page.logIn']}</a>
                                            </p>
                                        </Form.Group>
                                    </Row>
                                </Form>
                            )}
                        </Formik>
                    </Row>
                </Col>
            </Row>
        </Container>
    );
}

export default Signup;