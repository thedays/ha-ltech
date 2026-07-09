package com.ltech.smarthome.ui.device.curtain_motor;

import android.content.Intent;
import android.widget.SeekBar;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActBleMotorModeSetBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.view.dialog.EditDialog;

/* loaded from: classes4.dex */
public class ActBleMotorModeSet extends VMActivity<ActBleMotorModeSetBinding, ActBleMotorModeSetVM> implements ISelectAction {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_ble_motor_mode_set;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult */
    public /* synthetic */ void lambda$initView$0(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.app_str_curtain_mode_setting));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActBleMotorModeSetVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActBleMotorModeSetVM) this.mViewModel).position = getIntent().getIntExtra(Constants.ICON_POSITION, 0);
        ((ActBleMotorModeSetVM) this.mViewModel).modePosition = getIntent().getIntExtra(Constants.MODE_POSITION, 0);
        ((ActBleMotorModeSetVM) this.mViewModel).productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        ((ActBleMotorModeSetVM) this.mViewModel).modeName.setValue(getIntent().getStringExtra(Constants.MODE_NAME));
        ((ActBleMotorModeSetVM) this.mViewModel).sceneIconPos.setValue(Integer.valueOf(getIntent().getIntExtra(Constants.ICON_POSITION, 0)));
        Injection.repo().device().getDeviceFromDb(((ActBleMotorModeSetVM) this.mViewModel).controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleMotorModeSet$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActBleMotorModeSet.this.lambda$startObserve$0((Device) obj);
            }
        });
        ((ActBleMotorModeSetVM) this.mViewModel).sceneIconPos.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleMotorModeSet$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActBleMotorModeSet.this.lambda$startObserve$1((Integer) obj);
            }
        });
        ((ActBleMotorModeSetVM) this.mViewModel).showEditNameDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleMotorModeSet$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActBleMotorModeSet.this.lambda$startObserve$2((Void) obj);
            }
        });
        ((ActBleMotorModeSetBinding) this.mViewBinding).sbBrt.setIncludeZero(true);
        ((ActBleMotorModeSetVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleMotorModeSet$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActBleMotorModeSet.this.lambda$startObserve$3(obj);
            }
        });
        ((ActBleMotorModeSetBinding) this.mViewBinding).sbBrt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleMotorModeSet.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ((ActBleMotorModeSetBinding) ActBleMotorModeSet.this.mViewBinding).tvBrt.setText(seekBar.getProgress() + "%");
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() == 0) {
                    ((ActBleMotorModeSetBinding) ActBleMotorModeSet.this.mViewBinding).tvBrt.setText(ActBleMotorModeSet.this.getString(R.string.app_str_all_close));
                    return;
                }
                if (seekBar.getProgress() == 100) {
                    ((ActBleMotorModeSetBinding) ActBleMotorModeSet.this.mViewBinding).tvBrt.setText(ActBleMotorModeSet.this.getString(R.string.app_str_all_open));
                    return;
                }
                ((ActBleMotorModeSetBinding) ActBleMotorModeSet.this.mViewBinding).tvBrt.setText(seekBar.getProgress() + "%");
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Device device) {
        ((ActBleMotorModeSetVM) this.mViewModel).controlObject.setValue(device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Integer num) {
        ((ActBleMotorModeSetBinding) this.mViewBinding).ivModeIcon.setImageResource(SceneHelper.getSceneIcon(this, num.intValue()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r1) {
        showEditNameDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Object obj) {
        Device device = (Device) obj;
        if (device.getDeviceState().getCurtainMotorState() != null) {
            int modePositionPercent = device.getDeviceState().getCurtainMotorState().getModeInfoList().get(((ActBleMotorModeSetVM) this.mViewModel).modePosition).getModePositionPercent();
            ((ActBleMotorModeSetBinding) this.mViewBinding).sbBrt.setProgress(100 - modePositionPercent);
            if (modePositionPercent == 100) {
                ((ActBleMotorModeSetBinding) this.mViewBinding).tvBrt.setText(getString(R.string.app_str_all_close));
                return;
            }
            if (modePositionPercent == 0) {
                ((ActBleMotorModeSetBinding) this.mViewBinding).tvBrt.setText(getString(R.string.app_str_all_open));
                return;
            }
            ((ActBleMotorModeSetBinding) this.mViewBinding).tvBrt.setText(((ActBleMotorModeSetBinding) this.mViewBinding).sbBrt.getProgress() + "%");
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        ((ActBleMotorModeSetVM) this.mViewModel).setCurtainMode(((ActBleMotorModeSetBinding) this.mViewBinding).sbBrt.getProgress());
    }

    private void showEditNameDialog() {
        EditDialog.asDefault().setContent(((ActBleMotorModeSetBinding) this.mViewBinding).tvKeyName.getText().toString()).setTitle(getString(R.string.edit_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleMotorModeSet$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActBleMotorModeSet.this.lambda$showEditNameDialog$4((String) obj);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditNameDialog$4(String str) {
        ((ActBleMotorModeSetVM) this.mViewModel).changeModeName(str, ((ActBleMotorModeSetVM) this.mViewModel).modePosition);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3001) {
            lambda$initView$0(this);
        }
        if (3002 != resultCode || data == null) {
            return;
        }
        ((ActBleMotorModeSetVM) this.mViewModel).sceneIconPos.setValue(Integer.valueOf(data.getIntExtra(Constants.ICON_POSITION, 0)));
    }
}