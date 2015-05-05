package com.tgifreitag.billfold;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;

public class Dates{
    public static Date addDay(Date date, int i) {
        Log.d("DBAdapterBills", "addDay started " + date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, i);
        Log.d("DBAdapterBills", "addDay finished, returning " + cal.getTime() );
        return cal.getTime();
    }
    public static Date addMonth(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, i);
        return cal.getTime();
    }
    public static Date addYear(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, i);
        return cal.getTime();
    }

    public static Date weekStart(Date date,int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DAY_OF_YEAR, -(day_of_week - (1 + i)));
        Log.d("DBAdapterBills", "weekStart finished, returning " + cal.getTime());
        return cal.getTime();
    }

    public static Date weekEnd(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK);

        cal.add(Calendar.DAY_OF_YEAR, (7-day_of_week)+i);
        Log.d("DBAdapterBills", "weekStart finished, returning " + cal.getTime());
        return cal.getTime();
    }
    public static String thisMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day_of_month = (cal.get(Calendar.MONTH)+1);
        String doubleDigitMonth = String.valueOf(day_of_month);
        if (day_of_month < 10)
            doubleDigitMonth = ("0" + day_of_month);

        Log.d("DBAdapterBills", "Month Number = " + doubleDigitMonth);
        return doubleDigitMonth;
    }

}