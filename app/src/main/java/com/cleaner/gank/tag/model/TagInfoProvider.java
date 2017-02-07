package com.cleaner.gank.tag.model;

import android.support.annotation.NonNull;

import com.android.volley.VolleyError;
import com.cleaner.gank.Urls;

import org.json.JSONObject;

import java.util.List;

import common.http.volley.HttpResponse;
import common.http.volley.HttpTask;
import common.utils.LogUtils;

import static com.android.volley.VolleyLog.TAG;

/**
 * 描述:
 * Created by mjd on 2017/2/7.
 */

public class TagInfoProvider {

    private TagInfoListener tagInfoListener;

    public TagInfoProvider(@NonNull TagInfoListener tagInfoListener) {
        this.tagInfoListener = tagInfoListener;
    }

    public void getTagInfo(String category, String count, String page) {

        String url = Urls.GET_CATEGORY_INFO + category + "/" + count + "/" + page;

        HttpTask task = new HttpTask();
        task.url = url;
        task.isPost = false;
        task.isShowLoadingDialog = true;
        task.tag = category;
        task.setListener(new HttpTask.Listener() {
            @Override
            public void showLoading() {
                tagInfoListener.showLoading();
            }

            @Override
            public void hideLoading() {
                tagInfoListener.hideLoading();
            }

            @Override
            public void netUnConnect() {
                tagInfoListener.netUnConnect();
            }

            @Override
            public void onResponse(JSONObject response) {
                handlerResponse(response);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                tagInfoListener.onError(error);
            }
        });
        task.start();

    }

    private void handlerResponse(JSONObject response) {
        HttpResponse res = new HttpResponse(response);
        boolean error = res.getState("error");
        List<TagInfoBeen> results = null;
        if (error) {
            LogUtils.d(TAG, "response error !");
        } else {
            results = res.getList("results");
        }
        tagInfoListener.onSuccess(results);
    }


}
