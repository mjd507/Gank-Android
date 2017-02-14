package com.cleaner.gank.search.view;

import com.cleaner.gank.search.model.SearchBeen;

import java.util.List;

/**
 * 描述:
 * Created by mjd on 2017/2/8.
 */

public interface ISearchView {

    void showSuccessView(List<SearchBeen> results);

    void showErrorView();

    void hideLoading();

    void showLoading();
}
