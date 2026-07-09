package com.ltech.smarthome.utils;

import android.graphics.Point;

/* loaded from: classes4.dex */
public class ExtUtils {
    public static int getLength(float x1, float y1, float x2, float y2) {
        return (int) Math.sqrt(Math.pow(x1 - x2, 2.0d) + Math.pow(y1 - y2, 2.0d));
    }

    public static Point getBorderPoint(Point a2, Point b2, int cutRadius) {
        double d2 = cutRadius;
        double radian = getRadian(a2, b2);
        return new Point(a2.x + ((int) (Math.cos(radian) * d2)), a2.y + ((int) (d2 * Math.sin(radian))));
    }

    public static float getRadian(Point a2, Point b2) {
        float f = b2.x - a2.x;
        float f2 = b2.y - a2.y;
        return ((float) Math.acos(f / ((float) Math.sqrt((f * f) + (f2 * f2))))) * (b2.y < a2.y ? -1 : 1);
    }
}