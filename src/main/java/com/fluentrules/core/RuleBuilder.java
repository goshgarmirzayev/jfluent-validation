package com.fluentrules.core;

import com.fluentrules.validators.EmailValidator;
import com.fluentrules.validators.GreaterThanValidator;
import com.fluentrules.validators.LengthValidator;
import com.fluentrules.validators.LessThanValidator;
import com.fluentrules.validators.MustValidator;
import com.fluentrules.validators.NotEmptyValidator;
import com.fluentrules.validators.NotNullValidator;
import com.fluentrules.validators.NullValidator;
import com.fluentrules.validators.SetValidator;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Fluent builder responsible for configuring property level validators. Each method appends a validator to the
 * underlying {@link PropertyRule}. The builder is intentionally stateful and should be reused only within a rule
 * configuration block.
 *
 * @param <T> type being validated
 * @param <TProperty> property type for the current rule
 * @author Goshgar Mirzayev
 */
public class RuleBuilder<T, TProperty> {
    private final PropertyRule<T, TProperty> rule;

    RuleBuilder(PropertyRule<T, TProperty> rule) {
        this.rule = rule;
    }

    /**
     * @return the backing property rule. Intended for internal use.
     */
    protected PropertyRule<T, TProperty> getRule() {
        return rule;
    }

    /**
     * Ensures the property is not empty.
     *
     * @return current builder for chaining
     */
    public RuleBuilder<T, TProperty> NotEmpty() {
        rule.addValidator(new NotEmptyValidator<>());
        return this;
    }

    /**
     * Ensures the property is not {@code null}.
     *
     * @return current builder for chaining
     */
    public RuleBuilder<T, TProperty> NotNull() {
        rule.addValidator(new NotNullValidator<>());
        return this;
    }

    /**
     * Ensures the property is {@code null}.
     *
     * @return current builder for chaining
     */
    public RuleBuilder<T, TProperty> Null() {
        rule.addValidator(new NullValidator<>());
        return this;
    }

    /**
     * Applies an e-mail format validator. Only applicable to {@link String} properties.
     *
     * @return current builder for chaining
     */
    public RuleBuilder<T, TProperty> Email() {
        @SuppressWarnings("unchecked")
        PropertyValidator<T, TProperty> validator = (PropertyValidator<T, TProperty>) new EmailValidator<>();
        rule.addValidator(validator);
        return this;
    }

    /**
     * Adds a {@link GreaterThanValidator} using the supplied threshold.
     *
     * @param threshold value the property must exceed
     * @return current builder for chaining
     */
    public RuleBuilder<T, TProperty> GreaterThan(TProperty threshold) {
        rule.addValidator(new GreaterThanValidator<>(threshold));
        return this;
    }

    /**
     * Adds a {@link LessThanValidator} using the supplied threshold.
     *
     * @param threshold value the property must be less than
     * @return current builder for chaining
     */
    public RuleBuilder<T, TProperty> LessThan(TProperty threshold) {
        rule.addValidator(new LessThanValidator<>(threshold));
        return this;
    }

    /**
     * Restricts string length to the provided inclusive range.
     *
     * @param min minimum allowed length
     * @param max maximum allowed length
     * @return current builder for chaining
     */
    public RuleBuilder<T, TProperty> Length(int min, int max) {
        @SuppressWarnings("unchecked")
        PropertyValidator<T, TProperty> validator = (PropertyValidator<T, TProperty>) new LengthValidator<>(min, max);
        rule.addValidator(validator);
        return this;
    }

    /**
     * Registers a custom predicate based validator.
     *
     * @param predicate logic that returns {@code true} for valid values
     * @return current builder for chaining
     */
    public RuleBuilder<T, TProperty> Must(BiPredicate<T, TProperty> predicate) {
        Objects.requireNonNull(predicate, "predicate");
        rule.addValidator(new MustValidator<>(predicate));
        return this;
    }

    /**
     * Overrides the default message of the most recently added validator.
     *
     * @param message custom message to report when validation fails
     * @return current builder for chaining
     */
    public RuleBuilder<T, TProperty> WithMessage(String message) {
        if (rule.getValidators().isEmpty()) {
            throw new IllegalStateException("WithMessage must follow a validator call");
        }
        rule.getValidators().get(rule.getValidators().size() - 1).setMessageOverride(message);
        return this;
    }

    /**
     * Executes the provided configuration only when the predicate evaluates to {@code true}.
     *
     * @param predicate guard controlling the execution of nested rules
     * @param action nested rule configuration
     * @return current builder for chaining
     */
    public RuleBuilder<T, TProperty> When(Predicate<T> predicate, Consumer<RuleBuilder<T, TProperty>> action) {
        Objects.requireNonNull(predicate, "predicate");
        Objects.requireNonNull(action, "action");
        rule.pushCondition(predicate);
        try {
            action.accept(this);
        } finally {
            rule.popCondition();
        }
        return this;
    }

    /**
     * Executes the provided configuration only when the predicate evaluates to {@code false}.
     *
     * @param predicate guard controlling the execution of nested rules
     * @param action nested rule configuration
     * @return current builder for chaining
     */
    public RuleBuilder<T, TProperty> Unless(Predicate<T> predicate, Consumer<RuleBuilder<T, TProperty>> action) {
        return When(predicate.negate(), action);
    }

    /**
     * Synonym for {@link #When(Predicate, Consumer)} for expressive rule definitions.
     *
     * @param predicate guard controlling the execution of nested rules
     * @param action nested rule configuration
     * @return current builder for chaining
     */
    public RuleBuilder<T, TProperty> OnlyIf(Predicate<T> predicate, Consumer<RuleBuilder<T, TProperty>> action) {
        return When(predicate, action);
    }

    /**
     * Adds a validator capable of recursively validating elements of a collection or complex property.
     *
     * @param validator validator that will be applied to the property value
     * @return current builder for chaining
     */
    public RuleBuilder<T, TProperty> SetValidator(AbstractValidator<TProperty> validator) {
        rule.addValidator(new SetValidator<>(validator));
        return this;
    }
}
