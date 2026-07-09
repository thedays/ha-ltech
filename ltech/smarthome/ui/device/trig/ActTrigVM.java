package com.ltech.smarthome.ui.device.trig;

import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;

/* loaded from: classes4.dex */
public class ActTrigVM extends BaseViewModel {
    public SingleLiveEvent<Device> controlDevice = new SingleLiveEvent<>();
    public long controlId;

    public void setTrigType(final int outputType) {
        CmdAssistant.getSettingCmdAssistant(this.controlDevice.getValue(), new int[0]).setTrigType(ActivityUtils.getTopActivity(), outputType, new IAction() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActTrigVM.this.lambda$setTrigType$0(outputType, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setTrigType$0(int i, Boolean bool) {
        if (bool.booleanValue()) {
            ReplaceHelper.instance().backupData(getLifecycleOwner(), this.controlDevice.getValue().getDeviceId(), UpdateBackDataRequest.LIGHT_TYPE, CmdAssistant.getSettingCmdAssistant(null, new int[0]).setTrigType(i));
        }
    }

    public void queryTrigState(Device device) {
        CmdAssistant.getQueryCmdAssistant(device, new int[0]).queryPanelDeviceMotorState(ActivityUtils.getTopActivity());
    }

    public void setTrigOpenCloseValue(int i) {
        CmdAssistant.getDeviceAssistant(this.controlDevice.getValue(), new int[0]).setOpenCloseVar(ActivityUtils.getTopActivity(), i);
    }
}