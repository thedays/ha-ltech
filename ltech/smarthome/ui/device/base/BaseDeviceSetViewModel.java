package com.ltech.smarthome.ui.device.base;

import android.bluetooth.BluetoothDevice;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.aispeech.dca.Callback2;
import com.aispeech.dca.DcaSdk;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.JSONLexer;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.blemesh.IRemoveNodeCallback;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.net.response.automation.ListAutomationResponse;
import com.ltech.smarthome.net.response.device.GetReplaceDataResponse;
import com.ltech.smarthome.net.response.scene.ListSceneResponse;
import com.ltech.smarthome.nfc.ActNfcRestore;
import com.ltech.smarthome.ui.control.ActIntelligence;
import com.ltech.smarthome.ui.device.light.PowerState;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.ltech.smarthome.ui.log.ActLocalDeviceLog;
import com.ltech.smarthome.ui.replace.ActDeviceReplace;
import com.ltech.smarthome.ui.replace.ActSuperPanelReplace;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.upgrade.UpgradeFactory;
import com.ltech.smarthome.upgrade.UpgradeInfo;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RoomPickHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.LightAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.ltech.smarthome.view.dialog.SelectDimRangeDialog;
import com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import com.smart.message.utils.StringUtils;
import com.smart.product_agreement.bean.ProductVersionInfo;
import com.smart.product_agreement.param.SettingCmdParam;
import com.smart.product_agreement.parser.IUpgradeParser;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.xiaomi.mipush.sdk.Constants;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class BaseDeviceSetViewModel extends BaseViewModel {
    public long controlId;
    public long deviceId;
    public SelectPowerOnStateDialog powerOnStateDialog;
    private int tvAutomationNum;
    private int tvSceneNum;
    public MutableLiveData<Device> controlDevice = new MutableLiveData<>();
    public MutableLiveData<Group> controlGroup = new MutableLiveData<>();
    public SingleLiveEvent<Void> upgradeEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showDeleteDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showEditNameDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showEditRoomDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showForceDeleteDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showSelectDeviceIconDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showCreateGroupDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showPowerStateDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> refreshEvent = new SingleLiveEvent<>();
    public RoomPickHelper roomPickHelper = new RoomPickHelper();
    public MutableLiveData<String> productName = new MutableLiveData<>("");
    public MutableLiveData<Boolean> hasProductName = new MutableLiveData<>(false);
    public MutableLiveData<String> outputType = new MutableLiveData<>("");
    public MutableLiveData<Boolean> hasOutputType = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> newVersion = new MutableLiveData<>();
    public MutableLiveData<Boolean> newMucVersion = new MutableLiveData<>();
    public MutableLiveData<String> version = new MutableLiveData<>();
    public MutableLiveData<String> mcuversion = new MutableLiveData<>();
    public MutableLiveData<String> firmwareversion = new MutableLiveData<>();
    public MutableLiveData<Boolean> nextIcon = new MutableLiveData<>();
    public MutableLiveData<Boolean> nameChangeSuccess = new MutableLiveData<>();
    public MutableLiveData<JSONObject> backDataResult = new MutableLiveData<>();
    public MutableLiveData<PowerState> onState = new MutableLiveData<>();
    public MutableLiveData<Integer> sensitivity = new MutableLiveData<>(0);
    public int[] orderArray = {1, 2, 3};
    public MutableLiveData<Boolean> doubleMemorize = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> indicator = new MutableLiveData<>(false);
    public SingleLiveEvent<Void> getBatteryEvent = new SingleLiveEvent<>();
    public MutableLiveData<String> dimmingRange = new MutableLiveData<>("");
    public int[] brtRange = {1, 255};
    public MutableLiveData<Integer> getSceneAutomationOver = new MutableLiveData<>(0);
    public MutableLiveData<Boolean> showLog = new MutableLiveData<>();
    public BindingCommand<View> commonClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel$$ExternalSyntheticLambda2
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            BaseDeviceSetViewModel.this.lambda$new$12((View) obj);
        }
    });

    public void changeDeviceName(final String name) {
        final Device value = this.controlDevice.getValue();
        ((ObservableSubscribeProxy) Injection.net().updateDeviceName(value.getDeviceId(), name).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel$$ExternalSyntheticLambda17
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseDeviceSetViewModel.this.lambda$changeDeviceName$0((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new BaseDeviceSetViewModel$$ExternalSyntheticLambda18(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel$$ExternalSyntheticLambda19
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseDeviceSetViewModel.this.lambda$changeDeviceName$1(value, name, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeDeviceName$0(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeDeviceName$1(Device device, String str, Object obj) throws Exception {
        device.setDeviceName(str);
        Injection.repo().device().saveDevice(device);
        this.controlDevice.setValue(device);
        SmartToast.showShort(R.string.save_success);
        this.nameChangeSuccess.setValue(true);
    }

    public void changeDevicePlace(final long floorId, final long roomId) {
        final Device value = this.controlDevice.getValue();
        final String floorName = Injection.repo().home().getFloor(floorId).getFloorName();
        final String roomName = Injection.repo().home().getRoom(roomId).getRoomName();
        ((ObservableSubscribeProxy) Injection.net().updateDevicePlace(value.getDeviceId(), roomId, value != null && (ProductId.ID_BLE_HAM.equals(value.getProductId()) || ProductId.ID_MESH_GATEWAY.equals(value.getProductId()))).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseDeviceSetViewModel.this.lambda$changeDevicePlace$2((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new BaseDeviceSetViewModel$$ExternalSyntheticLambda18(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseDeviceSetViewModel.this.lambda$changeDevicePlace$3(value, floorId, roomId, floorName, roomName, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeDevicePlace$2(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeDevicePlace$3(Device device, long j, long j2, String str, String str2, Object obj) throws Exception {
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
            if (RelaySeparationHelper.isRelaySeparationDevice(device)) {
                List<Device> subDevice = Injection.repo().device().getSubDevice(device.getPlaceId(), device.getDeviceId());
                for (Device device3 : subDevice) {
                    if (device3.getFloorId() == device.getFloorId() && device3.getRoomId() == device.getRoomId()) {
                        device3.setFloorId(j);
                        device3.setRoomId(j2);
                        device3.setFloorName(str);
                        device3.setRoomName(str2);
                    }
                }
                Injection.repo().device().saveDevice(subDevice);
            }
            device.setFloorId(j);
            device.setRoomId(j2);
            device.setFloorName(str);
            device.setRoomName(str2);
            Injection.repo().device().saveDevice(device);
            this.controlDevice.setValue(device);
        }
        SmartToast.showShort(R.string.save_success);
    }

    public void deleteDevice() {
        if (isDeleteByMeshCmd()) {
            showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.removing));
            Injection.mesh().resetNodeByCmd(this.controlDevice.getValue(), new IAction() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel$$ExternalSyntheticLambda15
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    BaseDeviceSetViewModel.this.lambda$deleteDevice$4((Boolean) obj);
                }
            });
        } else {
            deleteDeviceFromNet();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteDevice$4(Boolean bool) {
        if (bool.booleanValue()) {
            dismissLoadingDialog();
            deleteDeviceFromNet();
        } else {
            dismissLoadingDialog();
            this.showForceDeleteDialogEvent.call();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteDeviceFromNet() {
        Observable<Object> deleteDevice;
        LHomeLog.i(getClass(), "deleteDeviceFromNet() enter");
        if (isDeleteByMeshCmd()) {
            if (this.controlDevice.getValue() == null) {
                dismissLoadingDialog();
                return;
            }
            BluetoothDevice connectedDevice = Injection.mesh().getConnectedDevice();
            if (connectedDevice != null && connectedDevice.getAddress().replaceAll(Constants.COLON_SEPARATOR, "").equals(this.controlDevice.getValue().getWifiMac())) {
                Injection.mesh().disconnect();
            }
        }
        if (this.controlDevice.getValue() == null) {
            dismissLoadingDialog();
            return;
        }
        final Device value = this.controlDevice.getValue();
        BleParam bleParam = (BleParam) value.getParam(BleParam.class);
        if (bleParam != null) {
            if (bleParam.getPublicationAddress() != 0) {
                if (ProductRepository.isEurPanel(value.getProductId()) && bleParam.getPublicationId() > 0) {
                    deleteDevice = Injection.net().deleteDevice(value.getDeviceId(), bleParam.getPublicationId());
                } else if (ProductRepository.isAsPanel(value.getProductId()) && bleParam.getPublicationId() > 0) {
                    deleteDevice = Injection.net().deleteDevice(value.getDeviceId(), bleParam.getPublicationId());
                } else if (bleParam.getGroupId() > 0) {
                    deleteDevice = Injection.net().deleteDevice(value.getDeviceId(), bleParam.getGroupId());
                } else {
                    deleteDevice = Injection.net().deleteDevice(value.getDeviceId());
                }
            } else {
                deleteDevice = Injection.net().deleteDevice(value.getDeviceId());
            }
        } else {
            deleteDevice = Injection.net().deleteDevice(value.getDeviceId());
        }
        if (deleteDevice != null) {
            ((ObservableSubscribeProxy) deleteDevice.delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel$$ExternalSyntheticLambda20
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    BaseDeviceSetViewModel.this.lambda$deleteDeviceFromNet$5((Disposable) obj);
                }
            }).compose(RxUtils.io_main()).doFinally(new BaseDeviceSetViewModel$$ExternalSyntheticLambda18(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    BaseDeviceSetViewModel.this.lambda$deleteDeviceFromNet$6(value, obj);
                }
            }, new SmartErrorComsumer() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel.1
                @Override // com.ltech.smarthome.net.SmartErrorComsumer
                protected void action(Throwable throwable) {
                    SmartToast.showShort(this.errorMessage);
                    BaseDeviceSetViewModel.this.dismissLoadingDialog();
                }
            });
        } else {
            dismissLoadingDialog();
        }
        unSubscribePublicationAddress(value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteDeviceFromNet$5(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.removing));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteDeviceFromNet$6(Device device, Object obj) throws Exception {
        dismissLoadingDialog();
        SharedPreferenceUtil.edit().keepShared(com.ltech.smarthome.utils.Constants.DEVICE_CHANGED, true);
        Injection.repo().device().removeDeviceFromDb(device.getId());
        removeGroupDeviceId(device.getDeviceId());
        RelaySeparationHelper.removeSub(device);
        LHomeLog.i(getClass(), "delete device--->" + device);
        if (ProductRepository.isDcaSuperPanel(device.getProductId())) {
            DcaSdk.getDeviceManager().unbindDevice(new Callback2(this) { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel.2
                @Override // com.aispeech.dca.Callback2
                public void onFailure(int i, String s) {
                }

                @Override // com.aispeech.dca.Callback2
                public void onSuccess() {
                }
            });
        }
        if (device.getProductId().equals(ProductId.ID_BLE_LIGHT_CGD_PRO)) {
            Injection.repo().device().removeDaliSubContentByDeviceId(device.getPlaceId(), device.getDeviceId());
            SharedPreferenceUtil.edit().keepShared(com.ltech.smarthome.utils.Constants.DEVICE_CHANGED, false);
        }
        setResult(5001);
        finishActivity();
    }

    private void removeGroupDeviceId(long deviceId) {
        for (Group group : Injection.repo().group().getGroupListByDeviceId(getCurPlace().getPlaceId(), deviceId)) {
            int i = 0;
            while (true) {
                if (i >= group.getDeviceIds().size()) {
                    break;
                }
                if (group.getDeviceIds().get(i).longValue() == deviceId) {
                    group.getDeviceIds().remove(i);
                    Injection.repo().group().saveGroup(group);
                    break;
                }
                i++;
            }
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private void unSubscribePublicationAddress(Device device) {
        LHomeLog.i(getClass(), "delete device--->" + this.controlDevice.getValue());
        String productId = device.getProductId();
        productId.hashCode();
        char c2 = 65535;
        switch (productId.hashCode()) {
            case -1819630261:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1_PRO)) {
                    c2 = 0;
                    break;
                }
                break;
            case -1817691924:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2_PRO)) {
                    c2 = 1;
                    break;
                }
                break;
            case -1805322688:
                if (productId.equals(ProductId.ID_BLE_LIGHT_DIM)) {
                    c2 = 2;
                    break;
                }
                break;
            case -1805199680:
                if (productId.equals(ProductId.ID_BLE_LIGHT_CT)) {
                    c2 = 3;
                    break;
                }
                break;
            case -1804340546:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGB)) {
                    c2 = 4;
                    break;
                }
                break;
            case -1804278081:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGBW)) {
                    c2 = 5;
                    break;
                }
                break;
            case -1803448738:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGBWY)) {
                    c2 = 6;
                    break;
                }
                break;
            case -1796419228:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3_PRO)) {
                    c2 = 7;
                    break;
                }
                break;
            case -1710907378:
                if (productId.equals(ProductId.ID_BLE_KBS)) {
                    c2 = '\b';
                    break;
                }
                break;
            case -1287620343:
                if (productId.equals(ProductId.ID_BLE_CURTAIN_CG_CURH3)) {
                    c2 = '\t';
                    break;
                }
                break;
            case -1084555505:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S6_PRO)) {
                    c2 = '\n';
                    break;
                }
                break;
            case -1082613022:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S6)) {
                    c2 = 11;
                    break;
                }
                break;
            case -969622016:
                if (productId.equals(ProductId.ID_SMART_PANEL_G4_PRO)) {
                    c2 = '\f';
                    break;
                }
                break;
            case -852623517:
                if (productId.equals(ProductId.ID_RC4S)) {
                    c2 = '\r';
                    break;
                }
                break;
            case -835060954:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1C)) {
                    c2 = 14;
                    break;
                }
                break;
            case -732569219:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S4)) {
                    c2 = 15;
                    break;
                }
                break;
            case -208296259:
                if (productId.equals(ProductId.ID_RC4)) {
                    c2 = 16;
                    break;
                }
                break;
            case -207348713:
                if (productId.equals(ProductId.ID_KEY_SWITCH_1)) {
                    c2 = 17;
                    break;
                }
                break;
            case -206567420:
                if (productId.equals(ProductId.ID_KEY_SWITCH_2)) {
                    c2 = 18;
                    break;
                }
                break;
            case -206510721:
                if (productId.equals(ProductId.ID_KEY_SWITCH_3)) {
                    c2 = 19;
                    break;
                }
                break;
            case -206454022:
                if (productId.equals(ProductId.ID_KEY_SWITCH_4)) {
                    c2 = 20;
                    break;
                }
                break;
            case 166485422:
                if (productId.equals(ProductId.ID_BLE_SWITCH)) {
                    c2 = 21;
                    break;
                }
                break;
            case 225641606:
                if (productId.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                    c2 = 22;
                    break;
                }
                break;
            case 427686243:
                if (productId.equals(ProductId.ID_SMART_PANEL_G4)) {
                    c2 = 23;
                    break;
                }
                break;
            case 662799966:
                if (productId.equals(ProductId.ID_BLE_LIGHT_SPI)) {
                    c2 = 24;
                    break;
                }
                break;
            case 1921260107:
                if (productId.equals(ProductId.ID_BLE_DRY_CONTACT)) {
                    c2 = 25;
                    break;
                }
                break;
            case 1951402182:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3C)) {
                    c2 = JSONLexer.EOI;
                    break;
                }
                break;
            case 1951547293:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2C)) {
                    c2 = 27;
                    break;
                }
                break;
            case 1976427583:
                if (productId.equals(ProductId.ID_BLE_CURTAIN)) {
                    c2 = 28;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case '\b':
            case '\t':
            case '\n':
            case 11:
            case '\f':
            case '\r':
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
                CmdAssistant.getSettingCmdAssistant(null, new int[0]).unSubscribePublishAddress(ActivityUtils.getTopActivity(), ((BleParam) device.getParam(BleParam.class)).getUnicastAddress(), ProductRepository.getAgreementIdByPid(device.getProductId()), new int[0]);
                break;
        }
    }

    public void forceDelDevice() {
        if (isDeleteByMeshCmd()) {
            showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.removing));
            Injection.mesh().removeNode(((BleParam) this.controlDevice.getValue().getParam(BleParam.class)).getUnicastAddress(), new IRemoveNodeCallback() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel.3
                @Override // com.ltech.smarthome.blemesh.IRemoveNodeCallback
                public void removeSuccess() {
                    BaseDeviceSetViewModel.this.deleteDeviceFromNet();
                }

                @Override // com.ltech.smarthome.blemesh.IRemoveNodeCallback
                public void removeFail() {
                    BaseDeviceSetViewModel.this.dismissLoadingDialog();
                    SmartToast.showShort(R.string.remove_fail);
                }
            });
        }
    }

    public boolean isDeleteByMeshCmd() {
        if (this.controlDevice.getValue() != null && !this.controlDevice.getValue().isVirtual()) {
            String productId = this.controlDevice.getValue().getProductId();
            if (!ProductId.CG485_SUB_DEVICE.equals(productId) && !ProductId.CGRS8_SUB_DEVICE.equals(productId)) {
                return ProductRepository.isBLeDevice(productId);
            }
        }
        return false;
    }

    public void checkVersion() {
        String string;
        final Device value = this.controlDevice.getValue();
        if (value == null || !ProductRepository.isBLeDevice(value.getProductId())) {
            return;
        }
        if (value.getMcuversion() != null) {
            this.version.setValue(value.getMcuversion());
        }
        if (value.getExtParam() != null && (string = JSONObject.parseObject(value.getExtParam()).getString("binName")) != null) {
            this.productName.setValue(string);
            this.hasProductName.setValue(true);
        }
        CmdAssistant.getQueryCmdAssistant(this.controlDevice.getValue(), new int[0]).queryProductVersion(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseDeviceSetViewModel.this.lambda$checkVersion$7(value, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkVersion$7(Device device, ResponseMsg responseMsg) {
        ProductVersionInfo parserUpgradeInfo;
        if (responseMsg == null) {
            this.newVersion.setValue(false);
            return;
        }
        if (Integer.parseInt(responseMsg.getResData().substring(6, 10), 16) != device.getUnicastAddress()) {
            this.newVersion.setValue(false);
            return;
        }
        if (responseMsg.getResData().length() < 16) {
            this.newVersion.setValue(false);
            parserUpgradeInfo = null;
        } else {
            LHomeLog.i(getClass(), "msg.getResData() current, resData-->" + responseMsg.getResData());
            parserUpgradeInfo = ((IUpgradeParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserUpgradeInfo(new String(StringUtils.hexStringToByte(responseMsg.getResData().substring(16))));
        }
        if (parserUpgradeInfo == null) {
            this.newVersion.setValue(false);
            return;
        }
        changeDeviceVersion(this.controlDevice.getValue(), parserUpgradeInfo.getsVer(), parserUpgradeInfo.gethVer());
        UpgradeInfo upgradeInfo = UpgradeFactory.getUpgradeInfo(parserUpgradeInfo);
        this.productName.setValue(parserUpgradeInfo.getDeviceModel());
        changeDeviceProductName(this.controlDevice.getValue(), parserUpgradeInfo.getDeviceModel());
        this.hasProductName.setValue(true);
        if (upgradeInfo == null || UpgradeInfo.getSoftwareVersion(upgradeInfo.getsVer()).compareTo(UpgradeInfo.getSoftwareVersion(parserUpgradeInfo.getsVer())) <= 0) {
            this.newVersion.setValue(false);
            this.version.setValue(parserUpgradeInfo.getsVer());
        } else {
            this.newVersion.setValue(true);
            UpgradeInfo.getSoftwareVersion(upgradeInfo.getsVer());
            this.version.setValue(parserUpgradeInfo.getsVer());
        }
    }

    public Place getCurPlace() {
        return Injection.repo().home().getSelPlace();
    }

    public void changeDeviceVersion(final Device device, final String sVersion, final String hVersion) {
        if (device != null) {
            if (TextUtils.isEmpty(device.getMcuversion()) || !device.getMcuversion().equals(sVersion) || TextUtils.isEmpty(device.getFirmwareversion()) || !device.getFirmwareversion().equals(hVersion)) {
                ((ObservableSubscribeProxy) Injection.net().updateDeviceVersion(device.getDeviceId(), sVersion, hVersion).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel$$ExternalSyntheticLambda10
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        BaseDeviceSetViewModel.this.lambda$changeDeviceVersion$8(device, sVersion, hVersion, obj);
                    }
                }, new SmartErrorComsumer());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeDeviceVersion$8(Device device, String str, String str2, Object obj) throws Exception {
        device.setMcuversion(str);
        device.setFirmwareversion(str2);
        Injection.repo().device().saveDevice(device);
        this.controlDevice.setValue(device);
    }

    public void changeDeviceProductName(Device device, String productName) {
        if (device != null) {
            JSONObject parseObject = device.getExtParam() != null ? JSONObject.parseObject(device.getExtParam()) : new JSONObject();
            String string = parseObject.getString("binName");
            if (string != null) {
                if (string.equals(productName)) {
                    return;
                }
                parseObject.put("binName", (Object) productName);
                updateParamExt(device, parseObject);
                return;
            }
            parseObject.put("binName", (Object) productName);
            updateParamExt(device, parseObject);
        }
    }

    public void updateParamExt(final Device device, final JSONObject obj) {
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(device.getDeviceId(), GsonUtils.toJson(obj)).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj2) {
                BaseDeviceSetViewModel.this.lambda$updateParamExt$9(device, obj, obj2);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateParamExt$9(Device device, JSONObject jSONObject, Object obj) throws Exception {
        device.setExtParam(jSONObject.toString());
        Injection.repo().device().saveDevice(device);
        this.controlDevice.setValue(device);
    }

    public void updateParamExt(final Device device, final String extParam) {
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(device.getDeviceId(), extParam).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel$$ExternalSyntheticLambda12
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseDeviceSetViewModel.this.lambda$updateParamExt$10(device, extParam, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateParamExt$10(Device device, String str, Object obj) throws Exception {
        device.setExtParam(str);
        Injection.repo().device().saveDevice(device);
        this.controlDevice.setValue(device);
        this.refreshEvent.call();
    }

    public void updateParamExt(final Device device, final String extParam, final IAction<Boolean> result) {
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(device.getDeviceId(), extParam).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel$$ExternalSyntheticLambda13
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseDeviceSetViewModel.lambda$updateParamExt$11(Device.this, extParam, result, obj);
            }
        }, new SmartErrorComsumer(this) { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel.4
            @Override // com.ltech.smarthome.net.SmartErrorComsumer
            protected void action(Throwable throwable) {
                IAction iAction = result;
                if (iAction != null) {
                    iAction.act(false);
                }
            }
        });
    }

    static /* synthetic */ void lambda$updateParamExt$11(Device device, String str, IAction iAction, Object obj) throws Exception {
        device.setExtParam(str);
        Injection.repo().device().saveDevice(device);
        if (iAction != null) {
            iAction.act(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$12(View view) {
        switch (view.getId()) {
            case R.id.layout_change_room /* 2131297392 */:
                this.showEditRoomDialogEvent.call();
                break;
            case R.id.layout_create_group /* 2131297408 */:
                this.showCreateGroupDialogEvent.call();
                break;
            case R.id.layout_device_id /* 2131297428 */:
                copyText(String.valueOf(this.controlDevice.getValue().getDeviceId()));
                break;
            case R.id.layout_device_log /* 2131297430 */:
                navigation(NavUtils.destination(ActLocalDeviceLog.class).withLong(com.ltech.smarthome.utils.Constants.CONTROL_ID, this.controlId));
                break;
            case R.id.layout_device_name /* 2131297433 */:
                this.showEditNameDialogEvent.call();
                break;
            case R.id.layout_device_replace /* 2131297434 */:
                if (ProductRepository.isSuperPanel(this.controlDevice.getValue().getProductId())) {
                    navigation(NavUtils.destination(ActSuperPanelReplace.class).withLong(com.ltech.smarthome.utils.Constants.PLACE_ID, getCurPlace().getPlaceId()).withLong("device_id", this.controlDevice.getValue().getDeviceId()).withDefaultRequestCode());
                    break;
                } else {
                    navigation(NavUtils.destination(ActDeviceReplace.class).withLong(com.ltech.smarthome.utils.Constants.PLACE_ID, getCurPlace().getPlaceId()).withLong("device_id", this.controlDevice.getValue().getDeviceId()).withDefaultRequestCode());
                    break;
                }
            case R.id.layout_mac_address /* 2131297529 */:
                copyText(this.controlDevice.getValue().getWifiMac());
                break;
            case R.id.layout_product_name /* 2131297585 */:
                copyText(this.productName.getValue());
                break;
            case R.id.layout_restore_factory /* 2131297598 */:
                navigation(NavUtils.destination(ActNfcRestore.class).withLong(com.ltech.smarthome.utils.Constants.CONTROL_ID, this.controlId));
                break;
            case R.id.layout_scene_and_automation /* 2131297612 */:
                navigation(NavUtils.destination(ActIntelligence.class).withLong("device_id", this.controlDevice.getValue().getDeviceId()).withBoolean(com.ltech.smarthome.utils.Constants.GROUP_CONTROL, false));
                break;
            case R.id.layout_upgrade /* 2131297687 */:
                this.upgradeEvent.call();
                break;
            case R.id.tv_delete_device /* 2131298576 */:
                this.showDeleteDialogEvent.call();
                break;
        }
    }

    public void copyText(String copyText) {
        ((ClipboardManager) ActivityUtils.getTopActivity().getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Label", copyText));
        SmartToast.showShort(R.string.copy_success);
    }

    public void queryScene(final long deviceId) {
        ((ObservableSubscribeProxy) Injection.net().queryDeviceScene(deviceId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseDeviceSetViewModel.this.lambda$queryScene$13(deviceId, (ListSceneResponse) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryScene$13(long j, ListSceneResponse listSceneResponse) throws Exception {
        this.tvSceneNum = listSceneResponse.getTotal();
        queryAutomation(j);
    }

    public void queryAutomation(long deviceId) {
        ((ObservableSubscribeProxy) Injection.net().queryDeviceAutomation(deviceId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseDeviceSetViewModel.this.lambda$queryAutomation$14((ListAutomationResponse) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryAutomation$14(ListAutomationResponse listAutomationResponse) throws Exception {
        int total = listAutomationResponse.getTotal();
        this.tvAutomationNum = total;
        this.getSceneAutomationOver.setValue(Integer.valueOf(this.tvSceneNum + total));
    }

    public void queryBackData(long deviceId) {
        ((ObservableSubscribeProxy) Injection.net().queryReplaceData(deviceId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseDeviceSetViewModel.this.lambda$queryBackData$15((GetReplaceDataResponse) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryBackData$15(GetReplaceDataResponse getReplaceDataResponse) throws Exception {
        JSONObject parseObject = JSON.parseObject(GsonUtils.toJson(getReplaceDataResponse.getBackData()));
        for (String str : parseObject.keySet()) {
            String str2 = (String) parseObject.get(str);
            LHomeLog.i(getClass(), str + Constants.COLON_SEPARATOR + str2);
        }
        this.backDataResult.setValue(parseObject);
    }

    public void clickAdjustKRange(Context context, Object controlObject, RelateInfoAssistant relateInfoAssistant, boolean isKnobPanel) {
        Group groupByGroupId;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < relateInfoAssistant.getZoneNumber(); i++) {
            RelatedInfoExtParam.RelateInfo relateInfo = relateInfoAssistant.getRelateInfo(i);
            SettingCmdParam.KInfo kInfo = new SettingCmdParam.KInfo();
            if (relateInfo != null && (relateInfo.action > 9 || isKnobPanel)) {
                if (relateInfo.type == 1) {
                    Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(relateInfo.objectId);
                    if (deviceByDeviceId != null) {
                        kInfo.setMinK(deviceByDeviceId.getMinkelvin());
                        kInfo.setMaxK(deviceByDeviceId.getMaxkelvin());
                    }
                } else if (relateInfo.type == 2 && (groupByGroupId = Injection.repo().group().getGroupByGroupId(relateInfo.objectId)) != null) {
                    kInfo.setMinK(groupByGroupId.getMinkelvin());
                    kInfo.setMaxK(groupByGroupId.getMaxkelvin());
                }
            }
            arrayList.add(kInfo);
        }
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.app_str_process));
        CmdAssistant.getSettingCmdAssistant(controlObject, new int[0]).setKInfo(context, (1 << relateInfoAssistant.getZoneNumber()) - 1, arrayList, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel.5
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    BaseDeviceSetViewModel.this.dismissLoadingDialog();
                    SmartToast.showCenterShort(ActivityUtils.getTopActivity().getString(R.string.calibration_succee));
                } else {
                    BaseDeviceSetViewModel.this.dismissLoadingDialog();
                    SmartToast.showCenterShort(ActivityUtils.getTopActivity().getString(R.string.calibration_fail));
                }
            }
        });
    }

    public void setOnState(final int state, final int brt, final IAction<Boolean> action) {
        getCmdHelper().setOnState(getContext(), state, brt, brt, new IAction() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseDeviceSetViewModel.this.lambda$setOnState$16(state, brt, action, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setOnState$16(int i, int i2, IAction iAction, Boolean bool) {
        if (bool.booleanValue()) {
            ReplaceHelper.instance().backupData(getLifecycleOwner(), this.controlDevice.getValue().getDeviceId(), UpdateBackDataRequest.POWER_STATUS, getCmdHelper().setOnState(i, i2, i2));
            iAction.act(true);
        } else {
            iAction.act(false);
        }
    }

    public void setBuzzerState(final boolean z, final int i, final IAction<Boolean> iAction) {
        getCmdHelper().setBuzzerState(getContext(), i, z ? 1 : 0, new IAction() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel$$ExternalSyntheticLambda11
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseDeviceSetViewModel.this.lambda$setBuzzerState$17(i, z, iAction, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setBuzzerState$17(int i, boolean z, IAction iAction, ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0) {
            ReplaceHelper.instance().backupData(getLifecycleOwner(), this.controlDevice.getValue().getDeviceId(), i == 1 ? UpdateBackDataRequest.BUZZER_STATUS : UpdateBackDataRequest.INDICATOR_STATUS, getCmdHelper().setBuzzerState(i, z ? 1 : 0));
            iAction.act(true);
        } else {
            iAction.act(false);
        }
    }

    public void showDimRangeDialog() {
        SelectDimRangeDialog.asDefault().setLowPos(this.brtRange[0]).setHighPos(this.brtRange[1]).setCurveType(-1).setRange(1, 255).setOnSaveListener(new SelectDimRangeDialog.OnSaveListener() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel$$ExternalSyntheticLambda14
            @Override // com.ltech.smarthome.view.dialog.SelectDimRangeDialog.OnSaveListener
            public final void onSave(int i, int i2) {
                BaseDeviceSetViewModel.this.lambda$showDimRangeDialog$19(i, i2);
            }
        }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDimRangeDialog$19(final int i, final int i2) {
        showLoadingDialog(getContext().getString(R.string.saving));
        getCmdHelper().setDimRange(getContext(), i, i2, i, i2, new IAction() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel$$ExternalSyntheticLambda16
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseDeviceSetViewModel.this.lambda$showDimRangeDialog$18(i, i2, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDimRangeDialog$18(int i, int i2, ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0) {
            int[] iArr = this.brtRange;
            iArr[0] = i;
            iArr[1] = i2;
            this.dimmingRange.setValue(getBrtString(this.brtRange[0]) + " - " + getBrtString(this.brtRange[1]));
            ReplaceHelper.instance().backupData(getLifecycleOwner(), this.controlDevice.getValue().getDeviceId(), UpdateBackDataRequest.DIM_RANGE, getCmdHelper().setDimRange(i, i2, i, i2));
            showSuccessTipDialog(getContext().getString(R.string.save_success));
            return;
        }
        showErrorTipDialog(getContext().getString(R.string.save_fail));
    }

    private LightAssistant getCmdHelper() {
        return CmdAssistant.getLightCmdAssistant(this.controlDevice.getValue(), new int[0]);
    }

    public void uploadData(int battery) {
        if (battery > 100) {
            return;
        }
        Device value = this.controlDevice.getValue();
        JSONObject parseObject = value.getExtParam() != null ? JSONObject.parseObject(value.getExtParam()) : new JSONObject();
        parseObject.put("battery", (Object) Integer.valueOf(battery));
        value.setExtParam(parseObject.toString());
        updateParamExt(value, parseObject.toString());
    }

    public String getLightTypeName(int lightType) {
        if (lightType == 1) {
            return getContext().getString(R.string.type_dim);
        }
        if (lightType == 2) {
            return getContext().getString(R.string.type_ct);
        }
        if (lightType == 3) {
            return getContext().getString(R.string.type_rgb);
        }
        if (lightType == 4) {
            return getContext().getString(R.string.type_rgbw);
        }
        if (lightType == 5) {
            return getContext().getString(R.string.type_rgbwy);
        }
        return "";
    }

    public String getBrtString(int brt) {
        return LightUtils.brt2ProgressHasBelowZero(brt) + "%";
    }

    public String getLocationName(long floorId, long roomId) {
        Floor floor = Injection.repo().home().getFloor(floorId);
        Room room = Injection.repo().home().getRoom(roomId);
        if (floor == null || room == null) {
            if (floor != null) {
                return floor.getFloorName();
            }
            return room != null ? room.getRoomName() : getContext().getString(R.string.app_str_not_distribution);
        }
        return floor.getFloorName() + room.getRoomName();
    }

    protected boolean isVirtual() {
        if (this.controlDevice.getValue() != null) {
            return this.controlDevice.getValue().isVirtual();
        }
        return false;
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    public boolean isOwnerOrManager() {
        Place currentPlace = getCurrentPlace();
        if (currentPlace != null) {
            return currentPlace.isOwner() || currentPlace.isManager();
        }
        return false;
    }
}