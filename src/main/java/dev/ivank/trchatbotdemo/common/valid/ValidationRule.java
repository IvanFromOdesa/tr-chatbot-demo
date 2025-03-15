package dev.ivank.trchatbotdemo.common.valid;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @param <T> entity type to validate
 * @param <A> additional argument type in case of validation failure. Must be serializable to transmit over network.
 */
@FunctionalInterface
public interface ValidationRule<T, A> {
    Optional<ValidationRuleViolation<A>> apply(T t);

    static boolean isNotValidRegex(Pattern pattern, String input) {
        return input != null && !input.isBlank() && !pattern.matcher(input).matches();
    }
}