package common.http.volley;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
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

    public boolean getState(String key) {
        if (jsonObject == null) return false;
        try {
            return jsonObject.getBoolean(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }


    public <T> List<T> getList(String key) {
        if (jsonObject == null) return null;
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            Gson gson = new Gson();
            return gson.fromJson(jsonArray.toString(), new TypeToken<List<T>>() {}.getType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public <T> Object getObj(String key, T type) {
        if (jsonObject == null) return null;
        try {
            JSONObject jsonObject = this.jsonObject.getJSONObject(key);
            Gson gson = new Gson();
            return gson.fromJson(jsonObject.toString(), type.getClass());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}
