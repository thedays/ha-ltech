package com.ltech.smarthome.ui.device.curtain_motor;

import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActBleCurtainMotorMoreSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActBleCurtainMotorMoreSetting extends VMActivity<ActBleCurtainMotorMoreSettingBinding, ActBleCurtainMotorMoreSettingVM> implements SwitchButton.OnCheckedChangeListener {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_ble_curtain_motor_more_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.more_settings));
        setBackImage(R.mipmap.icon_back);
        ((ActBleCurtainMotorMoreSettingBinding) this.mViewBinding).sbSoftStart.setOnCheckedChangeListener(this);
        ((ActBleCurtainMotorMoreSettingBinding) this.mViewBinding).sbManuallyPull.setOnCheckedChangeListener(this);
        ((ActBleCurtainMotorMoreSettingBinding) this.mViewBinding).sbSyncManualOperation.setOnCheckedChangeListener(this);
        ((ActBleCurtainMotorMoreSettingBinding) this.mViewBinding).sbSetLimitPosition.setOnCheckedChangeListener(this);
        ((ActBleCurtainMotorMoreSettingBinding) this.mViewBinding).sbMemorizeCurtainPosition.setOnCheckedChangeListener(this);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActBleCurtainMotorMoreSettingVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActBleCurtainMotorMoreSettingVM) this.mViewModel).controlDevice.setValue(Injection.repo().device().getDeviceById(((ActBleCurtainMotorMoreSettingVM) this.mViewModel).controlId));
        ((ActBleCurtainMotorMoreSettingVM) this.mViewModel).loadSettingParam();
        ((ActBleCurtainMotorMoreSettingVM) this.mViewModel).showChangeMotorDirectionDialogEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleCurtainMotorMoreSetting.1
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActBleCurtainMotorMoreSetting.this.showTipDialog();
            }
        });
        ((ActBleCurtainMotorMoreSettingVM) this.mViewModel).showMotorWhenStopDialogEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleCurtainMotorMoreSetting.2
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActBleCurtainMotorMoreSetting.this.showWhenStopTipDialog();
            }
        });
        ((ActBleCurtainMotorMoreSettingVM) this.mViewModel).showMotorAdjustDialogEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleCurtainMotorMoreSetting.3
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActBleCurtainMotorMoreSetting.this.showAdjustTipDialog();
            }
        });
        ((ActBleCurtainMotorMoreSettingVM) this.mViewModel).showMotorSpeedDialogEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleCurtainMotorMoreSetting.4
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActBleCurtainMotorMoreSetting.this.showSelectSpeedDialog();
            }
        });
        ((ActBleCurtainMotorMoreSettingVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleCurtainMotorMoreSetting$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActBleCurtainMotorMoreSetting.this.lambda$startObserve$0((Device) obj);
            }
        });
        if (((ActBleCurtainMotorMoreSettingVM) this.mViewModel).controlDevice.getValue().getProductId().equals(ProductId.ID_BLE_CURTAIN_CG_CURH3)) {
            ((ActBleCurtainMotorMoreSettingBinding) this.mViewBinding).layoutSetSoftStart.setVisibility(8);
            ((ActBleCurtainMotorMoreSettingBinding) this.mViewBinding).layoutSetLimitPosition.setVisibility(8);
            ((ActBleCurtainMotorMoreSettingBinding) this.mViewBinding).layoutMemorizeCurtainPosition.setVisibility(8);
            ((ActBleCurtainMotorMoreSettingBinding) this.mViewBinding).layoutAdjust.setVisibility(0);
            ((ActBleCurtainMotorMoreSettingBinding) this.mViewBinding).layoutMotorOpenType.setVisibility(8);
            ((ActBleCurtainMotorMoreSettingBinding) this.mViewBinding).layoutRemoteControl.setVisibility(8);
            ((ActBleCurtainMotorMoreSettingBinding) this.mViewBinding).layoutMotorSpeed.setVisibility(0);
            ((ActBleCurtainMotorMoreSettingBinding) this.mViewBinding).layoutSetSyncManualOperation.setVisibility(8);
            ((ActBleCurtainMotorMoreSettingBinding) this.mViewBinding).layoutSetManuallyPull.setVisibility(0);
        }
        ((ActBleCurtainMotorMoreSettingVM) this.mViewModel).motorSpeed.observe(this, new Observer<Integer>() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleCurtainMotorMoreSetting.5
            @Override // androidx.lifecycle.Observer
            public void onChanged(Integer integer) {
                if (integer.intValue() == 1) {
                    ((ActBleCurtainMotorMoreSettingBinding) ActBleCurtainMotorMoreSetting.this.mViewBinding).tvMotorSpeed.setText(ActBleCurtainMotorMoreSetting.this.getString(R.string.low_default));
                } else if (integer.intValue() == 3) {
                    ((ActBleCurtainMotorMoreSettingBinding) ActBleCurtainMotorMoreSetting.this.mViewBinding).tvMotorSpeed.setText(ActBleCurtainMotorMoreSetting.this.getString(R.string.high));
                } else {
                    ((ActBleCurtainMotorMoreSettingBinding) ActBleCurtainMotorMoreSetting.this.mViewBinding).tvMotorSpeed.setText(ActBleCurtainMotorMoreSetting.this.getString(R.string.medium));
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Device device) {
        CurtainMotorInfoExtParam curtainMotorInfoExtParam = new CurtainMotorInfoExtParam();
        if (device.getExtParam() != null && !TextUtils.isEmpty(device.getExtParam())) {
            curtainMotorInfoExtParam.fillMapWithString(device.getExtParam());
        }
        ((ActBleCurtainMotorMoreSettingBinding) this.mViewBinding).tvOpenType.setText(getString(curtainMotorInfoExtParam.getOpenType() == 0 ? R.string.app_curtain_open_two_way : R.string.app_curtain_open_one_way));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSelectSpeedDialog() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.low_default));
        arrayList.add(getString(R.string.medium));
        arrayList.add(getString(R.string.high));
        if (((ActBleCurtainMotorMoreSettingVM) this.mViewModel).motorSpeed.getValue() == null) {
            return;
        }
        SelectListDialog.asDefault(true).setTitle(getString(R.string.app_str_curtain_speed)).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.save)).setSelectPosition(((ActBleCurtainMotorMoreSettingVM) this.mViewModel).motorSpeed.getValue().intValue() != 0 ? ((ActBleCurtainMotorMoreSettingVM) this.mViewModel).motorSpeed.getValue().intValue() - 1 : 1).setSelectList(arrayList).setConfirmAction(new IAction<Integer>() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleCurtainMotorMoreSetting.6
            @Override // com.ltech.smarthome.base.IAction
            public void act(Integer pos) {
                ((ActBleCurtainMotorMoreSettingVM) ActBleCurtainMotorMoreSetting.this.mViewModel).setMotorSpeed(pos.intValue() + 1);
            }
        }).showDialog(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        ((ActBleCurtainMotorMoreSettingVM) this.mViewModel).controlDevice.setValue(Injection.repo().device().getDeviceById(((ActBleCurtainMotorMoreSettingVM) this.mViewModel).controlId));
    }

    @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
    public void onCheckedChanged(SwitchButton switchButton, boolean z) {
        int i = !z ? 1 : 0;
        int id = switchButton.getId();
        if (id == R.id.sb_soft_start) {
            ((ActBleCurtainMotorMoreSettingVM) this.mViewModel).getState().setSoftStart(i);
        } else if (id == R.id.sb_manually_pull) {
            ((ActBleCurtainMotorMoreSettingVM) this.mViewModel).getState().setManuallyPull(i);
        } else if (id == R.id.sb_set_limit_position) {
            ((ActBleCurtainMotorMoreSettingVM) this.mViewModel).getState().setLimitPosition(i);
        } else if (id == R.id.sb_sync_manual_operation) {
            ((ActBleCurtainMotorMoreSettingVM) this.mViewModel).getState().setSyncManual(i);
        } else if (id == R.id.sb_memorize_curtain_position) {
            ((ActBleCurtainMotorMoreSettingVM) this.mViewModel).getState().setMemorizePosition(i);
        }
        ((ActBleCurtainMotorMoreSettingVM) this.mViewModel).saveBleCurtainSetting();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showTipDialog() {
        MessageDialog.show(this, getString(R.string.tips), getString(R.string.app_str_change_ble_motor_tips)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleCurtainMotorMoreSetting$$ExternalSyntheticLambda1
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showTipDialog$1;
                lambda$showTipDialog$1 = ActBleCurtainMotorMoreSetting.this.lambda$showTipDialog$1(baseDialog, view);
                return lambda$showTipDialog$1;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showTipDialog$1(BaseDialog baseDialog, View view) {
        int motorDirection = ((ActBleCurtainMotorMoreSettingVM) this.mViewModel).getState().getMotorDirection();
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
        ((ActBleCurtainMotorMoreSettingVM) this.mViewModel).getState().setMotorDirection(motorDirection == 0 ? 1 : 0);
        ((ActBleCurtainMotorMoreSettingVM) this.mViewModel).saveBleCurtainSetting();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showWhenStopTipDialog() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.app_str_stop_while_meet_resistance));
        arrayList.add(getString(R.string.app_str_stop_while_limit_position));
        SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(((ActBleCurtainMotorMoreSettingVM) this.mViewModel).getState().getWhenMotorStop() == 0 ? 1 : 0).setSelectList(arrayList).setConfirmAction(new IAction<Integer>() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleCurtainMotorMoreSetting.7
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.ltech.smarthome.base.IAction
            public void act(Integer pos) {
                int i = pos.intValue() == 0 ? 1 : 0;
                ((ActBleCurtainMotorMoreSettingVM) ActBleCurtainMotorMoreSetting.this.mViewModel).whenMotorStop.setValue(Boolean.valueOf(i ^ 1));
                ((ActBleCurtainMotorMoreSettingVM) ActBleCurtainMotorMoreSetting.this.mViewModel).getState().setWhenMotorStop(i);
                ((ActBleCurtainMotorMoreSettingVM) ActBleCurtainMotorMoreSetting.this.mViewModel).saveBleCurtainSetting();
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAdjustTipDialog() {
        MessageDialog.show(this, getString(R.string.tips), getString(R.string.app_str_adjust_ble_motor_tips)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleCurtainMotorMoreSetting$$ExternalSyntheticLambda2
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showAdjustTipDialog$2;
                lambda$showAdjustTipDialog$2 = ActBleCurtainMotorMoreSetting.this.lambda$showAdjustTipDialog$2(baseDialog, view);
                return lambda$showAdjustTipDialog$2;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showAdjustTipDialog$2(BaseDialog baseDialog, View view) {
        ((ActBleCurtainMotorMoreSettingVM) this.mViewModel).adjustLimitPosition();
        return false;
    }
}