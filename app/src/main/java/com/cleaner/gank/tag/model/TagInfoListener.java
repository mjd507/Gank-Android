package com.cleaner.gank.tag.model;

import java.util.List;

import common.http.volley.VolleyListener;

/**
 * 描述:
 * Created by mjd on 2017/2/7.
 */

public interface TagInfoListener {

    void showLoading();

    void hideLoading();

    void onSuccess(List<TagInfoBeen> results);

    void onError(VolleyListener.ErrorType errorType);
}
