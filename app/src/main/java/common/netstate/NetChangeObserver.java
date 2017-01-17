package common.netstate;

import common.netstate.NetworkUtils.NetworkType;

/**
 * 描述:
 * Created by mjd on 2017/1/16.
 */

public interface NetChangeObserver {

    void onConnect(NetworkType type);

    void onDisConnect();

}
