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
    private static SPUtils spUtils;

    private SPUtils() {

    }

    public static SPUtils getInstence() {
        if (spUtils == null) {
            spUtils = new SPUtils();
        }
        return spUtils;
    }

    public void init(Context context) {
        sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void putString(String key, String value) {

        sp.edit().putString(key, value).apply();
    }

    public String getString(String key, String defaultValue) {

        return sp.getString(key, defaultValue);
    }

    public void putInt(String key, int value) {

        sp.edit().putInt(key, value).apply();
    }

    public int getInt(Context context, String key, int defaultValue) {

        return sp.getInt(key, defaultValue);
    }

    public void putBoolean(String key, boolean value) {

        sp.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defaultValue) {

        return sp.getBoolean(key, defaultValue);
    }

    public void putLong(String key, long value) {

        sp.edit().putLong(key, value).apply();
    }

    public long getLong(String key, long defaultValue) {

        return sp.getLong(key, defaultValue);
    }


}


