package com.moon.util;

import com.moon.beans.BeanInfoUtil;
import com.moon.lang.ThrowUtil;
import com.moon.lang.reflect.ConstructorUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.moon.beans.BeanInfoUtil.getFieldDescriptorsMap;
import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * 映射器：提供实体到 Map，实体到另一个实体，实体到类，Map 到实体之间的映射
 *
 * @author benshaoye
 * @see com.moon.sql.ResultSetUtil 提供 SQL 查询结果集到实体、数组、Map 的映射
 */
public final class MapperUtil {
    private MapperUtil() {
        noInstanceError();
    }

    /*
     * ---------------------------------------------------------------------------
     * mapper
     * ---------------------------------------------------------------------------
     */

    public final static Map<String, Object> toMap(Object bean) {
        return toMap(bean, new HashMap(16));
    }

    public final static Map<String, Object> toMap(Object bean, Map container) {
        if (bean != null) {
            getFieldDescriptorsMap(bean.getClass()).forEach((name, d) ->
                container.put(name, d.getValueIfPresent(bean, true)));
        }
        return container;
    }

    public final static <T> T toInstance(Map<String, ?> data, Class<T> type) {
        return overrideImpl(data, ConstructorUtil.newInstance(type, true), type);
    }

    public final static <T, E> T toInstance(E data, Class<T> type) {
        return override(data, ConstructorUtil.newInstance(type, true));
    }

    public final static <T> T override(Map<String, ?> data, T bean) {
        return overrideImpl(data, bean, (Class<T>) bean.getClass());
    }

    public final static <T, E> E override(T t, E e) {
        final Class targetType = e.getClass();

        getFieldDescriptorsMap(t.getClass()).forEach((name, srcDesc) ->
            BeanInfoUtil.ifSetterExecutorPresent(targetType, name, setDesc ->
                setDesc.setValue(e, srcDesc.getValue(t, true), true)));
        return e;
    }

    /*
     * ---------------------------------------------------------------------------
     * for each mapper
     * ---------------------------------------------------------------------------
     */

    public final static <T> List<Map<String, Object>> forEachToMap(List<T> beanList) {
        List<Map<String, Object>> result = new ArrayList<>();
        IteratorUtil.forEach(beanList, item -> result.add(toMap(item)));
        return result;
    }

    /**
     * type 必须有一个空构造器
     *
     * @param dataList
     * @param type
     * @param <T>
     * @return
     */
    public final static <T> List<T> forEachToInstance(List<Map<String, Object>> dataList, Class<T> type) {
        List<T> result = new ArrayList<>();
        IteratorUtil.forEach(dataList, itemMap -> result.add(toInstance(itemMap, type)));
        return result;
    }

    public final static <T, E> List<T> forEachToOther(List<E> dataList, Class<T> type) {
        List<T> result = new ArrayList<>();
        IteratorUtil.forEach(dataList, item -> result.add(toInstance(item, type)));
        return result;
    }

    private final static Object mapper(Class type) {
        return ThrowUtil.rejectAccessError();
    }

    private final static Object mapper(Class type, TypeConverter converter) {
        return ThrowUtil.rejectAccessError();
    }

    /*
     * ---------------------------------------------------------------------------
     * inner mapper
     * ---------------------------------------------------------------------------
     */

    private final static <T> T overrideImpl(Map<String, ?> data, T bean, Class<T> type) {
        getFieldDescriptorsMap(type).forEach((name, desc) ->
            desc.setValueIfPresent(bean, data.get(name), true));
        return bean;
    }
}
