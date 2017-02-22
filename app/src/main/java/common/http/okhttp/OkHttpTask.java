package common.http.okhttp;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import common.CommonApplication;
import common.http.common.HttpResponse;
import common.netstate.NetworkUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 描述:
 * Created by mjd on 2017/2/22.
 */

public class OkHttpTask {

    private final OkHttpClient okHttpClient;
    private Map<String, String> params = null;
    private Map<String, String> heads = null;

    private Gson gson;
    private final Handler handler;

    private static OkHttpTask httpManager;

    private OkHttpTask() {
        okHttpClient = new OkHttpClient();
        okHttpClient.newBuilder().connectTimeout(10, TimeUnit.SECONDS);
        okHttpClient.newBuilder().writeTimeout(10, TimeUnit.SECONDS);
        okHttpClient.newBuilder().readTimeout(10, TimeUnit.SECONDS);
        gson = new Gson();
        handler = new Handler(Looper.myLooper());
        this.params = new HashMap<>();
        this.heads = new HashMap<>();
    }

    public static OkHttpTask getInstance() {
        if (httpManager == null) {
            synchronized (OkHttpTask.class) {
                httpManager = new OkHttpTask();
            }
        }
        return httpManager;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public OkHttpTask addParam(String key, String value) {
        this.params.put(key, value);
        return this;
    }

    public Map<String, String> getHeads() {
        return heads;
    }


    public OkHttpTask addHeads(String key, String value) {
        this.heads.put(key, value);
        return this;
    }

    public void get(String url, WSCallBack bcb) {
        Request request = buildRequest(url, RequestType.GET);
        doRequest(request, bcb);
    }

    public void post(String url, WSCallBack bcb) {
        Request request = buildRequest(url, RequestType.POST);
        doRequest(request, bcb);
    }

    private Request buildRequest(String url, RequestType type) {
        Request.Builder builder = new Request.Builder();
        if (type == RequestType.GET) {
            url = getParamWithString(url);
            builder.get();
        } else if (type == RequestType.POST) {
            RequestBody requestBody = getFormatData(params);
            builder.post(requestBody);
        }
        builder.url(url);
        addAllHeads(builder);
        return builder.build();
    }

    private void addAllHeads(Request.Builder builder) {
        if (heads.size() > 0) {
            for (Map.Entry<String, String> entry : heads.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    public String getParamWithString(String url) {
        if (params == null || params.size() < 1)
            return url;
        StringBuilder sb = new StringBuilder();
        if (url.indexOf("http://") == 0 || url.indexOf("https://") == 0) {
            sb.append(url + "?");
        }

        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey()).append("=")
                    .append(entry.getValue()).append("&");
        }

        return sb.toString().substring(0, (sb.toString().length() - 1));
    }

    private RequestBody getFormatData(Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null && params.size() > 0)
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        return builder.build();
    }

    enum RequestType {
        GET,
        POST
    }

    public boolean isShowLoadingDialog = true;

    private void doRequest(Request request, final OkHttpListener listener) {
        if (listener == null) return;
        if (CommonApplication.getInstance().mNetType != NetworkUtils.NetworkType.NETWORK_NONE) {
            if (isShowLoadingDialog) listener.showLoading();

            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    sendFaile(listener, call, OkHttpListener.ErrorType.OTHER);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String json = response.body().string();
                        sendSuccess(json, call, listener);
                    } else {
                        sendFaile(listener, call, null);
                    }
                }
            });
        } else {
            listener.onErrorResponse(null, OkHttpListener.ErrorType.NetUnConnect);
        }

    }

    private void sendFaile(final OkHttpListener listener, final Call call, final OkHttpListener.ErrorType errorType) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isShowLoadingDialog) listener.hideLoading();
                listener.onErrorResponse(call, errorType);
            }
        });
    }

    private void sendSuccess(final String json, final Call call, final OkHttpListener listener) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isShowLoadingDialog) listener.hideLoading();
                HttpResponse response = new HttpResponse(json);
                listener.onResponse(response);
            }
        });
    }

    private OkHttpListener listener;

    public void setOkHttpListener(OkHttpListener volleyListener) {
        this.listener = volleyListener;
    }


}
