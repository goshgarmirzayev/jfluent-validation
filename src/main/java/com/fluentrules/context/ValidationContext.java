package com.fluentrules.context;

public final class ValidationContext<T> {
    private final T instance;
    private final String propertyPath;

    public ValidationContext(T instance) {
        this(instance, "");
    }

    public ValidationContext(T instance, String propertyPath) {
        this.instance = instance;
        this.propertyPath = propertyPath;
    }

    public T getInstance() {
        return instance;
    }

    public String getPropertyPath() {
        return propertyPath;
    }

    public ValidationContext<T> child(String childProperty) {
        String newPath = propertyPath == null || propertyPath.isEmpty() ? childProperty : propertyPath + "." + childProperty;
        return new ValidationContext<>(instance, newPath);
    }
}
