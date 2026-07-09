package com.ltech.smarthome.utils;

/* loaded from: classes4.dex */
public class LiveBusHelper {
    public static final int CHANGE_PAGE_ITEM = 5;
    public static final int INTERCOM_LOGIN_SUCCESS = 12;
    public static final int MIC_RECORD = 4;
    public static final int MUSIC_PLAY = 3;
    public static final int REGISTER_BY_MAIL = 2;
    public static final int REGISTER_BY_PHONE = 1;
    public static final int RTSP_MONITOR_ESTABLISHED = 16;
    public static final int RTSP_MONITOR_FINISHED = 13;
    public static final int SIP_CALL_ESTABLISHED = 14;
    public static final int SIP_CALL_FINISHED = 15;
    public static final int SUB_DEVICE_STATE_MESSAGE = 17;
    public static final int UPDATE_DEVICE_ICON = 7;
    public static final int UPDATE_DEVICE_VERSION = 6;
    public static final int UPDATE_GROUP = 9;
    public static final int UPDATE_SENSOR_ILLUMINANCE = 11;
    public static final int UPDATE_SENSOR_PARAM = 8;
    public static final int UPDATE_SENSOR_SENSITIVITY = 10;
    private int code;
    private Object data;

    public LiveBusHelper(int code) {
        this.code = code;
    }

    public LiveBusHelper(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return this.code;
    }

    public Object getData() {
        return this.data;
    }
}