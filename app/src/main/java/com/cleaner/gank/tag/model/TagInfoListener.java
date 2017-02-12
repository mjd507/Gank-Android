package com.cleaner.gank.tag.model;

import java.util.List;

/**
 * 描述:
 * Created by mjd on 2017/2/7.
 */

public interface TagInfoListener {

    void showLoading();

    void hideLoading();

    void netUnConnect();

    void onSuccess(List<TagInfoBeen> results);

    void onError();
}
