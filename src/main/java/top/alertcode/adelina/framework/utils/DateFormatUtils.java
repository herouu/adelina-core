package top.alertcode.adelina.framework.utils;

import org.apache.commons.lang3.StringUtils;
import top.alertcode.adelina.framework.exception.FrameworkUtilException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wanghongmeng on 2015/6/24.
 *
 * @author Bob
 * @version $Id: $Id
 */
public final class DateFormatUtils extends org.apache.commons.lang3.time.DateFormatUtils {
    private static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private DateFormatUtils() {
    }

    /**
     * <p>formatDateTime.</p>
     *
     * @param format a {@link java.lang.String} object.
     * @param args   a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     * @throws java.text.ParseException if any.
     */
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

    /**
     * {@inheritDoc}
     */
    public static String format(String dateStr, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = sdf.parse(dateStr);
        return sdf.format(date);
    }

    /**
     * <p>formatDate.</p>
     *
     * @param dateStr a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String formatDate(String dateStr) {
        try {
            return format(dateStr, DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.getPattern());
        } catch (ParseException e) {
            throw new FrameworkUtilException("DateFormatUtils format exception");
        }
    }
}
