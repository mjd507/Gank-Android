package common.http.common;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import common.logger.Logger;

/**
 * 描述:
 * Created by mjd on 2017/2/7.
 */

public class HttpResponse {

    private Object object;
    private JSONObject jsonObject;

    public HttpResponse(Object obj) {
        this.object = obj;
        if (object != null)
            Logger.json(object.toString());
        jsonObject = parse(object);
    }

    public JSONObject parse(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return new JSONObject(obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
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
