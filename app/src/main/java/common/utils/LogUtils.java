package common.utils;

import android.util.Log;

/**
 * 描述: 简单调试 Log 的工具类
 * Created by mjd on 2016/12/12.
 */

public class LogUtils {

    public static boolean IS_DEBUG = true;

    public static void d(String tag, String msg) {
        if (IS_DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable e) {
        if (IS_DEBUG) {
            Log.d(tag, msg, e);
        }
    }

    public static void i(String tag, String msg) {
        if (IS_DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable e) {
        if (IS_DEBUG) {
            Log.i(tag, msg, e);
        }
    }

    public static void e(String tag, String msg) {
        if (IS_DEBUG) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable e) {
        if (IS_DEBUG) {
            Log.e(tag, msg, e);
        }
    }


}
