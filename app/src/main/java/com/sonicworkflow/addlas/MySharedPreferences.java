package com.sonicworkflow.addlas;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by umarbradshaw on 9/25/15.
 */
public class MySharedPreferences {

    public static final String PREF_NAME = "devicePref";
    public static final int MODE = Context.MODE_PRIVATE;

    public static final String DEVICE_NAME = "DEVICE_NAME";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String TIME_INTERVAL = "TIME_INTERVAL";
    public static final String DELAY_TIME = "DELAY_TIME";
    public static final String AUTO_ACCEPT_CHANNEL = "AUTO_ACCEPT_CHANNEL";
    public static final String BACKUP_CHANNEL = "BACKUP_CHANNEL";







    public static void writeBoolean(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).commit();
    }

    public static boolean readBoolean(Context context, String key, boolean defValue) {
        return getPreferences(context).getBoolean(key, defValue);
    }


    public static void writeInteger(Context context, String key, int value) {
        getEditor(context).putInt(key, value).commit();

    }

    public static int readInteger(Context context, String key, int defValue) {
        return getPreferences(context).getInt(key, defValue);
    }

    public static void writeString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();

    }

    public static String readString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }


    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE);
    }

    public static Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }





}
