package com.cleaner.gank.daily.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cleaner.commonandroid.R;
import com.cleaner.gank.daily.model.DailyBeen;
import com.cleaner.gank.daily.presenter.DailyInfoPresenter;

import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import common.ui.BaseFragment;

/**
 * 描述:
 * Created by mjd on 2017/2/13.
 */

public class DailyInfoFrag extends BaseFragment implements IDailyView {
    private DailyInfoPresenter presenter;

    private boolean isPrepared;
    private boolean isLoadedTop;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tag, container, false);
        ButterKnife.bind(this, view);
        isPrepared = true;
        isLoadedTop = false;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new DailyInfoPresenter(this);
        loadMore();
    }

    @Override
    protected void lazyLoad() {

        if (isPrepared && isVisible && !isLoadedTop) {
            loadMore();
            isLoadedTop = true;
        }

    }

    private void loadMore() {

        presenter.getDailyInfo(new Date());

    }

    @Override
    public void showSuccessView(List<DailyBeen> results) {

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
