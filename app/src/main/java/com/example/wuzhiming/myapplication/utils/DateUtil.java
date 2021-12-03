package com.example.wuzhiming.myapplication.utils;


import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static final String PATTERN = "yyyy.MM.dd HH:mm:ss";
    public static final String PATTERN_Y = "yyyy";
    public static final String PATTERN_EMPTY = "yyyyMMdd";
    public static final String PATTERN_EMPTY_FULL = "yyyyMMdd-HHmmss";
    public static final String PATTERN_YMD = "yyyy.MM.dd";
    public static final String PATTERN_YMD_LINE = "yyyy-MM-dd";
    public static final String PATTERN_FULL_DATE = "EEE, MMM d, yyyyy hh:mm:ss aa z";
    public static final String PATTERN_ISO8601_DATE = "yyyy-MM-dd'T'HH:mm:ss.SS z";

    public static String getCurrentTime(String pattern) {
        if (TextUtils.isEmpty(pattern)) {
            pattern = PATTERN;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
        return sdf.format(new Date());
    }


    public static int getYear(Date date) {
        SimpleDateFormat mFormatYear = new SimpleDateFormat(PATTERN_Y, Locale.CHINA);
        return Integer.parseInt(mFormatYear.format(date));
    }

    public static Date getEndOfDay(Date day) {
        return getEndOfDay(day, Calendar.getInstance());
    }

    public static Date getEndOfDay(Date day, Calendar cal) {
        if (day == null) day = new Date();
        cal.setTime(day);
        cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getMaximum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));
        return cal.getTime();
    }

    public static Date getNoonOfDay(Date day, Calendar cal) {
        if (day == null) day = new Date();
        cal.setTime(day);
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
        return cal.getTime();
    }

    /**
     * Returns a Date set to the first possible millisecond of the day, just
     * after midnight. If a null day is passed in, a new Date is created.
     * midnight (00m 00h 00s)
     */
    public static Date getStartOfDay(Date day) {
        return getStartOfDay(day, Calendar.getInstance());
    }

    /**
     * Returns a Date set to the first possible millisecond of the day, just
     * after midnight. If a null day is passed in, a new Date is created.
     * midnight (00m 00h 00s)
     */
    public static Date getStartOfDay(Date day, Calendar cal) {
        if (day == null) day = new Date();
        cal.setTime(day);
        cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
        return cal.getTime();
    }


    public static boolean equals(Date date1, Date date2) {
        if (date1 == null && date2 == null)
            return true;
        if (date1 == null && date2 != null)
            return false;
        if (date1 != null && date2 == null)
            return false;
        return date1.equals(date2);
    }

    /**
     * @return java.util.Date
     */
    public static Date tomorrow() {
        Calendar calender = Calendar.getInstance();
        calender.roll(Calendar.DAY_OF_YEAR, true);
        return calender.getTime();
    }

    /**
     * @param date
     * @return java.util.Date
     */
    public static Date nextDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.roll(Calendar.DAY_OF_YEAR, 1);
        if (isEndOfYear(date, cal.getTime())) {
            cal.roll(Calendar.YEAR, true);
            cal.roll(Calendar.DAY_OF_YEAR, 1);
        }
        return cal.getTime();
    }

    /**
     * @param curDate
     * @param rollUpDate
     * @return boolean
     */
    private static boolean isEndOfYear(Date curDate, Date rollUpDate) {
        return (curDate.compareTo(rollUpDate) >= 0);
    }

    /**
     * @return java.util.Date
     */
    public static Date yesterday() {
        Calendar calender = Calendar.getInstance();
        calender.roll(Calendar.DAY_OF_YEAR, false);
        return calender.getTime();
    }


    /**
     * @param baseDate
     * @param type
     * @param num
     * @return Date
     */
    public static Date addDate(Date baseDate, int type, int num) {
        Date lastDate = null;
        try {
            Calendar cale = Calendar.getInstance();
            cale.setTime(baseDate);
            if (type == 1) {
                cale.add(Calendar.YEAR, num);
            } else if (type == 2) {
                cale.add(Calendar.MONTH, num);
            } else if (type == 3) {
                cale.add(Calendar.DATE, num);
            }
            lastDate = cale.getTime();
            return lastDate;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 根据年月获取天数的最大值
     *
     * @param year
     * @param month
     * @return
     */
    public static String getDayByYearAndMonth(int year, int month) {
        Calendar time = Calendar.getInstance();
        time.clear();
        time.set(Calendar.YEAR, year);
        //year年
        time.set(Calendar.MONTH, month - 1);
        //Calendar对象默认一月为0,month月
        String day = time.getActualMaximum(Calendar.DAY_OF_MONTH) + "";//本月份的天数
        time.clear();
        return day;
    }

    /**
     * 获取当月第一天日期
     * @param format
     * @return
     */
    public static String getFirstDayOfCurMonth(String format) {
        Calendar calender = Calendar.getInstance();
        calender.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        return sdf.format(calender.getTime());
    }

    /**
     * 获取当月最后一天日期
     * @param format
     * @return
     */
    public static String getEndDayOfCurMonth(String format) {
        Calendar calender = Calendar.getInstance();
        calender.set(Calendar.DAY_OF_MONTH, calender.getActualMaximum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        return sdf.format(calender.getTime());
    }

    public static int getDateSpan(Date beginDate, Date endDate, int calType) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(beginDate);
        int[] p1 = {cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH)};
        cal.clear();
        cal.setTime(endDate);
        int[] p2 = {cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH)};
        int[] s = {p2[0] - p1[0], p2[0] * 12 + p2[1] - p1[0] * 12 - p1[1], (int) ((endDate.getTime() - beginDate.getTime()) / (24 * 3600 * 1000))};
        if (calType <= 3 || calType >= 1) {
            return s[calType - 1];
        } else {
            return 0;
        }
    }

    public static String formatDate4Video(String content_createtime, String format) {
        String date = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        String systemTime = sdf.format(new Date()).toString();
        try {
            Date begin = sdf.parse(content_createtime);
            Date end = sdf.parse(systemTime);
            long second = (end.getTime() - begin.getTime()) / 1000L;
            long l = 60L * 1000L;
            long l1 = (end.getTime() - begin.getTime()) / l; // 转换成分钟
            long hl = 60L * 60L * 1000L;
            long h1 = (end.getTime() - begin.getTime()) / hl;   //转换成小时
            if (second < 60) {
                if (second < 3) {
                    date = ("刚刚");
                } else {
                    date = (second + "秒前");
                }
            } else if (l1 < 60 && l1 > 0) {
                date = (l1 + "分钟前");
            } else if (h1 < 24 && h1 > 0) {               //24小时前
                date = (h1 + "小时前");
            } else if (h1 < 48 && h1 >= 24) {               //48小时前
                date = "昨天";
            } else {
                String month = String.format("%tm", begin);
                String day = String.format("%td", begin);
                String hour = String.format("%tH", begin);
                String minutes = String.format("%tM", begin);
                date = month + "-" + day + " " + hour + ":"
                        + minutes;
            }
        } catch (ParseException e) {
            return date;
        }
        return date;

    }

    public static String formatDate4Video(long content_createtime, String format) {
        String date = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        String systemTime = sdf.format(new Date()).toString();
        try {
            Date begin = new Date(content_createtime);
            Date end = sdf.parse(systemTime);
            long second = (end.getTime() - begin.getTime()) / 1000L;
            long l = 60L * 1000L;
            long l1 = (end.getTime() - begin.getTime()) / l; // 转换成分钟
            long hl = 60L * 60L * 1000L;
            long h1 = (end.getTime() - begin.getTime()) / hl;   //转换成小时
            if (second < 60) {
                if (second < 3) {
                    date = ("刚刚");
                } else {
                    date = (second + "秒前");
                }
            } else if (l1 < 60 && l1 > 0) {
                date = (l1 + "分钟前");
            } else if (h1 < 24 && h1 > 0) {               //24小时前
                date = (h1 + "小时前");
            } else if (h1 < 48 && h1 >= 24) {               //48小时前
                date = "昨天";
            } else {
                String month = String.format("%tm", begin);
                String day = String.format("%td", begin);
                String hour = String.format("%tH", begin);
                String minutes = String.format("%tM", begin);
                date = month + "-" + day + " " + hour + ":"
                        + minutes;
            }
        } catch (ParseException e) {
            return date;
        }
        return date;

    }


    public static String formatDateDiff(long content_createtime) {
        String date = "";
        try {
            long begin = content_createtime;
            long end = System.currentTimeMillis();
            long second = (end - begin) / 1000L;
            long l = 60L * 1000L;
            long l1 = (end - begin) / l; // 转换成分钟
            long hl = 60L * 60L * 1000L;
            long h1 = (end - begin) / hl;   //转换成小时
            long dl = 24 * 60L * 60L * 1000L;
            long d1 = (end - begin) / dl;   //转换成天
            long ml = 30 * 24 * 60L * 60L * 1000L;
            long m1 = (end - begin) / ml;   //转换成月
            long yl = 12 * 30 * 24 * 60L * 60L * 1000L;
            long y1 = (end - begin) / yl;   //转换成年
            if (m1 < 12) {
                if (second < 60) {
                    if (second < 3) {
                        date = ("刚刚");
                    } else {
                        date = (second + "秒前");
                    }
                } else if (l1 < 60 && l1 > 0) {
                    date = (l1 + "分钟前");
                } else if (h1 < 24 && h1 > 0) {               //24小时前
                    date = (h1 + "小时前");
                } else if (d1 < 31 && d1 > 0) {               //30天前
                    date = (d1 + "天前");
                } else if (m1 < 13 && m1 > 0) {               //12小时前
                    date = (m1 + "个月前");
                } else {
                    date = y1 + "年前";
                }
            } else {
                date = y1 + "年前";
            }
        } catch (Exception e) {
            return date;
        }
        return date;

    }

    /**
     * 将一种格式的时间转换成另一种格式的时间
     *
     * @param time
     * @param format
     * @param targetFormat
     * @return
     */
    public static String formatDate(String time, String format, String targetFormat) {
        String targettime = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        try {
            Date date1 = sdf.parse(time);
            SimpleDateFormat targetsdf = new SimpleDateFormat(targetFormat, Locale.CHINA);
            targettime = targetsdf.format(date1);
        } catch (ParseException e) {
            return null;
        }
        return targettime;
    }

    /**
     * 观看时间转换
     *
     * @param playedTime
     * @return
     */
    public static String parsePlayedTime(int playedTime) {
        String parsePt;
        int second = playedTime / 1000;
        int minute = 0;
        if (second > 60) {
            minute = second / 60;
            second = second % 60;
        }
        String seconds = String.valueOf(second);
        if (second < 10) {
            seconds = "0" + second;
        }
        String minutes = String.valueOf(minute);
        if (minute < 10) {
            minutes = "0" + minute;
        } else if (minute == 0) {
            minutes = "00";
        }
        parsePt = minutes + ":" + seconds;
        return parsePt;
    }

    /**
     * 与当前时间比较，如果晚于当前时间则返回true
     *
     * @param time1
     * @param currenttime
     * @return
     */
    public static boolean compareTime(String time1, String currenttime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date1 = sdf.parse(time1);
            Date currentDate = sdf.parse(currenttime);
            if (date1.before(currentDate) || date1.equals(currentDate)) {
                return false;
            } else {
                return true;
            }
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 获取日期中的月份
     *
     * @param strDate
     * @return
     */
    public static String getMonthFromStr(String strDate) {
        String month = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt = sdf.parse(strDate);
            month = String.valueOf(dt.getMonth() + 1);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return month;
    }

    /**
     * 获取日期中的年份
     *
     * @param strDate
     * @return
     */
    public static String getYearFromStr(String strDate) {
        String year = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        try {
            Date dt = sdf.parse(strDate);
            year = String.valueOf(getYear(dt));
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return year;
    }

    /**
     * 获取两个日期之间的月份间隔
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getMonthBetweenDate(String date1, String date2) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = null;
        try {
            d1 = sd.parse(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date d2 = null;
        try {
            d2 = sd.parse(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int months = 0;//相差月份
        int y1 = d1.getYear();
        int y2 = d2.getYear();
        if (d1.getTime() < d2.getTime()) {
            months = d2.getMonth() - d1.getMonth() + (y2 - y1) * 12 + 1;
        }
        return months;
    }

    /**
     * 时分秒转成字符串
     *
     * @param time
     * @return
     */
    public static String getHms(long time) {
        String hms = "";
        long hour = 0l, minutes = 0l, seconds = 0l;
        hour = time / 3600;
        minutes = (time / 60) % 60;
        seconds = time % 60;
        hms = String.format("%02d:%02d:%02d", hour, minutes, seconds);
        return hms;
    }

    /**
     * 得到时间的tab
     *
     * @param newsDate 时间戳
     * @return
     */
    public static String getBeforeDateTwo(String newsDate) {
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数long diff;try
        long diff = 0;

        SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 格式化当前系统日期
        String dateTime = dateFm.format(new Date());


        // 获得两个时间的毫秒时间差异
        try {
            diff = dateFm.parse(dateTime).getTime()
                    - Long.parseLong(newsDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long day = diff / nd;// 计算差多少天
        if (day > 0) {
            if (day > 30) {
                long month = day / 30;
                if (month > 11) {
                    return "一年前";
                } else {
                    return month + "个月前";
                }
            } else {
                return day + "天前";
            }
        }
        long hour = diff % nd / nh;// 计算差多少小时
        if (hour > 0) {
            return hour + "小时前";
        }
        long min = diff % nd % nh / nm;// 计算差多少分钟
        if (min > 0) {
            return min + "分钟前";
        }
        long sec = diff % nd % nh % nm / ns;// 计算差多少秒//输出结果

        return "刚刚";
    }

    /**
     * 返回时间错的  HH:mm
     *
     * @param format 格式 HH:mm
     * @param date   毫秒
     * @return
     */
    public static String getShortDateJustHm(String format, long date) {
        String shortDate;
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.CHINA);
        shortDate = formatter.format(date);
        return shortDate;
    }

    /**
     * 返回时间错的  MM-dd
     *
     * @param format 格式 比如MM-dd
     * @param date   毫秒
     * @return
     */
    public static String getShortDateJustMD(String format, long date) {
        String shortDate;
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.CHINA);
        shortDate = formatter.format(date);
        return shortDate;
    }


    /**
     * 返回时间错的  xxxx年xx月xx日
     *
     * @param format 格式 比如 yyyy年MM月dd日
     * @param date   毫秒
     * @return
     */
    public static String getShortDateJustYMD(String format, long date) {
        String shortDate;
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.CHINA);
        shortDate = formatter.format(new Date(date));
        return shortDate;
    }

    /**
     * 返回年月日的时间错
     *
     * @param format yyyy-MM-dd
     * @param date
     * @return
     */
    public static Date getShortDateJustYMD(String format, String date) {
        Date shortDate = null;
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.CHINA);
        try {
            shortDate = formatter.parse(date);
        } catch (Exception e) {
            shortDate = null;
        }
        return shortDate;
    }

    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return weekDays[w];
    }

    public static String getWeekOfDate(long date) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(date * 1000));
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.clear();
        if (w < 0)
            w = 0;
        return weekDays[w];
    }


    /**
     * 毫秒转化时分秒毫秒
     *
     * @param ms
     * @return
     */
    public static String formatTimes(long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if (day > 0) {
            sb.append(day + "天");
        }
        if (hour > 0) {
            sb.append(hour + "小时");
        }
        if (minute > 0) {
            sb.append(minute + "分");
        }
        if (second > 0) {
            sb.append(second + "秒");
        }
        if (milliSecond > 0) {
            sb.append(milliSecond + "毫秒");
        }
        return sb.toString();
    }


    /**
     * 基于当前时间，获取n天前后的时间
     *
     * @param format 格式
     * @param amount 正数 多少天之后 比如 3 代表在今天8月1往后推三天的时间就是8月4
     *               负数 多少天之前
     * @return
     */
    public static String getDateAsRendom(String format, int amount) {
        SimpleDateFormat df = new SimpleDateFormat(format, Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, amount);
        Date time = calendar.getTime();
        return df.format(time);
    }

    /**
     * 基于指定时间获取偏移后的时间
     * @param time
     * @param offset
     * @return
     */
    public static long getSpecifyTimeByOffSet(long time, int offset){
        if (offset == 0) {
            return time;
        }
        return time + 24*60*60*1000*offset;
    }

    /**
     * 基于指定时间获取偏移后的时间
     * @param time
     * @param format
     * @param offset 偏移值，几天之后或之前
     *               正数 比如1，即往后推一天
     *               负数 比如-1 即往前推一天
     * @return
     * @throws ParseException
     */
    public static String getSpecifyTimeByOffSet(String time,String format,int offset) throws ParseException {
        if (offset == 0) {
            return time;
        }
        long timeValue = stringToLong(time,format);
        timeValue = timeValue + 24*60*60*1000*offset;
        Date date = longToDate(timeValue);
        return dateToString(date, format);
    }

    /**
     * 基于当前时间，获取n天 前/后 的时间
     *
     * @param format 时间格式
     * @param dayAgo 正数 多少天之前，比如2 代表在今天8月3往前推2天就是8月1
     *               负数 多少天之后，比如2 代表在今天8月1往后推2天就是8月3
     * @return
     */
    public static String getSpecifyDate(String format, int dayAgo) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(format, Locale.CHINA);
        int day = calendar.get(Calendar.DAY_OF_MONTH) - dayAgo;
        calendar.set(Calendar.DAY_OF_MONTH, day);
        Date time = calendar.getTime();
        return df.format(time);
    }

    /**
     * 基于当前时间 获取多少小时前后的时间
     *
     * @param format 格式
     * @param hour   小时
     *               正数 多少小时之后 比如3，代表在9.30往后推3个小时就是12.30
     *               负数，多少小时之前 比如3，代表在9.30往前推3个小时就是6.30
     * @return
     */
    public static String getSpecifyHour(String format, int hour) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(format, Locale.CHINA);
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        Date time = calendar.getTime();
        return df.format(time);
    }


    /**
     * long类型转换为String类型
     *
     * @param currentTime long类型的时间
     * @param formatType  string类型的时间格式
     * @return
     */
    public static String longToString(long currentTime, String formatType) {
        Date date = longToDate(currentTime); // long类型转成Date类型
        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }

    /**
     * long转换为Date类型
     *
     * @param currentTime long类型的时间
     * @return
     */
    public static Date longToDate(long currentTime) {
        Date date = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        return date;
    }

    /**
     * date类型转换为String类型
     *
     * @param data
     * @param formatType
     * @return
     */
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType, Locale.CHINA).format(data);
    }

    /**
     * string类型转换为date类型
     *
     * @param strTime    string类型的时间,格式必须要与formatType的时间格式相同
     * @param formatType 时间格式
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType, Locale.CHINA);
        Date date = formatter.parse(strTime);
        return date;
    }

    /**
     * string类型转换为long类型
     *
     * @param strTime    String类型的时间,时间格式和formatType的时间格式必须相同
     * @param formatType 时间格式
     * @return
     * @throws ParseException
     */
    public static long stringToLong(String strTime, String formatType)
            throws ParseException {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = date2Long(date); // date类型转成long类型
            return currentTime;
        }
    }

    /**
     * date类型转换为long类型
     *
     * @param date
     * @return
     */
    public static long date2Long(Date date) {
        return date.getTime();
    }


    public static void main(String[] args) {
        String s = "32:DA:CA:D0:7D:BF:49:06:9A:8C:71:3D:A8:31:81:B9".toLowerCase();
        System.out.println(s.replace(":", ""));

        long time = getSpecifyTimeByOffSet(System.currentTimeMillis(), 7);
        System.out.print(longToString(time,PATTERN));
    }
}

