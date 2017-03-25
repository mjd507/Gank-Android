package com.cleaner.gank.tag.view.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cleaner.gank.R;
import com.cleaner.gank.detail.InfoDetailActivity;
import com.cleaner.gank.tag.model.TagInfoBeen;

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

    public ItemAdapter() {
        if (results == null) {
            results = new ArrayList<>();
        }
    }

    public void addList(List<TagInfoBeen> infos) {
        if (infos != null && infos.size() > 0) {
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        final TagInfoBeen been = results.get(position);
        itemViewHolder.tvDesc.setText(been.desc);
        itemViewHolder.tvDesc.setTextColor(itemViewHolder.tvDesc.getContext().getResources().getColor(been.isRead ? R.color.text_be : R.color.black));
        itemViewHolder.tvAuthor.setText(been.who);

        itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                been.isRead = true;
                Intent intent = new Intent(v.getContext(), InfoDetailActivity.class);
                intent.putExtra(InfoDetailActivity.EXTRA_URL, been.url);
                intent.putExtra(InfoDetailActivity.EXTRA_TITLE, been.desc);
                v.getContext().startActivity(intent);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

}
