package com.ufo.socketioandroiddemo;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.ufo.retrofitextend.RetrofitExtendFactory;
import com.ufo.socketioservice.DeviceToken;

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
        RetrofitExtendFactory.initWithBaseUrl("http://192.168.19.96:8080/NettySocketioWebDemo/");


        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                appCount++;
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


}
