package com.moon.util.assertions;

import com.moon.lang.Executable;

/**
 * @author benshaoye
 */
interface AssertionsThrows extends AssertionsFail {
    /**
     * 是否会抛出异常
     *
     * @param executor
     * @return
     */
    default boolean assertThrows(Executable executor) {
        return assertThrows(Throwable.class, executor);
    }

    /**
     * 是否会抛出能匹配指定类型的异常
     *
     * @param throwType
     * @param executor
     * @param <T>
     * @return
     */
    default <T extends Throwable> boolean assertThrows(Class<T> throwType, Executable executor) {
        try {
            executor.execute();
        } catch (Throwable t) {
            if (throwType.isInstance(t)) {
                return success("Test result: {}\n\tThrows Type: {}\n\tMessage    : {}",
                    true, t.getClass(), t.getMessage());
            } else {
                return success("Test result: {}\n\tThrows Type: {}\n\tMessage    : {}",
                    false, t.getClass(), t.getMessage());
            }
        }
        return success("Test result: {}\n\tExpect type: {}", false, throwType);
    }

    /**
     * 运行是是否会抛出包含指定类型的异常
     *
     * @param causeType
     * @param executor
     * @param <T>
     * @return
     */
    default <T extends Throwable> boolean assertHasCauseOfType(Class<T> causeType, Executable executor) {
        try {
            executor.execute();
        } catch (Throwable t) {
            for (; t != null; t = t.getCause()) {
                if (causeType.isInstance(t)) {
                    return success("Test result: {}\n\tThrows Type: {}\n\tMessage    : {}",
                        true, t.getClass(), t.getMessage());
                }
            }
        }
        return success("Test result: {}\n\tExpect type: {}", false, causeType);
    }
}
