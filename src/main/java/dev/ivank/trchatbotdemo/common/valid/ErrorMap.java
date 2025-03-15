package dev.ivank.trchatbotdemo.common.valid;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ErrorMap {
    private final Map<String, Error> errors;

    public ErrorMap() {
        errors = new HashMap<>();
    }

    public void put(String cause, String sub, String msg) {
        Error error = errors.get(cause);
        if (error != null) {
            error.put(sub, msg);
        } else {
            errors.put(cause, new Error(sub, msg));
        }
    }

    public void put(String cause, Map<String, String> subs) {
        Error error = errors.get(cause);
        if (error != null) {
            error.putAll(subs);
        } else {
            errors.put(cause, new Error(subs));
        }
    }

    public void put(String cause, String msg) {
        errors.put(cause, new Error(msg));
    }

    public void putWithInput(String cause, String msg, Serializable input) {
        errors.put(cause, new Error(msg, input));
    }

    public void putAll(Map<String, Error> map) {
        errors.putAll(map);
    }

    public void putAll(ErrorMap map) {
        errors.putAll(map.getErrors());
    }

    public boolean isEmpty() {
        return errors.isEmpty();
    }

    public Map<String, Error> getErrors() {
        return errors;
    }

    public static ErrorMap empty() {
        return new ErrorMap();
    }

    @Override
    public String toString() {
        return errors.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(", ", "{", "}"));
    }

    public static class Error {
        private Map<String, String> errors;
        private final String msg;
        private final Serializable input;

        Error(String sub, String msg) {
            this.errors = new HashMap<>();
            this.errors.put(sub, msg);
            this.msg = null;
            this.input = null;
        }

        Error(Map<String, String> subs) {
            this.errors = new HashMap<>(subs);
            this.msg = null;
            this.input = null;
        }

        Error(String msg, Serializable input) {
            this.msg = msg;
            this.input = input;
        }

        Error(String msg) {
            this.msg = msg;
            this.input = null;
        }

        public void put(String sub, String msg) {
            if (errors == null) {
                errors = new HashMap<>();
            }
            errors.put(sub, msg);
        }

        public void putAll(Map<String, String> subs) {
            if (errors == null) {
                errors = new HashMap<>();
            }
            errors.putAll(subs);
        }

        public Map<String, String> getErrors() {
            return errors;
        }

        public String getMsg() {
            return msg;
        }

        public Serializable getInput() {
            return input;
        }

        // Logging
        @Override
        public String toString() {
            return errors == null ? String.format("Msg: %s, input: %s", msg, input) : formatMap();
        }

        private String formatMap() {
            return errors.keySet().stream()
                    .map(key -> key + "= " + errors.get(key))
                    .collect(Collectors.joining(", ", "{", "}"));
        }
    }
}
