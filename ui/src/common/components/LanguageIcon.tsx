import {Language} from "../types/ui.ts";
import React from "react";
import {PUBLIC_URL} from "../config.ts";

interface LanguageIconProps {
    language: Language;
    className?: string;
    width?: number;
    style?: React.CSSProperties;
}

const LanguageIcon: React.FC<LanguageIconProps> = ({ language, className = "", width = 16, style }) => {
    return (
        <img
            src={`${PUBLIC_URL}${language.imagePath}`}
            alt={language.name}
            className={className}
            width={width}
            height="auto"
            style={{...style }}
        />
    );
};

export default LanguageIcon;