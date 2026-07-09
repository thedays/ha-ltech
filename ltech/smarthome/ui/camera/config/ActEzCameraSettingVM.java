package com.ltech.smarthome.ui.camera.config;

import android.view.View;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.device_param.CameraParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.camera.EZManager;
import com.ltech.smarthome.ui.config.ActNetConfig;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;
import com.ltech.smarthome.ui.device.camera.EzDevice;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;

/* loaded from: classes4.dex */
public class ActEzCameraSettingVM extends BaseDeviceSetViewModel {
    public long controlId;
    public long placeId;
    public SingleLiveEvent<Void> showFlipImageEvent = new SingleLiveEvent<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.camera.config.ActEzCameraSettingVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActEzCameraSettingVM.this.lambda$new$0((View) obj);
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
            case R.id.layout_flip_image /* 2131297475 */:
                this.showFlipImageEvent.call();
                break;
            case R.id.layout_set_network /* 2131297644 */:
                CameraParam cameraParam = (CameraParam) this.controlDevice.getValue().getParam(CameraParam.class);
                if (cameraParam != null && cameraParam.getConfigType() != 0) {
                    EZManager.instance().configType = cameraParam.getConfigType();
                } else {
                    EZManager.instance().configType = 1;
                }
                EZManager.instance().setEzDevice(new EzDevice(this.controlDevice.getValue().getDevicesn(), cameraParam.getVerifyCode(), ""));
                NavUtils.destination(ActNetConfig.class).withBoolean(Constants.SETTING_PAGE, true).withString(Constants.PRODUCT_ID, ProductId.ID_WIFI_CAMERA).withLong(Constants.CONTROL_ID, this.controlId).navigation(ActivityUtils.getTopActivity());
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