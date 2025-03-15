package dev.ivank.trchatbotdemo.common;

import java.io.Serializable;

public interface IdAware<ID extends Serializable> {
    ID getId();
}