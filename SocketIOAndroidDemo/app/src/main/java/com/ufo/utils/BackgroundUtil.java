package com.ufo.utils;



import android.content.Context;

import com.ufo.socketioandroiddemo.MyApplication;

/**
 * Created by tjpld on 2017/5/8.
 */

public class BackgroundUtil {

    public static boolean isForeground(Context context) {
        return getApplicationValue((MyApplication)context.getApplicationContext());
    }

    private static boolean getApplicationValue(MyApplication myApplication) {
        return myApplication.getAppCount() > 0;
    }

}

