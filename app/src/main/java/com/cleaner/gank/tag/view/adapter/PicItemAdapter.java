package com.cleaner.gank.tag.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cleaner.commonandroid.R;
import common.image.ImagePresenter;
import com.cleaner.gank.tag.model.TagInfoBeen;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述:
 * Created by mjd on 2017/2/14.
 */

public class PicItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TagInfoBeen> results;
    private ImagePresenter presenter;
    private int[] screenSize;

    public PicItemAdapter() {
        if (results == null) {
            results = new ArrayList<>();
        }
        this.presenter = new ImagePresenter();
    }

    public void addList(List<TagInfoBeen> infos) {
        if (infos != null && infos.size() > 0) {
            results.addAll(infos);
        }
    }

    public void setItemWith(int[] screenSize) {
        this.screenSize = screenSize;
    }

    public List<TagInfoBeen> getList() {
        return results;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pic, parent, false);
        return new PicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PicViewHolder picViewHolder = (PicViewHolder) holder;
        String url = results.get(position).url;
        url += "?imageView2/0/w/" + screenSize[0];
        ViewGroup.LayoutParams params = picViewHolder.ivImage.getLayoutParams();
        params.height = screenSize[1] / 5 * 2;
        picViewHolder.ivImage.setLayoutParams(params);
        presenter.getImage(picViewHolder.ivImage, url);

    }

    @Override
    public int getItemCount() {
        return results.size();
    }


    public static class PicViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_iamge)
        ImageView ivImage;

        public PicViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
