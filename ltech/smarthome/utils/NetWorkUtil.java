package com.ltech.smarthome.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import com.smart.message.utils.LHomeLog;
import java.util.List;

/* loaded from: classes4.dex */
public class NetWorkUtil {
    public static void bindProcessToNetwork(Context context) {
        if (Build.VERSION.SDK_INT >= 29) {
            ((ConnectivityManager) context.getSystemService("connectivity")).bindProcessToNetwork(null);
        }
    }

    public static void disconnect(Context context) {
        if (Build.VERSION.SDK_INT >= 29) {
            ((ConnectivityManager) context.getSystemService("connectivity")).bindProcessToNetwork(null);
        }
    }

    public static boolean isNetWorkSaved(Context context, String ssid) {
        String str;
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        wifiManager.getConfiguredNetworks();
        for (WifiConfiguration wifiConfiguration : configuredNetworks) {
            LHomeLog.i(NetWorkUtil.class, "---------------");
            LHomeLog.i(NetWorkUtil.class, wifiConfiguration.toString());
            if (wifiConfiguration.SSID.length() > 2 && wifiConfiguration.SSID.charAt(0) == '\"' && wifiConfiguration.SSID.charAt(wifiConfiguration.SSID.length() - 1) == '\"') {
                str = wifiConfiguration.SSID.substring(1, wifiConfiguration.SSID.length() - 1);
            } else {
                str = wifiConfiguration.SSID;
            }
            if (str.equals(ssid)) {
                return true;
            }
        }
        return false;
    }
}