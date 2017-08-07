package com.example.ichi.kenshu;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatterUtil {

    public static String changeDateFormat(String purchaseDate) {
        Date date;
        String changedDate = null;
        try {
            date = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US).parse(purchaseDate);
            changedDate = new SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return changedDate;
    }
}
