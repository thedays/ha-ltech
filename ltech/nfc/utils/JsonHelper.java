package com.ltech.nfc.utils;

import android.content.Context;
import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.reflect.TypeToken;
import com.ltech.nfc.source.SourceHelper;
import com.ltech.nfc.source.SourceModel;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class JsonHelper {
    public static String getJson(String str, Context context) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getAssets().open(str)));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                sb.append(readLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static List<SourceModel> getSourceList(Context context) {
        return (List) GsonUtils.fromJson(getJson("DriverConfigsForAndroid.json", context), new TypeToken<List<SourceModel>>() { // from class: com.ltech.nfc.utils.JsonHelper.1
        }.getType());
    }

    public static List<SourceModel> getSourceList(byte[] bArr) {
        if (bArr != null) {
            List<SourceModel> list = (List) GsonUtils.fromJson(new String(bArr), new TypeToken<List<SourceModel>>() { // from class: com.ltech.nfc.utils.JsonHelper.2
            }.getType());
            SourceHelper.virtualProductList.clear();
            Iterator<SourceModel> it = list.iterator();
            while (it.hasNext()) {
                SourceHelper.virtualProductList.add(it.next().sourceName);
            }
            return list;
        }
        return new ArrayList();
    }
}