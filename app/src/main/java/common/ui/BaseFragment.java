package common.ui;


import android.support.v4.app.Fragment;

import com.umeng.analytics.MobclickAgent;

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
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected abstract void lazyLoad();


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getStartPageName());
    }

    protected abstract String getStartPageName();

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getStartPageName());
    }
}