package com.cleaner.gank.category.model;

import com.android.volley.VolleyError;

import java.util.List;

/**
 * 描述:
 * Created by mjd on 2017/2/7.
 */

public interface CategoryInfoListener {

    void showLoading();

    void hideLoading();

    void netUnConnect();

    void onSuccess(List<CategoryBeen> results);

    void onError(VolleyError error);
}
