package com.fluentrules.core;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * Internal data structure capturing a {@link PropertyValidator} together with optional conditions and message
 * overrides as part of a property rule configuration.
 *
 * @param <T> validated object type
 * @param <TProperty> property type
 * @author Goshgar Mirzayev
 */
class ValidatorEntry<T, TProperty> {
    private final PropertyValidator<T, TProperty> validator;
    private final Predicate<T> condition;
    private String messageOverride;

    /**
     * Creates a new entry for the given validator and condition.
     */
    ValidatorEntry(PropertyValidator<T, TProperty> validator, Predicate<T> condition) {
        this.validator = validator;
        this.condition = condition;
    }

    /**
     * @return validator assigned to the rule
     */
    PropertyValidator<T, TProperty> getValidator() {
        return validator;
    }

    /**
     * @return optional condition that must be satisfied before the validator is executed
     */
    Optional<Predicate<T>> getCondition() {
        return Optional.ofNullable(condition);
    }

    /**
     * Resolves the effective validation message, honoring overrides.
     *
     * @return message string for validation failures
     */
    String resolveMessage() {
        return messageOverride != null ? messageOverride : validator.getDefaultMessage();
    }

    /**
     * Overrides the default message provided by the validator.
     *
     * @param messageOverride message to use when validation fails
     */
    void setMessageOverride(String messageOverride) {
        this.messageOverride = messageOverride;
    }
}
