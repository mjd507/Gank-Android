package com.cleaner.commonandroid.utils;

import android.content.Context;

import com.cleaner.commonandroid.MyApplication;

/**
 * 描述:
 * Created by mjd on 2016/12/26.
 */

public class Utils {


    public static Context getAppContext() {
        return MyApplication.getInstance();
    }

    public static Context getInstance() {
        return MyApplication.getInstance();
    }
}
