package com.fang.utils;

import com.fang.enums.time.TimeFormatEnum;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author FPH
 */
@Slf4j
public class TimeUtils {
    /**
     * yyyy-MM-dd
     */
    public static SimpleDateFormat DATE_FORMAT;
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static SimpleDateFormat DATE_TIME_FORMAT;
    /**
     * HH:mm:ss
     */
    public static SimpleDateFormat TIME_FORMAT;
    /**
     * yyyy-MM-dd HH:mm
     */
    public static SimpleDateFormat MINUTE_FORMAT;

    private TimeUtils() {
    }

    public static String toText(Date date) {
        return DATE_FORMAT.format(date);
    }

    public static String toText(Date date, SimpleDateFormat format) {
        return format.format(date);
    }

    public static String toText(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern(TimeFormatEnum.SIMPLE.getFormat()));
    }

    public static String toText(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(TimeFormatEnum.DATE_TIME.getFormat()));
    }

    public static String toText(LocalTime localTime) {
        return localTime.format(DateTimeFormatter.ofPattern(TimeFormatEnum.HOUR_MINUTE_TIME.getFormat()));
    }

    public static String toText(Calendar calendar, String format) {
        return toText(calendar.getTime(), format);
    }

    public static String toText(Long timeStamp) {
        return toText(new Date(timeStamp));
    }

    public static String toText(Long timeStamp, String format) {
        return toText(new Date(timeStamp), format);
    }

    public static String toText(Long timeStamp, SimpleDateFormat format) {
        return toText(new Date(timeStamp), format);
    }

    public static String toText(Date date, String format) {
        SimpleDateFormat format1 = new SimpleDateFormat(format);
        return format1.format(date);
    }

    public static String toText2(Date date) {
        return DATE_TIME_FORMAT.format(date);
    }

    public static Date toDate(String date) {
        try {
            return DATE_FORMAT.parse(date);
        } catch (ParseException var2) {
            var2.printStackTrace();
            return new Date();
        }
    }

    public static Date toDate2(String date) {
        try {
            return DATE_TIME_FORMAT.parse(date);
        } catch (ParseException var2) {
            var2.printStackTrace();
            return new Date();
        }
    }

    public static Date toDate(String date, SimpleDateFormat format) {
        try {
            return format.parse(date);
        } catch (ParseException var3) {
            var3.printStackTrace();
            return new Date();
        }
    }

    public static Date toDate(String date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        try {
            return dateFormat.parse(date);
        } catch (ParseException var4) {
            var4.printStackTrace();
            return new Date();
        }
    }

    public static Date toDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date toDate(LocalDate localDate) {
        return toDate(localDate.atStartOfDay());
    }

    public static Date toDate(Calendar calendar, String format) {
        return toDate(toText(calendar, format));
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static LocalDate toLocalDate(Date date) {
        return toLocalDateTime(date).toLocalDate();
    }

    public static LocalDate toLocalDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(TimeFormatEnum.SIMPLE.getFormat()));
    }

    public static LocalDateTime toLocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(TimeFormatEnum.DATE_TIME.getFormat()));
    }

    public static long toTimestamp(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

    public static LocalDateTime toLocalDateTime(Long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }

    public static Long toTimestamp(String date) {
        return toDate(date).getTime();
    }

    public static Long toTimestamp2(String date) {
        return toDate2(date).getTime();
    }

    public static Long toTimestamp(String date, SimpleDateFormat format) {
        return toDate(date, format).getTime();
    }

    public static Long toTimestamp(String date, String format) {
        return toDate(date, format).getTime();
    }

    public static Calendar toCalendar(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(simpleDateFormat.parse(date));
        } catch (ParseException var4) {
            var4.printStackTrace();
        }

        return calendar;
    }

    public static Integer getYearsByStartTime(String date) {
        LocalDate startDate1 = toLocalDate(date);
        LocalDate currentDate = LocalDate.now();
        return currentDate.isBefore(startDate1) ? 0 : startDate1.until(currentDate).getYears();
    }

    public static Integer getYearsByStartTime(LocalDate date) {
        LocalDate currentDate = LocalDate.now();
        return currentDate.isBefore(date) ? 0 : date.until(currentDate).getYears();
    }

    public static int getAllDaysOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getActualMaximum(5);
    }

    public static int getDays(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(5);
    }

    public static int getYears(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(1);
    }

    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(2) + 1;
    }

    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(2, n);
        return cal.getTime();
    }

    public static Date addDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(5, n);
        return cal.getTime();
    }

    public static Date addSeconds(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(13, n);
        return cal.getTime();
    }

    /**
     * 两者时间相差多少时间戳
     * @param startDate
     * @param endDate
     * @return
     */
    public static long dateBetweenToTimeStamp(String startDate, String endDate) {
        Date dateStart = toDate(startDate,DATE_TIME_FORMAT);
        Date dateEnd = toDate(endDate,DATE_TIME_FORMAT);
        return dateEnd.getTime() - dateStart.getTime();
    }

    /**
     * 计算时间差
     * @param startTime
     * @param endTime
     * @return
     */
    public static String timeDistance(Date startTime,Date endTime){
        String dateStart = toText(startTime,TimeUtils.DATE_FORMAT);
        String dateEnd = toText(endTime);
        return timeDistance(dateStart,dateEnd);
    }

    /**
     * 计算时间差
     *
     * @param endTime 最后时间
     * @param startTime 开始时间
     * @return 时间差（天/小时/分钟）
     */
    public static String timeDistance(String startTime,String endTime)
    {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = dateBetweenToTimeStamp(startTime,endTime);
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟" + sec + "秒";
    }


    public static String dateBetweenToDate(String startDate, String endDate) {
        return TimeUtils.toText(new Date(dateBetweenToTimeStamp(startDate,endDate)),TimeUtils.MINUTE_FORMAT);
    }

    public static int dateBetween(String startDate, String endDate) {
        return (int)(dateBetweenToTimeStamp(startDate,endDate) / 1000L / 60L / 60L / 24L);
    }

    public static int dateBetween(Date startDate, Date endDate) {
        return (int)((startDate.getTime() - endDate.getTime()) / 1000L / 60L / 60L / 24L);
    }

    public static int dateBetweenIncludeToday(String startDate, String endDate) {
        return dateBetween(startDate, endDate) + 1;
    }

    public static int dateBetweenIncludeToday(Date startDate, Date endDate) {
        return dateBetween(startDate, endDate) + 1;
    }

    public static int dateBetweenSecond(Date startDate, Date endDate) {
        return (int)((startDate.getTime() - endDate.getTime()) / 1000L);
    }

    public static int dateBetweenSecond(String startDate, String endDate) {
        Date dateStart = toDate(startDate);
        Date dateEnd = toDate(endDate);
        return (int)((dateStart.getTime() - dateEnd.getTime()) / 1000L);
    }

    public static String now(){
        return now(DATE_TIME_FORMAT);
    }

    public static String now(SimpleDateFormat simpleDateFormat){
        return toText(new Date(),simpleDateFormat);
    }

    static {
        DATE_FORMAT = new SimpleDateFormat(TimeFormatEnum.SIMPLE.getFormat());
        DATE_TIME_FORMAT = new SimpleDateFormat(TimeFormatEnum.DATE_TIME.getFormat());
        MINUTE_FORMAT = new SimpleDateFormat(TimeFormatEnum.DATE_MINUTE.getFormat());
        TIME_FORMAT = new SimpleDateFormat(TimeFormatEnum.HOUR_MINUTE_TIME.getFormat());
    }
}
