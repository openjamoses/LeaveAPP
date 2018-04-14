package com.example.john.leaveapp.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by john on 7/9/17.
 */

public class DateTime {
    public static String getCurrentDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(c.getTime());
        return strDate;
    }
    public static String getYear(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String strDate = sdf.format(c.getTime());
        return strDate;
    }
    public static String getCurrentTime(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String strTime = sdf.format(c.getTime());
        return strTime;
    }
    public static String addDays(String date, int days){
        String dt_return = date;  // Start date
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(date));
            c.add(Calendar.DATE, days);  // number of days to add
            dt_return = sdf.format(c.getTime());  // dt is now the new date
        }catch (Exception e){
            e.printStackTrace();
        }

        return dt_return;
    }
}
