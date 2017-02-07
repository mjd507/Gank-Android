package com.cleaner.gank.category;

import com.cleaner.gank.category.model.CategoryBeen;

import java.util.List;

/**
 * 描述:
 * Created by mjd on 2017/2/7.
 */

public interface ICategoryInfoView {

    void showSuccessView(List<CategoryBeen> results);

    void showErrorView();

    void netUnConnect();

    void hideLoading();

    void showLoading();
}
