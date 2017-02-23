package common.http.volley;

import common.http.common.ErrorType;
import common.http.common.HttpResponse;

/**
 * 描述:
 * Created by mjd on 2017/2/22.
 */


public interface VolleyListener {

    void showLoading();

    void hideLoading();

    void onResponse(HttpResponse response);

    void onErrorResponse(ErrorType errorType);

}
