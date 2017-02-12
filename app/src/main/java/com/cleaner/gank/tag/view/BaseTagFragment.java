package com.cleaner.gank.tag.view;

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
import common.ui.BaseFragment;
import common.utils.ToastUtils;

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

    private ItemAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    protected int page;
    private boolean isPrepared; //标记布局加载完成，避免 NullPointer
    private boolean isLoadedTop; //控制每次 tab 切换时都加载数据的问题
    private boolean isLoadingBottom; //控制底部加载的变量

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tag, container, false);
        ButterKnife.bind(this, view);
        isPrepared = true;
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
        page = 1;
        isLoadedTop = false;
        isLoadingBottom = false;
        lazyLoad();
    }

    @Override
    public void showSuccessView(List<TagInfoBeen> results) {
        isLoadingBottom = false;

        if (page == 1) { //first load or onRefresh
            mAdapter.getList().clear();
            mSwipeRefreshLayout.setRefreshing(false);
        }

        mAdapter.addList(results);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorView() {
        mSwipeRefreshLayout.setRefreshing(false);
        ToastUtils.showShort(getActivity(), "解析错误");
    }

    @Override
    public void netUnConnect() {
        mSwipeRefreshLayout.setRefreshing(false);
        ToastUtils.showShort(getActivity(), "网络不可用");
    }

    @Override
    public void hideLoading() {
        if(page == 1){
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showLoading() {
        if(page == 1){
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        loadMore(page);
    }


    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisibleItem;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                if (!isLoadingBottom) {
                    loadMore(++page);
                    isLoadingBottom = true;
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

    @Override
    protected void lazyLoad() {

        if (isPrepared && isVisible && !isLoadedTop) {
            loadMore(page);
            isLoadedTop = true;
        }

    }
}
