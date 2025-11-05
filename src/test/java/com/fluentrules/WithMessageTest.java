package com.fluentrules;

import com.fluentrules.core.AbstractValidator;
import com.fluentrules.core.ValidationResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WithMessageTest {
    static class Model {
        private String value;

        String getValue() {
            return value;
        }

        void setValue(String value) {
            this.value = value;
        }
    }

    static class ModelValidator extends AbstractValidator<Model> {
        ModelValidator() {
            RuleFor(Model::getValue).NotEmpty().WithMessage("Custom message");
        }
    }

    @Test
    void shouldUseCustomMessage() {
        Model model = new Model();
        ValidationResult result = new ModelValidator().validate(model);
        assertEquals("Custom message", result.getErrors().get(0).getMessage());
    }
}
