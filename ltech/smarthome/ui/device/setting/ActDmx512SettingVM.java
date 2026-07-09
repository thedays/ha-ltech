package com.ltech.smarthome.ui.device.setting;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;
import com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewayLightSetting;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ActDmx512SettingVM extends BaseDeviceSetViewModel {
    public int channelNum;
    public long deviceId;
    public int firstAddress;
    public long placeId;
    public SingleLiveEvent<Void> showAddressDialogEvent = new SingleLiveEvent<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.setting.ActDmx512SettingVM$$ExternalSyntheticLambda1
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActDmx512SettingVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.layout_address /* 2131297351 */:
                break;
            case R.id.layout_atmosphere_lamp_setting /* 2131297355 */:
                NavUtils.destination(ActMeshGatewayLightSetting.class).withLong(Constants.CONTROL_ID, this.controlId).navigation(ActivityUtils.getTopActivity());
                return;
            case R.id.layout_change_icon /* 2131297387 */:
                this.showSelectDeviceIconDialogEvent.call();
                return;
            case R.id.layout_change_room /* 2131297392 */:
                this.showEditRoomDialogEvent.call();
                return;
            case R.id.layout_device_name /* 2131297433 */:
                this.showEditNameDialogEvent.call();
                return;
            case R.id.layout_upgrade /* 2131297687 */:
                this.upgradeEvent.call();
                break;
            case R.id.tv_delete_device /* 2131298576 */:
                this.showDeleteDialogEvent.call();
                return;
            default:
                return;
        }
        this.showAddressDialogEvent.call();
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel
    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    public void setChannel() {
        showLoadingDialog("");
        CmdAssistant.getLightCmdAssistant(this.controlDevice.getValue(), new int[0]).setDmxChannelValue(getContext(), this.firstAddress, this.channelNum, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.device.setting.ActDmx512SettingVM.1
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean aBoolean) {
                ActDmx512SettingVM.this.dismissLoadingDialog();
                if (aBoolean.booleanValue()) {
                    SmartToast.showCenterShort(ActDmx512SettingVM.this.getContext().getString(R.string.app_str_setting_success));
                    ActDmx512SettingVM.this.updateParamExt();
                } else {
                    SmartToast.showCenterShort(ActDmx512SettingVM.this.getContext().getString(R.string.app_str_setting_failed));
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateParamExt() {
        final Device value = this.controlDevice.getValue();
        try {
            JSONObject jSONObject = value.getExtParam() != null ? new JSONObject(value.getExtParam()) : new JSONObject();
            jSONObject.put("channelHead", this.firstAddress);
            jSONObject.put("channelCount", this.channelNum);
            value.setExtParam(jSONObject.toString());
            ((ObservableSubscribeProxy) Injection.net().updateParamExt(value.getDeviceId(), jSONObject.toString()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.setting.ActDmx512SettingVM$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    Injection.repo().device().saveDevice(Device.this);
                }
            }, new SmartErrorComsumer());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}