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

public class RuleBuilder<T, TProperty> {
    private final PropertyRule<T, TProperty> rule;

    RuleBuilder(PropertyRule<T, TProperty> rule) {
        this.rule = rule;
    }

    protected PropertyRule<T, TProperty> getRule() {
        return rule;
    }

    public RuleBuilder<T, TProperty> NotEmpty() {
        rule.addValidator(new NotEmptyValidator<>());
        return this;
    }

    public RuleBuilder<T, TProperty> NotNull() {
        rule.addValidator(new NotNullValidator<>());
        return this;
    }

    public RuleBuilder<T, TProperty> Null() {
        rule.addValidator(new NullValidator<>());
        return this;
    }

    public RuleBuilder<T, TProperty> Email() {
        rule.addValidator(new EmailValidator<>());
        return this;
    }

    public RuleBuilder<T, TProperty> GreaterThan(TProperty threshold) {
        rule.addValidator(new GreaterThanValidator<>(threshold));
        return this;
    }

    public RuleBuilder<T, TProperty> LessThan(TProperty threshold) {
        rule.addValidator(new LessThanValidator<>(threshold));
        return this;
    }

    public RuleBuilder<T, TProperty> Length(int min, int max) {
        rule.addValidator(new LengthValidator<>(min, max));
        return this;
    }

    public RuleBuilder<T, TProperty> Must(BiPredicate<T, TProperty> predicate) {
        Objects.requireNonNull(predicate, "predicate");
        rule.addValidator(new MustValidator<>(predicate));
        return this;
    }

    public RuleBuilder<T, TProperty> WithMessage(String message) {
        if (rule.getValidators().isEmpty()) {
            throw new IllegalStateException("WithMessage must follow a validator call");
        }
        rule.getValidators().get(rule.getValidators().size() - 1).setMessageOverride(message);
        return this;
    }

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

    public RuleBuilder<T, TProperty> Unless(Predicate<T> predicate, Consumer<RuleBuilder<T, TProperty>> action) {
        return When(predicate.negate(), action);
    }

    public RuleBuilder<T, TProperty> OnlyIf(Predicate<T> predicate, Consumer<RuleBuilder<T, TProperty>> action) {
        return When(predicate, action);
    }

    public RuleBuilder<T, TProperty> SetValidator(AbstractValidator<TProperty> validator) {
        rule.addValidator(new SetValidator<>(validator));
        return this;
    }
}
