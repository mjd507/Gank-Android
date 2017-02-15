package com.cleaner.gank.tag.view.frag;

import com.cleaner.gank.tag.TagType;
import com.cleaner.gank.tag.view.BaseTagFragment;

/**
 * 描述:
 * Created by mjd on 2017/2/11.
 */

public class IOSTagFrag extends BaseTagFragment {

    @Override
    public void loadMore(int page) {
        presenter.getInfo(TagType.iOS, page + "");
    }


    @Override
    protected String getStartPageName() {
        return this.getClass().getSimpleName();
    }

}
