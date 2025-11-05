package com.fluentrules;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Lightweight reflection based test runner so the suite can execute without
 * the official JUnit runtime. It scans the known test classes and executes
 * methods annotated with {@link Test}, reporting a summary to STDOUT.
 */
public final class TestRunner {
    private static final Class<?>[] TEST_CLASSES = new Class<?>[] {
        CollectionValidatorTest.class,
        ConditionalValidatorTest.class,
        EmailValidatorTest.class,
        GreaterThanValidatorTest.class,
        LengthValidatorTest.class,
        LessThanValidatorTest.class,
        MustValidatorTest.class,
        NestedValidatorTest.class,
        NotEmptyValidatorTest.class,
        NotNullValidatorTest.class,
        NullValidatorTest.class,
        OnlyIfValidatorTest.class,
        UnlessValidatorTest.class,
        WithMessageTest.class
    };

    private TestRunner() {
    }

    public static void main(String[] args) {
        List<String> failures = new ArrayList<>();
        int executed = 0;
        for (Class<?> testClass : TEST_CLASSES) {
            Object instance;
            try {
                instance = testClass.getDeclaredConstructor().newInstance();
            } catch (ReflectiveOperationException e) {
                failures.add(testClass.getName() + ": unable to instantiate - " + e.getMessage());
                continue;
            }

            for (Method method : testClass.getDeclaredMethods()) {
                if (!method.isAnnotationPresent(Test.class)) {
                    continue;
                }

                executed++;
                try {
                    method.setAccessible(true);
                    method.invoke(instance);
                } catch (InvocationTargetException ite) {
                    Throwable cause = ite.getCause();
                    failures.add(testClass.getSimpleName() + "." + method.getName() +
                            " failed: " + (cause != null ? cause : ite));
                } catch (ReflectiveOperationException e) {
                    failures.add(testClass.getSimpleName() + "." + method.getName() +
                            " failed: " + e.getMessage());
                }
            }
        }

        if (failures.isEmpty()) {
            System.out.println("Executed " + executed + " tests successfully.");
        } else {
            System.err.println("Executed " + executed + " tests with " + failures.size() + " failure(s):");
            for (String failure : failures) {
                System.err.println(" - " + failure);
            }
            System.exit(1);
        }
    }
}
