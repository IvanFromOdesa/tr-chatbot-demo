import {Alert, Button, Col, Container, Form, Row} from "react-bootstrap";
import React, {useState} from "react";
import './Login.css';
import {useStoreContext} from "../login.tsx";
import {LoginData} from "../types/login.data.ts";
import {useUrlErrorMessage} from "../../common/utils/hooks.ts";
import {submit} from "../api/submit.ts";
import PasswordToggleIcon from "../../common/components/PasswordToggleIcon.tsx";
import {redirect} from "../../common/utils/redirect.ts";

const Login = () => {
    const anonymousRootStore = useStoreContext();
    const {uiStore: {bundle, pathNavigation}} = anonymousRootStore;

    const [loginData, setLoginData] = useState<LoginData>({
        username: "",
        password: "",
    });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setLoginData({ ...loginData, [e.target.name]: e.target.value });
    };

    const errorMsg = useUrlErrorMessage(bundle?.['page.error.login']);
    const [emailFocused, setEmailFocused] = useState(false);
    const [passwordFocused, setPasswordFocused] = useState(false);

    const [showPassword, setShowPassword] = useState(false);

    const handleSubmit = (e: React.FormEvent) => {
        anonymousRootStore.loading = true;
        submit(e, loginData, pathNavigation?.loginSubmit)
            .then(res => {
                anonymousRootStore.loading = false;
                if (res) {
                    redirect(res);
                }
            });
    }

    return (
        <Container className="form-container">
            <Row className="justify-content-center">
                <Col>
                    <div className="form-content">
                        <h2 className="login-title">{bundle?.['page.title']}</h2>
                        {
                            errorMsg && <Alert variant="danger" className="justify-content-center">{errorMsg}</Alert>
                        }
                        <Form onSubmit={handleSubmit} className="login-form">
                            <Form.Group controlId="email" className={`input-wrapper ${emailFocused ? 'focused' : ''}`}>
                                <Form.Label className="floating-label">{bundle?.['page.enterEmail']}</Form.Label>
                                <Form.Control
                                    name="username"
                                    type="email"
                                    value={loginData.username}
                                    onChange={handleChange}
                                    onFocus={() => setEmailFocused(true)}
                                    onBlur={() => setEmailFocused(loginData.username !== '')}
                                    isInvalid={!!errorMsg}
                                    required
                                />
                            </Form.Group>

                            <Form.Group controlId="password"
                                        className={`input-wrapper ${passwordFocused ? 'focused' : ''}`}>
                                <Form.Label className="floating-label">{bundle?.['page.enterPassword']}</Form.Label>
                                <div className="password-input-wrapper">
                                    <Form.Control
                                        name="password"
                                        type={showPassword ? "text" : "password"}
                                        value={loginData.password}
                                        onChange={handleChange}
                                        onFocus={() => setPasswordFocused(true)}
                                        onBlur={() => setPasswordFocused(loginData.password !== '')}
                                        isInvalid={!!errorMsg}
                                        required
                                    />
                                    {
                                        !errorMsg &&
                                        <PasswordToggleIcon
                                            showPassword={showPassword}
                                            setShowPassword={setShowPassword}
                                        />
                                    }
                                </div>
                            </Form.Group>

                            <Button variant="primary" type="submit" className="form-button">
                                {bundle?.['page.signin']}
                            </Button>

                            <p className="mt-3 text-center">
                            {bundle?.['page.noAccount']}&nbsp;
                                <a href={pathNavigation?.signup || ''}>{bundle?.['page.signup']}</a>
                            </p>
                        </Form>
                    </div>
                </Col>
            </Row>
        </Container>
    );
}

export default Login;