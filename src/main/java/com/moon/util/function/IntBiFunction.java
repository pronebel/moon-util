package com.moon.util.function;

/**
 * @author benshaoye
 * @date 2018/9/12
 */
@FunctionalInterface
public interface IntBiFunction<T, R> {
    /**
     * Applies this function to the given arguments.
     *
     * @param value
     * @param obj
     * @return
     */
    R apply(int value, T obj);
}
