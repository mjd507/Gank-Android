package common.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 描述: SharedPreferences 工具类
 * Created by mjd on 2016/11/24.
 */

public class SPUtils {
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;


    public SPUtils(Context context,String name, int mode) {
        this.sp = context.getApplicationContext().getSharedPreferences(name, mode);
        this.edit = this.sp.edit();
    }

    /**
     * @return 返回器
     */
    public SharedPreferences.Editor getEdit() {
        return this.edit;
    }


    public void putBoolean(String key, boolean value) {
        this.edit.putBoolean(key, value).commit();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return this.sp.getBoolean(key, defValue);
    }

    public void putInt(String key, int value) {
        this.edit.putInt(key, value).commit();
    }

    public int getInt(String key, int defValue) {
        return this.sp.getInt(key, defValue);
    }

    public void putString(String key, String value) {
        this.edit.putString(key, value).commit();
    }

    public String getString(String key, String defValue) {
        return this.sp.getString(key, defValue);
    }

    public void remove(String key) {
        this.edit.remove(key).commit();
    }

}


