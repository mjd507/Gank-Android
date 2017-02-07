package com.cleaner.gank.category;

import android.app.Activity;
import android.os.Bundle;

import com.cleaner.gank.category.model.CategoryBeen;

import java.util.List;

/**
 * 描述:
 * Created by mjd on 2017/2/7.
 */

public class CategoryViewActivity extends Activity implements ICategoryInfoView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CategoryInfoPresenter presenter = new CategoryInfoPresenter(this);

    }

    @Override
    public void showSuccessView(List<CategoryBeen> results) {

    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void netUnConnect() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoading() {

    }
}
