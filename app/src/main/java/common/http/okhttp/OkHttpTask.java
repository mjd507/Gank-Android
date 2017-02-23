package common.http.okhttp;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import common.CommonApplication;
import common.http.common.ErrorType;
import common.http.common.HttpResponse;
import common.netstate.NetworkUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 描述:
 * Created by mjd on 2017/2/22.
 */

public class OkHttpTask {

    private Map<String, String> params = null;
    private Map<String, String> heads = null;
    private final Handler handler;

    private final OkHttpFactory httpFactory;

    private OkHttpTask() {
        httpFactory = OkHttpFactory.getInstance();
        handler = new Handler(Looper.myLooper());
        this.params = new HashMap<>();
        this.heads = new HashMap<>();
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

    public void get(String url) {
        Request request = buildRequest(url, RequestType.GET);
        doRequest(request);
    }

    public void post(String url) {
        Request request = buildRequest(url, RequestType.POST);
        doRequest(request);
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

    private String getParamWithString(String url) {
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

    private enum RequestType {
        GET,
        POST
    }

    private boolean isShowLoadingDialog = true;

    public void setIsShowLoadingDialog(boolean isShowLoadingDialog) {
        this.isShowLoadingDialog = isShowLoadingDialog;
    }

    private OkHttpListener listener;

    public void setOkHttpListener(OkHttpListener listener) {
        this.listener = listener;
    }

    private void doRequest(Request request) {
        if (listener == null) return;
        if (CommonApplication.getInstance().mNetType != NetworkUtils.NetworkType.NETWORK_NONE) {
            if (isShowLoadingDialog) listener.showLoading();

            httpFactory.getOkHttpClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    sendFailure(call, ErrorType.FAIL);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String json = response.body().string();
                        HttpResponse res = new HttpResponse(json);
                        sendSuccess(res, call);
                    } else {
                        sendFailure(call, ErrorType.NODATA);
                    }
                }
            });
        } else {
            listener.onErrorResponse(null, ErrorType.NetUnConnect);
        }

    }

    private void sendFailure(final Call call, final ErrorType errorType) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isShowLoadingDialog) listener.hideLoading();
                listener.onErrorResponse(call, errorType);
            }
        });
    }

    private void sendSuccess(final HttpResponse response, final Call call) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isShowLoadingDialog) listener.hideLoading();
                listener.onResponse(response);
            }
        });
    }



}
