package com.fluentrules.validators;

import com.fluentrules.context.ValidationContext;

public class NullValidator<T, TProperty> extends AbstractPropertyValidator<T, TProperty> {
    public NullValidator() {
        super("Value must be null");
    }

    @Override
    protected boolean isValid(TProperty value, ValidationContext<T> context, String propertyPath) {
        return value == null;
    }
}
