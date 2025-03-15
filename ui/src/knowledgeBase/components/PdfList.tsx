import './PdfList.css';
import {PdfModel} from "../types/model.ts";
import {Bundle, EmployeePath, LanguageCode, PathNavigation} from "../../common/types/ui.ts";
import {Button, Col, OverlayTrigger, Row, Tooltip} from "react-bootstrap";
import {formatDateTime, formatFileSize} from "../../common/utils/format.ts";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCircleCheck, faBan} from "@fortawesome/free-solid-svg-icons";
import {getUserOverlay} from "./common.tsx";

interface PdfListProps {
    pdfList: PdfModel[];
    bundle: Bundle;
    previewUrl: PathNavigation<EmployeePath>["knowledgeBasePdfPreview"] | undefined;
    downloadUrl: PathNavigation<EmployeePath>["knowledgeBasePdfDownload"] | undefined;
    userLocale: LanguageCode;
}

const PdfList = ({pdfList, bundle, previewUrl, downloadUrl, userLocale}: PdfListProps) => {
    return (
        <ul className="admin-list">
            {
                pdfList.map((pdf, idx) => {
                    const uploadedBy = pdf.uploadedBy;
                    return <li key={idx}>
                        <Row>
                            <Col sm={4} md={3} lg={2}>
                                <a href={`${downloadUrl}/${pdf.fileName}`} target="_blank" rel="noopener noreferrer">
                                    <img
                                        src={`${previewUrl}/${pdf.fileName}`}
                                        alt={pdf.originalFileName}
                                        className="pdf-preview"
                                    />
                                </a>
                            </Col>
                            <Col className="d-flex flex-column pt-xs-3">
                                <Row>
                                    <div>
                                        {bundle?.['page.view.pdf.fileName']}&nbsp;
                                        {pdf.fileName}
                                    </div>
                                </Row>
                                <Row>
                                    <div>
                                        {bundle?.['page.view.pdf.ogFileName']}&nbsp;
                                        {pdf.originalFileName}
                                    </div>
                                </Row>
                                <Row>
                                    <div>
                                        {bundle?.['page.view.pdf.size']}&nbsp;
                                        {formatFileSize(pdf.size, bundle)}
                                    </div>
                                </Row>
                                <Row>
                                    <div>
                                        {bundle?.['page.view.uploadedAt']}&nbsp;
                                        {formatDateTime(pdf.uploadedAt, userLocale)}
                                    </div>
                                </Row>
                                <Row>
                                    <div>
                                        {bundle?.['page.view.uploadedBy']}&nbsp;
                                        {uploadedBy.firstName + ' ' + uploadedBy.lastName}&nbsp;
                                        {getUserOverlay(uploadedBy)}
                                    </div>
                                </Row>
                                <Row>
                                    <div>
                                        {bundle?.['page.view.pdf.multilingual']}&nbsp;
                                        <OverlayTrigger overlay={
                                            <Tooltip>
                                                {
                                                    pdf.translatedToSupported ?
                                                        bundle?.['page.view.pdf.multilingual.enabled.tip'] :
                                                        bundle?.['page.view.pdf.multilingual.disabled.tip']
                                                }
                                            </Tooltip>
                                        }>
                                            <FontAwesomeIcon
                                                icon={pdf.translatedToSupported ? faCircleCheck : faBan}
                                                className="me-2"
                                                style={{color: '#1294be'}}
                                            />
                                        </OverlayTrigger>
                                    </div>
                                </Row>
                                <Row className="mt-auto pt-xs-3">
                                    <div>
                                        <Button size="sm" variant="danger">
                                            {bundle?.['action.delete']}
                                        </Button>
                                    </div>
                                </Row>
                            </Col>
                        </Row>
                    </li>
                })
            }
        </ul>
    )
}

export default PdfList;