package com.fluentrules.core;

import com.fluentrules.validators.CollectionValidator;

/**
 * Specialized {@link RuleBuilder} that targets collection properties and enables per element validation.
 *
 * @param <T> type being validated
 * @param <TElement> type of each element contained in the collection
 * @author Goshgar Mirzayev
 */
public class CollectionRuleBuilder<T, TElement> extends RuleBuilder<T, Iterable<TElement>> {

    CollectionRuleBuilder(PropertyRule<T, Iterable<TElement>> rule) {
        super(rule);
    }

    /**
     * Applies the provided validator to each element in the collection property.
     *
     * @param validator validator to run against each element
     * @return current builder for chaining
     */
    public CollectionRuleBuilder<T, TElement> SetElementValidator(AbstractValidator<TElement> validator) {
        getRule().addValidator(new CollectionValidator<>(validator));
        return this;
    }
}
