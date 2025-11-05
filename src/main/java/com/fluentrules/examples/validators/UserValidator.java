package com.fluentrules.examples.validators;

import com.fluentrules.core.AbstractValidator;
import com.fluentrules.examples.model.User;

public class UserValidator extends AbstractValidator<User> {
    public UserValidator() {
        RuleFor(User::getEmail)
            .NotEmpty()
            .Email();

        RuleFor(User::getAge)
            .NotNull()
            .GreaterThan(18)
            .LessThan(100)
            .WithMessage("Age must be between 18 and 100");

        RuleFor(User::getAddress)
            .NotNull()
            .SetValidator(new AddressValidator());

        RuleForEach(User::getOrders)
            .SetElementValidator(new OrderValidator());

        RuleFor(User::getEmail)
            .When(user -> user.getAge() != null && user.getAge() < 25, builder ->
                builder.Must((user, email) -> email != null && email.endsWith("@student.example.com"))
                    .WithMessage("Young users must use a student email"));
    }
}
