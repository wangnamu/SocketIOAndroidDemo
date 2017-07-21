package com.ufo.socketioservice;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.ufo.socketioandroiddemo.MainActivity;
import com.ufo.socketioandroiddemo.login.LoginActivity;
import com.ufo.socketioandroiddemo.login.UserInfoRepository;
import com.ufo.tools.NotificationAction;
import com.ufo.utils.BackgroundUtil;
import com.ufo.utils.NotificationUtil;

/**
 * Created by tjpld on 2017/5/8.
 */

public class SocketIOService extends Service {

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(NotificationAction.SOCKETIO_KICKOFF)) {

                String msg = intent.getStringExtra("msg");

                UserInfoRepository.getInstance().logoff(getApplicationContext());
                SocketIOManager.getInstance().disconnect();

                if (BackgroundUtil.isForeground(getApplicationContext())) {
                    Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
                    intentLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intentLogin.putExtra("isKickedOff", true);
                    intentLogin.putExtra("msg", msg);
                    startActivity(intentLogin);
                } else {
                    PendingIntent pendingIntent = PendingIntent.getActivity(
                            context, 0, new Intent(context, LoginActivity.class), 0);
                    NotificationUtil.sendNotification(context, "提示", msg, pendingIntent);
                }

                stopSelf();
            }
        }

    };


    @Override
    public void onCreate() {
        super.onCreate();
        registerReceiver(mReceiver, new IntentFilter(NotificationAction.SOCKETIO_KICKOFF));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("onStartCommand", "onStartCommand");
        boolean checkStatus = true;
        if (intent != null) {
            checkStatus = intent.getBooleanExtra("CheckStatus", true);
        }
        SocketIOManager.getInstance().connect(getApplicationContext(), checkStatus);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        SocketIOManager.getInstance().disconnect();
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}
