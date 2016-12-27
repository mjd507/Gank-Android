package com.cleaner.commonandroid;

import android.app.Application;

import common.utils.logger.AndroidLogAdapter;
import common.utils.logger.LogLevel;
import common.utils.logger.Logger;

/**
 * 描述:
 * Created by mjd on 2016/12/26.
 */

public class MyApplication extends Application {

    private static MyApplication application;

    public static MyApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;

        Logger
                .init("MJD_TAG")                 // default PRETTYLOGGER or use just init()
                .methodCount(3)                 // default 2
                .hideThreadInfo()               // default shown
                .logLevel(LogLevel.FULL)        // default LogLevel.FULL
                .methodOffset(2)                // default 0
                .logAdapter(new AndroidLogAdapter()); //default AndroidLogAdapter
    }
}
