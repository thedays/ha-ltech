package com.ltech.smarthome.service;

import android.content.Context;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/* loaded from: classes4.dex */
public class LocationService {
    private static AMapLocationClientOption DIYoption;
    private static AMapLocationClient client;
    private static AMapLocationClientOption mOption;
    private final Object objLock;

    public LocationService(Context locationContext) {
        Object obj = new Object();
        this.objLock = obj;
        synchronized (obj) {
            if (client == null) {
                try {
                    AMapLocationClient.updatePrivacyShow(locationContext, true, true);
                    AMapLocationClient.updatePrivacyAgree(locationContext, true);
                    AMapLocationClient aMapLocationClient = new AMapLocationClient(locationContext);
                    client = aMapLocationClient;
                    aMapLocationClient.setLocationOption(getDefaultLocationClientOption());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public boolean registerListener(AMapLocationListener listener) {
        if (listener == null) {
            return false;
        }
        client.setLocationListener(listener);
        return true;
    }

    public void unregisterListener(AMapLocationListener listener) {
        if (listener != null) {
            client.unRegisterLocationListener(listener);
        }
    }

    public String getSDKVersion() {
        AMapLocationClient aMapLocationClient = client;
        if (aMapLocationClient != null) {
            return aMapLocationClient.getVersion();
        }
        return null;
    }

    public static boolean setLocationOption(AMapLocationClientOption option) {
        if (option != null) {
            if (client.isStarted()) {
                client.stopLocation();
            }
            DIYoption = option;
            client.setLocationOption(option);
        }
        return false;
    }

    public AMapLocationClientOption getDefaultLocationClientOption() {
        if (mOption == null) {
            AMapLocationClientOption aMapLocationClientOption = new AMapLocationClientOption();
            mOption = aMapLocationClientOption;
            aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mOption.setInterval(120000L);
            mOption.setNeedAddress(true);
            mOption.setGpsFirst(true);
        }
        return mOption;
    }

    public AMapLocationClientOption getOption() {
        if (DIYoption == null) {
            DIYoption = new AMapLocationClientOption();
        }
        return DIYoption;
    }

    public void start() {
        synchronized (this.objLock) {
            AMapLocationClient aMapLocationClient = client;
            if (aMapLocationClient != null && !aMapLocationClient.isStarted()) {
                client.startLocation();
            }
        }
    }

    public void stop() {
        synchronized (this.objLock) {
            AMapLocationClient aMapLocationClient = client;
            if (aMapLocationClient != null && aMapLocationClient.isStarted()) {
                client.stopLocation();
            }
        }
    }

    public boolean isStart() {
        return client.isStarted();
    }
}