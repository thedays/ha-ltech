package com.ltech.smarthome.ui.device.microwave_sensor;

import android.view.View;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActSenseSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.HsdExtParam;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.ltech.smarthome.view.dialog.TimeSelectDialog;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActSenseSetting extends VMActivity<ActSenseSettingBinding, BaseDeviceSetViewModel> {
    private HsdExtParam extParam = new HsdExtParam();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_sense_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.sensor_settings));
        setBackImage(R.mipmap.icon_back);
        ((ActSenseSettingBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActSenseSetting$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActSenseSetting.this.lambda$initView$6((View) obj);
            }
        }));
        ((ActSenseSettingBinding) this.mViewBinding).sbIndicator.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActSenseSetting$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                ActSenseSetting.this.lambda$initView$9(switchButton, z);
            }
        });
        ((ActSenseSettingBinding) this.mViewBinding).sbSingleReport.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActSenseSetting$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                ActSenseSetting.this.lambda$initView$12(switchButton, z);
            }
        });
        ((BaseDeviceSetViewModel) this.mViewModel).controlDevice.setValue(Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$6(View view) {
        int id = view.getId();
        if (id == R.id.layout_set_motion) {
            RelateInfoUtils.showImageTipDialog(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue(), this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActSenseSetting$$ExternalSyntheticLambda10
                @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                    ActSenseSetting.this.lambda$initView$5(imageTipDialog);
                }
            });
        } else {
            if (id != R.id.layout_set_no_motion) {
                return;
            }
            RelateInfoUtils.showImageTipDialog(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue(), this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActSenseSetting$$ExternalSyntheticLambda9
                @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                    ActSenseSetting.this.lambda$initView$2(imageTipDialog);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(ImageTipDialog imageTipDialog) {
        ArrayList arrayList = new ArrayList();
        for (int i = 1; i <= 60; i++) {
            arrayList.add(String.valueOf(i));
        }
        TimeSelectDialog.asDefault().setTitle(getString(R.string.please_choose)).setMinList(arrayList).withUnit(true).setMinUnit(getString(R.string.min_new)).setSelectMinPosition(this.extParam.getTimeNoMotion() - 1).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActSenseSetting$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i2, int i3) {
                ActSenseSetting.this.lambda$initView$1(i2, i3);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(final int i, int i2) {
        CmdAssistant.getSettingCmdAssistant(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue(), new int[0]).setNoMotionTime(this, i + 1, new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActSenseSetting$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSenseSetting.this.lambda$initView$0(i, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(int i, Boolean bool) {
        if (bool.booleanValue()) {
            this.extParam.setTimeNoMotion(i + 1);
            ((BaseDeviceSetViewModel) this.mViewModel).updateParamExt(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue(), GsonUtils.toJson(this.extParam));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$5(ImageTipDialog imageTipDialog) {
        ArrayList arrayList = new ArrayList();
        for (int i = 10; i <= 60; i++) {
            arrayList.add(String.valueOf(i));
        }
        TimeSelectDialog.asDefault().setTitle(getString(R.string.please_choose)).setMinList(arrayList).withUnit(true).setMinUnit(getString(R.string.sec)).setSelectMinPosition(this.extParam.getTimeMotion() - 10).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActSenseSetting$$ExternalSyntheticLambda11
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i2, int i3) {
                ActSenseSetting.this.lambda$initView$4(i2, i3);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$4(final int i, int i2) {
        CmdAssistant.getSettingCmdAssistant(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue(), new int[0]).setMotionTime(this, i + 10, new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActSenseSetting$$ExternalSyntheticLambda12
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSenseSetting.this.lambda$initView$3(i, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$3(int i, Boolean bool) {
        if (bool.booleanValue()) {
            this.extParam.setTimeMotion(i + 10);
            ((BaseDeviceSetViewModel) this.mViewModel).updateParamExt(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue(), GsonUtils.toJson(this.extParam));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$9(SwitchButton switchButton, final boolean z) {
        RelateInfoUtils.showImageTipDialog(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue(), this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActSenseSetting$$ExternalSyntheticLambda7
            @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
            public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                ActSenseSetting.this.lambda$initView$8(z, imageTipDialog);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$8(final boolean z, ImageTipDialog imageTipDialog) {
        ((BaseDeviceSetViewModel) this.mViewModel).setBuzzerState(z, 2, new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActSenseSetting$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSenseSetting.this.lambda$initView$7(z, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$7(boolean z, Boolean bool) {
        if (bool.booleanValue()) {
            this.extParam.setIndicatorStatus(z ? 1 : 0);
            ((BaseDeviceSetViewModel) this.mViewModel).updateParamExt(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue(), GsonUtils.toJson(this.extParam));
        } else {
            ((ActSenseSettingBinding) this.mViewBinding).sbIndicator.setCheckedNotByUser(!z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$12(SwitchButton switchButton, final boolean z) {
        RelateInfoUtils.showImageTipDialog(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue(), this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActSenseSetting$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
            public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                ActSenseSetting.this.lambda$initView$11(z, imageTipDialog);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$11(final boolean z, ImageTipDialog imageTipDialog) {
        CmdAssistant.getSettingCmdAssistant(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue(), new int[0]).setSingleReport(this, z, new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActSenseSetting$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSenseSetting.this.lambda$initView$10(z, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$10(boolean z, Boolean bool) {
        if (bool.booleanValue()) {
            this.extParam.setSingleReport(z ? 1 : 0);
            ((BaseDeviceSetViewModel) this.mViewModel).updateParamExt(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue(), GsonUtils.toJson(this.extParam));
        } else {
            ((ActSenseSettingBinding) this.mViewBinding).sbSingleReport.setCheckedNotByUser(!z);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((BaseDeviceSetViewModel) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActSenseSetting$$ExternalSyntheticLambda13
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSenseSetting.this.lambda$startObserve$13((Device) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$13(Device device) {
        if (device.getExtParam() != null) {
            HsdExtParam hsdExtParam = (HsdExtParam) device.getExtParam(HsdExtParam.class);
            this.extParam = hsdExtParam;
            if (hsdExtParam != null) {
                if (hsdExtParam.getTimeNoMotion() != 0) {
                    ((ActSenseSettingBinding) this.mViewBinding).tvNoMotion.setText(this.extParam.getTimeNoMotion() + getString(R.string.min_new));
                }
                if (this.extParam.getTimeMotion() != 0) {
                    ((ActSenseSettingBinding) this.mViewBinding).tvMotion.setText(this.extParam.getTimeMotion() + getString(R.string.sec));
                }
                ((ActSenseSettingBinding) this.mViewBinding).sbIndicator.setCheckedNotByUser(this.extParam.getIndicatorStatus() == 1);
                ((ActSenseSettingBinding) this.mViewBinding).sbSingleReport.setCheckedNotByUser(this.extParam.getSingleReport() == 1);
            }
        }
    }
}