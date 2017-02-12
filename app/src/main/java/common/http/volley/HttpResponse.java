package common.http.volley;

import org.json.JSONObject;

import java.util.List;

import common.logger.Logger;

/**
 * 描述:
 * Created by mjd on 2017/2/7.
 */

public class HttpResponse {

    private JSONObject jsonObject;

    public HttpResponse(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        if (jsonObject != null)
            Logger.json(jsonObject.toString());
    }

    public JSONObject getResponse() {
        return jsonObject;
    }

    public boolean getState(String key) {
        return JsonUtil.getBoolean(jsonObject, key);
    }


    public <T> List<T> getList(String key, Class<T> type) {
        return JsonUtil.getList(jsonObject, key, type);
    }


    public <T> T getObj(String key, Class<T> type) {
        return JsonUtil.getObj(jsonObject, key, type);
    }


}
