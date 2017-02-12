package com.cleaner.gank.tag.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cleaner.commonandroid.R;
import com.cleaner.gank.tag.model.TagInfoBeen;
import com.cleaner.gank.tag.presenter.TagInfoPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.ui.BaseActivity;
import common.ui.BaseFragment;

/**
 * 描述:
 * Created by mjd on 2017/2/11.
 */

public class BaseTagFragment extends BaseFragment implements ITagInfoView {
    protected TagInfoPresenter presenter;
    protected int page = 1;
    @BindView(R.id.swipeRefreshLayout)
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;


    private BaseActivity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (BaseActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tag, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new TagInfoPresenter(this);
    }

    @Override
    public void showSuccessView(List<TagInfoBeen> results) {
    }

    @Override
    public void showErrorView() {
        mActivity.showBaseErrorView();
    }

    @Override
    public void netUnConnect() {
        mActivity.showNetError();
    }

    @Override
    public void hideLoading() {
        if (page == 1)
            mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoading() {
        if (page == 1)
            mSwipeRefreshLayout.setRefreshing(true);
    }


}
