package com.fluentrules.core;

/**
 * Contract for validators that can inspect an instance and produce a {@link ValidationResult}.
 *
 * <p>Part of the JFluent Validation library authored by Goshgar Mirzayev ({@code jgoshgarmirzayev@gmail.com}).</p>
 *
 * @param <T> type of instance that will be validated
 * @author Goshgar Mirzayev
 */
public interface Validator<T> {

    /**
     * Validates the provided instance and returns the outcome.
     *
     * @param instance object to validate
     * @return result containing validation status and errors
     */
    ValidationResult validate(T instance);
}
