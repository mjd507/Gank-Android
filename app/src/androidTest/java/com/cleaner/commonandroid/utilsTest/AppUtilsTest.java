package com.cleaner.commonandroid.utilsTest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import common.utils.AppUtils;
import common.utils.LogUtils;
import common.utils.SDCardUtils;

/**
 * 描述:
 * Created by mjd on 2016/12/28.
 */
@RunWith(AndroidJUnit4.class)
public class AppUtilsTest {

    private Context context;

    @Before
    public void getContext() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void installApp() {
        AppUtils.installApp(context, new File(SDCardUtils.getSDCardPath() + "test.apk"));
    }

    @Test
    public void getAppInfo() {
        String appVersionName = AppUtils.getAppVersionName(context);
        int appVersionCode = AppUtils.getAppVersionCode(context);
        LogUtils.d("AppUtilsTest", "版本号:" + appVersionName + "  版本码:" + appVersionCode);
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        LinearLayout contentView = new LinearLayout(this);
//        contentView.setOrientation(LinearLayout.VERTICAL);
//
//        Button btnInstallApp = new Button(this);
//        btnInstallApp.setText("安装下载好的 app");
//        btnInstallApp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AppUtils.installApp(getApplicationContext(), new File(SDCardUtils.getSDCardPath() + "SDCardUtilsTest.apk"));
//            }
//        });
//
//
//        Button btnAppInfo = new Button(this);
//        btnAppInfo.setText("app 版本号 和 版本码");
//        btnAppInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String appVersionName = AppUtils.getAppVersionName(getApplicationContext());
//                int appVersionCode = AppUtils.getAppVersionCode(getApplicationContext());
//                ToastUtils.showShort(getApplicationContext(), "版本号:" + appVersionName + "  版本码:" + appVersionCode);
//            }
//        });
//
//        Button btnAppForeground = new Button(this);
//        btnAppForeground.setText("app 是否在前台");
//        btnAppForeground.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean appForeground = AppUtils.isAppForeground(getApplicationContext());
//                ToastUtils.showShort(getApplicationContext(), appForeground ? "app在前台" : "app进入后台");
//            }
//        });
//
//
//        Button btnAppClean = new Button(this);
//        btnAppClean.setText("清除 app 数据");
//        btnAppClean.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean b = AppUtils.cleanAppData(getApplicationContext(), new File[]{});
//                ToastUtils.showShort(getApplicationContext(), b ? "清除成功" : "清除失败");
//            }
//        });
//
//        contentView.addView(btnInstallApp);
//        contentView.addView(btnAppInfo);
//        contentView.addView(btnAppForeground);
//        contentView.addView(btnAppClean);
//
//
//        setContentView(contentView);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        boolean appForeground = AppUtils.isAppForeground(getApplicationContext());
//        ToastUtils.showShort(getApplicationContext(), appForeground ? "app在前台" : "app进入后台");
//    }
//
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        boolean appForeground = AppUtils.isAppForeground(getApplicationContext());
//        ToastUtils.showShort(getApplicationContext(), appForeground ? "app在前台" : "app进入后台");
//    }
}
