package top.alertcode.adelina.framework.utils;

import java.util.Date;

/**
 * Created by gizmo on 15/12/16.
 */
public final class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    private DateUtils() {
    }

    public static Date now() {
        return new Date();
    }
}