package dev.ivank.trchatbotdemo.common.event;

public abstract class BaseEventListener<EVENT> {
    public abstract void handleEvent(EVENT event);
}
