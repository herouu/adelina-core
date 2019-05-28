package top.alertcode.adelina.framework.utils;

/**
 * Created by gizmo on 15/12/11.
 */
public final class StringUtils extends org.apache.commons.lang3.StringUtils {
    private StringUtils() {
    }

    public static boolean notEquals(final CharSequence cs1, final CharSequence cs2) {
        return !equals(cs1, cs2);
    }

    public static boolean notEqualsIgnoreCase(final CharSequence cs1, final CharSequence cs2) {
        return !equalsIgnoreCase(cs1, cs2);
    }
}
