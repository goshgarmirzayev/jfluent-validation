package com.fluentrules.core;

import java.util.Collections;
import java.util.List;

/**
 * Lightweight value object describing the result of an individual validator execution.
 *
 * @author Goshgar Mirzayev
 */
public final class ValidationStatus {
    private static final ValidationStatus VALID = new ValidationStatus(true, List.of());

    private final boolean valid;
    private final List<ValidationError> errors;

    private ValidationStatus(boolean valid, List<ValidationError> errors) {
        this.valid = valid;
        this.errors = errors;
    }

    /**
     * @return cached success status with no errors
     */
    public static ValidationStatus success() {
        return VALID;
    }

    /**
     * Creates a failure status without explicit error details.
     */
    public static ValidationStatus failure() {
        return new ValidationStatus(false, List.of());
    }

    /**
     * Creates a failure status capturing specific errors produced by the validator.
     *
     * @param errors errors generated during validation
     * @return failure status containing the supplied errors
     */
    public static ValidationStatus failure(List<ValidationError> errors) {
        return new ValidationStatus(false, List.copyOf(errors));
    }

    /**
     * @return {@code true} when the validator succeeded
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * @return immutable list of errors associated with the validation attempt
     */
    public List<ValidationError> getErrors() {
        return Collections.unmodifiableList(errors);
    }
}
