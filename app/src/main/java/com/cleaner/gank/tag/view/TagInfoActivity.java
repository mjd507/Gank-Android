package com.cleaner.gank.tag.view;

import android.os.Bundle;

import com.cleaner.gank.tag.model.TagInfoBeen;
import com.cleaner.gank.tag.presenter.TagInfoPresenter;

import java.util.List;

import common.ui.BaseActivity;

/**
 * 描述:
 * Created by mjd on 2017/2/7.
 */

public class TagInfoActivity extends BaseActivity implements ITagInfoView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TagInfoPresenter presenter = new TagInfoPresenter(this);
        presenter.getAndroidInfo("1");

    }

    @Override
    public void showSuccessView(List<TagInfoBeen> results) {

    }

    @Override
    public void showErrorView() {
        showBaseErrorView();
    }

    @Override
    public void netUnConnect() {
        NetBaseUnConnect();
    }

    @Override
    public void hideLoading() {
        closeBaseLoading();
    }

    @Override
    public void showLoading() {
        showBaseLoading();
    }
}
