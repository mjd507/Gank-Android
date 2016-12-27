package common.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 描述: SharedPreferences 工具类
 * Created by mjd on 2016/11/24.
 */

public class SPUtils {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;


    public SPUtils(Context context, String name, int mode) {
        this.sp = context.getApplicationContext().getSharedPreferences(name, mode);
        this.editor = this.sp.edit();
    }

    public SharedPreferences.Editor getEditor() {
        return this.editor;
    }

    public void putBoolean(String key, boolean value) {
        this.editor.putBoolean(key, value).commit();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return this.sp.getBoolean(key, defValue);
    }

    public void putFloat(String key, float value) {
        this.editor.putFloat(key, value).commit();
    }

    public float getFloat(String key, float defValue) {
        return this.sp.getFloat(key, defValue);
    }

    public void putInt(String key, int value) {
        this.editor.putInt(key, value).commit();
    }

    public int getInt(String key, int defValue) {
        return this.sp.getInt(key, defValue);
    }

    public void putLong(String key, long value) {
        this.editor.putLong(key, value).commit();
    }

    public long getLong(String key, long defValue) {
        return this.sp.getLong(key, defValue);
    }

    public void putString(String key, String value) {
        this.editor.putString(key, value).commit();
    }

    public String getString(String key, String defValue) {
        return this.sp.getString(key, defValue);
    }

    public void remove(String key) {
        this.editor.remove(key).commit();
    }

}


