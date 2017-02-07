package com.cleaner.gank.model;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import common.http.volley.HttpTask;

/**
 * 描述:
 * Created by mjd on 2017/2/7.
 */

public class InfoProvider {

    private Context context;

    public InfoProvider(Context context) {
        this.context = context;
    }

    public void getCategoryInfo(String category, String count, String page) {

        String url = Urls.GET_CATEGORY_INFO + category + "/" + count + "/" + page;

        HttpTask task = new HttpTask(context);
        task.url = url;
        task.isPost = false;
        task.isShowLoadingDialog = true;
        task.setListener(new HttpTask.Listener() {
            @Override
            public void onResponse(JSONObject response) {
                handlerResponse(response);
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }

    private void handlerResponse(JSONObject response) {

    }


}
