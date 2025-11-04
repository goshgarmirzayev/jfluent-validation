package com.fluentrules;

import com.fluentrules.core.AbstractValidator;
import com.fluentrules.core.ValidationResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MustValidatorTest {
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
            RuleFor(Model::getValue)
                .Must((model, value) -> value != null && value.startsWith("A"))
                .WithMessage("Value must start with A");
        }
    }

    @Test
    void shouldFailWhenPredicateFalse() {
        Model model = new Model();
        model.setValue("B");
        ValidationResult result = new ModelValidator().validate(model);
        assertFalse(result.isValid());
    }

    @Test
    void shouldPassWhenPredicateTrue() {
        Model model = new Model();
        model.setValue("Alpha");
        ValidationResult result = new ModelValidator().validate(model);
        assertTrue(result.isValid());
    }
}
