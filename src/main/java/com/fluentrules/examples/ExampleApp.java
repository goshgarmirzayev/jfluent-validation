package com.fluentrules.examples;

import com.fluentrules.core.ValidationResult;
import com.fluentrules.examples.model.Address;
import com.fluentrules.examples.model.Order;
import com.fluentrules.examples.model.User;
import com.fluentrules.examples.validators.UserValidator;

import java.math.BigDecimal;

public final class ExampleApp {
    private ExampleApp() {
    }

    public static void main(String[] args) {
        Address address = new Address("1 Main St", "", "123", "US");
        User user = new User("", 17, address);
        user.getOrders().add(new Order("", BigDecimal.valueOf(-10)));

        UserValidator validator = new UserValidator();
        ValidationResult result = validator.validate(user);

        if (!result.isValid()) {
            result.getErrors().forEach(error ->
                System.out.printf("%s: %s%n", error.getField(), error.getMessage()));
        }
    }
}
