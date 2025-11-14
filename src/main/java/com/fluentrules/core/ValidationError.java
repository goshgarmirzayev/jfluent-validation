package com.fluentrules.core;

/**
 * Immutable representation of an individual validation error emitted by the engine.
 *
 * <p>Created by Goshgar Mirzayev ({@code jgoshgarmirzayev@gmail.com}) for the JFluent Validation library.</p>
 *
 * @author Goshgar Mirzayev
 */

import java.util.Objects;

public final class ValidationError {
    private final String field;
    private final String message;

    /**
     * Creates a new validation error.
     *
     * @param field the field or property responsible for the error
     * @param message human friendly description of the problem
     */
    public ValidationError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    /**
     * @return name of the field or property responsible for the error
     */
    public String getField() {
        return field;
    }

    /**
     * @return error message describing the validation failure
     */
    public String getMessage() {
        return message;
    }

    @Override
    /**
     * Compares this error with another for structural equality.
     *
     * @param o object to compare with
     * @return {@code true} when both represent the same field and message
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValidationError that)) return false;
        return Objects.equals(field, that.field) && Objects.equals(message, that.message);
    }

    @Override
    /**
     * @return consistent hash based on the field and message
     */
    public int hashCode() {
        return Objects.hash(field, message);
    }

    @Override
    /**
     * @return debug friendly string representation of the error
     */
    public String toString() {
        return "ValidationError{" +
            "field='" + field + '\'' +
            ", message='" + message + '\'' +
            '}';
    }
}
