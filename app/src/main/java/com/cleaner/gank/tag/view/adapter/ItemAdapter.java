package com.cleaner.gank.tag.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cleaner.commonandroid.R;
import com.cleaner.gank.tag.model.TagInfoBeen;
import com.cleaner.gank.tag.presenter.TagInfoPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述:
 * Created by mjd on 2017/2/11.
 */

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<TagInfoBeen> results;
    private TagInfoPresenter presenter;

    public ItemAdapter(TagInfoPresenter presenter) {
        if (results == null) {
            results = new ArrayList<>();
        }
        this.presenter = presenter;
    }

    public void addList(List<TagInfoBeen> infos) {
        if (infos != null) {
            results.addAll(infos);
        }
    }

    public List<TagInfoBeen> getList() {
        return results;
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.iv_iamge)
        ImageView ivImage;
        @BindView(R.id.tv_author)
        TextView tvAuthor;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.tvDesc.setText(results.get(position).desc);
            List<String> images = results.get(position).images;
            if (images != null && images.size() > 0) {
                //图片效果不好，暂不显示
                //String url = images.get(0) + "?imageView2/0/w/160";
                //presenter.getImage(itemViewHolder.ivImage, url, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            }
            itemViewHolder.tvAuthor.setText(results.get(position).who);
        }

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

}
