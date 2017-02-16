package com.cleaner.gank.tag.model;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.cleaner.gank.constants.Urls;

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
    private HttpTask httpTask;

    public TagInfoProvider(@NonNull TagInfoListener tagInfoListener) {
        this.tagInfoListener = tagInfoListener;
    }

    private boolean isFromLocal;

    public void getTagInfoFormLocal(String url) {
        String result = SPUtils.getInstence().getString(url, "");
        if (!TextUtils.isEmpty(result)) {
            isFromLocal = true;
            HttpResponse response = new HttpResponse(JsonUtil.getJsonObj(result));
            handlerResponse(url, response);
        }
    }

    public void getTagInfoFromNet(final String category, final String page) {

        //每页返回十条数据
        final String url = Urls.GET_CATEGORY_INFO + category + "/" + 10 + "/" + page;

        httpTask = new HttpTask();
        httpTask.url = url;
        httpTask.isPost = false;
        httpTask.isShowLoadingDialog = true;
        httpTask.tag = category;
        httpTask.setListener(new HttpTask.Listener() {
            @Override
            public void showLoading() {
                tagInfoListener.showLoading();
            }

            @Override
            public void hideLoading() {
                tagInfoListener.hideLoading();
            }

            @Override
            public void onResponse(HttpResponse response) {
                handlerResponse(url, response);
            }

            @Override
            public void onErrorResponse(HttpTask.ErrorType errorType) {
                if (errorType == HttpTask.ErrorType.NetUnConnect) {
                    //从网络获取失败，从本地获取
                    getTagInfoFormLocal(url);
                }
                tagInfoListener.onError(errorType);

            }
        });
        httpTask.start();

    }

    private void handlerResponse(String url, HttpResponse response) {
        if (!isFromLocal) { //不是从本地获取，需要写入本地
            SPUtils.getInstence().putString(url, response.getResponse().toString());
        }
        boolean error = response.getState("error");
        List<TagInfoBeen> results = null;
        if (error) {
            LogUtils.d(TAG, "response error !");
            tagInfoListener.onError(HttpTask.ErrorType.NODATA);
            return;
        } else {
            results = response.getList("results", TagInfoBeen.class);
        }
        tagInfoListener.onSuccess(results);
    }

    public void cancelAll() {
        if (httpTask != null)
            httpTask.cancelAll();
    }

}
