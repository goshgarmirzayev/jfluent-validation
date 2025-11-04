package com.fluentrules;

import com.fluentrules.core.AbstractValidator;
import com.fluentrules.core.ValidationResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OnlyIfValidatorTest {
    static class Model {
        private boolean active;
        private String value;

        boolean isActive() {
            return active;
        }

        void setActive(boolean active) {
            this.active = active;
        }

        String getValue() {
            return value;
        }

        void setValue(String value) {
            this.value = value;
        }
    }

    static class ModelValidator extends AbstractValidator<Model> {
        ModelValidator() {
            RuleFor(Model::getValue)
                .OnlyIf(Model::isActive, builder -> builder.NotEmpty());
        }
    }

    @Test
    void shouldNotValidateWhenInactive() {
        Model model = new Model();
        model.setActive(false);
        ValidationResult result = new ModelValidator().validate(model);
        assertTrue(result.isValid());
    }

    @Test
    void shouldValidateWhenActive() {
        Model model = new Model();
        model.setActive(true);
        model.setValue("");
        ValidationResult result = new ModelValidator().validate(model);
        assertEquals(1, result.getErrors().size());
    }
}
