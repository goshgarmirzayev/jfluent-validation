package com.fluentrules.core;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

class PropertyRule<T, TProperty> {
    private final PropertyFunction<T, TProperty> accessor;
    private final String propertyName;
    private final List<ValidatorEntry<T, TProperty>> validators = new ArrayList<>();
    private final Deque<Predicate<T>> conditionStack = new ArrayDeque<>();

    PropertyRule(PropertyFunction<T, TProperty> accessor, String propertyName) {
        this.accessor = Objects.requireNonNull(accessor, "accessor");
        this.propertyName = Objects.requireNonNull(propertyName, "propertyName");
    }

    void addValidator(PropertyValidator<T, TProperty> validator) {
        validators.add(new ValidatorEntry<>(validator, effectiveCondition()));
    }

    void pushCondition(Predicate<T> predicate) {
        conditionStack.push(predicate);
    }

    void popCondition() {
        conditionStack.pop();
    }

    private Predicate<T> effectiveCondition() {
        if (conditionStack.isEmpty()) {
            return null;
        }
        return conditionStack.stream().reduce(Predicate::and).orElse(null);
    }

    TProperty getValue(T instance) {
        return accessor.apply(instance);
    }

    String getPropertyName() {
        return propertyName;
    }

    List<ValidatorEntry<T, TProperty>> getValidators() {
        return validators;
    }
}
