package com.cleaner.gank.tag.presenter;

import com.android.volley.VolleyError;
import com.cleaner.gank.tag.model.TagInfoBeen;
import com.cleaner.gank.tag.model.TagInfoListener;
import com.cleaner.gank.tag.model.TagInfoProvider;
import com.cleaner.gank.tag.view.ITagInfoView;

import java.util.List;

/**
 * 描述:
 * Created by mjd on 2017/2/7.
 */

public class TagInfoPresenter {

    private ITagInfoView ITagInfoView;
    private int count = 10; //分页加载，每页十条数据

    public TagInfoPresenter(ITagInfoView ITagInfoView) {
        this.ITagInfoView = ITagInfoView;
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
        TagInfoProvider tagInfoProvider = new TagInfoProvider(tagInfoListener);
        tagInfoProvider.getTagInfo(category, count, page);
    }

    private TagInfoListener tagInfoListener = new TagInfoListener() {

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
        public void onError(VolleyError error) {
            ITagInfoView.showErrorView();
        }
    };


}
