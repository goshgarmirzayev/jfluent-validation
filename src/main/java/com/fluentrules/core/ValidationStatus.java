package com.fluentrules.core;

import java.util.Collections;
import java.util.List;

public final class ValidationStatus {
    private static final ValidationStatus VALID = new ValidationStatus(true, List.of());

    private final boolean valid;
    private final List<ValidationError> errors;

    private ValidationStatus(boolean valid, List<ValidationError> errors) {
        this.valid = valid;
        this.errors = errors;
    }

    public static ValidationStatus success() {
        return VALID;
    }

    public static ValidationStatus failure() {
        return new ValidationStatus(false, List.of());
    }

    public static ValidationStatus failure(List<ValidationError> errors) {
        return new ValidationStatus(false, List.copyOf(errors));
    }

    public boolean isValid() {
        return valid;
    }

    public List<ValidationError> getErrors() {
        return Collections.unmodifiableList(errors);
    }
}
