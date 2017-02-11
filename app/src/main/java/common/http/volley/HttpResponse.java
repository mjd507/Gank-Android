package common.http.volley;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
        if (jsonObject == null) return false;
        try {
            return jsonObject.getBoolean(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }


    public <T> List<T> getList(String key, Class<T> type) {
        if (jsonObject == null) return null;
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            Gson gson = new Gson();

            List<JsonObject> jsonObjectList = gson.fromJson(jsonArray.toString(), new TypeToken<List<JsonObject>>() {}.getType());

            List<T> list = new ArrayList<>();
            for (JsonObject jsonObject : jsonObjectList) {
                list.add(gson.fromJson(jsonObject, type));
            }
            return list;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public <T> T getObj(String key, Class<T> type) {
        if (jsonObject == null) return null;
        try {
            JSONObject jsonObject = this.jsonObject.getJSONObject(key);
            Gson gson = new Gson();
            return (T) gson.fromJson(jsonObject.toString(), type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}
