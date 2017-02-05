package common.utilsTest;

import android.content.Context;
import android.os.Looper;
import android.os.Message;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import common.utils.HandlerUtils;
import common.utils.LogUtils;

/**
 * 描述:
 * Created by mjd on 2017/1/5.
 */
@RunWith(AndroidJUnit4.class)
public class HandlerUtilsTest implements HandlerUtils.OnReceiveMessageListener {

    private HandlerUtils.WrfHandler mHandler;
    private Context context;

    @Before
    public void before(){
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void sendEmptyMessageDelayed() {
        Looper.prepare();
        mHandler = new HandlerUtils.WrfHandler(this);
        LogUtils.d("HandlerUtilsTest","send msg start");
        mHandler.sendEmptyMessageDelayed(0, 3000);
        Looper.loop();
    }


    @Override
    public void handlerMessage(Message msg) {
        LogUtils.d("HandlerUtilsTest","receive msg ");
        mHandler.removeCallbacksAndMessages(null);
        mHandler.getLooper().quit();
        mHandler = null;
    }

}
