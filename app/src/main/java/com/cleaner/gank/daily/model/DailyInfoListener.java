package com.cleaner.gank.daily.model;

import java.util.List;

import common.http.volley.VolleyListener;

/**
 * 描述:
 * Created by mjd on 2017/2/7.
 */

public interface DailyInfoListener {
    void showLoading();

    void hideLoading();

    void onSuccess(List<DailyBeen> results);

    void onError(VolleyListener.ErrorType errorType);
}
