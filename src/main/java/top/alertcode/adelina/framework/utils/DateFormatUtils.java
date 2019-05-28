package top.alertcode.adelina.framework.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wanghongmeng on 2015/6/24.
 */
public final class DateFormatUtils extends org.apache.commons.lang3.time.DateFormatUtils {
    private static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private DateFormatUtils() {
    }

    public static String formatDateTime(String format, String... args) throws ParseException {
        if (StringUtils.isEmpty(format)) {
            return StringUtils.EMPTY;
        }

        for (String arg : args) {
            if (StringUtils.isEmpty(arg)) {
                return StringUtils.EMPTY;
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(format);

        StringBuilder str = new StringBuilder();
        for (String arg : args) {
            str.append(arg);
        }

        Date date = sdf.parse(str.toString());
        return org.apache.commons.lang3.time.DateFormatUtils.format(date, DEFAULT_DATETIME_FORMAT);
    }

    public static String format(String dateStr, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = sdf.parse(dateStr);
        return sdf.format(date);
    }
}
