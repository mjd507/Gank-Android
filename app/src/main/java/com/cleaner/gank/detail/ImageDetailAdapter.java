package com.cleaner.gank.detail;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cleaner.commonandroid.R;
import com.cleaner.gank.tag.model.TagInfoBeen;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.image.ImagePresenter;

/**
 * 描述:
 * Created by mjd on 2017/2/15.
 */

public class ImageDetailAdapter extends PagerAdapter {
    private List<TagInfoBeen> list;
    private ImagePresenter mImagePresenter;

    public ImageDetailAdapter() {
        if (list == null) {
            list = new ArrayList<>();
        }
        mImagePresenter = new ImagePresenter();
    }

    public void addList(List<TagInfoBeen> infoList) {
        if (infoList != null && infoList.size() > 0) {
            list.addAll(infoList);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_page_pic, container, false);
        ViewHolder holder = new ViewHolder(view);
        mImagePresenter.getImage(holder.iv, list.get(position).url);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public static class ViewHolder {
        @BindView(R.id.iv_iamge)
        ImageView iv;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
