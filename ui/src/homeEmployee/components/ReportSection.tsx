import '../../common/components/AdminSection.css';
import {
    faEye,
    faDownload,
} from '@fortawesome/free-solid-svg-icons';
import '@fortawesome/fontawesome-svg-core/styles.css';
import {useStoreContext} from "../homeEmployee.tsx";
import {Button, Card, Col, Container, Row} from "react-bootstrap";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

const ReportSection = () => {
    const rootStore = useStoreContext();
    const {uiStore: {bundle, pathNavigation}} = rootStore;

    return (
        <Container className="admin-section">
            <Row>
                <Col md={12}>
                    <Card className="admin-card">
                        <Card.Body>
                            <Card.Title
                                className="admin-title">{bundle?.['page.rs.title']}</Card.Title>
                            <Card.Text className="admin-desc">{bundle?.['page.rs.desc']}</Card.Text>
                            <ul className="admin-list">
                                <li>
                                    <h4 className="admin-subtitle">
                                        <FontAwesomeIcon icon={faEye} className="me-2 admin-icon" style={{color: '#1294be'}}/>&nbsp;
                                        {bundle?.['page.rs.vcs.title']}
                                    </h4>
                                    <p className="admin-subdesc">{bundle?.['page.rs.vcs.desc']}</p>
                                </li>
                                <li>
                                    <h4 className="admin-subtitle">
                                        <FontAwesomeIcon icon={faDownload} className="me-2 admin-icon" style={{color: '#1294be'}}/>&nbsp;
                                        {bundle?.['page.rs.dr.title']}
                                    </h4>
                                    <p className="admin-subdesc">{bundle?.['page.rs.dr.desc']}</p>
                                </li>
                            </ul>
                            <Button variant="primary" href={pathNavigation?.report}
                                    className="main-button">
                                {bundle?.['page.rs.link']}
                            </Button>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    )
}

export default ReportSection;