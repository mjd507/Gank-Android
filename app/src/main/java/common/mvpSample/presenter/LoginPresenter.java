package common.mvpSample.presenter;

import android.text.TextUtils;

import common.mvpSample.model.dataHelper.HttpHelper;
import common.mvpSample.view.ILoginView;

/**
 * 描述: 业务逻辑封装层
 * Created by mjd on 2017/1/6.
 */

public class LoginPresenter {
    private ILoginView loginView;

    public LoginPresenter(ILoginView loginView) {
        this.loginView = loginView;
    }

    public void login(String username, String password) {
        if (!checkInput(username, password)) {
            return;
        }
        // 执行登录请求的伪代码
        HttpHelper helper = new HttpHelper();
        helper.execRequest("url", new HttpHelper.HttpCallback() {
            @Override
            public void onSuccess() {
                loginView.showLoginSuccess();
                // 保存登录相关数据，如登录的标记，用户的唯一标识
                saveLoginData();
            }

            @Override
            public void onFail() {
                // 需要UI展示，暴露接口
                loginView.showLoginFail();
            }
        });
    }

    private void saveLoginData() {
        //do something
    }

    // 检查输入的合法性
    private boolean checkInput(String username, String password) {
        boolean result = true;
        // 1.检查为空
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            loginView.showInputNoNull();
            result = false;
        }
        // 2.检查长度
        if (username.length() != 11) {
            loginView.showUsernameLengthError();
            result = false;
        }
        return result;
    }
}
