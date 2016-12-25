package com.android.common;

import android.app.Application;

import com.android.common.utils.logger.AndroidLogAdapter;
import com.android.common.utils.logger.LogLevel;
import com.android.common.utils.logger.Logger;


/**
 * 描述:
 * Created by mjd on 2016/11/24.
 */

public class CommonApplication extends Application {

    private static CommonApplication application;

    public static CommonApplication getInstance() {
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
