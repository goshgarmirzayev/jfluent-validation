package com.fluentrules.validators;

import com.fluentrules.context.ValidationContext;
import com.fluentrules.core.PropertyValidator;
import com.fluentrules.core.ValidationStatus;

public abstract class AbstractPropertyValidator<T, TProperty> implements PropertyValidator<T, TProperty> {
    private final String message;

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

    protected abstract boolean isValid(TProperty value, ValidationContext<T> context, String propertyPath);
}
