package common.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * 描述: 为已有的 Adapter 添加 Header 和 Footer
 * Created by mjd on 2017/2/28.
 *
 * CommRecyclerAdapter adapter = new CommRecyclerAdapter();
 * adapter.addData();
 * View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, mRecyclerView, false);
 * View footerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer, mRecyclerView, false);
 * CommRecyclerAdapterWrapper newAdapter = new CommRecyclerAdapterWrapper(adapter);
 * newAdapter.addHeaderView(headerView);
 * newAdapter.addFooterView(footerView);
 * mRecyclerView.setAdapter(newAdapter);
 */

public class CommRecyclerAdapterWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    enum ITEM_TYPE {
        HEADER,
        FOOTER,
        NORMAL
    }

    private CommRecyclerAdapter mAdapter;
    private View mHeaderView;
    private View mFooterView;

    public CommRecyclerAdapterWrapper(CommRecyclerAdapter adapter) {
        this.mAdapter = adapter;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_TYPE.HEADER.ordinal();
        } else if (position == mAdapter.getItemCount() + 1) {
            return ITEM_TYPE.FOOTER.ordinal();
        } else {
            return ITEM_TYPE.NORMAL.ordinal();
        }
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount() + 2;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.HEADER.ordinal()) {
            return new RecyclerView.ViewHolder(mHeaderView) {
            };
        } else if (viewType == ITEM_TYPE.FOOTER.ordinal()) {
            return new RecyclerView.ViewHolder(mFooterView) {
            };
        } else {
            return mAdapter.onCreateViewHolder(parent, viewType);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            return;
        } else if (position == mAdapter.getItemCount() + 1) {
            return;
        } else {
            mAdapter.onBindViewHolder((CommRecyclerAdapter.VH) holder, position);
        }
    }

    public void addHeaderView(View view) {
        this.mHeaderView = view;
    }

    public void addFooterView(View view) {
        this.mFooterView = view;
    }

}
