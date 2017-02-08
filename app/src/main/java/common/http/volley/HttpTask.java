package common.http.volley;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import common.CommonApplication;
import common.netstate.NetworkUtils;

/**
 * 描述: 每个网络请求的统一任务栈
 * Created by mjd on 2017/2/7.
 */

public class HttpTask {

    private VolleyFactory volleyFactory;

    //每个请求相关元素的封装
    private NetworkUtils.NetworkType mNetType;
    public String url;
    public boolean isShowLoadingDialog = true;
    public boolean isPost = false;
    public Object tag;

    public HttpTask() {
        volleyFactory = VolleyFactory.getInstance();
        mNetType = CommonApplication.getInstance().mNetType;
    }

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void cancelAll() {
        volleyFactory.getRequestQueue().cancelAll(tag);
    }

    public void start() {
        if (listener == null) return;
        if (mNetType != NetworkUtils.NetworkType.NETWORK_NONE) {

            if (isShowLoadingDialog) listener.showLoading();

            int method = isPost ? Request.Method.POST : Request.Method.GET;

            JsonObjectRequest request = new JsonObjectRequest(method, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (isShowLoadingDialog) listener.hideLoading();
                    HttpResponse res = new HttpResponse(response);
                    listener.onResponse(res);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (isShowLoadingDialog) listener.hideLoading();
                    listener.onErrorResponse(error);
                }
            });
            if (tag != null) request.setTag(tag);
            volleyFactory.addToRequestQueue(request);

        } else {
            listener.netUnConnect();
        }
    }


    public interface Listener {

        void showLoading();

        void hideLoading();

        void netUnConnect();

        void onResponse(HttpResponse response);

        void onErrorResponse(VolleyError error);

    }

}
