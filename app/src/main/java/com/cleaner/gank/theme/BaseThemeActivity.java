package com.cleaner.gank.theme;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cleaner.commonandroid.R;

import common.ui.BaseActivity;
import common.utils.SPUtils;

/**
 * 描述:
 * Created by mjd on 2017/2/16.
 */

public class BaseThemeActivity extends BaseActivity {
    public static final String THEME = "theme";
    protected Colorful colorful;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int themeId = SPUtils.getInstence().getInt(this, THEME, 1);
        colorful = new Colorful.Builder(this).create(); //静态内部类 相当于单例 不会造成内存泄漏，放心 new
        if (themeId == 1) {
            colorful.setTheme(R.style.Theme1);
        } else if (themeId == 2) {
            colorful.setTheme(R.style.Theme2);
        } else if (themeId == 3) {
            colorful.setTheme(R.style.Theme3);
        } else if (themeId == 4) {
            colorful.setTheme(R.style.Theme4);
        }

    }
}
