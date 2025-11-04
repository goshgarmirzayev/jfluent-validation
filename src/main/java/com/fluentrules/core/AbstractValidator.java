package com.fluentrules.core;

import com.fluentrules.context.ValidationContext;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractValidator<T> implements Validator<T> {
    private final List<PropertyRule<T, ?>> rules = new ArrayList<>();

    protected <TProperty> RuleBuilder<T, TProperty> RuleFor(PropertyFunction<T, TProperty> accessor) {
        String propertyName = LambdaUtils.resolvePropertyName(accessor);
        PropertyRule<T, TProperty> rule = new PropertyRule<>(accessor, propertyName);
        rules.add(rule);
        return new RuleBuilder<>(rule);
    }

    protected <TElement> CollectionRuleBuilder<T, TElement> RuleForEach(PropertyFunction<T, Iterable<TElement>> accessor) {
        String propertyName = LambdaUtils.resolvePropertyName(accessor);
        PropertyRule<T, Iterable<TElement>> rule = new PropertyRule<>(accessor, propertyName);
        rules.add(rule);
        return new CollectionRuleBuilder<>(rule);
    }

    @Override
    public ValidationResult validate(T instance) {
        List<ValidationError> errors = new ArrayList<>();
        ValidationContext<T> context = new ValidationContext<>(instance);
        for (PropertyRule<T, ?> rule : rules) {
            validateRule(instance, context, rule, errors, rule.getPropertyName());
        }
        return new ValidationResult(errors);
    }

    private <TProperty> void validateRule(T instance, ValidationContext<T> context, PropertyRule<T, TProperty> rule,
                                          List<ValidationError> errors, String propertyPath) {
        TProperty value = rule.getValue(instance);
        for (ValidatorEntry<T, TProperty> entry : rule.getValidators()) {
            if (entry.getCondition().map(condition -> condition.test(instance)).orElse(true)) {
                PropertyValidator<T, TProperty> validator = entry.getValidator();
                ValidationContext<T> propertyContext = context.child(propertyPath);
                ValidationStatus status = validator.validate(value, propertyContext);
                if (!status.isValid()) {
                    if (status.getErrors().isEmpty()) {
                        errors.add(new ValidationError(propertyPath, entry.resolveMessage()));
                    } else {
                        errors.addAll(status.getErrors());
                    }
                }
            }
        }
    }
}
