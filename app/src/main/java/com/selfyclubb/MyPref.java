package com.selfyclubb;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.Set;

/**
 * Created by JitenRamen on 27-08-2016.
 */
public class MyPref {

    public static String IS_TUTORIAL_DONE = "IS_TUTORIAL_DONE";

    public static void setPref(Context mContext, String key, String value) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(mContext).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void setPref(Context mContext, String key, Boolean value) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(mContext).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void setPref(Context mContext, String key, Float value) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(mContext).edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public static void setPref(Context mContext, String key, int value) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(mContext).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void setPref(Context mContext, String key, long value) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(mContext).edit();
        editor.putLong(key, value);
        editor.commit();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void setPref(Context mContext, String key,
                               Set<String> valueSet) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(mContext).edit();
        editor.putStringSet(key, valueSet);
        editor.commit();
    }

    public static String getPref(Context mContext, String key, String defValue) {
        return PreferenceManager.getDefaultSharedPreferences(mContext)
                .getString(key, defValue);
    }

    public static boolean getPref(Context mContext, String key, boolean defValue) {
        return PreferenceManager.getDefaultSharedPreferences(mContext)
                .getBoolean(key, defValue);
    }

    public static float getPref(Context mContext, String key, float defValue) {
        return PreferenceManager.getDefaultSharedPreferences(mContext)
                .getFloat(key, defValue);
    }

    public static int getPref(Context mContext, String key, int defValue) {
        return PreferenceManager.getDefaultSharedPreferences(mContext).getInt(
                key, defValue);
    }

    public static long getPref(Context mContext, String key, long defValue) {
        return PreferenceManager.getDefaultSharedPreferences(mContext).getLong(
                key, defValue);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static Set<String> getPref(Context mContext, String key,
                                      Set<String> defValueSet) {
        return PreferenceManager.getDefaultSharedPreferences(mContext)
                .getStringSet(key, defValueSet);
    }
}

