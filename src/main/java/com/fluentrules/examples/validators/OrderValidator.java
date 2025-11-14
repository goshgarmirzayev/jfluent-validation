package com.fluentrules.examples.validators;

import com.fluentrules.core.AbstractValidator;
import com.fluentrules.examples.model.Order;

import java.math.BigDecimal;

/**
 * Illustrates rules for validating {@link Order} aggregates in the sample domain.
 *
 * @author Goshgar Mirzayev
 */
public class OrderValidator extends AbstractValidator<Order> {
    public OrderValidator() {
        RuleFor(Order::getId)
            .NotEmpty();

        RuleFor(Order::getTotal)
            .NotNull()
            .Must((order, total) -> total.compareTo(BigDecimal.ZERO) >= 0)
            .WithMessage("Total must be greater than or equal to 0");
    }
}
