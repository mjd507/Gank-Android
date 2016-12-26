package com.cleaner.commonandroid.libTest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.android.common.utils.LogUtils;
import com.android.common.utils.ScreenUtils;

import static android.content.ContentValues.TAG;

/**
 * 描述:
 * Created by mjd on 2016/12/26.
 */

public class ScreenUtilsTest extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        getScreenSize();
        getScreenRotation();


        HorizontalScrollView scrollView = new HorizontalScrollView(this);
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ImageView iv = new ImageView(this);
        Bitmap bitmap = captureWithStatusBar(this);
        iv.setImageBitmap(bitmap);

        ImageView iv2 = new ImageView(this);
        Bitmap bitmap2 = captureWithoutStatusBar(this);
        iv2.setImageBitmap(bitmap2);

        scrollView.addView(iv);
        scrollView.addView(iv2);

        setContentView(scrollView);

    }

    public void getScreenSize() {
        int[] screenSize = ScreenUtils.getScreenSize();
        LogUtils.d("ScreenUtilsTest", "手机 宽 : " + screenSize[0] + ", 高 = " + screenSize[1]);
    }

    public void getScreenRotation() {
        int screenRotation = ScreenUtils.getScreenRotation(this);
        LogUtils.d("ScreenUtilsTest", "手机旋转的角度为 : " + screenRotation);
    }


    public static Bitmap captureWithStatusBar(Activity activity) {
        Bitmap bitmap = ScreenUtils.captureWithStatusBar(activity);
        return bitmap;
    }

    public static Bitmap captureWithoutStatusBar(Activity activity) {
        Bitmap bitmap = ScreenUtils.captureWithoutStatusBar(activity);
        return bitmap;
    }

    public boolean isScreenLock() {
        boolean screenLock = ScreenUtils.isScreenLock();
        LogUtils.d(TAG, "手机锁屏 ? " + screenLock);
        return screenLock;
    }

}
