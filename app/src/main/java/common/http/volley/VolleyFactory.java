package common.http.volley;

import android.content.Context;

import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;

/**
 * 描述: 网络请求队列的生产工厂
 * Created by mjd on 2017/2/3.
 */

public class VolleyFactory {
    /**
     * 创建一个默认的网络请求队列实例
     *
     * @param context   应用全局上下文对象
     * @param diskCache 本地缓存
     * @return A started {@link RequestQueue} instance.
     */
    public static RequestQueue newRequestQueue(Context context, DiskBasedCache diskCache) {

        HttpStack stack = new HurlStack();

        Network network = new BasicNetwork(stack);

        RequestQueue queue = new RequestQueue(diskCache, network);
        queue.start();
        return queue;
    }
}
