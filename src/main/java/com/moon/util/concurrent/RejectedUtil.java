package com.moon.util.concurrent;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;

import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * @author benshaoye
 */
public final class RejectedUtil {
    private RejectedUtil() {
        noInstanceError();
    }

    /**
     * 拒绝并丢弃
     *
     * @return
     */
    public static final RejectedExecutionHandler abort() {
        return new AbortPolicy();
    }

    /**
     * 调用方自己运行
     *
     * @return
     */
    public static final RejectedExecutionHandler callerRun() {
        return new CallerRunsPolicy();
    }

    /**
     * 丢弃最老任务
     *
     * @return
     */
    public static final RejectedExecutionHandler discardOldest() {
        return new DiscardOldestPolicy();
    }

    /**
     * 丢弃
     *
     * @return
     */
    public static final RejectedExecutionHandler discard() {
        return new DiscardPolicy();
    }
}
