package common.test.utilsTest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import common.utils.SDCardUtils;
import common.utils.ToastUtils;

/**
 * 描述:
 * Created by mjd on 2016/12/28.
 * 需添加权限 {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}
 */

public class SDCardUtilsTest extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout contentView = new LinearLayout(this);
        contentView.setOrientation(LinearLayout.VERTICAL);

        Button btnSDCardEnable = new Button(this);
        btnSDCardEnable.setText("SDCard是否可用");
        btnSDCardEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean sdCardEnable = SDCardUtils.isSDCardEnable();
                ToastUtils.showShort(getApplicationContext(), "SDCard是否可用 ? = " + sdCardEnable);
            }
        });

        Button btnSDCardPath = new Button(this);
        btnSDCardPath.setText("SDCard路径");
        btnSDCardPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdCardPath = SDCardUtils.getSDCardPath();
                ToastUtils.showShort(getApplicationContext(), "SDCard路径  = " + sdCardPath);
            }
        });

        Button btnSDCardDataPath = new Button(this);
        btnSDCardDataPath.setText("SDCardData路径");
        btnSDCardDataPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdCardPath = SDCardUtils.getDataPath();
                ToastUtils.showShort(getApplicationContext(), "SDCardData路径  = " + sdCardPath);
            }
        });

        Button btnSDCardFree = new Button(this);
        btnSDCardFree.setText("SDCard剩余空间");
        btnSDCardFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdCardPath = SDCardUtils.getFreeSpace();
                ToastUtils.showShort(getApplicationContext(), "SDCard剩余空间  = " + sdCardPath);
            }
        });

        Button btnSDCardInfo = new Button(this);
        btnSDCardInfo.setText("SDCard信息");
        btnSDCardInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdCardPath = SDCardUtils.getSDCardInfo();
                ToastUtils.showShort(getApplicationContext(), "SDCard信息  = " + sdCardPath);
            }
        });

        contentView.addView(btnSDCardEnable);
        contentView.addView(btnSDCardPath);
        contentView.addView(btnSDCardDataPath);
        contentView.addView(btnSDCardFree);
        contentView.addView(btnSDCardInfo);

        setContentView(contentView);
    }
}
