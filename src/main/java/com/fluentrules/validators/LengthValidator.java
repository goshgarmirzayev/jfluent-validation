package com.fluentrules.validators;

import com.fluentrules.context.ValidationContext;

public class LengthValidator<T> extends AbstractPropertyValidator<T, CharSequence> {
    private final int min;
    private final int max;

    public LengthValidator(int min, int max) {
        super("Length must be between " + min + " and " + max);
        if (min < 0 || max < min) {
            throw new IllegalArgumentException("Invalid length range");
        }
        this.min = min;
        this.max = max;
    }

    @Override
    protected boolean isValid(CharSequence value, ValidationContext<T> context, String propertyPath) {
        if (value == null) {
            return true;
        }
        int length = value.length();
        return length >= min && length <= max;
    }
}
