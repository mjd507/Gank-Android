package common.ui;


import android.support.v4.app.Fragment;

import common.utils.LogUtils;

/**
 * 描述:
 * Created by mjd on 2017/1/2.
 */
public abstract class BaseFragment extends Fragment {

    protected boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            this.isVisible = true;
            onVisible();
        } else {
            this.isVisible = false;
            onInvisible();
        }

    }


    protected void onInvisible() {
        LogUtils.d("BaseFragment", "do  onInvisible");
    }

    protected void onVisible() {
        LogUtils.d("BaseFragment", "do  onVisible---");
        lazyLoad();
    }

    protected abstract void lazyLoad();

}


}