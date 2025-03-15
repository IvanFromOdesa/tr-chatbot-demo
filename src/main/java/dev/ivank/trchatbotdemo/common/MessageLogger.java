package dev.ivank.trchatbotdemo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MessageLogger {
    public static final Logger LOGGER = LoggerFactory.getLogger(MessageLogger.class);

    public static void info(String msg) {
        LOGGER.info(msg);
    }

    public static void warn(String msg) {
        LOGGER.warn(msg);
    }

    public static void error(String msg) {
        LOGGER.error(msg);
    }

    public static void error(String msg, Throwable ex) {
        LOGGER.error(msg, ex);
    }
}