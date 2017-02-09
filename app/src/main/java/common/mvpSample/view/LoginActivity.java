package common.mvpSample.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import common.CommonApplication;
import common.mvpSample.presenter.LoginPresenter;
import common.utils.ToastUtils;

/**
 * 描述:
 * Created by mjd on 2017/1/6.
 */

public class LoginActivity extends Activity implements ILoginView, View.OnClickListener {

    EditText mUserNameEditText, mPwdEditText;
    Button mLoginBtn;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout contentView = new LinearLayout(this);
        contentView.setOrientation(LinearLayout.VERTICAL);
        mUserNameEditText = new EditText(this);
        mPwdEditText = new EditText(this);
        mLoginBtn = new Button(this);
        contentView.addView(mUserNameEditText);
        contentView.addView(mPwdEditText);
        contentView.addView(mLoginBtn);
        setContentView(contentView);

        loginPresenter = new LoginPresenter(this);

        mLoginBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view == mLoginBtn) {
            String username = mUserNameEditText.getText().toString();
            String password = mPwdEditText.getText().toString();
            loginPresenter.login(username, password);
        }
    }

    @Override
    public void showLoginSuccess() {
        ToastUtils.showShort(this, "登录成功");
    }

    @Override
    public void showLoginFail() {
        ToastUtils.showShort(this, "登录失败");
    }

    @Override
    public void showInputNoNull() {
        ToastUtils.showShort(this, "用户名和密码不能为空");
    }

    @Override
    public void showUsernameLengthError() {
        ToastUtils.showShort(this, "用户名长度不正确");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonApplication application = (CommonApplication) this.getApplication();
        application.destroyReceiver();
    }
}
