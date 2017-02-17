package common.http.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import common.cache.LruBitmapCache;

/**
 * 描述:
 * Created by mjd on 2017/2/3.
 */

public class VolleyFactory {

    private static VolleyFactory mVolleyFactory;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private Context mCtx;

    private VolleyFactory() {
    }

    public static VolleyFactory getInstance() {
        if (mVolleyFactory == null) {
            mVolleyFactory = new VolleyFactory();
        }
        return mVolleyFactory;
    }

    public void init(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
        mImageLoader = new ImageLoader(mRequestQueue, new LruBitmapCache(LruBitmapCache.getCacheSize(mCtx.getApplicationContext())));
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }


}
