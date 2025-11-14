package com.fluentrules.validators;

import com.fluentrules.context.ValidationContext;
import com.fluentrules.core.PropertyValidator;
import com.fluentrules.core.ValidationStatus;

/**
 * Convenience base class for property validators that operate via a boolean validity check. Subclasses implement the
 * {@link #isValid(Object, ValidationContext, String)} hook and receive automatic handling of success/failure status.
 *
 * @param <T> type of the root object being validated
 * @param <TProperty> property type validated by the subclass
 * @author Goshgar Mirzayev
 */
public abstract class AbstractPropertyValidator<T, TProperty> implements PropertyValidator<T, TProperty> {
    private final String message;

    /**
     * @param message default failure message presented when validation does not succeed
     */
    protected AbstractPropertyValidator(String message) {
        this.message = message;
    }

    @Override
    public String getDefaultMessage() {
        return message;
    }

    @Override
    public ValidationStatus validate(TProperty value, ValidationContext<T> context) {
        if (isValid(value, context, context.getPropertyPath())) {
            return ValidationStatus.success();
        }
        return ValidationStatus.failure();
    }

    /**
     * Performs the actual validation logic.
     *
     * @param value property value to check
     * @param context validation context providing access to the root instance
     * @param propertyPath fully qualified property path
     * @return {@code true} if the value is considered valid
     */
    protected abstract boolean isValid(TProperty value, ValidationContext<T> context, String propertyPath);
}
