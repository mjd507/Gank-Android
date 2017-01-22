package com.cleaner.commonandroid.utilsTest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import common.utils.DeviceUtils;
import common.utils.LogUtils;

/**
 * 描述:
 * Created by mjd on 2017/1/5.
 */
@RunWith(AndroidJUnit4.class)
public class DeviceUtilsTest {

    private Context context;

    @Before
    public void getContext() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void getSDKVersion() {
        int sdkVersion = DeviceUtils.getSDKVersion();
        LogUtils.d("DeviceUtilsTest", "SDK Version = " + sdkVersion);
    }

    @Test
    public void getAndroidID() {
        String androidID = DeviceUtils.getAndroidID(context);
        LogUtils.d("DeviceUtilsTest", "设备ID = " + androidID);
    }

    @Test
    public void getManufacturer() {
        String manufacturer = DeviceUtils.getManufacturer();
        LogUtils.d("DeviceUtilsTest", "设备厂商 = " + manufacturer);
    }

    @Test
    public void getModel() {
        String model = DeviceUtils.getModel();
        LogUtils.d("DeviceUtilsTest", "设备型号 = " + model);
    }

    @Test
    public void getMacAddress() {
        String macAddress = DeviceUtils.getMacAddress(context);
        LogUtils.d("DeviceUtilsTest", "Mac 地址 = " + macAddress);
    }
}
