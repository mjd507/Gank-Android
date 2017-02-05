package common.utilsTest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import common.utils.LogUtils;
import common.utils.ScreenUtils;

/**
 * 描述:
 * Created by mjd on 2017/1/22.
 */
@RunWith(AndroidJUnit4.class)
public class ScreenUtilsTest {
    private Context context;

    @Before
    public void getContext() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void getScreenSize() {
        int[] screenSize = ScreenUtils.getScreenSize(context);
        LogUtils.d("ScreenUtilsTest", "手机 宽 : " + screenSize[0] + ", 高 : " + screenSize[1]);
    }

//    @DownLoadTest
//    public void getScreenRotation() {
//        int screenRotation = ScreenUtils.getScreenRotation(context);
//        LogUtils.d("ScreenUtilsTest", "手机旋转的角度为 : " + screenRotation);
//    }
//
//    @DownLoadTest
//    public void captureWithStatusBar() {
//        Bitmap bitmap = ScreenUtils.captureWithStatusBar(context);
//        LogUtils.d("ScreenUtilsTest", "captureWithStatusBar : " + bitmap.getHeight());
//    }
//
//    @DownLoadTest
//    public void captureWithoutStatusBar() {
//        Bitmap bitmap = ScreenUtils.captureWithStatusBar(context);
//        LogUtils.d("ScreenUtilsTest", "captureWithoutStatusBar : " + bitmap.getHeight());
//    }

    @Test
    public void isScreenLock() {
        boolean screenLock = ScreenUtils.isScreenLock(context);
        LogUtils.d("ScreenUtilsTest", "手机锁屏 ? " + screenLock);
    }

}
