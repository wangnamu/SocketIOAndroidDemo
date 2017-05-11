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

        SocketIOManager.getInstance().connect(getApplicationContext());

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
