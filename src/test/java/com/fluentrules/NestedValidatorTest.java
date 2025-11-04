package com.fluentrules;

import com.fluentrules.core.AbstractValidator;
import com.fluentrules.core.ValidationResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NestedValidatorTest {
    static class Address {
        private String city;

        String getCity() {
            return city;
        }

        void setCity(String city) {
            this.city = city;
        }
    }

    static class User {
        private Address address = new Address();

        Address getAddress() {
            return address;
        }

        void setAddress(Address address) {
            this.address = address;
        }
    }

    static class AddressValidator extends AbstractValidator<Address> {
        AddressValidator() {
            RuleFor(Address::getCity).NotEmpty().WithMessage("City required");
        }
    }

    static class UserValidator extends AbstractValidator<User> {
        UserValidator() {
            RuleFor(User::getAddress).NotNull().SetValidator(new AddressValidator());
        }
    }

    @Test
    void shouldCollectNestedErrorsWithPath() {
        User user = new User();
        user.getAddress().setCity("");
        ValidationResult result = new UserValidator().validate(user);
        assertFalse(result.isValid());
        assertEquals("address.city", result.getErrors().get(0).getField());
    }

    @Test
    void shouldPassWhenNestedValid() {
        User user = new User();
        user.getAddress().setCity("Paris");
        ValidationResult result = new UserValidator().validate(user);
        assertTrue(result.isValid());
    }
}
