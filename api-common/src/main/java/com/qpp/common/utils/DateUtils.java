package com.qpp.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * 
 * @author qpp
 */
@Slf4j
public class DateUtils {

    private DateUtils(){}

    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDD = "yyyyMMdd";

    public static String YYYYMMDD_1 = "yyyy/MM/dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    /**
     * 默认时间戳格式，到毫秒 yyyy-MM-dd HH:mm:ss SSS
     */
    public static final String DEFAULT_DATEDETAIL_PATTERN = "yyyy-MM-dd HH:mm:ss SSS";
    /**
     * 描述性日期
     */
    public static final String DESC_DATE_PATTERN = "yyyy年MM月dd日";

    /**
     * 描述性日期时间
     */
    public static final String DESC_DATETIME_PATTERN = "yyyy年MM月dd日 HH:mm:ss";



    /**
     * 获取当前Date型日期
     * 
     * @return Date() 当前日期
     */
    public static Date getNowDate()
    {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     * 
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    /**
     * @Author qipengpai
     * @Description //TODO 时间转字符串
     * @Date 2018/12/23 13:50
     * @Param [format, date] 
     * @return java.lang.String
     * @throws 
     **/
    public static String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * @Author qipengpai
     * @Description //TODO 字符串转时间
     * @Date 2018/12/23 13:50
     * @Param [format, ts] 
     * @return java.util.Date
     * @throws 
     **/
    public static Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        }
        catch (ParseException e) {
            log.error("字符串转换时间异常",e);
            return getNowDate();
        }
    }

    /**
     * @Author qipengpai
     * @Description //TODO  获得当前的日期毫秒
     * @Date 2018/12/23 13:50
     * @Param [] 
     * @return long
     * @throws 
     **/
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * @Author qipengpai
     * @Description //TODO 获得当前的时间戳
     * @Date 2018/12/23 13:50
     * @Param [] 
     * @return java.sql.Timestamp
     * @throws 
     **/
    public static Timestamp getCurrentTimeStamp() {
        return new Timestamp(getCurrentTimeMillis());
    }


    /**
     * @Author qipengpai
     * @Description //TODO 日期路径 即年/月/日 如2018/08/08
     * @Date 2018/12/23 13:41
     * @Param []
     * @return java.lang.String
     * @throws
     **/
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, YYYYMMDD_1);
    }

    /**
     * @Author qipengpai
     * @Description //TODO 日期路径 即年/月/日 如20180808
     * @Date 2018/12/23 13:40
     * @Param []
     * @return java.lang.String
     * @throws
     **/
    public static final String dateTime() {
        return DateFormatUtils.format(new Date(), YYYYMMDD);
    }
    /**
     * @Author qipengpai
     * @Description //TODO 将时间戳转换为时间
     * @Date 14:24 2018/11/1
     * @Param [s]
     * @return java.lang.String
     * @throws
     **/
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * @Author qipengpai
     * @Description //TODO 指定日期加上天数后的日期
     * @Date 2018/12/23 14:00
     * @Param [num, newDate] 
     * @return java.lang.String
     * @throws 
     **/
    public static String plusDay(int num, String newDate) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currdate = format.parse(newDate);
        log.info("现在的日期是：" + currdate);
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
        currdate = ca.getTime();
        String enddate = format.format(currdate);
        log.info("增加天数以后的日期：" + enddate);
        return enddate;
    }
}
