package com.ufo.socketioandroiddemo;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import com.ufo.retrofitextend.RetrofitExtendFactory;
import com.ufo.socketioservice.DeviceToken;
import com.ufo.socketioservice.SocketIOService;
import com.ufo.tools.MyChat;

/**
 * Created by tjpld on 2017/5/5.
 */

public class MyApplication extends Application {

    private int appCount = 0;

    @Override
    public void onCreate() {
        super.onCreate();

        //生成deviceToken
        if (DeviceToken.getDeviceToken(this) == null) {
            DeviceToken.generateDeivceToken(this);
        }

        //注册baseUrl
        RetrofitExtendFactory.initWithBaseUrl("http://192.168.19.211:8080/NettySocketioWebDemo/");


        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                appCount++;
                getRecent();
                startSocketIOService();
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                appCount--;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    public int getAppCount() {
        return appCount;
    }

    private void startSocketIOService() {
        Intent intent = new Intent(getApplicationContext(), SocketIOService.class);
        startService(intent);
    }

    private void getRecent() {
        MyChat.getInstance().getRecent(getApplicationContext());
    }


}
