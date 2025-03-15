import { UiStore } from "../stores/ui.store.ts";
import './Footer.css';
import {htmlFrom} from "../utils/html.parse.ts";
import {BasePath} from "../types/ui.ts";

interface IFooterProps {
    uiStore: UiStore<BasePath>;
}

const Footer = ({ uiStore: { bundle } }: IFooterProps) => {
    return (
        <footer className="footer mt-auto py-3 bg-light">
            <div className="container text-center">
                <span className="me-3">{htmlFrom(bundle?.['footer.title'])}</span>
                <span className="me-3">{htmlFrom(bundle?.['footer.contact'])}</span>
                <span className="me-3">{htmlFrom(bundle?.['footer.linkedIn'])}</span>
                <span>{htmlFrom(bundle?.['footer.github'])}</span>
            </div>
        </footer>
    );
}

export default Footer;