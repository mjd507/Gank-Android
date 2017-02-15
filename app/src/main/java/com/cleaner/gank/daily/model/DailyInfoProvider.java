package com.cleaner.gank.daily.model;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.cleaner.gank.constants.Urls;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import common.http.volley.HttpResponse;
import common.http.volley.HttpTask;
import common.http.volley.JsonUtil;
import common.utils.LogUtils;
import common.utils.SPUtils;
import common.utils.TimeUtils;

import static com.android.volley.VolleyLog.TAG;

/**
 * 描述:
 * Created by mjd on 2017/2/7.
 */

public class DailyInfoProvider {

    private DailyInfoListener dailyInfoListener;

    public DailyInfoProvider(@NonNull DailyInfoListener dailyInfoListener) {
        this.dailyInfoListener = dailyInfoListener;
    }

    private boolean isFromLocal;

    public void getDailyInfoFormLocal(String url) {
        String result = SPUtils.getInstence().getString(url, "");
        if (!TextUtils.isEmpty(result)) {
            isFromLocal = true;
            HttpResponse response = new HttpResponse(JsonUtil.getJsonObj(result));
            handlerResponse(url, response);
        } else {
            dailyInfoListener.onError(HttpTask.ErrorType.NODATA);
        }
    }


    public void getDailyInfoFromNet(Date date) {
        String day = TimeUtils.date2String(date, "yyyy/MM/dd");
        final String url = Urls.GET_DAILY_INFO + day;

        HttpTask task = new HttpTask();
        task.url = url;
        task.isPost = false;
        task.isShowLoadingDialog = true;
        task.tag = null;
        task.setListener(new HttpTask.Listener() {
            @Override
            public void showLoading() {
                dailyInfoListener.showLoading();
            }

            @Override
            public void hideLoading() {
                dailyInfoListener.hideLoading();
            }

            @Override
            public void onResponse(HttpResponse response) {
                handlerResponse(url, response);
            }

            @Override
            public void onErrorResponse(HttpTask.ErrorType errorType) {
                if (errorType == HttpTask.ErrorType.NetUnConnect) {
                    getDailyInfoFormLocal(url);
                }
                dailyInfoListener.onError(errorType);
            }


        });
        task.start();

    }

    private void handlerResponse(String url, HttpResponse response) {
        if (!isFromLocal) { //不是从本地获取，需要写入本地
            SPUtils.getInstence().putString(url, response.getResponse().toString());
        }
        boolean error = response.getState("error");
        if (error) {
            LogUtils.d(TAG, "response error !");
        } else {
            try {
                List<String> categories = response.getList("category", String.class);
                List<DailyBeen> list = new ArrayList<>();
                HttpResponse results = new HttpResponse(response.getResponse().getJSONObject("results"));
                for (int i = 0; i < categories.size(); i++) {
                    String category = categories.get(i);
                    List<DailyBeen> dailyBeen = results.getList(category, DailyBeen.class);
                    if (dailyBeen != null && dailyBeen.size() > 0) list.addAll(dailyBeen);
                }
                dailyInfoListener.onSuccess(list);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
