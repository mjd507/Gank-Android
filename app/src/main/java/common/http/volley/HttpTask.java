package common.http.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import common.CommonApplication;
import common.netstate.NetworkUtils;
import common.ui.dialog.LoadingDialog;
import common.utils.ToastUtils;

/**
 * 描述: 
 * Created by mjd on 2017/2/7.
 */

public class HttpTask {

    private Context context;
    private VolleyFactory volleyFactory;
    private NetworkUtils.NetworkType mNetType;

    public String url;
    public boolean isShowLoadingDialog = true;
    public boolean isPost = false;

    private LoadingDialog loadingDialog;

    public HttpTask(Context context) {
        this.context = context;
        volleyFactory = VolleyFactory.getInstance(context);
        CommonApplication appContext = (CommonApplication) context.getApplicationContext();
        mNetType = appContext.mNetType;
    }

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public <T> void start() {
        if (mNetType != NetworkUtils.NetworkType.NETWORK_NONE) {
            if (isShowLoadingDialog) {
                showLoading();
            }
            int method = isPost ? Request.Method.POST : Request.Method.GET;

            JsonObjectRequest request = new JsonObjectRequest(method, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    closeLoading();
                    if (listener != null) {
                        listener.onResponse(response);
                    }
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    closeLoading();
                    if (listener != null) {
                        listener.onErrorResponse(error);
                    }
                }
            });

            volleyFactory.addToRequestQueue(request);

        } else {
            ToastUtils.showShort(context, "当前网络不可用");
        }
    }


    private void showLoading() {
        loadingDialog = new LoadingDialog(context);
        loadingDialog.show();
    }

    private void closeLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    public interface Listener {

        void onResponse(JSONObject response);

        void onErrorResponse(VolleyError error);
    }

}
