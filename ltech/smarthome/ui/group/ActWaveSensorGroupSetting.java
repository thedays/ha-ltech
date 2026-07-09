package com.ltech.smarthome.ui.group;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActWaveSensorGroupSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.device_param.WaveSensorExtParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.control.ActIntelligence;
import com.ltech.smarthome.ui.device.microwave_sensor.ActIlluminanceSetting;
import com.ltech.smarthome.ui.device.microwave_sensor.ActSensitivitySetting;
import com.ltech.smarthome.ui.device.microwave_sensor.WaveSensorHelper;
import com.ltech.smarthome.ui.device.microwave_sensor.test.ActSensorNobodyTest;
import com.ltech.smarthome.ui.device.microwave_sensor.test.ActTestModeMain;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.EventBusUtils;
import com.ltech.smarthome.utils.LiveBusHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RoomPickHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.RoomSelectDialog;
import com.ltech.smarthome.view.dialog.SelectLuxDialog;
import com.ltech.smarthome.view.dialog.TimeSelectWithLimitDialog;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.ResponseMsg;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActWaveSensorGroupSetting extends VMActivity<ActWaveSensorGroupSettingBinding, BaseGroupSettingVM> {
    private long controlId;
    private int ct;
    public boolean is24G;
    private int lux;
    private Group mGroup;
    public long placeId;
    public RoomPickHelper roomPickHelper = new RoomPickHelper();
    private MutableLiveData<Integer> delayTime = new MutableLiveData<>(0);
    private WaveSensorExtParam extParam = new WaveSensorExtParam();

    static /* synthetic */ boolean lambda$showDelFailDialog$19(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_wave_sensor_group_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.setting));
        this.controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        this.placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActWaveSensorGroupSettingBinding) this.mViewBinding).setManagerOrOwner(Boolean.valueOf(isOwnerOrManager()));
        ((ActWaveSensorGroupSettingBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.group.ActWaveSensorGroupSetting$$ExternalSyntheticLambda12
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActWaveSensorGroupSetting.this.lambda$initView$9((View) obj);
            }
        }));
        ((ActWaveSensorGroupSettingBinding) this.mViewBinding).refreshLayout.setEnableFooterTranslationContent(false);
        ((ActWaveSensorGroupSettingBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((ActWaveSensorGroupSettingBinding) this.mViewBinding).refreshLayout.setFooterHeight(0.0f);
        ((ActWaveSensorGroupSettingBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.group.ActWaveSensorGroupSetting$$ExternalSyntheticLambda13
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                ActWaveSensorGroupSetting.this.lambda$initView$10(refreshLayout);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$9(View view) {
        switch (view.getId()) {
            case R.id.layout_automation_delay /* 2131297357 */:
                showSelectTimeDialog(this.delayTime.getValue().intValue());
                break;
            case R.id.layout_change_room /* 2131297392 */:
                showEditRoomDialog();
                break;
            case R.id.layout_edit_group /* 2131297457 */:
                NavUtils.destination(ProductRepository.isBleGroup(this.mGroup.getModuleType()) ? ActSelectDeviceNew.class : ActAddWiFiGroup.class).withLong(Constants.PLACE_ID, this.mGroup.getPlaceId()).withLong(Constants.FLOOR_ID, this.mGroup.getFloorId()).withLong(Constants.ROOM_ID, this.mGroup.getRoomId()).withLong(Constants.GROUP_ID, this.mGroup.getId()).navigation(this);
                break;
            case R.id.layout_group_name /* 2131297487 */:
                showEditNameDialog();
                break;
            case R.id.layout_scene_and_automation /* 2131297612 */:
                NavUtils.destination(ActIntelligence.class).withLong("device_id", this.mGroup.getGroupId()).withBoolean(Constants.GROUP_CONTROL, true).navigation(this);
                break;
            case R.id.layout_set_ct /* 2131297634 */:
                EditDialog.asDefault().setHint(getString(R.string.edit_ct_hint)).setTitle(getString(R.string.ct_compensation)).setContent(String.valueOf(this.ct)).setInputType(2).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.group.ActWaveSensorGroupSetting$$ExternalSyntheticLambda3
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActWaveSensorGroupSetting.this.lambda$initView$8((String) obj);
                    }
                }).showDialog(this);
                break;
            case R.id.layout_set_illuminance /* 2131297636 */:
                if (this.is24G) {
                    SelectLuxDialog.asDefault().setTitle(getString(R.string.edit)).setLux(this.mGroup.getGroupState().getWaveSensorState().getIlluminance()).setOnSaveListener(new SelectLuxDialog.OnSaveListener() { // from class: com.ltech.smarthome.ui.group.ActWaveSensorGroupSetting$$ExternalSyntheticLambda21
                        @Override // com.ltech.smarthome.view.dialog.SelectLuxDialog.OnSaveListener
                        public final void onSave(int i) {
                            ActWaveSensorGroupSetting.this.lambda$initView$1(i);
                        }
                    }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
                    break;
                } else {
                    NavUtils.destination(ActIlluminanceSetting.class).withLong(Constants.CONTROL_ID, this.controlId).withBoolean(Constants.GROUP_CONTROL, true).navigation(this);
                    break;
                }
            case R.id.layout_set_lux /* 2131297640 */:
                EditDialog.asDefault().setHint(getString(R.string.edit_lux_hint)).setTitle(getString(R.string.lux_compensation)).setContent(String.valueOf(this.lux)).setInputType(2).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.group.ActWaveSensorGroupSetting$$ExternalSyntheticLambda2
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActWaveSensorGroupSetting.this.lambda$initView$6((String) obj);
                    }
                }).showDialog(this);
                break;
            case R.id.layout_set_sensitivity /* 2131297647 */:
                NavUtils.destination(ActSensitivitySetting.class).withLong(Constants.CONTROL_ID, this.controlId).withBoolean(Constants.GROUP_CONTROL, true).navigation(this);
                break;
            case R.id.layout_test_mode /* 2131297680 */:
                if (this.mGroup.getDeviceIds().isEmpty()) {
                    SmartToast.showShort(getString(R.string.app_str_group_empty));
                    break;
                } else if (!this.mGroup.getGroupState().getWaveSensorState().isEnable()) {
                    SmartToast.showShort(getString(R.string.sensor_off_tip));
                    break;
                } else {
                    MessageDialog.show(this, R.string.test_of_nobody, R.string.test_of_nobody_tip).setOkButton(R.string.nobody_test, new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.ActWaveSensorGroupSetting$$ExternalSyntheticLambda22
                        @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                        public final boolean onClick(BaseDialog baseDialog, View view2) {
                            boolean lambda$initView$3;
                            lambda$initView$3 = ActWaveSensorGroupSetting.this.lambda$initView$3(baseDialog, view2);
                            return lambda$initView$3;
                        }
                    }).setCancelButton(R.string.test_skip, new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.ActWaveSensorGroupSetting$$ExternalSyntheticLambda1
                        @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                        public final boolean onClick(BaseDialog baseDialog, View view2) {
                            boolean lambda$initView$4;
                            lambda$initView$4 = ActWaveSensorGroupSetting.this.lambda$initView$4(baseDialog, view2);
                            return lambda$initView$4;
                        }
                    }).show();
                    break;
                }
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
    public /* synthetic */ void lambda$initView$1(final int i) {
        showLoadingDialog("");
        CmdAssistant.getDeviceAssistant(this.mGroup, new int[0]).setIllumincance(this.activity, true, i, new IAction() { // from class: com.ltech.smarthome.ui.group.ActWaveSensorGroupSetting$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActWaveSensorGroupSetting.this.lambda$initView$0(i, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(int i, Boolean bool) {
        if (bool.booleanValue()) {
            this.mGroup.getGroupState().getWaveSensorState().setIlluminance(i);
            Injection.repo().group().saveGroup(this.mGroup);
            ((BaseGroupSettingVM) this.mViewModel).controlObject.setValue(this.mGroup);
            showSuccessDialog(getString(R.string.app_str_setting_success));
            EventBusUtils.post(new LiveBusHelper(11, Integer.valueOf(i)));
            return;
        }
        showErrorDialog(getString(R.string.app_str_setting_failed));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initView$3(BaseDialog baseDialog, View view) {
        Group value = ((BaseGroupSettingVM) this.mViewModel).controlObject.getValue();
        WaveSensorHelper.instance().controlObject = value;
        showLoadingDialog("");
        CmdAssistant.getDeviceAssistant(this.mGroup, new int[0]).setTestMode(this, 1, 4, value.getGroupState().getWaveSensorState().getSensitivity(), new IAction() { // from class: com.ltech.smarthome.ui.group.ActWaveSensorGroupSetting$$ExternalSyntheticLambda16
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActWaveSensorGroupSetting.this.lambda$initView$2((Boolean) obj);
            }
        });
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(Boolean bool) {
        dismissLoadingDialog();
        if (bool.booleanValue()) {
            NavUtils.destination(ActSensorNobodyTest.class).navigation(this);
        } else {
            SmartToast.showCenterShort(getString(R.string.execute_fail));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initView$4(BaseDialog baseDialog, View view) {
        WaveSensorHelper.instance().controlObject = ((BaseGroupSettingVM) this.mViewModel).controlObject.getValue();
        NavUtils.destination(ActTestModeMain.class).navigation(this);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$6(final String str) {
        if (Integer.parseInt(str) < 0 || Integer.parseInt(str) > 100) {
            SmartToast.showShort(getString(R.string.app_str_out_of_range));
        } else {
            CmdAssistant.getDeviceAssistant(this.mGroup, new int[0]).setLuxCompensation(this, Integer.parseInt(str), new IAction() { // from class: com.ltech.smarthome.ui.group.ActWaveSensorGroupSetting$$ExternalSyntheticLambda9
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActWaveSensorGroupSetting.this.lambda$initView$5(str, (Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$5(String str, Boolean bool) {
        if (bool.booleanValue()) {
            this.lux = Integer.parseInt(str);
            ((ActWaveSensorGroupSettingBinding) this.mViewBinding).tvLuxValue.setText(getString(R.string.lux_value, new Object[]{Integer.valueOf(this.lux)}));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$8(final String str) {
        if (Integer.parseInt(str) < 0 || Integer.parseInt(str) > 10000) {
            SmartToast.showShort(getString(R.string.app_str_out_of_range));
        } else {
            CmdAssistant.getDeviceAssistant(this.mGroup, new int[0]).setCtCompensation(this, Integer.parseInt(str), new IAction() { // from class: com.ltech.smarthome.ui.group.ActWaveSensorGroupSetting$$ExternalSyntheticLambda10
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActWaveSensorGroupSetting.this.lambda$initView$7(str, (Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$7(String str, Boolean bool) {
        if (bool.booleanValue()) {
            this.ct = Integer.parseInt(str);
            ((ActWaveSensorGroupSettingBinding) this.mViewBinding).tvCtValue.setText(this.ct + "K");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$10(RefreshLayout refreshLayout) {
        Group group = this.mGroup;
        if (group != null) {
            CmdAssistant.getQueryCmdAssistant(group, new int[0]).queryWaveSensorState(this);
        }
        ((ActWaveSensorGroupSettingBinding) this.mViewBinding).refreshLayout.finishRefresh();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((BaseGroupSettingVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.group.ActWaveSensorGroupSetting$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActWaveSensorGroupSetting.this.lambda$startObserve$11((Group) obj);
            }
        });
        ((BaseGroupSettingVM) this.mViewModel).getSceneAutomationOver.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.group.ActWaveSensorGroupSetting$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActWaveSensorGroupSetting.this.lambda$startObserve$12((Integer) obj);
            }
        });
        this.delayTime.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.group.ActWaveSensorGroupSetting$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActWaveSensorGroupSetting.this.lambda$startObserve$13((Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$11(Group group) {
        this.mGroup = group;
        this.is24G = ProductRepository.getLightColorType((Object) group) == 25;
        ((ActWaveSensorGroupSettingBinding) this.mViewBinding).tvGroupName.setText(group.getGroupName());
        ((ActWaveSensorGroupSettingBinding) this.mViewBinding).tvDeviceCount.setText(String.format(Locale.US, "%d", Integer.valueOf(group.getDeviceIds() != null ? group.getDeviceIds().size() : 0)));
        Floor floor = Injection.repo().home().getFloor(this.mGroup.getFloorId());
        Room room = Injection.repo().home().getRoom(this.mGroup.getRoomId());
        String floorName = floor != null ? floor.getFloorName() : "";
        AppCompatTextView appCompatTextView = ((ActWaveSensorGroupSettingBinding) this.mViewBinding).tvRoomName;
        if (room != null) {
            floorName = floorName + " " + room.getRoomName();
        }
        appCompatTextView.setText(floorName);
        this.roomPickHelper.startObserve(this, this.mGroup.getPlaceId(), this.mGroup.getRoomId());
        int illuminance = this.mGroup.getGroupState().getWaveSensorState().getIlluminance();
        if (this.is24G) {
            ((ActWaveSensorGroupSettingBinding) this.mViewBinding).tvIlluminance.setText(illuminance == 0 ? getString(R.string.illuminance_value_disable) : getString(R.string.lux_value, new Object[]{Integer.valueOf(this.lux)}));
        } else {
            String[] stringArray = getResources().getStringArray(R.array.wave_sensor_illuminance);
            ((ActWaveSensorGroupSettingBinding) this.mViewBinding).tvIlluminance.setText(illuminance == 0 ? stringArray[stringArray.length - 1] : stringArray[illuminance - 1]);
        }
        ((ActWaveSensorGroupSettingBinding) this.mViewBinding).tvSensitivity.setText(getResources().getStringArray(R.array.wave_sensor_sensitivity)[this.mGroup.getGroupState().getWaveSensorState().getSensitivity()]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$12(Integer num) {
        ((ActWaveSensorGroupSettingBinding) this.mViewBinding).tvRelatedNum.setText(String.valueOf(num));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$13(Integer num) {
        if (num.intValue() > 0) {
            ((ActWaveSensorGroupSettingBinding) this.mViewBinding).tvDelay.setText(String.format(Locale.US, "%02d" + getString(R.string.min_new) + " %02d" + getString(R.string.sec), Integer.valueOf(num.intValue() / 60), Integer.valueOf(num.intValue() % 60)));
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        Group groupById = Injection.repo().group().getGroupById(this.controlId);
        this.mGroup = groupById;
        if (groupById != null && groupById.getExtParam() != null) {
            this.extParam.fillMapWithString(this.mGroup.getExtParam());
            this.delayTime.setValue(Integer.valueOf(this.extParam.getAutomationDelay()));
        }
        ((BaseGroupSettingVM) this.mViewModel).controlObject.setValue(this.mGroup);
        querySettingState(this.mGroup);
        ((BaseGroupSettingVM) this.mViewModel).queryScene(this.mGroup.getGroupId());
    }

    private void querySettingState(final Group group) {
        CmdAssistant.getQueryCmdAssistant(group, new int[0]).queryWaveSensorSetting(this, ProductRepository.getLightColorType((Object) group) == 25, new IAction() { // from class: com.ltech.smarthome.ui.group.ActWaveSensorGroupSetting$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActWaveSensorGroupSetting.this.lambda$querySettingState$14(group, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$querySettingState$14(Group group, ResponseMsg responseMsg) {
        if (responseMsg == null || Integer.parseInt(responseMsg.getResData().substring(6, 10), 16) != group.getGroupAddress()) {
            return;
        }
        if (responseMsg.getResData().length() >= 36) {
            if (this.mViewBinding != 0) {
                this.delayTime.setValue(Integer.valueOf(Integer.parseInt(responseMsg.getResData().substring(22, 28), 16)));
                this.lux = Integer.parseInt(responseMsg.getResData().substring(28, 32), 16);
                ((ActWaveSensorGroupSettingBinding) this.mViewBinding).tvLuxValue.setText(getString(R.string.lux_value, new Object[]{Integer.valueOf(this.lux)}));
                this.ct = Integer.parseInt(responseMsg.getResData().substring(32, 36), 16);
                ((ActWaveSensorGroupSettingBinding) this.mViewBinding).tvCtValue.setText(getString(R.string.ct_value, new Object[]{Integer.valueOf(this.ct)}));
                ((ActWaveSensorGroupSettingBinding) this.mViewBinding).layoutSetLux.setVisibility(isOwnerOrManager() ? 0 : 8);
                ((ActWaveSensorGroupSettingBinding) this.mViewBinding).layoutSetCt.setVisibility(isOwnerOrManager() ? 0 : 8);
                return;
            }
            return;
        }
        if (responseMsg.getResData().length() >= 26) {
            int parseInt = Integer.parseInt(responseMsg.getResData().substring(20, 26), 16);
            if (this.mViewBinding != 0) {
                this.delayTime.setValue(Integer.valueOf(parseInt));
            }
        }
    }

    private void showSelectTimeDialog(int sec) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 60; i++) {
            arrayList.add(i < 10 ? "0" + i : String.valueOf(i));
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i2 = 0; i2 < 60; i2++) {
            arrayList2.add(i2 < 10 ? "0" + i2 : String.valueOf(i2));
        }
        TimeSelectWithLimitDialog.asDefault().setTitle(getString(R.string.please_select_delay_time)).setMinList(arrayList).setSecList(arrayList2).withUnit(true).setMinUnit(getString(R.string.min)).setSecUnit(getString(R.string.sec)).setSelectMinPosition(sec / 60).setSelectSecPosition(sec % 60).setAutomationDelay(true).setSelectListener(new TimeSelectWithLimitDialog.SelectListener() { // from class: com.ltech.smarthome.ui.group.ActWaveSensorGroupSetting$$ExternalSyntheticLambda14
            @Override // com.ltech.smarthome.view.dialog.TimeSelectWithLimitDialog.SelectListener
            public final void confirm(int i3, int i4) {
                ActWaveSensorGroupSetting.this.lambda$showSelectTimeDialog$16(i3, i4);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectTimeDialog$16(final int i, final int i2) {
        CmdAssistant.getDeviceAssistant(this.mGroup, new int[0]).setAutomationDelayTime(this, (i * 60) + i2, new IAction() { // from class: com.ltech.smarthome.ui.group.ActWaveSensorGroupSetting$$ExternalSyntheticLambda17
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActWaveSensorGroupSetting.this.lambda$showSelectTimeDialog$15(i, i2, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectTimeDialog$15(int i, int i2, Boolean bool) {
        if (bool.booleanValue()) {
            int i3 = (i * 60) + i2;
            this.delayTime.setValue(Integer.valueOf(i3));
            this.extParam.setAutomationDelay(i3);
            ((BaseGroupSettingVM) this.mViewModel).updateParamExt(this.mGroup, this.extParam.getSensorParamMapString());
        }
    }

    private void showEditNameDialog() {
        EditDialog.asDefault().setContent(this.mGroup.getGroupName()).setTitle(getString(R.string.edit_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.group.ActWaveSensorGroupSetting$$ExternalSyntheticLambda15
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActWaveSensorGroupSetting.this.lambda$showEditNameDialog$17((String) obj);
            }
        }).showDialog(getSupportFragmentManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditNameDialog$17(String str) {
        ((BaseGroupSettingVM) this.mViewModel).lambda$showEditNameDialog$5(str);
    }

    protected void showEditRoomDialog() {
        if (this.roomPickHelper.getCanSetRoom() == null || !this.roomPickHelper.getCanSetRoom().getValue().booleanValue()) {
            return;
        }
        RoomSelectDialog.asDefault().setFloorList(this.roomPickHelper.getCurrentFloorNames()).setRoomList(this.roomPickHelper.getCurrentRoomNames()).setSelectFloorPosition(this.roomPickHelper.getSelectFloorPosition()).setSelectRoomPosition(this.roomPickHelper.getSelectRoomPosition()).setSelectListener(new RoomSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.group.ActWaveSensorGroupSetting.1
            @Override // com.ltech.smarthome.view.dialog.RoomSelectDialog.SelectListener
            public void confirm(int floorPosition, int roomPosition) {
                ActWaveSensorGroupSetting.this.roomPickHelper.setSelectPosition(floorPosition, roomPosition);
                ((BaseGroupSettingVM) ActWaveSensorGroupSetting.this.mViewModel).changeGroupPlace(ActWaveSensorGroupSetting.this.roomPickHelper.getSelectFloorId(), ActWaveSensorGroupSetting.this.roomPickHelper.getSelectRoomId());
            }

            @Override // com.ltech.smarthome.view.dialog.RoomSelectDialog.SelectListener
            public void onFloorSelect(RoomSelectDialog dialog, int floorPosition) {
                dialog.setRoomList(ActWaveSensorGroupSetting.this.roomPickHelper.getRoomNames(floorPosition));
                dialog.notifyDialog();
            }
        }).showDialog(this);
    }

    private void showDelDialog() {
        MessageDialog.show(this, getString(R.string.tips), getString(R.string.tip_del_group)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.ActWaveSensorGroupSetting$$ExternalSyntheticLambda11
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showDelDialog$18;
                lambda$showDelDialog$18 = ActWaveSensorGroupSetting.this.lambda$showDelDialog$18(baseDialog, view);
                return lambda$showDelDialog$18;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showDelDialog$18(BaseDialog baseDialog, View view) {
        delGroupFromNet(this.mGroup);
        return false;
    }

    private void showDelFailDialog() {
        MessageDialog.show(this, getString(R.string.del_fail), getString(R.string.del_fail_tip)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.ActWaveSensorGroupSetting$$ExternalSyntheticLambda7
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return ActWaveSensorGroupSetting.lambda$showDelFailDialog$19(baseDialog, view);
            }
        });
    }

    private void delGroupFromNet(final Group group) {
        ((ObservableSubscribeProxy) Injection.net().deleteGroup(group.getGroupId()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActWaveSensorGroupSetting$$ExternalSyntheticLambda18
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActWaveSensorGroupSetting.this.lambda$delGroupFromNet$20((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.group.ActWaveSensorGroupSetting$$ExternalSyntheticLambda19
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActWaveSensorGroupSetting.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActWaveSensorGroupSetting$$ExternalSyntheticLambda20
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActWaveSensorGroupSetting.this.lambda$delGroupFromNet$21(group, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$delGroupFromNet$20(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.removing));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$delGroupFromNet$21(Group group, Object obj) throws Exception {
        Injection.repo().group().removeGroupFromDb(group.getId());
        setResult(5002);
        finishActivity();
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }
}