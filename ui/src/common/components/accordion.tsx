import {useState, useRef, useEffect, ReactNode} from 'react';
import { Card, Button, Collapse } from 'react-bootstrap';

interface AccordionItemProps {
    title: string | undefined;
    children: ReactNode;
    initialOpen?: boolean;
}

interface AccordionProps {
    children: ReactNode;
}

const AccordionItem = ({ title, children, initialOpen = false }: AccordionItemProps) => {
    const [open, setOpen] = useState(initialOpen);
    const contentRef = useRef(null);

    useEffect(() => {
        if (initialOpen) {
            setOpen(true);
        }
    }, [initialOpen]);

    return (
        <Card>
            <Card.Header>
                <Button
                    variant="link"
                    onClick={() => setOpen(!open)}
                    aria-controls="collapse-text"
                    aria-expanded={open}
                    className="base-link"
                >
                    {title}
                </Button>
            </Card.Header>
            <Collapse in={open} ref={contentRef}>
                <Card.Body>{children}</Card.Body>
            </Collapse>
        </Card>
    );
};

const Accordion = ({ children }: AccordionProps) => {
    return <>{children}</>;
};

export { Accordion, AccordionItem };