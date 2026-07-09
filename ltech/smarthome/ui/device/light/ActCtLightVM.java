package com.ltech.smarthome.ui.device.light;

import android.view.View;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.LightAssistant;

/* loaded from: classes4.dex */
public class ActCtLightVM extends BaseViewModel {
    public long controlId;
    public boolean groupControl;
    public MutableLiveData<Object> controlObject = new MutableLiveData<>();
    public MutableLiveData<Boolean> stateOn = new MutableLiveData<>();
    public MutableLiveData<Boolean> stateOnUI = new MutableLiveData<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.light.ActCtLightVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActCtLightVM.this.lambda$new$0((View) obj);
        }
    });

    public LightAssistant getLightAssistant() {
        return CmdAssistant.getLightCmdAssistant(this.controlObject.getValue(), new int[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        if (view.getId() != R.id.iv_open) {
            return;
        }
        this.stateOn.setValue(Boolean.valueOf(!r3.getValue().booleanValue()));
        getLightAssistant().sendOnOff(ActivityUtils.getTopActivity(), this.stateOn.getValue().booleanValue());
        if (this.groupControl) {
            this.stateOnUI.setValue(this.stateOn.getValue());
        }
    }
}