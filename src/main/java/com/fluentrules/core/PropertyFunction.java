package com.fluentrules.core;

import java.io.Serializable;
import java.util.function.Function;

@FunctionalInterface
public interface PropertyFunction<T, R> extends Function<T, R>, Serializable {
}
