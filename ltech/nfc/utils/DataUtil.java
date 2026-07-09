package com.ltech.nfc.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class DataUtil {
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static char[] encodeHex(byte[] bArr) {
        return encodeHex(bArr, true);
    }

    public static char[] encodeHex(byte[] bArr, boolean z) {
        return encodeHex(bArr, z ? DIGITS_LOWER : DIGITS_UPPER);
    }

    protected static char[] encodeHex(byte[] bArr, char[] cArr) {
        if (bArr == null) {
            return null;
        }
        char[] cArr2 = new char[bArr.length << 1];
        int i = 0;
        for (byte b2 : bArr) {
            int i2 = i + 1;
            cArr2[i] = cArr[(b2 & 240) >>> 4];
            i += 2;
            cArr2[i2] = cArr[b2 & 15];
        }
        return cArr2;
    }

    public static String encodeHexStr(byte[] bArr) {
        return encodeHexStr(bArr, true);
    }

    public static String encodeHexStr(byte[] bArr, boolean z) {
        return encodeHexStr(bArr, z ? DIGITS_LOWER : DIGITS_UPPER);
    }

    protected static String encodeHexStr(byte[] bArr, char[] cArr) {
        return new String(encodeHex(bArr, cArr));
    }

    public static String formatHexString(byte[] bArr) {
        return formatHexString(bArr, true);
    }

    public static String formatHexString(int[] iArr) {
        return formatHexString(convertToBytes(iArr), false);
    }

    public static String formatHexString(List<Integer> list) {
        return formatHexString(convertToBytes(list), false);
    }

    public static String formatHexString(byte[] bArr, boolean z) {
        if (bArr == null || bArr.length < 1) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bArr.length; i++) {
            String hexString = Integer.toHexString(bArr[i] & 255);
            if (hexString.length() == 1) {
                hexString = "0" + hexString;
            }
            sb.append(hexString);
            if (z && i != 0 && (i + 1) % 4 == 0) {
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }

    public static byte[] decodeHex(char[] cArr) {
        int length = cArr.length;
        if ((length & 1) != 0) {
            throw new RuntimeException("Odd number of characters.");
        }
        byte[] bArr = new byte[length >> 1];
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int i3 = i + 1;
            int digit = (toDigit(cArr[i], i) << 4) | toDigit(cArr[i3], i3);
            i += 2;
            bArr[i2] = (byte) (digit & 255);
            i2++;
        }
        return bArr;
    }

    protected static int toDigit(char c2, int i) {
        int digit = Character.digit(c2, 16);
        if (digit != -1) {
            return digit;
        }
        throw new RuntimeException("Illegal hexadecimal character " + c2 + " at index " + i);
    }

    public static byte[] hexStringToBytes(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        String upperCase = str.trim().toUpperCase();
        int length = upperCase.length() / 2;
        char[] charArray = upperCase.toCharArray();
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) (charToByte(charArray[i2 + 1]) | (charToByte(charArray[i2]) << 4));
        }
        return bArr;
    }

    public static int[] hexStringToInts(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        String upperCase = str.trim().toUpperCase();
        int length = upperCase.length() / 2;
        char[] charArray = upperCase.toCharArray();
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            iArr[i] = charToByte(charArray[i2 + 1]) | (charToByte(charArray[i2]) << 4);
        }
        return iArr;
    }

    public static List<Integer> hexStringToIntList(String str) {
        ArrayList arrayList = new ArrayList();
        if (str != null && !str.isEmpty()) {
            String upperCase = str.trim().toUpperCase();
            int length = upperCase.length() / 2;
            char[] charArray = upperCase.toCharArray();
            for (int i = 0; i < length; i++) {
                int i2 = i * 2;
                arrayList.add(Integer.valueOf(charToByte(charArray[i2 + 1]) | (charToByte(charArray[i2]) << 4)));
            }
        }
        return arrayList;
    }

    public static List<Integer> intToList(int i, int i2) {
        ArrayList arrayList = new ArrayList();
        for (int i3 = i2 - 1; i3 >= 0; i3--) {
            arrayList.add(Integer.valueOf((i >> (i3 * 8)) & 255));
        }
        return arrayList;
    }

    public static byte[] convertToBytes(List<Integer> list) {
        byte[] bArr = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            bArr[i] = (byte) (list.get(i).intValue() & 255);
        }
        return bArr;
    }

    public static byte[] convertToBytes(int[] iArr) {
        byte[] bArr = new byte[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            bArr[i] = (byte) (iArr[i] & 255);
        }
        return bArr;
    }

    public static byte charToByte(char c2) {
        return (byte) "0123456789ABCDEF".indexOf(c2);
    }

    public static int getCheckSum(List<Integer> list, int i, int i2) {
        int i3 = 0;
        while (i < Math.min(i2, list.size())) {
            i3 += list.get(i).intValue();
            i++;
        }
        return i3;
    }

    public static int getCheckSum(int[] iArr, int i, int i2) {
        int i3 = 0;
        while (i < Math.min(i2, iArr.length)) {
            i3 += iArr[i];
            i++;
        }
        return i3;
    }

    public static int crc16(List<Integer> list) {
        Iterator<Integer> it = list.iterator();
        int i = 65535;
        while (it.hasNext()) {
            i ^= (it.next().intValue() & 255) << 8;
            for (int i2 = 0; i2 < 8; i2++) {
                i = (32768 & i) != 0 ? (i << 1) ^ 4129 : i << 1;
            }
        }
        return i & 65535;
    }
}