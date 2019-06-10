package top.alertcode.adelina.framework.utils;

import java.util.Date;

/**
 * Created by gizmo on 15/12/16.
 *
 * @author Bob
 * @version $Id: $Id
 */
public final class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    private DateUtils() {
    }

    /**
     * <p>now.</p>
     *
     * @return a {@link java.util.Date} object.
     */
    public static Date now() {
        return new Date();
    }


}
