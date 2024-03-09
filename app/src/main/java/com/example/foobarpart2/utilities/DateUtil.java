package com.example.foobarpart2.utilities;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
    public static String toISO8601UTC(Date date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format.format(date);
    }
}
