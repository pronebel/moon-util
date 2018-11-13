package com.moon.util.compute.core;

import com.moon.lang.CharUtil;
import com.moon.lang.SupportUtil;
import com.moon.lang.ref.IntAccessor;

import static com.moon.lang.ThrowUtil.noInstanceError;
import static com.moon.util.compute.core.Constants.DOUBLE_QUOTE;
import static com.moon.util.compute.core.Constants.SINGLE_QUOTE;

/**
 * @author benshaoye
 */
public class ParseUtil {
    /**
     * @throws AssertionError 不可实例化
     */
    protected ParseUtil() {
        noInstanceError();
    }

    /*
     * -----------------------------------------------
     * assertions
     * -----------------------------------------------
     */

    final static boolean isNum(int value) {
        return value > 47 && value < 58;
    }

    final static boolean isVar(int value) {
        return CharUtil.isLetter(value)
            || value == '$' || value == '_'
            || CharUtil.isChinese(value);
    }

    final static boolean isStr(int value) {
        return value == SINGLE_QUOTE || value == DOUBLE_QUOTE;
    }

    /*
     * -----------------------------------------------
     * tools
     * -----------------------------------------------
     */

    final static void setIndexer(IntAccessor indexer, int index, int len) {
        indexer.set(index < len ? index : len);
    }

    final static String toStr(char[] chars, int len) {
        return new String(chars, 0, len);
    }

    /**
     * 运行之前 indexer 指向起始索引
     * 运行完成之后 indexer 指向下一个非空白字符索引或字符串长度
     * 返回当前非空白字符或 -1
     *
     * @param chars
     * @param indexer
     * @param len
     * @return
     */
    final static int skipWhitespaces(char[] chars, IntAccessor indexer, final int len) {
        return SupportUtil.skipWhitespaces(chars, indexer, len);
    }

    /*
     * -------------------------------------------------------
     * assertions
     * -------------------------------------------------------
     */

    final static <T> T throwErr(char[] chars, IntAccessor indexer) {
        return SupportUtil.throwErr(chars, indexer);
    }

    final static void assertTrue(boolean value, char[] chars, IntAccessor indexer) {
        if (value) {
            return;
        }
        throwErr(chars, indexer);
    }

    final static void assertFalse(boolean value, char[] chars, IntAccessor indexer) {
        if (value) {
            throwErr(chars, indexer);
        }
    }

    /*
     * -----------------------------------------------
     * api
     * -----------------------------------------------
     */

    /**
     * 解析运行一个字符串表达式的入口方法
     *
     * @param expression
     * @param data
     * @return
     */
    protected final static Object run0(String expression, Object data) {
        return ParseCore.parse(expression).use(data);
    }

    /**
     * 解析运行一个包含字符串表达式的入口方法
     *
     * @param expression
     * @param delimiters 指定分隔符
     * @param data       如表达式中有变量，变量则从 data 中读取值
     * @return 返回运行结果
     */
    protected final static Object parseRun0(String expression, String[] delimiters, Object data) {
        return ParseDelimiters.parse(expression, delimiters).use(data);
    }
}
