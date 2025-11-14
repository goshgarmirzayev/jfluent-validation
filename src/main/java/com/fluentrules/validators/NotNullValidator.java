package com.fluentrules.validators;

import com.fluentrules.context.ValidationContext;

/**
 * Ensures a property is not {@code null}.
 *
 * @param <T> type of the root object being validated
 * @param <TProperty> property type
 * @author Goshgar Mirzayev
 */
public class NotNullValidator<T, TProperty> extends AbstractPropertyValidator<T, TProperty> {
    public NotNullValidator() {
        super("Value must not be null");
    }

    @Override
    protected boolean isValid(TProperty value, ValidationContext<T> context, String propertyPath) {
        return value != null;
    }
}
