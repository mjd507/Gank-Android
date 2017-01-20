package common.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import common.utils.DeviceUtils;
import common.utils.ToastUtils;

/**
 * 描述:
 * Created by mjd on 2017/1/5.
 */

public class DeviceUtilsTest extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout contentView = new LinearLayout(this);
        contentView.setOrientation(LinearLayout.VERTICAL);
        Button btnSDKVersion = new Button(this);
        btnSDKVersion.setText("Android版本");
        btnSDKVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sdkVersion = DeviceUtils.getSDKVersion();
                ToastUtils.showShort(getApplicationContext(), String.valueOf(sdkVersion));
            }
        });
        Button btnAndroidID = new Button(this);
        btnAndroidID.setText("设备ID");
        btnAndroidID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String androidID = DeviceUtils.getAndroidID(getApplicationContext());
                ToastUtils.showShort(getApplicationContext(), androidID);
            }
        });

        Button btnManufacturer = new Button(this);
        btnManufacturer.setText("设备厂商");
        btnManufacturer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String manufacturer = DeviceUtils.getManufacturer();
                ToastUtils.showShort(getApplicationContext(), manufacturer);
            }
        });

        Button btnModel = new Button(this);
        btnModel.setText("设备型号");
        btnModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String model = DeviceUtils.getModel();
                ToastUtils.showShort(getApplicationContext(), model);
            }
        });


        Button btnMacAddress = new Button(this);
        btnMacAddress.setText("Mac 地址");
        btnMacAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String macAddress = DeviceUtils.getMacAddress(getApplicationContext());
                ToastUtils.showShort(getApplicationContext(), macAddress);
            }
        });

        contentView.addView(btnSDKVersion);
        contentView.addView(btnAndroidID);
        contentView.addView(btnManufacturer);
        contentView.addView(btnModel);
        contentView.addView(btnMacAddress);
        setContentView(contentView);

    }
}
