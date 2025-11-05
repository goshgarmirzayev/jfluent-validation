package com.fluentrules.validators;

import com.fluentrules.context.ValidationContext;

public class NotNullValidator<T, TProperty> extends AbstractPropertyValidator<T, TProperty> {
    public NotNullValidator() {
        super("Value must not be null");
    }

    @Override
    protected boolean isValid(TProperty value, ValidationContext<T> context, String propertyPath) {
        return value != null;
    }
}
