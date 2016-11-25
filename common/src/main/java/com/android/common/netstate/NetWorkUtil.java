package com.android.common.netstate;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 描述: 检测网络的工具类
 */
public class NetWorkUtil {
    public static enum NetType {
        wifi, CMNET, CMWAP, noneNet
    }

    /**
     * 网络是否可用
     *
     * @return 可用返回true, 否则返回false
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager mgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info = mgr.getAllNetworkInfo();
        if (info != null) {
            for (int i = 0; i < info.length; i++) {
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断WIFI网络是否可用
     *
     * @return 可用返回true, 否则返回false
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断MOBILE网络是否可用
     *
     * @return 可用返回true, 否则返回false
     */
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 获取网络类型
     *
     * @return 返回网络类型
     */
    public static NetType getNetType(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return NetType.noneNet;
        }
        int nType = networkInfo.getType();

        if (nType == ConnectivityManager.TYPE_MOBILE) {
            if ("cmnet".equalsIgnoreCase(networkInfo.getExtraInfo())) {
                return NetType.CMNET;
            } else {
                return NetType.CMWAP;
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            return NetType.wifi;
        }
        return NetType.noneNet;
    }

    /**
     * 注册网络状态广播
     */
    public static NetworkStateReceiver registerNetworkStateReceiver(NetworkStateReceiver receiver, Context mContext) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(NetworkStateReceiver.ANDROID_NET_CHANGE_ACTION);
        mContext.getApplicationContext().registerReceiver(receiver, filter);
        return receiver;
    }

    /**
     * 注销网络状态广播
     */
    public static void unRegisterNetworkStateReceiver(NetworkStateReceiver receiver, Context mContext) {
        try {
            mContext.getApplicationContext().unregisterReceiver(receiver);

        } catch (Exception e) {

        } finally {
            receiver.dstroyInstance();
        }
    }
}
