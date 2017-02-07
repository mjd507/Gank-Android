package com.cleaner.gank.daily.model;

import com.android.volley.VolleyError;

import java.util.List;

/**
 * 描述:
 * Created by mjd on 2017/2/7.
 */

public interface DailyInfoListener {
    void showLoading();

    void hideLoading();

    void netUnConnect();

    void onSuccess(List<DailyBeen> results);

    void onError(VolleyError error);
}
