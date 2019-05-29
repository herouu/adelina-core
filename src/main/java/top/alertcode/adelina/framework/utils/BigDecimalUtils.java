package top.alertcode.adelina.framework.utils;

import java.math.BigDecimal;

/**
 * Created by gizmo on 15/12/11.
 *
 * @author Bob
 * @version $Id: $Id
 */
public final class BigDecimalUtils {
    private BigDecimalUtils() {
    }

    /**
     * <p>compare.</p>
     *
     * @param a a {@link java.math.BigDecimal} object.
     * @param b a {@link java.math.BigDecimal} object.
     * @return a int.
     */
    public static int compare(BigDecimal a, BigDecimal b) {
        if (a == null && b == null) {
            return 0;
        }

        if (a == null) {
            return -1;
        }

        if (b == null) {
            return 1;
        }

        return a.compareTo(b);
    }

    /**
     * <p>greater.</p>
     *
     * @param a a {@link java.math.BigDecimal} object.
     * @param b a {@link java.math.BigDecimal} object.
     * @return a boolean.
     */
    public static boolean greater(BigDecimal a, BigDecimal b) {
        return compare(a, b) == 1;
    }

    /**
     * <p>equals.</p>
     *
     * @param a a {@link java.math.BigDecimal} object.
     * @param b a {@link java.math.BigDecimal} object.
     * @return a boolean.
     */
    public static boolean equals(BigDecimal a, BigDecimal b) {
        return compare(a, b) == 0;
    }

    /**
     * <p>less.</p>
     *
     * @param a a {@link java.math.BigDecimal} object.
     * @param b a {@link java.math.BigDecimal} object.
     * @return a boolean.
     */
    public static boolean less(BigDecimal a, BigDecimal b) {
        return compare(a, b) == -1;
    }

    /**
     * <p>max.</p>
     *
     * @param a a {@link java.math.BigDecimal} object.
     * @param b a {@link java.math.BigDecimal} object.
     * @return a {@link java.math.BigDecimal} object.
     */
    public static BigDecimal max(BigDecimal a, BigDecimal b) {
        if (compare(a, b) >= 0) {
            return a;
        }
        return b;
    }


    /**
     * <p>min.</p>
     *
     * @param a a {@link java.math.BigDecimal} object.
     * @param b a {@link java.math.BigDecimal} object.
     * @return a {@link java.math.BigDecimal} object.
     */
    public static BigDecimal min(BigDecimal a, BigDecimal b) {
        if (compare(a, b) >= 0) {
            return b;
        }
        return a;
    }

}
