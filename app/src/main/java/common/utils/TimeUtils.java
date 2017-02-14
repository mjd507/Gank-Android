package common.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 描述: 时间相关的工具类
 * Created by mjd on 2016/12/19.
 */

public class TimeUtils {

    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";


    //---------------- millis => String  millis <==> date  date => String  start  -------------

    /**
     * 将时间戳转为时间字符串
     */
    public static String millis2String(long millis) {
        return new SimpleDateFormat(DEFAULT_PATTERN, Locale.getDefault()).format(new Date(millis));
    }

    /**
     * 将时间戳转为时间字符串
     */
    public static String millis2String(long millis, String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(new Date(millis));
    }

    /**
     * 将Date类型转为时间字符串
     */
    public static String date2String(Date date) {
        return date2String(date, DEFAULT_PATTERN);
    }

    /**
     * 将Date类型转为时间字符串
     */
    public static String date2String(Date date, String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(date);
    }

    /**
     * 将Date类型转为时间戳
     */
    public static long date2Millis(Date date) {
        return date.getTime();
    }

    /**
     * 将时间戳转为Date类型
     */
    public static Date millis2Date(long millis) {
        return new Date(millis);
    }


    //---------------- 获取合适型两个时间差 返回 String 类型的 start ----------------

    /**
     * 获取合适型两个时间差
     * precision = 0，返回null
     * precision = 1，返回天
     * precision = 2，返回天和小时
     * precision = 3，返回天、小时和分钟
     * precision = 4，返回天、小时、分钟和秒
     * precision >= 5，返回天、小时、分钟、秒和毫秒
     */
    public static String getFitTimeSpan(long millis0, long millis1, int precision) {
        long millis = Math.abs(millis0 - millis1);
        if (millis <= 0 || precision <= 0) return null;
        StringBuilder sb = new StringBuilder();
        String[] units = {"天", "小时", "分钟", "秒", "毫秒"};
        int[] unitLen = {ConstUtils.DAY, ConstUtils.HOUR, ConstUtils.MIN, ConstUtils.SEC, 1};
        precision = Math.min(precision, 5);
        for (int i = 0; i < precision; i++) {
            if (millis >= unitLen[i]) {
                long mode = millis / unitLen[i];
                millis -= mode * unitLen[i];
                sb.append(mode).append(units[i]);
            }
        }
        return sb.toString();
    }

    /**
     * 获取友好型与当前时间的差
     * <p>
     * 如果小于1秒钟内，显示刚刚
     * 如果在1分钟内，显示XXX秒前
     * 如果在1小时内，显示XXX分钟前
     * 如果在1小时外的今天内，显示今天15:32
     * 如果是昨天的，显示昨天15:32
     * 其余显示，2016-10-15
     * 时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007
     */
    @SuppressLint("DefaultLocale")
    public static String getFriendlyTimeSpanByNow(long millis) {
        long now = System.currentTimeMillis();
        long span = now - millis;
        if (span < 0)
            return String.format("%tc", millis);// U can read http://www.apihome.cn/api/java/Formatter.html to understand it.
        if (span < 1000) {
            return "刚刚";
        } else if (span < ConstUtils.MIN) {
            return String.format("%d秒前", span / ConstUtils.SEC);
        } else if (span < ConstUtils.HOUR) {
            return String.format("%d分钟前", span / ConstUtils.MIN);
        }
        // 获取当天00:00
        long wee = (now / ConstUtils.DAY) * ConstUtils.DAY;
        if (millis >= wee) {
            return String.format("今天%tR", millis);
        } else if (millis >= wee - ConstUtils.DAY) {
            return String.format("昨天%tR", millis);
        } else {
            return String.format("%tF", millis);
        }
    }

    /**
     * 判断是否同一天
     */
    public static boolean isSameDay(long millis) {
        long wee = (System.currentTimeMillis() / ConstUtils.DAY) * ConstUtils.DAY;
        return millis >= wee && millis < wee + ConstUtils.DAY;
    }

    /**
     * 判断是否闰年
     */
    public static boolean isLeapYear(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(millis2Date(millis));
        int year = cal.get(Calendar.YEAR);
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }


    /**
     * 获取星期
     */
    public static String getWeek(long millis) {
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(new Date(millis));
    }

    /**
     * 获取星期 index
     * 注意: 周日的Index才是1，周六为7
     */
    public static int getWeekIndex(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        return cal.get(Calendar.DAY_OF_WEEK);
    }


    /**
     * 获取月份中的第几周
     * 注意：国外周日才是新的一周的开始
     *
     * @return 1...5
     */
    public static int getWeekOfMonth(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        return cal.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 获取年份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     */
    public static int getWeekOfYear(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }


    private static final String[] CHINESE_ZODIAC = {"猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊"};

    /**
     * 获取生肖
     */
    public static String getChineseZodiac(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        return CHINESE_ZODIAC[cal.get(Calendar.YEAR) % 12];
    }

    /**
     * 获取生肖
     */
    public static String getChineseZodiac(int year) {
        return CHINESE_ZODIAC[year % 12];
    }


    private static final String[] ZODIAC = {"水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座"};
    private static final int[] ZODIAC_FLAGS = {20, 19, 21, 21, 21, 22, 23, 23, 23, 24, 23, 22};

    /**
     * 获取星座
     */
    public static String getZodiac(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return getZodiac(month, day);
    }

    /**
     * 获取星座
     */
    public static String getZodiac(int month, int day) {
        return ZODIAC[day >= ZODIAC_FLAGS[month - 1]
                ? month - 1
                : (month + 10) % 12];
    }

    public static Date getBeforeDay() {
        Date dNow = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dNow);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }
}
