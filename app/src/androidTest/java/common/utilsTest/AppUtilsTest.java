package common.utilsTest;

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

    @Test
    public void isForeground() {
        boolean appForeground = AppUtils.isAppForeground(context);
        LogUtils.d("AppUtilsTest", "isForeground:" + appForeground);
    }

    @Test
    public void cleanData(){
        boolean b = AppUtils.cleanAppData(context, new File[]{});
        LogUtils.d("AppUtilsTest", b ? "清除成功" : "清除失败");
    }

}
