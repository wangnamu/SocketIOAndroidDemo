package com.ufo.socketioandroiddemo.setting;

import android.content.Context;

import com.ufo.socketioandroiddemo.mvp.BasePresenter;
import com.ufo.socketioandroiddemo.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SettingContract {
    interface View extends BaseView {
        
    }

    interface  Presenter extends BasePresenter<View> {
        
    }
}
