package com.fluentrules.core;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

/**
 * Utility methods for working with lambda expressions used in rule configuration.
 *
 * @author Goshgar Mirzayev
 */
final class LambdaUtils {
    private LambdaUtils() {
    }

    /**
     * Attempts to resolve the property name targeted by the provided lambda expression.
     *
     * @param lambda property accessor lambda captured by {@link PropertyFunction}
     * @return inferred property name
     */
    static String resolvePropertyName(PropertyFunction<?, ?> lambda) {
        try {
            Method writeReplace = lambda.getClass().getDeclaredMethod("writeReplace");
            writeReplace.setAccessible(true);
            Object serializedForm = writeReplace.invoke(lambda);
            if (serializedForm instanceof SerializedLambda serializedLambda) {
                String methodName = serializedLambda.getImplMethodName();
                if (methodName.startsWith("get") && methodName.length() > 3) {
                    return decapitalize(methodName.substring(3));
                }
                if (methodName.startsWith("is") && methodName.length() > 2) {
                    return decapitalize(methodName.substring(2));
                }
                return methodName;
            }
        } catch (ReflectiveOperationException ex) {
            throw new IllegalStateException("Unable to resolve property name from lambda", ex);
        }
        throw new IllegalStateException("Unable to resolve property name from lambda");
    }

    private static String decapitalize(String value) {
        if (value.isEmpty()) {
            return value;
        }
        if (value.length() > 1 && Character.isUpperCase(value.charAt(1)) && Character.isUpperCase(value.charAt(0))) {
            return value;
        }
        return Character.toLowerCase(value.charAt(0)) + value.substring(1);
    }
}
