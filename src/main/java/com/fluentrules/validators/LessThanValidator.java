package com.fluentrules.validators;

import com.fluentrules.context.ValidationContext;

public class LessThanValidator<T, TProperty> extends AbstractPropertyValidator<T, TProperty> {
    private final TProperty threshold;

    public LessThanValidator(TProperty threshold) {
        super("Value must be less than " + threshold);
        this.threshold = threshold;
    }

    @Override
    protected boolean isValid(TProperty value, ValidationContext<T> context, String propertyPath) {
        if (value == null) {
            return true;
        }
        if (!(value instanceof Comparable<?> comparable) || !(threshold instanceof Comparable<?>)) {
            throw new IllegalStateException("Both value and threshold must be comparable");
        }
        @SuppressWarnings("unchecked")
        Comparable<Object> valueComparable = (Comparable<Object>) comparable;
        return valueComparable.compareTo(threshold) < 0;
    }
}
