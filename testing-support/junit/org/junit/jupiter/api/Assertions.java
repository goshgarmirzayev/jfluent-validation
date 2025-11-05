package org.junit.jupiter.api;

import java.util.Objects;

/**
 * Tiny subset of JUnit Jupiter assertions so the offline build can execute the
 * library test suite without downloading external artifacts.
 */
public final class Assertions {
    private Assertions() {
    }

    public static void assertTrue(boolean condition) {
        assertTrue(condition, "Expected condition to be true");
    }

    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void assertFalse(boolean condition) {
        assertFalse(condition, "Expected condition to be false");
    }

    public static void assertFalse(boolean condition, String message) {
        if (condition) {
            throw new AssertionError(message);
        }
    }

    public static void assertEquals(Object expected, Object actual) {
        assertEquals(expected, actual, () -> "Expected <" + expected + "> but was <" + actual + ">");
    }

    public static void assertEquals(Object expected, Object actual, String message) {
        assertEquals(expected, actual, () -> message);
    }

    public static void assertEquals(Object expected, Object actual, MessageSupplier messageSupplier) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError(messageSupplier.get());
        }
    }

    @FunctionalInterface
    public interface MessageSupplier {
        String get();
    }
}
