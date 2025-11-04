package com.fluentrules.core;

public interface Validator<T> {
    ValidationResult validate(T instance);
}
