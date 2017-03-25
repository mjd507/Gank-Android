package com.cleaner.gank.tag.view.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cleaner.gank.R;
import com.cleaner.gank.detail.ImageDetailActivity;
import com.cleaner.gank.tag.model.TagInfoBeen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.image.ImagePresenter;

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        PicViewHolder picViewHolder = (PicViewHolder) holder;
        final String url = results.get(position).url;
        ViewGroup.LayoutParams params = picViewHolder.ivImage.getLayoutParams();
        params.height = screenSize[1] / 5 * 2;
        picViewHolder.ivImage.setLayoutParams(params);
        presenter.getImage(picViewHolder.ivImage, url + "?imageView2/0/w/" + screenSize[0] / 2);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ImageDetailActivity.class);
                intent.putExtra(ImageDetailActivity.URL, url);
                intent.putExtra(ImageDetailActivity.POSITION, position);
                intent.putExtra(ImageDetailActivity.PIC_LIST, (Serializable) results);
                v.getContext().startActivity(intent);
            }
        });
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
