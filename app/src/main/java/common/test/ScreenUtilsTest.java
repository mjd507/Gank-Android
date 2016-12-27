package common.test;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.common.api.GoogleApiClient;

import common.utils.LogUtils;
import common.utils.ScreenUtils;
import common.utils.ToastUtils;

/**
 * 描述:
 * Created by mjd on 2016/12/26.
 */

public class ScreenUtilsTest extends Activity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private ImageView ivFull;
    private ImageView ivNoFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout contentView = new LinearLayout(this);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.setOrientation(LinearLayout.VERTICAL);
        Button btnScreenSize = new Button(this);
        btnScreenSize.setText("屏幕大小");
        btnScreenSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] screenSize = ScreenUtils.getScreenSize(ScreenUtilsTest.this);
                LogUtils.d("ScreenUtilsTest", "手机 宽 : " + screenSize[0] + ", 高 : " + screenSize[1]);
                ToastUtils.showShort(ScreenUtilsTest.this, "手机 宽 : " + screenSize[0] + ", 高 = " + screenSize[1]);
            }
        });
        Button btnScreenRotation = new Button(this);
        btnScreenRotation.setText("屏幕角度");
        btnScreenRotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int screenRotation = ScreenUtils.getScreenRotation(ScreenUtilsTest.this);
                LogUtils.d("ScreenUtilsTest", "手机旋转的角度为 : " + screenRotation);
                ToastUtils.showShort(ScreenUtilsTest.this, "手机旋转的角度为 : " + screenRotation);
            }
        });
        Button btnCaptureWithStatusBar = new Button(this);
        btnCaptureWithStatusBar.setText("全屏截屏");
        btnCaptureWithStatusBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = ScreenUtils.captureWithStatusBar(ScreenUtilsTest.this);
                ivFull.setImageBitmap(bitmap);
            }
        });
        Button btnCaptureWithOutStatusBar = new Button(this);
        btnCaptureWithOutStatusBar.setText("无状态栏截屏");
        btnCaptureWithOutStatusBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = ScreenUtils.captureWithoutStatusBar(ScreenUtilsTest.this);
                ivNoFull.setImageBitmap(bitmap);
            }
        });
        Button isLock = new Button(this);
        isLock.setText("手机锁屏?");
        isLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean screenLock = ScreenUtils.isScreenLock(ScreenUtilsTest.this);
                LogUtils.d("ScreenUtilsTest", "手机锁屏 ? " + screenLock);
                ToastUtils.showShort(ScreenUtilsTest.this, "手机锁屏 ? " + screenLock);
            }
        });

        ivFull = new ImageView(this);
        ivNoFull = new ImageView(this);
        LinearLayout images = new LinearLayout(this);
        images.addView(ivFull);
        images.addView(ivNoFull);
        HorizontalScrollView scrollView = new HorizontalScrollView(this);
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        scrollView.addView(images);

        contentView.addView(btnScreenSize);
        contentView.addView(btnScreenRotation);
        contentView.addView(btnCaptureWithStatusBar);
        contentView.addView(btnCaptureWithOutStatusBar);
        contentView.addView(isLock);
        contentView.addView(scrollView);

        setContentView(contentView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean screenLock = ScreenUtils.isScreenLock(ScreenUtilsTest.this);
                LogUtils.d("ScreenUtilsTest", "手机锁屏 ? " + screenLock);
                ToastUtils.showShort(ScreenUtilsTest.this, "手机锁屏 ? " + screenLock);
            }
        }, 5000);

    }
}
