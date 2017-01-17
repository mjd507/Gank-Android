package common.test.utilsTest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import common.netstate.NetworkUtils;
import common.utils.ToastUtils;

/**
 * 描述:
 * Created by mjd on 2016/12/27.
 */

public class NetworkUtilsTest extends Activity {

    boolean isOpen = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout contentView = new LinearLayout(this);
        contentView.setOrientation(LinearLayout.VERTICAL);

        Button btnOpenWirelessSettings = new Button(this);
        btnOpenWirelessSettings.setText("打开网络设置");
        btnOpenWirelessSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkUtils.openWirelessSettings(getApplicationContext());
            }
        });

        Button btnIsConn = new Button(this);
        btnIsConn.setText("网络是否连接");
        btnIsConn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean connected = NetworkUtils.isConnected(getApplicationContext());
                ToastUtils.showShort(getApplicationContext(), "网络是否连接 ? = " + connected);
            }
        });

//        Button btnIsUse = new Button(this);
//        btnIsUse.setText("网络是否可用");
//        btnIsUse.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean isAvailableByPing = NetworkUtils.isAvailableByPing();
//                ToastUtils.showShort(getApplicationContext(), "网络是否可用 ? = " + isAvailableByPing);
//            }
//        });

//        Button btnMobile = new Button(this);
//        btnMobile.setText("移动数据是否打开");
//        btnMobile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean dataEnabled = NetworkUtils.getDataEnabled(getApplicationContext());
//                ToastUtils.showShort(getApplicationContext(), "移动数据是否打开 ? = " + dataEnabled);
//            }
//        });

//        Button btnMobileOpenOrClose = new Button(this);
//        btnMobileOpenOrClose.setText("打开或关闭移动数据");
//        btnMobileOpenOrClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NetworkUtils.setDataEnabled(getApplicationContext(), isOpen);
//                if (isOpen) {
//                    ToastUtils.showShort(getApplicationContext(), "打开了移动数据");
//                } else {
//                    ToastUtils.showShort(getApplicationContext(), "关闭了移动数据");
//                }
//                isOpen = !isOpen;
//            }
//        });

//        Button btnIs4G = new Button(this);
//        btnIs4G.setText("是否 是 4G");
//        btnIs4G.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean is4G = NetworkUtils.is4G(getApplicationContext());
//                ToastUtils.showShort(getApplicationContext(), "是否 是 4G ? = " + is4G);
//            }
//        });
//
//        Button btnWifiEnable = new Button(this);
//        btnWifiEnable.setText("wifi是否打开");
//        btnWifiEnable.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean wifiEnabled = NetworkUtils.getWifiEnabled(getApplicationContext());
//                ToastUtils.showShort(getApplicationContext(), "判断wifi是否打开 ? = " + wifiEnabled);
//            }
//        });

//        Button btnSetWifi = new Button(this);
//        btnSetWifi.setText("打开或关闭wifi");
//        btnSetWifi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NetworkUtils.setWifiEnabled(getApplicationContext(), isOpen);
//                if (isOpen) {
//                    ToastUtils.showShort(getApplicationContext(), "打开了 WIFI");
//                } else {
//                    ToastUtils.showShort(getApplicationContext(), "关闭了 WIFI");
//                }
//                isOpen = !isOpen;
//            }
//        });

        Button btnWifiConn = new Button(this);
        btnWifiConn.setText("wifi 是否连接");
        btnWifiConn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean wifiConnected = NetworkUtils.isWifiConnected(getApplicationContext());
                ToastUtils.showShort(getApplicationContext(), "wifi 是否连接 ? = " + wifiConnected);
            }
        });


//        Button btnWifiUse = new Button(this);
//        btnWifiUse.setText("wifi 是否可用");
//        btnWifiUse.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean wifiAvailable = NetworkUtils.isWifiAvailable(getApplicationContext());
//                ToastUtils.showShort(getApplicationContext(), "wifi 是否可用 ? = " + wifiAvailable);
//            }
//        });
//
//        Button btnNetName = new Button(this);
//        btnNetName.setText("网络运营商名称");
//        btnNetName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String networkOperatorName = NetworkUtils.getNetworkOperatorName(getApplicationContext());
//                ToastUtils.showShort(getApplicationContext(), "网络运营商名称 = " + networkOperatorName);
//            }
//        });

        Button btnCurNet = new Button(this);
        btnCurNet.setText("当前网络类型");
        btnCurNet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkUtils.NetworkType networkType = NetworkUtils.getNetworkType(getApplicationContext());
                String type = "";
                switch (networkType) {
//                    case NETWORK_2G:
//                        type = "2G";
//                        break;
//                    case NETWORK_3G:
//                        type = "3G";
//                        break;
//                    case NETWORK_4G:
//                        type = "4G";
//                        break;
                    case NETWORK_WIFI:
                        type = "WIFI";
                        break;
//                    case NETWORK_UNKNOWN:
//                        type = "手机在线";
//                        break;
                    case NETWORK_NONE:
                        break;
                }
                ToastUtils.showShort(getApplicationContext(), "当前网络类型 = " + type);
            }
        });

//        Button btnIP = new Button(this);
//        btnIP.setText("IP地址");
//        btnIP.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String ipAddress = NetworkUtils.getIPAddress(true);
//                ToastUtils.showShort(getApplicationContext(), "IP地址 = " + ipAddress);
//            }
//        });
//        Button btnDomainIP = new Button(this);
//        btnDomainIP.setText("百度IP地址");
//        btnDomainIP.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String ipAddress = NetworkUtils.getDomainAddress("www.baidu.com");
//                ToastUtils.showShort(getApplicationContext(), "百度IP地址 = " + ipAddress);
//            }
//        });


        contentView.addView(btnOpenWirelessSettings);
        contentView.addView(btnIsConn);
//        contentView.addView(btnIsUse);
//        contentView.addView(btnMobile);
//        contentView.addView(btnMobileOpenOrClose);
//        contentView.addView(btnIs4G);
//        contentView.addView(btnWifiEnable);
//        contentView.addView(btnSetWifi);
//        contentView.addView(btnWifiConn);
//        contentView.addView(btnWifiUse);
//        contentView.addView(btnNetName);
        contentView.addView(btnCurNet);
//        contentView.addView(btnIP);
//        contentView.addView(btnDomainIP);


        ScrollView scrollView = new ScrollView(this);
        scrollView.addView(contentView);

        setContentView(scrollView);

    }
}
