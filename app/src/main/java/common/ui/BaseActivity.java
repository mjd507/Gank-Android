package common.ui;


import android.support.v4.app.FragmentActivity;

import common.ui.dialog.LoadingDialog;
import common.utils.ToastUtils;

/**
 * 描述:
 * Created by mjd on 2017/1/2.
 */
public abstract class BaseActivity extends FragmentActivity {

    private LoadingDialog loadingDialog;

    /**
     * 显示加载框
     */
    protected void showBaseLoading() {
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
    }

    /**
     * 关闭加载框
     */
    protected void closeBaseLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    protected void NetBaseUnConnect() {
        ToastUtils.showShort(this, "网络不可用");
    }


    protected void showBaseErrorView() {

    }

}