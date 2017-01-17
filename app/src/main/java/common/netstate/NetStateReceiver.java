package common.netstate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import common.netstate.NetworkUtils.NetworkType;

/**
 * 描述:
 * Created by mjd on 2017/1/17.
 */

public class NetStateReceiver extends BroadcastReceiver {

    public final static String ANDROID_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    private static NetStateReceiver receiver;

    private ArrayList<NetChangeObserver> observers = new ArrayList<>();

    private NetworkType oldNetType;// 上一次的网络类型

    public static NetStateReceiver getReceiver() {
        if (receiver == null) {
            receiver = new NetStateReceiver();
        }
        return receiver;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase(ANDROID_NET_CHANGE_ACTION)) {
            notifyObserver(context);
        }
    }

    private void notifyObserver(Context context) {
        for (int i = 0; i < observers.size(); i++) {
            NetChangeObserver observer = observers.get(i);
            if (observer != null) {
                boolean isConnected = NetworkUtils.isConnected(context);
                NetworkType currentType = NetworkUtils.getNetworkType(context);
                if (currentType == oldNetType) break; //do not need to notify all observers
                if (isConnected) {
                    observer.onConnect(currentType);
                } else {
                    observer.onDisConnect();
                }
                oldNetType = currentType;
            }
        }

    }

    /**
     * 注册网络连接观察者,可以注册多个观察者
     */
    public void registerObserver(NetChangeObserver observer) {
        if (observers == null) {
            observers = new ArrayList<NetChangeObserver>();
        }
        observers.add(observer);
    }

    /**
     * 注销网络连接观察者
     */
    public void removeRegisterObserver(NetChangeObserver observer) {
        if (observers != null) {
            observers.remove(observer);
        }
    }

    public void destroyInstance() {
        observers.clear();
        receiver = null;
    }
}
