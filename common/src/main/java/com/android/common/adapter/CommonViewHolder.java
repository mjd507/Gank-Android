package com.android.common.adapter;

import android.content.Context;
import android.view.View;

/**
 * 描述：ViewHolder的基类
 * 作者 mjd
 * 日期：2015/11/3 10:22
 */
public abstract class CommonViewHolder<Bean> {

    protected Context context;
    protected View rootView;
    protected Bean bean;

    public CommonViewHolder(Context context) {
        this.context = context;
        rootView = initView(context);
        rootView.setTag(this);
    }

    public abstract View initView(Context context);

    public View getRootView() {
        return rootView;
    }

    public abstract void refreshView(Bean bean);

    public void setBean(Bean bean) {
        this.bean = bean;
        refreshView(bean);
    }

}
