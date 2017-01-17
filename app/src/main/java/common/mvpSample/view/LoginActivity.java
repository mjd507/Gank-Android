package common.mvpSample.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import common.CommonApplication;
import common.mvpSample.presenter.LoginPresenter;
import common.ui.BaseActivity;
import common.utils.ToastUtils;

/**
 * 描述:
 * Created by mjd on 2017/1/6.
 */

public class LoginActivity extends BaseActivity implements ILoginView {

    EditText mUserNameEditText, mPwdEditText;
    Button mLoginBtn;
    private LoginPresenter loginPresenter;

    @Override
    protected void initViews() {
        LinearLayout contentView = new LinearLayout(this);
        contentView.setOrientation(LinearLayout.VERTICAL);
        mUserNameEditText = new EditText(this);
        mPwdEditText = new EditText(this);
        mLoginBtn = new Button(this);
        contentView.addView(mUserNameEditText);
        contentView.addView(mPwdEditText);
        contentView.addView(mLoginBtn);
        setContentView(contentView);
    }

    @Override
    protected void initData() {
        loginPresenter = new LoginPresenter(this);
    }

    @Override
    protected void initListeners() {
        mLoginBtn.setOnClickListener(this);
    }

    @Override
    protected void handleClick(View view) {
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
