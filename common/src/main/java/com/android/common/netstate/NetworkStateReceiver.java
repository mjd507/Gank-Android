package com.android.common.netstate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.common.netstate.NetWorkUtil.NetType;

import java.util.ArrayList;

/**
 * 描述: 是一个检测网络状态改变的 需要开启权限
 * <uses-permission
 * android:name="android.permission.ACCESS_NETWORK_STATE"/>
 */
public class NetworkStateReceiver extends BroadcastReceiver {
    public final static String ANDROID_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    private static NetworkStateReceiver receiver;
    private ArrayList<NetChangeObserver> netChangeObserverArrayList = new ArrayList<NetChangeObserver>();
    private NetType oldNetType;// 上一次的网络类型

    public static NetworkStateReceiver getReceiver() {
        if (receiver == null) {
            receiver = new NetworkStateReceiver();
        }
        return receiver;
    }

    /**
     * 销毁这个单例对象
     */
    public void dstroyInstance() {
        netChangeObserverArrayList.clear();
        receiver = null;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase(ANDROID_NET_CHANGE_ACTION)) {
            notifyObserver(context);
        }
    }

    private void notifyObserver(Context context) {
        for (int i = 0; i < netChangeObserverArrayList.size(); i++) {
            NetChangeObserver observer = netChangeObserverArrayList.get(i);
            if (observer != null) {
                boolean isNetworkAvailable = NetWorkUtil
                        .isNetworkAvailable(context);
                NetType currentNetType = NetWorkUtil.getNetType(context);
                if (isNetworkAvailable && oldNetType != currentNetType) {
                    observer.onConnect(currentNetType);
                } else if ((!isNetworkAvailable)
                        && oldNetType != currentNetType) {
                    observer.onDisConnect();
                }
                oldNetType = currentNetType;
            }
        }

    }

    /**
     * 注册网络连接观察者,可以注册多个观察者
     */
    public void registerObserver(NetChangeObserver observer) {
        if (netChangeObserverArrayList == null) {
            netChangeObserverArrayList = new ArrayList<NetChangeObserver>();
        }
        netChangeObserverArrayList.add(observer);
    }

    /**
     * 注销网络连接观察者
     *
     * @param observer 所要注销的观察者
     */
    public void removeRegisterObserver(NetChangeObserver observer) {
        if (netChangeObserverArrayList != null) {
            netChangeObserverArrayList.remove(observer);
        }
    }
}