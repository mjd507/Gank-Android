package com.cleaner.gank.theme;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.cleaner.commonandroid.R;

/**
 * 描述:
 * Created by mjd on 2017/2/16.
 */

public class ThemeChooseDialog extends Dialog {

    public ThemeChooseDialog(Context context) {
        this(context, R.style.ThemeDialogStyle);
        initView(context);
    }

    public ThemeChooseDialog(Context context, int themeResId) {
        super(context, R.style.ThemeDialogStyle);
        initView(context);
    }

    private void initView(Context context) {
        final View view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_theme, null);
        view.findViewById(R.id.theme1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(1);
            }
        });
        view.findViewById(R.id.theme2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(2);
            }
        });
        view.findViewById(R.id.theme3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(3);
            }
        });
        view.findViewById(R.id.theme4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(4);
            }
        });
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.width = width;
                view.setLayoutParams(params);
            }
        });

        this.setContentView(view);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(true);
    }

    private OnThemeItemClickListener mListener;

    private int width;

    public void setDialogWidth(int width) {
        this.width = width;
    }

    public interface OnThemeItemClickListener {
        void onClick(int positon);
    }

    public void setListener(OnThemeItemClickListener listener) {
        mListener = listener;
    }

}
