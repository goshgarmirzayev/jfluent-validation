package com.fluentrules.core;

import com.fluentrules.context.ValidationContext;

public interface PropertyValidator<T, TProperty> {
    ValidationStatus validate(TProperty value, ValidationContext<T> context);

    String getDefaultMessage();
}
