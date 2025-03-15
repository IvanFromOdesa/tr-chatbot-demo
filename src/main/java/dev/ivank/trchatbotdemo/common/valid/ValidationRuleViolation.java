package dev.ivank.trchatbotdemo.common.valid;

public final class ValidationRuleViolation<A> {
    private final String cause;
    private final String code;
    private final A argument;

    public ValidationRuleViolation(String cause, String code) {
        this.cause = cause;
        this.code = code;
        this.argument = null;
    }

    public ValidationRuleViolation(String cause, String code, A argument) {
        this.cause = cause;
        this.code = code;
        this.argument = argument;
    }

    public String getCause() {
        return cause;
    }

    public String getCode() {
        return code;
    }

    public A getArgument() {
        return argument;
    }
}