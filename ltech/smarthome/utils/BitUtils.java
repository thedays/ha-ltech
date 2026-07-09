package com.ltech.smarthome.utils;

/* loaded from: classes4.dex */
public class BitUtils {
    public static int convertIntToBit(int number) {
        int i = number < 256 ? 8 : 16;
        for (int i2 = 0; i2 < i; i2++) {
            if (((number >> i2) & 1) == 1) {
                return i2;
            }
        }
        return 0;
    }

    public static int[] convertStringToArray(String data) {
        int[] iArr = new int[data.length() / 2];
        for (int i = 0; i < data.length() / 2; i++) {
            int i2 = i * 2;
            iArr[i] = Integer.parseInt(data.substring(i2, i2 + 2), 16);
        }
        return iArr;
    }
}