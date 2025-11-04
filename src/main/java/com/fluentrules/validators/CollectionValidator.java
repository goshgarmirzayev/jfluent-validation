package com.fluentrules.validators;

import com.fluentrules.context.ValidationContext;
import com.fluentrules.core.AbstractValidator;
import com.fluentrules.core.PropertyValidator;
import com.fluentrules.core.ValidationError;
import com.fluentrules.core.ValidationResult;
import com.fluentrules.core.ValidationStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CollectionValidator<T, TElement> implements PropertyValidator<T, Iterable<TElement>> {
    private final AbstractValidator<TElement> elementValidator;

    public CollectionValidator(AbstractValidator<TElement> elementValidator) {
        this.elementValidator = Objects.requireNonNull(elementValidator, "elementValidator");
    }

    @Override
    public ValidationStatus validate(Iterable<TElement> value, ValidationContext<T> context) {
        if (value == null) {
            return ValidationStatus.success();
        }
        List<ValidationError> errors = new ArrayList<>();
        int index = 0;
        for (TElement element : value) {
            ValidationResult result = elementValidator.validate(element);
            if (!result.isValid()) {
                String prefix = context.getPropertyPath().isEmpty() ? "[" + index + "]"
                    : context.getPropertyPath() + "[" + index + "]";
                for (ValidationError error : result.getErrors()) {
                    String combined = error.getField().isEmpty() ? prefix : prefix + "." + error.getField();
                    errors.add(new ValidationError(combined, error.getMessage()));
                }
            }
            index++;
        }
        if (errors.isEmpty()) {
            return ValidationStatus.success();
        }
        return ValidationStatus.failure(errors);
    }

    @Override
    public String getDefaultMessage() {
        return "Collection validation failed";
    }
}
