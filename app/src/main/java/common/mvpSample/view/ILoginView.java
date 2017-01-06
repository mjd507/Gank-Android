package common.mvpSample.view;

/**
 * 描述: View 和 Presenter 解耦层
 * Created by mjd on 2017/1/6.
 */

public interface ILoginView {

    void showLoginSuccess();

    void showLoginFail();

    void showInputNoNull();

    void showUsernameLengthError();
}
