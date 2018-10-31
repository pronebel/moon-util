package com.moon.util;

import com.moon.enums.CollectionEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * @author benshaoye
 * @date 2018/9/15
 */
public final class FilterUtil {

    private FilterUtil() {
        noInstanceError();
    }

    /*
     * --------------------------------------------------------------
     * find one
     * --------------------------------------------------------------
     */

    /**
     * 找出符合条件的第一项，没有匹配项抛出异常
     *
     * @param ts
     * @param tester
     * @param <T>
     * @return
     */
    public static <T> T requireFirst(T[] ts, Predicate<T> tester) {
        for (int i = 0, len = ts.length; i < len; i++) {
            if (tester.test(ts[i])) {
                return ts[i];
            }
        }
        throw new NoSuchItemException();
    }

    public static <T> T requireLast(T[] ts, Predicate<T> tester) {
        for (int i = ts.length - 1; i > -1; i--) {
            if (tester.test(ts[i])) {
                return ts[i];
            }
        }
        throw new NoSuchItemException();
    }

    /**
     * 找出符合条件的第一项，任何情况导致找不到匹配项都返回 null
     *
     * @param ts
     * @param tester
     * @param <T>
     * @return
     */
    public static <T> T nullableFirst(T[] ts, Predicate<T> tester) {
        if (ts != null) {
            for (int i = 0, len = ts.length; i < len; i++) {
                if (tester.test(ts[i])) {
                    return ts[i];
                }
            }
        }
        return null;
    }

    public static <T> T nullableLast(T[] ts, Predicate<T> tester) {
        if (ts != null) {
            for (int i = ts.length - 1; i > -1; i--) {
                if (tester.test(ts[i])) {
                    return ts[i];
                }
            }
        }
        return null;
    }

    /**
     * 找出符合条件的第一项，没有匹配项抛出异常
     *
     * @param collect
     * @param tester
     * @param <T>
     * @return
     */
    public static <T> T requireFirst(Iterable<T> collect, Predicate<T> tester) {
        for (T item : collect) {
            if (tester.test(item)) {
                return item;
            }
        }
        throw new NoSuchItemException();
    }

    /**
     * 找出符合条件的第一项，任何情况导致找不到匹配项都返回 null
     *
     * @param collect
     * @param tester
     * @param <T>
     * @return
     */
    public static <T> T nullableFirst(Iterable<T> collect, Predicate<T> tester) {
        if (collect != null) {
            for (T item : collect) {
                if (tester.test(item)) {
                    return item;
                }
            }
        }
        return null;
    }

    /**
     * 找出符合条件的第一项，没有匹配项抛出异常
     *
     * @param iterator
     * @param tester
     * @param <T>
     * @return
     */
    public static <T> T requireFirst(Iterator<T> iterator, Predicate<T> tester) {
        while (iterator.hasNext()) {
            T item = iterator.next();
            if (tester.test(item)) {
                return item;
            }
        }
        throw new NoSuchItemException();
    }

    /**
     * 找出符合条件的第一项，任何情况导致找不到匹配项都返回 null
     *
     * @param iterator
     * @param tester
     * @param <T>
     * @return
     */
    public static <T> T nullableFirst(Iterator<T> iterator, Predicate<T> tester) {
        if (iterator != null) {
            while (iterator.hasNext()) {
                T item = iterator.next();
                if (tester.test(item)) {
                    return item;
                }
            }
        }
        return null;
    }

    /*
     * --------------------------------------------------------------
     * match
     * --------------------------------------------------------------
     */

    /**
     * 测试数组中至少有一项符合匹配
     *
     * @param ts
     * @param tester
     * @param <T>
     * @return
     */
    public static <T> boolean matchAny(T[] ts, Predicate<T> tester) {
        if (ts != null) {
            for (T item : ts) {
                if (tester.test(item)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 测试集合中至少有一项符合匹配
     *
     * @param iterable
     * @param tester
     * @param <T>
     * @return
     */
    public static <T> boolean matchAny(Iterable<T> iterable, Predicate<T> tester) {
        if (iterable != null) {
            for (T item : iterable) {
                if (tester.test(item)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 测试数组中所有项符合匹配
     *
     * @param ts
     * @param tester
     * @param <T>
     * @return
     */
    public static <T> boolean matchAll(T[] ts, Predicate<T> tester) {
        if (ts == null) {
            return false;
        } else {
            for (T item : ts) {
                if (!tester.test(item)) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * 测试集合中所有项符合匹配
     *
     * @param iterable
     * @param tester
     * @param <T>
     * @return
     */
    public static <T> boolean matchAll(Iterable<T> iterable, Predicate<T> tester) {
        if (iterable != null) {
            for (T item : iterable) {
                if (!tester.test(item)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /*
     * --------------------------------------------------------------
     * filter
     * --------------------------------------------------------------
     */

    /**
     * 按条件 tester 过滤出集合 valuesList 中的匹配项
     *
     * @param list
     * @param tester
     * @param <E>
     * @param <L>
     * @return
     */
    public static <E, L extends List<E>> List<E> filter(L list, Predicate<E> tester) {
        final Supplier supplier = CollectionEnum.getOrDefault(list, CollectionEnum.ArrayList);
        return (List) filter(list, tester, supplier);
    }

    public static <E, L extends List<E>> List<E> multiplyFilter(L list, Predicate<E>... testers) {
        final Supplier supplier = CollectionEnum.getOrDefault(list, CollectionEnum.HashSet);
        return (List) multiplyFilter(list, supplier, testers);
    }

    /**
     * 按条件 tester 过滤出集合 set 中的匹配项
     *
     * @param set
     * @param tester
     * @param <E>
     * @param <S>
     * @return
     */
    public static <E, S extends Set<E>> Set<E> filter(S set, Predicate<E> tester) {
        final Supplier supplier = CollectionEnum.getOrDefault(set, CollectionEnum.HashSet);
        return (Set) filter(set, tester, supplier);
    }

    public static <E, S extends Set<E>> Set<E> multiplyFilter(S set, Predicate<E>... testers) {
        final Supplier supplier = CollectionEnum.getOrDefault(set, CollectionEnum.HashSet);
        return (Set) multiplyFilter(set, supplier, testers);
    }

    /**
     * 按条件 tester 过滤出集合 collect 中的匹配项
     *
     * @param collect
     * @param tester
     * @param resultContainerSupplier 符合过滤条件项容器构造器
     * @param <E>
     * @param <C>
     * @param <CR>
     * @return
     */
    public static <E, C extends Collection<E>, CR extends Collection<E>>

    CR filter(C collect, Predicate<E> tester, Supplier<CR> resultContainerSupplier) {
        return filter(collect, tester, resultContainerSupplier.get());
    }

    public static <E, C extends Collection<E>, CR extends Collection<E>>

    CR multiplyFilter(C collect, Supplier<CR> resultContainerSupplier, Predicate<E>... testers) {
        return multiplyFilter(collect, resultContainerSupplier.get(), testers);
    }

    /**
     * 按条件 tester 过滤出集合 collect 中的匹配项，添加到集合 container 并返回
     *
     * @param collect
     * @param tester
     * @param container 符合过滤条件的容器
     * @param <E>
     * @param <C>
     * @param <CR>
     * @return 返回提供的容器 container
     */
    public static <E, C extends Iterable<E>, CR extends Collection<E>>

    CR filter(C collect, Predicate<E> tester, CR container) {
        if (collect != null) {
            for (E item : collect) {
                if (tester.test(item)) {
                    container.add(item);
                }
            }
        }
        return container;
    }

    public static <E, C extends Iterable<E>, CR extends Collection<E>>

    CR multiplyFilter(C collect, CR container, Predicate<E>... testers) {
        if (collect != null) {
            int i;
            final int len = testers.length;
            if (len == 0) {
                if (collect instanceof Collection) {
                    container.addAll((Collection) collect);
                } else {
                    for (E item : collect) {
                        container.add(item);
                    }
                }
                return container;
            }
            boolean matched;
            Predicate<E> tester;
            outer:
            for (E item : collect) {
                matched = true;
                inner:
                for (i = 0; i < len; i++) {
                    tester = testers[i];
                    if (!tester.test(item)) {
                        matched = false;
                        break inner;
                    }
                }
                if (matched) {
                    container.add(item);
                }
            }
        }
        return container;
    }

    /*
     * --------------------------------------------------------------
     * consumer present
     * --------------------------------------------------------------
     */

    public static <T> void forEachMatches(
        Collection<T> collect, Predicate<T> tester, Consumer<T> consumer) {
        if (collect != null) {
            for (T item : collect) {
                if (tester.test(item)) {
                    consumer.accept(item);
                }
            }
        }
    }

    public static <T> void forEachMatches(
        T[] ts, Predicate<T> tester, Consumer<T> consumer) {
        final int length = ts == null ? 0 : ts.length;
        for (int i = 0; i < length; i++) {
            T item = ts[i];
            if (tester.test(item)) {
                consumer.accept(item);
            }
        }
    }
}