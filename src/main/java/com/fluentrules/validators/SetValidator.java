package com.fluentrules.validators;

import com.fluentrules.context.ValidationContext;
import com.fluentrules.core.AbstractValidator;
import com.fluentrules.core.ValidationError;
import com.fluentrules.core.ValidationResult;
import com.fluentrules.core.ValidationStatus;

import java.util.ArrayList;
import java.util.List;

public class SetValidator<T, TProperty> extends AbstractPropertyValidator<T, TProperty> {
    private final AbstractValidator<TProperty> validator;

    public SetValidator(AbstractValidator<TProperty> validator) {
        super("Nested validation failed");
        this.validator = java.util.Objects.requireNonNull(validator, "validator");
    }

    @Override
    protected boolean isValid(TProperty value, ValidationContext<T> context, String propertyPath) {
        throw new UnsupportedOperationException("Use validate override");
    }

    @Override
    public ValidationStatus validate(TProperty value, ValidationContext<T> context) {
        if (value == null) {
            return ValidationStatus.success();
        }
        ValidationResult result = validator.validate(value);
        if (result.isValid()) {
            return ValidationStatus.success();
        }
        List<ValidationError> errors = new ArrayList<>();
        String basePath = context.getPropertyPath();
        for (ValidationError error : result.getErrors()) {
            String field = error.getField();
            String combined;
            if (basePath == null || basePath.isEmpty()) {
                combined = field;
            } else if (field == null || field.isEmpty()) {
                combined = basePath;
            } else {
                combined = basePath + "." + field;
            }
            errors.add(new ValidationError(combined == null ? "" : combined, error.getMessage()));
        }
        return ValidationStatus.failure(errors);
    }
}
