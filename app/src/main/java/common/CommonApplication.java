package common;

import android.app.Application;

import common.netstate.NetChangeObserver;
import common.netstate.NetStateReceiver;
import common.netstate.NetworkUtils;

/**
 * 描述:
 * Created by mjd on 2017/1/16.
 */

public class CommonApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        handleNetChange();

    }

    private void handleNetChange() {

        NetStateReceiver receiver = NetStateReceiver.getReceiver();

        NetChangeObserver netChangeObserver = new NetChangeObserver() {

            @Override
            public void onConnect(NetworkUtils.NetworkType type) {
                CommonApplication.this.onConnect(type);
            }

            @Override
            public void onDisConnect() {
                CommonApplication.this.onDisConnect();
            }
        };

        // 注册网络连接变化的观察者
        receiver.registerObserver(netChangeObserver);

        //注册网络变化广播
        NetworkUtils.registerNetStateReceiver(receiver, this);

    }

    public void onDisConnect() {

    }

    protected void onConnect(NetworkUtils.NetworkType type) {

    }



}
