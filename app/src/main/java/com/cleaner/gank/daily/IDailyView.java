package com.cleaner.gank.daily;

import com.cleaner.gank.tag.model.TagInfoBeen;

import java.util.List;

/**
 * 描述:
 * Created by mjd on 2017/2/7.
 */

public interface IDailyView {

    void showSuccessView(List<TagInfoBeen> results);

    void showErrorView();

    void netUnConnect();

    void hideLoading();

    void showLoading();
}
