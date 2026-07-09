package com.ltech.smarthome.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.TypedValue;
import com.ltech.smarthome.BuildConfig;
import com.ltech.smarthome.R;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class Utils {
    public static int dip2px(Context context, float dpValue) {
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static String getVersionName(Context context) throws Exception {
        return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
    }

    public static int getVersionCode(Context context) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            packageInfo = null;
        }
        return packageInfo.versionCode;
    }

    public static String getFlavorName(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo == null) {
                return "";
            }
            String string = applicationInfo.metaData.getString("channel_name");
            return string == null ? "" : string;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean isYYBFlavor(Context context) {
        String flavorName = getFlavorName(context);
        return flavorName != null && flavorName.equals(BuildConfig.FLAVOR);
    }

    public static <E> List<E> getAddaListThanbList(List<E> aList, List<E> bList) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < aList.size(); i++) {
            if (!myListContains(bList, aList.get(i))) {
                arrayList.add(aList.get(i));
            }
        }
        return arrayList;
    }

    public static <E> List<E> getReduceaListThanbList(List<E> aList, List<E> bList) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < bList.size(); i++) {
            if (!myListContains(aList, bList.get(i))) {
                arrayList.add(bList.get(i));
            }
        }
        return arrayList;
    }

    private static <E> boolean myListContains(List<E> sourceList, E element) {
        if (sourceList == null || element == null || sourceList.isEmpty()) {
            return false;
        }
        Iterator<E> it = sourceList.iterator();
        while (it.hasNext()) {
            if (element.equals(it.next())) {
                return true;
            }
        }
        return false;
    }

    public static <E> List<E> cleanDisRepet(List<E> list) {
        HashSet hashSet = new HashSet(list);
        list.clear();
        list.addAll(hashSet);
        return list;
    }

    public static void copyText(Context context, String text) {
        ((ClipboardManager) context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Label", text));
        SmartToast.showShort(R.string.copy_success);
    }

    public static List<String> getNameList(List<ParamMap> paramList) {
        ArrayList arrayList = new ArrayList();
        Iterator<ParamMap> it = paramList.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getName());
        }
        return arrayList;
    }

    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(1, dpValue, context.getResources().getDisplayMetrics());
    }
}