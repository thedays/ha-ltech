package com.ltech.smarthome.utils;

import android.os.Build;

/* loaded from: classes4.dex */
public class VersionUtils {
    public static boolean isAndroidQ() {
        return Build.VERSION.SDK_INT >= 29;
    }
}