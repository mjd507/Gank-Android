package com.cleaner.gank.daily.presenter;

import com.android.volley.VolleyError;
import com.cleaner.gank.daily.view.IDailyView;
import com.cleaner.gank.daily.model.DailyBeen;
import com.cleaner.gank.daily.model.DailyInfoListener;
import com.cleaner.gank.daily.model.DailyInfoProvider;

import java.util.List;

/**
 * 描述:
 * Created by mjd on 2017/2/8.
 */

public class DailyInfoPresenter implements DailyInfoListener {

    private IDailyView dailyView;

    public DailyInfoPresenter(IDailyView dailyView) {
        this.dailyView = dailyView;
    }

    public void getDailyInfo() {
        DailyInfoProvider infoProvider = new DailyInfoProvider(this);
        infoProvider.getDailyInfo();
    }

    @Override
    public void showLoading() {
        dailyView.showLoading();
    }

    @Override
    public void hideLoading() {
        dailyView.hideLoading();
    }

    @Override
    public void netUnConnect() {
        dailyView.netUnConnect();
    }

    @Override
    public void onSuccess(List<DailyBeen> results) {
        dailyView.showSuccessView(results);
    }

    @Override
    public void onError(VolleyError error) {
        dailyView.showErrorView();
    }

}
