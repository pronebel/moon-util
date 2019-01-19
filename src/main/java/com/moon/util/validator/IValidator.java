package com.moon.util.validator;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author benshaoye
 */
interface IValidator<T, IMPL> {
    /**
     * 前置条件，总是返回一个新对象
     *
     * @param tester
     * @return
     */
    IMPL when(Predicate<T> tester);

    /**
     * 前置条件内执行
     *
     * @param consumer
     * @return
     */
    IMPL ifWhen(Consumer<T> consumer);

    /**
     * 结束条件
     *
     * @return
     */
    IMPL end();

    /**
     * 返回对象
     *
     * @return
     */
    T getValue();

    /**
     * 要求符合指定验证规则，使用指定错误信息
     *
     * @param tester
     * @param message
     * @return
     */
    IMPL require(Predicate<? super T> tester, String message);

    /**
     * 要求符合指定验证规则
     *
     * @param tester
     * @return
     */
    default IMPL require(Predicate<? super T> tester) {
        return require(tester, Value.NONE);
    }
}
