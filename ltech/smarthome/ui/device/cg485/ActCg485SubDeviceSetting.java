package com.ltech.smarthome.ui.device.cg485;

import android.view.View;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSubDeviceSettingDefaultBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.ui.device.setting.ActSubDeviceSettingDefaultVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.view.dialog.AddCg485DeviceDialog;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;

/* loaded from: classes4.dex */
public class ActCg485SubDeviceSetting extends BaseDeviceSetActivity<ActSubDeviceSettingDefaultBinding, ActSubDeviceSettingDefaultVM> {
    private int commandType;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_sub_device_setting_default;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.setting));
        setBackImage(R.mipmap.icon_back);
        ((ActSubDeviceSettingDefaultBinding) this.mViewBinding).layoutChangeIcon.setVisibility(0);
        ((ActSubDeviceSettingDefaultVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActSubDeviceSettingDefaultVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485SubDeviceSetting$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCg485SubDeviceSetting.this.lambda$startObserve$0((Device) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Device device) {
        ActCg485SubDeviceSetting actCg485SubDeviceSetting;
        this.commandType = device.isSubDevice() ? 1 : 2;
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(device.getMacdeviceid());
        if (deviceByDeviceId != null) {
            ((ActSubDeviceSettingDefaultBinding) this.mViewBinding).tvDeviceName.setText(device.getDeviceName());
            ((ActSubDeviceSettingDefaultBinding) this.mViewBinding).tvSubordinateDevice.setText(deviceByDeviceId.getDeviceName());
            actCg485SubDeviceSetting = this;
            ((ActSubDeviceSettingDefaultVM) this.mViewModel).roomPickHelper.startObserve(actCg485SubDeviceSetting, device.getPlaceId(), device.getRoomId());
            ((ActSubDeviceSettingDefaultBinding) actCg485SubDeviceSetting.mViewBinding).ivRoomNameGo.setVisibility(0);
        } else {
            actCg485SubDeviceSetting = this;
            ((ActSubDeviceSettingDefaultBinding) actCg485SubDeviceSetting.mViewBinding).tvDeviceName.setText(Cg485Helper.getCategory(actCg485SubDeviceSetting.commandType).getCategoryName());
            ((ActSubDeviceSettingDefaultBinding) actCg485SubDeviceSetting.mViewBinding).tvSubordinateDevice.setText(device.getDeviceName());
            ((ActSubDeviceSettingDefaultBinding) actCg485SubDeviceSetting.mViewBinding).layoutChangeRoom.setEnabled(false);
            ((ActSubDeviceSettingDefaultBinding) actCg485SubDeviceSetting.mViewBinding).ivRoomNameGo.setVisibility(8);
        }
        ((ActSubDeviceSettingDefaultBinding) actCg485SubDeviceSetting.mViewBinding).tvRoomName.setText(getPlaceInfo(device));
        ((ActSubDeviceSettingDefaultBinding) actCg485SubDeviceSetting.mViewBinding).ivIcon.setImageResource(Cg485Helper.getDeviceImage(this, Cg485Helper.getCategory(actCg485SubDeviceSetting.commandType).getColorIdx()));
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        ((ActSubDeviceSettingDefaultVM) this.mViewModel).controlDevice.setValue(Injection.repo().device().getDeviceById(((ActSubDeviceSettingDefaultVM) this.mViewModel).controlId));
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity
    protected void showEditNameDialog() {
        EditDialog.asDefault().setContent(((ActSubDeviceSettingDefaultBinding) this.mViewBinding).tvDeviceName.getText().toString()).setTitle(getString(R.string.edit_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485SubDeviceSetting$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActCg485SubDeviceSetting.this.lambda$showEditNameDialog$1((String) obj);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditNameDialog$1(String str) {
        ((ActSubDeviceSettingDefaultBinding) this.mViewBinding).tvDeviceName.setText(str);
        Cg485Helper.getCategory(this.commandType).setCategoryName(str);
        if (this.commandType == 1) {
            Cg485Helper.updateNameAndParamExt(this, str, false, true);
        } else {
            Cg485Helper.updateParamExt(this, false, true);
        }
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity
    protected void showSelectDeviceIconDialog() {
        AddCg485DeviceDialog.asDefault().setTitle(getString(R.string.choose_icon)).setSelectPosition(Cg485Helper.getCategory(this.commandType).getColorIdx()).setIconMode(true).setOnSaveListener(new AddCg485DeviceDialog.OnSaveListener() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485SubDeviceSetting$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.dialog.AddCg485DeviceDialog.OnSaveListener
            public final void onSave(String str, int i, int i2) {
                ActCg485SubDeviceSetting.this.lambda$showSelectDeviceIconDialog$2(str, i, i2);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectDeviceIconDialog$2(String str, int i, int i2) {
        Cg485Helper.getCategory(this.commandType).setColorIdx(i2);
        ((ActSubDeviceSettingDefaultBinding) this.mViewBinding).ivIcon.setImageResource(Cg485Helper.getDeviceImage(this, Cg485Helper.getCategory(this.commandType).getColorIdx()));
        Cg485Helper.updateParamExt(this, false, true);
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity
    protected void showDeleteDeviceDialog() {
        MessageDialog.show(this, getString(R.string.sure_delete_device), getString(R.string.delete_485_device_tip)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485SubDeviceSetting$$ExternalSyntheticLambda2
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showDeleteDeviceDialog$3;
                lambda$showDeleteDeviceDialog$3 = ActCg485SubDeviceSetting.this.lambda$showDeleteDeviceDialog$3(baseDialog, view);
                return lambda$showDeleteDeviceDialog$3;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showDeleteDeviceDialog$3(BaseDialog baseDialog, View view) {
        if (this.commandType == 1) {
            ((ActSubDeviceSettingDefaultVM) this.mViewModel).deleteDevice();
            return false;
        }
        Cg485Helper.deleteCategory(this);
        return false;
    }
}