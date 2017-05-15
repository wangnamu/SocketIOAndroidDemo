package com.ufo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tjpld on 2017/5/12.
 */

public class DateUtil {

    public static String dateToShort(long date) {

        long different = new Date().getTime() - date;

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;

        if (elapsedDays == 0) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("a hh:mm");
            return dateFormat.format(date);
        } else if (elapsedDays > 0 && elapsedDays <= 1) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("a hh:mm");
            return String.format("昨天 %s", dateFormat.format(date));
        } else if (elapsedDays > 1 && elapsedDays <= 7) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("E a hh:mm");
            return dateFormat.format(date);
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 a hh:mm");
            return dateFormat.format(date);
        }

    }

    public static boolean inTimeCurrent(long current, long last, int elapsed) {

        long different = current - last;

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;


        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        if (elapsedDays > 0) {
            return true;
        }

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        if (elapsedHours > 0) {
            return true;
        }

        long elapsedMinutes = different / minutesInMilli;

        if (elapsedMinutes >= elapsed) {
            return true;
        }

        return false;
    }

    public static Date dateFromString(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.parse(date);
    }

    public static String stringFromDate(Date date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }


    public static long longFromDate(Date date) {
        return date.getTime();
    }


    public static Date dateFromLong(long date) {
        return new Date(date);
    }


    public static String stringFromLong(long date) throws ParseException {
        return stringFromDate(dateFromLong(date));
    }

    public static long longFromString(String date) throws ParseException {
        return longFromDate(dateFromString(date));
    }


}
