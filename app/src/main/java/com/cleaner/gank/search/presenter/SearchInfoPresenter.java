package com.cleaner.gank.search.presenter;

import com.cleaner.gank.search.model.SearchBeen;
import com.cleaner.gank.search.model.SearchInfoListener;
import com.cleaner.gank.search.model.SearchInfoProvider;
import com.cleaner.gank.search.view.ISearchView;

import java.util.List;

import common.http.volley.HttpTask;

/**
 * 描述:
 * Created by mjd on 2017/2/8.
 */

public class SearchInfoPresenter implements SearchInfoListener {

    private ISearchView searchView;

    public SearchInfoPresenter(ISearchView searchView) {
        this.searchView = searchView;
    }

    public void getSearchInfo(String category, String page) {
        SearchInfoProvider infoProvider = new SearchInfoProvider(this);
        infoProvider.getSearchInfo(category, page);
    }

    @Override
    public void showLoading() {
        searchView.showLoading();
    }

    @Override
    public void hideLoading() {
        searchView.hideLoading();
    }

    @Override
    public void onSuccess(List<SearchBeen> results) {
        searchView.showSuccessView(results);
    }

    @Override
    public void onError(HttpTask.ErrorType error) {
        searchView.showErrorView();
    }
}
