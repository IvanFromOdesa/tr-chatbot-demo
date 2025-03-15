import React from 'react';
import { OverlayTrigger, Tooltip } from 'react-bootstrap';
import { PUBLIC_URL } from '../../common/config.ts';
import { ChatAvatar } from '../types/chat.ts';

interface AvatarOverlayProps {
    avatar: ChatAvatar | null;
    bundle: Record<string, string>;
}

const AvatarOverlay: React.FC<AvatarOverlayProps> = ({ avatar, bundle }) => {
    if (!avatar) {
        return null;
    }

    return (
        <OverlayTrigger
            overlay={<Tooltip>{bundle?.[avatar.name]}</Tooltip>}
        >
            <img
                src={`${PUBLIC_URL}${avatar.path}`}
                alt={`${avatar.type}-${avatar.name}`}
                className="avatar"
            />
        </OverlayTrigger>
    );
};

export default AvatarOverlay;