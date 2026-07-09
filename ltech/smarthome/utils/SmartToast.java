package com.ltech.smarthome.utils;

import android.graphics.Color;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;

/* loaded from: classes4.dex */
public class SmartToast {
    public static void showShort(int resId) {
        showShort(ActivityUtils.getTopActivity().getString(resId));
    }

    public static void showShort(String content) {
        showCenterShort(content);
    }

    public static void showCenterShort(final String content) {
        if (ActivityUtils.getTopActivity() == null || ActivityUtils.getTopActivity().isFinishing()) {
            return;
        }
        final ToastUtils toastUtils = new ToastUtils();
        toastUtils.setGravity(17, 0, 0);
        toastUtils.setBgColor(Color.argb(255, 74, 74, 74));
        toastUtils.setDurationIsLong(false);
        toastUtils.setTextColor(-1);
        ActivityUtils.getTopActivity().runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.utils.SmartToast.1
            @Override // java.lang.Runnable
            public void run() {
                ToastUtils.this.show(content);
            }
        });
    }
}