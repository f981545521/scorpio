package cn.acyou.framework.utils;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;

import java.util.Calendar;
import java.util.Date;

/**
 * @author youfang
 * @date 2018-02-09 11:23
 **/
public final class DateUtil {

    public static final String SHORT_DATE_PATTERN = "yyyyMMdd";
    public static final String DEFAULT_DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    public static final String SPECIFIC_DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_DAY_MAX_TIME = " 23:59:59";
    public static final String DATE_SERIES_FORMAT_PATTERN = "yyyyMMddHHmmss";
    public final static char[] upper = "〇一二三四五六七八九十".toCharArray();

    private DateUtil() {

    }

    public static String getDateFormat(Date date) {
        return getDateFormat(date, SPECIFIC_DATE_FORMAT_PATTERN);
    }

    public static String getCurrentDateFormat() {
        return getDateFormat(new Date(), SPECIFIC_DATE_FORMAT_PATTERN);
    }

    public static String getCurrentDateFormat(String format) {
        return getDateFormat(new Date(), format);
    }

    public static String getDateShortFormat(Date date) {
        return getDateFormat(date, SHORT_DATE_PATTERN);
    }

    public static String getCurrentDateShortFormat() {
        return getDateFormat(new Date(), SHORT_DATE_PATTERN);
    }

    public static String getDateFormat(Date date, String format) {
        return new DateTime(date).toString(format);
    }

    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, DEFAULT_DATE_FORMAT_PATTERN);
    }

    public static Date parseDate(String dateStr, String format) {
        return DateTimeFormat.forPattern(format).parseDateTime(dateStr).toDate();
    }

    /**
     * 向日期增加天数
     *
     * @param date 日期
     * @param day  天数
     * @return
     */
    public static Date addDay(Date date, int day) {
        return new DateTime(date).plusDays(day).toDate();
    }

    /**
     * 向日期减少天数
     *
     * @param date 日期
     * @param day  天数
     * @return
     */
    public static Date minusDay(Date date, int day) {
        return new DateTime(date).minusDays(day).toDate();
    }

    /**
     * 向日期增加月数
     *
     * @param date  日期
     * @param month 月
     * @return
     */
    public static Date addMonth(Date date, int month) {
        return new DateTime(date).plusMonths(month).toDate();
    }

    /**
     * 向日期减少月数
     *
     * @param date  日期
     * @param month 月
     * @return
     */
    public static Date minusMonth(Date date, int month) {
        return new DateTime(date).minusMonths(month).toDate();
    }

    /**
     * 向日期增加小时
     *
     * @param date 日期
     * @param hour 小时
     * @return
     */
    public static Date addHour(Date date, int hour) {
        return new DateTime(date).plusHours(hour).toDate();
    }

    /**
     * 向日期减少小时
     *
     * @param date 日期
     * @param hour 小时
     * @return
     */
    public static Date minusHour(Date date, int hour) {
        return new DateTime(date).minusHours(hour).toDate();
    }

    /**
     * 向日期增加分钟
     *
     * @param date    日期
     * @param minutes 分钟
     * @return
     */
    public static Date addMinutes(Date date, int minutes) {
        return new DateTime(date).plusMinutes(minutes).toDate();
    }

    /**
     * 向日期减少分钟
     *
     * @param date    日期
     * @param minutes 分钟
     * @return
     */
    public static Date minusMinutes(Date date, int minutes) {
        return new DateTime(date).minusMinutes(minutes).toDate();
    }

    /**
     * 向日期增加秒
     *
     * @param date    日期
     * @param seconds 秒
     * @return
     */
    public static Date addSeconds(Date date, int seconds) {
        return new DateTime(date).plusSeconds(seconds).toDate();
    }

    /**
     * 向日期减少秒
     *
     * @param date    日期
     * @param seconds 秒
     * @return
     */
    public static Date minusSeconds(Date date, int seconds) {
        return new DateTime(date).minusSeconds(seconds).toDate();
    }

    public static Date randomRangeDate(String startStr, String endStr) {
        long startTime = new DateTime(startStr).toDate().getTime();
        long endTime = new DateTime(endStr).toDate().getTime();
        double randomDate = Math.random() * (endTime - startTime) + startTime;
        DateTime random = new DateTime(Math.round(randomDate));
        return random.toDate();
    }

    /**
     * 比较两个时间相差多少秒
     */
    public static long getDiffSeconds(Date startDate, Date endDate) {
        return Math.abs((endDate.getTime() - startDate.getTime()) / 1000);
    }

    /**
     * 比较两个时间相差多少分钟
     */
    public static long getDiffMinutes(Date startDate, Date endDate) {
        long diffSeconds = getDiffSeconds(startDate, endDate);
        return diffSeconds / 60;
    }

    /**
     * 比较两个时间相差多少天
     * 如果开始时间<结束时间 返回天数为正值
     * 如果开始时间>结束时间 返回天数为负值
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     */
    public static long getDiffDay(Date startDate, Date endDate) {
        long between = Math.abs((startDate.getTime() - endDate.getTime()) / 1000);
        long day = between / 60 / 60 / 24;
        if (startDate.after(endDate)) {
            return (long) -Math.floor(day);
        }
        return (long) Math.floor(day);
    }

    /**
     * 比较两个时间相差多少天
     * 如果开始时间<结束时间 返回天数为正值
     * 如果开始时间>结束时间 返回天数为负值
     *
     * @param startDate 开始时间
     */
    public static long getCurrentDiffDay(Date startDate) {
        return getDiffDay(startDate, new Date());
    }

    /**
     * 比较两个时间相差多少天
     * 如果开始时间<结束时间 返回天数为正值
     * 如果开始时间>结束时间 返回天数为负值
     *
     * @param startDate 开始时间
     */
    public static long getCurrentDiffMinutes(Date startDate) {
        return getDiffMinutes(startDate, new Date());
    }

    /**
     * 获取两个时间相差月份
     */
    public static int getDiffMonth(Date start, Date end) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        return (endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR)) * 12
                + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
    }

    /**
     * 返回传入时间月份的第一天
     */
    public static Date firstDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int value = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, value);
        return cal.getTime();
    }

    /**
     * 返回传入时间月份的最后一天
     */
    public static Date lastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, value);
        return cal.getTime();
    }

    /**
     * 判断是否为闰年<br>
     * generate by: zengqw at 2012-9-26
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * 计算相对于dateToCompare的年龄，长用于计算指定生日在某年的年龄
     *
     * @param birthDay      生日
     * @param dateToCompare 需要对比的日期
     * @return 年龄
     */
    public static int age(Date birthDay, Date dateToCompare) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateToCompare);

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException("Birthday is after date " + getDateFormat(birthDay) + "!");
        }

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthDay);
        int age = year - cal.get(Calendar.YEAR);

        int monthBirth = cal.get(Calendar.MONTH);
        if (month == monthBirth) {
            int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
            if (dayOfMonth < dayOfMonthBirth) {
                // 如果生日在当月，但是未达到生日当天的日期，年龄减一
                age--;
            }
        } else if (month < monthBirth) {
            // 如果当前月份未达到生日的月份，年龄计算减一
            age--;
        }

        return age;
    }

    public static Date randomDate() {
        return randomRangeDate("1990-01-01", "2018-12-31");
    }


    public static final int DAYS_PER_WEEKEND = 2;
    public static final int WEEK_START = DateTimeConstants.MONDAY;
    public static final int WEEK_END = DateTimeConstants.FRIDAY;

    /**
     * 获取两个日期之间的工作日
     *
     * @param d1
     * @param d2
     * @return
     */
    public static int workdayDiff(Date d1, Date d2) {
        LocalDate start = LocalDate.fromDateFields(d1);
        LocalDate end = LocalDate.fromDateFields(d2);
        start = toWorkday(start);
        end = toWorkday(end);
        int daysBetween = Days.daysBetween(start, end).getDays();
        int weekendsBetween = Weeks.weeksBetween(start.withDayOfWeek(WEEK_START), end.withDayOfWeek(WEEK_START)).getWeeks();
        return daysBetween - (weekendsBetween * DAYS_PER_WEEKEND);
    }

    /**
     * 获取指定日期的 yyyy-MM-dd 23:59:59 格式
     *
     * @param date 日期
     * @return
     */
    public static String getDateFormatMaxTime(Date date) {
        String dateFormat = getDateFormat(date, DEFAULT_DATE_FORMAT_PATTERN);
        return dateFormat + DATE_DAY_MAX_TIME;
    }


    /**
     * 根据小写数字格式的日期转换成大写格式的日期
     * 支持yyyy-MM-dd、yyyy/MM/dd、yyyyMMdd等格式
     *
     * @param date 日期
     * @return
     */
    public static String getUpperDate(Date date) {
        return getUpperDate(getDateShortFormat(date));
    }

    /**
     * 获取当前日期
     * 根据小写数字格式的日期转换成大写格式的日期
     * 支持yyyy-MM-dd、yyyy/MM/dd、yyyyMMdd等格式
     *
     * @return
     */
    public static String getUpperDate() {
        return getUpperDate(new Date());
    }

    /**
     * 根据小写数字格式的日期转换成大写格式的日期
     * 支持yyyy-MM-dd、yyyy/MM/dd、yyyyMMdd等格式
     *
     * @param date 字符串日期
     * @return
     */
    public static String getUpperDate(String date) {
        if (date == null) return "";
        //非数字的都去掉
        date = date.replaceAll("\\D", "");
        if (date.length() != 8) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {//年
            sb.append(upper[Integer.parseInt(date.substring(i, i + 1))]);
        }
        sb.append("年");//拼接年
        int month = Integer.parseInt(date.substring(4, 6));
        if (month <= 10) {
            sb.append(upper[month]);
        } else {
            sb.append("十").append(upper[month % 10]);
        }
        sb.append("月");//拼接月

        int day = Integer.parseInt(date.substring(6));
        if (day <= 10) {
            sb.append(upper[day]);
        } else if (day < 20) {
            sb.append("十").append(upper[day % 10]);
        } else {
            sb.append(upper[day / 10]).append("十");
            int tmp = day % 10;
            if (tmp != 0) sb.append(upper[tmp]);
        }
        sb.append("日");//拼接日
        return sb.toString();
    }


    public static LocalDate toWorkday(LocalDate d) {
        if (d.getDayOfWeek() > WEEK_END) {
            return d.plusDays(DateTimeConstants.DAYS_PER_WEEK - d.getDayOfWeek() + 1);
        }
        return d;
    }

    /**
     * 根据月份获取英文月份名称
     *
     * @param month 月份 1~12
     * @return 英文月份名称
     */
    public static String getMonthEN(int month) {
        String[] allMonths = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        if (0 < month && month < 13) {
            return allMonths[month - 1];
        }
        return "";
    }

    /**
     * 获取英文出生日期
     *
     * @param date 日期
     * @return April 1.1979
     */
    private static String getEnBirthDay(Date date) {
        DateTime dateTime = new DateTime(date);
        String monthEN = getMonthEN(dateTime.getMonthOfYear());
        return monthEN + " " + dateTime.getDayOfMonth() + "." + dateTime.getYear();
    }


    public static void main(String[] args) {
        //Date d1 = DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime("2019-10-01").toDate();
        //Date d2 = DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime("2019-09-12").toDate();
        //long diffDay = getDiffDay(d1, d2);
        //System.out.println(diffDay);

        DateTime dateTime = new DateTime();
        System.out.println(dateTime.getMonthOfYear());

        String sss = DateUtil.getEnBirthDay(new Date());
        System.out.println(sss);


    }


}