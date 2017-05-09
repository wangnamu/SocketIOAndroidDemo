package com.ufo.socketioservice;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.ufo.socketioandroiddemo.login.UserInfoBean;
import com.ufo.socketioandroiddemo.login.UserInfoRepository;
import com.ufo.socketioservice.model.SocketIOUserInfo;
import com.ufo.utils.BackgroundUtil;

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
    private static final String mUrl = "http://192.168.19.211:3000";

    private static final String LOGIN = "login";
    private static final String EVENT_KICKOFF = "kickoff";
    private static final String EVENT_NEWS = "news";


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

    public Boolean connect(final Context context) {

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

                Log.d("SocketIOManager", "connecting");

                SocketIOUserInfo model = new SocketIOUserInfo();
                model.setSID(userInfoBean.getSID());
                model.setUserName(userInfoBean.getUserName());
                model.setNickName(userInfoBean.getNickName());

                model.setDeviceToken(deviceToken);
                model.setProject("SocketIODemo");
                model.setDeviceType("ANDROID");
                model.setLoginTime(System.currentTimeMillis());


                if (mSocket != null) {

                    final Gson gson = new Gson();
                    String json = gson.toJson(model);

                    mSocket.emit(LOGIN, json, new Ack() {
                        @Override
                        public void call(Object... args) {
                            if (args != null && args.length > 0
                                    && args[0].toString().equals("NO ACK")) {
                                Log.d("SocketIOManager", "reconnecting");
                                mSocket.connect();
                            } else {
                                Log.d("SocketIOManager", "connected");
                            }

                        }
                    });

                }

            }
        });


        mSocket.on(EVENT_NEWS, new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                Log.e("news", args + "");

                Ack ack = (Ack) args[args.length - 1];
                if (ack != null) {
                    ack.call("success");
                }

                Gson gson = new Gson();


                if (BackgroundUtil.isForeground(context)) {

                } else {

                }



                Log.e("onNews->", args[0].toString());

            }
        });


        mSocket.connect();

        return true;

//        NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
//        NSString *deviceToken = [userDefaults objectForKey:@"deviceToken"];
//
//        UserInfoBean *userInfoBean = [[UserInfoRepository sharedClient] currentUser];
//
//        if (deviceToken == nil || userInfoBean == nil) {
//            return NO;
//        }
//
//        if(socket != nil) {
//
//            if (socket.status == SocketIOClientStatusConnected || socket.status == SocketIOClientStatusConnecting) {
//                return NO;
//            }
//
//        [socket on:@"connect" callback:^(NSArray* data, SocketAckEmitter* ack) {
//
//                NSLog(@"socket connecting");
//
//                SocketIOUserInfo *model = [[SocketIOUserInfo alloc] init];
//                model.SID = userInfoBean.SID;
//                model.UserName = userInfoBean.UserName;
//                model.NickName = userInfoBean.NickName;
//
//                model.DeviceToken = deviceToken;
//                model.Project = @"SocketIODemo";
//                model.DeviceType = @"IOS";
//                model.LoginTime = [[NSDate date] timeIntervalSince1970] * 1000;
//
//                NSString* json = [model mj_JSONString];
//
//                if (socket != nil) {
//                [[socket emitWithAck:@"login" with:@[json]] timingOutAfter:30 callback:^(NSArray* args) {
//                        NSLog(@"socket connected");
//                        NSLog(@"login->%@",args);
//                        if (args != nil && args.count > 0 && [[args firstObject] isEqualToString:@"NO ACK"]) {
//                        [socket reconnect];
//                        }
//                    }];
//                }
//
//            }];
//
//        [socket on:@"disconnect" callback:^(NSArray* data, SocketAckEmitter* ack) {
//                NSLog(@"disconnect---%@",data);
//            }];
//
//        [socket on:@"reconnect" callback:^(NSArray* data, SocketAckEmitter* ack) {
//                NSLog(@"reconnect---%@",data);
//            }];
//
//
//        [socket on:@"kickoff" callback:^(NSArray* data, SocketAckEmitter* ack) {
//                NSLog(@"kickoff---%@",data);
//            [[NSNotificationCenter defaultCenter] postNotificationName:Notification_Socketio_Kickoff object:nil];
//            }];
//
//        [socket on:@"notifyotherplatforms" callback:^(NSArray* data, SocketAckEmitter* ack) {
//                NSLog(@"notifyotherplatforms---%@",data);
//            [[NSNotificationCenter defaultCenter] postNotificationName:Notification_Socketio_Notifyotherplatforms object:[data objectAtIndex:0]];
//            }];
//
//        [socket on:@"news" callback:^(NSArray* data, SocketAckEmitter* ack) {
//                NSLog(@"news---%@",data);
//                if (ack) {
//                [ack with:@[@"success"]];
//                }
//
//                NSDictionary *dic = [[data objectAtIndex:0] mj_JSONObject];
//                SocketIOMessage *msg = [SocketIOMessage mj_objectWithKeyValues:dic];
//
//                if ([msg.OthersType isEqualToString:OthersTypeMessage]) {
//
//                [ChatMessageModel mj_setupReplacedKeyFromPropertyName:^NSDictionary *{
//                    return @{
//                        @"SID" : @"SID",
//                        @"SenderID" : @"SenderID",
//                        @"Title" : @"Title",
//                        @"Body" : @"Body",
//                        @"Time" : @"Time",
//                        @"MessageType" : @"MessageType",
//                        @"NickName" : @"NickName",
//                        @"HeadPortrait" : @"HeadPortrait",
//                        @"ChatID" : @"ChatID",
//                        @"Thumbnail" : @"Thumbnail",
//                        @"Original" : @"Original",
//                    };
//                }];
//
//
//                    ChatMessageModel *chatMessageModel = [ChatMessageModel mj_objectWithKeyValues:msg.Others];
//
//                [[MyChat sharedClient] receiveChatMessage:chatMessageModel];
//                }
//
//            }];
//
//
//
//        [socket connect];

    }


    public boolean disconnect() {
        if (mSocket != null) {
            mSocket.disconnect();
            mSocket.off();
        }
        return true;
    }


//        - (BOOL)disconnect {
//            if(socket != nil) {
//        [socket disconnect];
//        [socket removeAllHandlers];
//            }
//            return YES;
//        }
//
//        - (void)notifyOtherPlatforms:(SocketIONotify*)notify {
//            if(socket != nil && socket.status == SocketIOClientStatusConnected) {
//                NSString* json = [notify mj_JSONString];
//        [socket emit:@"notifyotherplatforms" with:@[json]];
//            }
//        }
//
//        - (void)sendNews:(SocketIOMessage*)msg {
//            if(socket != nil && socket.status == SocketIOClientStatusConnected) {
//                NSString* json = [msg mj_JSONString];
//        [socket emit:@"news" with:@[json]];
//            }
//        }


}
