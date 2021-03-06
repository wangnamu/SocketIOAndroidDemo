package com.ufo.tools;


import android.content.Context;
import android.content.Intent;

import com.ufo.retrofitextend.RetrofitExtendFactory;
import com.ufo.socketioandroiddemo.login.UserInfoRepository;
import com.ufo.socketioandroiddemo.message.api.MessageAPI;
import com.ufo.socketioandroiddemo.message.model.ChatMessageBean;
import com.ufo.socketioandroiddemo.message.model.ChatMessageModel;
import com.ufo.socketioandroiddemo.message.model.ChatModel;
import com.ufo.socketioandroiddemo.message.repository.ChatMessageRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Retrofit;

/**
 * Created by tjpld on 2017/5/10.
 */

public class MyChat {

    private ExecutorService mExecutorService;

    private static final MyChat shareInstance = new MyChat();

    public static MyChat getInstance() {
        return shareInstance;
    }

    private MyChat() {
        mExecutorService = Executors.newSingleThreadExecutor();
    }

    public void updateChatMessage(final Context context, final ChatMessageModel model) {

        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                ChatMessageBean bean = model.toBean();
                ChatMessageRepository.getInstance().updateChatMessage(bean);

                Intent intentSendMessage = new Intent(NotificationAction.Send_Message);

                intentSendMessage.putExtra("model", model);

                context.sendBroadcast(intentSendMessage);

                Intent intentUpdateChat = new Intent(NotificationAction.Update_Chat);
                context.sendBroadcast(intentUpdateChat);
            }
        });

    }

    public void sendChatMessage(final Context context, final ChatMessageModel model, final sendChatMessageCallback callback) {

        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                ChatMessageBean bean = model.toBean();
                bean.setLocalTime(System.currentTimeMillis());

                List<ChatMessageBean> list = new ArrayList<>();
                list.add(bean);

                ChatMessageRepository.getInstance().createChatMessage(list);

                callback.send(model);

                Intent intentUpdateChat = new Intent(NotificationAction.Update_Chat);
                context.sendBroadcast(intentUpdateChat);

            }
        });


    }


    public void receiveChatMessage(final Context context, final ChatMessageModel model) {

        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                ChatMessageBean bean = model.toBean();
                bean.setLocalTime(System.currentTimeMillis());

                List<ChatMessageBean> list = new ArrayList<>();
                list.add(bean);

                ChatMessageRepository.getInstance().createChatMessage(list);

                Intent intentReceiveMessage = new Intent(NotificationAction.Receive_Message);

                intentReceiveMessage.putExtra("model", model);

                context.sendBroadcast(intentReceiveMessage);

                Intent intentUpdateChat = new Intent(NotificationAction.Update_Chat);
                context.sendBroadcast(intentUpdateChat);

            }
        });


    }

    public void getRecent(final Context context, final getRecentCallback callback) {

        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {

                Intent intentGetRecentBegin = new Intent(NotificationAction.Get_Recent_Begin);
                context.sendBroadcast(intentGetRecentBegin);

                long last = 0;
                long current = System.currentTimeMillis();

                ChatModel chatLast = ChatMessageRepository.getInstance().getChatLast();

                if (chatLast != null) {
                    last = chatLast.getCreateTime();
                }

                List<ChatMessageBean> dataChatMessage = new ArrayList<>();


                Retrofit retrofit = RetrofitExtendFactory.createNormalRetrofit(context);
                MessageAPI messageAPI = retrofit.create(MessageAPI.class);


                List<ChatModel> chatListSycResult;

                try {
                    chatListSycResult = messageAPI.chatListSyc(UserInfoRepository.getInstance().currentUser(context).getSID(),
                            last, current).execute().body();
                } catch (IOException e) {
                    //e.printStackTrace();
                    return;
                }

                for (ChatModel chatModel : chatListSycResult) {
                    ChatMessageRepository.getInstance().createOrUpdateChat(chatModel.toBean());
                }


                ChatMessageModel chatMessageLast = ChatMessageRepository.getInstance().getChatMessageLast();

                if (chatMessageLast != null) {
                    last = chatMessageLast.getTime();
                } else {
                    last = UserInfoRepository.getInstance().currentUser(context).getLoginTime();
                }


                List<ChatMessageModel> chatMessageListSycResult;

                try {
                    chatMessageListSycResult = messageAPI.chatMessageListSyc(UserInfoRepository.getInstance().currentUser(context).getSID(),
                            last, current).execute().body();
                } catch (IOException e) {
                    //e.printStackTrace();
                    return;
                }


                Collections.sort(chatMessageListSycResult, new Comparator<ChatMessageModel>() {
                    @Override
                    public int compare(ChatMessageModel c1, ChatMessageModel c2) {
                        if (c1.getTime() < c2.getTime()) {
                            return -1;
                        } else if (c1.getTime() > c2.getTime()) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });


                for (ChatMessageModel chatMessageModel : chatMessageListSycResult) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    chatMessageModel.setLocalTime(System.currentTimeMillis());
                    dataChatMessage.add(chatMessageModel.toBean());
                }


                if (dataChatMessage.size() > 0) {
                    ChatMessageRepository.getInstance().createChatMessage(dataChatMessage);
                }


                Intent intentUpdateContact = new Intent(NotificationAction.Update_Contact);
                context.sendBroadcast(intentUpdateContact);

                Intent intentGetRecentFinish = new Intent(NotificationAction.Get_Recent_Finish);
                context.sendBroadcast(intentGetRecentFinish);

                if (callback != null) {
                    callback.getRecentFinish();
                }

            }
        });

    }


    public interface sendChatMessageCallback {
        void send(ChatMessageModel chatMessageModel);
    }

    public interface getRecentCallback {
        void getRecentFinish();
    }

}
