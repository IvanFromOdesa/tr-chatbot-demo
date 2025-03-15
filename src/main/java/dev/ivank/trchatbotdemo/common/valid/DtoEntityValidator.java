package dev.ivank.trchatbotdemo.common.valid;

import java.util.function.Consumer;

@FunctionalInterface
public interface DtoEntityValidator<DTO, CONTEXT extends FormValidationContext<DTO>> {
    ErrorMap validate(CONTEXT context);

    static <D, C extends FormValidationContext<D>> DtoEntityValidator<D, C> withDefaults() {
        return context -> ErrorMap.empty();
    }

    default Consumer<ValidationRuleViolation<String>> putToMap(ErrorMap errorMap) {
        return v -> errorMap.putWithInput(v.getCause(), v.getCode(), v.getArgument());
    }
}
