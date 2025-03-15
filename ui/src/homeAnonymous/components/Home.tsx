import './Home.css';
import {Button, Card, Col, Container, Row} from 'react-bootstrap';
import {useStoreContext} from "../homeAnonymous.tsx";
import {htmlFrom} from "../../common/utils/html.parse.ts";
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {
    faChartLine,
    faCheckCircle,
    faClock,
    faLanguage,
    faLightbulb,
    faQuestionCircle,
    faSearch,
    faSyncAlt,
    faUpload,
    faUser
} from '@fortawesome/free-solid-svg-icons';
import '@fortawesome/fontawesome-svg-core/styles.css';

const Home = () => {
    const {uiStore: {bundle, pathNavigation}} = useStoreContext();

    const benefitIcons = {
        ia: faLightbulb,        // Instant Answers
        sa: faUser,             // Simplified Access
        ck: faSearch,           // Comprehensive Knowledge
        ip: faChartLine,        // Improved Productivity
        av: faClock,            // 24/7 Availability
        cr: faCheckCircle,      // Consistent Responses
        ms: faLanguage          // Multilingual Support
    };

    const kbmIcons = {
        ud: faUpload,           // Upload Documents
        qa: faQuestionCircle,   // Add Q&A Pairs
        ci: faSyncAlt           // Continuous Improvement
    };

    type BenefitKey = "ia" | "sa" | "ck" | "ip" | "av" | "cr" | "ms";
    type KBMKey = "ud" | "qa" | "ci";

    const renderBenefit = (key: BenefitKey) => (
        <Col lg={4} sm={6} key={key} className="mb-4">
            <Card className="h-100 benefit-card benefit-card-animated">
                <Card.Body>
                    <Card.Title>
                        <FontAwesomeIcon icon={benefitIcons[key]} className="benefit-icon me-2"/>&nbsp;
                        {bundle?.[`page.howItWorks.${key}.title`]}
                    </Card.Title>
                    <Card.Text>{bundle?.[`page.howItWorks.${key}.desc`]}</Card.Text>
                </Card.Body>
            </Card>
        </Col>
    );

    const renderKBMItem = (key: KBMKey) => (
        <div key={key} className="mb-3">
            <h5>
                <FontAwesomeIcon icon={kbmIcons[key]} className="kbm-icon me-2"/>&nbsp;
                {bundle?.[`page.kbm.${key}.title`]}
            </h5>
            <p>{bundle?.[`page.kbm.${key}.desc`]}</p>
        </div>
    );

    const login = pathNavigation?.login;

    return (
        <Container className="home-container">
            <Row className="v-centered">
                <Col md={12} className="text-center mb-5">
                    <h1 className="text-center-full">{htmlFrom(bundle?.['page.bep.title'])}</h1>
                    <p className="lead">{bundle?.['page.bep.desc']}</p>
                    {
                        <Button
                            variant="primary"
                            className="main-button"
                            onClick={() => {
                                if (login) {
                                    window.location.href = login
                                }
                            }}
                        >
                            {bundle?.['page.btn.cta']}
                        </Button>
                    }
                </Col>
            </Row>

            <Row className="mb-5 how-it-works-section">
                <Col md={12} className="text-center mb-3">
                    <h2>{bundle?.['page.howItWorks.title']}</h2>
                </Col>
                <Col md={4}>
                    <div className="how-it-works-step h-100">
                        <h3>{bundle?.['page.howToUse.as.title']}</h3>
                        <p>{bundle?.['page.howToUse.as.desc']}</p>
                    </div>
                </Col>
                <Col md={4}>
                    <div className="how-it-works-step h-100">
                        <h3>{bundle?.['page.howToUse.se.title']}</h3>
                        <p>{bundle?.['page.howToUse.se.desc']}</p>
                    </div>
                </Col>
                <Col md={4}>
                    <div className="how-it-works-step h-100">
                        <h3>{bundle?.['page.howToUse.an.title']}</h3>
                        <p>{bundle?.['page.howToUse.an.desc']}</p>
                    </div>
                </Col>
            </Row>

            <Row className="mb-5">
                <Col md={12} className="text-center mb-3">
                    <h2>{bundle?.['page.kb.title']}</h2>
                </Col>
                {["ia", "sa", "ck", "ip", "av", "cr", "ms"].map((key) => renderBenefit(key as BenefitKey))}
            </Row>

            <Row className="kbm-section mb-5">
                <Col md={12} className="text-center">
                    <h2>{bundle?.['page.kbm.title']}</h2>
                    <p>{bundle?.['page.kbm.desc']}</p>
                </Col>
                <Col md={12}>
                    {renderKBMItem("ud")}
                    {renderKBMItem("qa")}
                    {renderKBMItem("ci")}
                </Col>
            </Row>
        </Container>
    );
};

export default Home;