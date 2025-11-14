package com.fluentrules.core;

/**
 * Represents the outcome of a validation operation. Encapsulates discovered {@link ValidationError}s and exposes
 * convenience methods to determine if an object passed validation.
 *
 * <p>Crafted for the JFluent Validation project by Goshgar Mirzayev ({@code jgoshgarmirzayev@gmail.com}).</p>
 *
 * @author Goshgar Mirzayev
 */

import java.util.Collections;
import java.util.List;

public final class ValidationResult {
    private final List<ValidationError> errors;

    /**
     * Creates a validation result with the given errors.
     *
     * @param errors collection of errors discovered during validation
     */
    public ValidationResult(List<ValidationError> errors) {
        this.errors = List.copyOf(errors);
    }

    /**
     * Indicates whether the validation was successful.
     *
     * @return {@code true} when no errors were captured; otherwise {@code false}
     */
    public boolean isValid() {
        return errors.isEmpty();
    }

    /**
     * Returns an immutable view of the validation errors.
     *
     * @return unmodifiable list of {@link ValidationError} instances
     */
    public List<ValidationError> getErrors() {
        return Collections.unmodifiableList(errors);
    }
}
