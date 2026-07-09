package com.ltech.smarthome.ui.device.central.air;

import com.smart.message.utils.LHomeLog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class Utils {
    public static final int FUN_CODE = 208;
    public static final int SELECT_STATUS_COMMAND_CODE = 1;
    public static final int SELECT_STATUS_ONE_OR_CONTROL_COMMAND_CODE = 2;

    public static boolean checkData(String data) {
        String substring = data.substring(0, data.length() - 2);
        String substring2 = data.substring(data.length() - 2);
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i < substring.length()) {
            int i2 = i + 2;
            arrayList.add(Integer.valueOf(substring.substring(i, i2), 16));
            i = i2;
        }
        int i3 = 0;
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            i3 += ((Integer) arrayList.get(i4)).intValue();
        }
        String intToHex = intToHex(i3);
        String substring3 = intToHex.length() < 2 ? "0" + intToHex : intToHex.substring(intToHex.length() - 2);
        LHomeLog.i(Utils.class, "checkData=" + substring3.equals(substring2) + "___calculateSumToHexLow8=" + substring3);
        return substring3.equals(substring2);
    }

    private static String intToHex(int n) {
        if (n == 0) {
            return "00";
        }
        StringBuilder sb = new StringBuilder(8);
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        while (n != 0) {
            sb.append(cArr[n % 16]);
            n /= 16;
        }
        String upperCase = sb.reverse().toString().toUpperCase();
        if (upperCase.length() >= 2) {
            return upperCase;
        }
        return "0" + upperCase;
    }

    public static int getCheckCode(List<Integer> datas) {
        int i = 0;
        for (int i2 = 0; i2 < datas.size(); i2++) {
            i += datas.get(i2).intValue();
        }
        return Integer.valueOf(intToHex(i).substring(r3.length() - 2), 16).intValue();
    }
}