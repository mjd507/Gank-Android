package com.android.common.utils;

/**
 * 描述: 字符串工具类
 * <p>
 * Created by mjd on 2016/12/19.
 */

public class StringUtils {

    public static boolean isEmpty(CharSequence s) {
        return s == null || s.length() == 0;
    }
}
