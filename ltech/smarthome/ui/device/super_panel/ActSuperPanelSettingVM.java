package com.ltech.smarthome.ui.device.super_panel;

import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.iflytek.home.sdk.IFlyHome;
import com.iflytek.home.sdk.callback.ResponseCallback;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.device_param.SuperPanelExtParam;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.super_panel.IFlyDeviceDetail;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Response;

/* loaded from: classes4.dex */
public class ActSuperPanelSettingVM extends BaseDeviceSetViewModel {
    public long controlId;
    public String lastFirmwareVersion;
    public String lastMcuVersion;
    public MutableLiveData<Boolean> isContinous_mode = new MutableLiveData<>();
    public MutableLiveData<Boolean> isDirectVoice = new MutableLiveData<>();
    public MutableLiveData<Boolean> isNearbyWakeup = new MutableLiveData<>();
    public MutableLiveData<Boolean> engravedText = new MutableLiveData<>();
    public MutableLiveData<Boolean> nightMode = new MutableLiveData<>();
    public MutableLiveData<Boolean> memorizePowerOff = new MutableLiveData<>();
    public MutableLiveData<Boolean> isChinaNode = new MutableLiveData<>();
    public SingleLiveEvent<Void> setVoiceControlRangeEvent = new SingleLiveEvent<>();
    public MutableLiveData<Boolean> autoUpgrade = new MutableLiveData<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSettingVM$$ExternalSyntheticLambda11
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActSuperPanelSettingVM.this.lambda$new$0((View) obj);
        }
    });

    public MutableLiveData<Boolean> getIsContinous_mode() {
        return this.isContinous_mode;
    }

    public void getDeviceDetail(String deviceID) {
        IFlyHome.INSTANCE.getDeviceDetail("000d9d32-b027-410d-981d-fead0b8cbddd." + deviceID, new ResponseCallback() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSettingVM.1
            @Override // com.iflytek.home.sdk.callback.ResponseCallback
            public void onFailure(Call<String> call, Throwable throwable) {
            }

            @Override // com.iflytek.home.sdk.callback.ResponseCallback
            public void onResponse(Response<String> response) {
                if (response.isSuccessful()) {
                    ActSuperPanelSettingVM.this.isContinous_mode.setValue(Boolean.valueOf(((IFlyDeviceDetail) GsonUtils.getGson().fromJson(response.body(), IFlyDeviceDetail.class)).isContinous_mode()));
                } else {
                    ActSuperPanelSettingVM.this.isContinous_mode.setValue(false);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.layout_change_room /* 2131297392 */:
                this.showEditRoomDialogEvent.call();
                break;
            case R.id.layout_device_mcu /* 2131297432 */:
                navigation(NavUtils.destination(ActChildMcuUpgrade.class).withLong(Constants.PLACE_ID, getCurPlace().getPlaceId()).withLong(Constants.CONTROL_ID, this.controlDevice.getValue().getId()).withDefaultRequestCode());
                break;
            case R.id.layout_device_name /* 2131297433 */:
                this.showEditNameDialogEvent.call();
                break;
            case R.id.layout_direct_voice /* 2131297444 */:
                navigation(NavUtils.destination(ActDirectVoice.class).withLong(Constants.CONTROL_ID, this.controlDevice.getValue().getId()).withBoolean("isDirectVoice", this.isDirectVoice.getValue().booleanValue()).withDefaultRequestCode());
                break;
            case R.id.layout_nearby_wakeup /* 2131297552 */:
                navigation(NavUtils.destination(ActNearbyWakeup.class).withLong(Constants.CONTROL_ID, this.controlDevice.getValue().getId()).withBoolean("isNearbyWakeup", this.isNearbyWakeup.getValue().booleanValue()).withDefaultRequestCode());
                break;
            case R.id.layout_talk /* 2131297675 */:
                navigation(NavUtils.destination(ActContinousTalk.class).withLong(Constants.CONTROL_ID, this.controlDevice.getValue().getId()).withBoolean("continous", this.isContinous_mode.getValue().booleanValue()).withString("deviceSN", this.controlDevice.getValue().getDevicesn()).withDefaultRequestCode());
                break;
            case R.id.layout_voice_control_range /* 2131297691 */:
                this.setVoiceControlRangeEvent.call();
                break;
            case R.id.layout_wake_up /* 2131297693 */:
                navigation(NavUtils.destination(ActWakeUpWords.class).withLong(Constants.CONTROL_ID, this.controlDevice.getValue().getId()));
                break;
            case R.id.tv_delete_device /* 2131298576 */:
                this.showDeleteDialogEvent.call();
                break;
        }
    }

    public void updateSuperPanelExt(final SuperPanelExtParam extParam) {
        try {
            JSONObject jSONObject = new JSONObject(extParam.getParamString());
            if (TextUtils.isEmpty(extParam.getSwitch1_name())) {
                jSONObject.remove("switch1_name");
            }
            if (TextUtils.isEmpty(extParam.getSwitch2_name())) {
                jSONObject.remove("switch2_name");
            }
            if (TextUtils.isEmpty(extParam.getSwitch3_name())) {
                jSONObject.remove("switch3_name");
            }
            if (TextUtils.isEmpty(extParam.getSwitch4_name())) {
                jSONObject.remove("switch4_name");
            }
            ((ObservableSubscribeProxy) Injection.net().updateParamExt(this.controlDevice.getValue().getDeviceId(), jSONObject.toString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSettingVM$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActSuperPanelSettingVM.this.lambda$updateSuperPanelExt$1((Disposable) obj);
                }
            }).doFinally(new ActSuperPanelSettingVM$$ExternalSyntheticLambda3(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSettingVM$$ExternalSyntheticLambda4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActSuperPanelSettingVM.this.lambda$updateSuperPanelExt$2(extParam, obj);
                }
            }, new SmartErrorComsumer());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateSuperPanelExt$1(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateSuperPanelExt$2(SuperPanelExtParam superPanelExtParam, Object obj) throws Exception {
        this.controlDevice.getValue().setExtParam(superPanelExtParam.getParamString());
        Injection.repo().device().saveDevice(this.controlDevice.getValue());
        SmartToast.showShort(R.string.save_success);
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel
    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    public void firmwareUpdate() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("CharSwitch", "66BB00C00000F700020103EB");
            ((ObservableSubscribeProxy) Injection.net().deviceController(this.controlDevice.getValue().getDeviceId(), jSONObject.toString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSettingVM$$ExternalSyntheticLambda9
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActSuperPanelSettingVM.this.lambda$firmwareUpdate$3((Disposable) obj);
                }
            }).doFinally(new ActSuperPanelSettingVM$$ExternalSyntheticLambda3(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSettingVM$$ExternalSyntheticLambda10
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SmartToast.showShort(R.string.encrypt_password_open_success);
                }
            }, new SmartErrorComsumer());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$firmwareUpdate$3(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.loading));
    }

    public void appUpdate() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("CharSwitch", "66BB00C00000F700020102EB");
            ((ObservableSubscribeProxy) Injection.net().deviceController(this.controlDevice.getValue().getDeviceId(), jSONObject.toString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSettingVM$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActSuperPanelSettingVM.this.lambda$appUpdate$5((Disposable) obj);
                }
            }).doFinally(new ActSuperPanelSettingVM$$ExternalSyntheticLambda3(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSettingVM$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SmartToast.showShort(R.string.encrypt_password_open_success);
                }
            }, new SmartErrorComsumer());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$appUpdate$5(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.loading));
    }

    public void uploadLog() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("CharSwitch", "66BB00000000FC0100EB");
            ((ObservableSubscribeProxy) Injection.net().deviceController(this.controlDevice.getValue().getDeviceId(), jSONObject.toString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSettingVM$$ExternalSyntheticLambda7
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActSuperPanelSettingVM.this.lambda$uploadLog$7((Disposable) obj);
                }
            }).doFinally(new ActSuperPanelSettingVM$$ExternalSyntheticLambda3(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSettingVM$$ExternalSyntheticLambda8
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SmartToast.showShort(R.string.encrypt_password_open_success);
                }
            }, new SmartErrorComsumer());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadLog$7(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.loading));
    }

    public void reboot() {
        try {
            new JSONObject().put("CharSwitch", "66BB00C00000F700020101EB");
            ((ObservableSubscribeProxy) Injection.net().deviceController(this.controlDevice.getValue().getDeviceId(), "\"CharSwitch\":\"66BB00C00000F700020101EB\"").compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSettingVM$$ExternalSyntheticLambda5
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActSuperPanelSettingVM.this.lambda$reboot$9((Disposable) obj);
                }
            }).doFinally(new ActSuperPanelSettingVM$$ExternalSyntheticLambda3(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSettingVM$$ExternalSyntheticLambda6
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SmartToast.showShort(R.string.encrypt_password_open_success);
                }
            }, new SmartErrorComsumer());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reboot$9(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.loading));
    }
}