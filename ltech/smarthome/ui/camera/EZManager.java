package com.ltech.smarthome.ui.camera;

import android.app.Application;
import android.content.Context;
import com.ltech.smarthome.singleton.Singleton;
import com.ltech.smarthome.ui.device.camera.EzConstants;
import com.ltech.smarthome.ui.device.camera.EzDevice;
import com.smart.message.utils.LHomeLog;
import com.videogo.common.SpTool;
import com.videogo.errorlayer.ErrorInfo;
import com.videogo.exception.BaseException;
import com.videogo.exception.ErrorCode;
import com.videogo.openapi.EZConstants;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.EZOpenSDKListener;
import com.videogo.openapi.bean.EZDeviceInfo;
import com.videogo.openapi.bean.EZProbeDeviceInfoResult;
import com.videogo.wificonfig.APWifiConfig;

/* loaded from: classes4.dex */
public class EZManager {
    public int cameraNum;
    public int configType;
    private EzDevice ezDevice;
    private EZDeviceInfo ezDeviceInfo;

    public interface IAddDeviceListener {
        void onFail(int errorCode);

        void onSuccess();
    }

    public interface IConfigListener {
        void onConnected();

        void onRegistered();
    }

    public interface IDeleteDeviceListener {
        void onFail(int errorCode);

        void onSuccess();
    }

    public interface IQueryDeviceListener {
        void onAlreadyAddedByOther(boolean online);

        void onAlreadyAddedBySelf(boolean online);

        void onFail();

        void onNeedAdd(boolean online);
    }

    public static EZManager instance() {
        return (EZManager) Singleton.getSingleton(EZManager.class);
    }

    public void init(Application application) {
        EZOpenSDK.showSDKLog(true);
        EZOpenSDK.enableP2P(false);
        EZOpenSDK.initLib(application, EzConstants.APPKEY);
        SpTool.init(application);
    }

    public void setAccesstoken(String accesstoken) {
        EZOpenSDK.getInstance().setAccessToken(accesstoken);
    }

    public void exit() {
        EZOpenSDK.getInstance().logout();
    }

    public EZOpenSDK getSDK() {
        return EZOpenSDK.getInstance();
    }

    public EzDevice getEzDevice() {
        return this.ezDevice;
    }

    public void setEzDevice(EzDevice ezDevice) {
        this.ezDevice = ezDevice;
    }

    public EZDeviceInfo getEzDeviceInfo() {
        return this.ezDeviceInfo;
    }

    public void setEzDeviceInfo(EZDeviceInfo ezDeviceInfo) {
        this.ezDeviceInfo = ezDeviceInfo;
    }

    public int getConfigType() {
        return this.configType;
    }

    public void queryDeviceInfo(String serialNo, String deviceType, IQueryDeviceListener queryDeviceListener) {
        LHomeLog.i(getClass(), "accessToken = " + getSDK().getEZAccessToken().getAccessToken());
        EZProbeDeviceInfoResult probeDeviceInfo = getSDK().probeDeviceInfo(serialNo, deviceType);
        if (probeDeviceInfo != null) {
            if (probeDeviceInfo.getEZProbeDeviceInfo() != null) {
                if (probeDeviceInfo.getEZProbeDeviceInfo().getSupportAP() == 2) {
                    this.configType = 1;
                }
                if (probeDeviceInfo.getEZProbeDeviceInfo().getSupportWifi() == 3) {
                    this.configType = 2;
                }
                this.cameraNum = probeDeviceInfo.getEZProbeDeviceInfo().getAvailableChannelCount();
            }
            if (probeDeviceInfo.getBaseException() == null) {
                queryDeviceListener.onNeedAdd(true);
                return;
            }
            LHomeLog.i(getClass(), serialNo + ",ErrorCode = " + probeDeviceInfo.getBaseException().getErrorCode());
            switch (probeDeviceInfo.getBaseException().getErrorCode()) {
                case ErrorCode.ERROR_WEB_DEVICE_NOT_EXIT /* 120002 */:
                case ErrorCode.ERROR_WEB_DEVICE_OFFLINE_NOT_ADD /* 120023 */:
                    queryDeviceListener.onNeedAdd(false);
                    break;
                case ErrorCode.ERROR_WEB_DEVICE_NOT_ADD /* 120020 */:
                    queryDeviceListener.onAlreadyAddedBySelf(true);
                    break;
                case 120022:
                    queryDeviceListener.onAlreadyAddedByOther(true);
                    break;
                case 120024:
                    queryDeviceListener.onAlreadyAddedByOther(false);
                    break;
                case ErrorCode.ERROR_WEB_DEVICE_ADD_OWN_AGAIN /* 120029 */:
                    queryDeviceListener.onAlreadyAddedBySelf(false);
                    break;
                default:
                    queryDeviceListener.onFail();
                    break;
            }
            return;
        }
        queryDeviceListener.onFail();
    }

    public void queryDeviceInfo(IQueryDeviceListener queryDeviceListener) {
        queryDeviceInfo(this.ezDevice.getSerialNo(), this.ezDevice.getDeviceType(), queryDeviceListener);
    }

    public void addDevice(String serialNo, String verifyCode, IAddDeviceListener addDeviceListener) {
        try {
            if (getSDK().addDevice(serialNo, verifyCode)) {
                addDeviceListener.onSuccess();
            }
        } catch (BaseException e) {
            ErrorInfo object = e.getObject();
            LHomeLog.i(getClass(), object.toString());
            addDeviceListener.onFail(object.errorCode);
        }
    }

    public void addDevice(IAddDeviceListener addDeviceListener) {
        addDevice(this.ezDevice.getSerialNo(), this.ezDevice.getVerifyCode(), addDeviceListener);
    }

    public void deleteDevice(IDeleteDeviceListener deleteDeviceListener) {
        try {
            if (!getSDK().deleteDevice(this.ezDevice.getSerialNo()) || deleteDeviceListener == null) {
                return;
            }
            deleteDeviceListener.onSuccess();
        } catch (BaseException e) {
            ErrorInfo object = e.getObject();
            LHomeLog.i(getClass(), object.toString());
            if (deleteDeviceListener != null) {
                deleteDeviceListener.onFail(object.errorCode);
            }
        }
    }

    public void startConfigWifi(Context context, String serialNo, String ssid, String password, final IConfigListener configListener) {
        if (serialNo.equals("")) {
            serialNo = this.ezDevice.getSerialNo();
        }
        getSDK().startConfigWifi(context, serialNo, ssid, password, EZConstants.EZWiFiConfigMode.EZWiFiConfigSmart, new EZOpenSDKListener.EZStartConfigWifiCallback() { // from class: com.ltech.smarthome.ui.camera.EZManager.1
            @Override // com.videogo.openapi.EZOpenSDKListener.EZStartConfigWifiCallbackInterface
            public void onStartConfigWifiCallback(String deviceSerial, EZConstants.EZWifiConfigStatus status) {
                if (status == EZConstants.EZWifiConfigStatus.DEVICE_WIFI_CONNECTED) {
                    configListener.onConnected();
                } else if (status == EZConstants.EZWifiConfigStatus.DEVICE_PLATFORM_REGISTED) {
                    EZManager.this.getSDK().stopConfigWiFi();
                    configListener.onRegistered();
                }
            }
        });
    }

    public void stopConfigWifi() {
        getSDK().stopConfigWiFi();
    }

    public void startAPConfigWifi(String serialNo, String verifyCode, String ssid, String password, final IConfigListener configListener) {
        if (serialNo.equals("")) {
            serialNo = this.ezDevice.getSerialNo();
        }
        final String str = serialNo;
        if (verifyCode.equals("")) {
            verifyCode = this.ezDevice.getVerifyCode();
        }
        getSDK().startAPConfigWifiWithSsid(ssid, password, str, verifyCode, new APWifiConfig.APConfigCallback(this) { // from class: com.ltech.smarthome.ui.camera.EZManager.2
            @Override // com.videogo.wificonfig.APWifiConfig.APConfigCallbackInterface
            public void onSuccess() {
                LHomeLog.e(getClass(), "startAPConfigWifiWithSsid onSuccess mDeviceSerial = " + str);
                configListener.onConnected();
            }

            @Override // com.videogo.wificonfig.APWifiConfig.APConfigCallbackInterface
            public void OnError(int code) {
                EZOpenSDK.getInstance().stopAPConfigWifiWithSsid();
                LHomeLog.e(getClass(), "startAPConfigWifiWithSsid  OnError mDeviceSerial = " + str);
            }
        });
    }

    public void stopAPConfigWifi() {
        getSDK().stopAPConfigWifiWithSsid();
    }
}