package com.ltech.smarthome.ui.device.screenpanel;

import android.text.TextUtils;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.ltech.smarthome.utils.RoomPickHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.dialog.EditDeviceDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSmartPanelSwitchSettingVM extends BaseViewModel {
    public Object controlObject;
    public long deviceId;
    public long placeId;
    public RoomPickHelper roomPickHelper = new RoomPickHelper();
    public List<Long> selectRoleIds = new ArrayList();
    public SingleLiveEvent<Void> refreshRoleItem = new SingleLiveEvent<>();
    private List<Observable<Void>> requestList = new ArrayList();

    public void changeHide(final Device device, final boolean isChecked) {
        ((ObservableSubscribeProxy) Injection.net().updateSubDeviceHide(device.getDeviceId(), isChecked).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchSettingVM$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelSwitchSettingVM.this.lambda$changeHide$0((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActSmartPanelSwitchSettingVM$$ExternalSyntheticLambda3(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchSettingVM$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelSwitchSettingVM.lambda$changeHide$1(Device.this, isChecked, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeHide$0(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    static /* synthetic */ void lambda$changeHide$1(Device device, boolean z, Object obj) throws Exception {
        device.setSubhide(!z ? 1 : 0);
        Injection.repo().device().saveDevice(device);
        SmartToast.showShort(R.string.save_success);
    }

    public void changeHide(final Group group, final boolean isChecked) {
        ((ObservableSubscribeProxy) Injection.net().updateSubGroupHide(group.getGroupId(), isChecked).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchSettingVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelSwitchSettingVM.this.lambda$changeHide$2((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActSmartPanelSwitchSettingVM$$ExternalSyntheticLambda3(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchSettingVM$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelSwitchSettingVM.lambda$changeHide$3(Group.this, isChecked, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeHide$2(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    static /* synthetic */ void lambda$changeHide$3(Group group, boolean z, Object obj) throws Exception {
        group.setSubhide(!z ? 1 : 0);
        Injection.repo().group().saveGroup(group);
        SmartToast.showShort(R.string.save_success);
    }

    public void showEditDialog(final Role role) {
        final EditDeviceDialog onLocationListener = EditDeviceDialog.asDefault().setTitle(StringUtils.getString(R.string.setting)).setLabel(StringUtils.getString(R.string.app_str_switch_name)).setIconLabel(StringUtils.getString(R.string.app_str_switch_icon)).setRole(role).setSelectFloorPosition(this.roomPickHelper.getSelectFloorPosition()).setSelectRoomPosition(this.roomPickHelper.getSelectRoomPosition()).setLocation(false).setOnSaveListener(new EditDeviceDialog.OnSaveListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchSettingVM.1
            @Override // com.ltech.smarthome.view.dialog.EditDeviceDialog.OnSaveListener
            public void cancel() {
            }

            @Override // com.ltech.smarthome.view.dialog.EditDeviceDialog.OnSaveListener
            public boolean onSave(String name, int imgIndex, int floorPos, int roomPos) {
                if (TextUtils.isEmpty(name.trim())) {
                    SmartToast.showShort(R.string.input_name);
                    return false;
                }
                ActSmartPanelSwitchSettingVM.this.roomPickHelper.setSelectPosition(floorPos, roomPos);
                ActSmartPanelSwitchSettingVM.this.updateRole(role, name, imgIndex, ActSmartPanelSwitchSettingVM.this.roomPickHelper.getSelectFloorId(), ActSmartPanelSwitchSettingVM.this.roomPickHelper.getSelectRoomId(), ActSmartPanelSwitchSettingVM.this.roomPickHelper.getSelectFloorName(), ActSmartPanelSwitchSettingVM.this.roomPickHelper.getSelectRoomName());
                return true;
            }
        }).setOnLocationListener(new IAction() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchSettingVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                CmdAssistant.getSettingCmdAssistant(Role.this, new int[0]).testDeviceLocation(ActivityUtils.getTopActivity());
            }
        });
        onLocationListener.setSelectRoom(this.roomPickHelper.canSetRoom()).setFloorList(this.roomPickHelper.getCurrentFloorNames()).setRoomList(this.roomPickHelper.getCurrentRoomNames()).setSelectFloorPosition(this.roomPickHelper.getSelectFloorPosition()).setSelectRoomPosition(this.roomPickHelper.getSelectRoomPosition()).setOnSelectFloorListener(new EditDeviceDialog.OnSelectFloorListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchSettingVM$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.dialog.EditDeviceDialog.OnSelectFloorListener
            public final void selectFloor(EditDeviceDialog editDeviceDialog, int i, String str) {
                ActSmartPanelSwitchSettingVM.this.lambda$showEditDialog$5(onLocationListener, editDeviceDialog, i, str);
            }
        }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditDialog$5(EditDeviceDialog editDeviceDialog, EditDeviceDialog editDeviceDialog2, int i, String str) {
        editDeviceDialog.setRoomList(this.roomPickHelper.getRoomNames(i));
        editDeviceDialog.notifyDialog();
    }

    public void updateRole(Role role, String name, int imgIndex, long floorId, long roomId, String floorName, String roomName) {
        this.requestList.clear();
        this.requestList.add(changeRoleName(role, name));
        if (imgIndex != -1) {
            this.requestList.add(changeRoleIcon(role, imgIndex));
        }
        this.requestList.add(changeRolePlace(role, floorId, roomId, floorName, roomName));
        batchControl(this.requestList);
    }

    public Observable<Void> changeRoleName(final Role role, final String name) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchSettingVM$$ExternalSyntheticLambda7
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                ActSmartPanelSwitchSettingVM.this.lambda$changeRoleName$6(role, name, observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeRoleName$6(final Role role, final String str, final ObservableEmitter observableEmitter) throws Exception {
        if (role instanceof Device) {
            final Device device = (Device) role;
            Injection.net().updateDeviceName(device.getDeviceId(), str).compose(RxUtils.io_io()).subscribe(new DisposableObserver<Object>() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchSettingVM.2
                @Override // io.reactivex.Observer
                public void onNext(Object o) {
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable e) {
                    observableEmitter.onComplete();
                }

                @Override // io.reactivex.Observer
                public void onComplete() {
                    if (ProductRepository.isScreenPanel(device.getProductId())) {
                        ActSmartPanelSwitchSettingVM.this.setScreen(str, role);
                    }
                    device.setDeviceName(str);
                    Injection.repo().device().saveDevice(device);
                    observableEmitter.onComplete();
                }
            });
        } else if (role instanceof Group) {
            final Group group = (Group) role;
            Injection.net().updateGroupName(group.getGroupId(), str).compose(RxUtils.io_io()).subscribe(new DisposableObserver<Object>() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchSettingVM.3
                @Override // io.reactivex.Observer
                public void onNext(Object o) {
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable e) {
                    observableEmitter.onComplete();
                }

                @Override // io.reactivex.Observer
                public void onComplete() {
                    if (ActSmartPanelSwitchSettingVM.this.getProPanelCount(group) > 0) {
                        ActSmartPanelSwitchSettingVM.this.setScreen(str, role);
                    }
                    group.setGroupName(str);
                    Injection.repo().group().saveGroup(group);
                    observableEmitter.onComplete();
                }
            });
        }
    }

    public Observable<Void> changeRoleIcon(final Role role, final int imgIndex) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchSettingVM$$ExternalSyntheticLambda8
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                ActSmartPanelSwitchSettingVM.this.lambda$changeRoleIcon$7(role, imgIndex, observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeRoleIcon$7(Role role, final int i, final ObservableEmitter observableEmitter) throws Exception {
        if (role instanceof Device) {
            final Device device = (Device) role;
            Injection.net().updateDeviceImgIndex(device.getDeviceId(), i).compose(RxUtils.io_io()).subscribe(new DisposableObserver<Object>(this) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchSettingVM.4
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
            Injection.net().updateGroupDeviceIcon(group.getGroupId(), i).compose(RxUtils.io_io()).subscribe(new DisposableObserver<Object>(this) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchSettingVM.5
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

    public Observable<Void> changeRolePlace(final Role role, final long floorId, final long roomId, final String floorName, final String roomName) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchSettingVM$$ExternalSyntheticLambda9
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                ActSmartPanelSwitchSettingVM.this.lambda$changeRolePlace$8(role, roomId, floorId, floorName, roomName, observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeRolePlace$8(Role role, final long j, final long j2, final String str, final String str2, final ObservableEmitter observableEmitter) throws Exception {
        if (role instanceof Device) {
            final Device device = (Device) role;
            Injection.net().updateDevicePlace(device.getDeviceId(), j, ProductId.ID_BLE_HAM.equals(device.getProductId()) || ProductId.ID_MESH_GATEWAY.equals(device.getProductId())).compose(RxUtils.io_io()).subscribe(new DisposableObserver<Object>(this) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchSettingVM.6
                @Override // io.reactivex.Observer
                public void onNext(Object o) {
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable e) {
                    observableEmitter.onComplete();
                }

                @Override // io.reactivex.Observer
                public void onComplete() {
                    device.setFloorId(j2);
                    device.setRoomId(j);
                    device.setFloorName(str);
                    device.setRoomName(str2);
                    Injection.repo().device().saveDevice(device);
                    observableEmitter.onComplete();
                }
            });
        } else {
            final Group group = (Group) role;
            Injection.net().updateGroupLocation(group.getGroupId(), j2, j).compose(RxUtils.io_io()).subscribe(new DisposableObserver<Object>(this) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchSettingVM.7
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
                    group.setFloorName(str);
                    group.setRoomName(str2);
                    Injection.repo().group().saveGroup(group);
                    observableEmitter.onComplete();
                }
            });
        }
    }

    public void batchControl(List<Observable<Void>> request) {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
        ((ObservableSubscribeProxy) Observable.concat(request).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) ActivityUtils.getTopActivity(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchSettingVM.8
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
                ActSmartPanelSwitchSettingVM.this.dismissLoadingDialog();
                ActSmartPanelSwitchSettingVM.this.refreshRoleItem.call();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setScreen(String name, Role role) {
        RelateInfoUtils.initRelateInfoList(this.controlObject);
        int relayNum = RelaySeparationHelper.getRelayNum(role);
        int i = 0;
        while (i < RelateInfoUtils.relatedInfoList.size()) {
            RelateInfoItem relateInfoItem = RelateInfoUtils.relatedInfoList.get(i);
            if (relateInfoItem.relateInfo != null && relateInfoItem.type == 0 && relateInfoItem.relateInfo.switchIndex == relayNum) {
                RelaySeparationHelper.setScreenData(ActivityUtils.getTopActivity(), i, name, this.controlObject, RelateInfoUtils.relateInfoAssistant, true, new IAction<Integer>() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchSettingVM.9
                    @Override // com.ltech.smarthome.base.IAction
                    public void act(Integer integer) {
                        if (integer.intValue() == 0) {
                            ActSmartPanelSwitchSettingVM.this.showLoadingDialog("");
                            return;
                        }
                        if (integer.intValue() == 1) {
                            ActSmartPanelSwitchSettingVM.this.dismissLoadingDialog();
                        } else if (integer.intValue() == 2) {
                            ActSmartPanelSwitchSettingVM actSmartPanelSwitchSettingVM = ActSmartPanelSwitchSettingVM.this;
                            actSmartPanelSwitchSettingVM.showErrorTipDialog(actSmartPanelSwitchSettingVM.getContext().getString(R.string.save_fail));
                        }
                    }
                });
                return;
            } else {
                i++;
                name = name;
            }
        }
    }

    public int getProPanelCount(Group group) {
        int i = 0;
        if (group != null && !group.getDeviceIds().isEmpty()) {
            Iterator<Long> it = group.getDeviceIds().iterator();
            while (it.hasNext()) {
                Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(it.next().longValue());
                if (deviceByDeviceId != null && ProductRepository.isScreenPanel(deviceByDeviceId.getProductId())) {
                    i++;
                }
            }
        }
        return i;
    }
}