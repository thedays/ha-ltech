package com.ltech.smarthome.ui.device.setting;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;
import com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewayLightSetting;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSonosSettingDefaultVM extends BaseDeviceSetViewModel {
    public long deviceId;
    public long placeId;
    public boolean samePlace;
    public MutableLiveData<Boolean> isCrossFade = new MutableLiveData<>(false);
    public MutableLiveData<Integer> playModeEvent = new MutableLiveData<>(0);
    public MutableLiveData<Boolean> canCrossfade = new MutableLiveData<>(false);
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.setting.ActSonosSettingDefaultVM$$ExternalSyntheticLambda2
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActSonosSettingDefaultVM.this.lambda$new$0((View) obj);
        }
    });

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel
    public boolean isDeleteByMeshCmd() {
        return false;
    }

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

    public void setMode() {
        boolean z;
        boolean z2;
        boolean z3;
        if (this.playModeEvent.getValue() != null) {
            boolean z4 = this.playModeEvent.getValue().intValue() == 1;
            boolean z5 = this.playModeEvent.getValue().intValue() == 2;
            z = z4;
            z3 = this.playModeEvent.getValue().intValue() == 3;
            z2 = z5;
        } else {
            z = false;
            z2 = false;
            z3 = false;
        }
        ((ObservableSubscribeProxy) Injection.net().sonosMode(this.deviceId, Injection.repo().home().getSelPlace().getPlaceId(), z, z2, z3, this.isCrossFade.getValue() != null ? this.isCrossFade.getValue().booleanValue() : false).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.setting.ActSonosSettingDefaultVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSonosSettingDefaultVM.this.lambda$setMode$1((Objects) obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.device.setting.ActSonosSettingDefaultVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSonosSettingDefaultVM.this.lambda$setMode$2((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setMode$1(Objects objects) throws Exception {
        dismissLoadingDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setMode$2(Throwable th) throws Exception {
        SmartToast.showShort(StringUtils.getString(R.string.app_str_setting_failed));
        dismissLoadingDialog();
    }
}