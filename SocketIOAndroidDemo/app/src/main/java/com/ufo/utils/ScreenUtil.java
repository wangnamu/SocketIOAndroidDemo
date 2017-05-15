package com.ufo.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by tjpld on 2017/5/15.
 */

public class ScreenUtil {

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        return width;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int hegiht = dm.heightPixels;
        return hegiht;
    }

}
