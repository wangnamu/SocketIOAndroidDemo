package com.ufo.socketioservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.ufo.utils.BackgroundUtil;

/**
 * Created by tjpld on 2017/5/8.
 */

public class SocketIOService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.e("onStartCommand","onStartCommand");


        SocketIOManager.getInstance().connect(this);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int i = 0;
//
//                while (i < 10000) {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    Boolean flag = BackgroundUtil.isForeground(getApplicationContext());
//
//                    Log.e("isForeground", flag + "");
//
//                    i++;
//
//                }
//
//            }
//        }).start();

        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        SocketIOManager.getInstance().disconnect();
        super.onDestroy();
    }
}
