package com.fluentrules;

import com.fluentrules.core.AbstractValidator;
import com.fluentrules.core.ValidationResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UnlessValidatorTest {
    static class Model {
        private boolean skip;
        private String value;

        boolean isSkip() {
            return skip;
        }

        void setSkip(boolean skip) {
            this.skip = skip;
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
                .Unless(Model::isSkip, builder -> builder.NotEmpty());
        }
    }

    @Test
    void shouldSkipWhenUnlessConditionTrue() {
        Model model = new Model();
        model.setSkip(true);
        ValidationResult result = new ModelValidator().validate(model);
        assertTrue(result.isValid());
    }

    @Test
    void shouldValidateWhenConditionFalse() {
        Model model = new Model();
        model.setSkip(false);
        model.setValue("");
        ValidationResult result = new ModelValidator().validate(model);
        assertEquals(1, result.getErrors().size());
    }
}
