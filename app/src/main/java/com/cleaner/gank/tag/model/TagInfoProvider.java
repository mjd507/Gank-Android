package com.cleaner.gank.tag.model;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.cleaner.gank.Urls;

import java.util.List;

import common.http.volley.HttpResponse;
import common.http.volley.HttpTask;
import common.http.volley.JsonUtil;
import common.http.volley.VolleyFactory;
import common.utils.LogUtils;
import common.utils.SPUtils;

import static com.android.volley.VolleyLog.TAG;

/**
 * 描述:
 * Created by mjd on 2017/2/7.
 */

public class TagInfoProvider {

    private TagInfoListener tagInfoListener;
    private String url;

    public TagInfoProvider(@NonNull TagInfoListener tagInfoListener) {
        this.tagInfoListener = tagInfoListener;
    }

    public void getTagInfoFormLocal(String category, String page) {
        //每页返回十条数据
        url = Urls.GET_CATEGORY_INFO + category + "/" + 10 + "/" + page;
        String result = SPUtils.getInstence().getString(url, "");
        if (!TextUtils.isEmpty(result)) {
            HttpResponse response = new HttpResponse(JsonUtil.getJsonObj(result));
            handlerResponse(response);
        }
    }

    public void getTagInfoFromNet(String category, String page) {
        //每页返回十条数据
        url = Urls.GET_CATEGORY_INFO + category + "/" + 10 + "/" + page;

        HttpTask task = new HttpTask();
        task.url = url;
        task.isPost = false;
        task.isShowLoadingDialog = true;
        task.tag = category;
        task.setListener(new HttpTask.Listener() {
            @Override
            public void showLoading() {
                tagInfoListener.showLoading();
            }

            @Override
            public void hideLoading() {
                tagInfoListener.hideLoading();
            }

            @Override
            public void netUnConnect() {
                tagInfoListener.netUnConnect();
            }

            @Override
            public void onResponse(HttpResponse response) {
                handlerResponse(response);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                tagInfoListener.onError(error);
            }
        });
        task.start();

    }

    private void handlerResponse(HttpResponse response) {
        SPUtils.getInstence().putString(url, response.toString());
        boolean error = response.getState("error");
        List<TagInfoBeen> results = null;
        if (error) {
            LogUtils.d(TAG, "response error !");
        } else {
            results = response.getList("results", TagInfoBeen.class);
        }
        tagInfoListener.onSuccess(results);
    }


    public void getImage(ImageView iv, String url, int defaultImage, int errorImage) {
        ImageLoader imageLoader = VolleyFactory.getInstance().getImageLoader();
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(iv, defaultImage, errorImage);
        imageLoader.get(url, imageListener);
    }


}
