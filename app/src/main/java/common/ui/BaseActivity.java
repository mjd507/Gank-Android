package common.ui;


import android.support.v7.app.AppCompatActivity;

import common.ui.dialog.LoadingDialog;
import common.utils.ToastUtils;

/**
 * 描述:
 * Created by mjd on 2017/1/2.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private LoadingDialog loadingDialog;

    /**
     * 显示加载框
     */
    public void showBaseLoading() {
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
    }

    /**
     * 关闭加载框
     */
    public void closeBaseLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    public void showNetError() {
        ToastUtils.showShort(this, "网络不可用");
    }


    public void showBaseErrorView() {

    }

}