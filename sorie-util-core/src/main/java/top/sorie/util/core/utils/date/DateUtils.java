package top.sorie.util.core.utils.date;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class DateUtils {

    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /***
     * 当前时间
     * @return
     */
    public static Date now() {
        return new Date();
    }

    /**
     * 根据指定格式将字符串转换为Date
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date parseDate(String dateStr, String pattern) {
        if (dateStr == null || pattern == null) {
            return null;
        }
        try {
            SimpleDateFormat parser = new SimpleDateFormat(pattern);
            return parser.parse(dateStr);
        } catch (Exception e) {
            log.debug("转换失败", e);
        }
        return null;
    }

    /**
     * 日期型字符串转化为日期 格式
     *
     * @param dateStr
     * @return
     */
    public static Date parse(String dateStr) {
        if (dateStr == null) {
            return null;
        }
        for (String pattern : parsePatterns) {
            if (pattern.length() != dateStr.length()) {
                continue;
            }
            try {
                SimpleDateFormat parser = new SimpleDateFormat(pattern);
                return parser.parse(dateStr);
            } catch (Exception e) {
                log.debug("转换失败", e);
            }
        }
        return null;
    }

    /**
     * yyyy-MM-dd 格式字符串 转换为 日期
     * @param dateStr
     * @return
     */
    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, YYYY_MM_DD);
    }

    /**
     * yyyy-MM-dd HH:mm:ss格式字符串 转换为 日期
     * @param dateStr
     * @return
     */
    public static Date parseDateTime(String dateStr) {
        return parseDate(dateStr, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 日期转化为字符串
     *
     * @param dateStr
     * @return
     */
    public static String format(Date date, String pattern) {
        if (date == null || pattern == null) {
            return null;
        }
        try {
            SimpleDateFormat parser = new SimpleDateFormat(pattern);
            return parser.format(date);
        } catch (Exception e) {
            log.debug("转换失败", e);
        }
        return null;
    }

}
