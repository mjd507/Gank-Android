package common.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 * Created by mjd on 2016/11/29.
 */

public abstract class CommRecyclerAdapter<T> extends RecyclerView.Adapter<CommRecyclerAdapter.VH> {

    private List<T> mDatas;

    public CommRecyclerAdapter() {
        if (this.mDatas == null) {
            this.mDatas = new ArrayList<>();
        }
    }

    public void addData(List<T> datas) {
        if (datas != null) {
            this.mDatas.addAll(datas);
        }
    }

    //根据 viewType 返回 布局 ID
    public abstract int getLayoutId(int viewType);

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return VH.get(parent, getLayoutId(viewType));
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        refreshItem(holder, mDatas.get(position), position);
    }

    public abstract void refreshItem(VH holder, T data, int position);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        private View mItemView;
        private SparseArray<View> mViews;

        private VH(View itemView) {
            super(itemView);
            mItemView = itemView;
            mViews = new SparseArray<>();
        }

        public static VH get(ViewGroup parent, int layoutId) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
            return new VH(itemView);
        }

        public <T extends View> T getView(int id) {
            View v = mViews.get(id);
            if (v == null) {
                v = mItemView.findViewById(id);
                mViews.put(id, v);
            }
            return (T) v;
        }


    }


}
