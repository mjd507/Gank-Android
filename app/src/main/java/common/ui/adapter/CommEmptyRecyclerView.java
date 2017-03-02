package common.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 描述:
 * Created by mjd on 2017/2/28.
 */

public class CommEmptyRecyclerView extends RecyclerView {

    private View mEmptyView;

    public CommEmptyRecyclerView(Context context) {
        this(context, null);
    }

    public CommEmptyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setEmptyView(View view) {
        this.mEmptyView = view;
        ((ViewGroup) this.getRootView()).addView(mEmptyView);
    }


    private AdapterDataObserver mObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            Adapter adapter = getAdapter();
            if (adapter.getItemCount() == 0) {
                mEmptyView.setVisibility(VISIBLE);
                CommEmptyRecyclerView.this.setVisibility(GONE);
            } else {
                mEmptyView.setVisibility(GONE);
                CommEmptyRecyclerView.this.setVisibility(VISIBLE);
            }
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            onChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            onChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            onChanged();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            onChanged();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            onChanged();
        }
    };

}
