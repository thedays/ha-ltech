package com.ltech.smarthome.utils;

import android.content.Context;
import android.content.SharedPreferences;

/* loaded from: classes4.dex */
public class LHomeSharedPreferencesUtil {
    public static final String APK_DOWNLOAD_URL = "APK_DOWNLOAD_URL";
    public static final String APK_VERSION = "APK_VERSION";
    private static final String PREFERENCE_NAME = "lhome_config";
    private static SharedPreferences sharedPreferences;

    public static void putBoolean(Context context, String key, boolean value) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, 0);
        }
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key, boolean value) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, 0);
        }
        return sharedPreferences.getBoolean(key, value);
    }

    public static void putString(Context context, String key, String value) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, 0);
        }
        sharedPreferences.edit().putString(key, value).commit();
    }

    public static String getString(Context context, String key, String defValue) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, 0);
        }
        return sharedPreferences.getString(key, defValue);
    }

    public static void putInt(Context context, String key, int value) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, 0);
        }
        sharedPreferences.edit().putInt(key, value).commit();
    }

    public static int getInt(Context context, String key, int defValue) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, 0);
        }
        return sharedPreferences.getInt(key, defValue);
    }

    public static void remove(Context context, String key) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, 0);
        }
        sharedPreferences.edit().remove(key).commit();
    }
}