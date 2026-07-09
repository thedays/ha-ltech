package com.ltech.smarthome.ui.device.setting;

import android.view.View;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;
import com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewayLightSetting;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.ResponseMsg;

/* loaded from: classes4.dex */
public class ActBleTrigSceneSettingVM extends BaseDeviceSetViewModel {
    public long deviceId;
    public long placeId;
    public boolean samePlace;
    public SingleLiveEvent<Void> showControlModeDialogEvent = new SingleLiveEvent<>();
    public MutableLiveData<Integer> showControlMode = new MutableLiveData<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.setting.ActBleTrigSceneSettingVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActBleTrigSceneSettingVM.this.lambda$new$0((View) obj);
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
            case R.id.layout_control_mode /* 2131297403 */:
                this.showControlModeDialogEvent.call();
                break;
            case R.id.layout_device_name /* 2131297433 */:
                this.showEditNameDialogEvent.call();
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

    public void setControlMode(final int mode) {
        CmdAssistant.getDeviceAssistant(this.controlDevice.getValue(), new int[0]).channelControlMode(ActivityUtils.getTopActivity(), mode, new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.setting.ActBleTrigSceneSettingVM.1
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                if (responseMsg != null) {
                    SmartToast.showShort(R.string.save_success);
                    ReplaceHelper.instance().backupData(ActBleTrigSceneSettingVM.this.getLifecycleOwner(), ActBleTrigSceneSettingVM.this.controlDevice.getValue().getDeviceId(), UpdateBackDataRequest.DRY_CONTACT_SIGNAL_CONTROL, CmdAssistant.getDeviceAssistant(ActBleTrigSceneSettingVM.this.controlDevice.getValue(), new int[0]).channelControlMode(mode));
                } else {
                    SmartToast.showShort(R.string.save_fail);
                }
            }
        });
    }

    public void checkControlMode() {
        CmdAssistant.getDeviceAssistant(this.controlDevice.getValue(), new int[0]).queryControlMode(ActivityUtils.getTopActivity(), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.setting.ActBleTrigSceneSettingVM.2
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                if (responseMsg != null) {
                    ActBleTrigSceneSettingVM.this.showControlMode.setValue(Integer.valueOf(ActBleTrigSceneSettingVM.this.convertMode(responseMsg.getResData())));
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int convertMode(String resData) {
        if (resData.length() == 18) {
            return Integer.parseInt(resData.substring(16, 18), 16);
        }
        return 1;
    }
}