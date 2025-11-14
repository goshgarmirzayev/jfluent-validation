package com.fluentrules.validators;

import com.fluentrules.context.ValidationContext;

import java.util.Objects;
import java.util.function.BiPredicate;

/**
 * Wraps a {@link BiPredicate} to express custom validation logic that has access to both the instance and property.
 *
 * @param <T> type of the root object being validated
 * @param <TProperty> property type
 * @author Goshgar Mirzayev
 */
public class MustValidator<T, TProperty> extends AbstractPropertyValidator<T, TProperty> {
    private final BiPredicate<T, TProperty> predicate;

    public MustValidator(BiPredicate<T, TProperty> predicate) {
        super("Custom validation failed");
        this.predicate = Objects.requireNonNull(predicate, "predicate");
    }

    @Override
    protected boolean isValid(TProperty value, ValidationContext<T> context, String propertyPath) {
        return predicate.test(context.getInstance(), value);
    }
}
