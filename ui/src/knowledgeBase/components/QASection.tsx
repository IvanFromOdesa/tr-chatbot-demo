import './KnowledgeBaseSection.css';
import {Button, Card, Col, Container, Row} from "react-bootstrap";
import {useStoreContext} from "../knowledgeBase.tsx";
import {useQuery} from "@tanstack/react-query";
import {GET_QAS_KEY} from "../../common/config/tanstack.query.client.ts";
import {getData} from "../api/kb.ts";
import {QAModelPage} from "../types/model.ts";
import {useState} from "react";
import {getPageData} from "./common.tsx";
import {BasePagination} from "../../common/components/BasePagination.tsx";
import QAList from "./QAList.tsx";
import QAUploadModal from "./QAUploadModal.tsx";
import {scrollToTop} from "../../common/utils/navigation.ts";

const QASection = () => {
    const knowledgeBaseRootStore = useStoreContext();
    const { uiStore: {
        bundle,
        pathNavigation,
        assets,
        locale},
        authStore
    } = knowledgeBaseRootStore;

    const [activePage, setActivePage] = useState<number>(0);

    const [showModal, setShowModal] = useState(false);
    const handleClose = () => {
        setShowModal(false);
        scrollToTop();
    }

    const {data} = useQuery({
        queryKey: [GET_QAS_KEY, {activePage}],
        queryFn: () => getData<QAModelPage>(pathNavigation?.knowledgeBaseGetQAs, activePage),
        enabled: !!pathNavigation?.knowledgeBaseGetQAs,
    });

    return (
        <Container className="admin-section">
            <Row>
                <Col md={12}>
                    <Card className="admin-card">
                        <Card.Body>
                            <Card.Title className="admin-title">
                                {bundle?.['page.section.qa']}
                            </Card.Title>
                            <Card.Text className="admin-desc">
                                {bundle?.['page.view.qa.desc']}
                            </Card.Text>
                            <Button
                                className="main-button mb-4"
                                onClick={() => setShowModal(true)}
                            >
                                {bundle?.['page.view.qa.add']}
                            </Button>
                            <QAUploadModal
                                show={showModal}
                                handleClose={handleClose}
                                bundle={bundle}
                                qaValidation={knowledgeBaseRootStore.qaValidation}
                                pathNavigation={pathNavigation}
                                setUploadStarted={value => authStore.ctxUploaded = value}
                            />
                            {
                                data && <>
                                    {getPageData(
                                        data,
                                        bundle,
                                        assets,
                                        bundle?.['page.view.qa.pagination']
                                    )}
                                    <BasePagination
                                        activePage={activePage}
                                        pageSetter={(n) => setActivePage(n)}
                                        pagination={data}
                                        render={() => <QAList
                                            qaList={data.content}
                                            bundle={bundle}
                                            userLocale={locale}
                                        />}
                                    />
                                </>
                            }
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    )
}

export default QASection;