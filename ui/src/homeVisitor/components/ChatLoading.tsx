import React from "react";
import "./ChatTypingAnimation.css";

const ChatTypingAnimation: React.FC = () => {
    return (
        <div className="chat-typing-container">
            <span className="dot"></span>
            <span className="dot"></span>
            <span className="dot"></span>
        </div>
    );
};

export default ChatTypingAnimation;