package com.ltech.smarthome.ui.device.super_panel;

import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;

/* loaded from: classes4.dex */
public class ActSuperPanelVM extends BaseViewModel {
    public Device controlDevice;
    public long controlId;
    public long placeId;

    public void sendSingleOnOff(int position, boolean on) {
        CmdAssistant.getLightCmdAssistant(this.controlDevice, 1 << position).sendSingleOnOff(ActivityUtils.getTopActivity(), on);
    }

    public boolean isSupportNewMusic() {
        if (this.controlDevice.getMcuversion() == null) {
            return false;
        }
        String productId = this.controlDevice.getProductId();
        productId.hashCode();
        switch (productId) {
            case "122042815485901":
                if (this.controlDevice.getMcuversion().compareTo("1.1.5") >= 0) {
                }
                break;
            case "122080911090801":
            case "121052512023201":
                if (this.controlDevice.getMcuversion().compareTo("2.2.6") >= 0) {
                }
                break;
        }
        return false;
    }
}