package com.cleaner.gank.tag.view.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cleaner.gank.tag.TagType;
import com.cleaner.gank.tag.model.TagInfoBeen;
import com.cleaner.gank.tag.view.BaseTagFragment;
import com.cleaner.gank.tag.view.InfoItemAdapter;

import java.util.List;

/**
 * 描述:
 * Created by mjd on 2017/2/11.
 */

public class AndroidTagFrag extends BaseTagFragment implements SwipeRefreshLayout.OnRefreshListener {

    private InfoItemAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.addOnScrollListener(onScrollListener);
        onRefresh();
    }

    @Override
    public void showSuccessView(List<TagInfoBeen> results) {
        super.showSuccessView(results);
        isLoading = false;
        if (mAdapter == null) {
            mAdapter = new InfoItemAdapter(presenter);
            mRecyclerView.setAdapter(mAdapter);
        }
        if (page == 1) { //first load or onRefresh
            mAdapter.getList().clear();
        }
        mAdapter.addList(results);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        page = 1;
        presenter.getInfo(TagType.Android, page + "");
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
                    loadMore();
                }

            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        }
    };

    private void loadMore() {
        isLoading = true;
        presenter.getInfo(TagType.Android, ++page + "");
    }

}
