package com.ufo.socketioandroiddemo.message.contract;

import android.os.Handler;

import com.ufo.socketioandroiddemo.message.model.ChatMessageModel;
import com.ufo.socketioandroiddemo.mvp.BasePresenter;
import com.ufo.socketioandroiddemo.mvp.BaseView;

import java.util.List;

/**
 * Created by tjpld on 2017/5/9.
 */

public class ChatMessageContract {

    public interface View extends BaseView {

        Handler getHandler();

        List<ChatMessageModel> getDataSource();

        void reloadDataComplete();

        void loadMoreDataComplete();

        void updateChatMessageCell();

        void reloadData();
    }

    public interface Presenter extends BasePresenter<ChatMessageContract.View> {

        void initExecutorService();

        void shutDownExecutorService();

        void loadMoreDataWithChatID(String chatID);

        void reloadDataWithChatID(String chatID);

        void sendText(String body, String chatID);

        void insertChatMessage(ChatMessageModel model);

        void updateChatMessage(ChatMessageModel model);
    }
}
