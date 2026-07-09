package com.ltech.smarthome.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/* loaded from: classes4.dex */
public final class DeviceIdUtils {
    private static final String SP_KEY_DEVICE_ID = "device_id";
    private static final String SP_NAME = "device_info";
    private static final String TAG = "DeviceIdUtils";
    private static final String TEMP_DIR = "system_config";
    private static final String TEMP_FILE_NAME = "system_file";
    private static final String TEMP_FILE_NAME_MIME_TYPE = "application/octet-stream";

    public static String getDeviceId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME, 0);
        String string = sharedPreferences.getString("device_id", null);
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        String imei = getIMEI(context);
        if (TextUtils.isEmpty(imei)) {
            imei = createUUID(context);
        }
        sharedPreferences.edit().putString("device_id", imei).apply();
        return imei;
    }

    /* JADX WARN: Code restructure failed: missing block: B:120:0x0187, code lost:
    
        if (r7 != null) goto L188;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x0189, code lost:
    
        r7.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x018e, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x018f, code lost:
    
        r0.printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0072, code lost:
    
        if (r14 == null) goto L147;
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x01b1, code lost:
    
        if (r7 == null) goto L147;
     */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x0202, code lost:
    
        if (r1 == null) goto L147;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0074, code lost:
    
        r14.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0079, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x007a, code lost:
    
        r0.printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0093, code lost:
    
        if (r14 == null) goto L147;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x00fe, code lost:
    
        if (r7 != null) goto L184;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x010b, code lost:
    
        r7.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0110, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0111, code lost:
    
        r0.printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x0109, code lost:
    
        if (0 == 0) goto L147;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:178:0x0214 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:185:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:186:0x020a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00a5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:55:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x009b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r7v0, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r7v11 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String createUUID(android.content.Context r14) {
        /*
            Method dump skipped, instructions count: 541
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.utils.DeviceIdUtils.createUUID(android.content.Context):java.lang.String");
    }

    private static String getIMEI(Context context) {
        if (Build.VERSION.SDK_INT >= 29) {
            return null;
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager == null) {
                return null;
            }
            return telephonyManager.getDeviceId();
        } catch (Exception unused) {
            return null;
        }
    }
}