package com.fluentrules.examples.validators;

import com.fluentrules.core.AbstractValidator;
import com.fluentrules.examples.model.Address;

public class AddressValidator extends AbstractValidator<Address> {
    public AddressValidator() {
        RuleFor(Address::getStreet)
            .NotEmpty()
            .WithMessage("Street must not be empty");

        RuleFor(Address::getCity)
            .NotEmpty()
            .WithMessage("City must not be empty");

        RuleFor(Address::getPostalCode)
            .NotEmpty()
            .WithMessage("Postal code must not be empty")
            .Length(4, 10)
            .WithMessage("Postal code must be between 4 and 10 characters")
            .When(address -> "US".equals(address.getCountry()), builder ->
                builder.Length(5, 5).WithMessage("US postal codes must be exactly 5 characters"));
    }
}
