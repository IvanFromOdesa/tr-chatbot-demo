import './AdminGreeting.css';
import {Col, Container, Row} from "react-bootstrap";
import {useStoreContext} from "../homeEmployee.tsx";

const EmployeeGreeting = () => {
    const rootStore = useStoreContext();
    const { uiStore, authStore} = rootStore;

    const formattedGreeting = uiStore
        .bundle?.['page.greeting']
        ?.replace('{0}', `${authStore.getUserName()}`);

    return (
        <Container className="admin-greeting-container">
            <Row>
                <Col>
                    <h1 className="admin-greeting">👋 {formattedGreeting}</h1>
                </Col>
            </Row>
        </Container>
    )
}

export default EmployeeGreeting;