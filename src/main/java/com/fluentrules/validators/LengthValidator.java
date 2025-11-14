package com.fluentrules.validators;

import com.fluentrules.context.ValidationContext;

/**
 * Ensures a character sequence has a length within the provided inclusive range.
 *
 * @param <T> type of the root object being validated
 * @param <TProperty> property type (must implement {@link CharSequence})
 * @author Goshgar Mirzayev
 */
public class LengthValidator<T, TProperty extends CharSequence> extends AbstractPropertyValidator<T, TProperty> {
    private final int min;
    private final int max;

    public LengthValidator(int min, int max) {
        super("Length must be between " + min + " and " + max);
        this.min = min;
        this.max = max;
    }

    @Override
    protected boolean isValid(TProperty value, ValidationContext<T> context, String propertyPath) {
        if (value == null) {
            return true;
        }
        int length = value.length();
        return length >= min && length <= max;
    }
}
