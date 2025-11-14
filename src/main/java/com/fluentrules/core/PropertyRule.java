package com.fluentrules.core;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Encapsulates configuration for a single property including validators and conditional execution state.
 * Instances are created internally by {@link AbstractValidator} when rules are defined.
 *
 * @param <T> validated object type
 * @param <TProperty> property type controlled by this rule
 * @author Goshgar Mirzayev
 */
class PropertyRule<T, TProperty> {
    private final PropertyFunction<T, TProperty> accessor;
    private final String propertyName;
    private final List<ValidatorEntry<T, TProperty>> validators = new ArrayList<>();
    private final Deque<Predicate<T>> conditionStack = new ArrayDeque<>();

    PropertyRule(PropertyFunction<T, TProperty> accessor, String propertyName) {
        this.accessor = Objects.requireNonNull(accessor, "accessor");
        this.propertyName = Objects.requireNonNull(propertyName, "propertyName");
    }

    /**
     * Adds a new validator to the rule, inheriting the current conditional execution context.
     */
    void addValidator(PropertyValidator<T, TProperty> validator) {
        validators.add(new ValidatorEntry<>(validator, effectiveCondition()));
    }

    /**
     * Pushes a condition onto the stack used to gate subsequent validators.
     */
    void pushCondition(Predicate<T> predicate) {
        conditionStack.push(predicate);
    }

    /**
     * Removes the most recently added condition from the stack.
     */
    void popCondition() {
        conditionStack.pop();
    }

    private Predicate<T> effectiveCondition() {
        if (conditionStack.isEmpty()) {
            return null;
        }
        return conditionStack.stream().reduce(Predicate::and).orElse(null);
    }

    /**
     * Evaluates the property accessor against the given instance.
     */
    TProperty getValue(T instance) {
        return accessor.apply(instance);
    }

    /**
     * @return name of the property being validated
     */
    String getPropertyName() {
        return propertyName;
    }

    /**
     * @return mutable list of validator entries associated with this rule
     */
    List<ValidatorEntry<T, TProperty>> getValidators() {
        return validators;
    }
}
