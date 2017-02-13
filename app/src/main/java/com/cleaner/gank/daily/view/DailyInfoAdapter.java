package com.cleaner.gank.daily.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cleaner.commonandroid.R;
import com.cleaner.gank.daily.model.DailyBeen;
import com.cleaner.gank.image.ImagePresenter;
import com.cleaner.gank.tag.TagType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述:
 * Created by mjd on 2017/2/13.
 */

public class DailyInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DailyBeen> list;
    private ImagePresenter imagePresenter;

    public DailyInfoAdapter() {
        if (list == null) {
            list = new ArrayList<>();
        }
        imagePresenter = new ImagePresenter();
    }

    public void addList(List<DailyBeen> result) {
        if (result != null && result.size() > 0) {
            handleList(result);
            list.addAll(result);
        }
    }

    private List<DailyBeen> handleList(List<DailyBeen> list) {
        if (list == null || list.size() == 0) return null;
        int i = findKeyIndex(list, TagType.Welfare);
        DailyBeen removeBeen = list.remove(i);
        list.add(0, removeBeen);
        return list;
    }

    private int findKeyIndex(List<DailyBeen> list, String key) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).type.equals(key)) {
                return i;
            }
        }
        return -1;
    }

    private static final int ITEM_HEAD_GIRL = 1;
    private static final int ITEM_NORMAL = 2;

    @Override
    public int getItemViewType(int position) {
        DailyBeen dailyBeen = list.get(position);
        if (dailyBeen.type.equals(TagType.Welfare)) {
            return ITEM_HEAD_GIRL;
        } else {
            return ITEM_NORMAL;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_HEAD_GIRL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily_1, parent, false);
            return new HeaderViewHolder(view);
        } else if (viewType == ITEM_NORMAL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily_2, parent, false);
            return new ItemViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            String url = list.get(position).url;
            imagePresenter.getImage(((HeaderViewHolder) holder).ivHeader, url);
        } else if (holder instanceof ItemViewHolder) {
            DailyBeen lastDailyBeen = list.get(position - 1);
            DailyBeen dailyBeen = list.get(position);
            if (lastDailyBeen.type.equals(dailyBeen.type)) {
                ((ItemViewHolder) holder).tvCategory.setVisibility(View.GONE);
            } else {
                ((ItemViewHolder) holder).tvCategory.setVisibility(View.VISIBLE);
                ((ItemViewHolder) holder).tvCategory.setText(dailyBeen.type);
            }
            ((ItemViewHolder) holder).tvDesc.setText(dailyBeen.desc);
            ((ItemViewHolder) holder).tvAuthor.setText(dailyBeen.who);
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_category)
        TextView tvCategory;
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

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_header)
        ImageView ivHeader;


        public HeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
