package com.hx.blog_v2.util;

import com.hx.log.util.Tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期相关的工具类
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 9:50 AM
 */
public final class DateUtils {

    // disable constructor
    private DateUtils() {
        Tools.assert0("can't instantiate !");
    }

    /**
     * 当前Utils 允许的pattern
     */
    public static final Set<String> VALID_PATTERNS = Tools.asSet(
            BlogConstants.FORMAT_YYYY_MM_DD, BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS,
            BlogConstants.FORMAT_YYYY_MM, BlogConstants.FORMAT_FILENAME
    );
    /**
     * 线程私有的 DateFormat
     * pattern -> threadLocal<dateFormat>
     */
    private static final Map<String, ThreadLocal<DateFormat>> DATE_FORMATES = new HashMap<>();

    /**
     * 格式化给定的日期
     *
     * @param date    date
     * @param pattern pattern
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 5/20/2017 9:52 AM
     * @since 1.0
     */
    public static String formate(Date date, String pattern) {
        return getDateFormat(pattern).format(date);
    }

    /**
     * 格式化给定的日期
     *
     * @param date    date
     * @param pattern pattern
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 5/20/2017 9:52 AM
     * @since 1.0
     */
    public static Date parse(String date, String pattern) {
        try {
            return getDateFormat(pattern).parse(date);
        } catch (Exception e) {
            e.printStackTrace();
            return new Date(0);
        }
    }

    /**
     * 获取一个当前线程私有的 dateFormat
     *
     * @return java.text.DateFormat
     * @author Jerry.X.He
     * @date 5/20/2017 9:55 AM
     * @since 1.0
     */
    public static DateFormat getDateFormat(String pattern) {
        Tools.assert0(VALID_PATTERNS.contains(pattern), " sorry, DateUtils does not support this pattern !");

        ThreadLocal<DateFormat> threadLocal = DATE_FORMATES.get(pattern);
        if (threadLocal == null) {
            threadLocal = new ThreadLocal<>();
            DATE_FORMATES.put(pattern, threadLocal);
        }
        DateFormat dateFormat = threadLocal.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat(pattern);
            threadLocal.set(dateFormat);
        }

        return dateFormat;
    }

    /**
     * 获取 date + dayOff 对应的日期
     *
     * @param date   date
     * @param dayOff dayOff
     * @return java.util.Date
     * @author Jerry.X.He
     * @date 6/10/2017 11:36 PM
     * @since 1.0
     */
    public static Date addDay(Date date, int dayOff) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, dayOff);
        return cal.getTime();
    }

    /**
     * 获取给定的日期的 早上0点0分0秒
     *
     * @param date date
     * @return java.util.Date
     * @author Jerry.X.He
     * @date 6/10/2017 11:58 PM
     * @since 1.0
     */
    public static Date beginOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 1);
        return cal.getTime();
    }

}
