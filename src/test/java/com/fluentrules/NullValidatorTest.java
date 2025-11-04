package com.fluentrules;

import com.fluentrules.core.AbstractValidator;
import com.fluentrules.core.ValidationResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NullValidatorTest {
    static class Model {
        private String value = "data";

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    static class ModelValidator extends AbstractValidator<Model> {
        ModelValidator() {
            RuleFor(Model::getValue).Null();
        }
    }

    @Test
    void shouldFailWhenNotNull() {
        Model model = new Model();
        ValidationResult result = new ModelValidator().validate(model);
        assertFalse(result.isValid());
    }

    @Test
    void shouldPassWhenNull() {
        Model model = new Model();
        model.setValue(null);
        ValidationResult result = new ModelValidator().validate(model);
        assertTrue(result.isValid());
    }
}
