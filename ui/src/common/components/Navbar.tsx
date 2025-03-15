import {Alert, Button, Container, Dropdown, Nav, Navbar as BootstrapNavbar} from 'react-bootstrap';
import './Navbar.css';
import {UiStore} from "../stores/ui.store.ts";
import {PUBLIC_URL} from "../config.ts";
import {AllPaths, BasePath, LanguageCode, PathNavigation, SessionMsg} from "../types/ui.ts";
import {getSessionMsg, logout, setLanguagePreference} from "../api/base.ts";
import {AuthStore} from "../stores/auth.store.ts";
import {useEffect, useState} from "react";
import {useNavbarPadding} from "../utils/hooks.ts";
import {redirect} from "../utils/redirect.ts";

interface INavbarProps {
    uiStore: UiStore<AllPaths>;
    authStore?: AuthStore;
}

const Navbar = ({uiStore, authStore}: INavbarProps) => {
    const {navMenu, bundle, pathNavigation, locale} = uiStore;
    const [selectedLanguage, setSelectedLanguage] = useState<LanguageCode | null>(locale);

    const [sessionMsg, setSessionMsg] = useState<SessionMsg | null>(null);

    useNavbarPadding();

    useEffect(() => {
        getSessionMsg(pathNavigation?.sessionMsg)
            .then(data => {
                if (data) {
                    setSessionMsg(data);
                }
            })
    }, []);


    const handleLanguageChange = (lang: LanguageCode) => {
        uiStore.locale = lang;
        setSelectedLanguage(lang);
        setLanguagePreference((pathNavigation as PathNavigation<BasePath>)?.langPreference, lang)
            .then(() => {
                window.location.reload();
            })
    };

    const handleLogout = () => {
        logout(pathNavigation?.logout)
            .then(res => {
                if (res) {
                    redirect(res);
                }
            })
    };

    const logoPath = uiStore.navMenu?.logoPath;

    const user = authStore?.user;

    return (
        <div className="navbar-container fixed-top">
            {
                sessionMsg?.msg &&
                <Alert
                    variant={sessionMsg.variant || 'primary'}
                    className="session-alert"
                    onClose={() => setSessionMsg(null)}
                >
                    {sessionMsg.msg}
                </Alert>
            }
            <BootstrapNavbar bg="light" expand="lg" className="navbar-custom">
                <Container>
                    <BootstrapNavbar.Brand href={pathNavigation?.home} className="navbar-brand-custom">
                        {
                            logoPath && <img src={`${PUBLIC_URL}${logoPath}`} alt="Logo" className="navbar-logo"/>
                        }
                    </BootstrapNavbar.Brand>
                    <BootstrapNavbar.Toggle aria-controls="basic-navbar-nav"/>
                    <BootstrapNavbar.Collapse id="basic-navbar-nav" className="justify-content-end">
                        <Nav className="me-3">
                            {
                                user ?
                                    <BootstrapNavbar.Text>
                                        {
                                            authStore?.getUserName()
                                        }
                                    </BootstrapNavbar.Text> :
                                    <Nav.Link href={pathNavigation?.login}>{bundle?.['nav.logIn']}</Nav.Link>
                            }
                        </Nav>
                        <Nav className="me-3">
                            {navMenu?.languages && (
                                <Dropdown>
                                    <Dropdown.Toggle variant="light" id="dropdown-basic" className="language-dropdown">
                                        {bundle?.['nav.language']}
                                    </Dropdown.Toggle>
                                    <Dropdown.Menu>
                                        {navMenu.languages.map((lang) => (
                                            <Dropdown.Item
                                                key={lang.alias}
                                                onClick={() => handleLanguageChange(lang.alias)}
                                                title={lang.helpText}
                                                active={lang.alias === selectedLanguage}
                                            >
                                                <img
                                                    src={`${PUBLIC_URL}${lang.imagePath}`}
                                                    alt={lang.name}
                                                    className="language-icon me-2"
                                                    width="30"
                                                    height="22.5"
                                                />
                                                {lang.name}
                                            </Dropdown.Item>
                                        ))}
                                    </Dropdown.Menu>
                                </Dropdown>
                            )}
                        </Nav>
                        {
                            user &&
                            <Nav>
                                <Button
                                    className="nav-link me-auto"
                                    variant="link"
                                    onClick={handleLogout}
                                >
                                    {bundle?.['nav.logout']}
                                </Button>
                            </Nav>
                        }
                    </BootstrapNavbar.Collapse>
                </Container>
            </BootstrapNavbar>
        </div>
    );
};

export default Navbar;