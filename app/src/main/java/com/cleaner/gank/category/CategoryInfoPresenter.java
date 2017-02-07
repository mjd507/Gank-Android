package com.cleaner.gank.category;

import com.android.volley.VolleyError;
import com.cleaner.gank.category.model.CategoryBeen;
import com.cleaner.gank.category.model.CategoryInfoListener;
import com.cleaner.gank.category.model.CategoryInfoProvider;

import java.util.List;

/**
 * 描述:
 * Created by mjd on 2017/2/7.
 */

public class CategoryInfoPresenter {

    private ICategoryInfoView ICategoryInfoView;
    private int count = 10; //分页加载，每页十条数据

    public CategoryInfoPresenter(ICategoryInfoView ICategoryInfoView) {
        this.ICategoryInfoView = ICategoryInfoView;
    }

    public void getAndroidInfo(String page) {
        getInfo("Android", String.valueOf(count), page);
    }

    public void getiOSInfo(String page) {
        getInfo("iOS", String.valueOf(count), page);
    }

    public void getWelfareInfo(String page) {
        getInfo("福利", String.valueOf(count), page);
    }

    public void getWebInfo(String page) {
        getInfo("前端", String.valueOf(count), page);
    }

    public void getOtherInfo(String page) {
        getInfo("拓展资源", String.valueOf(count), page);
    }

    public void getRestInfo(String page) {
        getInfo("休息视频", String.valueOf(count), page);
    }

    private void getInfo(String category, String count, String page) {
        CategoryInfoProvider categoryInfoProvider = new CategoryInfoProvider(categoryInfoListener);
        categoryInfoProvider.getCategoryInfo(category, count, page);
    }

    private CategoryInfoListener categoryInfoListener = new CategoryInfoListener() {

        @Override
        public void showLoading() {
            ICategoryInfoView.showLoading();
        }

        @Override
        public void hideLoading() {
            ICategoryInfoView.hideLoading();
        }

        @Override
        public void netUnConnect() {
            ICategoryInfoView.netUnConnect();
        }

        @Override
        public void onSuccess(List<CategoryBeen> results) {
            ICategoryInfoView.showSuccessView(results);
        }

        @Override
        public void onError(VolleyError error) {
            ICategoryInfoView.showErrorView();
        }
    };


}
