package common.http.okhttp;

import common.http.common.HttpResponse;
import okhttp3.Call;

/**
 * 描述:
 * Created by mjd on 2017/2/22.
 */


public interface OkHttpListener {

    void showLoading();

    void hideLoading();

    void onResponse(HttpResponse response);

    void onErrorResponse(Call call, ErrorType errorType);

    public enum ErrorType {
        NetUnConnect, NODATA, OTHER
    }

}
