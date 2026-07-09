package com.ltech.smarthome.model.bean;

/* loaded from: classes4.dex */
public interface Role {

    /* renamed from: com.ltech.smarthome.model.bean.Role$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static boolean $default$isVirtual(Role _this) {
            return false;
        }
    }

    String getCreatetime();

    int getDaliHidden();

    DeviceState getDeviceState();

    String getExtParam();

    long getFloorId();

    String getFloorName();

    int getHideDevice();

    int getIconPos();

    long getId();

    int getIndex();

    String getName();

    long getObjectId();

    long getPlaceId();

    long getRoomId();

    String getRoomName();

    int getSceneType();

    boolean isVirtual();

    void setDeviceState(DeviceState deviceState);
}