package dev.ivank.trchatbotdemo.common.valid;

public class BaseErrorMapException extends RuntimeException {
    private ErrorMap errorMap;

    public BaseErrorMapException(String message) {
        super(message);
    }

    public BaseErrorMapException(ErrorMap errorMap) {
        super(errorMap.getErrors().toString());
        this.errorMap = errorMap;
    }

    public ErrorMap getErrorMap() {
        return errorMap;
    }
}
