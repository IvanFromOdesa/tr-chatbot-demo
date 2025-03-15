import {StrictMode} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {createRoot} from "react-dom/client";

function App() {
    const navigateTo = (app: string) => {
        const appSelector = document.getElementById('app-selector');
        const loading = document.getElementById('loading');
        if (appSelector && loading) {
            appSelector.style.display = 'none';
            loading.style.display = 'block';
        }

        window.location.href = `/public/bundles/entry/${app}.html`;
    };

    return (
        <div className="d-flex flex-column justify-content-center align-items-center vh-100">
            <h1 className="text-center mb-4">TR Chatbot UI Dev Server</h1>
            <div id="app-selector" className="d-flex flex-wrap justify-content-center">
                <button className="btn btn-info m-2" onClick={() => navigateTo('login')}>Log In</button>
                <button className="btn btn-danger m-2" onClick={() => navigateTo('signup')}>Sign Up</button>
                <button className="btn btn-primary m-2" onClick={() => navigateTo('homeVisitor')}>Home Visitor</button>
                <button className="btn btn-success m-2" onClick={() => navigateTo('homeAnonymous')}>Home Anonymous</button>
                <button className="btn btn-warning m-2" onClick={() => navigateTo('homeEmployee')}>Home Employee</button>
                <button className="btn btn-dark m-2" onClick={() => navigateTo('knowledgeBase')}>Knowledge Base</button>
                <button className="btn btn-light m-2" onClick={() => navigateTo('homeEmployee')}>Report</button>
            </div>
            <div id="loading" style={{ display: 'none' }}>Loading...</div>
        </div>
    );
}

createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <App/>
    </StrictMode>
);
