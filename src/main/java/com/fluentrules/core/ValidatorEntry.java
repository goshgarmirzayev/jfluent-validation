package com.fluentrules.core;

import java.util.Optional;
import java.util.function.Predicate;

class ValidatorEntry<T, TProperty> {
    private final PropertyValidator<T, TProperty> validator;
    private final Predicate<T> condition;
    private String messageOverride;

    ValidatorEntry(PropertyValidator<T, TProperty> validator, Predicate<T> condition) {
        this.validator = validator;
        this.condition = condition;
    }

    PropertyValidator<T, TProperty> getValidator() {
        return validator;
    }

    Optional<Predicate<T>> getCondition() {
        return Optional.ofNullable(condition);
    }

    String resolveMessage() {
        return messageOverride != null ? messageOverride : validator.getDefaultMessage();
    }

    void setMessageOverride(String messageOverride) {
        this.messageOverride = messageOverride;
    }
}
