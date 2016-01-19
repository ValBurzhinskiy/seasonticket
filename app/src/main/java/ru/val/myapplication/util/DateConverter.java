package ru.val.myapplication.util;


import android.content.Context;

import java.util.Calendar;

import ru.val.myapplication.R;

public class DateConverter {
    public static String dateToString(Context context, Calendar calendar){
        return String.format(context.getResources().getString(R.string.string_format_date), calendar.get(Calendar.DAY_OF_MONTH), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.YEAR));
    }

    public static  String dateToString(Context context, int dayOfMonth, int monthOfYear, int year){
        return String.format(context.getResources().getString(R.string.string_format_date), dayOfMonth, (monthOfYear + 1), year);
    }

    public static Calendar stringToDate (String date) {
        String str[] = date.split("\\W{1}");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(str[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(str[1]));
        calendar.set(Calendar.YEAR, Integer.parseInt(str[2]));
        return calendar;
    }
}
