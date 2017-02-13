package com.cleaner.gank.tag.model;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.android.volley.VolleyError;
import com.cleaner.gank.Urls;

import java.util.List;

import common.http.volley.HttpResponse;
import common.http.volley.HttpTask;
import common.http.volley.JsonUtil;
import common.utils.LogUtils;
import common.utils.SPUtils;

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

    private boolean isFromLocal;

    public void getTagInfoFormLocal(String url, String category, String page) {
        String result = SPUtils.getInstence().getString(url, "");
        if (!TextUtils.isEmpty(result)) {
            isFromLocal = true;
            HttpResponse response = new HttpResponse(JsonUtil.getJsonObj(result));
            handlerResponse(url, response);
        } else {
            tagInfoListener.onError();
        }
    }

    public void getTagInfoFromNet(final String category, final String page) {

        //每页返回十条数据
        final String url = Urls.GET_CATEGORY_INFO + category + "/" + 10 + "/" + page;

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
                getTagInfoFormLocal(url, category, page);
                tagInfoListener.netUnConnect();
            }

            @Override
            public void onResponse(HttpResponse response) {
                handlerResponse(url, response);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                //从网络获取失败，从本地获取
                getTagInfoFormLocal(url, category, page);

            }
        });
        task.start();

    }

    private void handlerResponse(String url, HttpResponse response) {
        if (!isFromLocal) { //不是从本地获取，需要写入本地
            SPUtils.getInstence().putString(url, response.getResponse().toString());
        }
        boolean error = response.getState("error");
        List<TagInfoBeen> results = null;
        if (error) {
            LogUtils.d(TAG, "response error !");
        } else {
            results = response.getList("results", TagInfoBeen.class);
        }
        tagInfoListener.onSuccess(results);
    }


}
