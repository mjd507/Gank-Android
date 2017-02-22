package common.http.common;

/**
 * 描述:
 * Created by mjd on 2017/2/22.
 */


public interface Listener {

    void showLoading();

    void hideLoading();

    void onResponse(HttpResponse response);

    void onErrorResponse(ErrorType errorType);

    public enum ErrorType {
        NetUnConnect, NODATA, OTHER
    }

}
