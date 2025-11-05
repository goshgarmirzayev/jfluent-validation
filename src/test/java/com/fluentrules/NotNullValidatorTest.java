package com.fluentrules;

import com.fluentrules.core.AbstractValidator;
import com.fluentrules.core.ValidationResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NotNullValidatorTest {
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
            RuleFor(Model::getValue).NotNull();
        }
    }

    @Test
    void shouldFailWhenNull() {
        Model model = new Model();
        ValidationResult result = new ModelValidator().validate(model);
        assertFalse(result.isValid());
    }

    @Test
    void shouldPassWhenNotNull() {
        Model model = new Model();
        model.setValue("value");
        ValidationResult result = new ModelValidator().validate(model);
        assertTrue(result.isValid());
    }
}
