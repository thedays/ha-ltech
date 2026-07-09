package com.ltech.smarthome.ui.mode;

import com.sun.jna.Function;
import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes4.dex */
public class AdvancedModeHelper {
    public static int calculateTotalTime(int hourPosition, int minPosition, int secPosition, int msPosition) {
        return (((hourPosition * 3600) + (minPosition * 60) + secPosition) * 1000) + (msPosition * 40);
    }

    public static int getHourPos(int time) {
        return ((time / 1000) / 3600) % 60;
    }

    public static int getMinPos(int time) {
        return ((time / 1000) / 60) % 60;
    }

    public static int getSecPos(int time) {
        return (time / 1000) % 60;
    }

    public static int getMsPos(int time) {
        return (time % 1000) / 40;
    }

    public static String getTimeString(int time) {
        Object obj;
        Object obj2;
        Object obj3;
        Object obj4;
        int i = time / 1000;
        int i2 = (i / 3600) % 60;
        int i3 = (i / 60) % 60;
        int i4 = i % 60;
        int i5 = time % 1000;
        StringBuilder sb = new StringBuilder();
        if (i2 >= 10) {
            obj = Integer.valueOf(i2);
        } else {
            obj = "0" + i2;
        }
        sb.append(obj);
        sb.append(Constants.COLON_SEPARATOR);
        if (i3 < 10) {
            obj2 = "0" + i3;
        } else {
            obj2 = Integer.valueOf(i3);
        }
        sb.append(obj2);
        sb.append(Constants.COLON_SEPARATOR);
        if (i4 < 10) {
            obj3 = "0" + i4;
        } else {
            obj3 = Integer.valueOf(i4);
        }
        sb.append(obj3);
        sb.append(Constants.COLON_SEPARATOR);
        if (i5 >= 100) {
            obj4 = Integer.valueOf(i5);
        } else if (i5 == 0) {
            obj4 = "000";
        } else {
            obj4 = "0" + i5;
        }
        sb.append(obj4);
        return sb.toString();
    }

    public static String getTotalTimeString(int total) {
        Object obj;
        Object obj2;
        Object obj3;
        Object obj4;
        int i = total / 1000;
        int i2 = (i / 3600) % Function.USE_VARARGS;
        int i3 = (i / 60) % 60;
        int i4 = i % 60;
        int i5 = total % 1000;
        StringBuilder sb = new StringBuilder();
        if (i2 >= 10) {
            obj = Integer.valueOf(i2);
        } else {
            obj = "0" + i2;
        }
        sb.append(obj);
        sb.append(Constants.COLON_SEPARATOR);
        if (i3 < 10) {
            obj2 = "0" + i3;
        } else {
            obj2 = Integer.valueOf(i3);
        }
        sb.append(obj2);
        sb.append(Constants.COLON_SEPARATOR);
        if (i4 < 10) {
            obj3 = "0" + i4;
        } else {
            obj3 = Integer.valueOf(i4);
        }
        sb.append(obj3);
        sb.append(".");
        if (i5 >= 100) {
            obj4 = Integer.valueOf(i5);
        } else if (i5 == 0) {
            obj4 = "000";
        } else {
            obj4 = "0" + i5;
        }
        sb.append(obj4);
        return sb.toString();
    }
}