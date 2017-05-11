package com.ufo.tools;


import android.content.Context;
import android.content.Intent;

import com.ufo.retrofitextend.RetrofitExtendFactory;
import com.ufo.socketioandroiddemo.login.UserInfoRepository;
import com.ufo.socketioandroiddemo.message.api.MessageAPI;
import com.ufo.socketioandroiddemo.message.model.ChatBean;
import com.ufo.socketioandroiddemo.message.model.ChatMessageBean;
import com.ufo.socketioandroiddemo.message.model.ChatMessageModel;
import com.ufo.socketioandroiddemo.message.model.ChatModel;
import com.ufo.socketioandroiddemo.message.repository.ChatMessageRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.realm.RealmResults;
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

                //intentSendMessage.putExtra("model",model);

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

                //intentReceiveMessage.putExtra("model",model);

                context.sendBroadcast(intentReceiveMessage);

                Intent intentUpdateChat = new Intent(NotificationAction.Update_Chat);
                context.sendBroadcast(intentUpdateChat);

            }
        });


    }

    public void getRecent(final Context context) {

        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {

                Intent intentGetRecentBegin = new Intent(NotificationAction.Get_Recent_Begin);
                context.sendBroadcast(intentGetRecentBegin);

                long last = 0;
                long current = System.currentTimeMillis();

                RealmResults<ChatBean> chatBeans = ChatMessageRepository.getInstance().getChat();

                if (chatBeans.size() > 0) {
                    last = chatBeans.first().getCreateTime();
                }

                List<ChatMessageBean> dataChatMessage = new ArrayList<>();


                Retrofit retrofit = RetrofitExtendFactory.createNormalRetrofit(context);
                MessageAPI messageAPI = retrofit.create(MessageAPI.class);


                List<ChatModel> chatListSycResult = null;

                try {
                    chatListSycResult = messageAPI.chatListSyc(UserInfoRepository.getInstance().currentUser(context).getSID(),
                            last, current).execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for (ChatModel chatModel : chatListSycResult) {
                    ChatMessageRepository.getInstance().createOrUpdateChat(chatModel.toBean());
                }


                RealmResults<ChatMessageBean> chatMessageBeans = ChatMessageRepository.getInstance().getChatMessage();

                if (chatMessageBeans.size() > 0) {
                    last = chatMessageBeans.first().getTime();
                } else {
                    last = System.currentTimeMillis();
                }


                List<ChatMessageModel> chatMessageListSycResult = null;

                try {
                    chatMessageListSycResult = messageAPI.chatMessageListSyc(UserInfoRepository.getInstance().currentUser(context).getSID(),
                            last, current).execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for (ChatMessageModel chatMessageModel : chatMessageListSycResult) {
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

            }
        });

    }


    interface sendChatMessageCallback {
        void send(ChatMessageModel chatMessageModel);
    }

}
