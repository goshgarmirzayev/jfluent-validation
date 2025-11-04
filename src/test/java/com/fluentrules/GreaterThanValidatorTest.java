package com.fluentrules;

import com.fluentrules.core.AbstractValidator;
import com.fluentrules.core.ValidationResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GreaterThanValidatorTest {
    static class Model {
        private Integer value;

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }

    static class ModelValidator extends AbstractValidator<Model> {
        ModelValidator() {
            RuleFor(Model::getValue).GreaterThan(10);
        }
    }

    @Test
    void shouldFailWhenLessThanOrEqual() {
        Model model = new Model();
        model.setValue(10);
        ValidationResult result = new ModelValidator().validate(model);
        assertFalse(result.isValid());
    }

    @Test
    void shouldPassWhenGreaterThan() {
        Model model = new Model();
        model.setValue(11);
        ValidationResult result = new ModelValidator().validate(model);
        assertTrue(result.isValid());
    }
}
