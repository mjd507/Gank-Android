package com.cleaner.gank.tag.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cleaner.commonandroid.R;
import com.cleaner.gank.tag.model.TagInfoBeen;
import com.cleaner.gank.tag.presenter.TagInfoPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述:
 * Created by mjd on 2017/2/11.
 */

public class InfoItemAdapter extends RecyclerView.Adapter<InfoItemAdapter.ViewHolder> {
    private List<TagInfoBeen> results;
    private TagInfoPresenter presenter;

    public InfoItemAdapter(List<TagInfoBeen> results, TagInfoPresenter presenter) {
        this.results = results;
        this.presenter = presenter;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.iv_iamge)
        ImageView ivImage;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public InfoItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InfoItemAdapter.ViewHolder holder, int position) {
        holder.tvDesc.setText(results.get(position).desc);
        List<String> images = results.get(position).images;
        if (images != null && images.size() > 0) {
            presenter.getImage(holder.ivImage, images.get(0), R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

}
