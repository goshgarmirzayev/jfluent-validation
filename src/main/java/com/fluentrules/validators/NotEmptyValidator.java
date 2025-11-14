package com.fluentrules.validators;

import com.fluentrules.context.ValidationContext;

import java.util.Collection;

/**
 * Ensures a property contains at least one element or character depending on its type.
 *
 * @param <T> type of the root object being validated
 * @param <TProperty> property type
 * @author Goshgar Mirzayev
 */
public class NotEmptyValidator<T, TProperty> extends AbstractPropertyValidator<T, TProperty> {
    public NotEmptyValidator() {
        super("Value must not be empty");
    }

    @Override
    protected boolean isValid(TProperty value, ValidationContext<T> context, String propertyPath) {
        if (value == null) {
            return false;
        }
        if (value instanceof CharSequence sequence) {
            return sequence.length() > 0;
        }
        if (value instanceof Collection<?> collection) {
            return !collection.isEmpty();
        }
        if (value instanceof Object[] array) {
            return array.length > 0;
        }
        return true;
    }
}
