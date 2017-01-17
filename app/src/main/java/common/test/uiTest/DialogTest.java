package common.test.uiTest;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import common.ui.BaseActivity;
import common.ui.dialog.CommonDialog;
import common.utils.ToastUtils;

/**
 * 描述:
 * Created by mjd on 2017/1/3.
 */

public class DialogTest extends BaseActivity {
    @Override
    protected void initViews() {
        LinearLayout contentView = new LinearLayout(this);
        contentView.setOrientation(LinearLayout.VERTICAL);
        Button btnCommonDialog = new Button(this);
        btnCommonDialog.setText("显示普通对话框");
        btnCommonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showComDialog();
            }
        });
        Button btnLoadingDialog = new Button(this);
        btnLoadingDialog.setText("显示加载对话框");
        btnLoadingDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoadingDialog();
            }
        });
        contentView.addView(btnCommonDialog);
        contentView.addView(btnLoadingDialog);
        setContentView(contentView);
    }


    private void showComDialog() {
        CommonDialog dialog = new CommonDialog(this);
        dialog.setTitleText("提示");
        dialog.setMsgText("您好,欢迎乘坐和谐号动车!本次列车终点站: 上海虹桥站!");
        dialog.setNegativeBtn("取消", new CommonDialog.OnDialogClickListener() {
            @Override
            public void onClick(Dialog dialog, int which) {
                ToastUtils.showShort(getApplicationContext(), "取消");
                dialog.dismiss();
            }
        });
        dialog.setPositiveBtn("确定", new CommonDialog.OnDialogClickListener() {
            @Override
            public void onClick(Dialog dialog, int which) {
                ToastUtils.showShort(getApplicationContext(), "确认");
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showLoadingDialog() {
        showLoading();
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void handleClick(View view) {

    }


}
