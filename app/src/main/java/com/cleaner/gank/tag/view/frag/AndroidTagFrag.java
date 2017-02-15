package com.cleaner.gank.tag.view.frag;

import com.cleaner.gank.tag.TagType;
import com.cleaner.gank.tag.view.BaseTagFragment;

/**
 * 描述:
 * Created by mjd on 2017/2/11.
 */

public class AndroidTagFrag extends BaseTagFragment {


    @Override
    public void loadMore(int page) {
        presenter.getInfo(TagType.Android, page + "");
    }


    @Override
    protected String getStartPageName() {
        return this.getClass().getSimpleName();
    }

}
