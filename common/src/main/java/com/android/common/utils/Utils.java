package com.android.common.utils;

import android.content.Context;

/**
 * 描述: 获取应用上下文的工具类
 * Created by mjd on 2016/12/19.
 */

public class Utils {

    public static Context context;

    public static void init(Context context) {
        Utils.context = context.getApplicationContext();
    }
}
