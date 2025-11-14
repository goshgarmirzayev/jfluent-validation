package com.fluentrules.context;

/**
 * Provides contextual information to validators including the root instance and current property path.
 *
 * @param <T> type of the root object being validated
 * @author Goshgar Mirzayev
 */
public final class ValidationContext<T> {
    private final T instance;
    private final String propertyPath;

    /**
     * Creates a context for the provided instance using an empty property path.
     */
    public ValidationContext(T instance) {
        this(instance, "");
    }

    /**
     * Creates a context for the provided instance and property path.
     */
    public ValidationContext(T instance, String propertyPath) {
        this.instance = instance;
        this.propertyPath = propertyPath;
    }

    /**
     * @return root instance being validated
     */
    public T getInstance() {
        return instance;
    }

    /**
     * @return dot separated path to the current property
     */
    public String getPropertyPath() {
        return propertyPath;
    }

    /**
     * Creates a nested context for a child property.
     *
     * @param childProperty name of the child property
     * @return new validation context representing the child scope
     */
    public ValidationContext<T> child(String childProperty) {
        String newPath = propertyPath == null || propertyPath.isEmpty() ? childProperty : propertyPath + "." + childProperty;
        return new ValidationContext<>(instance, newPath);
    }
}
