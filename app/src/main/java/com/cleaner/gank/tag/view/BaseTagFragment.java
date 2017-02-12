package com.cleaner.gank.tag.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cleaner.commonandroid.R;
import com.cleaner.gank.tag.model.TagInfoBeen;
import com.cleaner.gank.tag.presenter.TagInfoPresenter;
import com.cleaner.gank.tag.view.adapter.ItemAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.ui.BaseActivity;
import common.ui.BaseFragment;

/**
 * 描述:
 * Created by mjd on 2017/2/11.
 */

public abstract class BaseTagFragment extends BaseFragment implements ITagInfoView, SwipeRefreshLayout.OnRefreshListener {
    protected TagInfoPresenter presenter;

    @BindView(R.id.swipeRefreshLayout)
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;


    private BaseActivity mActivity;

    private ItemAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    protected int page = 1;

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
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new ItemAdapter(presenter);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(onScrollListener);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void showSuccessView(List<TagInfoBeen> results) {
        isLoading = false;

        if (page == 1) { //first load or onRefresh
            mAdapter.getList().clear();
        }

        mAdapter.addList(results);
        mAdapter.notifyDataSetChanged();
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


    @Override
    public void onRefresh() {
        page = 1;
        loadMore(page);
    }


    boolean isLoading = false;
    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisibleItem;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                if (!isLoading) {
                    loadMore(++page);
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

    public abstract void loadMore(int page);


}
