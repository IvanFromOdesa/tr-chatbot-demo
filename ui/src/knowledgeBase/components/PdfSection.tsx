import './KnowledgeBaseSection.css';
import {useStoreContext} from "../knowledgeBase.tsx";
import {Button, Card, Col, Container, Row} from "react-bootstrap";
import {useQuery} from "@tanstack/react-query";
import {GET_PDFS_KEY} from "../../common/config/tanstack.query.client.ts";
import {getData} from "../api/kb.ts";
import {useState} from "react";
import PdfList from "./PdfList.tsx";
import {BasePagination} from "../../common/components/BasePagination.tsx";
import PdfUploadModal from "./PdfUploadModal.tsx";
import {PdfModelPage} from "../types/model.ts";
import {getPageData} from "./common.tsx";
import {scrollToTop} from "../../common/utils/navigation.ts";

const PdfSection = () => {
    const knowledgeBaseRootStore = useStoreContext();
    const { uiStore: {
        bundle,
        pathNavigation,
        assets,
        locale},
        authStore,
        maxUploadFileSize
    } = knowledgeBaseRootStore;

    const [activePage, setActivePage] = useState<number>(0);

    const [showModal, setShowModal] = useState(false);
    const handleClose = () => {
        setShowModal(false);
        scrollToTop();
    }

    const {data} = useQuery({
        queryKey: [GET_PDFS_KEY, {activePage}],
        queryFn: () => getData<PdfModelPage>(pathNavigation?.knowledgeBaseGetPdfs, activePage),
        enabled: !!pathNavigation?.knowledgeBaseGetPdfs,
    });

    return (
        <Container className="admin-section">
            <Row>
                <Col md={12}>
                    <Card className="admin-card">
                        <Card.Body>
                            <Card.Title className="admin-title">
                                {bundle?.['page.section.pdfs']}
                            </Card.Title>
                            <Card.Text className="admin-desc">
                                {bundle?.['page.view.pdf.desc']}
                            </Card.Text>
                            <Button
                                className="main-button mb-4"
                                onClick={() => setShowModal(true)}
                            >
                                {bundle?.['page.view.pdf.upload']}
                            </Button>
                            <PdfUploadModal
                                show={showModal}
                                handleClose={handleClose}
                                maxUploadFileSize={maxUploadFileSize}
                                bundle={bundle}
                                uploadUrl={pathNavigation?.knowledgeBaseUploadPdf}
                                setUploadStarted={(value: boolean) => authStore.ctxUploaded = value}
                            />
                            {
                                data && <>
                                    {getPageData<PdfModelPage>(
                                        data,
                                        bundle,
                                        assets,
                                        bundle?.['page.view.pdf.pagination']
                                    )}
                                    <BasePagination
                                        activePage={activePage}
                                        pageSetter={(n) => setActivePage(n)}
                                        pagination={data}
                                        render={() => <PdfList
                                            pdfList={data?.content || []}
                                            bundle={bundle}
                                            previewUrl={pathNavigation?.knowledgeBasePdfPreview}
                                            downloadUrl={pathNavigation?.knowledgeBasePdfDownload}
                                            userLocale={locale || "en-US"}
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

export default PdfSection;