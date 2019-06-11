package top.alertcode.adelina.framework.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.*;
import java.util.*;

/**
 * Created by gizmo on 15/12/11.
 *
 * @author Bob
 * @version $Id: $Id
 */
public final class ReflectionUtils {
    /**
     * Constant <code>CGLIB_CLASS_SEPARATOR="$$"</code>
     */
    public static final String CGLIB_CLASS_SEPARATOR = "$$";

    private ReflectionUtils() {
    }

    /**
     * <p>invokeGetterMethod.</p>
     *
     * @param obj a {@link java.lang.Object} object.
     * @param propertyName a {@link java.lang.String} object.
     * @return a {@link java.lang.Object} object.
     */
    public static Object invokeGetterMethod(Object obj, String propertyName) {
        String getterMethodName = "get" + StringUtils.capitalize(propertyName);
        return invokeMethod(obj, getterMethodName, new Class[]{},
                new Object[]{});
    }

    /**
     * <p>invokeSetterMethod.</p>
     *
     * @param obj a {@link java.lang.Object} object.
     * @param propertyName a {@link java.lang.String} object.
     * @param value a {@link java.lang.Object} object.
     */
    public static void invokeSetterMethod(Object obj, String propertyName,
                                          Object value) {
        invokeSetterMethod(obj, propertyName, value, null);
    }

    /**
     * <p>invokeSetterMethod.</p>
     *
     * @param obj a {@link java.lang.Object} object.
     * @param propertyName a {@link java.lang.String} object.
     * @param value a {@link java.lang.Object} object.
     * @param propertyType a {@link java.lang.Class} object.
     */
    public static void invokeSetterMethod(Object obj, String propertyName,
                                          Object value, Class<?> propertyType) {
        Class<?> type = propertyType != null ? propertyType : value.getClass();
        String setterMethodName = "set" + StringUtils.capitalize(propertyName);
        invokeMethod(obj, setterMethodName, new Class[]{type},
                new Object[]{value});
    }

    /**
     * <p>getFieldValue.</p>
     *
     * @param obj a {@link java.lang.Object} object.
     * @param fieldName a {@link java.lang.String} object.
     * @return a {@link java.lang.Object} object.
     */
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

    /**
     * <p>setFieldValue.</p>
     *
     * @param obj a {@link java.lang.Object} object.
     * @param fieldName a {@link java.lang.String} object.
     * @param value a {@link java.lang.Object} object.
     */
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

    /**
     * <p>getAccessibleField.</p>
     *
     * @param obj a {@link java.lang.Object} object.
     * @param fieldName a {@link java.lang.String} object.
     * @return a {@link java.lang.reflect.Field} object.
     */
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

    /**
     * <p>getUserClass.</p>
     *
     * @param clazz a {@link java.lang.Class} object.
     * @return a {@link java.lang.Class} object.
     */
    public static Class<?> getUserClass(Class<?> clazz) {
        if (clazz != null && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null && !Object.class.equals(superClass)) {
                return superClass;
            }
        }
        return clazz;
    }

    /**
     * <p>invokeMethod.</p>
     *
     * @param obj a {@link java.lang.Object} object.
     * @param methodName a {@link java.lang.String} object.
     * @param parameterTypes an array of {@link java.lang.Class} objects.
     * @param args an array of {@link java.lang.Object} objects.
     * @return a {@link java.lang.Object} object.
     */
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

    /**
     * <p>getAccessibleMethod.</p>
     *
     * @param obj a {@link java.lang.Object} object.
     * @param methodName a {@link java.lang.String} object.
     * @param parameterTypes a {@link java.lang.Class} object.
     * @return a {@link java.lang.reflect.Method} object.
     */
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
                // Method不在当前类定�?,继续向上转型
            }
        }
        return null;
    }

    /**
     * <p>getSuperClassGenricType.</p>
     *
     * @param clazz a {@link java.lang.Class} object.
     * @param <T> a T object.
     * @return a {@link java.lang.Class} object.
     */
    public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * <p>getSuperClassGenricType.</p>
     *
     * @param clazz a {@link java.lang.Class} object.
     * @param index a int.
     * @return a {@link java.lang.Class} object.
     */
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

    /**
     * <p>convertReflectionExceptionToUnchecked.</p>
     *
     * @param e a {@link java.lang.Exception} object.
     * @return a {@link java.lang.RuntimeException} object.
     */
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

    /**
     * <p>newInstance.</p>
     *
     * @param cls a {@link java.lang.Class} object.
     * @param <T> a T object.
     * @return a T object.
     */
    public static <T> T newInstance(Class<T> cls) {
        T r = null;
        try {
            r = cls.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("生成类实例失败！");
        }
        return r;
    }

    /**
     * <p>extractToMap.</p>
     *
     * @param collection a {@link java.util.Collection} object.
     * @param keyPropertyName a {@link java.lang.String} object.
     * @param valuePropertyName a {@link java.lang.String} object.
     * @param <K> a K object.
     * @param <V> a V object.
     * @return a {@link java.util.Map} object.
     */
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

    /**
     * <p>tranToMap.</p>
     *
     * @param collection a {@link java.util.Collection} object.
     * @param keyPropertyName a {@link java.lang.String} object.
     * @param <K> a K object.
     * @param <V> a V object.
     * @return a {@link java.util.Map} object.
     */
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

    /**
     * <p>extractToList.</p>
     *
     * @param collection a {@link java.util.Collection} object.
     * @param propertyName a {@link java.lang.String} object.
     * @param <K> a K object.
     * @return a {@link java.util.List} object.
     */
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

    /**
     * <p>getExistAccessibleField.</p>
     *
     * @param value a {@link java.lang.Object} object.
     * @param propertyName a {@link java.lang.String} object.
     * @return a {@link java.lang.reflect.Field} object.
     */
    public static Field getExistAccessibleField(Object value, String propertyName) {
        Field propertyField = getAccessibleField(value, propertyName);
        if (propertyField == null) {
            throw new RuntimeException(value.getClass().getSimpleName() + " 没有�?" + propertyName + "】属�?");
        }
        return propertyField;
    }
}
