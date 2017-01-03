package common.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

import com.cleaner.commonandroid.R;

/**
 * 描述: 加载时的对话框
 * Created by mjd on 2016/11/26.
 */

public class LoadingDialog extends Dialog {
    private AnimationDrawable mAnimDrawable;

    public LoadingDialog(Context context) {
        this(context, R.style.CommonDialogStyle);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
        initView(context);
    }

    private void initView(Context context) {
        ImageView iv = new ImageView(context);
        iv.setImageResource(R.drawable.anim_dialog);
        iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mAnimDrawable = (AnimationDrawable) iv.getDrawable();
        mAnimDrawable.start();
        this.setContentView(iv);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(false);
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
