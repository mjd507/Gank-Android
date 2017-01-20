package com.cleaner.commonandroid.uiTest;

import android.app.Dialog;
import android.content.Context;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import common.ui.dialog.CommonDialog;
import common.utils.ToastUtils;

import static org.junit.Assert.assertEquals;

/**
 * 描述:
 * Created by mjd on 2017/1/3.
 */
@RunWith(AndroidJUnit4.class)
public class DialogTest  {

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.cleaner.commonandroid", appContext.getPackageName());
    }



//    @Override
//    protected void initViews() {
//        LinearLayout contentView = new LinearLayout(this);
//        contentView.setOrientation(LinearLayout.VERTICAL);
//        Button btnCommonDialog = new Button(this);
//        btnCommonDialog.setText("显示普通对话框");
//        btnCommonDialog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showComDialog();
//            }
//        });
//        Button btnLoadingDialog = new Button(this);
//        btnLoadingDialog.setText("显示加载对话框");
//        btnLoadingDialog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showLoadingDialog();
//            }
//        });
//        contentView.addView(btnCommonDialog);
//        contentView.addView(btnLoadingDialog);
//        setContentView(contentView);
//    }


    @Test
    public void showComDialog() {
        Looper.prepare();
        final Context context = InstrumentationRegistry.getTargetContext();
        CommonDialog dialog = new CommonDialog(context);
        dialog.setTitleText("提示");
        dialog.setMsgText("您好,欢迎乘坐和谐号动车!本次列车终点站: 上海虹桥站!");
        dialog.setNegativeBtn("取消", new CommonDialog.OnDialogClickListener() {
            @Override
            public void onClick(Dialog dialog, int which) {
                ToastUtils.showShort(context, "取消");
                dialog.dismiss();
            }
        });
        dialog.setPositiveBtn("确定", new CommonDialog.OnDialogClickListener() {
            @Override
            public void onClick(Dialog dialog, int which) {
                ToastUtils.showShort(context, "确认");
                dialog.dismiss();
            }
        });
        dialog.show();
        Looper.loop();
    }

}
