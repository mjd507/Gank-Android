package com.cleaner.gank.search.model;

import com.android.volley.VolleyError;

import java.util.List;

/**
 * 描述:
 * Created by mjd on 2017/2/8.
 */

public interface SearchInfoListener {

    void showLoading();

    void hideLoading();

    void netUnConnect();

    void onSuccess(List<SearchBeen> results);

    void onError(VolleyError error);

}
