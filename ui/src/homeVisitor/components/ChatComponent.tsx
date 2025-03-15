import './ChatComponent.css';
import '@fortawesome/fontawesome-svg-core/styles.css';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faPaperPlane, faCheck, faXmark} from '@fortawesome/free-solid-svg-icons';
import {useStoreContext} from "../homeVisitor.tsx";
import {Button, Container, Form, FormControl, InputGroup} from "react-bootstrap";
import React, {useEffect, useRef, useState} from "react";
import {useChat} from "../hooks/chat.ts";
import {AvatarType, ChatAvatar, ChatFeedback, Conversation} from "../types/chat.ts";
import {changeConversationStatus, getChatAvatars, getOngoingConversation} from "../api/chatting.ts";
import AvatarOverlay from "./AvatarOverlay.tsx";
import ChatLoading from "./ChatLoading.tsx";
import LanguageIcon from "../../common/components/LanguageIcon.tsx";
import {formatDateTime, formatSeconds} from "../../common/utils/format.ts";
import ProcessingTimeBadge from "./ProcessingTimeBadge.tsx";

const ChatComponent = () => {
    const {uiStore: {bundle, pathNavigation, locale}, authStore} = useStoreContext();

    const [userInput, setUserInput] = useState("");
    const [conversation, setConversation] = useState<Conversation | null>(null);

    const { chatInteractions, sendMessage, isLoading } = useChat(pathNavigation?.chat, 3);

    const [visitorAvatar, setVisitorAvatar] = useState<ChatAvatar | null>(null);
    const [botAvatar, setBotAvatar] = useState<ChatAvatar | null>(null);
    const latestBotMessageRef = useRef<HTMLDivElement | null>(null);
    const [messageHoveredId, setMessageHoveredId] = useState<string | null>(null);

    const [feedbackSent, setFeedbackSent] = useState<boolean>(false);

    useEffect(() => {
        getOngoingConversation(pathNavigation?.currentConversation)
            .then(res => {
                if (res) {
                    setConversation(res);
                    const timeoutId = setTimeout(scrollToTheLatestChatInteraction, 500);

                    return () => clearTimeout(timeoutId);
                }
            });
        getChatAvatars(pathNavigation?.avatarIcons)
            .then(res => {
                if (res) {
                    setBotAvatar(getRandomAvatar(res, 'Bot'));
                    setVisitorAvatar(getRandomAvatar(res, 'User'));
                }
            });
    }, []);

    const scrollToTheLatestChatInteraction = () => {
        if (latestBotMessageRef.current) {
            if ("scrollIntoView" in latestBotMessageRef.current) {
                latestBotMessageRef.current.scrollIntoView({behavior: "smooth"});
            }
        }
    }

    useEffect(() => {
        setConversation((prev) => {
            const updatedInteractions = prev?.chatInteractions
                ? prev.chatInteractions.filter(
                    (interaction) => !chatInteractions.some(
                        newInteraction => newInteraction.id === interaction.id
                    )
                )
                : [];

            return {
                startedAt: prev?.startedAt ?? null,
                status: prev?.status ?? 'not_yet_completed',
                chatInteractions: [...updatedInteractions, ...chatInteractions],
            };
        });
        scrollToTheLatestChatInteraction();
    }, [chatInteractions]);

    const getRandomAvatar = (res: ChatAvatar[], type: AvatarType) => {
        const typeAvatars = res.filter(a => a.type === type);
        return typeAvatars[Math.floor(Math.random() * typeAvatars.length)]
    }

    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setUserInput(event.target.value);
    };

    const handleSendMessage = (e: React.FormEvent) => {
        e.preventDefault();
        setFeedbackSent(false);
        sendMessage(userInput);
        setUserInput("");
    };

    const handeSendFeedback = (res: ChatFeedback) => {
        setFeedbackSent(true);
        changeConversationStatus(pathNavigation?.chatFeedback, res)
            .then(res => {
                if (res) {
                    setConversation((prev) =>
                        prev ? {
                            ...prev,
                            status: res
                        } : null
                    );
                }
            });
    }

    const conversationCIs = conversation?.chatInteractions;

    return (
        <Container className="base-container container-xl">
            {
                conversationCIs?.length == 0 && !isLoading && <div className="chat-content">
                    <p className="chat-greeting">
                        {bundle?.["page.visitor.greeting"].replace("{0}", authStore.getUserName())}
                    </p>
                    <p className="chat-tip">
                        {bundle?.["page.visitor.tip"]}
                    </p>
                </div>
            }
            <div id="chat-messages" className="chat-messages-container">
                {conversationCIs?.map((m) => (
                    <div key={m.id} className="mb-3 message-container">
                        <div className="user-message-wrapper">
                            <div
                                className="user-message"
                                id={`user-message-${m.id}`}
                                onMouseEnter={() => setMessageHoveredId(`user-message-${m.id}`)}
                                onMouseLeave={() => setMessageHoveredId(null)}
                            >
                                {m.question}
                                {
                                    messageHoveredId === `user-message-${m.id}` &&
                                    <div className="chat-message-md">
                                        {bundle?.['page.visitor.askedAt']}&nbsp;
                                        {formatDateTime(m.askedAt, locale || "en-US")}
                                    </div>
                                }
                            </div>
                            <AvatarOverlay avatar={visitorAvatar} bundle={bundle} />
                        </div>
                        <div
                            className="bot-message-wrapper"
                            ref={conversationCIs?.indexOf(m) === conversationCIs?.length - 1 ? latestBotMessageRef : null}
                        >
                            <AvatarOverlay avatar={botAvatar} bundle={bundle} />
                            <div
                                className="bot-message"
                                id={`bot-answer-${m.id}`}
                                onMouseEnter={() => setMessageHoveredId(`bot-answer-${m.id}`)}
                                onMouseLeave={() => setMessageHoveredId(null)}
                            >
                                {m.answer}
                                {
                                    messageHoveredId === `bot-answer-${m.id}` &&
                                    <div className="chat-message-md">
                                        <LanguageIcon
                                            language={m.questionLanguage}
                                            width={14}
                                            style={{verticalAlign: 'baseline'}}
                                        />&nbsp;
                                        {bundle?.['page.visitor.answeredAt']}&nbsp;
                                        {formatDateTime(
                                            m.askedAt + m.timeToProcess,
                                            locale || "en-US")
                                        }
                                        <br/>
                                        <ProcessingTimeBadge
                                            time={formatSeconds(m.timeToProcess)}
                                            bundle={bundle}
                                        />
                                    </div>
                                }
                            </div>
                        </div>
                    </div>
                ))}
                {
                    isLoading && <div className="mb-3"><ChatLoading /></div>
                }
                {
                    (conversationCIs?.length !== 0  && !isLoading && !feedbackSent && conversation?.status !== 'completed_explicitly') &&
                    <div className="message-feedback-wrapper">
                        <div className="message-feedback mb-3">
                            <h6>{bundle?.['page.visitor.feedback']}</h6>
                            <div className="message-feedback-btns mt-2">
                                <Button
                                    variant="success"
                                    type="submit"
                                    size="sm"
                                    disabled={feedbackSent}
                                    onClick={() => handeSendFeedback(0)}
                                >
                                    <FontAwesomeIcon icon={faCheck}/>&nbsp;
                                    {bundle?.['page.visitor.feedback.positive']}
                                </Button>
                                <Button
                                    variant="danger"
                                    type="submit"
                                    size="sm"
                                    disabled={feedbackSent}
                                    onClick={() => handeSendFeedback(1)}
                                >
                                    <FontAwesomeIcon icon={faXmark}/>&nbsp;
                                    {bundle?.['page.visitor.feedback.negative']}
                                </Button>
                            </div>
                        </div>
                    </div>
                }
            </div>
            <Form onSubmit={handleSendMessage} className="chat-form">
                <InputGroup>
                    <FormControl
                        type="text"
                        placeholder={bundle?.["page.visitor.type"]}
                        value={userInput}
                        onChange={handleInputChange}
                        className="chat-input"
                    />
                    <Button
                        variant="primary"
                        disabled={!userInput}
                        type="submit"
                        className="chat-send-button"
                    >
                        <FontAwesomeIcon icon={faPaperPlane}/>
                    </Button>
                </InputGroup>
            </Form>
        </Container>
    )
}

export default ChatComponent;