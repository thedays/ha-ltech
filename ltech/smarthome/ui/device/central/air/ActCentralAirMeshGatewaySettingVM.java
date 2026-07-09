package com.ltech.smarthome.ui.device.central.air;

import android.bluetooth.BluetoothDevice;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.blemesh.IRemoveNodeCallback;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.smart.message.MessageManager;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.xiaomi.mipush.sdk.Constants;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActCentralAirMeshGatewaySettingVM extends BaseDeviceSetViewModel {
    public long placeId;
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.central.air.ActCentralAirMeshGatewaySettingVM$$ExternalSyntheticLambda4
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActCentralAirMeshGatewaySettingVM.this.lambda$new$0((View) obj);
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

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel
    public void deleteDevice() {
        MessageManager.getInstance().setGetCentralAirSubData(null);
        if (isDeleteByMeshCmd()) {
            showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.removing));
            Injection.mesh().resetNodeByCmd(this.controlDevice.getValue(), new IAction() { // from class: com.ltech.smarthome.ui.device.central.air.ActCentralAirMeshGatewaySettingVM$$ExternalSyntheticLambda3
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActCentralAirMeshGatewaySettingVM.this.lambda$deleteDevice$1((Boolean) obj);
                }
            });
        } else {
            deleteDeviceFromNet();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteDevice$1(Boolean bool) {
        if (bool.booleanValue()) {
            dismissLoadingDialog();
            deleteDeviceFromNet();
        } else {
            dismissLoadingDialog();
            this.showForceDeleteDialogEvent.call();
        }
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel
    public void forceDelDevice() {
        if (isDeleteByMeshCmd()) {
            showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.removing));
            Injection.mesh().removeNode(((BleParam) this.controlDevice.getValue().getParam(BleParam.class)).getUnicastAddress(), new IRemoveNodeCallback() { // from class: com.ltech.smarthome.ui.device.central.air.ActCentralAirMeshGatewaySettingVM.1
                @Override // com.ltech.smarthome.blemesh.IRemoveNodeCallback
                public void removeSuccess() {
                    ActCentralAirMeshGatewaySettingVM.this.deleteDeviceFromNet();
                }

                @Override // com.ltech.smarthome.blemesh.IRemoveNodeCallback
                public void removeFail() {
                    ActCentralAirMeshGatewaySettingVM.this.dismissLoadingDialog();
                    SmartToast.showShort(R.string.remove_fail);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteDeviceFromNet() {
        Observable<Object> deleteDevice;
        LHomeLog.i(getClass(), "deleteDeviceFromNet() enter");
        if (isDeleteByMeshCmd()) {
            if (this.controlDevice.getValue() == null) {
                return;
            }
            BluetoothDevice connectedDevice = Injection.mesh().getConnectedDevice();
            if (connectedDevice != null && connectedDevice.getAddress().replaceAll(Constants.COLON_SEPARATOR, "").equals(this.controlDevice.getValue().getWifiMac())) {
                Injection.mesh().disconnect();
            }
        }
        if (this.controlDevice.getValue() == null) {
            return;
        }
        BleParam bleParam = (BleParam) this.controlDevice.getValue().getParam(BleParam.class);
        if (bleParam != null) {
            LHomeLog.i(getClass(), "bleParam != null && bleParam.getGroupId() > 0 delete device");
            deleteDevice = Injection.net().deleteDevice(this.controlDevice.getValue().getDeviceId(), bleParam.getGroupId());
        } else {
            LHomeLog.i(getClass(), "no bleParam, delete device");
            deleteDevice = Injection.net().deleteDevice(this.controlDevice.getValue().getDeviceId());
        }
        ((ObservableSubscribeProxy) deleteDevice.delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.central.air.ActCentralAirMeshGatewaySettingVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActCentralAirMeshGatewaySettingVM.this.lambda$deleteDeviceFromNet$2((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.central.air.ActCentralAirMeshGatewaySettingVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActCentralAirMeshGatewaySettingVM.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.central.air.ActCentralAirMeshGatewaySettingVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActCentralAirMeshGatewaySettingVM.this.lambda$deleteDeviceFromNet$3(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteDeviceFromNet$2(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.removing));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteDeviceFromNet$3(Object obj) throws Exception {
        Injection.repo().device().removeDeviceFromDb(this.controlDevice.getValue().getId());
        deleteSubCentral();
        setResult(5001);
        finishActivity();
    }

    private void deleteSubCentral() {
        for (Device device : Injection.repo().device().getDeviceListCache().getValue()) {
            if (device.getMacdeviceid() == this.controlDevice.getValue().getDeviceId()) {
                Injection.repo().device().removeDeviceFromDb(device.getId());
            }
        }
    }
}