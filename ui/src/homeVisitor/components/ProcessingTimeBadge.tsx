import React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCircle } from "@fortawesome/free-solid-svg-icons";
import {Bundle} from "../../common/types/ui.ts";
import {OverlayTrigger, Tooltip} from "react-bootstrap";

const ProcessingTimeBadge = ({ time, bundle } : {time: number, bundle: Bundle}) => {
    const styles: {
        container: React.CSSProperties,
        icon: React.CSSProperties,
        text: React.CSSProperties
    } = {
        container: {
            position: "relative",
            display: "inline-block",
            width: "24px",
            height: "24px",
        },
        icon: {
            fontSize: "24px",
            color: "#3498db",
        },
        text: {
            position: "absolute",
            top: "50%",
            left: "50%",
            transform: "translate(-50%, -50%)",
            fontSize: "8px",
            fontWeight: "bold",
            color: "white",
        },
    };

    return (
        <OverlayTrigger overlay={<Tooltip>{bundle?.['page.visitor.timeToProcess']}</Tooltip>}>
            <div style={styles.container}>
                <FontAwesomeIcon icon={faCircle} style={styles.icon}/>
                <span style={styles.text}>{time}</span>
            </div>
        </OverlayTrigger>
    );
};

export default ProcessingTimeBadge;