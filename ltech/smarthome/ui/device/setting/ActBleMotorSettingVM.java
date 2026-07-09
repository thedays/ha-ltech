package com.ltech.smarthome.ui.device.setting;

import android.view.View;
import androidx.lifecycle.MutableLiveData;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.ResponseMsg;

/* loaded from: classes4.dex */
public class ActBleMotorSettingVM extends BaseDeviceSetViewModel {
    public long deviceId;
    public long placeId;
    public String productId;
    public boolean samePlace;
    public SingleLiveEvent<Void> showChangeMotorDirectionDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showMoreSettingDialogEvent = new SingleLiveEvent<>();
    public MutableLiveData<String> motorVersion = new MutableLiveData<>();
    public Boolean isFirstGetMotorVersion = true;
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.setting.ActBleMotorSettingVM$$ExternalSyntheticLambda1
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActBleMotorSettingVM.this.lambda$new$0((View) obj);
        }
    });

    public static class ModeInfoItem {
        public String actionString;
        public int iconIndex;
        public int modeIcon;
        public String modeName;
        public int type;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.btn_motor_direction /* 2131296495 */:
                this.showChangeMotorDirectionDialogEvent.call();
                break;
            case R.id.layout_change_room /* 2131297392 */:
                this.showEditRoomDialogEvent.call();
                break;
            case R.id.layout_create_group /* 2131297408 */:
                this.showCreateGroupDialogEvent.call();
                break;
            case R.id.layout_device_name /* 2131297433 */:
                this.showEditNameDialogEvent.call();
                break;
            case R.id.layout_more_settings /* 2131297544 */:
                this.showMoreSettingDialogEvent.call();
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

    public void motorCheckVersion() {
        String string;
        final Device value = this.controlDevice.getValue();
        if (value == null || !ProductRepository.isBLeDevice(value.getProductId())) {
            return;
        }
        if (value.getExtParam() != null && (string = JSONObject.parseObject(value.getExtParam()).getString("motorVersion")) != null) {
            this.motorVersion.setValue(string);
        }
        CmdAssistant.getQueryCmdAssistant(this.controlDevice.getValue(), new int[0]).queryMotorVersion(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.setting.ActBleMotorSettingVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActBleMotorSettingVM.this.lambda$motorCheckVersion$1(value, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$motorCheckVersion$1(Device device, ResponseMsg responseMsg) {
        if (responseMsg == null) {
            this.newVersion.setValue(false);
            return;
        }
        if (responseMsg.getResData().length() > 20) {
            String substring = responseMsg.getResData().substring(16, 22);
            String str = Integer.parseInt(substring.substring(0, 2)) + "." + Integer.parseInt(substring.substring(2, 4)) + "." + Integer.parseInt(substring.substring(4, 6));
            JSONObject parseObject = device.getExtParam() != null ? JSONObject.parseObject(device.getExtParam()) : new JSONObject();
            parseObject.put("motorVersion", (Object) str);
            updateParamExt(device, parseObject);
            this.isFirstGetMotorVersion = false;
        }
    }
}