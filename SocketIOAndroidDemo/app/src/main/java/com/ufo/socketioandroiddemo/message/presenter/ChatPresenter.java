package com.ufo.socketioandroiddemo.message.presenter;

import com.ufo.socketioandroiddemo.message.contract.ChatContract;
import com.ufo.socketioandroiddemo.message.model.ChatModel;
import com.ufo.socketioandroiddemo.message.repository.ChatMessageRepository;
import com.ufo.socketioandroiddemo.mvp.BasePresenterImpl;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;


/**
 * Created by tjpld on 2017/5/9.
 */

public class ChatPresenter extends BasePresenterImpl<ChatContract.View> implements ChatContract.Presenter {

    private ExecutorService mExecutorService;
    private Semaphore mSemaphore;


    @Override
    public void initExecutorService() {
        mExecutorService = Executors.newSingleThreadExecutor();
        mSemaphore = new Semaphore(1);
    }


    @Override
    public void shutDownExecutorService() {
        mExecutorService.shutdown();
    }


    @Override
    public void updateChat() {

        if (mExecutorService.isShutdown())
            return;

        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    mSemaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                final List<ChatModel> result = ChatMessageRepository.getInstance().getChat();

                if (mView != null) {
                    mView.getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            if (mView.getDataSource().size() > 0) {
                                mView.getDataSource().clear();
                            }
                            mView.getDataSource().addAll(result);
                            mView.refreshData();

                            mSemaphore.release();
                        }
                    });
                } else {
                    mSemaphore.release();
                }
            }
        });


    }


}
