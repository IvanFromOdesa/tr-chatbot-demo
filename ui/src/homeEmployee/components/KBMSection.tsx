import '../../common/components/AdminSection.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
    faUpload,
    faSyncAlt,
    faTrash,
} from '@fortawesome/free-solid-svg-icons';
import '@fortawesome/fontawesome-svg-core/styles.css';
import {useStoreContext} from "../homeEmployee.tsx";
import {Button, Card, Col, Container, Row} from "react-bootstrap";

const KBMSection = () => {
    const rootStore = useStoreContext();
    const {uiStore: {bundle, pathNavigation}} = rootStore;

    return (
        <Container className="admin-section">
            <Row>
                <Col md={12}>
                    <Card className="admin-card">
                        <Card.Body>
                            <Card.Title className="admin-title">{bundle?.['page.kdb.title']}</Card.Title>
                            <Card.Text className="admin-desc">{bundle?.['page.kdb.desc']}</Card.Text>
                            <ul className="admin-list">
                                <li>
                                    <h4 className="admin-subtitle">
                                        <FontAwesomeIcon icon={faUpload} className="me-2 admin-icon" style={{color: '#1294be'}}/>&nbsp;
                                        {bundle?.['page.kdb.ai.title']}
                                    </h4>
                                    <p className="admin-subdesc">{bundle?.['page.kdb.ai.desc']}</p>
                                </li>
                                <li>
                                    <h4 className="admin-subtitle">
                                        <FontAwesomeIcon icon={faSyncAlt} className="me-2 admin-icon" style={{color: '#1294be'}}/>&nbsp;
                                        {bundle?.['page.kdb.me.title']}
                                    </h4>
                                    <p className="admin-subdesc">{bundle?.['page.kdb.me.desc']}</p>
                                </li>
                                <li>
                                    <h4 className="admin-subtitle">
                                        <FontAwesomeIcon icon={faTrash} className="me-2 admin-icon" style={{color: '#1294be'}}/>&nbsp;
                                        {bundle?.['page.kdb.rid.title']}
                                    </h4>
                                    <p className="admin-subdesc">{bundle?.['page.kdb.rid.desc']}</p>
                                </li>
                            </ul>
                            <Button variant="primary" href={pathNavigation?.knowledgeBase} className="main-button">
                                {bundle?.['page.kdb.link']}
                            </Button>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    )
}

export default KBMSection;