package com.ltech.smarthome.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* loaded from: classes4.dex */
public class JumpPermissionManagement {
    private static final String MANUFACTURER_HUAWEI = "Huawei";
    private static final String MANUFACTURER_LENOVO = "LENOVO";
    private static final String MANUFACTURER_LETV = "Letv";
    private static final String MANUFACTURER_LG = "LG";
    private static final String MANUFACTURER_MEIZU = "Meizu";
    private static final String MANUFACTURER_OPPO = "OPPO";
    private static final String MANUFACTURER_SAMSUNG = "samsung";
    private static final String MANUFACTURER_SONY = "Sony";
    private static final String MANUFACTURER_VIVO = "vivo";
    private static final String MANUFACTURER_XIAOMI = "Xiaomi";
    private static final String MANUFACTURER_YULONG = "YuLong";
    private static final String MANUFACTURER_ZTE = "ZTE";

    public static void GoToSetting(Activity activity, int request) {
        String str = Build.MANUFACTURER;
        str.hashCode();
        switch (str) {
            case "Huawei":
                Huawei(activity, request);
                break;
            case "Xiaomi":
                Xiaomi(activity, request);
                break;
            case "LG":
                LG(activity, request);
                break;
            case "Letv":
                Letv(activity, request);
                break;
            case "OPPO":
                OPPO(activity, request);
                break;
            case "Sony":
                Sony(activity, request);
                break;
            case "vivo":
                goVivoMainager(activity, request);
                break;
            case "Meizu":
                Meizu(activity, request);
                break;
            case "samsung":
                goSangXinMainager(activity, request);
                break;
            default:
                ApplicationInfo(activity, request);
                break;
        }
    }

    public static void Huawei(Activity activity, int request) {
        Intent intent = new Intent();
        intent.setFlags(268435456);
        intent.putExtra(com.taobao.accs.common.Constants.KEY_PACKAGE_NAME, activity.getPackageName());
        intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity"));
        activity.startActivityForResult(intent, request);
    }

    public static void Meizu(Activity activity, int request) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.putExtra(com.taobao.accs.common.Constants.KEY_PACKAGE_NAME, activity.getPackageName());
        activity.startActivityForResult(intent, request);
    }

    private static String getMiuiVersion() {
        BufferedReader bufferedReader;
        Throwable th;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("getprop ro.miui.ui.version.name").getInputStream()), 1024);
        } catch (IOException e) {
            e = e;
            bufferedReader = null;
        } catch (Throwable th2) {
            bufferedReader = null;
            th = th2;
            try {
                bufferedReader.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            throw th;
        }
        try {
            try {
                String readLine = bufferedReader.readLine();
                bufferedReader.close();
                try {
                    bufferedReader.close();
                    return readLine;
                } catch (IOException e3) {
                    e3.printStackTrace();
                    return readLine;
                }
            } catch (Throwable th3) {
                th = th3;
                bufferedReader.close();
                throw th;
            }
        } catch (IOException e4) {
            e = e4;
            e.printStackTrace();
            try {
                bufferedReader.close();
            } catch (IOException e5) {
                e5.printStackTrace();
            }
            return null;
        }
    }

    private static void Xiaomi(Activity activity, int request) {
        String miuiVersion = getMiuiVersion();
        Intent intent = new Intent();
        if ("V6".equals(miuiVersion) || "V7".equals(miuiVersion)) {
            intent.setAction("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            intent.putExtra("extra_pkgname", activity.getPackageName());
            activity.startActivityForResult(intent, request);
            return;
        }
        if ("V8".equals(miuiVersion) || "V9".equals(miuiVersion)) {
            intent.setAction("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            intent.putExtra("extra_pkgname", activity.getPackageName());
            activity.startActivityForResult(intent, request);
            return;
        }
        goIntentSetting(activity, request);
    }

    public static void Sony(Activity activity, int request) {
        Intent intent = new Intent();
        intent.setFlags(268435456);
        intent.putExtra(com.taobao.accs.common.Constants.KEY_PACKAGE_NAME, activity.getPackageName());
        intent.setComponent(new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity"));
        activity.startActivityForResult(intent, request);
    }

    public static void OPPO(Activity activity, int request) {
        try {
            activity.startActivityForResult(oppoApi(activity), request);
        } catch (Exception e) {
            e.printStackTrace();
            activity.startActivity(getAppDetailSettingIntent(activity));
        }
    }

    public static void LG(Activity activity, int request) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setFlags(268435456);
        intent.putExtra(com.taobao.accs.common.Constants.KEY_PACKAGE_NAME, activity.getPackageName());
        intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity"));
        activity.startActivityForResult(intent, request);
    }

    public static void Letv(Activity activity, int request) {
        Intent intent = new Intent();
        intent.setFlags(268435456);
        intent.putExtra(com.taobao.accs.common.Constants.KEY_PACKAGE_NAME, activity.getPackageName());
        intent.setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.PermissionAndApps"));
        activity.startActivityForResult(intent, request);
    }

    public static void _360(Activity activity, int request) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setFlags(268435456);
        intent.putExtra(com.taobao.accs.common.Constants.KEY_PACKAGE_NAME, activity.getPackageName());
        intent.setComponent(new ComponentName("com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.ui.index.AppEnterActivity"));
        activity.startActivityForResult(intent, request);
    }

    public static void ApplicationInfo(Activity activity, int request) {
        Intent intent = new Intent();
        intent.addFlags(268435456);
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        activity.startActivityForResult(intent, request);
    }

    public static void SystemConfig(Activity activity, int request) {
        activity.startActivityForResult(new Intent("android.settings.SETTINGS"), request);
    }

    private static void goSangXinMainager(Activity activity, int request) {
        goIntentSetting(activity, request);
    }

    private static void goIntentSetting(Activity activity, int request) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        try {
            activity.startActivityForResult(intent, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void goVivoMainager(Activity activity, int request) {
        Intent intent = new Intent();
        intent.setClassName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.PurviewTabActivity");
        if (isActivityAvailable(activity, intent)) {
            intent.putExtra("packagename", activity.getPackageName());
            intent.putExtra("tabId", "1");
            activity.startActivityForResult(intent, request);
            return;
        }
        Intent intent2 = new Intent();
        intent2.setClassName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity");
        if (isActivityAvailable(activity, intent2)) {
            intent2.setAction("secure.intent.action.softPermissionDetail");
            intent2.putExtra("packagename", activity.getPackageName());
            activity.startActivityForResult(intent2, request);
        }
    }

    private static boolean isActivityAvailable(Activity activity, Intent intent) {
        return intent.resolveActivity(activity.getPackageManager()) != null;
    }

    private static Intent oppoApi(Context context) {
        Intent intent = new Intent();
        if (hasActivity(context, intent)) {
            return intent;
        }
        intent.setComponent(new ComponentName("com.color.safecenter", "com.color.safecenter.permission.PermissionManagerActivity"));
        return intent;
    }

    private static boolean hasActivity(Context context, Intent intent) {
        return context.getPackageManager().queryIntentActivities(intent, 65536).size() > 0;
    }

    private static Intent getAppDetailSettingIntent(Activity activity) {
        Intent intent = new Intent();
        intent.addFlags(268435456);
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        return intent;
    }
}