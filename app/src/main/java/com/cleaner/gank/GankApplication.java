package com.cleaner.gank;

import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;

import java.util.LinkedHashMap;
import java.util.Map;

import common.CommonApplication;
import common.utils.AppUtils;

/**
 * 描述:
 * Created by mjd on 2017/2/7.
 */

public class GankApplication extends CommonApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        CrashReport.UserStrategy userStrategy = new CrashReport.UserStrategy(this);
        userStrategy.setAppVersion(AppUtils.getAppVersionName(this));
        userStrategy.setCrashHandleCallback(crashCallback);
        CrashReport.initCrashReport(getApplicationContext(), userStrategy);

        //禁止默认的页面统计方式，这样将不会再自动统计Activity。
        MobclickAgent.openActivityDurationTrack(false);
        /** 设置是否对日志信息进行加密, 默认false(不加密). */
        MobclickAgent.enableEncrypt(true);
    }

    private CrashReport.CrashHandleCallback crashCallback = new CrashReport.CrashHandleCallback() {

        @Override
        public synchronized Map<String, String> onCrashHandleStart(int crashType, String errorType, String errorMessage, String errorStack) {
            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
            map.put(errorMessage, errorStack);
            return map;
        }

        @Override
        public synchronized byte[] onCrashHandleStart2GetExtraDatas(int crashType, String errorType, String errorMessage, String errorStack) {
            try {
                return errorMessage.getBytes("UTF-8");
            } catch (Exception e) {
                return null;
            }
        }
    };
}
