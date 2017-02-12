package com.cleaner.gank.tag.view.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cleaner.gank.tag.TagType;
import com.cleaner.gank.tag.view.BaseTagFragment;

import common.utils.LogUtils;

/**
 * 描述:
 * Created by mjd on 2017/2/11.
 */

public class WebTagFrag extends BaseTagFragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadMore(page);
        LogUtils.d("WebTagFrag", "onActivityCreated");
    }

    @Override
    public void loadMore(int page) {
        presenter.getInfo(TagType.Web, page + "");
    }


}
