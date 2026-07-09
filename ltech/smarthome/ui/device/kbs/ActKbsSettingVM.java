package com.ltech.smarthome.ui.device.kbs;

import android.text.TextUtils;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;
import com.ltech.smarthome.ui.device.light.ActDiyLightName;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.EditDeviceDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.ResponseMsg;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActKbsSettingVM extends BaseDeviceSetViewModel {
    public long deviceId;
    public int imageIndex;
    public int lightType;
    public long placeId;
    public int showPowerOffSceneDelay;
    public int showPowerOnSceneDelay;
    public SingleLiveEvent<Void> showNoPermissionDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showControlTypeEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> refreshRoleItem = new SingleLiveEvent<>();
    public MutableLiveData<Integer> controlType = new MutableLiveData<>(1);
    private List<Observable<Void>> requestList = new ArrayList();
    public MutableLiveData<Boolean> query = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> showPowerOnOffScene = new MutableLiveData<>(false);
    public MutableLiveData<String> showPowerOnScene = new MutableLiveData<>();
    public MutableLiveData<String> showPowerOffScene = new MutableLiveData<>();
    public MutableLiveData<String> showPowerOnSceneDelayStr = new MutableLiveData<>();
    public MutableLiveData<String> showPowerOffSceneDelayStr = new MutableLiveData<>();
    public MutableLiveData<Boolean> selectSceneDialogEvent = new MutableLiveData<>();
    public MutableLiveData<Boolean> selectSceneDelayDialogEvent = new MutableLiveData<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSettingVM$$ExternalSyntheticLambda3
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActKbsSettingVM.this.lambda$new$0((View) obj);
        }
    });

    static /* synthetic */ boolean lambda$setting$9(BaseDialog baseDialog, View view) {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.layout_change_icon /* 2131297387 */:
                this.showSelectDeviceIconDialogEvent.call();
                break;
            case R.id.layout_change_room /* 2131297392 */:
                this.showEditRoomDialogEvent.call();
                break;
            case R.id.layout_control_type /* 2131297404 */:
                this.showControlTypeEvent.call();
                break;
            case R.id.layout_create_group /* 2131297408 */:
                this.showCreateGroupDialogEvent.call();
                break;
            case R.id.layout_device_name /* 2131297433 */:
                NavUtils.destination(ActDiyLightName.class).withLong(Constants.CONTROL_ID, this.controlId).withBoolean(Constants.GROUP_CONTROL, false).navigation(ActivityUtils.getTopActivity());
                break;
            case R.id.layout_power_off_scene /* 2131297580 */:
                this.selectSceneDialogEvent.setValue(false);
                break;
            case R.id.layout_power_off_scene_delay /* 2131297581 */:
                this.selectSceneDelayDialogEvent.setValue(false);
                break;
            case R.id.layout_power_on_scene /* 2131297582 */:
                this.selectSceneDialogEvent.setValue(true);
                break;
            case R.id.layout_power_on_scene_delay /* 2131297583 */:
                this.selectSceneDelayDialogEvent.setValue(true);
                break;
            case R.id.layout_set_on_state /* 2131297646 */:
                this.showPowerStateDialogEvent.call();
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

    public void changeHide(final Device device, final boolean isChecked) {
        ((ObservableSubscribeProxy) Injection.net().updateSubDeviceHide(device.getDeviceId(), isChecked).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSettingVM$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActKbsSettingVM.this.lambda$changeHide$1((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSettingVM$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActKbsSettingVM.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSettingVM$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActKbsSettingVM.lambda$changeHide$2(Device.this, isChecked, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeHide$1(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    static /* synthetic */ void lambda$changeHide$2(Device device, boolean z, Object obj) throws Exception {
        device.setSubhide(!z ? 1 : 0);
        Injection.repo().device().saveDevice(device);
        SmartToast.showShort(R.string.save_success);
    }

    public void showEditDialog(final Role role) {
        final EditDeviceDialog onLocationListener = EditDeviceDialog.asDefault().setTitle(StringUtils.getString(R.string.setting)).setLabel(StringUtils.getString(R.string.app_str_switch_name)).setIconLabel(StringUtils.getString(R.string.app_str_switch_icon)).setRole(role).setLocation(false).setOnSaveListener(new EditDeviceDialog.OnSaveListener() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSettingVM.1
            @Override // com.ltech.smarthome.view.dialog.EditDeviceDialog.OnSaveListener
            public void cancel() {
            }

            @Override // com.ltech.smarthome.view.dialog.EditDeviceDialog.OnSaveListener
            public boolean onSave(String name, int imgIndex, int floorPos, int roomPos) {
                if (TextUtils.isEmpty(name.trim())) {
                    SmartToast.showShort(R.string.input_name);
                    return false;
                }
                ActKbsSettingVM.this.roomPickHelper.setSelectPosition(floorPos, roomPos);
                ActKbsSettingVM.this.updateRole(role, name, imgIndex, ActKbsSettingVM.this.roomPickHelper.getSelectFloorId(), ActKbsSettingVM.this.roomPickHelper.getSelectRoomId());
                return true;
            }
        }).setOnLocationListener(new IAction() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSettingVM$$ExternalSyntheticLambda11
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                CmdAssistant.getSettingCmdAssistant(Role.this, new int[0]).testDeviceLocation(ActivityUtils.getTopActivity());
            }
        });
        onLocationListener.setSelectRoom(this.roomPickHelper.canSetRoom()).setFloorList(this.roomPickHelper.getCurrentFloorNames()).setRoomList(this.roomPickHelper.getCurrentRoomNames()).setSelectFloorPosition(this.roomPickHelper.getSelectFloorPosition()).setSelectRoomPosition(this.roomPickHelper.getSelectRoomPosition()).setOnSelectFloorListener(new EditDeviceDialog.OnSelectFloorListener() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSettingVM$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.dialog.EditDeviceDialog.OnSelectFloorListener
            public final void selectFloor(EditDeviceDialog editDeviceDialog, int i, String str) {
                ActKbsSettingVM.this.lambda$showEditDialog$4(onLocationListener, editDeviceDialog, i, str);
            }
        }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditDialog$4(EditDeviceDialog editDeviceDialog, EditDeviceDialog editDeviceDialog2, int i, String str) {
        editDeviceDialog.setRoomList(this.roomPickHelper.getRoomNames(i));
        editDeviceDialog.notifyDialog();
    }

    public void updateRole(Role role, String name, int imgIndex, long floorId, long roomId) {
        this.requestList.clear();
        this.requestList.add(changeRoleName(role, name));
        if (imgIndex != -1) {
            this.requestList.add(changeRoleIcon(role, imgIndex));
        }
        this.requestList.add(changeRolePlace(role, floorId, roomId));
        batchControl(this.requestList);
    }

    public Observable<Void> changeRoleName(final Role role, final String name) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSettingVM$$ExternalSyntheticLambda10
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                ActKbsSettingVM.this.lambda$changeRoleName$5(role, name, observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeRoleName$5(Role role, final String str, final ObservableEmitter observableEmitter) throws Exception {
        if (role instanceof Device) {
            final Device device = (Device) role;
            Injection.net().updateDeviceName(device.getDeviceId(), str).compose(RxUtils.io_io()).subscribe(new DisposableObserver<Object>(this) { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSettingVM.2
                @Override // io.reactivex.Observer
                public void onNext(Object o) {
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable e) {
                    observableEmitter.onComplete();
                }

                @Override // io.reactivex.Observer
                public void onComplete() {
                    device.setDeviceName(str);
                    Injection.repo().device().saveDevice(device);
                    observableEmitter.onComplete();
                }
            });
        } else if (role instanceof Group) {
            final Group group = (Group) role;
            Injection.net().updateGroupName(group.getGroupId(), str).compose(RxUtils.io_io()).subscribe(new DisposableObserver<Object>(this) { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSettingVM.3
                @Override // io.reactivex.Observer
                public void onNext(Object o) {
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable e) {
                    observableEmitter.onComplete();
                }

                @Override // io.reactivex.Observer
                public void onComplete() {
                    group.setGroupName(str);
                    Injection.repo().group().saveGroup(group);
                    observableEmitter.onComplete();
                }
            });
        }
    }

    public Observable<Void> changeRoleIcon(final Role role, final int imgIndex) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSettingVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                ActKbsSettingVM.this.lambda$changeRoleIcon$6(role, imgIndex, observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeRoleIcon$6(Role role, final int i, final ObservableEmitter observableEmitter) throws Exception {
        if (role instanceof Device) {
            final Device device = (Device) role;
            Injection.net().updateDeviceImgIndex(device.getDeviceId(), i).compose(RxUtils.io_io()).subscribe(new DisposableObserver<Object>(this) { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSettingVM.4
                @Override // io.reactivex.Observer
                public void onNext(Object o) {
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable e) {
                    observableEmitter.onComplete();
                }

                @Override // io.reactivex.Observer
                public void onComplete() {
                    device.setImageIndex(i);
                    Injection.repo().device().saveDevice(device);
                    observableEmitter.onComplete();
                }
            });
        } else {
            final Group group = (Group) role;
            Injection.net().updateGroupDeviceIcon(group.getGroupId(), i).compose(RxUtils.io_io()).subscribe(new DisposableObserver<Object>(this) { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSettingVM.5
                @Override // io.reactivex.Observer
                public void onNext(Object o) {
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable e) {
                    observableEmitter.onComplete();
                }

                @Override // io.reactivex.Observer
                public void onComplete() {
                    group.setImgindex(i);
                    Injection.repo().group().saveGroup(group);
                    observableEmitter.onComplete();
                }
            });
        }
    }

    public Observable<Void> changeRolePlace(final Role role, final long floorId, final long roomId) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSettingVM$$ExternalSyntheticLambda4
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                ActKbsSettingVM.this.lambda$changeRolePlace$7(role, roomId, floorId, observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeRolePlace$7(Role role, final long j, final long j2, final ObservableEmitter observableEmitter) throws Exception {
        if (role instanceof Device) {
            final Device device = (Device) role;
            Injection.net().updateDevicePlace(device.getDeviceId(), j, ProductId.ID_BLE_HAM.equals(device.getProductId()) || ProductId.ID_MESH_GATEWAY.equals(device.getProductId())).compose(RxUtils.io_io()).subscribe(new DisposableObserver<Object>(this) { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSettingVM.6
                @Override // io.reactivex.Observer
                public void onNext(Object o) {
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable e) {
                    observableEmitter.onComplete();
                }

                @Override // io.reactivex.Observer
                public void onComplete() {
                    if (ProductId.ID_BLE_HAM.equals(device.getProductId()) || ProductId.ID_MESH_GATEWAY.equals(device.getProductId())) {
                        ArrayList arrayList = new ArrayList();
                        for (Device device2 : Injection.repo().device().getDeviceListByPlaceId(device.getPlaceId())) {
                            if (device2.getMacdeviceid() == device.getDeviceId()) {
                                device2.setFloorId(j2);
                                device2.setRoomId(j);
                                arrayList.add(device2);
                            }
                        }
                        Injection.repo().device().saveDevice(arrayList);
                    }
                    device.setFloorId(j2);
                    device.setRoomId(j);
                    Injection.repo().device().saveDevice(device);
                    observableEmitter.onComplete();
                }
            });
        } else {
            final Group group = (Group) role;
            Injection.net().updateGroupLocation(group.getGroupId(), j2, j).compose(RxUtils.io_io()).subscribe(new DisposableObserver<Object>(this) { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSettingVM.7
                @Override // io.reactivex.Observer
                public void onNext(Object o) {
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable e) {
                    observableEmitter.onComplete();
                }

                @Override // io.reactivex.Observer
                public void onComplete() {
                    group.setFloorId(j2);
                    group.setRoomId(j);
                    Injection.repo().group().saveGroup(group);
                    observableEmitter.onComplete();
                }
            });
        }
    }

    public void batchControl(List<Observable<Void>> request) {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
        ((ObservableSubscribeProxy) Observable.concat(request).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) ActivityUtils.getTopActivity(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSettingVM.8
            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onNext(Void aVoid) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d2) {
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                SmartToast.showShort(R.string.save_success);
                ActKbsSettingVM.this.dismissLoadingDialog();
                ActKbsSettingVM.this.refreshRoleItem.call();
            }
        });
    }

    public void changeControlType(final Integer integer) {
        CmdAssistant.getSettingCmdAssistant(this.controlDevice.getValue(), 65535).setControlType(getContext(), integer.intValue() == 0 ? 1 : 0, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSettingVM.9
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    SmartToast.showShort(R.string.app_str_setting_success);
                    ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.CONTROL_INPUT_TYPE, CmdAssistant.getSettingCmdAssistant(ActKbsSettingVM.this.controlDevice.getValue(), 65535).setControlType(integer.intValue() == 0 ? 1 : 0));
                    ReplaceHelper.instance().backupData(ActKbsSettingVM.this.getLifecycleOwner(), ActKbsSettingVM.this.controlDevice.getValue().getDeviceId());
                    ActKbsSettingVM.this.controlType.setValue(Integer.valueOf(integer.intValue() != 0 ? 0 : 1));
                    return;
                }
                SmartToast.showShort(R.string.app_str_setting_failed);
            }
        });
    }

    public void queryControlType() {
        CmdAssistant.getQueryCmdAssistant(this.controlDevice.getValue(), new int[0]).queryControlType(getContext(), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSettingVM.10
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg msg) {
                if (msg == null || msg.getResData() == null || msg.getResData().length() != 18) {
                    return;
                }
                ActKbsSettingVM.this.controlType.setValue(Integer.valueOf(Integer.parseInt(msg.getResData().substring(16, 18)) & 1));
            }
        });
    }

    public void queryPowerOnOffScene() {
        final Device value = this.controlDevice.getValue();
        CmdAssistant.getQueryCmdAssistant(value, new int[0]).queryPowerOnOffScene(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSettingVM$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActKbsSettingVM.this.lambda$queryPowerOnOffScene$8(value, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryPowerOnOffScene$8(Device device, ResponseMsg responseMsg) {
        int parseInt;
        if (device == null || responseMsg == null || responseMsg.getStateCode() != 0 || responseMsg.getResData() == null) {
            return;
        }
        BleParam bleParam = (BleParam) device.getParam(BleParam.class);
        if (Integer.parseInt(responseMsg.getResData().substring(6, 10), 16) == (bleParam != null ? bleParam.getUnicastAddress() : 0) && Integer.parseInt(responseMsg.getResData().substring(18, 20), 16) == 2) {
            String substring = responseMsg.getResData().substring(20);
            if (substring.isEmpty() || (parseInt = Integer.parseInt(responseMsg.getResData().substring(18, 20), 16)) <= 0) {
                return;
            }
            String substring2 = substring.substring(2);
            for (int i = 0; i < parseInt * 2; i++) {
                boolean z = Integer.parseInt(substring2.substring(0, 2), 16) == 1;
                int parseInt2 = Integer.parseInt(substring2.substring(2, 4), 16);
                int parseInt3 = Integer.parseInt(substring2.substring(4, 6), 16);
                Scene localSceneBySceneNum = Injection.repo().scene().getLocalSceneBySceneNum(parseInt2);
                if (z) {
                    if (localSceneBySceneNum != null) {
                        this.showPowerOnScene.setValue(localSceneBySceneNum.getName());
                        this.showPowerOnSceneDelayStr.setValue(parseInt3 + getContext().getString(R.string.sec));
                        this.showPowerOnSceneDelay = parseInt3;
                    }
                } else if (localSceneBySceneNum != null) {
                    this.showPowerOffScene.setValue(localSceneBySceneNum.getName());
                    this.showPowerOffSceneDelayStr.setValue(parseInt3 + getContext().getString(R.string.sec));
                    this.showPowerOffSceneDelay = parseInt3;
                }
            }
        }
    }

    public void unbindScene(boolean isExc) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(isExc ? 1 : 2));
        arrayList.add(0);
        setting(3, arrayList, isExc);
    }

    public void bindSceneDelayTime(boolean isExc, int time) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(isExc ? 1 : 2));
        arrayList.add(Integer.valueOf(time));
        setting(4, arrayList, isExc);
    }

    private void setting(final int cmd, final List<Integer> data, final boolean isExc) {
        CmdAssistant.getSettingCmdAssistant(this.controlDevice.getValue(), new int[0]).setSmartPanelNightUpMode(ActivityUtils.getTopActivity(), cmd, data, new IAction() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSettingVM$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActKbsSettingVM.this.lambda$setting$10(cmd, data, isExc, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setting$10(int i, List list, boolean z, ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0) {
            if (i == 3 && ((Integer) list.get(1)).intValue() == 0) {
                ReplaceHelper.instance().addBackupIndexAndTypeData(UpdateBackDataRequest.TRIGGER_SCENE, null, 1, z ? 1 : 2);
                ReplaceHelper.instance().backupData(getLifecycleOwner(), this.controlDevice.getValue().getDeviceId());
                if (z) {
                    this.showPowerOnScene.setValue("");
                    return;
                } else {
                    this.showPowerOffScene.setValue("");
                    return;
                }
            }
            ReplaceHelper.instance().addBackupIndexAndTypeData(UpdateBackDataRequest.TRIGGER_DELAY, CmdAssistant.getSettingCmdAssistant(this.controlDevice, new int[0]).setSmartPanelNightUpMode(i, list), 1, z ? 1 : 2);
            ReplaceHelper.instance().backupData(getLifecycleOwner(), this.controlDevice.getValue().getDeviceId());
            int intValue = ((Integer) list.get(1)).intValue();
            if (z) {
                this.showPowerOnSceneDelay = ((Integer) list.get(1)).intValue();
                this.showPowerOnSceneDelayStr.setValue(intValue + getContext().getString(R.string.sec));
                return;
            }
            this.showPowerOffSceneDelay = ((Integer) list.get(1)).intValue();
            this.showPowerOffSceneDelayStr.setValue(intValue + getContext().getString(R.string.sec));
            return;
        }
        if (responseMsg == null || responseMsg.getStateCode() != 3) {
            return;
        }
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), getContext().getString(R.string.app_str_operation_failure), getContext().getString(R.string.local_scene_error_03)).setOkButton(getContext().getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSettingVM$$ExternalSyntheticLambda5
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return ActKbsSettingVM.lambda$setting$9(baseDialog, view);
            }
        });
    }
}