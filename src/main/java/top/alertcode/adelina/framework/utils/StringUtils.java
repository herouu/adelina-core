package top.alertcode.adelina.framework.utils;

/**
 * Created by gizmo on 15/12/11.
 *
 * @author Bob
 * @version $Id: $Id
 */
public final class StringUtils extends org.apache.commons.lang3.StringUtils {
    private StringUtils() {
    }

    /**
     * <p>notEquals.</p>
     *
     * @param cs1 a {@link java.lang.CharSequence} object.
     * @param cs2 a {@link java.lang.CharSequence} object.
     * @return a boolean.
     */
    public static boolean notEquals(final CharSequence cs1, final CharSequence cs2) {
        return !equals(cs1, cs2);
    }

    /**
     * <p>notEqualsIgnoreCase.</p>
     *
     * @param cs1 a {@link java.lang.CharSequence} object.
     * @param cs2 a {@link java.lang.CharSequence} object.
     * @return a boolean.
     */
    public static boolean notEqualsIgnoreCase(final CharSequence cs1, final CharSequence cs2) {
        return !equalsIgnoreCase(cs1, cs2);
    }
}
