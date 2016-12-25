package com.android.common.utils;

import android.content.Context;

import com.android.common.CommonApplication;

/**
 * 描述: 获取应用上下文的工具类
 * Created by mjd on 2016/12/19.
 */

public class Utils {

    public static Context getAppContext() {
        return CommonApplication.getInstance();
    }


}
