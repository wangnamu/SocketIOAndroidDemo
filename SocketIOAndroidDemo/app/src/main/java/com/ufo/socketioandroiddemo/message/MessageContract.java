package com.ufo.socketioandroiddemo.message;

import android.content.Context;

import com.ufo.socketioandroiddemo.mvp.BasePresenter;
import com.ufo.socketioandroiddemo.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MessageContract {

    interface View extends BaseView {
        
    }

    interface  Presenter extends BasePresenter<View> {
        
    }
}
