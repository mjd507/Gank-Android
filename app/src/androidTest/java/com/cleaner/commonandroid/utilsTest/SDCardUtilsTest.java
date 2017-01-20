package com.cleaner.commonandroid.utilsTest;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import common.utils.LogUtils;
import common.utils.SDCardUtils;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * 描述:
 * Created by mjd on 2016/12/28.
 * 需添加权限 {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}
 */
@RunWith(AndroidJUnit4.class)
public class SDCardUtilsTest {

    @Test
    public void isSDCardEnable(){
        boolean sdCardEnable = SDCardUtils.isSDCardEnable();
        assertTrue(sdCardEnable);
    }

    @Test
    public void showSDCardPath(){
        String sdCardPath = SDCardUtils.getSDCardPath();
        assertEquals("/storage/emulated/0/",sdCardPath);
    }

    @Test
    public void showSDCardDataPath(){
        String sdCardPath = SDCardUtils.getDataPath();
        assertEquals("/storage/emulated/0/data/",sdCardPath);
    }

    @Test
    public void getFreeSpace(){
        String freeSpace = SDCardUtils.getFreeSpace();
        LogUtils.d("SDCardUtilsTest","SD 卡剩余空间: "+freeSpace);
    }


    @Test
    public void getSDCardInfo(){
        String SDCardInfo = SDCardUtils.getSDCardInfo();
        LogUtils.d("SDCardUtilsTest","SD 卡 信息: "+SDCardInfo);
    }
}
