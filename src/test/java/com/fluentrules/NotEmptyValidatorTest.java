package com.fluentrules;

import com.fluentrules.core.AbstractValidator;
import com.fluentrules.core.ValidationResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NotEmptyValidatorTest {
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
            RuleFor(Model::getValue).NotEmpty().WithMessage("Value must not be empty");
        }
    }

    @Test
    void shouldFailWhenEmpty() {
        Model model = new Model();
        model.setValue("");
        ValidationResult result = new ModelValidator().validate(model);
        assertEquals(1, result.getErrors().size());
    }

    @Test
    void shouldPassWhenNotEmpty() {
        Model model = new Model();
        model.setValue("data");
        ValidationResult result = new ModelValidator().validate(model);
        assertTrue(result.isValid());
    }
}
