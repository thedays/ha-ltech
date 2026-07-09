package com.ltech.smarthome.ui.device.setting;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActBleTrigCurtainSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.device_param.TrigExtParam;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.smart.message.utils.LHomeLog;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActBleTrigCurtainSetting extends BaseDeviceSetActivity<ActBleTrigCurtainSettingBinding, ActBleTrigCurtainSettingVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_ble_trig_curtain_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.setting));
        setBackImage(R.mipmap.icon_back);
        isOwnerOrManager();
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActBleTrigCurtainSettingVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActBleTrigCurtainSettingVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActBleTrigCurtainSettingVM) this.mViewModel).deviceId = getIntent().getLongExtra("device_id", -1L);
        ((ActBleTrigCurtainSettingVM) this.mViewModel).productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        ((ActBleTrigCurtainSettingVM) this.mViewModel).samePlace = getIntent().getBooleanExtra(Constants.SAME_PLACE, true);
        ((ActBleTrigCurtainSettingVM) this.mViewModel).subType = getIntent().getIntExtra(Constants.SUB_TYPE, 0);
        Injection.repo().device().getDeviceFromDb(((ActBleTrigCurtainSettingVM) this.mViewModel).controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.setting.ActBleTrigCurtainSetting$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActBleTrigCurtainSetting.this.lambda$startObserve$0((Device) obj);
            }
        });
        ((ActBleTrigCurtainSettingVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.setting.ActBleTrigCurtainSetting$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActBleTrigCurtainSetting.this.lambda$startObserve$1((Device) obj);
            }
        });
        ((ActBleTrigCurtainSettingVM) this.mViewModel).showControlModeDialogEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.setting.ActBleTrigCurtainSetting.1
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActBleTrigCurtainSetting.this.showControlModelDialog();
            }
        });
        ((ActBleTrigCurtainSettingVM) this.mViewModel).showControlMode.observe(this, new Observer<Integer>() { // from class: com.ltech.smarthome.ui.device.setting.ActBleTrigCurtainSetting.2
            @Override // androidx.lifecycle.Observer
            public void onChanged(Integer mode) {
                ActBleTrigCurtainSetting actBleTrigCurtainSetting;
                int i;
                ((ActBleTrigCurtainSettingVM) ActBleTrigCurtainSetting.this.mViewModel).mMode = mode.intValue();
                AppCompatTextView appCompatTextView = ((ActBleTrigCurtainSettingBinding) ActBleTrigCurtainSetting.this.mViewBinding).tvCurtainControlSettingName;
                if (mode.intValue() == 1) {
                    actBleTrigCurtainSetting = ActBleTrigCurtainSetting.this;
                    i = R.string.app_str_trig_control_mode_2;
                } else {
                    actBleTrigCurtainSetting = ActBleTrigCurtainSetting.this;
                    i = R.string.app_str_trig_control_mode_3;
                }
                appCompatTextView.setText(actBleTrigCurtainSetting.getString(i));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Device device) {
        ((ActBleTrigCurtainSettingVM) this.mViewModel).controlDevice.setValue(device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device) {
        String floorName;
        LHomeLog.i("Test", getClass(), "mViewModel.controlDevice enter");
        Floor floor = Injection.repo().home().getFloor(device.getFloorId());
        Room room = Injection.repo().home().getRoom(device.getRoomId());
        AppCompatTextView appCompatTextView = ((ActBleTrigCurtainSettingBinding) this.mViewBinding).tvRoomName;
        if (room != null) {
            floorName = floor.getFloorName() + " " + room.getRoomName();
        } else {
            floorName = floor != null ? floor.getFloorName() : "";
        }
        appCompatTextView.setText(floorName);
        ((ActBleTrigCurtainSettingVM) this.mViewModel).roomPickHelper.startObserve(this, device.getPlaceId(), device.getRoomId());
        if (device.getExtParam() != null) {
            TrigExtParam trigExtParam = new TrigExtParam();
            trigExtParam.fillMapWithString(device.getExtParam());
            int curtainType = trigExtParam.getCurtainType();
            if (curtainType == -1 || curtainType == 0) {
                ((ActBleTrigCurtainSettingBinding) this.mViewBinding).tvCurtainOpenDirName.setText(getString(R.string.app_str_curtain_run_dir_left_to_right));
            } else if (curtainType == 1) {
                ((ActBleTrigCurtainSettingBinding) this.mViewBinding).tvCurtainOpenDirName.setText(getString(R.string.app_str_curtain_run_up_to_down));
            } else if (curtainType == 2) {
                ((ActBleTrigCurtainSettingBinding) this.mViewBinding).tvCurtainOpenDirName.setText(getString(R.string.app_str_curtain_run_left));
            }
        } else {
            ((ActBleTrigCurtainSettingBinding) this.mViewBinding).tvCurtainOpenDirName.setText(getString(R.string.app_str_curtain_run_dir_left_to_right));
        }
        if (((ActBleTrigCurtainSettingVM) this.mViewModel).subType == 0) {
            ((ActBleTrigCurtainSettingVM) this.mViewModel).checkControlMode();
        } else {
            ((ActBleTrigCurtainSettingBinding) this.mViewBinding).layoutCurtainControlSetting.setVisibility(8);
        }
    }

    private void showModelDialog() {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.app_str_continuous_mode));
        arrayList.add(getString(R.string.app_str_jog_mode));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.setting.ActBleTrigCurtainSetting$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActBleTrigCurtainSetting.this.lambda$showModelDialog$2((Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showModelDialog$2(Integer num) {
        if (num.intValue() == 0) {
            ((ActBleTrigCurtainSettingBinding) this.mViewBinding).tvControlModeName.setText(getString(R.string.app_str_continuous_mode));
            ((ActBleTrigCurtainSettingVM) this.mViewModel).setControlMode(1);
        } else if (num.intValue() == 1) {
            ((ActBleTrigCurtainSettingBinding) this.mViewBinding).tvControlModeName.setText(getString(R.string.app_str_jog_mode));
            ((ActBleTrigCurtainSettingVM) this.mViewModel).setControlMode(2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showControlModelDialog() {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(((ActBleTrigCurtainSettingVM) this.mViewModel).mMode != 1 ? 1 : 0);
        arrayList.add(getString(R.string.app_str_trig_control_mode_2));
        arrayList.add(getString(R.string.app_str_trig_control_mode_3));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.setting.ActBleTrigCurtainSetting$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActBleTrigCurtainSetting.this.lambda$showControlModelDialog$3((Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showControlModelDialog$3(Integer num) {
        if (num.intValue() == 0) {
            ((ActBleTrigCurtainSettingBinding) this.mViewBinding).tvCurtainControlSettingName.setText(getString(R.string.app_str_trig_control_mode_2));
            ((ActBleTrigCurtainSettingVM) this.mViewModel).setControlMode(1);
        } else if (num.intValue() == 1) {
            ((ActBleTrigCurtainSettingBinding) this.mViewBinding).tvCurtainControlSettingName.setText(getString(R.string.app_str_trig_control_mode_3));
            ((ActBleTrigCurtainSettingVM) this.mViewModel).setControlMode(2);
        }
    }
}