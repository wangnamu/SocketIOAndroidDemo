package com.ufo.socketioandroiddemo.message.contract;

import com.ufo.socketioandroiddemo.mvp.BasePresenter;
import com.ufo.socketioandroiddemo.mvp.BaseView;

/**
 * Created by tjpld on 2017/5/9.
 */

public class ChatContract {

    public interface View extends BaseView {

    }

    public interface Presenter extends BasePresenter<View> {

    }
}
