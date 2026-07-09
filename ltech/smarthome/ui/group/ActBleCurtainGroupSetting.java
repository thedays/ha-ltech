package com.ltech.smarthome.ui.group;

import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActGroupCurtainSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.group.UpdateGroupResponse;
import com.ltech.smarthome.ui.device.curtain_motor.CurtainMotorInfoExtParam;
import com.ltech.smarthome.ui.device.light.ActLightOnOffTime;
import com.ltech.smarthome.ui.device.light.ActSetLightOnState;
import com.ltech.smarthome.ui.device.trig.ActBleCurtainMotorTypeSetting;
import com.ltech.smarthome.ui.group.ActBleCurtainGroupSetting;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RoomPickHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.RoomSelectDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActBleCurtainGroupSetting extends VMActivity<ActGroupCurtainSettingBinding, BaseGroupSettingVM> {
    private Group mGroup;
    public RoomPickHelper roomPickHelper = new RoomPickHelper();
    final int COUNTS = 10;
    final long DURATION = 5000;
    long[] mHits = new long[10];

    static /* synthetic */ boolean lambda$showDelFailDialog$3(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_group_curtain_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.setting));
        setEditString("             ");
        if (this.mViewBinding != 0) {
            if (getCurrentPlace() != null && (getCurrentPlace().isOwner() || getCurrentPlace().isManager())) {
                ((ActGroupCurtainSettingBinding) this.mViewBinding).layoutGroupName.setEnabled(true);
                ((ActGroupCurtainSettingBinding) this.mViewBinding).layoutEditGroup.setEnabled(true);
                ((ActGroupCurtainSettingBinding) this.mViewBinding).tvDelete.setVisibility(0);
                ((ActGroupCurtainSettingBinding) this.mViewBinding).ivGroupNameGo.setVisibility(0);
                ((ActGroupCurtainSettingBinding) this.mViewBinding).ivDeviceCountGo.setVisibility(0);
                ((ActGroupCurtainSettingBinding) this.mViewBinding).layoutChangeRoom.setEnabled(true);
                ((ActGroupCurtainSettingBinding) this.mViewBinding).ivRoomNameGo.setVisibility(0);
            } else {
                ((ActGroupCurtainSettingBinding) this.mViewBinding).layoutGroupName.setEnabled(false);
                ((ActGroupCurtainSettingBinding) this.mViewBinding).layoutEditGroup.setEnabled(false);
                ((ActGroupCurtainSettingBinding) this.mViewBinding).tvDelete.setVisibility(8);
                ((ActGroupCurtainSettingBinding) this.mViewBinding).ivGroupNameGo.setVisibility(8);
                ((ActGroupCurtainSettingBinding) this.mViewBinding).ivDeviceCountGo.setVisibility(8);
                ((ActGroupCurtainSettingBinding) this.mViewBinding).layoutChangeRoom.setEnabled(false);
                ((ActGroupCurtainSettingBinding) this.mViewBinding).ivRoomNameGo.setVisibility(8);
            }
            ((ActGroupCurtainSettingBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.group.ActBleCurtainGroupSetting$$ExternalSyntheticLambda6
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    ActBleCurtainGroupSetting.this.lambda$initView$0((View) obj);
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        switch (view.getId()) {
            case R.id.layout_change_room /* 2131297392 */:
                showEditRoomDialog();
                break;
            case R.id.layout_edit_group /* 2131297457 */:
                NavUtils.destination(ProductRepository.isBleGroup(this.mGroup.getModuleType()) ? ActSelectDeviceNew.class : ActAddWiFiGroup.class).withLong(Constants.PLACE_ID, this.mGroup.getPlaceId()).withLong(Constants.FLOOR_ID, this.mGroup.getFloorId()).withLong(Constants.ROOM_ID, this.mGroup.getRoomId()).withLong(Constants.GROUP_ID, this.mGroup.getId()).navigation(this);
                break;
            case R.id.layout_group_name /* 2131297487 */:
                showEditNameDialog();
                break;
            case R.id.layout_motor_open_type /* 2131297546 */:
                NavUtils.destination(ActBleCurtainMotorTypeSetting.class).withBoolean(Constants.GROUP_CONTROL, true).withLong(Constants.CONTROL_ID, this.mGroup.getId()).navigation(this);
                break;
            case R.id.layout_on_off_time /* 2131297559 */:
                NavUtils.destination(ActLightOnOffTime.class).withLong(Constants.CONTROL_ID, this.mGroup.getId()).withBoolean(Constants.GROUP_CONTROL, true).withDefaultRequestCode().navigation(this);
                break;
            case R.id.layout_set_on_state /* 2131297646 */:
                NavUtils.destination(ActSetLightOnState.class).withLong(Constants.CONTROL_ID, this.mGroup.getId()).withBoolean(Constants.GROUP_CONTROL, true).withDefaultRequestCode().navigation(this);
                break;
            case R.id.tv_delete /* 2131298575 */:
                if (ProductRepository.isBleGroup(this.mGroup.getModuleType()) && !this.mGroup.getDeviceIds().isEmpty()) {
                    showDelFailDialog();
                    break;
                } else {
                    showDelDialog();
                    break;
                }
                break;
        }
    }

    private void showEditNameDialog() {
        EditDialog.asDefault().setContent(this.mGroup.getGroupName()).setTitle(getString(R.string.edit_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.group.ActBleCurtainGroupSetting$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActBleCurtainGroupSetting.this.lambda$showEditNameDialog$1((String) obj);
            }
        }).showDialog(getSupportFragmentManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditNameDialog$1(String str) {
        updateName(this.mGroup, str);
    }

    /* renamed from: com.ltech.smarthome.ui.group.ActBleCurtainGroupSetting$1, reason: invalid class name */
    class AnonymousClass1 implements Observer<Group> {
        AnonymousClass1() {
        }

        @Override // androidx.lifecycle.Observer
        public void onChanged(final Group group) {
            ActBleCurtainGroupSetting.this.mGroup = group;
            ((ActGroupCurtainSettingBinding) ActBleCurtainGroupSetting.this.mViewBinding).tvGroupName.setText(group.getGroupName());
            ((ActGroupCurtainSettingBinding) ActBleCurtainGroupSetting.this.mViewBinding).tvDeviceCount.setText(String.format(Locale.US, "%d", Integer.valueOf(group.getDeviceIds() != null ? group.getDeviceIds().size() : 0)));
            Floor floor = Injection.repo().home().getFloor(ActBleCurtainGroupSetting.this.mGroup.getFloorId());
            Room room = Injection.repo().home().getRoom(ActBleCurtainGroupSetting.this.mGroup.getRoomId());
            String floorName = floor != null ? floor.getFloorName() : "";
            AppCompatTextView appCompatTextView = ((ActGroupCurtainSettingBinding) ActBleCurtainGroupSetting.this.mViewBinding).tvRoomName;
            if (room != null) {
                floorName = floorName + " " + room.getRoomName();
            }
            appCompatTextView.setText(floorName);
            RoomPickHelper roomPickHelper = ActBleCurtainGroupSetting.this.roomPickHelper;
            ActBleCurtainGroupSetting actBleCurtainGroupSetting = ActBleCurtainGroupSetting.this;
            roomPickHelper.startObserve(actBleCurtainGroupSetting, actBleCurtainGroupSetting.mGroup.getPlaceId(), ActBleCurtainGroupSetting.this.mGroup.getRoomId());
            final CurtainMotorInfoExtParam curtainMotorInfoExtParam = new CurtainMotorInfoExtParam();
            if (ActBleCurtainGroupSetting.this.mGroup.getExtParam() != null && !TextUtils.isEmpty(ActBleCurtainGroupSetting.this.mGroup.getExtParam())) {
                curtainMotorInfoExtParam.fillMapWithString(ActBleCurtainGroupSetting.this.mGroup.getExtParam());
            }
            ((ActGroupCurtainSettingBinding) ActBleCurtainGroupSetting.this.mViewBinding).ivMotorDirectionNameGo.setVisibility(0);
            ((ActGroupCurtainSettingBinding) ActBleCurtainGroupSetting.this.mViewBinding).layoutMotorOpenType.setEnabled(true);
            ((ActGroupCurtainSettingBinding) ActBleCurtainGroupSetting.this.mViewBinding).layoutMotorOpenType.setVisibility(0);
            ((ActGroupCurtainSettingBinding) ActBleCurtainGroupSetting.this.mViewBinding).tvOpenType.setText(ActBleCurtainGroupSetting.this.getString(curtainMotorInfoExtParam.getOpenType() == 0 ? R.string.app_curtain_open_two_way : R.string.app_curtain_open_one_way));
            if (group.getDeviceIds().size() > 0) {
                Iterator<Long> it = group.getDeviceIds().iterator();
                int i = 0;
                while (it.hasNext()) {
                    Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(it.next().longValue());
                    if (deviceByDeviceId != null && deviceByDeviceId.getProductId().equals(ProductId.ID_BLE_CURTAIN_CG_CURH3)) {
                        i++;
                    }
                }
                if (i == group.getDeviceIds().size()) {
                    ((ActGroupCurtainSettingBinding) ActBleCurtainGroupSetting.this.mViewBinding).layoutMotorOpenType.setVisibility(8);
                } else if (i > 0 && i < group.getDeviceIds().size()) {
                    ((ActGroupCurtainSettingBinding) ActBleCurtainGroupSetting.this.mViewBinding).layoutMotorOpenType.setVisibility(0);
                    ((ActGroupCurtainSettingBinding) ActBleCurtainGroupSetting.this.mViewBinding).layoutMotorOpenType.setEnabled(false);
                    ((ActGroupCurtainSettingBinding) ActBleCurtainGroupSetting.this.mViewBinding).ivMotorDirectionNameGo.setVisibility(8);
                    ((ActGroupCurtainSettingBinding) ActBleCurtainGroupSetting.this.mViewBinding).tvOpenType.setText(ActBleCurtainGroupSetting.this.getString(R.string.app_curtain_open_two_way));
                }
                if (i > 0) {
                    curtainMotorInfoExtParam.setOpenType(0);
                    ((ObservableSubscribeProxy) Injection.net().updateGroupParamExt(group.getGroupId(), curtainMotorInfoExtParam.getParamMapString()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(ActBleCurtainGroupSetting.this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActBleCurtainGroupSetting$1$$ExternalSyntheticLambda0
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj) {
                            ActBleCurtainGroupSetting.AnonymousClass1.lambda$onChanged$0(Group.this, curtainMotorInfoExtParam, obj);
                        }
                    }, new SmartErrorComsumer());
                }
            }
        }

        static /* synthetic */ void lambda$onChanged$0(Group group, CurtainMotorInfoExtParam curtainMotorInfoExtParam, Object obj) throws Exception {
            group.setExtParam(curtainMotorInfoExtParam.getParamMapString());
            Injection.repo().group().saveGroup(group);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((BaseGroupSettingVM) this.mViewModel).controlObject.observe(this, new AnonymousClass1());
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        ((BaseGroupSettingVM) this.mViewModel).controlObject.setValue(Injection.repo().group().getGroupById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L)));
    }

    private void showDelDialog() {
        MessageDialog.show(this, getString(R.string.tips), getString(R.string.tip_del_group)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.ActBleCurtainGroupSetting$$ExternalSyntheticLambda10
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showDelDialog$2;
                lambda$showDelDialog$2 = ActBleCurtainGroupSetting.this.lambda$showDelDialog$2(baseDialog, view);
                return lambda$showDelDialog$2;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showDelDialog$2(BaseDialog baseDialog, View view) {
        delGroupFromNet(this.mGroup);
        return false;
    }

    private void showDelFailDialog() {
        MessageDialog.show(this, getString(R.string.del_fail), getString(R.string.del_fail_tip)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.ActBleCurtainGroupSetting$$ExternalSyntheticLambda9
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return ActBleCurtainGroupSetting.lambda$showDelFailDialog$3(baseDialog, view);
            }
        });
    }

    private void delGroupFromNet(final Group group) {
        ((ObservableSubscribeProxy) Injection.net().deleteGroup(group.getGroupId()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActBleCurtainGroupSetting$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActBleCurtainGroupSetting.this.lambda$delGroupFromNet$4((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActBleCurtainGroupSetting$$ExternalSyntheticLambda2(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActBleCurtainGroupSetting$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActBleCurtainGroupSetting.this.lambda$delGroupFromNet$5(group, obj);
            }
        }, new SmartErrorComsumer());
        int lightColorType = ProductRepository.getLightColorType((Object) group);
        if (lightColorType == 1 || lightColorType == 2 || lightColorType == 3 || lightColorType == 4 || lightColorType == 5) {
            unSubscribePublicationAddress(group);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$delGroupFromNet$4(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.removing));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$delGroupFromNet$5(Group group, Object obj) throws Exception {
        Injection.repo().group().removeGroupFromDb(group.getId());
        setResult(5002);
        finishActivity();
    }

    private void unSubscribePublicationAddress(Group group) {
        LHomeLog.i(getClass(), "delete device--->" + group);
        CmdAssistant.getSettingCmdAssistant(null, new int[0]).unSubscribePublishAddress(ActivityUtils.getTopActivity(), group.getGroupAddress(), ProductRepository.getGroupAgreementId(group.getModuleType(), group.getControlType()), new int[0]);
    }

    private void updateName(final Group group, final String name) {
        ((ObservableSubscribeProxy) Injection.net().updateGroupName(group.getGroupId(), name).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActBleCurtainGroupSetting$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActBleCurtainGroupSetting.this.lambda$updateName$6((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActBleCurtainGroupSetting$$ExternalSyntheticLambda2(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActBleCurtainGroupSetting$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActBleCurtainGroupSetting.this.lambda$updateName$7(group, name, (UpdateGroupResponse) obj);
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
        SmartToast.showShort(getString(R.string.save_success));
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    protected void showEditRoomDialog() {
        if (this.roomPickHelper.getCanSetRoom() == null || !this.roomPickHelper.getCanSetRoom().getValue().booleanValue()) {
            return;
        }
        RoomSelectDialog.asDefault().setFloorList(this.roomPickHelper.getCurrentFloorNames()).setRoomList(this.roomPickHelper.getCurrentRoomNames()).setSelectFloorPosition(this.roomPickHelper.getSelectFloorPosition()).setSelectRoomPosition(this.roomPickHelper.getSelectRoomPosition()).setSelectListener(new RoomSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.group.ActBleCurtainGroupSetting.2
            @Override // com.ltech.smarthome.view.dialog.RoomSelectDialog.SelectListener
            public void confirm(int floorPosition, int roomPosition) {
                ActBleCurtainGroupSetting.this.roomPickHelper.setSelectPosition(floorPosition, roomPosition);
                ActBleCurtainGroupSetting actBleCurtainGroupSetting = ActBleCurtainGroupSetting.this;
                actBleCurtainGroupSetting.changeGroupPlace(actBleCurtainGroupSetting.roomPickHelper.getSelectFloorId(), ActBleCurtainGroupSetting.this.roomPickHelper.getSelectRoomId());
            }

            @Override // com.ltech.smarthome.view.dialog.RoomSelectDialog.SelectListener
            public void onFloorSelect(RoomSelectDialog dialog, int floorPosition) {
                dialog.setRoomList(ActBleCurtainGroupSetting.this.roomPickHelper.getRoomNames(floorPosition));
                dialog.notifyDialog();
            }
        }).showDialog(this);
    }

    public void changeGroupPlace(final long floorId, final long roomId) {
        ((ObservableSubscribeProxy) Injection.net().updateGroupLocation(this.mGroup.getGroupId(), floorId, roomId).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActBleCurtainGroupSetting$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActBleCurtainGroupSetting.this.lambda$changeGroupPlace$8((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActBleCurtainGroupSetting$$ExternalSyntheticLambda2(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActBleCurtainGroupSetting$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActBleCurtainGroupSetting.this.lambda$changeGroupPlace$9(floorId, roomId, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeGroupPlace$8(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeGroupPlace$9(long j, long j2, Object obj) throws Exception {
        this.mGroup.setFloorId(j);
        this.mGroup.setRoomId(j2);
        Injection.repo().group().saveGroup(this.mGroup);
        SmartToast.showShort(R.string.save_success);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        if (getCurPlace() != null) {
            if (getCurPlace().isManager() || getCurPlace().isOwner()) {
                long[] jArr = this.mHits;
                System.arraycopy(jArr, 1, jArr, 0, jArr.length - 1);
                long[] jArr2 = this.mHits;
                jArr2[jArr2.length - 1] = SystemClock.uptimeMillis();
                if (this.mHits[0] >= SystemClock.uptimeMillis() - 5000) {
                    ((BaseGroupSettingVM) this.mViewModel).showEditTTLDialog(this);
                }
            }
        }
    }

    public Place getCurPlace() {
        return Injection.repo().home().getSelPlace();
    }
}