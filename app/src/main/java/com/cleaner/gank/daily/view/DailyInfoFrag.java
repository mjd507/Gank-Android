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
import common.ui.BaseFragment;

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
    private boolean isLoading; //控制加载的变量

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tag, container, false);
        ButterKnife.bind(this, view);
        isPrepared = true;
        hasLoadedTop = false;
        isLoading = false;
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
        mRecyclerView.addOnScrollListener(onScrollListener);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        loadMore();
    }

    @Override
    protected void lazyLoad() {

        if (isPrepared && isVisible && !hasLoadedTop) {
            loadMore();
            hasLoadedTop = true;
        }

    }

    private void loadMore() {

        presenter.getDailyInfo(new Date());

    }


    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisibleItem;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                if (!isLoading) {
                    loadMore();
                    isLoading = true;
                }

            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        }
    };


    @Override
    public void showSuccessView(List<DailyBeen> results) {
        isLoading = false;
        mAdapter.addList(results);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void netUnConnect() {

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
}
