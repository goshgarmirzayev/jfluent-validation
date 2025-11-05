package com.fluentrules;

import com.fluentrules.core.AbstractValidator;
import com.fluentrules.core.ValidationResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConditionalValidatorTest {
    static class User {
        private boolean require;
        private String value;

        boolean isRequire() {
            return require;
        }

        void setRequire(boolean require) {
            this.require = require;
        }

        String getValue() {
            return value;
        }

        void setValue(String value) {
            this.value = value;
        }
    }

    static class UserValidator extends AbstractValidator<User> {
        UserValidator() {
            RuleFor(User::getValue)
                .When(User::isRequire, builder -> builder.NotEmpty().WithMessage("Value required when flag is set"));
        }
    }

    @Test
    void shouldSkipValidationWhenConditionFalse() {
        User user = new User();
        user.setRequire(false);
        ValidationResult result = new UserValidator().validate(user);
        assertTrue(result.isValid());
    }

    @Test
    void shouldValidateWhenConditionTrue() {
        User user = new User();
        user.setRequire(true);
        user.setValue("");
        ValidationResult result = new UserValidator().validate(user);
        assertEquals(1, result.getErrors().size());
    }
}
