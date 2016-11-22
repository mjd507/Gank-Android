package universal.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述:
 * Created by mjd on 2016/11/22.
 */
public class DateUtils {
    /**
     * 获取当前年份
     * */
    public static int getYear() {
        Date currTime = new Date();
        return currTime.getYear();
    }

    /**
     * 获取当前月份
     * */
    public static int getMonth() {
        Date currTime = new Date();
        return currTime.getMonth() + 1;
    }

    /**
     * 获取当前星期几
     * */
    public static int getDay() {
        Date currTime = new Date();
        return currTime.getDay();
    }

    /**
     * 获取当月日数
     * */
    public static int getDate() {
        Date currTime = new Date();
        return currTime.getDate();
    }

    /**
     * 格式化时间
     *
     * @param date
     *            时间
     * @param pattern
     *            格式化字符串的规则。
     * @return 如规则：yyyy年MM月dd日 HH时mm分ss秒 则返回：xxxx年xx月xx日 xx时xx分xx秒
     * */
    public static String formatTime(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    /**
     * 格式化当前时间
     * @param pattern
     *            格式化字符串的规则。
     * @return 如规则：yyyy年MM月dd日 HH时mm分ss秒 则返回：xxxx年xx月xx日 xx时xx分xx秒
     * */
    public static String formatTime(String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获得格式化定制化的时间
     * @param date
     * @return	返回规则为：今天 xx:xx、昨天 xx:xx、前天 xx:xx；今年的不在这三天的：xx月xx日 xx:xx;不在今年的：xxxx年xx月xx日 xx:xx
     */
    @SuppressWarnings("deprecation")
    public static String getFormatDate(Date date){
        //本年
        if (date.getYear() == getYear()) {
            //本月
            if (date.getMonth() + 1 == getMonth()) {
                //今天
                if (date.getDate() == getDate()) {
                    return formatTime(date, "今天 HH:mm");
                }else if (date.getDate() == getDate() - 1) {//昨天
                    return formatTime(date, "昨天 HH:mm");
                }else if (date.getDate() == getDate() - 2) {//前天
                    return formatTime(date, "前天 HH:mm");
                }else {//本月非今天、昨天、前天
                    return formatTime(date, "MM月dd日 HH:mm");
                }
            }else {//不是本月
                return formatTime(date, "MM月dd日 HH:mm");
            }
        }else {//不是本年
            return formatTime(date, "yyyy年MM月dd日 HH:mm");
        }
    }

    /**
     * 将字符串日期转换为Date类型
     * @param date	字符串日期
     * @param pattern	字符串日期的格式  如规则：yyyy年MM月dd日 HH时mm分ss秒 则返回：xxxx年xx月xx日 xx时xx分xx秒  或  yyyy-MM-dd HH:mm:ss 返回 xxxx-xx-xx xx:xx:xx
     * @return
     */
    public static Date stringToDate(String date, String pattern){
        DateFormat format = new SimpleDateFormat(pattern);
        Date date2 = null;
        try {
            date2 = format.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date2;

    }
}
