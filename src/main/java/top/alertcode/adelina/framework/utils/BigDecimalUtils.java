package top.alertcode.adelina.framework.utils;

import java.math.BigDecimal;

/**
 * Created by gizmo on 15/12/11.
 */
public final class BigDecimalUtils {
    private BigDecimalUtils() {
    }

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

    public static boolean greater(BigDecimal a, BigDecimal b) {
        return compare(a, b) == 1;
    }

    public static boolean equals(BigDecimal a, BigDecimal b) {
        return compare(a, b) == 0;
    }

    public static boolean less(BigDecimal a, BigDecimal b) {
        return compare(a, b) == -1;
    }

    public static BigDecimal max(BigDecimal a, BigDecimal b) {
        if (compare(a, b) >= 0) {
            return a;
        }
        return b;
    }


    public static BigDecimal min(BigDecimal a, BigDecimal b) {
        if (compare(a, b) >= 0) {
            return b;
        }
        return a;
    }

}
