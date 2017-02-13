package com.cleaner.gank.tag.presenter;

import com.cleaner.gank.tag.model.TagInfoBeen;
import com.cleaner.gank.tag.model.TagInfoListener;
import com.cleaner.gank.tag.model.TagInfoProvider;
import com.cleaner.gank.tag.view.ITagInfoView;

import java.util.List;

/**
 * 描述:
 * Created by mjd on 2017/2/7.
 */

public class TagInfoPresenter implements TagInfoListener {

    private ITagInfoView ITagInfoView;
    private TagInfoProvider tagInfoProvider;

    public TagInfoPresenter(ITagInfoView ITagInfoView) {
        this.ITagInfoView = ITagInfoView;
        tagInfoProvider = new TagInfoProvider(this);
    }

    public void getInfo(String category, String page) {
        tagInfoProvider.getTagInfoFromNet(category, page);
    }

    @Override
    public void showLoading() {
        ITagInfoView.showLoading();
    }

    @Override
    public void hideLoading() {
        ITagInfoView.hideLoading();
    }

    @Override
    public void netUnConnect() {
        ITagInfoView.netUnConnect();
    }

    @Override
    public void onSuccess(List<TagInfoBeen> results) {
        ITagInfoView.showSuccessView(results);
    }

    @Override
    public void onError() {
        ITagInfoView.showErrorView();
    }


}
