package com.cleaner.gank.tag.view.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cleaner.commonandroid.R;
import com.cleaner.gank.tag.TagType;
import com.cleaner.gank.tag.model.TagInfoBeen;
import com.cleaner.gank.tag.presenter.TagInfoPresenter;
import com.cleaner.gank.tag.view.ITagInfoView;
import com.cleaner.gank.tag.view.adapter.PicItemAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.http.volley.HttpTask;
import common.ui.BaseFragment;
import common.utils.EncodeUtils;
import common.utils.ScreenUtils;
import common.utils.ToastUtils;

/**
 * 描述:
 * Created by mjd on 2017/2/11.
 */

public class WelfareTagFrag extends BaseFragment implements ITagInfoView, SwipeRefreshLayout.OnRefreshListener {

    protected TagInfoPresenter presenter;

    @BindView(R.id.swipeRefreshLayout)
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;

    private PicItemAdapter mAdapter;
    private GridLayoutManager mLayoutManager;

    protected int page;
    private boolean isPrepared; //标记布局加载完成，避免 NullPointer
    private boolean hasLoadedTop; //控制每次 tab 切换时都加载数据的问题
    private boolean isLoading; //控制加载的变量

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        presenter = new TagInfoPresenter(this);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new PicItemAdapter();
        int[] screenSize = ScreenUtils.getScreenSize(getActivity());
        mAdapter.setItemWith(screenSize);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(onScrollListener);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        page = 1;
        lazyLoad();
    }

    @Override
    public void showSuccessView(List<TagInfoBeen> results) {
        isLoading = false;

        if (page == 1) { //first load or onRefresh
            mAdapter.getList().clear();
            mSwipeRefreshLayout.setRefreshing(false);
        }

        mAdapter.addList(results);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorView(HttpTask.ErrorType errorType) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (errorType == HttpTask.ErrorType.NetUnConnect) {
            ToastUtils.showShort(getActivity(), "网络不可用");
        } else if (errorType == HttpTask.ErrorType.OTHER) {
            ToastUtils.showShort(getActivity(), "加载失败");
        } else if (errorType == HttpTask.ErrorType.NODATA) {
            ToastUtils.showShort(getActivity(), "暂无数据");

        }

    }


    @Override
    public void hideLoading() {
        if (page == 1) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showLoading() {
        if (page == 1) {
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
                if (!isLoading) {
                    isLoading = true;
                    loadMore(++page);
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
    protected void lazyLoad() {

        if (isPrepared && isVisible && !hasLoadedTop) {
            loadMore(page);
            hasLoadedTop = true;
        }

    }

    @Override
    protected String getStartPageName() {
        return this.getClass().getSimpleName();
    }

    public void loadMore(int page) {
        presenter.getInfo(EncodeUtils.urlEncode(TagType.Welfare), page + "");
    }

    @Override
    public void onPause() {
        super.onPause();
        mSwipeRefreshLayout.setRefreshing(false);
        if (presenter != null) presenter.cancelAll();
    }
}
