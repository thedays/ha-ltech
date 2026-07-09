package com.ltech.smarthome.ui.device.microwave_sensor;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActWaveSensorEffectPeriodBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.WaveSensorExtParam;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.automation.ActSelectWeek;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.ltech.smarthome.view.dialog.TimeSelectDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.ResponseMsg;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActWaveSensorEffectPeriod extends BaseNormalActivity<ActWaveSensorEffectPeriodBinding> {
    private List<String> hourList;
    private int index;
    private List<String> minList;
    private int triggerFlag;
    private String weeks;
    public MutableLiveData<String> repeatTimeLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> showNextDay = new MutableLiveData<>();
    public SingleLiveEvent<Void> showEditStartTimeDialog = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showEditEndTimeDialog = new SingleLiveEvent<>();
    private int startHourPos = 0;
    private int endHourPos = 0;
    private int startMinPos = 0;
    private int endMinPos = 0;
    private WaveSensorExtParam param = new WaveSensorExtParam();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_wave_sensor_effect_period;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.effective_period));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
        this.weeks = getIntent().getStringExtra(Constants.WEEKS);
        this.index = getIntent().getIntExtra(Constants.WAVE_INDEX, 0);
        this.param.fillMapWithString(WaveSensorHelper.instance().extParam.getSensorParamMapString());
        this.startHourPos = this.param.getEffectTimeStartH(this.index);
        this.startMinPos = this.param.getEffectTimeStartM(this.index);
        this.endHourPos = this.param.getEffectTimeEndH(this.index);
        this.endMinPos = this.param.getEffectTimeEndM(this.index);
        this.triggerFlag = (this.param.getEffectTimeRepeat(this.index) & 128) == 0 ? 0 : 1;
        ((ActWaveSensorEffectPeriodBinding) this.mViewBinding).tvTriggerTime.setText(getString(this.triggerFlag == 0 ? R.string.trigger_repeat : R.string.only_once));
        this.hourList = new ArrayList();
        for (int i = 0; i < 24; i++) {
            this.hourList.add(i < 10 ? "0" + i : String.valueOf(i));
        }
        this.minList = new ArrayList();
        for (int i2 = 0; i2 < 60; i2++) {
            this.minList.add(i2 < 10 ? "0" + i2 : String.valueOf(i2));
        }
        this.showNextDay.setValue(Boolean.valueOf(isNextDay()));
        ((ActWaveSensorEffectPeriodBinding) this.mViewBinding).setStartTime(getTimeString(this.startHourPos, this.startMinPos));
        ((ActWaveSensorEffectPeriodBinding) this.mViewBinding).setEndTime(getTimeString(this.endHourPos, this.endMinPos));
        this.repeatTimeLiveData.setValue(this.weeks);
        ((ActWaveSensorEffectPeriodBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorEffectPeriod$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActWaveSensorEffectPeriod.this.lambda$initView$0((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        switch (view.getId()) {
            case R.id.tv_end_time /* 2131298626 */:
                this.showEditEndTimeDialog.call();
                break;
            case R.id.tv_start_time /* 2131298991 */:
                this.showEditStartTimeDialog.call();
                break;
            case R.id.v_select_time /* 2131299162 */:
                NavUtils.destination(ActSelectWeek.class).withString(Constants.WEEKS, this.weeks).withDefaultRequestCode().navigation(this);
                break;
            case R.id.v_set_trigger_times /* 2131299163 */:
                showTriggerTimeDialog();
                break;
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        this.showEditStartTimeDialog.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorEffectPeriod$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActWaveSensorEffectPeriod.this.lambda$startObserve$2((Void) obj);
            }
        });
        this.showEditEndTimeDialog.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorEffectPeriod$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActWaveSensorEffectPeriod.this.lambda$startObserve$4((Void) obj);
            }
        });
        this.repeatTimeLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorEffectPeriod$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActWaveSensorEffectPeriod.this.lambda$startObserve$5((String) obj);
            }
        });
        this.showNextDay.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorEffectPeriod$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActWaveSensorEffectPeriod.this.lambda$startObserve$6((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r4) {
        showEditTimeDialog(true, this.startHourPos, this.startMinPos, new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorEffectPeriod$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i, int i2) {
                ActWaveSensorEffectPeriod.this.lambda$startObserve$1(i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(int i, int i2) {
        this.startHourPos = i;
        this.startMinPos = i2;
        this.param.setEffectTimeStartH(this.index, i);
        this.param.setEffectTimeStartM(this.index, this.startMinPos);
        ((ActWaveSensorEffectPeriodBinding) this.mViewBinding).setStartTime(getTimeString(i, i2));
        this.showNextDay.setValue(Boolean.valueOf(isNextDay()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Void r4) {
        showEditTimeDialog(false, this.endHourPos, this.endMinPos, new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorEffectPeriod$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i, int i2) {
                ActWaveSensorEffectPeriod.this.lambda$startObserve$3(i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(int i, int i2) {
        this.endHourPos = i;
        this.endMinPos = i2;
        this.param.setEffectTimeEndH(this.index, i);
        this.param.setEffectTimeEndM(this.index, this.endMinPos);
        ((ActWaveSensorEffectPeriodBinding) this.mViewBinding).setEndTime(getTimeString(i, i2));
        this.showNextDay.setValue(Boolean.valueOf(isNextDay()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(String str) {
        if (TextUtils.isEmpty(str)) {
            ((ActWaveSensorEffectPeriodBinding) this.mViewBinding).tvRepeatTime.setText(str);
            this.param.setEffectTimeRepeat(this.index, this.triggerFlag == 0 ? 0 : 128);
            return;
        }
        ((ActWaveSensorEffectPeriodBinding) this.mViewBinding).tvRepeatTime.setText(HelpUtils.getWeeksString(this, str));
        int repeatInt = getRepeatInt(str);
        WaveSensorExtParam waveSensorExtParam = this.param;
        int i = this.index;
        if (this.triggerFlag != 0) {
            repeatInt |= 128;
        }
        waveSensorExtParam.setEffectTimeRepeat(i, repeatInt);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(Boolean bool) {
        ((ActWaveSensorEffectPeriodBinding) this.mViewBinding).date.setText(getString(bool.booleanValue() ? R.string.next_day : R.string.this_day));
    }

    private void showEditTimeDialog(boolean start, int hourPos, int minPos, TimeSelectDialog.SelectListener listener) {
        TimeSelectDialog.asDefault().setTitle(getString(start ? R.string.start_time : R.string.end_time)).setMinList(this.hourList).setSecList(this.minList).setSelectMinPosition(hourPos).setSelectSecPosition(minPos).setSelectListener(listener).showDialog(this);
    }

    private void showTriggerTimeDialog() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.set_trigger_times_cycle));
        arrayList.add(getString(R.string.set_trigger_times_once));
        SelectListDialog.asDefault(false).setTitle(getString(R.string.set_trigger_times_title)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorEffectPeriod$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActWaveSensorEffectPeriod.this.lambda$showTriggerTimeDialog$7((Integer) obj);
            }
        }).setSelectList(arrayList).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showTriggerTimeDialog$7(Integer num) {
        if (num.intValue() == 1) {
            this.triggerFlag = 1;
            WaveSensorExtParam waveSensorExtParam = this.param;
            int i = this.index;
            waveSensorExtParam.setEffectTimeRepeat(i, waveSensorExtParam.getEffectTimeRepeat(i) | 128);
            ((ActWaveSensorEffectPeriodBinding) this.mViewBinding).tvTriggerTime.setText(R.string.only_once);
            return;
        }
        this.triggerFlag = 0;
        WaveSensorExtParam waveSensorExtParam2 = this.param;
        int i2 = this.index;
        waveSensorExtParam2.setEffectTimeRepeat(i2, waveSensorExtParam2.getEffectTimeRepeat(i2) & 127);
        ((ActWaveSensorEffectPeriodBinding) this.mViewBinding).tvTriggerTime.setText(R.string.trigger_repeat);
    }

    public boolean isNextDay() {
        return (Integer.parseInt(this.hourList.get(this.startHourPos)) * 60) + Integer.parseInt(this.minList.get(this.startMinPos)) >= (Integer.parseInt(this.hourList.get(this.endHourPos)) * 60) + Integer.parseInt(this.minList.get(this.endMinPos));
    }

    public String getTimeString(int hourPos, int minPos) {
        return String.format("%s:%s", this.hourList.get(hourPos), this.minList.get(minPos));
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 3004 || data == null) {
            return;
        }
        String stringExtra = data.getStringExtra(Constants.WEEKS);
        this.weeks = stringExtra;
        this.repeatTimeLiveData.setValue(stringExtra);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        if (TextUtils.isEmpty(this.weeks)) {
            SmartToast.showShort(R.string.please_select_date);
        } else if (Injection.repo().device().getSuperPanel() != null) {
            showLoadingDialog(getString(R.string.saving));
            WaveSensorHelper.instance().getCmdHelper().setValidTime(this, this.param.getEffectTimeStartH(this.index), this.param.getEffectTimeStartM(this.index), this.param.getEffectTimeEndH(this.index), this.param.getEffectTimeEndM(this.index), this.param.getEffectTimeRepeat(this.index), new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorEffectPeriod$$ExternalSyntheticLambda8
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActWaveSensorEffectPeriod.this.lambda$edit$8((ResponseMsg) obj);
                }
            });
        } else {
            showNoSuperPanelDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$8(ResponseMsg responseMsg) {
        if (responseMsg != null) {
            if (responseMsg.getStateCode() == 0) {
                updateWaveSensorExt(WaveSensorHelper.instance().controlObject, this.param);
                showSuccessDialog(getString(R.string.save_success));
                return;
            } else if (responseMsg.getStateCode() == 24) {
                MessageDialog.show(this, getString(R.string.set_fail), getString(R.string.sensor_period_enable_set_fail)).setOkButton(getString(R.string.ok)).show();
                dismissLoadingDialog();
                return;
            }
        }
        showErrorDialog(getString(R.string.save_fail));
    }

    private void setSuccessResult() {
        WaveSensorHelper.instance().extParam = this.param;
        setResult(3004);
    }

    private int getRepeatInt(String week) {
        if (TextUtils.isEmpty(week)) {
            return 0;
        }
        String[] split = week.split(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP);
        if (split.length == 7) {
            return 127;
        }
        int i = 0;
        for (String str : split) {
            i += 1 << (Integer.parseInt(str) - 1);
        }
        return i;
    }

    private void updateWaveSensorExt(Object object, final WaveSensorExtParam extParam) {
        if (object instanceof Device) {
            final Device device = (Device) object;
            ((ObservableSubscribeProxy) Injection.net().updateParamExt(device.getDeviceId(), extParam.getSensorParamMapString()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorEffectPeriod$$ExternalSyntheticLambda9
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActWaveSensorEffectPeriod.this.lambda$updateWaveSensorExt$9(device, extParam, obj);
                }
            }, new SmartErrorComsumer());
        } else {
            final Group group = (Group) object;
            ((ObservableSubscribeProxy) Injection.net().updateGroupParamExt(group.getGroupId(), extParam.getSensorParamMapString()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorEffectPeriod$$ExternalSyntheticLambda10
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActWaveSensorEffectPeriod.this.lambda$updateWaveSensorExt$10(group, extParam, obj);
                }
            }, new SmartErrorComsumer());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateWaveSensorExt$9(Device device, WaveSensorExtParam waveSensorExtParam, Object obj) throws Exception {
        Device deviceById = Injection.repo().device().getDeviceById(device.getId());
        deviceById.setExtParam(waveSensorExtParam.getSensorParamMapString());
        Injection.repo().device().saveDevice(deviceById);
        setSuccessResult();
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateWaveSensorExt$10(Group group, WaveSensorExtParam waveSensorExtParam, Object obj) throws Exception {
        Group groupByGroupId = Injection.repo().group().getGroupByGroupId(group.getGroupId());
        groupByGroupId.setExtParam(waveSensorExtParam.getSensorParamMapString());
        Injection.repo().group().saveGroup(groupByGroupId);
        setSuccessResult();
        finish();
    }
}