package com.ltech.smarthome.ui.device.light;

import android.view.View;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.LightAssistant;

/* loaded from: classes4.dex */
public class ActModuleSwitchVM extends BaseViewModel {
    public long controlId;
    public boolean groupControl;
    public boolean status;
    public MutableLiveData<Object> controlObject = new MutableLiveData<>();
    public MutableLiveData<Boolean> stateOn = new MutableLiveData<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.light.ActModuleSwitchVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActModuleSwitchVM.this.lambda$new$0((View) obj);
        }
    });

    public LightAssistant getLightAssistant() {
        return CmdAssistant.getLightCmdAssistant(this.controlObject.getValue(), new int[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        if (view.getId() != R.id.iv_switch_status) {
            return;
        }
        LightAssistant lightAssistant = getLightAssistant();
        boolean equals = Boolean.FALSE.equals(this.stateOn.getValue());
        lightAssistant.sendOnOff(ActivityUtils.getTopActivity(), equals);
        this.stateOn.setValue(Boolean.valueOf(equals));
        if (this.groupControl && RelaySeparationHelper.isRelaySeparationSub(this.controlObject.getValue())) {
            RelaySeparationHelper.changeMainGroupRelayOnOff((Group) this.controlObject.getValue());
        }
    }
}