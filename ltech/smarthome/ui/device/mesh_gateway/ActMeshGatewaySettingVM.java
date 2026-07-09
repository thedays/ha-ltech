package com.ltech.smarthome.ui.device.mesh_gateway;

import android.view.View;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.ui.config.ActProductIntroduction1;
import com.ltech.smarthome.ui.config.ConfigHelper;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;
import com.ltech.smarthome.upgrade.ActUpgrade;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;

/* loaded from: classes4.dex */
public class ActMeshGatewaySettingVM extends BaseDeviceSetViewModel {
    public long controlId;
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewaySettingVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActMeshGatewaySettingVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.layout_atmosphere_lamp_setting /* 2131297355 */:
                NavUtils.destination(ActMeshGatewayLightSetting.class).withLong(Constants.CONTROL_ID, this.controlId).navigation(ActivityUtils.getTopActivity());
                break;
            case R.id.layout_change_room /* 2131297392 */:
                this.showEditRoomDialogEvent.call();
                break;
            case R.id.layout_device_name /* 2131297433 */:
                this.showEditNameDialogEvent.call();
                break;
            case R.id.layout_set_network /* 2131297644 */:
                if (this.controlDevice.getValue() != null) {
                    ConfigHelper.instance().reset();
                    ConfigHelper.instance().placeId = this.controlDevice.getValue().getPlaceId();
                    ConfigHelper.instance().floorId = this.controlDevice.getValue().getFloorId();
                    ConfigHelper.instance().roomId = this.controlDevice.getValue().getRoomId();
                    ConfigHelper.instance().macdeviceid = this.controlDevice.getValue().getDeviceId();
                    ConfigHelper.instance().mac = this.controlDevice.getValue().getWifiMac();
                    ConfigHelper.instance().productInfo = Injection.productFactory().createMeshGateway();
                    NavUtils.destination(ActProductIntroduction1.class).withBoolean(Constants.SETTING_PAGE, true).navigation(ActivityUtils.getTopActivity());
                    break;
                }
                break;
            case R.id.layout_upgrade /* 2131297687 */:
                NavUtils.destination(ActUpgrade.class).withLong(Constants.CONTROL_ID, this.controlId).navigation(ActivityUtils.getTopActivity());
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