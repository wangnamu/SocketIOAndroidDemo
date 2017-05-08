package com.ufo.socketioservice;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.UUID;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by tjpld on 2017/5/8.
 */

public class DeviceToken {

    public static String generateDeivceToken(Context context){

        SharedPreferences preferences = context.getSharedPreferences("deviceToken",MODE_PRIVATE);

        String tokenID = UUID.randomUUID().toString();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("tokenID",tokenID);
        editor.apply();

        return tokenID;
    }


    public static String getDeviceToken(Context context){

        SharedPreferences preferences = context.getSharedPreferences("deviceToken",MODE_PRIVATE);

        if(!preferences.getAll().isEmpty()) {
            return preferences.getString("tokenID",null);
        }

        return null;
    }

}
