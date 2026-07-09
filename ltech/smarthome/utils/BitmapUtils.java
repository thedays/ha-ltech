package com.ltech.smarthome.utils;

import android.graphics.Bitmap;
import android.graphics.Color;

/* loaded from: classes4.dex */
public class BitmapUtils {
    private static boolean tablesInitialized = false;
    private static final byte[] redTable = new byte[256];
    private static final byte[] greenTable = new byte[256];
    private static final byte[] blueTable = new byte[256];

    public static byte[] argb8888ToArgb5658(Bitmap bitmap) {
        return BMPConvertRGBA8888ToRGBA5658(bitmapToByteArrayWithAlphaFirst(bitmap), bitmap.getWidth(), bitmap.getHeight());
    }

    private static byte[] bitmapToByteArrayWithAlphaFirst(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i = width * height;
        int[] iArr = new int[i];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        byte[] bArr = new byte[i * 4];
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            int i4 = iArr[i3];
            int alpha = Color.alpha(i4);
            int red = Color.red(i4);
            int green = Color.green(i4);
            int blue = Color.blue(i4);
            bArr[i2] = (byte) red;
            bArr[i2 + 1] = (byte) green;
            int i5 = i2 + 3;
            bArr[i2 + 2] = (byte) blue;
            i2 += 4;
            bArr[i5] = (byte) alpha;
        }
        return bArr;
    }

    private static byte[] BMPConvertRGBA8888ToRGBA5658(byte[] src, int width, int height) {
        int i = width * height;
        byte[] bArr = new byte[i * 3];
        if (!tablesInitialized) {
            for (int i2 = 0; i2 < 256; i2++) {
                byte b2 = (byte) ((i2 >> 3) & 31);
                redTable[i2] = b2;
                greenTable[i2] = (byte) ((i2 >> 2) & 63);
                blueTable[i2] = b2;
            }
            tablesInitialized = true;
        }
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < i; i5++) {
            byte b3 = redTable[src[i3] & 255];
            byte b4 = greenTable[src[i3 + 1] & 255];
            int i6 = i3 + 3;
            byte b5 = blueTable[src[i3 + 2] & 255];
            bArr[i4] = (byte) ((b3 << 3) | (b4 >> 3));
            int i7 = i4 + 2;
            bArr[i4 + 1] = (byte) (((b4 & 7) << 5) | b5);
            i4 += 3;
            i3 += 4;
            bArr[i7] = src[i6];
        }
        return bArr;
    }
}