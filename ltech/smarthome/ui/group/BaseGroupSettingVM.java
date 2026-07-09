package com.ltech.smarthome.ui.group;

import android.content.Context;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.device_param.EurPanelGroupParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.automation.ListAutomationResponse;
import com.ltech.smarthome.net.response.group.UpdateGroupResponse;
import com.ltech.smarthome.net.response.scene.ListSceneResponse;
import com.ltech.smarthome.ui.control.ActIntelligence;
import com.ltech.smarthome.ui.device.aspanel.AsHelper;
import com.ltech.smarthome.ui.device.eurpanel.EurHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RoomPickHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.ltech.smarthome.view.dialog.DeviceFrequencyAndIntervalDialog;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.RoomSelectDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.product_agreement.param.SettingCmdParam;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class BaseGroupSettingVM extends BaseViewModel {
    public Group controlGroup;
    private int tvAutomationNum;
    private int tvSceneNum;
    public MutableLiveData<Group> controlObject = new MutableLiveData<>();
    public RoomPickHelper roomPickHelper = new RoomPickHelper();
    public SingleLiveEvent<Void> refreshEvent = new SingleLiveEvent<>();
    public MutableLiveData<Boolean> nameChangeSuccess = new MutableLiveData<>();
    public MutableLiveData<Integer> getSceneAutomationOver = new MutableLiveData<>(0);
    public BindingCommand<View> commonClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.group.BaseGroupSettingVM$$ExternalSyntheticLambda5
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            BaseGroupSettingVM.this.lambda$new$0((View) obj);
        }
    });

    static /* synthetic */ boolean lambda$showDelFailDialog$2(BaseDialog baseDialog, View view) {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.layout_change_room /* 2131297392 */:
                showEditRoomDialog();
                break;
            case R.id.layout_edit_group /* 2131297457 */:
                navigation(NavUtils.destination(ProductRepository.isBleGroup(this.controlGroup.getModuleType()) ? ActSelectDeviceNew.class : ActAddWiFiGroup.class).withLong(Constants.PLACE_ID, this.controlGroup.getPlaceId()).withLong(Constants.FLOOR_ID, this.controlGroup.getFloorId()).withLong(Constants.ROOM_ID, this.controlGroup.getRoomId()).withLong(Constants.GROUP_ID, this.controlGroup.getId()));
                break;
            case R.id.layout_group_name /* 2131297487 */:
                showEditNameDialog();
                break;
            case R.id.layout_scene_and_automation /* 2131297612 */:
                navigation(NavUtils.destination(ActIntelligence.class).withLong("device_id", this.controlObject.getValue().getGroupId()).withBoolean(Constants.GROUP_CONTROL, true));
                break;
            case R.id.tv_delete /* 2131298575 */:
                if (ProductRepository.isBleGroup(this.controlObject.getValue().getModuleType()) && !this.controlGroup.getDeviceIds().isEmpty()) {
                    showDelFailDialog();
                    break;
                } else {
                    showDelDialog();
                    break;
                }
                break;
        }
    }

    private void showDelDialog() {
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), StringUtils.getString(R.string.tips), StringUtils.getString(R.string.tip_del_group)).setOkButton(StringUtils.getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.BaseGroupSettingVM$$ExternalSyntheticLambda3
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showDelDialog$1;
                lambda$showDelDialog$1 = BaseGroupSettingVM.this.lambda$showDelDialog$1(baseDialog, view);
                return lambda$showDelDialog$1;
            }
        }).setCancelButton(StringUtils.getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showDelDialog$1(BaseDialog baseDialog, View view) {
        delGroupFromNet(this.controlGroup);
        return false;
    }

    private void showDelFailDialog() {
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), StringUtils.getString(R.string.del_fail), StringUtils.getString(R.string.del_fail_tip)).setOkButton(StringUtils.getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.BaseGroupSettingVM$$ExternalSyntheticLambda12
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return BaseGroupSettingVM.lambda$showDelFailDialog$2(baseDialog, view);
            }
        });
    }

    private void delGroupFromNet(final Group group) {
        Observable<Object> deleteGroup;
        if ((AsHelper.isNewUb(group) || EurHelper.isEb125(group)) && group.getParam(EurPanelGroupParam.class) != null && ((EurPanelGroupParam) group.getParam(EurPanelGroupParam.class)).getPublicationId() > 0) {
            deleteGroup = Injection.net().deleteGroup(group.getGroupId(), ((EurPanelGroupParam) group.getParam(EurPanelGroupParam.class)).getPublicationId());
        } else {
            deleteGroup = Injection.net().deleteGroup(group.getGroupId());
        }
        ((ObservableSubscribeProxy) deleteGroup.delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.BaseGroupSettingVM$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseGroupSettingVM.this.lambda$delGroupFromNet$3((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new BaseGroupSettingVM$$ExternalSyntheticLambda8(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.BaseGroupSettingVM$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseGroupSettingVM.this.lambda$delGroupFromNet$4(group, obj);
            }
        }, new SmartErrorComsumer());
        int lightColorType = ProductRepository.getLightColorType((Object) group);
        if (lightColorType == 13 || lightColorType == 15) {
            return;
        }
        switch (lightColorType) {
            case 22:
            case 23:
            case 24:
                break;
            case 25:
                return;
            default:
                switch (lightColorType) {
                    case 27:
                    case 28:
                    case 29:
                    case 30:
                        break;
                    default:
                        unSubscribePublicationAddress(group.getGroupAddress());
                        break;
                }
                return;
        }
        if (group.getParam(EurPanelGroupParam.class) != null) {
            unSubscribePublicationAddress(((EurPanelGroupParam) group.getParam(EurPanelGroupParam.class)).getPublicationAddress());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$delGroupFromNet$3(Disposable disposable) throws Exception {
        showLoadingDialog(StringUtils.getString(R.string.removing));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$delGroupFromNet$4(Group group, Object obj) throws Exception {
        Iterator<Group> it = Injection.repo().group().getSubGroup(group.getPlaceId(), group.getGroupId()).iterator();
        while (it.hasNext()) {
            Injection.repo().group().removeGroupFromDb(it.next().getId());
        }
        Injection.repo().group().removeGroupFromDb(group.getId());
        setResult(5002);
        finishActivity();
    }

    private void unSubscribePublicationAddress(int publishAddress) {
        if (publishAddress > 0) {
            CmdAssistant.getSettingCmdAssistant(null, new int[0]).unSubscribePublishAddress(ActivityUtils.getTopActivity(), publishAddress, 2, new int[0]);
        }
    }

    protected void showEditNameDialog() {
        EditDialog.asDefault().setContent(this.controlGroup.getGroupName()).setTitle(StringUtils.getString(R.string.edit_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.group.BaseGroupSettingVM$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseGroupSettingVM.this.lambda$showEditNameDialog$5((String) obj);
            }
        }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
    }

    /* renamed from: updateName, reason: merged with bridge method [inline-methods] */
    public void lambda$showEditNameDialog$5(final String name) {
        final Group value = this.controlObject.getValue();
        ((ObservableSubscribeProxy) Injection.net().updateGroupName(value.getGroupId(), name).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.BaseGroupSettingVM$$ExternalSyntheticLambda15
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseGroupSettingVM.this.lambda$updateName$6((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new BaseGroupSettingVM$$ExternalSyntheticLambda8(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.BaseGroupSettingVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseGroupSettingVM.this.lambda$updateName$7(value, name, (UpdateGroupResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateName$6(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateName$7(Group group, String str, UpdateGroupResponse updateGroupResponse) throws Exception {
        group.setGroupName(str);
        Injection.repo().group().saveGroup(group);
        SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.save_success));
        this.controlObject.setValue(group);
        this.nameChangeSuccess.setValue(true);
    }

    protected void showEditRoomDialog() {
        if (this.roomPickHelper.getCanSetRoom() == null || !this.roomPickHelper.getCanSetRoom().getValue().booleanValue()) {
            return;
        }
        RoomSelectDialog.asDefault().setFloorList(this.roomPickHelper.getCurrentFloorNames()).setRoomList(this.roomPickHelper.getCurrentRoomNames()).setSelectFloorPosition(this.roomPickHelper.getSelectFloorPosition()).setSelectRoomPosition(this.roomPickHelper.getSelectRoomPosition()).setSelectListener(new RoomSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.group.BaseGroupSettingVM.1
            @Override // com.ltech.smarthome.view.dialog.RoomSelectDialog.SelectListener
            public void confirm(int floorPosition, int roomPosition) {
                BaseGroupSettingVM.this.roomPickHelper.setSelectPosition(floorPosition, roomPosition);
                BaseGroupSettingVM baseGroupSettingVM = BaseGroupSettingVM.this;
                baseGroupSettingVM.changeGroupPlace(baseGroupSettingVM.roomPickHelper.getSelectFloorId(), BaseGroupSettingVM.this.roomPickHelper.getSelectRoomId());
            }

            @Override // com.ltech.smarthome.view.dialog.RoomSelectDialog.SelectListener
            public void onFloorSelect(RoomSelectDialog dialog, int floorPosition) {
                dialog.setRoomList(BaseGroupSettingVM.this.roomPickHelper.getRoomNames(floorPosition));
                dialog.notifyDialog();
            }
        }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
    }

    public void changeGroupPlace(final long floorId, final long roomId) {
        final Group value = this.controlObject.getValue();
        ((ObservableSubscribeProxy) Injection.net().updateGroupLocation(value.getGroupId(), floorId, roomId).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.BaseGroupSettingVM$$ExternalSyntheticLambda13
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseGroupSettingVM.this.lambda$changeGroupPlace$8((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new BaseGroupSettingVM$$ExternalSyntheticLambda8(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.BaseGroupSettingVM$$ExternalSyntheticLambda14
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseGroupSettingVM.this.lambda$changeGroupPlace$9(value, floorId, roomId, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeGroupPlace$8(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeGroupPlace$9(Group group, long j, long j2, Object obj) throws Exception {
        group.setFloorId(j);
        group.setRoomId(j2);
        Injection.repo().group().saveGroup(group);
        this.controlObject.setValue(group);
        SmartToast.showShort(R.string.save_success);
    }

    public void queryScene(final long groupId) {
        ((ObservableSubscribeProxy) Injection.net().queryGroupScene(groupId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.BaseGroupSettingVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseGroupSettingVM.this.lambda$queryScene$10(groupId, (ListSceneResponse) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryScene$10(long j, ListSceneResponse listSceneResponse) throws Exception {
        this.tvSceneNum = listSceneResponse.getTotal();
        queryAutomation(j);
    }

    public void queryAutomation(long groupId) {
        ((ObservableSubscribeProxy) Injection.net().queryGroupAutomation(groupId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.BaseGroupSettingVM$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseGroupSettingVM.this.lambda$queryAutomation$11((ListAutomationResponse) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryAutomation$11(ListAutomationResponse listAutomationResponse) throws Exception {
        int total = listAutomationResponse.getTotal();
        this.tvAutomationNum = total;
        this.getSceneAutomationOver.setValue(Integer.valueOf(this.tvSceneNum + total));
    }

    public void updateParamExt(final Group group, final String extParam) {
        ((ObservableSubscribeProxy) Injection.net().updateGroupParamExt(group.getGroupId(), extParam).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.BaseGroupSettingVM$$ExternalSyntheticLambda11
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseGroupSettingVM.this.lambda$updateParamExt$12(group, extParam, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateParamExt$12(Group group, String str, Object obj) throws Exception {
        group.setExtParam(str);
        Injection.repo().group().saveGroup(group);
        this.controlObject.setValue(group);
        this.refreshEvent.call();
    }

    protected void showEditTimesDialog(final Context context) {
        DeviceFrequencyAndIntervalDialog.asDefault().setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.group.BaseGroupSettingVM$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseGroupSettingVM.this.lambda$showEditTimesDialog$13(context, (DeviceFrequencyAndIntervalDialog.FrequencyAndIntervalBean) obj);
            }
        }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditTimesDialog$13(final Context context, DeviceFrequencyAndIntervalDialog.FrequencyAndIntervalBean frequencyAndIntervalBean) {
        if (frequencyAndIntervalBean.getFrequency() > 10 || frequencyAndIntervalBean.getFrequency() < 1 || frequencyAndIntervalBean.getInterval() > 300 || frequencyAndIntervalBean.getInterval() < 100) {
            SmartToast.showShort(context.getString(R.string.app_str_out_of_range));
        } else {
            CmdAssistant.getSettingCmdAssistant(this.controlObject.getValue(), new int[0]).setDeviceFrequency(context, frequencyAndIntervalBean.getFrequency(), frequencyAndIntervalBean.getInterval(), new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.group.BaseGroupSettingVM.2
                @Override // com.ltech.smarthome.base.IAction
                public void act(Boolean aBoolean) {
                    BaseGroupSettingVM.this.dismissLoadingDialog();
                    SmartToast.showShort(aBoolean.booleanValue() ? context.getString(R.string.save_success) : context.getString(R.string.save_fail));
                }
            });
        }
    }

    protected void showEditTTLDialog(final Context context) {
        EditDialog.asDefault().setHint(context.getString(R.string.app_str_ttl_scope)).setTitle(context.getString(R.string.app_str_change_device_ttl)).setInputType(2).setInputEmptyTip(context.getString(R.string.input_number)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.group.BaseGroupSettingVM$$ExternalSyntheticLambda10
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseGroupSettingVM.this.lambda$showEditTTLDialog$14(context, (String) obj);
            }
        }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditTTLDialog$14(final Context context, String str) {
        if (Integer.parseInt(str) > 32 || Integer.parseInt(str) < 4) {
            SmartToast.showShort(context.getString(R.string.app_str_out_of_range));
        } else {
            showLoadingDialog(context.getString(R.string.saving));
            CmdAssistant.getSettingCmdAssistant(this.controlObject.getValue(), new int[0]).setDeviceTTl(context, Integer.parseInt(str), new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.group.BaseGroupSettingVM.3
                @Override // com.ltech.smarthome.base.IAction
                public void act(Boolean aBoolean) {
                    BaseGroupSettingVM.this.dismissLoadingDialog();
                    SmartToast.showShort(aBoolean.booleanValue() ? context.getString(R.string.save_success) : context.getString(R.string.save_fail));
                }
            });
        }
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
        CmdAssistant.getSettingCmdAssistant(controlObject, new int[0]).setKInfo(context, (1 << relateInfoAssistant.getZoneNumber()) - 1, arrayList, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.group.BaseGroupSettingVM.4
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    BaseGroupSettingVM.this.dismissLoadingDialog();
                    SmartToast.showCenterShort(ActivityUtils.getTopActivity().getString(R.string.calibration_succee));
                } else {
                    BaseGroupSettingVM.this.dismissLoadingDialog();
                    SmartToast.showCenterShort(ActivityUtils.getTopActivity().getString(R.string.calibration_fail));
                }
            }
        });
    }

    public boolean isOwnerOrManager() {
        Place selPlace = Injection.repo().home().getSelPlace();
        if (selPlace != null) {
            return selPlace.isOwner() || selPlace.isManager();
        }
        return false;
    }
}