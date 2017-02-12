package com.cleaner.gank.tag.view.frag;

import com.cleaner.gank.tag.TagType;
import com.cleaner.gank.tag.view.BaseTagFragment;

import common.utils.EncodeUtils;

/**
 * 描述:
 * Created by mjd on 2017/2/11.
 */

public class WelfareTagFrag extends BaseTagFragment {

    @Override
    public void loadMore(int page) {
        presenter.getInfo(EncodeUtils.urlEncode(TagType.Welfare), page + "");
    }


}
