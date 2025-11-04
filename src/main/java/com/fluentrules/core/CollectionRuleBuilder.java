package com.fluentrules.core;

import com.fluentrules.validators.CollectionValidator;



public class CollectionRuleBuilder<T, TElement> extends RuleBuilder<T, Iterable<TElement>> {

    CollectionRuleBuilder(PropertyRule<T, Iterable<TElement>> rule) {
        super(rule);
    }

    public CollectionRuleBuilder<T, TElement> SetValidator(AbstractValidator<TElement> validator) {
        getRule().addValidator(new CollectionValidator<>(validator));
        return this;
    }
}
