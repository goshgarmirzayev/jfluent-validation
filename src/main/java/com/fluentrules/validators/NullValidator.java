package com.fluentrules.validators;

import com.fluentrules.context.ValidationContext;

/**
 * Ensures a property is {@code null}.
 *
 * @param <T> type of the root object being validated
 * @param <TProperty> property type
 * @author Goshgar Mirzayev
 */
public class NullValidator<T, TProperty> extends AbstractPropertyValidator<T, TProperty> {
    public NullValidator() {
        super("Value must be null");
    }

    @Override
    protected boolean isValid(TProperty value, ValidationContext<T> context, String propertyPath) {
        return value == null;
    }
}
