package com.fluentrules.validators;

import com.fluentrules.context.ValidationContext;

import java.util.function.BiPredicate;

public class MustValidator<T, TProperty> extends AbstractPropertyValidator<T, TProperty> {
    private final BiPredicate<T, TProperty> predicate;

    public MustValidator(BiPredicate<T, TProperty> predicate) {
        super("Custom validation failed");
        this.predicate = predicate;
    }

    @Override
    protected boolean isValid(TProperty value, ValidationContext<T> context, String propertyPath) {
        return predicate.test(context.getInstance(), value);
    }
}
