package com.cleaner.gank.search.model;

import android.support.annotation.NonNull;

import com.cleaner.gank.constants.Urls;

import java.util.List;

import common.http.common.HttpResponse;
import common.http.common.Listener;
import common.http.volley.VolleyHttpTask;
import common.utils.LogUtils;

import static com.android.volley.VolleyLog.TAG;

/**
 * 描述:
 * Created by mjd on 2017/2/8.
 */

public class SearchInfoProvider {

    private SearchInfoListener searchInfoListener;

    public SearchInfoProvider(@NonNull SearchInfoListener searchInfoListener) {
        this.searchInfoListener = searchInfoListener;
    }

    public void getSearchInfo(String category, String page) {
        VolleyHttpTask task = new VolleyHttpTask();
        task.url = Urls.GET_SEARCH + category + "/count/10/" + page;    //  /Android/count/10/page/1
        task.isShowLoadingDialog = true;
        task.isPost = false;
        task.tag = null;
        task.setListener(new Listener() {
            @Override
            public void showLoading() {
                searchInfoListener.showLoading();
            }

            @Override
            public void hideLoading() {
                searchInfoListener.hideLoading();
            }

            @Override
            public void onResponse(HttpResponse response) {
                handleResponse(response);
            }

            @Override
            public void onErrorResponse(ErrorType error) {
                searchInfoListener.onError(error);
            }
        });
        task.start();
    }

    private void handleResponse(HttpResponse response) {
        boolean error = response.getState("error");
        if (error) {
            LogUtils.d(TAG, "response error !");
        } else {
            List<SearchBeen> results = response.getList("results",SearchBeen.class);
            searchInfoListener.onSuccess(results);
        }
    }
}
