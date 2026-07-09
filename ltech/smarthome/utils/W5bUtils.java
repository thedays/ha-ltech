package com.ltech.smarthome.utils;

/* loaded from: classes4.dex */
public class W5bUtils {
    public static final int MAX_K = 20000;

    public static int[] xyToRgb(byte level, float x, float y) {
        float f = level & 255;
        float f2 = f / y;
        float f3 = (x * f2) / 100.0f;
        float f4 = f / 100.0f;
        float f5 = (((1.0f - x) - y) * f2) / 100.0f;
        float f6 = ((3.2406f * f3) - (1.5372f * f4)) - (0.4986f * f5);
        float f7 = (-(0.9689f * f3)) + (1.8758f * f4) + (0.0415f * f5);
        float f8 = ((f3 * 0.0557f) - (f4 * 0.204f)) + (f5 * 1.057f);
        return new int[]{(int) (clamp(f6 <= 0.0031308f ? f6 * 12.92f : ((float) (Math.pow(f6, 0.4166666666666667d) * 1.055d)) - 0.055f, 0.0f, 1.0f) * 255.0f), (int) (clamp(f7 <= 0.0031308f ? f7 * 12.92f : ((float) (Math.pow(f7, 0.4166666666666667d) * 1.055d)) - 0.055f, 0.0f, 1.0f) * 255.0f), (int) (clamp(f8 <= 0.0031308f ? f8 * 12.92f : ((float) (Math.pow(f8, 0.4166666666666667d) * 1.055d)) - 0.055f, 0.0f, 1.0f) * 255.0f)};
    }

    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(value, max));
    }

    public static int[] xyToRgb2(float x, float y) {
        return xyToRgb2(x, y, 255);
    }

    public static int[] xyToRgb2(float x, float y, int bri) {
        return ColorSpace.sRGB.colorFromXYY(x, y, (ColorSpace.sRGB.findMaximumY(x, y) * bri) / 255.0f).clamped();
    }

    public static int[] hslToRgb(float h, float s, float l) {
        float abs = (1.0f - Math.abs((l * 2.0f) - 1.0f)) * s;
        float abs2 = (1.0f - Math.abs(((h / 60.0f) % 2.0f) - 1.0f)) * abs;
        float f = l - (0.5f * abs);
        float f2 = 0.0f;
        if (h < 60.0f) {
            f2 = abs2;
        } else {
            if (h >= 120.0f) {
                if (h < 180.0f) {
                    f2 = abs;
                } else {
                    if (h >= 240.0f) {
                        if (h < 300.0f) {
                            abs2 = abs;
                            abs = abs2;
                        }
                        return new int[]{(int) ((abs + f) * 255.0f), (int) ((f2 + f) * 255.0f), (int) ((abs2 + f) * 255.0f)};
                    }
                    f2 = abs2;
                    abs2 = abs;
                }
                abs = 0.0f;
                return new int[]{(int) ((abs + f) * 255.0f), (int) ((f2 + f) * 255.0f), (int) ((abs2 + f) * 255.0f)};
            }
            f2 = abs;
            abs = abs2;
        }
        abs2 = 0.0f;
        return new int[]{(int) ((abs + f) * 255.0f), (int) ((f2 + f) * 255.0f), (int) ((abs2 + f) * 255.0f)};
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0067  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static float[] rgbToHsl(int r9, int r10, int r11) {
        /*
            float r9 = (float) r9
            r0 = 1132396544(0x437f0000, float:255.0)
            float r9 = r9 / r0
            int r9 = (int) r9
            float r10 = (float) r10
            float r10 = r10 / r0
            int r10 = (int) r10
            float r11 = (float) r11
            float r11 = r11 / r0
            int r11 = (int) r11
            int r0 = java.lang.Math.max(r10, r11)
            int r0 = java.lang.Math.max(r9, r0)
            float r0 = (float) r0
            int r1 = java.lang.Math.min(r10, r11)
            int r1 = java.lang.Math.min(r9, r1)
            float r1 = (float) r1
            float r2 = r0 - r1
            float r3 = r0 + r1
            r4 = 1073741824(0x40000000, float:2.0)
            float r5 = r3 / r4
            r6 = 1135869952(0x43b40000, float:360.0)
            r7 = 0
            int r8 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
            if (r8 == 0) goto L69
            r8 = 1056964608(0x3f000000, float:0.5)
            int r8 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1))
            if (r8 >= 0) goto L33
            goto L36
        L33:
            float r3 = r4 - r0
            float r3 = r3 - r1
        L36:
            float r1 = r2 / r3
            float r3 = (float) r9
            int r3 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r3 != 0) goto L48
            int r9 = r10 - r11
            float r9 = (float) r9
            float r9 = r9 / r2
            if (r10 >= r11) goto L46
            r10 = 1086324736(0x40c00000, float:6.0)
            goto L5c
        L46:
            r10 = 0
            goto L5c
        L48:
            float r3 = (float) r10
            int r3 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r3 != 0) goto L52
            int r11 = r11 - r9
            float r9 = (float) r11
            float r9 = r9 / r2
            float r9 = r9 + r4
            goto L5f
        L52:
            float r11 = (float) r11
            int r11 = (r0 > r11 ? 1 : (r0 == r11 ? 0 : -1))
            if (r11 != 0) goto L5e
            int r9 = r9 - r10
            float r9 = (float) r9
            float r9 = r9 / r2
            r10 = 1082130432(0x40800000, float:4.0)
        L5c:
            float r9 = r9 + r10
            goto L5f
        L5e:
            r9 = 0
        L5f:
            r10 = 1114636288(0x42700000, float:60.0)
            float r9 = r9 * r10
            int r10 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
            if (r10 >= 0) goto L6b
            float r9 = r9 + r6
            goto L6b
        L69:
            r9 = 0
            r1 = 0
        L6b:
            float r9 = r9 % r6
            r10 = 1065353216(0x3f800000, float:1.0)
            float r11 = java.lang.Math.min(r10, r1)
            float r11 = java.lang.Math.max(r7, r11)
            float r10 = java.lang.Math.min(r10, r5)
            float r10 = java.lang.Math.max(r7, r10)
            r0 = 3
            float[] r0 = new float[r0]
            r1 = 0
            r0[r1] = r9
            r9 = 1
            r0[r9] = r11
            r9 = 2
            r0[r9] = r10
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.utils.W5bUtils.rgbToHsl(int, int, int):float[]");
    }

    public static float convertXtoY(float x) {
        double d2 = x;
        return (float) ((((((Math.pow(d2, 5.0d) * 24.52d) - (Math.pow(d2, 4.0d) * 59.04d)) + (Math.pow(d2, 3.0d) * 54.84d)) - (Math.pow(d2, 2.0d) * 27.26d)) + (d2 * 7.988d)) - 0.6952d);
    }

    public static int convertXtoK(float x) {
        double d2;
        double d3;
        double d4;
        double d5;
        double d6;
        if (x < 0.280421d) {
            d2 = 0.00389448372d;
            d3 = 10.9716698d;
            d4 = 1.19741961E-24d;
            d5 = 46.2509348d;
            d6 = 5543.64447d;
        } else {
            d2 = 2154.29907d;
            d3 = 1.1047656d;
            d4 = 0.00633126931d;
            d5 = 10.4273795d;
            d6 = -2402.27824d;
        }
        return (int) clamp((int) ((d2 * Math.pow(r0, -d3)) + (d4 * Math.pow(r0, -d5)) + d6), 1000.0f, 20000.0f);
    }

    public static int[] convertKForCW(double kelvin, int bri) {
        int i;
        double d2;
        double pow;
        double d3 = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
        if (kelvin < 1000.0d || kelvin > 2600.0d) {
            if (kelvin > 2600.0d && kelvin <= 5400.0d) {
                double d4 = 0.2655d * kelvin;
                pow = (((Math.pow(kelvin, 3.0d) * 2.083E-9d) - (Math.pow(kelvin, 2.0d) * 3.519E-5d)) + d4) - 484.7d;
                d3 = (((Math.pow(kelvin, 3.0d) * (-2.083E-9d)) + (Math.pow(kelvin, 2.0d) * 3.519E-5d)) - d4) + 739.7d;
            } else if (kelvin > 5400.0d && kelvin <= 10000.0d) {
                pow = (((Math.pow(kelvin, 3.0d) * (-4.225E-10d)) + (Math.pow(kelvin, 2.0d) * 1.218E-5d)) - (kelvin * 0.1241d)) + 640.1d;
            } else {
                if (kelvin <= 10000.0d || kelvin > 100000.0d) {
                    i = bri;
                    d2 = 0.0d;
                } else {
                    i = bri;
                    d2 = 0.0d;
                    d3 = ((((((Math.pow(kelvin, 6.0d) * 2.382E-27d) - (Math.pow(kelvin, 5.0d) * 9.1E-22d)) + (Math.pow(kelvin, 4.0d) * 1.391E-16d)) - (Math.pow(kelvin, 3.0d) * 1.09E-11d)) + (Math.pow(kelvin, 2.0d) * 4.656E-7d)) - (kelvin * 0.01059d)) + 262.6d;
                }
                double d5 = i / 255.0f;
                return new int[]{(int) clamp((float) (d3 * d5), 0.0f, 255.0f), (int) clamp((float) (d2 * d5), 0.0f, 255.0f)};
            }
            double d6 = d3;
            d3 = pow;
            d2 = d6;
        } else {
            d2 = (((Math.pow(kelvin, 3.0d) * (-1.866E-8d)) + (Math.pow(kelvin, 2.0d) * 8.426E-5d)) + (kelvin * 0.03156d)) - 67.14d;
        }
        i = bri;
        double d52 = i / 255.0f;
        return new int[]{(int) clamp((float) (d3 * d52), 0.0f, 255.0f), (int) clamp((float) (d2 * d52), 0.0f, 255.0f)};
    }

    private static double[] getUvFromXy(double x, double y) {
        double d2 = ((12.0d * y) - (2.0d * x)) + 3.0d;
        return new double[]{(x * 4.0d) / d2, (y * 6.0d) / d2};
    }

    private static double[] getXyFromUv(double u, double v) {
        double d2 = v * 1.5d;
        return new double[]{(9.0d * u) / (((u * 6.0d) - (16.0d * d2)) + 12.0d), (2.0d * d2) / (((u * 3.0d) - (d2 * 8.0d)) + 6.0d)};
    }

    public static double[] toNewXyFromDuv(double duv, double[] xy, double[] xy1) {
        if (duv != com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON) {
            double[] uvFromXy = getUvFromXy(xy[0], xy[1]);
            double[] uvFromXy2 = getUvFromXy(xy1[0], xy1[1]);
            double d2 = uvFromXy[0] - uvFromXy2[0];
            double d3 = uvFromXy[1] - uvFromXy2[1];
            if (xy[0] < xy1[0]) {
                d2 = -d2;
                d3 = -d3;
            }
            if (Math.sqrt((d2 * d2) + (d3 * d3)) != com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON) {
                return getXyFromUv((float) (uvFromXy[0] - ((d3 / r10) * duv)), (float) (uvFromXy[1] + (duv * (d2 / r10))));
            }
        }
        return xy;
    }
}