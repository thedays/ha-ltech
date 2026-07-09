package com.ltech.smarthome.ui.device.dalipro;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.CgdProLightExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActDaliLightSettingVM extends BaseViewModel {
    public int ctHighLight;
    public int ctLowLight;
    public int dimFadeTime;
    public int dimFadeTimeCount;
    public int dimFadeTimePosition;
    public int failBrt;
    public int failWy;
    public int lightOnBrt;
    public int lightOnWy;
    public int lightType;
    public int maxK;
    public int minK;
    public int rgbHighLight;
    public int rgbLowLight;
    public int zoneNum;
    public int dimCurveType = 1;
    public int lightOnColor = Integer.MIN_VALUE;
    public int failColor = Integer.MIN_VALUE;
    public MutableLiveData<Boolean> isAddToRoom = new MutableLiveData<>();
    public MutableLiveData<String> deviceName = new MutableLiveData<>("");
    public MutableLiveData<String> dimmingCurve = new MutableLiveData<>("");
    public MutableLiveData<String> dimmingRange = new MutableLiveData<>("");
    public MutableLiveData<String> dimmingFadeTime = new MutableLiveData<>("");
    public MutableLiveData<String> ctRange = new MutableLiveData<>("");
    public MutableLiveData<String> lightOnState = new MutableLiveData<>("");
    public MutableLiveData<String> failureState = new MutableLiveData<>("");
    public SingleLiveEvent<Void> showEditNameDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> changeRoomEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> changeIconEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> dimmingFadeTimeEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> dimmingRangeTimeEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> kRangeEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> dimmingCurveTimeEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> lightOnStateEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> failureStateEvent = new SingleLiveEvent<>();
    public MutableLiveData<Device> controlDevice = new MutableLiveData<>();
    public MutableLiveData<Device> parentDevice = new MutableLiveData<>();
    public BindingCommand<View> clickCommand = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSettingVM$$ExternalSyntheticLambda5
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActDaliLightSettingVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.layout_change_icon /* 2131297387 */:
                this.changeIconEvent.call();
                break;
            case R.id.layout_change_room /* 2131297392 */:
                this.changeRoomEvent.call();
                break;
            case R.id.layout_ct_range /* 2131297411 */:
                this.kRangeEvent.call();
                break;
            case R.id.layout_dimming_curve /* 2131297441 */:
                this.dimmingCurveTimeEvent.call();
                break;
            case R.id.layout_dimming_fade_time /* 2131297442 */:
                this.dimmingFadeTimeEvent.call();
                break;
            case R.id.layout_dimming_range /* 2131297443 */:
                this.dimmingRangeTimeEvent.call();
                break;
            case R.id.layout_failure_state /* 2131297473 */:
                this.failureStateEvent.call();
                break;
            case R.id.layout_light_on_state /* 2131297519 */:
                this.lightOnStateEvent.call();
                break;
            case R.id.layout_scene_name /* 2131297615 */:
                this.showEditNameDialogEvent.call();
                break;
        }
    }

    public void changeDeviceName(final String name) {
        final Device value = this.controlDevice.getValue();
        ((ObservableSubscribeProxy) Injection.net().updateDeviceName(value.getDeviceId(), name).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSettingVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActDaliLightSettingVM.this.lambda$changeDeviceName$1((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActDaliLightSettingVM$$ExternalSyntheticLambda1(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSettingVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActDaliLightSettingVM.this.lambda$changeDeviceName$2(value, name, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeDeviceName$1(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeDeviceName$2(Device device, String str, Object obj) throws Exception {
        device.setDeviceName(str);
        Injection.repo().device().saveDevice(device);
        this.controlDevice.setValue(device);
        SmartToast.showShort(R.string.save_success);
    }

    public void changeDevicePlace(final long floorId, final long roomId) {
        final Device value = this.controlDevice.getValue();
        final String floorName = Injection.repo().home().getFloor(floorId).getFloorName();
        final String roomName = Injection.repo().home().getRoom(roomId).getRoomName();
        ((ObservableSubscribeProxy) Injection.net().updateDevicePlace(value.getDeviceId(), roomId, value != null && (ProductId.ID_BLE_HAM.equals(value.getProductId()) || ProductId.ID_MESH_GATEWAY.equals(value.getProductId()))).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSettingVM$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActDaliLightSettingVM.this.lambda$changeDevicePlace$3((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActDaliLightSettingVM$$ExternalSyntheticLambda1(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSettingVM$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActDaliLightSettingVM.this.lambda$changeDevicePlace$4(value, floorId, roomId, floorName, roomName, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeDevicePlace$3(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeDevicePlace$4(Device device, long j, long j2, String str, String str2, Object obj) throws Exception {
        if (ProductId.ID_BLE_HAM.equals(device.getProductId()) || ProductId.ID_MESH_GATEWAY.equals(device.getProductId())) {
            ArrayList arrayList = new ArrayList();
            for (Device device2 : Injection.repo().device().getDeviceListByPlaceId(device.getPlaceId())) {
                if (device2.getMacdeviceid() == device.getDeviceId()) {
                    device2.setFloorId(j);
                    device2.setRoomId(j2);
                    arrayList.add(device2);
                }
            }
            device.setFloorId(j);
            device.setRoomId(j2);
            device.setFloorName(str);
            device.setRoomName(str2);
            arrayList.add(device);
            Injection.repo().device().saveDevice(arrayList);
            this.controlDevice.setValue(device);
        } else {
            device.setFloorId(j);
            device.setRoomId(j2);
            device.setFloorName(str);
            device.setRoomName(str2);
            Injection.repo().device().saveDevice(device);
            this.controlDevice.setValue(device);
        }
        SmartToast.showShort(R.string.save_success);
    }

    public void setAddToSmart(boolean z) {
        final CgdProLightExtParam cgdProLightExtParam = (CgdProLightExtParam) this.controlDevice.getValue().getExtParam(CgdProLightExtParam.class);
        cgdProLightExtParam.setDaliHidden(!z ? 1 : 0);
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(this.controlDevice.getValue().getDeviceId(), GsonUtils.toJson(cgdProLightExtParam)).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSettingVM$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActDaliLightSettingVM.this.lambda$setAddToSmart$5((Disposable) obj);
            }
        }).doFinally(new ActDaliLightSettingVM$$ExternalSyntheticLambda1(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightSettingVM$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActDaliLightSettingVM.this.lambda$setAddToSmart$6(cgdProLightExtParam, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setAddToSmart$5(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setAddToSmart$6(CgdProLightExtParam cgdProLightExtParam, Object obj) throws Exception {
        this.controlDevice.getValue().setExtParam(GsonUtils.toJson(cgdProLightExtParam));
        Injection.repo().device().saveDevice(this.controlDevice.getValue());
        MutableLiveData<Device> mutableLiveData = this.controlDevice;
        mutableLiveData.setValue(mutableLiveData.getValue());
        showSuccessTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_success));
    }
}