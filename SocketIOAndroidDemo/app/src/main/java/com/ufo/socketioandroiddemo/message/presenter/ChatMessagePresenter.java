package com.ufo.socketioandroiddemo.message.presenter;

import com.ufo.socketioandroiddemo.message.contract.ChatMessageContract;
import com.ufo.socketioandroiddemo.message.model.ChatMessageBean;
import com.ufo.socketioandroiddemo.message.model.ChatMessageModel;
import com.ufo.socketioandroiddemo.message.repository.ChatMessageRepository;
import com.ufo.socketioandroiddemo.mvp.BasePresenterImpl;

import java.util.Collections;

import io.realm.RealmResults;

/**
 * Created by tjpld on 2017/5/9.
 */

public class ChatMessagePresenter extends BasePresenterImpl<ChatMessageContract.View> implements ChatMessageContract.Presenter {

    private boolean isLoading = false;
    private boolean hasMore = false;
    private int pageSize = 10;


    @Override
    public void loadMoreDataWithChatID(String chatID) {

    }

    @Override
    public void reloadDataWithChatID(final String chatID) {

        if (mView == null)
            return;

        mView.getHandler().post(new Runnable() {

            @Override
            public void run() {

                isLoading = true;

                RealmResults<ChatMessageBean> result = ChatMessageRepository.getInstance().getChatMessageByChatID(chatID);

                if (mView.getDataSource().size() > 0)
                    mView.getDataSource().clear();

                int start = mView.getDataSource().size() > pageSize ? result.size() - pageSize : 0;

                for (int i = start; i < result.size(); i++) {
                    ChatMessageBean bean = result.get(i);
                    mView.getDataSource().add(ChatMessageModel.fromBean(bean));
                }

                hasMore = start > 0;
                mView.reloadDataComplete();
                isLoading = false;

            }
        });

    }


    @Override
    public void insertChatMessage(final ChatMessageModel model) {

        if (mView == null)
            return;

        mView.getHandler().post(new Runnable() {
            @Override
            public void run() {
                mView.getDataSource().add(model);
                mView.updateChatMessageCell();
            }
        });

    }

    @Override
    public void updateChatMessage(final ChatMessageModel model) {

        if (mView == null)
            return;

        mView.getHandler().post(new Runnable() {
            @Override
            public void run() {
                if (mView.getDataSource().size() > 0 && mView.getDataSource().contains(model)) {
                    int index = mView.getDataSource().indexOf(model);
                    Collections.replaceAll(mView.getDataSource(),mView.getDataSource().get(index),model);
                    mView.updateChatMessageCell();
                }
            }
        });

    }


    @Override
    public void sendText(String body, String chatID) {

    }

}
