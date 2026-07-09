package com.ltech.smarthome.ui.device.eurpanel;

import android.view.View;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.ui.group.BaseGroupSettingVM;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;

/* loaded from: classes4.dex */
public class ActEurPanelGroupSettingVM extends BaseGroupSettingVM {
    public long controlId;
    public RelateInfoAssistant relateInfoAssistant;
    public MutableLiveData<Boolean> buzzerIsOpen = new MutableLiveData<>();
    public MutableLiveData<Boolean> isShowBindKRange = new MutableLiveData<>();
    public SingleLiveEvent<Void> adjustKRange = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showDmxTypeDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showPowerStateDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showEditGroupEvent = new SingleLiveEvent<>();
    public MutableLiveData<Boolean> keySave = new MutableLiveData<>(false);
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSettingVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActEurPanelGroupSettingVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.btn_adjust /* 2131296487 */:
                this.adjustKRange.call();
                break;
            case R.id.layout_edit_group /* 2131297457 */:
                this.showEditGroupEvent.call();
                break;
            case R.id.layout_set_dmx_type /* 2131297635 */:
                this.showDmxTypeDialogEvent.call();
                break;
            case R.id.layout_set_on_state /* 2131297646 */:
                this.showPowerStateDialogEvent.call();
                break;
        }
    }

    public void initRelateInfoList(Group group) {
        RelateInfoUtils.initRelateInfoList(group);
        this.relateInfoAssistant = RelateInfoUtils.relateInfoAssistant;
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }
}