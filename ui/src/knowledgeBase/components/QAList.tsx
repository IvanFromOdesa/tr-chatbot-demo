import {
    faLanguage
} from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {QAModel} from "../types/model.ts";
import {Bundle, LanguageCode} from "../../common/types/ui.ts";
import {Accordion, Button, Col, OverlayTrigger, Row, Tooltip} from "react-bootstrap";
import {formatDateTime} from "../../common/utils/format.ts";
import {useState} from "react";
import {getUserOverlay} from "./common.tsx";
import {scrollTo} from "../../common/utils/navigation.ts";
import {AccordionEventKey} from "react-bootstrap/AccordionContext";
import LanguageIcon from "../../common/components/LanguageIcon.tsx";

interface QAListProps {
    qaList: QAModel[];
    bundle: Bundle;
    userLocale: LanguageCode | null;
}

const QAList = ({ qaList, bundle, userLocale }: QAListProps) => {
    const [activeKey, setActiveKey] = useState<string | null>("0");

    const handleScrollAndOpen = (targetId: string) => {
        setActiveKey(targetId);
        setTimeout(() => {
            scrollTo(targetId);
        }, 100);
    };

    const handleSelect = (eventKey: AccordionEventKey) => {
        if (eventKey !== undefined) {
            setActiveKey(eventKey as string);
        } else {
            setActiveKey(null);
        }
    };

    return (
        <Accordion activeKey={activeKey} onSelect={handleSelect}>
            {qaList.map((qa) => {
                const currentId = `qa${qa.dbId}`;
                const uploadedBy = qa.uploadedBy;

                return (
                    <Accordion.Item
                        eventKey={currentId}
                        key={qa.dbId}
                        id={currentId}
                    >
                        <Accordion.Header>
                            <Row>
                                <Col xs="auto">
                                    <strong>{bundle["page.view.qa.q"]}</strong>&nbsp;{qa.q}&nbsp;
                                    <LanguageIcon
                                        language={qa.language}
                                        className="me-2"
                                        style={{verticalAlign: 'baseline'}}
                                    />
                                    {qa.translateStatus === 1 ? (
                                        <FontAwesomeIcon icon={faLanguage} style={{color: "#1294be"}}/>
                                    ) : null}
                                </Col>
                            </Row>
                        </Accordion.Header>
                        <Accordion.Body>
                            <p>
                                <strong>{bundle["page.view.qa.a"]}</strong> {qa.a}
                            </p>
                            <p>
                                <strong>{bundle["page.view.qa.ts"]}</strong>&nbsp;
                                {qa.translateStatus === 1
                                    ? <OverlayTrigger overlay={
                                        <Tooltip>
                                            {
                                                bundle?.['page.view.qa.ts.tip']
                                            }
                                        </Tooltip>
                                    }>
                                        <a
                                            href={`#${currentId}`}
                                            className="base-link"
                                            onClick={(e) => {
                                                e.preventDefault();
                                                handleScrollAndOpen(`qa${qa.originalQdbId}`);
                                            }}
                                        >
                                            {bundle?.["page.view.qa.ts.tr"]}
                                        </a>
                                    </OverlayTrigger>
                                    : bundle?.["page.view.qa.ts.og"]}
                            </p>
                            <p>
                                <strong>{bundle["page.view.qa.language"]}</strong> {qa.language.name}
                            </p>
                            <p>
                                <strong>{bundle["page.view.uploadedAt"]}</strong>&nbsp;
                                {formatDateTime(qa.uploadedAt, userLocale || "en-US")}
                            </p>
                            <p>
                                <strong>{bundle["page.view.uploadedBy"]}</strong>&nbsp;
                                {uploadedBy.firstName + ' ' + uploadedBy.lastName}&nbsp;
                                {getUserOverlay(uploadedBy)}
                            </p>
                            <p>
                                <Button
                                    size="sm"
                                    variant="primary"
                                    className="me-2"
                                >
                                    {bundle?.['action.edit']}
                                </Button>
                                <Button
                                    size="sm"
                                    variant="danger"
                                >
                                    {bundle?.['action.delete']}
                                </Button>
                            </p>
                        </Accordion.Body>
                    </Accordion.Item>
                );
            })}
        </Accordion>
    );
};

export default QAList;