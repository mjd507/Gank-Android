package com.android.common;

import android.app.Application;

import com.android.common.logger.AndroidLogAdapter;
import com.android.common.logger.LogLevel;
import com.android.common.logger.Logger;


/**
 * 描述:
 * Created by mjd on 2016/11/24.
 */

public class CommonApplication extends Application {

    private static CommonApplication instance = new CommonApplication();

    private CommonApplication(){

    }

    private static CommonApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Logger
                .init("MJD_TAG")                 // default PRETTYLOGGER or use just init()
                .methodCount(3)                 // default 2
                .hideThreadInfo()               // default shown
                .logLevel(LogLevel.FULL)        // default LogLevel.FULL
                .methodOffset(2)                // default 0
                .logAdapter(new AndroidLogAdapter()); //default AndroidLogAdapter
    }





}
