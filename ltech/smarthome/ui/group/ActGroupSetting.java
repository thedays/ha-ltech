package com.ltech.smarthome.ui.group;

import android.content.Intent;
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
import com.ltech.smarthome.databinding.ActGroupSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.group.UpdateGroupResponse;
import com.ltech.smarthome.ui.device.light.ActAutoNetTimeSetting;
import com.ltech.smarthome.ui.device.light.ActDiyLightName;
import com.ltech.smarthome.ui.device.light.ActLightOnOffTime;
import com.ltech.smarthome.ui.device.light.ActRhythmsSettingVM;
import com.ltech.smarthome.ui.device.light.ActSetLightOnState;
import com.ltech.smarthome.ui.group.ActGroupSetting;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.IconRepository;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RoomPickHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.RoomSelectDialog;
import com.ltech.smarthome.view.dialog.SelectDeviceIconDialog;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.ltech.smarthome.view.dialog.TimeSelectDialog;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActGroupSetting extends VMActivity<ActGroupSettingBinding, ActRhythmsSettingVM> {
    private Group mGroup;
    private int mSelectHour;
    private int mSelectMin;
    public RoomPickHelper roomPickHelper = new RoomPickHelper();
    final int COUNTS = 10;
    final long DURATION = 5000;
    long[] mHits = new long[10];

    static /* synthetic */ boolean lambda$showDelFailDialog$5(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_group_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.setting));
        setEditString("             ");
        if (this.mViewBinding != 0) {
            ((ActGroupSettingBinding) this.mViewBinding).setManagerOrOwner(Boolean.valueOf(isOwnerOrManager()));
            if (getCurrentPlace() != null && ((ActGroupSettingBinding) this.mViewBinding).getManagerOrOwner().booleanValue()) {
                ((ActGroupSettingBinding) this.mViewBinding).layoutGroupName.setEnabled(true);
                ((ActGroupSettingBinding) this.mViewBinding).layoutEditGroup.setEnabled(true);
                ((ActGroupSettingBinding) this.mViewBinding).tvDelete.setVisibility(0);
                ((ActGroupSettingBinding) this.mViewBinding).ivGroupNameGo.setVisibility(0);
                ((ActGroupSettingBinding) this.mViewBinding).ivDeviceCountGo.setVisibility(0);
                ((ActGroupSettingBinding) this.mViewBinding).layoutChangeRoom.setEnabled(true);
                ((ActGroupSettingBinding) this.mViewBinding).ivRoomNameGo.setVisibility(0);
            } else {
                ((ActGroupSettingBinding) this.mViewBinding).layoutGroupName.setEnabled(false);
                ((ActGroupSettingBinding) this.mViewBinding).layoutEditGroup.setEnabled(false);
                ((ActGroupSettingBinding) this.mViewBinding).tvDelete.setVisibility(8);
                ((ActGroupSettingBinding) this.mViewBinding).ivGroupNameGo.setVisibility(8);
                ((ActGroupSettingBinding) this.mViewBinding).ivDeviceCountGo.setVisibility(8);
                ((ActGroupSettingBinding) this.mViewBinding).layoutChangeRoom.setEnabled(false);
                ((ActGroupSettingBinding) this.mViewBinding).ivRoomNameGo.setVisibility(8);
            }
            ((ActGroupSettingBinding) this.mViewBinding).sbRhythmsText.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.group.ActGroupSetting.1
                @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
                public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                    if (Injection.repo().device().getExistGateway() == null) {
                        ((ActGroupSettingBinding) ActGroupSetting.this.mViewBinding).sbRhythmsText.setCheckedNotByUser(false);
                        ActGroupSetting actGroupSetting = ActGroupSetting.this;
                        actGroupSetting.showErrorDialog(actGroupSetting.getString(R.string.no_super_panel));
                    } else {
                        ((ActRhythmsSettingVM) ActGroupSetting.this.mViewModel).showRhythmsSetting.setValue(Boolean.valueOf(isChecked));
                        ((ActRhythmsSettingVM) ActGroupSetting.this.mViewModel).onOffRhythms(ActGroupSetting.this.activity, isChecked, true);
                    }
                }
            });
            ((ActGroupSettingBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.group.ActGroupSetting$$ExternalSyntheticLambda12
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    ActGroupSetting.this.lambda$initView$0((View) obj);
                }
            }));
        }
        if (getCurrentPlace() != null && (getCurrentPlace().isOwner() || getCurrentPlace().isManager())) {
            ((ActGroupSettingBinding) this.mViewBinding).setManagerOrOwner(true);
        } else {
            ((ActGroupSettingBinding) this.mViewBinding).setManagerOrOwner(false);
        }
        ((ActGroupSettingBinding) this.mViewBinding).refreshLayout.setEnableFooterTranslationContent(false);
        ((ActGroupSettingBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((ActGroupSettingBinding) this.mViewBinding).refreshLayout.setFooterHeight(0.0f);
        ((ActGroupSettingBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.group.ActGroupSetting$$ExternalSyntheticLambda13
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                ActGroupSetting.this.lambda$initView$1(refreshLayout);
            }
        });
        ((ActRhythmsSettingVM) this.mViewModel).getSunTime();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        switch (view.getId()) {
            case R.id.layout_change_icon /* 2131297387 */:
                showSelectDeviceIconDialog();
                break;
            case R.id.layout_change_room /* 2131297392 */:
                showEditRoomDialog();
                break;
            case R.id.layout_edit_group /* 2131297457 */:
                NavUtils.destination(ProductRepository.isBleGroup(this.mGroup.getModuleType()) ? ActSelectDeviceNew.class : ActAddWiFiGroup.class).withLong(Constants.PLACE_ID, this.mGroup.getPlaceId()).withLong(Constants.FLOOR_ID, this.mGroup.getFloorId()).withLong(Constants.ROOM_ID, this.mGroup.getRoomId()).withLong(Constants.GROUP_ID, this.mGroup.getId()).navigation(this);
                break;
            case R.id.layout_group_name /* 2131297487 */:
                String groupKey = this.mGroup.getGroupKey();
                if (groupKey.equals(ProductId.BLE_GROUP_DIM_LIGHT) || groupKey.equals(ProductId.BLE_GROUP_CT_LIGHT) || groupKey.equals(ProductId.BLE_GROUP_RGB_LIGHT) || groupKey.equals(ProductId.BLE_GROUP_RGBW_LIGHT) || groupKey.equals(ProductId.BLE_GROUP_RGBWY_LIGHT)) {
                    NavUtils.destination(ActDiyLightName.class).withLong(Constants.CONTROL_ID, this.mGroup.getId()).withBoolean(Constants.GROUP_CONTROL, true).navigation(ActivityUtils.getTopActivity());
                    break;
                } else {
                    showEditNameDialog();
                    break;
                }
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

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(RefreshLayout refreshLayout) {
        int lightColorType = ProductRepository.getLightColorType((Object) this.mGroup);
        if ((lightColorType == 2 && this.mGroup.getMinkelvin() > 0) || lightColorType == 20) {
            ((ActRhythmsSettingVM) this.mViewModel).queryRhythmsSetting();
        }
        ((ActGroupSettingBinding) this.mViewBinding).refreshLayout.finishRefresh();
    }

    private void showEditNameDialog() {
        EditDialog.asDefault().setContent(this.mGroup.getGroupName()).setTitle(getString(R.string.edit_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.group.ActGroupSetting$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActGroupSetting.this.lambda$showEditNameDialog$2((String) obj);
            }
        }).showDialog(getSupportFragmentManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditNameDialog$2(String str) {
        updateName(this.mGroup, str);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActRhythmsSettingVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.group.ActGroupSetting$$ExternalSyntheticLambda15
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActGroupSetting.this.lambda$startObserve$3(obj);
            }
        });
        ((ActRhythmsSettingVM) this.mViewModel).repeatTimeLiveData.observe(this, new Observer<String>() { // from class: com.ltech.smarthome.ui.group.ActGroupSetting.2
            @Override // androidx.lifecycle.Observer
            public void onChanged(String s) {
                if (TextUtils.isEmpty(s)) {
                    ((ActGroupSettingBinding) ActGroupSetting.this.mViewBinding).repeatWeekTv.setText(ActGroupSetting.this.getString(R.string.only_once));
                } else {
                    ((ActGroupSettingBinding) ActGroupSetting.this.mViewBinding).repeatWeekTv.setText(HelpUtils.getWeeksStringNew(ActGroupSetting.this.activity, s));
                }
            }
        });
        ((ActRhythmsSettingVM) this.mViewModel).selectEndTimeDialogEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.group.ActGroupSetting.3
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActGroupSetting.this.showTimeDialog(false);
            }
        });
        ((ActRhythmsSettingVM) this.mViewModel).selectStarTimeDialogEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.group.ActGroupSetting.4
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActGroupSetting.this.showTimeDialog(true);
            }
        });
        ((ActRhythmsSettingVM) this.mViewModel).rhythmsIsPlay.observe(this, new Observer<Boolean>() { // from class: com.ltech.smarthome.ui.group.ActGroupSetting.5
            @Override // androidx.lifecycle.Observer
            public void onChanged(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    ((ActGroupSettingBinding) ActGroupSetting.this.mViewBinding).ivPlayAnim.start();
                } else {
                    ((ActGroupSettingBinding) ActGroupSetting.this.mViewBinding).ivPlayAnim.stop();
                }
            }
        });
        ((ActRhythmsSettingVM) this.mViewModel).showPlanListEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.group.ActGroupSetting.6
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActGroupSetting.this.showPlanListDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Object obj) {
        if (obj == null) {
            return;
        }
        this.mGroup = (Group) obj;
        ((ActGroupSettingBinding) this.mViewBinding).tvGroupName.setText(this.mGroup.getGroupName());
        ((ActGroupSettingBinding) this.mViewBinding).tvDeviceCount.setText(String.format(Locale.US, "%d", Integer.valueOf(this.mGroup.getDeviceIds() != null ? this.mGroup.getDeviceIds().size() : 0)));
        Floor floor = Injection.repo().home().getFloor(this.mGroup.getFloorId());
        Room room = Injection.repo().home().getRoom(this.mGroup.getRoomId());
        String floorName = floor != null ? floor.getFloorName() : "";
        AppCompatTextView appCompatTextView = ((ActGroupSettingBinding) this.mViewBinding).tvRoomName;
        if (room != null) {
            floorName = floorName + " " + room.getRoomName();
        }
        appCompatTextView.setText(floorName);
        this.roomPickHelper.startObserve(this, this.mGroup.getPlaceId(), this.mGroup.getRoomId());
        int lightColorType = ProductRepository.getLightColorType((Object) this.mGroup);
        if ((lightColorType == 2 && this.mGroup.getMinkelvin() > 0) || lightColorType == 20) {
            ((ActRhythmsSettingVM) this.mViewModel).queryRhythmsSetting();
        }
        int imgindex = this.mGroup.getImgindex();
        int[] lightGroupIconValue = IconRepository.getLightGroupIconValue(this);
        if (imgindex < 0 || imgindex > lightGroupIconValue.length) {
            return;
        }
        ((ActGroupSettingBinding) this.mViewBinding).ivIcon.setImageResource(lightGroupIconValue[imgindex]);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        ((ActRhythmsSettingVM) this.mViewModel).controlObject.setValue(Injection.repo().group().getGroupById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L)));
    }

    private void showDelDialog() {
        MessageDialog.show(this, getString(R.string.tips), getString(R.string.tip_del_group)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.ActGroupSetting$$ExternalSyntheticLambda3
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showDelDialog$4;
                lambda$showDelDialog$4 = ActGroupSetting.this.lambda$showDelDialog$4(baseDialog, view);
                return lambda$showDelDialog$4;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showDelDialog$4(BaseDialog baseDialog, View view) {
        delGroupFromNet(this.mGroup);
        return false;
    }

    private void showDelFailDialog() {
        MessageDialog.show(this, getString(R.string.del_fail), getString(R.string.del_fail_tip)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.ActGroupSetting$$ExternalSyntheticLambda4
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return ActGroupSetting.lambda$showDelFailDialog$5(baseDialog, view);
            }
        });
    }

    private void delGroupFromNet(final Group group) {
        ((ObservableSubscribeProxy) Injection.net().deleteGroup(group.getGroupId()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActGroupSetting$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActGroupSetting.this.lambda$delGroupFromNet$6((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActGroupSetting$$ExternalSyntheticLambda9(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActGroupSetting$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActGroupSetting.this.lambda$delGroupFromNet$7(group, obj);
            }
        }, new SmartErrorComsumer());
        int lightColorType = ProductRepository.getLightColorType((Object) group);
        if (lightColorType == 13 || lightColorType == 15 || lightColorType == 25) {
            return;
        }
        unSubscribePublicationAddress(group);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$delGroupFromNet$6(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.removing));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$delGroupFromNet$7(Group group, Object obj) throws Exception {
        Injection.repo().group().removeGroupFromDb(group.getId());
        Injection.repo().group().removeSubGroupFromDb(group.getPlaceId(), group.getGroupId());
        setResult(5002);
        finishActivity();
    }

    private void unSubscribePublicationAddress(Group group) {
        LHomeLog.i(getClass(), "delete device--->" + group);
        CmdAssistant.getSettingCmdAssistant(null, new int[0]).unSubscribePublishAddress(ActivityUtils.getTopActivity(), group.getGroupAddress(), ProductRepository.getGroupAgreementId(group.getModuleType(), group.getControlType()), new int[0]);
    }

    private void updateName(final Group group, final String name) {
        ((ObservableSubscribeProxy) Injection.net().updateGroupName(group.getGroupId(), name).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActGroupSetting$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActGroupSetting.this.lambda$updateName$8((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActGroupSetting$$ExternalSyntheticLambda9(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActGroupSetting$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActGroupSetting.this.lambda$updateName$9(group, name, (UpdateGroupResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateName$8(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateName$9(Group group, String str, UpdateGroupResponse updateGroupResponse) throws Exception {
        group.setGroupName(str);
        Injection.repo().group().saveGroup(group);
        ((ActRhythmsSettingVM) this.mViewModel).controlObject.setValue(group);
        SmartToast.showShort(getString(R.string.save_success));
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    protected void showEditRoomDialog() {
        if (this.roomPickHelper.getCanSetRoom() == null || !this.roomPickHelper.getCanSetRoom().getValue().booleanValue()) {
            return;
        }
        RoomSelectDialog.asDefault().setFloorList(this.roomPickHelper.getCurrentFloorNames()).setRoomList(this.roomPickHelper.getCurrentRoomNames()).setSelectFloorPosition(this.roomPickHelper.getSelectFloorPosition()).setSelectRoomPosition(this.roomPickHelper.getSelectRoomPosition()).setSelectListener(new RoomSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.group.ActGroupSetting.7
            @Override // com.ltech.smarthome.view.dialog.RoomSelectDialog.SelectListener
            public void confirm(int floorPosition, int roomPosition) {
                ActGroupSetting.this.roomPickHelper.setSelectPosition(floorPosition, roomPosition);
                ActGroupSetting actGroupSetting = ActGroupSetting.this;
                actGroupSetting.changeGroupPlace(actGroupSetting.roomPickHelper.getSelectFloorId(), ActGroupSetting.this.roomPickHelper.getSelectRoomId());
            }

            @Override // com.ltech.smarthome.view.dialog.RoomSelectDialog.SelectListener
            public void onFloorSelect(RoomSelectDialog dialog, int floorPosition) {
                dialog.setRoomList(ActGroupSetting.this.roomPickHelper.getRoomNames(floorPosition));
                dialog.notifyDialog();
            }
        }).showDialog(this);
    }

    public void changeGroupPlace(final long floorId, final long roomId) {
        ((ObservableSubscribeProxy) Injection.net().updateGroupLocation(this.mGroup.getGroupId(), floorId, roomId).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActGroupSetting$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActGroupSetting.this.lambda$changeGroupPlace$10((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActGroupSetting$$ExternalSyntheticLambda9(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActGroupSetting$$ExternalSyntheticLambda10
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActGroupSetting.this.lambda$changeGroupPlace$11(floorId, roomId, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeGroupPlace$10(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeGroupPlace$11(long j, long j2, Object obj) throws Exception {
        this.mGroup.setFloorId(j);
        this.mGroup.setRoomId(j2);
        Injection.repo().group().saveGroup(this.mGroup);
        ((ActRhythmsSettingVM) this.mViewModel).controlObject.setValue(this.mGroup);
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
                    showBindDialog();
                }
            }
        }
    }

    private void showBindDialog() {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.app_str_engineering_mode)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add("TTL");
        arrayList.add(getString(R.string.auto_net_time));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.group.ActGroupSetting$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActGroupSetting.this.lambda$showBindDialog$12((Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showBindDialog$12(Integer num) {
        int intValue = num.intValue();
        if (intValue == 0) {
            showEditTTLDialog();
        } else {
            if (intValue != 1) {
                return;
            }
            NavUtils.destination(ActAutoNetTimeSetting.class).withLong(Constants.CONTROL_ID, this.mGroup.getId()).withBoolean(Constants.GROUP_CONTROL, true).navigation(this);
        }
    }

    protected void showEditTTLDialog() {
        EditDialog.asDefault().setHint(getString(R.string.app_str_ttl_scope)).setTitle(getString(R.string.app_str_change_device_ttl)).setInputType(2).setInputEmptyTip(getString(R.string.input_number)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.group.ActGroupSetting$$ExternalSyntheticLambda14
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActGroupSetting.this.lambda$showEditTTLDialog$13((String) obj);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditTTLDialog$13(String str) {
        if (Integer.parseInt(str) > 32 || Integer.parseInt(str) < 4) {
            SmartToast.showShort(getString(R.string.app_str_out_of_range));
        } else {
            CmdAssistant.getSettingCmdAssistant(this.mGroup, new int[0]).setDeviceTTl(this.activity, Integer.parseInt(str), new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.group.ActGroupSetting.8
                @Override // com.ltech.smarthome.base.IAction
                public void act(Boolean aBoolean) {
                    ActGroupSetting actGroupSetting;
                    int i;
                    ActGroupSetting.this.dismissLoadingDialog();
                    if (aBoolean.booleanValue()) {
                        actGroupSetting = ActGroupSetting.this;
                        i = R.string.save_success;
                    } else {
                        actGroupSetting = ActGroupSetting.this;
                        i = R.string.save_fail;
                    }
                    SmartToast.showShort(actGroupSetting.getString(i));
                }
            });
        }
    }

    public Place getCurPlace() {
        return Injection.repo().home().getSelPlace();
    }

    public void showTimeDialog(final boolean isStart) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 24; i++) {
            arrayList.add(i < 10 ? "0" + i : String.valueOf(i));
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i2 = 0; i2 < 60; i2++) {
            arrayList2.add(i2 < 10 ? "0" + i2 : String.valueOf(i2));
        }
        ActRhythmsSettingVM actRhythmsSettingVM = (ActRhythmsSettingVM) this.mViewModel;
        this.mSelectHour = isStart ? actRhythmsSettingVM.getStarH() : actRhythmsSettingVM.getEndH();
        ActRhythmsSettingVM actRhythmsSettingVM2 = (ActRhythmsSettingVM) this.mViewModel;
        this.mSelectMin = isStart ? actRhythmsSettingVM2.getStarM() : actRhythmsSettingVM2.getEndM();
        TimeSelectDialog.asDefault().setTitle(getString(isStart ? R.string.start_time : R.string.end_time)).setMinList(arrayList).setSecList(arrayList2).withUnit(false).setMinUnit(getString(R.string.hour)).setSecUnit(getString(R.string.min)).setSelectMinPosition(this.mSelectHour).setSelectSecPosition(this.mSelectMin).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.group.ActGroupSetting$$ExternalSyntheticLambda16
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i3, int i4) {
                ActGroupSetting.this.lambda$showTimeDialog$14(isStart, i3, i4);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showTimeDialog$14(boolean z, int i, int i2) {
        if (this.mSelectHour == i && this.mSelectMin == i2) {
            return;
        }
        this.mSelectHour = i;
        this.mSelectMin = i2;
        if (z) {
            ((ActRhythmsSettingVM) this.mViewModel).setStarTime(this.mSelectHour, this.mSelectMin);
        } else {
            ((ActRhythmsSettingVM) this.mViewModel).setEndTime(this.mSelectHour, this.mSelectMin);
        }
    }

    private void showSelectDeviceIconDialog() {
        SelectDeviceIconDialog.asDefault().setGroupTag(true).imageIndex(this.mGroup.getImgindex()).setOnSaveListener(new AnonymousClass9()).showDialog(this);
    }

    /* renamed from: com.ltech.smarthome.ui.group.ActGroupSetting$9, reason: invalid class name */
    class AnonymousClass9 implements SelectDeviceIconDialog.OnSaveListener {
        @Override // com.ltech.smarthome.view.dialog.SelectDeviceIconDialog.OnSaveListener
        public void cancel() {
        }

        AnonymousClass9() {
        }

        @Override // com.ltech.smarthome.view.dialog.SelectDeviceIconDialog.OnSaveListener
        public boolean onSave(final int selectPos) {
            ((ObservableSubscribeProxy) Injection.net().updateGroupDeviceIcon(ActGroupSetting.this.mGroup.getGroupId(), selectPos).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActGroupSetting$9$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActGroupSetting.AnonymousClass9.this.lambda$onSave$0((Disposable) obj);
                }
            }).compose(RxUtils.io_main()).doFinally(new ActGroupSetting$$ExternalSyntheticLambda9(ActGroupSetting.this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(ActGroupSetting.this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActGroupSetting$9$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActGroupSetting.AnonymousClass9.this.lambda$onSave$1(selectPos, obj);
                }
            }, new SmartErrorComsumer());
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSave$0(Disposable disposable) throws Exception {
            ActGroupSetting actGroupSetting = ActGroupSetting.this;
            actGroupSetting.showLoadingDialog(actGroupSetting.getString(R.string.saving));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSave$1(int i, Object obj) throws Exception {
            SmartToast.showShort(ActGroupSetting.this.getString(R.string.save_success));
            ActGroupSetting.this.mGroup.setImgindex(i);
            Injection.repo().group().saveGroup(ActGroupSetting.this.mGroup);
            ((ActRhythmsSettingVM) ActGroupSetting.this.mViewModel).controlObject.setValue(ActGroupSetting.this.mGroup);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPlanListDialog() {
        SelectListDialog selectPosition = SelectListDialog.asDefault(true).setTitle(getString(R.string.setting)).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.save)).setSelectPosition(-1);
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.group.ActGroupSetting$$ExternalSyntheticLambda11
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActGroupSetting.this.lambda$showPlanListDialog$15((Integer) obj);
            }
        }).setSelectList(((ActRhythmsSettingVM) this.mViewModel).rhythmsNameList);
        selectPosition.setOutCancel(false);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPlanListDialog$15(Integer num) {
        ((ActRhythmsSettingVM) this.mViewModel).setPlan(num.intValue() + 1);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 3004 || data == null) {
            return;
        }
        ((ActRhythmsSettingVM) this.mViewModel).setWeek(data.getStringExtra(Constants.WEEKS));
    }
}