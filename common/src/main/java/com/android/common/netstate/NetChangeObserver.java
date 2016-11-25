package com.android.common.netstate;

import com.android.common.netstate.NetWorkUtil.NetType;

/**
 * 描述: 检测网络改变的观察者
 */
public interface NetChangeObserver {

    /**
     * 网络连接连接时调用
     */
    void onConnect(NetType type);

    /**
     * 断开网络连接时调用
     */
    void onDisConnect();
}
