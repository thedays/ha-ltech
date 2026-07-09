package com.ltech.smarthome.ui.device.doorsensor;

import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.ui.device.super_panel.SuperPanelVersionHelper;

/* loaded from: classes4.dex */
public class ActDoorSensorVM extends BaseViewModel {
    public long controlId;
    public MutableLiveData<Device> controlObject = new MutableLiveData<>();
    public long placeId;

    public boolean hasOnlineGatewaySupport() {
        for (Device device : Injection.repo().device().getDeviceListByPlaceId(this.placeId)) {
            if (device.isOnline() && SuperPanelVersionHelper.supportDoorSensor(device)) {
                return true;
            }
        }
        return false;
    }
}