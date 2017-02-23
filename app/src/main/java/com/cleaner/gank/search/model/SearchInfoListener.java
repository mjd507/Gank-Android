package com.cleaner.gank.search.model;

import java.util.List;

import common.http.common.ErrorType;

/**
 * 描述:
 * Created by mjd on 2017/2/8.
 */

public interface SearchInfoListener {

    void showLoading();

    void hideLoading();

    void onSuccess(List<SearchBeen> results);

    void onError(ErrorType error);

}
