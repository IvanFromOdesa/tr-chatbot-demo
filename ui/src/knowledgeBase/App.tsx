import 'bootstrap/dist/css/bootstrap.min.css';
import '../main.css';
import '@fortawesome/fontawesome-svg-core/styles.css';
import {lazy} from "react";
import {observer} from "mobx-react";
import {useStoreContext} from "./knowledgeBase.tsx";
import Loading from "../common/components/Loading.tsx";
import {UiStore} from "../common/stores/ui.store.ts";
import {AllPaths} from "../common/types/ui.ts";
import PdfSection from "./components/PdfSection.tsx";
import QASection from "./components/QASection.tsx";
import {Accordion, AccordionItem} from "../common/components/accordion.tsx";
import {Container} from "react-bootstrap";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faDatabase} from "@fortawesome/free-solid-svg-icons";

const Navbar = lazy(() => import("../common/components/Navbar.tsx"));
const Footer = lazy(() => import("../common/components/Footer.tsx"));
const NotificationAlert = lazy(() => import("../common/components/NotificationAlert.tsx"));

function App() {
    const knowledgeBaseRootStore = useStoreContext();
    const { uiStore, authStore } = knowledgeBaseRootStore;

    const bundle = uiStore.bundle;
    return (
        <>
            {
                knowledgeBaseRootStore.loading ?
                    <Loading /> :
                    <>
                        <Navbar
                            uiStore={uiStore as UiStore<AllPaths>}
                            authStore={authStore}
                        />
                        {
                            authStore.ctxUploaded ?
                                <NotificationAlert
                                    message={bundle?.['notification.success.upload']}
                                    variant={'success'}
                                    onClose={(value: boolean) => authStore.ctxUploaded = value}
                                /> : null
                        }
                        <Container className="base-container">
                            <h1 className="mb-3">
                                {bundle?.['page.title']}&nbsp;
                                <FontAwesomeIcon icon={faDatabase} style={{color: '#1294be'}}/>
                            </h1>
                            <Accordion>
                                <AccordionItem title={bundle?.['page.view.pdf.title']}>
                                    <PdfSection />
                                </AccordionItem>
                                <AccordionItem title={bundle?.['page.view.qa.title']}>
                                    <QASection />
                                </AccordionItem>
                            </Accordion>
                            <p className="mt-3">{bundle?.['page.kbm.desc']}</p>
                        </Container>
                        <Footer uiStore={uiStore}/>
                    </>
            }
        </>
    )
}

export default observer(App);