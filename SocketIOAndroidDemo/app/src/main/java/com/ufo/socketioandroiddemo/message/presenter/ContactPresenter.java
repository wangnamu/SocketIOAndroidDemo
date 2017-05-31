package com.ufo.socketioandroiddemo.message.presenter;


import com.ufo.socketioandroiddemo.message.contract.ContactContract;
import com.ufo.socketioandroiddemo.message.model.ChatBean;
import com.ufo.socketioandroiddemo.message.model.ChatModel;
import com.ufo.socketioandroiddemo.message.repository.ChatMessageRepository;
import com.ufo.socketioandroiddemo.mvp.BasePresenterImpl;

import io.realm.RealmResults;

/**
 * Created by tjpld on 2017/5/9.
 */

public class ContactPresenter extends BasePresenterImpl<ContactContract.View> implements ContactContract.Presenter {


    @Override
    public void loadData() {

        if (mView == null)
            return;
        if (mView.getDataSource().size() > 0) {
            mView.getDataSource().clear();
        }

        mView.getDataSource().addAll(ChatMessageRepository.getInstance().getContact());

        mView.getHandler().post(new Runnable() {
            @Override
            public void run() {
                if (mView != null)
                    mView.refreshData();
            }
        });

    }


}
