package common.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 描述: SharedPreferences 工具类
 * Created by mjd on 2016/11/24.
 */

public class SPUtils {
    private static final String SHARED_PREFERENCES_NAME = "common";
    private static SharedPreferences sp;

    public static void putString(Context context, String key, String value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }

    public static String getString(Context context, String key, String defaultValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        }
        return sp.getString(key, defaultValue);
    }

    public static void putInt(Context context, String key, int value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key, value).commit();
    }

    public static int getInt(Context context, String key, int defaultValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        }
        return sp.getInt(key, defaultValue);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defaultValue);
    }

    public static void putLong(Context context, String key, long value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putLong(key, value).commit();
    }

    public static long getLong(Context context, String key, long defaultValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        }
        return sp.getLong(key, defaultValue);
    }


}


