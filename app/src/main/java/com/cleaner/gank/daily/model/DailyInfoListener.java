package com.cleaner.gank.daily.model;

import java.util.List;

import common.http.common.Listener;

/**
 * 描述:
 * Created by mjd on 2017/2/7.
 */

public interface DailyInfoListener {
    void showLoading();

    void hideLoading();

    void onSuccess(List<DailyBeen> results);

    void onError(Listener.ErrorType errorType);
}
