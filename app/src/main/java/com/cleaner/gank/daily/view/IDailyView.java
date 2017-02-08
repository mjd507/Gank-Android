package com.cleaner.gank.daily.view;

import com.cleaner.gank.daily.model.DailyBeen;

import java.util.List;

/**
 * 描述:
 * Created by mjd on 2017/2/7.
 */

public interface IDailyView {

    void showSuccessView(List<DailyBeen> results);

    void showErrorView();

    void netUnConnect();

    void hideLoading();

    void showLoading();
}
