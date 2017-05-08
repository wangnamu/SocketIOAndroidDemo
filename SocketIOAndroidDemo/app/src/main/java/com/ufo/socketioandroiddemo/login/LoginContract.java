package com.ufo.socketioandroiddemo.login;

import com.ufo.socketioandroiddemo.mvp.BasePresenter;
import com.ufo.socketioandroiddemo.mvp.BaseView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LoginContract {

    interface View extends BaseView {
        void loginSuccess();

        void loginFail(String errorMessage);
    }

    interface Presenter extends BasePresenter<View> {
        void login(String username, String password);
    }
}
