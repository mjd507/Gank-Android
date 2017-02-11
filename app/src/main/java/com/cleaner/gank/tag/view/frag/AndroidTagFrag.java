package com.cleaner.gank.tag.view.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class AndroidTagFrag extends BaseTagFragment {

    private int page = 1;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.getInfo(TagType.Android, page + "");
    }

    @Override
    public void showSuccessView(List<TagInfoBeen> results) {
        super.showSuccessView(results);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.Adapter mAdapter = new InfoItemAdapter(results, presenter);
        mRecyclerView.setAdapter(mAdapter);
    }

    
}
