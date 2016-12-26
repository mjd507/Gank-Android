package com.android.common.utils;

import android.content.Context;

/**
 * 描述:
 * Created by mjd on 2016/12/26.
 */

public class BarUtils {

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = -1;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
