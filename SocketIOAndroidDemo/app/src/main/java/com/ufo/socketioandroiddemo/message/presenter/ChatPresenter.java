package com.ufo.socketioandroiddemo.message.presenter;

import com.ufo.socketioandroiddemo.message.contract.ChatContract;
import com.ufo.socketioandroiddemo.message.model.ChatModel;
import com.ufo.socketioandroiddemo.message.repository.ChatMessageRepository;
import com.ufo.socketioandroiddemo.mvp.BasePresenterImpl;

import java.util.List;


/**
 * Created by tjpld on 2017/5/9.
 */

public class ChatPresenter extends BasePresenterImpl<ChatContract.View> implements ChatContract.Presenter {

    @Override
    public void updateChat() {

        if (mView == null)
            return;

        if (mView.getDataSource().size() > 0) {
            mView.getDataSource().clear();
        }

        List<ChatModel> result = ChatMessageRepository.getInstance().getChat();
        mView.getDataSource().addAll(result);

        mView.getHandler().post(new Runnable() {
            @Override
            public void run() {
                if (mView != null)
                    mView.refreshData();
            }
        });

    }


}
