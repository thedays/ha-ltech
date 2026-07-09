package com.ltech.smarthome.ui.control.binding;

import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;

/* loaded from: classes4.dex */
abstract class BaseDeviceItemBinding implements IBindItem<Device> {
    BaseDeviceItemBinding() {
    }

    void setDeviceFavourite(Device device) {
        device.getDeviceState().setFavorite(!device.getDeviceState().isFavorite());
        Injection.repo().device().saveDevice(device);
    }

    void setDeviceOn(Device device, boolean on) {
        device.getDeviceState().setOn(on);
        Injection.repo().device().saveDevice(device);
    }
}