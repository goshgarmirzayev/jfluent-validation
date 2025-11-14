package com.fluentrules.core;

import java.io.Serializable;
import java.util.function.Function;

/**
 * Serializable functional interface used to capture property access expressions for validation rules.
 *
 * <p>Provided as part of the JFluent Validation toolkit maintained by Goshgar Mirzayev
 * ({@code jgoshgarmirzayev@gmail.com}).</p>
 *
 * @param <T> type of the object supplying the property
 * @param <R> type returned by the property accessor
 * @author Goshgar Mirzayev
 */
@FunctionalInterface
public interface PropertyFunction<T, R> extends Function<T, R>, Serializable {
}
