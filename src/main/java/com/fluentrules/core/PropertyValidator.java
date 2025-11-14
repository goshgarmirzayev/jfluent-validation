package com.fluentrules.core;

import com.fluentrules.context.ValidationContext;

/**
 * Defines the contract for validators that operate on individual properties.
 *
 * @param <T> type of the root object being validated
 * @param <TProperty> property value type
 * @author Goshgar Mirzayev
 */
public interface PropertyValidator<T, TProperty> {

    /**
     * Performs validation for the provided value.
     *
     * @param value property value to validate
     * @param context validation context providing scope information
     * @return validation status describing the outcome
     */
    ValidationStatus validate(TProperty value, ValidationContext<T> context);

    /**
     * @return default validation message used when no custom message is supplied
     */
    String getDefaultMessage();
}
