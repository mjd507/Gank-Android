package com.cleaner.gank.daily.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cleaner.commonandroid.R;
import com.cleaner.gank.daily.model.DailyBeen;
import com.cleaner.gank.daily.presenter.DailyInfoPresenter;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.http.volley.HttpTask;
import common.ui.BaseFragment;
import common.utils.TimeUtils;
import common.utils.ToastUtils;


/**
 * 描述:
 * Created by mjd on 2017/2/13.
 */

public class DailyInfoFrag extends BaseFragment implements IDailyView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipeRefreshLayout)
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;

    private DailyInfoAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private DailyInfoPresenter presenter;

    private boolean isPrepared;
    private boolean hasLoadedTop;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tag, container, false);
        ButterKnife.bind(this, view);
        isPrepared = true;
        hasLoadedTop = false;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new DailyInfoPresenter(this);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new DailyInfoAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if (isPrepared && isVisible && !hasLoadedTop) {
            hasLoadedTop = true;
            loadMore();
        }
    }

    private void loadMore() {
        presenter.getDailyInfo(new Date());
    }

    @Override
    public void showSuccessView(List<DailyBeen> results) {
        if (results == null || results.size() == 0) {
            //获取前一天数据
            Date beforeDay = TimeUtils.getBeforeDay();
            presenter.getDailyInfo(beforeDay);
            return;
        }
        mAdapter.addList(results);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorView(HttpTask.ErrorType errorType) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (errorType == HttpTask.ErrorType.NetUnConnect) {
            ToastUtils.showShort(getActivity(), "网络不可用");
        } else if (errorType == HttpTask.ErrorType.NODATA) {
            ToastUtils.showShort(getActivity(), "运营休息中，暂无数据");
        } else if (errorType == HttpTask.ErrorType.OTHER) {
            ToastUtils.showShort(getActivity(), "加载失败");
        }
    }

    @Override
    public void hideLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onRefresh() {
        loadMore();
    }

    @Override
    public void onPause() {
        super.onPause();
        mSwipeRefreshLayout.setRefreshing(false);
        if (presenter != null) presenter.cancelAll();
    }

    @Override
    protected String getStartPageName() {
        return this.getClass().getSimpleName();
    }

}
