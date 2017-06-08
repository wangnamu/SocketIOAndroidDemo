package com.ufo.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.ufo.socketioandroiddemo.R;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by tjpld on 2017/6/6.
 */

public class NotificationUtil {

    private static final int NOTIFICATIONS_ID = 100001;


    /**
     * 发送广播
     *
     * @param context
     * @param title
     * @param content
     * @param pendingIntent
     */
    public static void sendNotification(Context context, String title, String content, PendingIntent pendingIntent) {

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(content)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setDefaults(Notification.DEFAULT_ALL);

        notificationManager.notify(NOTIFICATIONS_ID, builder.build());
    }

    /**
     * 清除所有通知
     * @param context
     */
    public static void cancelAllNotification(Context context) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}
