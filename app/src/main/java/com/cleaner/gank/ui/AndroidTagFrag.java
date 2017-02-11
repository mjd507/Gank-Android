package com.cleaner.gank.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cleaner.gank.tag.model.TagInfoBeen;

import java.util.List;

/**
 * 描述:
 * Created by mjd on 2017/2/11.
 */

public class AndroidTagFrag extends TagFragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.getInfo("Android", "1");
    }

    @Override
    public void showSuccessView(List<TagInfoBeen> results) {
        super.showSuccessView(results);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView.Adapter mAdapter = new MyAdapter(results);
        mRecyclerView.setAdapter(mAdapter);
    }
}
