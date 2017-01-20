package common.test;

import android.app.Activity;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import common.utils.LocationUtils;
import common.utils.ToastUtils;

/**
 * 描述: 室内测试失败 参考(http://blog.csdn.net/itleaks/article/details/31511105)
 * Created by mjd on 2017/1/6.
 */

public class LocationUtilsTest extends Activity implements LocationUtils.OnLocationChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout contentView = new LinearLayout(this);
        contentView.setOrientation(LinearLayout.VERTICAL);

        Button btnGPS = new Button(this);
        btnGPS.setText("GPS 是否可用");
        btnGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean gpsEnabled = LocationUtils.isGpsEnabled(getApplicationContext());
                ToastUtils.showShort(getApplicationContext(), gpsEnabled ? "可用" : "不可用");
            }
        });

        Button btnLoc = new Button(this);
        btnLoc.setText("定位 是否可用(net | gps)");
        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean gpsEnabled = LocationUtils.isLocationEnabled(getApplicationContext());
                ToastUtils.showShort(getApplicationContext(), gpsEnabled ? "可用" : "不可用");
            }
        });

        Button btnGPSet = new Button(this);
        btnGPSet.setText("打开 GPS 设置");
        btnGPSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationUtils.openGpsSettings(getApplicationContext());
            }
        });

        Button btnRegister = new Button(this);
        btnRegister.setText("注册定位监听");
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean register = LocationUtils.register(getApplicationContext(), 0, 0, LocationUtilsTest.this);
                ToastUtils.showShort(getApplicationContext(), register ? "注册成功" : "注册失败");
            }
        });

        Button btnUnRegister = new Button(this);
        btnUnRegister.setText("注销定位监听");
        btnUnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationUtils.unregister(getApplicationContext());
            }
        });

        Button btnAddress = new Button(this);
        btnAddress.setText("获取地址");
        btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Address address = LocationUtils.getAddress(getApplicationContext(), location.getLatitude(), location.getLongitude());
                ToastUtils.showShort(getApplicationContext(), address.getFeatureName());
            }
        });

        Button btnCountry = new Button(this);
        btnCountry.setText("获取国家");
        btnCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = LocationUtils.getCountryName(getApplicationContext(), location.getLatitude(), location.getLongitude());
                ToastUtils.showShort(getApplicationContext(), address);
            }
        });

        Button btnLoca = new Button(this);
        btnLoca.setText("获取所在地");
        btnLoca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = LocationUtils.getLocality(getApplicationContext(), location.getLatitude(), location.getLongitude());
                ToastUtils.showShort(getApplicationContext(), address);
            }
        });

        Button btnStreet = new Button(this);
        btnStreet.setText("获取街道");
        btnStreet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = LocationUtils.getStreet(getApplicationContext(), location.getLatitude(), location.getLongitude());
                ToastUtils.showShort(getApplicationContext(), address);
            }
        });

        contentView.addView(btnGPS);
        contentView.addView(btnLoc);
        contentView.addView(btnGPSet);
        contentView.addView(btnRegister);
        contentView.addView(btnUnRegister);
        contentView.addView(btnAddress);
        contentView.addView(btnCountry);
        contentView.addView(btnLoca);
        contentView.addView(btnStreet);

        setContentView(contentView);
    }


    private Location location;

    @Override
    public void getLastKnownLocation(Location location) {
        this.location = location;
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
}

