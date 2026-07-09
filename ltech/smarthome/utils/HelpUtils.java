package com.ltech.smarthome.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Vibrator;
import android.text.TextUtils;
import anetwork.channel.util.RequestConstant;
import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.ltech.smarthome.R;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

/* loaded from: classes4.dex */
public class HelpUtils {
    public static void threadSleep(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int[] getDrawableResourceArray(Context context, int id) {
        TypedArray obtainTypedArray = context.getResources().obtainTypedArray(id);
        int length = obtainTypedArray.length();
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            iArr[i] = obtainTypedArray.getResourceId(i, 0);
        }
        obtainTypedArray.recycle();
        return iArr;
    }

    public static void vibrate(long time) {
        ((Vibrator) com.blankj.utilcode.util.Utils.getApp().getSystemService("vibrator")).vibrate(time);
    }

    public static String getGmtTimeZone() {
        return TimeZone.getDefault().getDisplayName(false, 0);
    }

    public static String getTimeZoneId() {
        return TimeZone.getDefault().getID();
    }

    public static String getTimeString(int hour, int min) {
        Object valueOf;
        Object valueOf2;
        StringBuilder sb = new StringBuilder();
        if (hour < 10) {
            valueOf = "0" + hour;
        } else {
            valueOf = Integer.valueOf(hour);
        }
        sb.append(valueOf);
        sb.append(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR);
        if (min < 10) {
            valueOf2 = "0" + min;
        } else {
            valueOf2 = Integer.valueOf(min);
        }
        sb.append(valueOf2);
        return sb.toString();
    }

    public static String getWeeksString(Context context, String weeks) {
        if (TextUtils.isEmpty(weeks)) {
            return context.getString(R.string.only_once);
        }
        String[] split = weeks.split(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP);
        if (split.length == 7) {
            return context.getString(R.string.every_day);
        }
        if (split.length == 2 && split[0].equals("1") && split[1].equals("7")) {
            return context.getString(R.string.weekend);
        }
        if (split.length == 5 && split[0].equals("2") && split[1].equals("3") && split[2].equals("4") && split[3].equals("5") && split[4].equals("6")) {
            return context.getString(R.string.workday);
        }
        StringBuilder sb = new StringBuilder();
        for (String str : split) {
            str.hashCode();
            switch (str) {
                case "1":
                    sb.append(context.getString(R.string.sun));
                    sb.append(".");
                    break;
                case "2":
                    sb.append(context.getString(R.string.mon));
                    sb.append(".");
                    break;
                case "3":
                    sb.append(context.getString(R.string.tue));
                    sb.append(".");
                    break;
                case "4":
                    sb.append(context.getString(R.string.wed));
                    sb.append(".");
                    break;
                case "5":
                    sb.append(context.getString(R.string.thur));
                    sb.append(".");
                    break;
                case "6":
                    sb.append(context.getString(R.string.fri));
                    sb.append(".");
                    break;
                case "7":
                    sb.append(context.getString(R.string.sat));
                    sb.append(".");
                    break;
            }
        }
        if (sb.length() != 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public static String getWeeksStringNew(Context context, String weeks) {
        if (TextUtils.isEmpty(weeks)) {
            return context.getString(R.string.only_once);
        }
        String[] split = weeks.split(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP);
        if (split.length == 7) {
            return context.getString(R.string.every_day);
        }
        if (split.length == 2) {
            if (split[0].equals("7") && split[1].equals("6")) {
                return context.getString(R.string.weekend);
            }
            if (split[0].equals("6") && split[1].equals("7")) {
                return context.getString(R.string.weekend);
            }
        }
        if (split.length == 5 && split[0].equals("1") && split[1].equals("2") && split[2].equals("3") && split[3].equals("4") && split[4].equals("5")) {
            return context.getString(R.string.workday);
        }
        StringBuilder sb = new StringBuilder();
        for (String str : split) {
            str.hashCode();
            switch (str) {
                case "1":
                    sb.append(context.getString(R.string.mon));
                    sb.append(".");
                    break;
                case "2":
                    sb.append(context.getString(R.string.tue));
                    sb.append(".");
                    break;
                case "3":
                    sb.append(context.getString(R.string.wed));
                    sb.append(".");
                    break;
                case "4":
                    sb.append(context.getString(R.string.thur));
                    sb.append(".");
                    break;
                case "5":
                    sb.append(context.getString(R.string.fri));
                    sb.append(".");
                    break;
                case "6":
                    sb.append(context.getString(R.string.sat));
                    sb.append(".");
                    break;
                case "7":
                    sb.append(context.getString(R.string.sun));
                    sb.append(".");
                    break;
            }
        }
        if (sb.length() != 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public static synchronized boolean compareObject(Object obj1, Object obj2) throws Exception {
        synchronized (HelpUtils.class) {
            if (obj1 != null && obj2 != null) {
                if (obj1.getClass() != null && obj2.getClass() != null) {
                    HashMap hashMap = new HashMap();
                    if (obj1.getClass() == obj2.getClass()) {
                        for (Field field : obj1.getClass().getDeclaredFields()) {
                            field.setAccessible(true);
                            hashMap.put(field.getName(), String.valueOf(equals(field.get(obj1), field.get(obj2))));
                        }
                    }
                    Iterator it = hashMap.values().iterator();
                    while (it.hasNext()) {
                        if (RequestConstant.FALSE.equals((String) it.next())) {
                            return false;
                        }
                    }
                    return true;
                }
            }
            return false;
        }
    }

    private static boolean equals(Object obj1, Object obj2) {
        return GsonUtils.toJson(obj1).equals(GsonUtils.toJson(obj2));
    }

    public static Object removeObjectKey(Object object, String key) {
        JsonElement jsonTree = new Gson().newBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create().toJsonTree(object);
        jsonTree.getAsJsonObject().remove(key);
        jsonTree.getAsJsonObject();
        return jsonTree;
    }

    public static Object removeObjectKey(Object object, List<String> keyList) {
        JsonElement jsonTree = new Gson().newBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create().toJsonTree(object);
        Iterator<String> it = keyList.iterator();
        while (it.hasNext()) {
            jsonTree.getAsJsonObject().remove(it.next());
        }
        jsonTree.getAsJsonObject();
        return jsonTree;
    }
}