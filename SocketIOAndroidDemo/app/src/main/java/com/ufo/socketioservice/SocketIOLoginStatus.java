package com.ufo.socketioservice;

import android.content.Context;
import android.content.SharedPreferences;


import static android.content.Context.MODE_PRIVATE;

/**
 * Created by tjpld on 2017/6/5.
 */

public class SocketIOLoginStatus {

    public static boolean isNeedToCheck(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("loginStatus", MODE_PRIVATE);
        if (!preferences.getAll().isEmpty()) {
            return preferences.getBoolean("needToCheck", false);
        }
        return false;
    }

    public static void setNeedToCheck(Context context, boolean status) {
        SharedPreferences preferences = context.getSharedPreferences("loginStatus", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("needToCheck", status);
        editor.apply();
    }

}
