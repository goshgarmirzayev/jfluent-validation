package com.fluentrules;

import com.fluentrules.core.AbstractValidator;
import com.fluentrules.core.ValidationResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LengthValidatorTest {
    static class Model {
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    static class ModelValidator extends AbstractValidator<Model> {
        ModelValidator() {
            RuleFor(Model::getValue).Length(2, 5);
        }
    }

    @Test
    void shouldFailWhenTooShort() {
        Model model = new Model();
        model.setValue("a");
        ValidationResult result = new ModelValidator().validate(model);
        assertFalse(result.isValid());
    }

    @Test
    void shouldFailWhenTooLong() {
        Model model = new Model();
        model.setValue("abcdef");
        ValidationResult result = new ModelValidator().validate(model);
        assertFalse(result.isValid());
    }

    @Test
    void shouldPassWhenWithinRange() {
        Model model = new Model();
        model.setValue("abcd");
        ValidationResult result = new ModelValidator().validate(model);
        assertTrue(result.isValid());
    }
}
