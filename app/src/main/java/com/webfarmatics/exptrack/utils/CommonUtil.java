package com.webfarmatics.exptrack.utils;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CommonUtil {

    public static String getSharePreferenceString(Context context, String key,
                                                  String defaultValue) {
        SharedPreferences setting = PreferenceManager
                .getDefaultSharedPreferences(context);

        return setting.getString(key, defaultValue);
    }

    public static void setSharePreferenceString(Context context, String key,
                                                String value) {
        SharedPreferences setting = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = setting.edit();

        if (value.equals("")) {
            editor.remove(key);
        } else {
            editor.putString(key, value);
        }
        editor.commit();
    }

    public static String getTodaysDate(){

        Calendar calendar;
        int day, month, year, count1 = 0;
        String selected_date;
        boolean setDateStatus = false;
        String date = null;

        calendar = Calendar.getInstance();

        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);


        month = month + 1;
        Log.e("error", "showDateFirst   day " + day + " month " + month + " year " + year);
        date = day + "/" + month + "/" + year;
        return date;

    }

    public static ArrayList<String> getDMY(String selectedGridDate) {

        String[] dateItemArray = selectedGridDate.split("/");
//        String[] dateItemArray = selectedGridDate.split("-");
        String day = null, monthName = null, year;

//        String dd = dateItemArray[1];
//        String mm = dateItemArray[0];
//        String yy = dateItemArray[2];

        String dd = dateItemArray[0];
        String mm = dateItemArray[1];
        String yy = dateItemArray[2];

        Log.e("wwww", "dd" + dd);
        Log.e("wwww", "mm" + mm);
        Log.e("wwww", "yy" + yy);

        int month = Integer.parseInt(mm);

        int d = Integer.parseInt(dd);

        Log.e("wwww", "month" + month);
        Log.e("wwww", "dayy" + d);

        if (d < 10) {
            day = dd;
        }

        day = dd;

        if (month == 1) {
            monthName = "January";
        }
        if (month == 2) {
            monthName = "February";
        }
        if (month == 3) {
            monthName = "March";
        }
        if (month == 4) {
            monthName = "April";
        }
        if (month == 5) {
            monthName = "May";
        }
        if (month == 6) {
            monthName = "June";
        }
        if (month == 7) {
            monthName = "July";
        }
        if (month == 8) {
            monthName = "August";
        }
        if (month == 9) {
            monthName = "September";
        }
        if (month == 10) {
            monthName = "October";
        }
        if (month == 11) {
            monthName = "November";
        }
        if (month == 12) {
            monthName = "December";
        }

        year = yy;

        ArrayList<String> dmy1 = new ArrayList<>(3);
        dmy1.add(0, "" + year);
        dmy1.add(1, "" + monthName);
        dmy1.add(2, "" + day);

        return dmy1;

    }

    public static void setLocaleHindi(Context context) {
        Log.e("tag", "" + "  hi  ");

        Locale locale1 = new Locale("hi");
        Locale.setDefault(locale1);
        Configuration config = new Configuration();
        config.locale = locale1;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    public static void setLocaleEnglish(Context context) {
        Log.e("tag", "" + "  en  ");
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
    }


}
