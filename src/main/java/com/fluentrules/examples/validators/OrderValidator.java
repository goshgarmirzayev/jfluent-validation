package com.fluentrules.examples.validators;

import com.fluentrules.core.AbstractValidator;
import com.fluentrules.examples.model.Order;

import java.math.BigDecimal;

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
