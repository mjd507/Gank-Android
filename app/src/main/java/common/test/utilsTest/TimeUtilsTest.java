package common.test.utilsTest;

import org.junit.Test;

import java.util.Date;

import common.utils.TimeUtils;

/**
 * 描述:
 * Created by mjd on 2016/12/23.
 */

public class TimeUtilsTest {

    //---------------- millis <=> String  millis <==> date  String <=> date  start  -------------

    @Test
    public void millis2String() {
        long time = new Date().getTime();
        String s = TimeUtils.millis2String(time);
        System.out.println(s);
        String s2 = TimeUtils.millis2String(time, "yyyy-MM-dd HH:mm");
        System.out.println(s2);
    }
//
//    @Test
//    public void string2Millis() {
//        String time = "2016-12-23 10:22:19";
//        long l = TimeUtils.string2Millis(time);
//        System.out.println(l);
//        long l2 = TimeUtils.string2Millis(time, "yyyy-MM-dd HH:mm");
//        System.out.println(l2);
//    }
//
//    @Test
//    public void string2Date() {
//        String time = "2016-12-23 10:22:19";
//        Date date = TimeUtils.string2Date(time);
//        System.out.println(date);
//        Date date1 = TimeUtils.string2Date(time, "yyyy-MM-dd HH:mm");
//        System.out.println(date1);
//    }

    @Test
    public void date2String() {
        Date date = new Date();
        String s = TimeUtils.date2String(date);
        System.out.println(s);
        String s2 = TimeUtils.date2String(date, "yyyy-MM-dd HH:mm");
        System.out.println(s2);
    }

    @Test
    public void date2MillisAndMiles2Date() {
        Date date = new Date();
        long l = TimeUtils.date2Millis(date);
        System.out.println(l);
        Date date1 = TimeUtils.millis2Date(l);
        System.out.println(date1);
    }

    //---------------- 获取合适型两个时间差 返回 String 类型的 start ----------------
    /**
     * precision = 0，返回null
     * precision = 1，返回天
     * precision = 2，返回天和小时
     * precision = 3，返回天、小时和分钟
     * precision = 4，返回天、小时、分钟和秒
     * precision >= 5，返回天、小时、分钟、秒和毫秒
     */
    @Test
    public void getFitTimeSpan() throws InterruptedException {
        String time1 = "2016-12-23 10:22:19";
        String time2 = "2016-12-23 10:23:20";
//        String fitTimeSpan = TimeUtils.getFitTimeSpan(time1, time2, 5);
//        System.out.println(fitTimeSpan);
//        String fitTimeSpan1 = TimeUtils.getFitTimeSpan(time1, time2, 3, "yyyy-MM-dd HH:mm");
//        System.out.println(fitTimeSpan1);
        Date date = new Date();
        Thread.sleep(3000);
        Date date1 = new Date();
//        String fitTimeSpan2 = TimeUtils.getFitTimeSpan(date, date1, 5);
//        System.out.println(fitTimeSpan2);
        long t1 = 1482463000;
        long t2 = 1482463900;
        String fitTimeSpan3 = TimeUtils.getFitTimeSpan(t1, t2, 5);
        System.out.println(fitTimeSpan3);
    }


    //---------------- 获取当前时间 start ----------------
    @Test
    public void getCurrentTime(){
//        long nowTimeMills = TimeUtils.getNowTimeMills();
//        System.out.println(nowTimeMills);
//        String nowTimeString = TimeUtils.getNowTimeString();
//        System.out.println(nowTimeString);
//        String nowTimeString1 = TimeUtils.getNowTimeString("yyyy-MM-dd HH:mm");
//        System.out.println(nowTimeString1);
//        Date nowTimeDate = TimeUtils.getNowTimeDate();
//        System.out.println(nowTimeDate);
    }

    //---------------- 获取合适型与当前时间的差 start ----------------
    @Test
    public void getFitTimeSpanByNow() throws InterruptedException {
//        String fitTimeSpanByNow = TimeUtils.getFitTimeSpanByNow(System.currentTimeMillis(), 5);
//        System.out.println(fitTimeSpanByNow);
//        String time = "2016-12-21 02:23:24";
//        String fitTimeSpanByNow1 = TimeUtils.getFitTimeSpanByNow(time, 5);
//        System.out.println(fitTimeSpanByNow1);
//        String fitTimeSpanByNow2 = TimeUtils.getFitTimeSpanByNow(time, 5, "yyyy-MM-dd HH:mm");
//        System.out.println(fitTimeSpanByNow2);
//        Date date = TimeUtils.string2Date(time);
//        String fitTimeSpanByNow3 = TimeUtils.getFitTimeSpanByNow(date, 5);
//        System.out.println(fitTimeSpanByNow3);
    }

    //---------------- 获取友好型与当前时间的差 start ----------------
    @Test
    public void getFriendlyTimeSpanByNow(){
        String friendlyTimeSpanByNow = TimeUtils.getFriendlyTimeSpanByNow(System.currentTimeMillis());
        System.out.println(friendlyTimeSpanByNow);
//        String time = "2016-12-23 14:55:45";
//        String friendlyTimeSpanByNow1 = TimeUtils.getFriendlyTimeSpanByNow(time);
//        System.out.println(friendlyTimeSpanByNow1);
//        String time1 = "2016-12-20 14:55:45";
//        String friendlyTimeSpanByNow2 = TimeUtils.getFriendlyTimeSpanByNow(time1, "yyyy-MM-dd HH:mm");
//        System.out.println(friendlyTimeSpanByNow2);
//        String friendlyTimeSpanByNow3 = TimeUtils.getFriendlyTimeSpanByNow(new Date());
//        System.out.println(friendlyTimeSpanByNow3);
    }


    //---------------- 是否是同一天(今天) start ----------------
    @Test
    public void isSameDay(){
//        boolean sameDay = TimeUtils.isSameDay(new Date());
//        System.out.println(sameDay);
//        boolean sameDay1 = TimeUtils.isSameDay("2016-12-23 22:22:22");
//        System.out.println(sameDay1);
//        boolean sameDay2 = TimeUtils.isSameDay("2016-12-22 22:22:22","yyyy-MM-dd HH:mm");
//        System.out.println(sameDay2);
        boolean sameDay3 = TimeUtils.isSameDay(new Date().getTime());
        System.out.println(sameDay3);
    }

    //---------------- 是否是闰年 start ----------------
    @Test
    public void isLeapYear(){
//        boolean leapYear = TimeUtils.isLeapYear(new Date());
//        System.out.println(leapYear);
//        boolean leapYear1 = TimeUtils.isLeapYear("2016-10-10 10:00:00");
//        System.out.println(leapYear1);
        boolean leapYear2 = TimeUtils.isLeapYear(new Date().getTime());
        System.out.println(leapYear2);
        boolean leapYear3 = TimeUtils.isLeapYear(2012);
        System.out.println(leapYear3);
//        boolean leapYear4 = TimeUtils.isLeapYear("2016-10-10 10:00:00","yyyy");
//        System.out.println(leapYear);
    }

    @Test
    public void getWeek(){
        String week = TimeUtils.getWeek(new Date().getTime());
        System.out.println(week);
    }


    @Test
    public void getWeekIndex(){
        int week = TimeUtils.getWeekIndex(new Date().getTime());
        System.out.println(week);
    }


    @Test
    public void getWeekOfMonth(){
        int week = TimeUtils.getWeekOfMonth(new Date().getTime());
        System.out.println(week);
    }
    @Test
    public void getWeekOfYear(){
        int week = TimeUtils.getWeekOfYear(new Date().getTime());
        System.out.println(week);
    }

    @Test
    public void getChineseZodiac(){
        String chineseZodiac = TimeUtils.getChineseZodiac(1995);
        System.out.println(chineseZodiac);
        String chineseZodiac1 = TimeUtils.getChineseZodiac(new Date().getTime());
        System.out.println(chineseZodiac1);
    }

    @Test
    public void getZodiac(){
        String zodiac = TimeUtils.getZodiac(new Date().getTime());
        System.out.println(zodiac);
        String zodiac1 = TimeUtils.getZodiac(11, 7);
        System.out.println(zodiac1);
    }

}
