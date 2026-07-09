package com.ltech.smarthome.ui.device.aspanel;

import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;
import com.ltech.smarthome.ui.device.light.ActLightOnOffTime;
import com.ltech.smarthome.ui.device.light.ActSetLighDmxType;
import com.ltech.smarthome.ui.device.light.ActSetLightOnState;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;

/* loaded from: classes4.dex */
public class ActAsPanelSettingVM extends BaseDeviceSetViewModel {
    public int ligth_type;
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanelSettingVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActAsPanelSettingVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.layout_change_room /* 2131297392 */:
                this.showEditRoomDialogEvent.call();
                break;
            case R.id.layout_device_name /* 2131297433 */:
                this.showEditNameDialogEvent.call();
                break;
            case R.id.layout_on_off_time /* 2131297559 */:
                navigation(NavUtils.destination(ActLightOnOffTime.class).withLong(Constants.CONTROL_ID, this.controlId).withDefaultRequestCode());
                break;
            case R.id.layout_set_dmx_type /* 2131297635 */:
                navigation(NavUtils.destination(ActSetLighDmxType.class).withLong(Constants.CONTROL_ID, this.controlId).withDefaultRequestCode());
                break;
            case R.id.layout_set_on_state /* 2131297646 */:
                navigation(NavUtils.destination(ActSetLightOnState.class).withLong(Constants.CONTROL_ID, this.controlId).withDefaultRequestCode());
                break;
            case R.id.layout_upgrade /* 2131297687 */:
                this.upgradeEvent.call();
                break;
            case R.id.tv_delete_device /* 2131298576 */:
                this.showDeleteDialogEvent.call();
                break;
        }
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel
    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }
}