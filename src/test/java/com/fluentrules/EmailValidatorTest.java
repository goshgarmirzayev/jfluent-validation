package com.fluentrules;

import com.fluentrules.core.AbstractValidator;
import com.fluentrules.core.ValidationResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmailValidatorTest {
    static class Model {
        private String email;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    static class ModelValidator extends AbstractValidator<Model> {
        ModelValidator() {
            RuleFor(Model::getEmail).Email();
        }
    }

    @Test
    void shouldFailForInvalidEmail() {
        Model model = new Model();
        model.setEmail("invalid");
        ValidationResult result = new ModelValidator().validate(model);
        assertEquals(1, result.getErrors().size());
    }

    @Test
    void shouldPassForValidEmail() {
        Model model = new Model();
        model.setEmail("user@example.com");
        ValidationResult result = new ModelValidator().validate(model);
        assertTrue(result.isValid());
    }
}
