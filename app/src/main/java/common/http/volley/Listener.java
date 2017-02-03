package common.http.volley;

import com.android.volley.VolleyError;

/**
 * 描述:
 * Created by mjd on 2017/2/4.
 */

public interface Listener<T> {

    void onResponse(T response);

    void onErrorResponse(VolleyError error);
}
