package com.cleaner.commonandroid.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.cleaner.commonandroid.R;

/**
 * 描述: 加载时的对话框
 * Created by mjd on 2016/11/26.
 */

public class LoadingDialog extends Dialog {
    private Context mContext;
    private AnimationDrawable mAnimDrawable;

    public LoadingDialog(Context context) {
        this(context, 0);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
    }

    private void initView() {
        ImageView iv = new ImageView(mContext);
        iv.setImageResource(R.drawable.dialog_anim);
        iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mAnimDrawable = (AnimationDrawable) iv.getDrawable();
        mAnimDrawable.start();
        this.setContentView(iv);
        this.setTitle(null);
    }

    private void initListener() {
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mAnimDrawable != null && mAnimDrawable.isRunning()) {
                    mAnimDrawable.stop();
                }
            }
        });
    }
}
