package com.ufo.socketioservice;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.ufo.socketioandroiddemo.MainActivity;
import com.ufo.socketioandroiddemo.login.UserInfoBean;
import com.ufo.socketioandroiddemo.login.UserInfoRepository;
import com.ufo.socketioandroiddemo.message.model.ChatMessageModel;
import com.ufo.socketioservice.model.SocketIOMessage;
import com.ufo.socketioservice.model.SocketIONotify;
import com.ufo.socketioservice.model.SocketIOResponse;
import com.ufo.socketioservice.model.SocketIOUserInfo;
import com.ufo.tools.MyChat;
import com.ufo.tools.NotificationAction;
import com.ufo.utils.BackgroundUtil;
import com.ufo.utils.NotificationUtil;

import java.net.URISyntaxException;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by tjpld on 2017/5/1.
 */

public class SocketIOManager {

    private Socket mSocket;
    //    private static final String mUrl = "http://192.168.16.61:3000";
    private static final String mUrl = "http://192.168.19.76:3000";

    private static final String LOGIN = "login";
    private static final String LOGOFF = "logoff";
    private static final String CHECKKICKOFF = "checkkickoff";

    private static final String EVENT_KICKOFF = "kickoff";
    private static final String EVENT_NEWS = "news";
    private static final String EVENT_NOTIFYOTHERPLATFORMS = "notifyotherplatforms";


    private static final String OthersTypeChat = "chat";
    private static final String OthersTypeMessage = "message";


    private static final SocketIOManager shareInstance = new SocketIOManager();

    public static SocketIOManager getInstance() {
        return shareInstance;
    }

    private SocketIOManager() {

        try {
            IO.Options opts = new IO.Options();
            opts.forceNew = false;
            mSocket = IO.socket(mUrl, opts);
        } catch (URISyntaxException e) {
            //e.printStackTrace();
            mSocket = null;
        }

    }

    public Socket getSocket() {
        return mSocket;
    }

    public Boolean connect(final Context context, final Boolean checkStatus) {

        final UserInfoBean userInfoBean = UserInfoRepository.getInstance().currentUser(context);
        final String deviceToken = DeviceToken.getDeviceToken(context);

        if (userInfoBean == null || deviceToken == null)
            return false;

        if (mSocket == null)
            return false;

        if (mSocket.connected())
            return true;


        mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                Log.e("SocketIOManager", "connecting");

                SocketIOUserInfo model = new SocketIOUserInfo();
                model.setSID(userInfoBean.getSID());
                model.setUserName(userInfoBean.getUserName());
                model.setNickName(userInfoBean.getNickName());

                model.setDeviceToken(deviceToken);

                Log.e("deviceToken", deviceToken);

                model.setDeviceType("ANDROID");
                model.setLoginTime(System.currentTimeMillis());
                model.setCheckStatus(checkStatus);


                if (mSocket != null) {

                    final Gson gson = new Gson();
                    final String json = gson.toJson(model);

                    mSocket.emit(LOGIN, json, new Ack() {
                        @Override
                        public void call(Object... args1) {

                            if (args1 != null && args1.length > 0) {
                                if (args1[0].toString().equals("NO ACK")) {
                                    Log.d("SocketIOManager", "reconnecting");
                                    mSocket.connect();
                                } else {

                                    String resp = args1[0].toString();
                                    SocketIOResponse socketIOResponse = gson.fromJson(resp, SocketIOResponse.class);
                                    if (socketIOResponse.getIsSuccess()) {
                                        Log.d("SocketIOManager", "login success");
                                    } else {
                                        Log.d("SocketIOManager", socketIOResponse.getMessage());
                                        Intent intentKickOff = new Intent(NotificationAction.SOCKETIO_KICKOFF);
                                        intentKickOff.putExtra("msg", socketIOResponse.getMessage());
                                        context.sendBroadcast(intentKickOff);
                                    }

                                }
                            }

                        }
                    });


                }

            }
        });


        mSocket.on(EVENT_NEWS, new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                Log.d("news", args + "");

                Ack ack = (Ack) args[args.length - 1];
                if (ack != null) {
                    ack.call("success");
                }

                Log.d("onNews->", args[0].toString());

                Gson gson = new Gson();

                SocketIOMessage message = gson.fromJson(args[0].toString(), SocketIOMessage.class);

                if (BackgroundUtil.isForeground(context)) {

                    if (message.getOthersType().equals(OthersTypeMessage)) {
                        ChatMessageModel chatMessageModel = gson.fromJson(gson.toJson(message.getOthers()), ChatMessageModel.class);
                        MyChat.getInstance().receiveChatMessage(context, chatMessageModel);
                    }

                } else {

                    if (message.getAlert()) {
                        PendingIntent pendingIntent = PendingIntent.getActivity(
                                context, 0, new Intent(context, MainActivity.class), 0);
                        NotificationUtil.sendNotification(context, message.getTitle(), message.getBody(), pendingIntent);
                    }

                }

            }
        });


        mSocket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("disconnect", args.toString());
            }
        });

        mSocket.on(Socket.EVENT_RECONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("reconnect", args.toString());
            }
        });


        mSocket.on(EVENT_KICKOFF, new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                Log.e("kickoff", args.toString());
                Intent intentKickOff = new Intent(NotificationAction.SOCKETIO_KICKOFF);
                context.sendBroadcast(intentKickOff);

            }
        });


        mSocket.on(EVENT_NOTIFYOTHERPLATFORMS, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("notifyotherplatforms", args.toString());
            }
        });

        mSocket.connect();

        return true;

    }


    public boolean loginOff(final Context context) {

        final UserInfoBean userInfoBean = UserInfoRepository.getInstance().currentUser(context);

        if (userInfoBean == null)
            return false;

        if (mSocket == null)
            return false;

        SocketIOUserInfo model = new SocketIOUserInfo();
        model.setSID(userInfoBean.getSID());
        model.setDeviceType("ANDROID");

        final Gson gson = new Gson();
        final String json = gson.toJson(model);

        mSocket.emit(LOGOFF, json);

        return true;
    }


    public boolean disconnect() {
        if (mSocket != null) {
            mSocket.disconnect();
            mSocket.off();
        }
        return true;
    }


    public void notifyOtherPlatforms(SocketIONotify notify) {
        if (mSocket != null && mSocket.connected()) {
            final Gson gson = new Gson();
            String json = gson.toJson(notify);
            mSocket.emit(EVENT_NOTIFYOTHERPLATFORMS, json);
        }
    }


    public void sendNews(SocketIOMessage msg) {
        if (mSocket != null && mSocket.connected()) {
            final Gson gson = new Gson();
            String json = gson.toJson(msg);
            mSocket.emit(EVENT_NEWS, json);
        }
    }


}
