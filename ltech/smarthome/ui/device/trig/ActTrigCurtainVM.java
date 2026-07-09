package com.ltech.smarthome.ui.device.trig;

import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;

/* loaded from: classes4.dex */
public class ActTrigCurtainVM extends BaseViewModel {
    public long controlId;
    public MutableLiveData<Object> controlObject = new MutableLiveData<>();
    public boolean groupControl;
    public boolean selectAction;

    public void setTrigOpenCloseValue(int i) {
        CmdAssistant.getDeviceAssistant(this.controlObject.getValue(), new int[0]).setOpenCloseVar(ActivityUtils.getTopActivity(), i);
    }

    public void setTrigType(final int outputType) {
        CmdAssistant.getSettingCmdAssistant(this.controlObject.getValue(), new int[0]).setTrigType(ActivityUtils.getTopActivity(), outputType, new IAction() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigCurtainVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActTrigCurtainVM.this.lambda$setTrigType$0(outputType, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setTrigType$0(int i, Boolean bool) {
        if (bool.booleanValue()) {
            Object value = this.controlObject.getValue();
            if (value instanceof Device) {
                ReplaceHelper.instance().backupData(getLifecycleOwner(), ((Device) value).getDeviceId(), UpdateBackDataRequest.LIGHT_TYPE, CmdAssistant.getSettingCmdAssistant(null, new int[0]).setTrigType(i));
            }
        }
    }
}