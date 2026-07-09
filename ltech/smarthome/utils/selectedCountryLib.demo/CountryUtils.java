package com.ltech.smarthome.utils.selectedCountryLib.demo;

import android.content.Context;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.spongycastle.pqc.math.linearalgebra.Matrix;

/* loaded from: classes4.dex */
public class CountryUtils {
    public static HashMap<Character, ArrayList<String[]>> getGroupedCountryList(Context context) {
        Character[] chArr = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', Character.valueOf(Matrix.MATRIX_TYPE_RANDOM_LT), 'M', 'N', 'O', 'P', 'Q', Character.valueOf(Matrix.MATRIX_TYPE_RANDOM_REGULAR), 'S', 'T', Character.valueOf(Matrix.MATRIX_TYPE_RANDOM_UT), 'V', 'W', 'X', 'Y', Character.valueOf(Matrix.MATRIX_TYPE_ZERO)};
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (int i = 0; i < 26; i++) {
            Character ch = chArr[i];
            ArrayList<String[]> array = getArray(context, "smssdk_country_group_" + String.valueOf(ch).toLowerCase());
            if (!array.isEmpty()) {
                linkedHashMap.put(ch, array);
            }
        }
        return linkedHashMap;
    }

    private static ArrayList<String[]> getArray(Context context, String key) {
        ArrayList<String[]> arrayList = new ArrayList<>();
        int identifier = context.getResources().getIdentifier(key, TmpConstant.TYPE_VALUE_ARRAY, context.getPackageName());
        if (identifier > 0) {
            for (String str : context.getResources().getStringArray(identifier)) {
                arrayList.add(str.split(Constants.ACCEPT_TIME_SEPARATOR_SP));
            }
        }
        return arrayList;
    }
}