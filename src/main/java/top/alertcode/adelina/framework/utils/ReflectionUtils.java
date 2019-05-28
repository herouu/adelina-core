package top.alertcode.adelina.framework.utils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.*;
import java.util.*;

/**
 * Created by gizmo on 15/12/11.
 */
public final class ReflectionUtils {
    public static final String CGLIB_CLASS_SEPARATOR = "$$";

    private ReflectionUtils() {
    }

    public static Object invokeGetterMethod(Object obj, String propertyName) {
        String getterMethodName = "get" + StringUtils.capitalize(propertyName);
        return invokeMethod(obj, getterMethodName, new Class[]{},
                new Object[]{});
    }

    public static void invokeSetterMethod(Object obj, String propertyName,
                                          Object value) {
        invokeSetterMethod(obj, propertyName, value, null);
    }

    public static void invokeSetterMethod(Object obj, String propertyName,
                                          Object value, Class<?> propertyType) {
        Class<?> type = propertyType != null ? propertyType : value.getClass();
        String setterMethodName = "set" + StringUtils.capitalize(propertyName);
        invokeMethod(obj, setterMethodName, new Class[]{type},
                new Object[]{value});
    }

    public static Object getFieldValue(final Object obj, final String fieldName) {
        Field field = getAccessibleField(obj, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field ["
                    + fieldName + "] on target [" + obj + "]");
        }

        Object result = null;
        try {
            result = field.get(obj);
        } catch (IllegalAccessException e) {
        }
        return result;
    }

    public static void setFieldValue(final Object obj, final String fieldName,
                                     final Object value) {
        Field field = getAccessibleField(obj, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field ["
                    + fieldName + "] on target [" + obj + "]");
        }

        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
        }
    }

    public static Field getAccessibleField(final Object obj, final String fieldName) {
        AssertUtils.notNull(obj, "object不能为空");

        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
            }
        }

        return null;
    }

    public static Class<?> getUserClass(Class<?> clazz) {
        if (clazz != null && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null && !Object.class.equals(superClass)) {
                return superClass;
            }
        }
        return clazz;
    }

    public static Object invokeMethod(final Object obj,
                                      final String methodName, final Class<?>[] parameterTypes,
                                      final Object[] args) {
        Method method = getAccessibleMethod(obj, methodName, parameterTypes);
        if (method == null) {
            throw new IllegalArgumentException("Could not find method ["
                    + methodName + "] on target [" + obj + "]");
        }

        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw convertReflectionExceptionToUnchecked(e);
        }
    }

    public static Method getAccessibleMethod(final Object obj,
                                             final String methodName, final Class<?>... parameterTypes) {
        AssertUtils.notNull(obj, "object不能为空");

        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
                .getSuperclass()) {
            try {
                Method method = superClass.getDeclaredMethod(methodName,
                        parameterTypes);

                method.setAccessible(true);

                return method;

            } catch (NoSuchMethodException e) {// NOSONAR
                // Method不在当前类定义,继续向上转型
            }
        }
        return null;
    }

    public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    public static Class getSuperClassGenricType(final Class clazz,
                                                final int index) {

        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }

        return (Class) params[index];
    }

    public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
        if (e instanceof IllegalAccessException
                || e instanceof IllegalArgumentException
                || e instanceof NoSuchMethodException) {
            return new IllegalArgumentException("Reflection Exception.", e);
        } else if (e instanceof InvocationTargetException) {
            return new RuntimeException("Reflection Exception.",
                    ((InvocationTargetException) e).getTargetException());
        } else if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        return new RuntimeException("Unexpected Checked Exception.", e);
    }

    public static <T> T newInstance(Class<T> cls) {
        T r = null;
        try {
            r = cls.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("生成类实例失败！");
        }
        return r;
    }

    public static <K, V> Map<K, V> extractToMap(final Collection<?> collection, final String keyPropertyName, final String valuePropertyName) {
        Map<K, V> map = new HashMap<K, V>();
        if (CollectionUtils.isEmpty(collection)) {
            return map;
        }
        Iterator<?> iterator = collection.iterator();
        Object value = iterator.next();
        Field keyField = getExistAccessibleField(value, keyPropertyName);
        Field valueField = getExistAccessibleField(value, valuePropertyName);
        try {
            map.put((K) keyField.get(value), (V) valueField.get(value));
            while (iterator.hasNext()) {
                Object v = iterator.next();
                map.put((K) keyField.get(v), (V) valueField.get(v));
            }
        } catch (Exception e) {
            throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
        }
        return map;
    }

    public static <K, V> Map<K, V> tranToMap(final Collection<V> collection, final String keyPropertyName) {
        Map<K, V> map = new HashMap<K, V>();
        if (CollectionUtils.isEmpty(collection)) {
            return map;
        }
        Iterator<V> iterator = collection.iterator();
        V value = iterator.next();
        Field keyField = getExistAccessibleField(value, keyPropertyName);
        try {
            map.put((K) keyField.get(value), value);
            while (iterator.hasNext()) {
                V v = iterator.next();
                map.put((K) keyField.get(v), v);
            }
        } catch (Exception e) {
            throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
        }
        return map;
    }

    public static <K> List<K> extractToList(final Collection<?> collection, final String propertyName) {
        List<K> list = new ArrayList<K>();
        if (CollectionUtils.isEmpty(collection)) {
            return list;
        }
        Iterator<?> iterator = collection.iterator();
        Object value = iterator.next();
        Field propertyField = getExistAccessibleField(value, propertyName);
        try {
            list.add((K) propertyField.get(value));
            while (iterator.hasNext()) {
                Object v = iterator.next();
                list.add((K) propertyField.get(v));
            }
        } catch (Exception e) {
            throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
        }
        return list;
    }

    public static Field getExistAccessibleField(Object value, String propertyName) {
        Field propertyField = getAccessibleField(value, propertyName);
        if (propertyField == null) {
            throw new RuntimeException(value.getClass().getSimpleName() + " 没有【" + propertyName + "】属性");
        }
        return propertyField;
    }
}
