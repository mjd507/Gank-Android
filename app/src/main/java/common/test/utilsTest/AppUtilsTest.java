package common.test.utilsTest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.File;

import common.utils.AppUtils;
import common.utils.SDCardUtils;
import common.utils.ToastUtils;

/**
 * 描述:
 * Created by mjd on 2016/12/28.
 */

public class AppUtilsTest extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout contentView = new LinearLayout(this);
        contentView.setOrientation(LinearLayout.VERTICAL);

        Button btnInstallApp = new Button(this);
        btnInstallApp.setText("安装下载好的 app");
        btnInstallApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.installApp(getApplicationContext(), new File(SDCardUtils.getSDCardPath() + "SDCardUtilsTest.apk"));
            }
        });


        Button btnAppInfo = new Button(this);
        btnAppInfo.setText("app 版本号 和 版本码");
        btnAppInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String appVersionName = AppUtils.getAppVersionName(getApplicationContext());
                int appVersionCode = AppUtils.getAppVersionCode(getApplicationContext());
                ToastUtils.showShort(getApplicationContext(), "版本号:" + appVersionName + "  版本码:" + appVersionCode);
            }
        });

        Button btnAppForeground = new Button(this);
        btnAppForeground.setText("app 是否在前台");
        btnAppForeground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean appForeground = AppUtils.isAppForeground(getApplicationContext());
                ToastUtils.showShort(getApplicationContext(), appForeground ? "app在前台" : "app进入后台");
            }
        });


        Button btnAppClean = new Button(this);
        btnAppClean.setText("清除 app 数据");
        btnAppClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean b = AppUtils.cleanAppData(getApplicationContext(), new File[]{});
                ToastUtils.showShort(getApplicationContext(), b ? "清除成功" : "清除失败");
            }
        });

        contentView.addView(btnInstallApp);
        contentView.addView(btnAppInfo);
        contentView.addView(btnAppForeground);
        contentView.addView(btnAppClean);


        setContentView(contentView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean appForeground = AppUtils.isAppForeground(getApplicationContext());
        ToastUtils.showShort(getApplicationContext(), appForeground ? "app在前台" : "app进入后台");
    }


    @Override
    protected void onStop() {
        super.onStop();
        boolean appForeground = AppUtils.isAppForeground(getApplicationContext());
        ToastUtils.showShort(getApplicationContext(), appForeground ? "app在前台" : "app进入后台");
    }
}
