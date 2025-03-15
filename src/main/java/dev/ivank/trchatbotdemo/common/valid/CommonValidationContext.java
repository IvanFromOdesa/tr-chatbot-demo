package dev.ivank.trchatbotdemo.common.valid;

public class CommonValidationContext {
    protected final ErrorMap errorMap;

    public CommonValidationContext() {
        this.errorMap = new ErrorMap();
    }

    public ErrorMap getErrorMap() {
        return errorMap;
    }
}
