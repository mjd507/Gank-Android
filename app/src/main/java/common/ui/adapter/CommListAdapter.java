package common.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import java.util.List;

/**
 * 描述：adapter的基类
 * 作者 mjd
 * 日期：2015/11/3 9:46
 */
public abstract class CommListAdapter<Bean> extends BaseAdapter {
    private Context context;
    private List<Bean> list;

    public CommListAdapter(Context context, List<Bean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommListViewHolder<Bean> holder;
        if (convertView == null) {
            holder = getViewHolder(context);
        } else {
            holder = (CommListViewHolder<Bean>) convertView.getTag();
        }
        holder.setBean(list.get(position));
        return holder.getRootView();
    }

    public abstract CommListViewHolder<Bean> getViewHolder(Context context);

}
