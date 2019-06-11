package top.alertcode.adelina.framework.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Map;

/**
 * <p>AssertUtils class.</p>
 *
 * @author Bob
 * @version $Id: $Id
 */
public final class AssertUtils extends Assert {
    private AssertUtils() {
    }

    /**
     * {@inheritDoc}
     *
     * <p>isTrue.</p>
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /** {@inheritDoc} */
    @Deprecated
    public static void isTrue(boolean expression) {
        isTrue(expression, "[Assertion failed] - this expression must be true");
    }

    /**
     * <p>isFalse.</p>
     *
     * @param expression a boolean.
     * @param message a {@link java.lang.String} object.
     */
    public static void isFalse(boolean expression, String message) {
        if (expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * <p>isFalse.</p>
     *
     * @param expression a boolean.
     */
    public static void isFalse(boolean expression) {
        isFalse(expression, "[Assertion failed] - this expression must be true");
    }

    /**
     * {@inheritDoc}
     */
    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new IllegalArgumentException(message);
        }
    }

    /** {@inheritDoc} */
    @Deprecated
    public static void isNull(Object object) {
        isNull(object, "[Assertion failed] - the object argument must be null");
    }

    /**
     * {@inheritDoc}
     *
     * <p>notNull.</p>
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /** {@inheritDoc} */
    @Deprecated
    public static void notNull(Object object) {
        notNull(object, "[Assertion failed] - this argument is required; it must not be null");
    }

    /**
     * <p>notBlank.</p>
     *
     * @param text a {@link java.lang.String} object.
     * @param message a {@link java.lang.String} object.
     */
    public static void notBlank(String text, String message) {
        if (StringUtils.isBlank(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * <p>notBlank.</p>
     *
     * @param text a {@link java.lang.String} object.
     */
    public static void notBlank(String text) {
        notBlank(text,
                "[Assertion failed] - this String argument must have length; it must not be null or empty");
    }

    /**
     * <p>notContain.</p>
     *
     * @param textToSearch a {@link java.lang.String} object.
     * @param substring a {@link java.lang.String} object.
     * @param message a {@link java.lang.String} object.
     */
    public static void notContain(String textToSearch, String substring, String message) {
        if (StringUtils.isNotBlank(textToSearch) && StringUtils.isNotBlank(substring) &&
                textToSearch.contains(substring)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * <p>notContain.</p>
     *
     * @param textToSearch a {@link java.lang.String} object.
     * @param substring a {@link java.lang.String} object.
     */
    public static void notContain(String textToSearch, String substring) {
        notContain(textToSearch, substring,
                "[Assertion failed] - this String argument must not contain the substring [" + substring + "]");
    }

    /**
     * {@inheritDoc}
     *
     * @param array an array of {@link java.lang.Object} objects.
     * @param message a {@link java.lang.String} object.
     */
    public static void notEmpty(Object[] array, String message) {
        if (ArrayUtils.isEmpty(array)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * <p>notEmpty.</p>
     *
     * @param array an array of {@link java.lang.Object} objects.
     */
    @Deprecated
    public static void notEmpty(Object[] array) {
        notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
    }

    /**
     * {@inheritDoc}
     *
     * @param array an array of {@link java.lang.Object} objects.
     * @param message a {@link java.lang.String} object.
     */
    public static void noNullElements(Object[] array, String message) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new IllegalArgumentException(message);
                }
            }
        }
    }

    /**
     * <p>noNullElements.</p>
     *
     * @param array an array of {@link java.lang.Object} objects.
     */
    @Deprecated
    public static void noNullElements(Object[] array) {
        noNullElements(array, "[Assertion failed] - this array must not contain any null elements");
    }

    /**
     * {@inheritDoc}
     *
     * @param collection a {@link java.util.Collection} object.
     * @param message a {@link java.lang.String} object.
     */
    public static void notEmpty(Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>notEmpty.</p>
     *
     * @param collection a {@link java.util.Collection} object.
     */
    @Deprecated
    public static void notEmpty(Collection<?> collection) {
        notEmpty(collection,
                "[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
    }

    /**
     * {@inheritDoc}
     *
     * @param map a {@link java.util.Map} object.
     * @param message a {@link java.lang.String} object.
     */
    public static void notEmpty(Map<?, ?> map, String message) {
        if (MapUtils.isEmpty(map)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * <p>notEmpty.</p>
     *
     * @param map a {@link java.util.Map} object.
     */
    @Deprecated
    public static void notEmpty(Map<?, ?> map) {
        notEmpty(map, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
    }

    /** {@inheritDoc} */
    public static void isInstanceOf(Class<?> clazz, Object obj) {
        isInstanceOf(clazz, obj, "");
    }

    /**
     * {@inheritDoc}
     *
     * <p>isInstanceOf.</p>
     *
     * @param type a {@link java.lang.Class} object.
     * @param obj a {@link java.lang.Object} object.
     * @param message a {@link java.lang.String} object.
     */
    public static void isInstanceOf(Class<?> type, Object obj, String message) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            throw new IllegalArgumentException(
                    (StringUtils.isNotBlank(message) ? message + " " : "") +
                            "Object of class [" + (obj != null ? obj.getClass().getName() : "null") +
                            "] must be an instance of " + type);
        }
    }

    /** {@inheritDoc} */
    public static void isAssignable(Class<?> superType, Class<?> subType) {
        isAssignable(superType, subType, "");
    }

    /**
     * {@inheritDoc}
     *
     * <p>isAssignable.</p>
     *
     * @param superType a {@link java.lang.Class} object.
     * @param subType a {@link java.lang.Class} object.
     * @param message a {@link java.lang.String} object.
     */
    public static void isAssignable(Class<?> superType, Class<?> subType, String message) {
        notNull(superType, "Type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw new IllegalArgumentException(message + subType + " is not assignable to " + superType);
        }
    }

    /**
     * <p>state.</p>
     *
     * @param expression a boolean.
     * @param message a {@link java.lang.String} object.
     */
    public static void state(boolean expression, String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }

    /** {@inheritDoc} */
    @Deprecated
    public static void state(boolean expression) {
        state(expression, "[Assertion failed] - this state invariant must be true");
    }
}
