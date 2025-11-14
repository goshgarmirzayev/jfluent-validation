package com.fluentrules.validators;

import com.fluentrules.context.ValidationContext;

/**
 * Ensures a comparable property is strictly less than the provided threshold.
 *
 * @param <T> type of the root object being validated
 * @param <TProperty> property type (must implement {@link Comparable})
 * @author Goshgar Mirzayev
 */
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
        @SuppressWarnings({"unchecked", "rawtypes"})
        Comparable<Object> left = (Comparable) comparable;
        return left.compareTo(threshold) < 0;
    }
}
