import React from 'react';
import { Spinner } from 'react-bootstrap';

const Loading: React.FC = () => {
    const styles: {container: React.CSSProperties; spinner: React.CSSProperties} = {
        container: {
            position: 'fixed',
            top: 0,
            left: 0,
            width: '100%',
            height: '100%',
            backgroundColor: 'rgba(0, 0, 0, 0.2)',
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            zIndex: 1050,
        },
        spinner: {
            color: '#1294be',
            width: '3rem',
            height: '3rem',
        },
    };

    return (
        <div style={styles.container}>
            <Spinner animation="border" role="status" style={styles.spinner}>
                <span className="visually-hidden">Loading...</span>
            </Spinner>
        </div>
    );
};

export default Loading;