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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述:
 * Created by mjd on 2017/2/11.
 */

public class InfoItemAdapter extends RecyclerView.Adapter<InfoItemAdapter.ItemViewHolder> {
    private List<TagInfoBeen> results;
    private TagInfoPresenter presenter;

    public InfoItemAdapter(TagInfoPresenter presenter) {
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

    public boolean isSameContent(List<TagInfoBeen> infos) {
        if (this.results != null && infos != null && results.size() == infos.size()) {
            for (int i = 0; i < infos.size(); i++) {
                if (!results.get(i).equals(infos.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private static final int TYPE_ITEM =  0;
    private static final int TYPE_FOOTER = 1;

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.iv_iamge)
        ImageView ivImage;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_load_more)
        TextView mFooterView;

        public FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position +  1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.tvDesc.setText(results.get(position).desc);
        List<String> images = results.get(position).images;
        if (images != null && images.size() > 0) {
            //presenter.getImage(holder.ivImage, images.get(0), R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

}
