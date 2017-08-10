package com.example.ichi.kenshu;


import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatterUtil {
    private static final String API_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";
    private static final String APP_DATE_FORMAT = "yyyy/MM/dd";

    public static String changeDateFormat(String purchaseDate) {
        Date date;
        String changedDate = null;
        try {
            date = new SimpleDateFormat(API_DATE_FORMAT, Locale.US).parse(purchaseDate);
            changedDate = new SimpleDateFormat(APP_DATE_FORMAT, Locale.JAPAN).format(date);
        } catch (ParseException e) {
            Log.d("error", "ParseException");
        }
        return changedDate;
    }
}
