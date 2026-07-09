package com.ltech.smarthome.ui.device.ir;

import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;

/* loaded from: classes4.dex */
public class ActIrSettingVM extends BaseDeviceSetViewModel {
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.ir.ActIrSettingVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActIrSettingVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        int id = view.getId();
        if (id == R.id.layout_change_ir) {
            NavUtils.Builder irNavBuilder = NavHelper.getIrNavBuilder(this.controlDevice.getValue().getProductId());
            if (irNavBuilder != null) {
                navigation(irNavBuilder.withLong(Constants.CONTROL_ID, this.controlDevice.getValue().getId()).withBoolean(Constants.CHANGE_IR, true));
                return;
            }
            return;
        }
        if (id == R.id.layout_device_name) {
            this.showEditNameDialogEvent.call();
        } else {
            if (id != R.id.tv_delete_device) {
                return;
            }
            this.showDeleteDialogEvent.call();
        }
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel
    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }
}