import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faEye, faEyeSlash} from "@fortawesome/free-solid-svg-icons";
import React from "react";

interface IPasswordToggleIconProps {
    showPassword: boolean;
    setShowPassword: (v: boolean) => void;
    rightPadding?: number
}

const PasswordToggleIcon: React.FC<IPasswordToggleIconProps> = ({showPassword,
                                                                    setShowPassword,
                                                                    rightPadding }) => {
    const styles: {passwordToggleIcon: React.CSSProperties} = {
        passwordToggleIcon: {
            position: 'absolute',
            top: '50%',
            right: rightPadding || 15,
            transform: 'translateY(-50%)',
            cursor: 'pointer',
            color: '#adb5bd'
        }
    };
    return (
        <span
            style={styles.passwordToggleIcon}
            onClick={() => setShowPassword(!showPassword)}
            role="button"
        >
            <FontAwesomeIcon icon={showPassword ? faEye : faEyeSlash} />
        </span>
    );
};

export default PasswordToggleIcon;