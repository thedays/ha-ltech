package com.ltech.smarthome.ui.device.dalipro;

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
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.net.response.device.ListDaliDeviceResponse;
import com.ltech.smarthome.net.response.group.ListDaliGroupResponse;
import com.ltech.smarthome.net.response.scene.ListDaliSceneResponse;
import com.ltech.smarthome.ui.device.dalipro.ActCgdProLightSettingVM;
import com.ltech.smarthome.ui.device.light.ActLightOnOffTime;
import com.ltech.smarthome.ui.device.light.ActRhythmsSettingVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.LightAssistant;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActCgdProLightSettingVM extends ActRhythmsSettingVM {
    public long deviceId;
    public long placeId;
    public boolean samePlace;
    public SingleLiveEvent<Void> showBatchSetEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showSearchAddressEvent = new SingleLiveEvent<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActCgdProLightSettingVM$$ExternalSyntheticLambda3
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActCgdProLightSettingVM.this.lambda$new$0((View) obj);
        }
    });

    public interface OnCgpProGetDataListener {
        void onDismissDialog();

        void onGetAllData();

        void onGetFirstFrameData();

        void updateProgress(int progress);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.layout_batch_hide_manage /* 2131297361 */:
                navigation(NavUtils.destination(ActDaliBatchHideManage.class).withLong(Constants.PLACE_ID, this.placeId).withLong("device_id", this.deviceId).withDefaultRequestCode());
                break;
            case R.id.layout_batch_set /* 2131297362 */:
                this.showBatchSetEvent.call();
                break;
            case R.id.layout_change_icon /* 2131297387 */:
                this.showSelectDeviceIconDialogEvent.call();
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
            case R.id.layout_on_off_time /* 2131297559 */:
                navigation(NavUtils.destination(ActLightOnOffTime.class).withLong(Constants.CONTROL_ID, this.controlId).withDefaultRequestCode());
                break;
            case R.id.layout_search_address /* 2131297619 */:
                this.showSearchAddressEvent.call();
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

    public void initData(final Boolean isCleanData, final OnCgpProGetDataListener onCgpProGetDataListener) {
        CgdProDataManager.getInstance().clearDeviceByteList();
        CgdProDataManager.getInstance().clearGroupPositionList();
        getLightCmdHelper().queryCgdLight(ActivityUtils.getTopActivity(), 0, isCleanData.booleanValue() ? 2 : 1, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActCgdProLightSettingVM$$ExternalSyntheticLambda9
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActCgdProLightSettingVM.this.lambda$initData$3(onCgpProGetDataListener, isCleanData, (ResponseMsg) obj);
            }
        }, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActCgdProLightSettingVM$$ExternalSyntheticLambda10
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActCgdProLightSettingVM.lambda$initData$4(ActCgdProLightSettingVM.OnCgpProGetDataListener.this, (Integer) obj);
            }
        }, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActCgdProLightSettingVM$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActCgdProLightSettingVM.lambda$initData$5(ActCgdProLightSettingVM.OnCgpProGetDataListener.this, (Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initData$3(final OnCgpProGetDataListener onCgpProGetDataListener, final Boolean bool, ResponseMsg responseMsg) {
        if (responseMsg != null) {
            if (responseMsg.getStateCode() == 21) {
                LHomeLog.e(getClass(), "DALI主控无设备");
                if (onCgpProGetDataListener != null) {
                    onCgpProGetDataListener.onDismissDialog();
                }
                showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.dali_no_devices));
                return;
            }
            final List<Device> deviceList = CgdProDataManager.getInstance().getDeviceList(this.controlDevice.getValue(), bool.booleanValue());
            if (deviceList == null || deviceList.size() <= 0) {
                return;
            }
            ((ObservableSubscribeProxy) Injection.net(30).updateDaliDeviceList(this.controlDevice.getValue(), deviceList).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActCgdProLightSettingVM$$ExternalSyntheticLambda5
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActCgdProLightSettingVM.this.lambda$initData$1(deviceList, onCgpProGetDataListener, bool, (ListDaliDeviceResponse) obj);
                }
            }, new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActCgdProLightSettingVM$$ExternalSyntheticLambda6
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActCgdProLightSettingVM.this.lambda$initData$2(onCgpProGetDataListener, (Throwable) obj);
                }
            });
            return;
        }
        if (onCgpProGetDataListener != null) {
            onCgpProGetDataListener.onDismissDialog();
        }
        showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.local_scene_sync_fail));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initData$1(List list, OnCgpProGetDataListener onCgpProGetDataListener, Boolean bool, ListDaliDeviceResponse listDaliDeviceResponse) throws Exception {
        if (listDaliDeviceResponse != null) {
            for (int i = 0; i < listDaliDeviceResponse.getDevices().size(); i++) {
                ((Device) list.get(i)).setDeviceId(listDaliDeviceResponse.getDevices().get(i).getDeviceid().longValue());
                ((Device) list.get(i)).setDeviceName(listDaliDeviceResponse.getDevices().get(i).getDevicename());
                ((Device) list.get(i)).setWifiMac(listDaliDeviceResponse.getDevices().get(i).getMac());
            }
            Injection.boxStore().boxFor(Device.class).put((Collection) list);
            updateGroup(list, onCgpProGetDataListener, bool.booleanValue());
            return;
        }
        if (onCgpProGetDataListener != null) {
            onCgpProGetDataListener.onDismissDialog();
        }
        showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initData$2(OnCgpProGetDataListener onCgpProGetDataListener, Throwable th) throws Exception {
        if (onCgpProGetDataListener != null) {
            onCgpProGetDataListener.onDismissDialog();
        }
        showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
    }

    static /* synthetic */ void lambda$initData$4(OnCgpProGetDataListener onCgpProGetDataListener, Integer num) {
        if (onCgpProGetDataListener != null) {
            onCgpProGetDataListener.updateProgress(num.intValue());
        }
    }

    static /* synthetic */ void lambda$initData$5(OnCgpProGetDataListener onCgpProGetDataListener, Integer num) {
        if (onCgpProGetDataListener != null) {
            onCgpProGetDataListener.onGetFirstFrameData();
        }
    }

    private void updateGroup(List<Device> deviceList, final OnCgpProGetDataListener onCgpProGetDataListener, final boolean isCleanData) {
        final List<Group> groupList = CgdProDataManager.getInstance().getGroupList(this.controlDevice.getValue(), deviceList, isCleanData);
        if (groupList != null && groupList.size() > 0) {
            ((ObservableSubscribeProxy) Injection.net().updateDaliGroupList(this.controlDevice.getValue(), groupList).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActCgdProLightSettingVM$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActCgdProLightSettingVM.this.lambda$updateGroup$6(groupList, onCgpProGetDataListener, isCleanData, (ListDaliGroupResponse) obj);
                }
            }, new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActCgdProLightSettingVM$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActCgdProLightSettingVM.this.lambda$updateGroup$7(onCgpProGetDataListener, (Throwable) obj);
                }
            });
            return;
        }
        if (onCgpProGetDataListener != null) {
            onCgpProGetDataListener.onDismissDialog();
        }
        showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateGroup$6(List list, OnCgpProGetDataListener onCgpProGetDataListener, boolean z, ListDaliGroupResponse listDaliGroupResponse) throws Exception {
        if (listDaliGroupResponse != null && listDaliGroupResponse.getRet() == 0) {
            for (int i = 0; i < listDaliGroupResponse.getData().size(); i++) {
                ((Group) list.get(i)).setGroupId(listDaliGroupResponse.getData().get(i).getGroupid());
                ((Group) list.get(i)).setGroupName(listDaliGroupResponse.getData().get(i).getGroupname());
            }
            Injection.boxStore().boxFor(Group.class).put((Collection) list);
            updateScene(onCgpProGetDataListener, z);
            return;
        }
        if (onCgpProGetDataListener != null) {
            onCgpProGetDataListener.onDismissDialog();
        }
        showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateGroup$7(OnCgpProGetDataListener onCgpProGetDataListener, Throwable th) throws Exception {
        if (onCgpProGetDataListener != null) {
            onCgpProGetDataListener.onDismissDialog();
        }
        showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
    }

    private void updateScene(final OnCgpProGetDataListener onCgpProGetDataListener, final boolean isCleanData) {
        CgdProDataManager.getInstance().clearScenePositionList();
        getLightCmdHelper().queryCgdScene(ActivityUtils.getTopActivity(), 1, 0, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActCgdProLightSettingVM$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActCgdProLightSettingVM.this.lambda$updateScene$10(isCleanData, onCgpProGetDataListener, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateScene$10(boolean z, final OnCgpProGetDataListener onCgpProGetDataListener, ResponseMsg responseMsg) {
        if (responseMsg != null) {
            if (responseMsg.getStateCode() == 22) {
                final List<Scene> sceneList = CgdProDataManager.getInstance().getSceneList(this.controlDevice.getValue(), z);
                ((ObservableSubscribeProxy) Injection.net().updateDaliSceneList(this.controlDevice.getValue(), sceneList).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActCgdProLightSettingVM$$ExternalSyntheticLambda7
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActCgdProLightSettingVM.this.lambda$updateScene$8(sceneList, onCgpProGetDataListener, (ListDaliSceneResponse) obj);
                    }
                }, new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActCgdProLightSettingVM$$ExternalSyntheticLambda8
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActCgdProLightSettingVM.this.lambda$updateScene$9(onCgpProGetDataListener, (Throwable) obj);
                    }
                });
                return;
            } else {
                if (onCgpProGetDataListener != null) {
                    onCgpProGetDataListener.onDismissDialog();
                }
                showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.local_scene_sync_fail));
                return;
            }
        }
        if (onCgpProGetDataListener != null) {
            onCgpProGetDataListener.onDismissDialog();
        }
        showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.local_scene_sync_fail));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateScene$8(List list, OnCgpProGetDataListener onCgpProGetDataListener, ListDaliSceneResponse listDaliSceneResponse) throws Exception {
        if (listDaliSceneResponse != null && listDaliSceneResponse.getRet() == 0) {
            for (int i = 0; i < listDaliSceneResponse.getData().size(); i++) {
                ((Scene) list.get(i)).setSceneId(listDaliSceneResponse.getData().get(i).getSceneid());
                ((Scene) list.get(i)).setSceneName(listDaliSceneResponse.getData().get(i).getScenename());
            }
            Injection.boxStore().boxFor(Scene.class).put((Collection) list);
            if (onCgpProGetDataListener != null) {
                onCgpProGetDataListener.onGetAllData();
                return;
            }
            return;
        }
        if (onCgpProGetDataListener != null) {
            onCgpProGetDataListener.onDismissDialog();
        }
        showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateScene$9(OnCgpProGetDataListener onCgpProGetDataListener, Throwable th) throws Exception {
        if (onCgpProGetDataListener != null) {
            onCgpProGetDataListener.onDismissDialog();
        }
        showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
    }

    public LightAssistant getLightCmdHelper() {
        return CmdAssistant.getLightCmdAssistant(this.controlDevice.getValue(), new int[0]);
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel
    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }
}