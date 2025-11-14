package com.fluentrules.validators;

import com.fluentrules.context.ValidationContext;

import java.util.regex.Pattern;

/**
 * Validates that a character sequence conforms to a simple e-mail address pattern.
 *
 * @param <T> type of the root object being validated
 * @param <TProperty> type of the property under validation (must implement {@link CharSequence})
 * @author Goshgar Mirzayev
 */
public class EmailValidator<T, TProperty extends CharSequence> extends AbstractPropertyValidator<T, TProperty> {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public EmailValidator() {
        super("Email is not valid");
    }

    @Override
    protected boolean isValid(TProperty value, ValidationContext<T> context, String propertyPath) {
        if (value == null || value.length() == 0) {
            return true; // allow empty, combine with NotEmpty if needed
        }
        return EMAIL_PATTERN.matcher(value).matches();
    }
}
