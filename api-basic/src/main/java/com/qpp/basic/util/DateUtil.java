package com.qpp.basic.util;

import java.util.Calendar;
import java.util.Date;

/**
 * created by fuyd on 2018/05/03
 */
public class DateUtil {

    /**
     * 获取日期的最开始时间
     * 
     * @param d
     * @return
     */
    public static Date thatDayZeroPoint(Date d) {
        if (d == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取日期的最后时间
     * 
     * @param d
     * @return
     */
    public static Date thatDayLastTime(Date d) {
        if (d == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }
    public static Date getDaysIntervalDate(Date pDate, int pDays) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(pDate);
        cl.add(Calendar.DATE, pDays);
        return parseDate(cl);
    }

    public static Date parseDate(Calendar calendar) {
        if (calendar == null)
            return null;
        Date date = null;
        Date udate = calendar.getTime();
        return udate;
    }
    public static int getYear(Date date) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        int intWeek = cl.get(Calendar.YEAR);
        return intWeek;
    }
}
