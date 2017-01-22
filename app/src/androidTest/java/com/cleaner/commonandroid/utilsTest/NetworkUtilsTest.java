package com.cleaner.commonandroid.utilsTest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import common.netstate.NetworkUtils;
import common.utils.LogUtils;

/**
 * 描述:
 * Created by mjd on 2016/12/27.
 */
@RunWith(AndroidJUnit4.class)
public class NetworkUtilsTest{


    private Context context;

    @Before
    public void getContext() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void openWirelessSettings() {
        NetworkUtils.openWirelessSettings(context);
    }

    @Test
    public void isConnected() {
        boolean connected = NetworkUtils.isConnected(context);
        LogUtils.d("NetworkUtilsTest", "网络是否连接 ? = " + connected);
    }

    @Test
    public void isWifiConnected() {
        boolean wifiConnected = NetworkUtils.isWifiConnected(context);
        LogUtils.d("NetworkUtilsTest", "wifi 是否连接 ? = " + wifiConnected);
    }

    @Test
    public void getNetworkType() {
        NetworkUtils.NetworkType networkType = NetworkUtils.getNetworkType(context);
        String type = "";
        switch (networkType) {
            case NETWORK_MOBILE:
                type = "2/3/4 G";
                break;
            case NETWORK_WIFI:
                type = "WIFI";
                break;
            case NETWORK_NONE:
                type = "断开";
                break;
        }
        LogUtils.d("NetworkUtilsTest", "当前网络类型 = " + type);
    }


}
