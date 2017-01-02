package common.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cleaner.commonandroid.R;
import common.ui.dialog.LoadingDialog;

/**
 * 描述:
 * Created by mjd on 2017/1/2.
 */
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {

    public boolean isLeftBack = true;   //默认左上角代表返回按钮

    public ImageView ivLeft;
    public ImageView ivRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initData();
        initListeners();
    }

    protected abstract void initViews();

    protected abstract void initData();

    protected abstract void initListeners();

    protected abstract void handleClick(View view);

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_title_left && isLeftBack) {    //点击左边 并且代表返回时，finish
            this.finish();
        } else {
            handleClick(v);
        }
    }

    /**
     * 设置标题
     */
    public void setTitle(String title) {
        TextView tvTitle = (TextView) this.findViewById(R.id.tv_title);
        tvTitle.setText(title);
    }

    /**
     * 设置标题、返回按钮
     */
    public void setTitleBack(String title) {
        setTitle(title);
        ivLeft = (ImageView) this.findViewById(R.id.iv_title_left);
        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setImageResource(R.mipmap.btn_title_back);
        ivLeft.setOnClickListener(this);
    }

    /**
     * 设置标题、返回按钮、右边图片
     */
    public void setTitleBackAndRight(String title, int rightId) {
        setTitleBack(title);
        ivRight = (ImageView) this.findViewById(R.id.iv_title_right);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(rightId);
        ivRight.setOnClickListener(this);
    }

    /**
     * 设置标题、左边图片、右边图片
     */
    public void setTitleLeftAndRight(String title, int leftId, int rightId) {
        isLeftBack = false;
        setTitleBackAndRight(title, rightId);
        ivLeft.setImageResource(leftId);
    }

    /**
     * 进入Activity
     */
    protected void enterActivity(Class<?> targetClass) {
        startActivity(new Intent(this, targetClass));
    }

    /**
     * 进入Activity（带数据）
     */
    protected void enterActivity(Class<?> targetClass, Bundle bundle) {
        Intent intent = new Intent(this, targetClass);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 进入指定activity并清除压在该Activity上面的Activity
     */
    protected void enterActivityAndClearTop(Class<?> targetClass) {
        Intent intent = new Intent();
        intent.setClass(this, targetClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }


    private LoadingDialog progressDialog;

    /**
     * 显示加载框
     */
    protected void showLoading(String msg) {
        progressDialog = new LoadingDialog(this, msg);
        progressDialog.show();
    }

    /**
     * 关闭加载框
     */
    protected void closeLoading(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }

}