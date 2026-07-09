package com.ltech.smarthome.utils;

/* loaded from: classes4.dex */
public class GPSUtil {

    /* renamed from: a, reason: collision with root package name */
    public static double f6278a = 6378245.0d;
    public static double ee = 0.006693421622965943d;
    public static double pi = 3.141592653589793d;
    public static double x_pi = 52.35987755982988d;

    public static boolean outOfChina(double lat, double lon) {
        return lon < 72.004d || lon > 137.8347d || lat < 0.8293d || lat > 55.8271d;
    }

    public static double transformLat(double x, double y) {
        double d2 = x * 2.0d;
        return (-100.0d) + d2 + (y * 3.0d) + (y * 0.2d * y) + (0.1d * x * y) + (Math.sqrt(Math.abs(x)) * 0.2d) + ((((Math.sin((x * 6.0d) * pi) * 20.0d) + (Math.sin(d2 * pi) * 20.0d)) * 2.0d) / 3.0d) + ((((Math.sin(pi * y) * 20.0d) + (Math.sin((y / 3.0d) * pi) * 40.0d)) * 2.0d) / 3.0d) + ((((Math.sin((y / 12.0d) * pi) * 160.0d) + (Math.sin((y * pi) / 30.0d) * 320.0d)) * 2.0d) / 3.0d);
    }

    public static double transformLon(double x, double y) {
        double d2 = x * 0.1d;
        return x + 300.0d + (y * 2.0d) + (d2 * x) + (d2 * y) + (Math.sqrt(Math.abs(x)) * 0.1d) + ((((Math.sin((6.0d * x) * pi) * 20.0d) + (Math.sin((x * 2.0d) * pi) * 20.0d)) * 2.0d) / 3.0d) + ((((Math.sin(pi * x) * 20.0d) + (Math.sin((x / 3.0d) * pi) * 40.0d)) * 2.0d) / 3.0d) + ((((Math.sin((x / 12.0d) * pi) * 150.0d) + (Math.sin((x / 30.0d) * pi) * 300.0d)) * 2.0d) / 3.0d);
    }

    public static double[] transform(double lat, double lon) {
        if (outOfChina(lat, lon)) {
            return new double[]{lat, lon};
        }
        double d2 = lon - 105.0d;
        double d3 = lat - 35.0d;
        double transformLat = transformLat(d2, d3);
        double transformLon = transformLon(d2, d3);
        double d4 = (lat / 180.0d) * pi;
        double sin = Math.sin(d4);
        double d5 = 1.0d - ((ee * sin) * sin);
        double sqrt = Math.sqrt(d5);
        double d6 = f6278a;
        return new double[]{lat + ((transformLat * 180.0d) / ((((1.0d - ee) * d6) / (d5 * sqrt)) * pi)), lon + ((transformLon * 180.0d) / (((d6 / sqrt) * Math.cos(d4)) * pi))};
    }

    public static double[] gps84_To_Gcj02(double lat, double lon) {
        if (outOfChina(lat, lon)) {
            return new double[]{lat, lon};
        }
        double d2 = lon - 105.0d;
        double d3 = lat - 35.0d;
        double transformLat = transformLat(d2, d3);
        double transformLon = transformLon(d2, d3);
        double d4 = (lat / 180.0d) * pi;
        double sin = Math.sin(d4);
        double d5 = 1.0d - ((ee * sin) * sin);
        double sqrt = Math.sqrt(d5);
        double d6 = f6278a;
        return new double[]{lat + ((transformLat * 180.0d) / ((((1.0d - ee) * d6) / (d5 * sqrt)) * pi)), lon + ((transformLon * 180.0d) / (((d6 / sqrt) * Math.cos(d4)) * pi))};
    }

    public static double[] gcj02_To_Gps84(double lat, double lon) {
        double[] transform = transform(lat, lon);
        return new double[]{(lat * 2.0d) - transform[0], (lon * 2.0d) - transform[1]};
    }

    public static double[] gcj02_To_Bd09(double lat, double lon) {
        double sqrt = Math.sqrt((lon * lon) + (lat * lat)) + (Math.sin(x_pi * lat) * 2.0E-5d);
        double atan2 = Math.atan2(lat, lon) + (Math.cos(lon * x_pi) * 3.0E-6d);
        return new double[]{(sqrt * Math.sin(atan2)) + 0.006d, (Math.cos(atan2) * sqrt) + 0.0065d};
    }

    public static double[] bd09_To_Gcj02(double lat, double lon) {
        double d2 = lon - 0.0065d;
        double d3 = lat - 0.006d;
        double sqrt = Math.sqrt((d2 * d2) + (d3 * d3)) - (Math.sin(x_pi * d3) * 2.0E-5d);
        double atan2 = Math.atan2(d3, d2) - (Math.cos(d2 * x_pi) * 3.0E-6d);
        return new double[]{sqrt * Math.sin(atan2), Math.cos(atan2) * sqrt};
    }

    public static double[] gps84_To_bd09(double lat, double lon) {
        double[] gps84_To_Gcj02 = gps84_To_Gcj02(lat, lon);
        return gcj02_To_Bd09(gps84_To_Gcj02[0], gps84_To_Gcj02[1]);
    }

    public static double[] bd09_To_gps84(double lat, double lon) {
        double[] bd09_To_Gcj02 = bd09_To_Gcj02(lat, lon);
        double[] gcj02_To_Gps84 = gcj02_To_Gps84(bd09_To_Gcj02[0], bd09_To_Gcj02[1]);
        gcj02_To_Gps84[0] = retain6(gcj02_To_Gps84[0]);
        gcj02_To_Gps84[1] = retain6(gcj02_To_Gps84[1]);
        return gcj02_To_Gps84;
    }

    private static double retain6(double num) {
        return Double.valueOf(String.format("%.6f", Double.valueOf(num))).doubleValue();
    }
}