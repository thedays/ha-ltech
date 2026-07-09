package com.ltech.smarthome.ui.device.microwave_sensor;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActWaveSensorSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.WaveSensorExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.ui.device.microwave_sensor.test.ActSensorNobodyTest;
import com.ltech.smarthome.ui.device.microwave_sensor.test.ActTestModeMain;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.TimeSelectWithLimitDialog;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.ResponseMsg;
import java.util.ArrayList;
import java.util.Locale;

/* loaded from: classes4.dex */
public class ActWaveSensorSetting extends BaseDeviceSetActivity<ActWaveSensorSettingBinding, ActWaveSensorSettingVM> {
    private int ct;
    private MutableLiveData<Integer> delayTime = new MutableLiveData<>(0);
    private WaveSensorExtParam extParam = new WaveSensorExtParam();
    private int lux;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_wave_sensor_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.setting));
        setBackImage(R.mipmap.icon_back);
        ((ActWaveSensorSettingVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActWaveSensorSettingBinding) this.mViewBinding).refreshLayout.setEnableFooterTranslationContent(false);
        ((ActWaveSensorSettingBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((ActWaveSensorSettingBinding) this.mViewBinding).refreshLayout.setFooterHeight(0.0f);
        ((ActWaveSensorSettingBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorSetting$$ExternalSyntheticLambda3
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                ActWaveSensorSetting.this.lambda$initView$0(refreshLayout);
            }
        });
        ((ActWaveSensorSettingBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorSetting$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActWaveSensorSetting.this.lambda$initView$5((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(RefreshLayout refreshLayout) {
        if (((ActWaveSensorSettingVM) this.mViewModel).controlDevice.getValue() != null) {
            querySettingState(((ActWaveSensorSettingVM) this.mViewModel).controlDevice.getValue());
        }
        ((ActWaveSensorSettingBinding) this.mViewBinding).refreshLayout.finishRefresh();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$5(View view) {
        int id = view.getId();
        if (id == R.id.layout_set_ct) {
            EditDialog.asDefault().setHint(getString(R.string.edit_ct_hint)).setTitle(getString(R.string.ct_compensation)).setContent(String.valueOf(this.ct)).setInputType(2).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorSetting$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActWaveSensorSetting.this.lambda$initView$4((String) obj);
                }
            }).showDialog(this);
        } else {
            if (id != R.id.layout_set_lux) {
                return;
            }
            EditDialog.asDefault().setHint(getString(R.string.edit_lux_hint)).setTitle(getString(R.string.lux_compensation)).setContent(String.valueOf(this.lux)).setInputType(2).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorSetting$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActWaveSensorSetting.this.lambda$initView$2((String) obj);
                }
            }).showDialog(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(final String str) {
        if (Integer.parseInt(str) < 0 || Integer.parseInt(str) > 100) {
            SmartToast.showShort(getString(R.string.app_str_out_of_range));
        } else {
            CmdAssistant.getDeviceAssistant(((ActWaveSensorSettingVM) this.mViewModel).controlDevice.getValue(), new int[0]).setLuxCompensation(this, Integer.parseInt(str), new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorSetting$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActWaveSensorSetting.this.lambda$initView$1(str, (Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(String str, Boolean bool) {
        if (bool.booleanValue()) {
            this.lux = Integer.parseInt(str);
            ((ActWaveSensorSettingBinding) this.mViewBinding).tvLuxValue.setText(getString(R.string.lux_value, new Object[]{Integer.valueOf(this.lux)}));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$4(final String str) {
        if (Integer.parseInt(str) < 0 || Integer.parseInt(str) > 10000) {
            SmartToast.showShort(getString(R.string.app_str_out_of_range));
        } else {
            CmdAssistant.getDeviceAssistant(((ActWaveSensorSettingVM) this.mViewModel).controlDevice.getValue(), new int[0]).setCtCompensation(this, Integer.parseInt(str), new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorSetting$$ExternalSyntheticLambda7
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActWaveSensorSetting.this.lambda$initView$3(str, (Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$3(String str, Boolean bool) {
        if (bool.booleanValue()) {
            this.ct = Integer.parseInt(str);
            ((ActWaveSensorSettingBinding) this.mViewBinding).tvCtValue.setText(this.ct + "K");
        }
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActWaveSensorSettingVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorSetting$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActWaveSensorSetting.this.lambda$startObserve$6((Device) obj);
            }
        });
        ((ActWaveSensorSettingVM) this.mViewModel).getSceneAutomationOver.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorSetting$$ExternalSyntheticLambda9
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActWaveSensorSetting.this.lambda$startObserve$7((Integer) obj);
            }
        });
        ((ActWaveSensorSettingVM) this.mViewModel).showAutomationDelayEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorSetting$$ExternalSyntheticLambda10
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActWaveSensorSetting.this.lambda$startObserve$8((Void) obj);
            }
        });
        this.delayTime.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorSetting$$ExternalSyntheticLambda11
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActWaveSensorSetting.this.lambda$startObserve$9((Integer) obj);
            }
        });
        ((ActWaveSensorSettingVM) this.mViewModel).showTestModeEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorSetting$$ExternalSyntheticLambda12
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActWaveSensorSetting.this.lambda$startObserve$13((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(Device device) {
        String floorName;
        ((ActWaveSensorSettingVM) this.mViewModel).is24G = ProductId.ID_SENSOR_MS03.equals(device.getProductId());
        Floor floor = Injection.repo().home().getFloor(device.getFloorId());
        Room room = Injection.repo().home().getRoom(device.getRoomId());
        AppCompatTextView appCompatTextView = ((ActWaveSensorSettingBinding) this.mViewBinding).tvRoomName;
        if (room != null) {
            floorName = floor.getFloorName() + room.getRoomName();
        } else {
            floorName = floor != null ? floor.getFloorName() : "";
        }
        appCompatTextView.setText(floorName);
        ((ActWaveSensorSettingVM) this.mViewModel).roomPickHelper.startObserve(this, device.getPlaceId(), device.getRoomId());
        int illuminance = device.getDeviceState().getWaveSensorState().getIlluminance();
        if (((ActWaveSensorSettingVM) this.mViewModel).is24G) {
            ((ActWaveSensorSettingBinding) this.mViewBinding).tvIlluminance.setText(illuminance == 0 ? getString(R.string.illuminance_value_disable) : getString(R.string.lux_value, new Object[]{Integer.valueOf(this.lux)}));
        } else {
            String[] stringArray = getResources().getStringArray(R.array.wave_sensor_illuminance);
            ((ActWaveSensorSettingBinding) this.mViewBinding).tvIlluminance.setText(illuminance == 0 ? stringArray[stringArray.length - 1] : stringArray[illuminance - 1]);
        }
        ((ActWaveSensorSettingBinding) this.mViewBinding).tvSensitivity.setText(getResources().getStringArray(R.array.wave_sensor_sensitivity)[device.getDeviceState().getWaveSensorState().getSensitivity()]);
        ((ActWaveSensorSettingBinding) this.mViewBinding).layoutSetLux.setVisibility(8);
        ((ActWaveSensorSettingBinding) this.mViewBinding).layoutSetCt.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$7(Integer num) {
        ((ActWaveSensorSettingBinding) this.mViewBinding).tvRelatedNum.setText(String.valueOf(num));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$8(Void r1) {
        showSelectTimeDialog(this.delayTime.getValue().intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$9(Integer num) {
        if (num.intValue() > 0) {
            ((ActWaveSensorSettingBinding) this.mViewBinding).tvDelay.setText(String.format(Locale.US, "%02d" + getString(R.string.min_new) + " %02d" + getString(R.string.sec), Integer.valueOf(num.intValue() / 60), Integer.valueOf(num.intValue() % 60)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$13(Void r3) {
        if (!((ActWaveSensorSettingVM) this.mViewModel).controlDevice.getValue().getDeviceState().getWaveSensorState().isEnable()) {
            SmartToast.showShort(getString(R.string.sensor_off_tip));
        } else {
            MessageDialog.show(this, R.string.test_of_nobody, R.string.test_of_nobody_tip).setOkButton(R.string.nobody_test, new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorSetting$$ExternalSyntheticLambda15
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    boolean lambda$startObserve$11;
                    lambda$startObserve$11 = ActWaveSensorSetting.this.lambda$startObserve$11(baseDialog, view);
                    return lambda$startObserve$11;
                }
            }).setCancelButton(R.string.test_skip, new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorSetting$$ExternalSyntheticLambda16
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    boolean lambda$startObserve$12;
                    lambda$startObserve$12 = ActWaveSensorSetting.this.lambda$startObserve$12(baseDialog, view);
                    return lambda$startObserve$12;
                }
            }).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$startObserve$11(BaseDialog baseDialog, View view) {
        Device value = ((ActWaveSensorSettingVM) this.mViewModel).controlDevice.getValue();
        WaveSensorHelper.instance().controlObject = value;
        showLoadingDialog("");
        CmdAssistant.getDeviceAssistant(WaveSensorHelper.instance().controlObject, new int[0]).setTestMode(this, 1, 4, value.getDeviceState().getWaveSensorState().getSensitivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorSetting$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActWaveSensorSetting.this.lambda$startObserve$10((Boolean) obj);
            }
        });
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$10(Boolean bool) {
        dismissLoadingDialog();
        if (bool.booleanValue()) {
            NavUtils.destination(ActSensorNobodyTest.class).navigation(this);
        } else {
            SmartToast.showCenterShort(getString(R.string.execute_fail));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$startObserve$12(BaseDialog baseDialog, View view) {
        WaveSensorHelper.instance().controlObject = ((ActWaveSensorSettingVM) this.mViewModel).controlDevice.getValue();
        NavUtils.destination(ActTestModeMain.class).navigation(this);
        return false;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        Device deviceById = Injection.repo().device().getDeviceById(((ActWaveSensorSettingVM) this.mViewModel).controlId);
        if (deviceById != null && deviceById.getExtParam() != null) {
            this.extParam.fillMapWithString(deviceById.getExtParam());
            this.delayTime.setValue(Integer.valueOf(this.extParam.getAutomationDelay()));
        }
        ((ActWaveSensorSettingVM) this.mViewModel).controlDevice.setValue(deviceById);
        querySettingState(deviceById);
        if (deviceById != null) {
            ((ActWaveSensorSettingVM) this.mViewModel).queryScene(deviceById.getDeviceId());
        }
    }

    private void querySettingState(final Device device) {
        CmdAssistant.getQueryCmdAssistant(device, new int[0]).queryWaveSensorSetting(this, ProductId.ID_SENSOR_MS03.equals(device.getProductId()), new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorSetting$$ExternalSyntheticLambda13
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActWaveSensorSetting.this.lambda$querySettingState$14(device, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$querySettingState$14(Device device, ResponseMsg responseMsg) {
        if (responseMsg == null || Integer.parseInt(responseMsg.getResData().substring(6, 10), 16) != ((BleParam) device.getParam(BleParam.class)).getUnicastAddress()) {
            return;
        }
        if (responseMsg.getResData().length() >= 36) {
            if (this.mViewBinding != 0) {
                this.delayTime.setValue(Integer.valueOf(Integer.parseInt(responseMsg.getResData().substring(22, 28), 16)));
                this.lux = Integer.parseInt(responseMsg.getResData().substring(28, 32), 16);
                ((ActWaveSensorSettingBinding) this.mViewBinding).tvLuxValue.setText(getString(R.string.lux_value, new Object[]{Integer.valueOf(this.lux)}));
                this.ct = Integer.parseInt(responseMsg.getResData().substring(32, 36), 16);
                ((ActWaveSensorSettingBinding) this.mViewBinding).tvCtValue.setText(getString(R.string.ct_value, new Object[]{Integer.valueOf(this.ct)}));
                ((ActWaveSensorSettingBinding) this.mViewBinding).layoutSetLux.setVisibility(isOwnerOrManager() ? 0 : 8);
                ((ActWaveSensorSettingBinding) this.mViewBinding).layoutSetCt.setVisibility(isOwnerOrManager() ? 0 : 8);
                return;
            }
            return;
        }
        if (responseMsg.getResData().length() < 26 || this.mViewBinding == 0) {
            return;
        }
        this.delayTime.setValue(Integer.valueOf(Integer.parseInt(responseMsg.getResData().substring(20, 26), 16)));
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
        TimeSelectWithLimitDialog.asDefault().setTitle(getString(R.string.please_select_delay_time)).setMinList(arrayList).setSecList(arrayList2).withUnit(true).setMinUnit(getString(R.string.min)).setSecUnit(getString(R.string.sec)).setSelectMinPosition(sec / 60).setSelectSecPosition(sec % 60).setAutomationDelay(true).setSelectListener(new TimeSelectWithLimitDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorSetting$$ExternalSyntheticLambda14
            @Override // com.ltech.smarthome.view.dialog.TimeSelectWithLimitDialog.SelectListener
            public final void confirm(int i3, int i4) {
                ActWaveSensorSetting.this.lambda$showSelectTimeDialog$16(i3, i4);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectTimeDialog$16(final int i, final int i2) {
        CmdAssistant.getDeviceAssistant(((ActWaveSensorSettingVM) this.mViewModel).controlDevice.getValue(), new int[0]).setAutomationDelayTime(this, (i * 60) + i2, new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorSetting$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActWaveSensorSetting.this.lambda$showSelectTimeDialog$15(i, i2, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectTimeDialog$15(int i, int i2, Boolean bool) {
        if (bool.booleanValue()) {
            int i3 = (i * 60) + i2;
            this.delayTime.setValue(Integer.valueOf(i3));
            this.extParam.setAutomationDelay(i3);
            ((ActWaveSensorSettingVM) this.mViewModel).updateParamExt(((ActWaveSensorSettingVM) this.mViewModel).controlDevice.getValue(), this.extParam.getSensorParamMapString());
        }
    }
}