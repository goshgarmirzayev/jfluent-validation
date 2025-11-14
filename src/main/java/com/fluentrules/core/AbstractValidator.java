package com.fluentrules.core;

/**
 * Base implementation of {@link Validator} that manages the lifecycle of property rules and coordinates
 * validation execution against a provided instance. Concrete validators should extend this class and declare
 * validation logic through the provided {@code RuleFor} and {@code RuleForEach} helpers.
 *
 * <p>This library is maintained by Goshgar Mirzayev ({@code jgoshgarmirzayev@gmail.com}).</p>
 *
 * @param <T> type of the object being validated
 * @author Goshgar Mirzayev
 */

import com.fluentrules.context.ValidationContext;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractValidator<T> implements Validator<T> {
    private final List<PropertyRule<T, ?>> rules = new ArrayList<>();

    /**
     * Registers a validation rule for the provided property accessor.
     *
     * @param accessor lambda expression that extracts the property from the validated instance
     * @param <TProperty> property type
     * @return a rule builder that can be used to configure validators for the property
     */
    protected <TProperty> RuleBuilder<T, TProperty> RuleFor(PropertyFunction<T, TProperty> accessor) {
        String propertyName = LambdaUtils.resolvePropertyName(accessor);
        PropertyRule<T, TProperty> rule = new PropertyRule<>(accessor, propertyName);
        rules.add(rule);
        return new RuleBuilder<>(rule);
    }

    /**
     * Registers a validation rule for each element of a collection property.
     *
     * @param accessor lambda expression that extracts the collection from the validated instance
     * @param <TElement> element type in the collection
     * @return a collection rule builder that allows configuring validators for each element
     */
    protected <TElement> CollectionRuleBuilder<T, TElement> RuleForEach(PropertyFunction<T, Iterable<TElement>> accessor) {
        String propertyName = LambdaUtils.resolvePropertyName(accessor);
        PropertyRule<T, Iterable<TElement>> rule = new PropertyRule<>(accessor, propertyName);
        rules.add(rule);
        return new CollectionRuleBuilder<>(rule);
    }

    @Override
    /**
     * Executes validation for the provided instance and returns the aggregated {@link ValidationResult}.
     *
     * @param instance object to validate
     * @return validation result containing any errors discovered during evaluation
     */
    public ValidationResult validate(T instance) {
        List<ValidationError> errors = new ArrayList<>();
        ValidationContext<T> context = new ValidationContext<>(instance);
        for (PropertyRule<T, ?> rule : rules) {
            validateRule(instance, context, rule, errors, rule.getPropertyName());
        }
        return new ValidationResult(errors);
    }

    /**
     * Applies the specified property rule to the instance and collects any validation errors.
     *
     * @param instance object being validated
     * @param context validation context shared across rules
     * @param rule property rule to execute
     * @param errors accumulator for discovered validation errors
     * @param propertyPath human-readable path describing the property being validated
     * @param <TProperty> property type associated with the rule
     */
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
