package common.utilsTest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import common.utils.BarUtils;
import common.utils.LogUtils;

/**
 * 描述:
 * Created by mjd on 2017/1/20.
 */
@RunWith(AndroidJUnit4.class)
public class BarUtilsTest {


    private Context context;

    @Before
    public void getContext() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void barHeight() {
        int statusBarHeight = BarUtils.getStatusBarHeight(context);
        LogUtils.d("BarUtilsTest", "状态栏高度:" + statusBarHeight);
    }

}