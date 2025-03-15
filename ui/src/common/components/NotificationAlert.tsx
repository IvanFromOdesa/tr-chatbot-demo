import './NotificationAlert.css';
import {Alert} from "react-bootstrap";
import {Variant} from "react-bootstrap/types";

interface NotificationAlertProps {
    message: string | undefined;
    variant: Variant;
    onClose?: (b: boolean) => void;
}

const NotificationAlert = ({message, variant, onClose}: NotificationAlertProps) => {
    return (
        <Alert
            variant={variant}
            dismissible
            onClose={() => onClose && onClose(false)}
            className="notification-alert"
            id={`nt-alert-${variant}`}
        >
            <p>
                {message}
            </p>
        </Alert>
    )
}

export default NotificationAlert;