package com.ufo.socketioandroiddemo.message.presenter;


import com.ufo.retrofitextend.RetrofitExtendFactory;
import com.ufo.socketioandroiddemo.login.UserInfoBean;
import com.ufo.socketioandroiddemo.login.UserInfoRepository;
import com.ufo.socketioandroiddemo.message.api.MessageAPI;
import com.ufo.socketioandroiddemo.message.contract.ChatMessageContract;
import com.ufo.socketioandroiddemo.message.model.ChatMessageModel;
import com.ufo.socketioandroiddemo.message.model.MessageTypeEnum;
import com.ufo.socketioandroiddemo.message.model.SendStatusTypeEnum;
import com.ufo.socketioandroiddemo.message.repository.ChatMessageRepository;
import com.ufo.socketioandroiddemo.mvp.BasePresenterImpl;
import com.ufo.socketioservice.DeviceToken;
import com.ufo.tools.MyChat;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import retrofit2.Retrofit;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by tjpld on 2017/5/9.
 */

public class ChatMessagePresenter extends BasePresenterImpl<ChatMessageContract.View> implements ChatMessageContract.Presenter {

    private ExecutorService mExecutorService;
    private Semaphore mSemaphore;

    private boolean isLoading = false;
    private boolean hasMore = false;
    private int pageSize = 10;


    public boolean isLoading() {
        return isLoading;
    }


    public boolean isHasMore() {
        return hasMore;
    }


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
    public void loadMoreDataWithChatID(final String chatID) {

        if (mExecutorService.isShutdown())
            return;

        final int start = mView.getDataSource().size() - 1;

        final int totalCount = ChatMessageRepository.getInstance().getChatMessageSizeByChatID(chatID);

        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    mSemaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                isLoading = true;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                int length = totalCount - start > pageSize ? totalCount - start - pageSize : 0;

                final List<ChatMessageModel> list = ChatMessageRepository.getInstance().getChatMessageByChatID(chatID, length, totalCount - start);

                hasMore = length > 0;

                if (mView != null) {
                    mView.getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            if (mView.getDataSource().size() > 1) {
                                mView.getDataSource().addAll(1, list);
                            }
                            mView.loadMoreDataComplete();
                            isLoading = false;
                            mSemaphore.release();
                        }
                    });
                } else {
                    mSemaphore.release();
                }


            }
        });

    }

    @Override
    public void reloadDataWithChatID(final String chatID) {

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

                isLoading = true;

                int totalCount = ChatMessageRepository.getInstance().getChatMessageSizeByChatID(chatID);

                int start = totalCount > pageSize ? totalCount - pageSize : 0;

                final List<ChatMessageModel> list = ChatMessageRepository.getInstance().getChatMessageByChatID(chatID, start, totalCount);

                hasMore = start > 0;

                if (mView != null) {
                    mView.getHandler().post(new Runnable() {

                        @Override
                        public void run() {
                            if (mView.getDataSource().size() > 0)
                                mView.getDataSource().clear();
                            mView.getDataSource().addAll(list);
                            mView.reloadDataComplete();
                            isLoading = false;
                            mSemaphore.release();

                        }
                    });
                } else {
                    mSemaphore.release();
                }

            }
        });

    }


    @Override
    public void insertChatMessage(final ChatMessageModel model) {

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

                if (mView != null) {
                    mView.getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            mView.getDataSource().add(model);
                            mView.updateChatMessageCell();
                            mSemaphore.release();
                        }
                    });
                } else {
                    mSemaphore.release();
                }

            }
        });

    }

    @Override
    public void updateChatMessage(final ChatMessageModel model) {

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

                if (mView != null) {
                    mView.getHandler().post(new Runnable() {
                        @Override
                        public void run() {

                            if (mView.getDataSource().size() > 0 && mView.getDataSource().contains(model)) {
                                int index = mView.getDataSource().indexOf(model);
                                mView.getDataSource().set(index, model);
                                mView.updateChatMessageCell();
                            }

                            mSemaphore.release();

                        }
                    });
                } else {
                    mSemaphore.release();
                }


            }
        });


    }


    @Override
    public void sendText(String body, String chatID) {


        UserInfoBean userInfoBean = UserInfoRepository.getInstance().currentUser(mView.getAppContext());
        String deviceToken = DeviceToken.getDeviceToken(mView.getContext());
        if (userInfoBean == null || deviceToken == null) {
            return;
        }

        String messageID = UUID.randomUUID().toString();
        final ChatMessageModel model = new ChatMessageModel();
        model.setSID(messageID);
        model.setSenderID(userInfoBean.getSID());
        model.setSenderDeviceToken(deviceToken);
        model.setTitle(userInfoBean.getNickName());
        model.setBody(body);
        model.setTime(System.currentTimeMillis());
        model.setMessageType(MessageTypeEnum.Text);
        model.setNickName(userInfoBean.getNickName());
        model.setHeadPortrait(userInfoBean.getHeadPortrait());
        model.setChatID(chatID);
        model.setSendStatusType(SendStatusTypeEnum.Sending);


        MyChat.getInstance().sendChatMessage(mView.getAppContext(), model, new MyChat.sendChatMessageCallback() {
            @Override
            public void send(ChatMessageModel chatMessageModel) {
                insertChatMessage(model);
            }
        });


        Retrofit retrofit = RetrofitExtendFactory.createNormalRetrofit(mView.getAppContext());
        MessageAPI messageAPI = retrofit.create(MessageAPI.class);

        Subscription subscription = messageAPI.sendTextAsyc(chatID, body, messageID, userInfoBean.getSID(), deviceToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ChatMessageModel>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

//                        String errorMessage;
//
//                        if (e instanceof HttpException) {
//                            HttpException httpException = (HttpException) e;
//                            int code = httpException.code();
//                            String msg = httpException.getMessage();
//
//                            errorMessage = code + " " + msg;
//
//                        } else {
//                            errorMessage = e.getMessage();
//                        }


                    }

                    @Override
                    public void onNext(ChatMessageModel chatMessageModel) {
                        chatMessageModel.setSendStatusType(SendStatusTypeEnum.Sended);
                        if (mView != null) {
                            MyChat.getInstance().updateChatMessage(mView.getAppContext(), chatMessageModel);
                        }

                    }
                });

        addSubscription(subscription);

    }

}
