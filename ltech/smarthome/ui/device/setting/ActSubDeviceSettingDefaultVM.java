package com.ltech.smarthome.ui.device.setting;

import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;

/* loaded from: classes4.dex */
public class ActSubDeviceSettingDefaultVM extends BaseDeviceSetViewModel {
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.setting.ActSubDeviceSettingDefaultVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActSubDeviceSettingDefaultVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.layout_change_icon /* 2131297387 */:
                this.showSelectDeviceIconDialogEvent.call();
                break;
            case R.id.layout_change_room /* 2131297392 */:
                this.showEditRoomDialogEvent.call();
                break;
            case R.id.layout_device_name /* 2131297433 */:
                this.showEditNameDialogEvent.call();
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