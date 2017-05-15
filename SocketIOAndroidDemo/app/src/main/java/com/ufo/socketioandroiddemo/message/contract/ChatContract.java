package com.ufo.socketioandroiddemo.message.contract;

import android.os.Handler;

import com.ufo.socketioandroiddemo.message.model.ChatModel;
import com.ufo.socketioandroiddemo.mvp.BasePresenter;
import com.ufo.socketioandroiddemo.mvp.BaseView;

import java.util.List;

/**
 * Created by tjpld on 2017/5/9.
 */

public class ChatContract {

    public interface View extends BaseView {
        Handler getHandler();

        List<ChatModel> getDataSource();

        void refreshData();
    }

    public interface Presenter extends BasePresenter<View> {
        void updateChat();
    }
}
