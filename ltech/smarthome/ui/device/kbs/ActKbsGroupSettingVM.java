package com.ltech.smarthome.ui.device.kbs;

import android.text.TextUtils;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.device_param.KbsExtParam;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.group.BaseGroupSettingVM;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.EditDeviceDialog;
import com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActKbsGroupSettingVM extends BaseGroupSettingVM {
    public long deviceId;
    public long groupId;
    public int imageIndex;
    public int lightType;
    public long placeId;
    public SelectPowerOnStateDialog powerOnStateDialog;
    public SingleLiveEvent<Void> showNoPermissionDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> refreshRoleItem = new SingleLiveEvent<>();
    public MutableLiveData<Integer> controlType = new MutableLiveData<>(0);
    public MutableLiveData<Boolean> query = new MutableLiveData<>(false);

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    public void showEditDialog(final int i, final Role role) {
        EditDeviceDialog.asDefault().setTitle(StringUtils.getString(R.string.setting)).setLabel(StringUtils.getString(R.string.app_str_switch_name)).setIconLabel(StringUtils.getString(R.string.app_str_switch_icon)).setRole(role).setSelectRoom(false).setLocation(false).setOnSaveListener(new EditDeviceDialog.OnSaveListener() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsGroupSettingVM.1
            @Override // com.ltech.smarthome.view.dialog.EditDeviceDialog.OnSaveListener
            public void cancel() {
            }

            @Override // com.ltech.smarthome.view.dialog.EditDeviceDialog.OnSaveListener
            public boolean onSave(String name, int imgIndex, int floorPos, int roomPos) {
                if (TextUtils.isEmpty(name.trim())) {
                    SmartToast.showShort(R.string.input_name);
                    return false;
                }
                ActKbsGroupSettingVM.this.updateRole(i, name, imgIndex);
                return true;
            }
        }).setOnLocationListener(new IAction() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsGroupSettingVM$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                CmdAssistant.getSettingCmdAssistant(Role.this, new int[0]).testDeviceLocation(ActivityUtils.getTopActivity());
            }
        }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
    }

    public void updateRole(int i, String name, int imgIndex) {
        final KbsExtParam kbsExtParam = (KbsExtParam) this.controlGroup.getExtParam(KbsExtParam.class);
        if (kbsExtParam == null) {
            kbsExtParam = new KbsExtParam();
        }
        if (i == 0) {
            kbsExtParam.setName1(name);
            kbsExtParam.setImgindex1(imgIndex);
        } else {
            kbsExtParam.setName2(name);
            kbsExtParam.setImgindex2(imgIndex);
        }
        ((ObservableSubscribeProxy) Injection.net().updateGroupParamExt(this.controlGroup.getGroupId(), GsonUtils.toJson(kbsExtParam)).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsGroupSettingVM$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActKbsGroupSettingVM.this.lambda$updateRole$1((Disposable) obj);
            }
        }).doFinally(new ActKbsGroupSettingVM$$ExternalSyntheticLambda1(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsGroupSettingVM$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActKbsGroupSettingVM.this.lambda$updateRole$2(kbsExtParam, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateRole$1(Disposable disposable) throws Exception {
        showLoadingDialog(getContext().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateRole$2(KbsExtParam kbsExtParam, Object obj) throws Exception {
        this.controlGroup.setExtParam(GsonUtils.toJson(kbsExtParam));
        Injection.repo().group().saveGroup(this.controlGroup);
        showSuccessTipDialog(getContext().getString(R.string.save_success));
        this.controlObject.setValue(this.controlGroup);
    }

    @Override // com.ltech.smarthome.ui.group.BaseGroupSettingVM
    public void changeGroupPlace(final long floorId, final long roomId) {
        final Group group = this.controlGroup;
        final String floorName = Injection.repo().home().getFloor(floorId).getFloorName();
        final String roomName = Injection.repo().home().getRoom(roomId).getRoomName();
        ((ObservableSubscribeProxy) Injection.net().updateGroupLocation(group.getGroupId(), floorId, roomId).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsGroupSettingVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActKbsGroupSettingVM.this.lambda$changeGroupPlace$3((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActKbsGroupSettingVM$$ExternalSyntheticLambda1(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsGroupSettingVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActKbsGroupSettingVM.this.lambda$changeGroupPlace$4(group, floorId, roomId, floorName, roomName, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeGroupPlace$3(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeGroupPlace$4(Group group, long j, long j2, String str, String str2, Object obj) throws Exception {
        group.setFloorId(j);
        group.setRoomId(j2);
        group.setFloorName(str);
        group.setRoomName(str2);
        Injection.repo().group().saveGroup(group);
        this.controlObject.setValue(group);
        SmartToast.showShort(R.string.save_success);
    }

    public void updateGroupImageIndex(long groupId, final int selectPos) {
        ((ObservableSubscribeProxy) Injection.net().updateGroupDeviceIcon(groupId, selectPos).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsGroupSettingVM$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActKbsGroupSettingVM.this.lambda$updateGroupImageIndex$5((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsGroupSettingVM.2
            @Override // io.reactivex.functions.Action
            public void run() throws Exception {
                ActKbsGroupSettingVM.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsGroupSettingVM$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActKbsGroupSettingVM.this.lambda$updateGroupImageIndex$6(selectPos, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateGroupImageIndex$5(Disposable disposable) throws Exception {
        showLoadingDialog(getContext().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateGroupImageIndex$6(int i, Object obj) throws Exception {
        Group group = this.controlGroup;
        group.setImgindex(i);
        Injection.repo().group().saveGroup(group);
        this.controlObject.setValue(group);
        SmartToast.showShort(R.string.save_success);
    }
}