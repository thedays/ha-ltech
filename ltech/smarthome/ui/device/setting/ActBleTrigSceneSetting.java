package com.ltech.smarthome.ui.device.setting;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActBleTrigSceneSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActBleTrigSceneSetting extends BaseDeviceSetActivity<ActBleTrigSceneSettingBinding, ActBleTrigSceneSettingVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_ble_trig_scene_setting;
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
        ((ActBleTrigSceneSettingVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActBleTrigSceneSettingVM) this.mViewModel).samePlace = getIntent().getBooleanExtra(Constants.SAME_PLACE, true);
        ((ActBleTrigSceneSettingVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActBleTrigSceneSettingVM) this.mViewModel).deviceId = getIntent().getLongExtra("device_id", -1L);
        if (((ActBleTrigSceneSettingVM) this.mViewModel).samePlace) {
            Injection.repo().device().getDeviceFromDb(((ActBleTrigSceneSettingVM) this.mViewModel).controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.setting.ActBleTrigSceneSetting$$ExternalSyntheticLambda1
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActBleTrigSceneSetting.this.lambda$startObserve$0((Device) obj);
                }
            });
        } else {
            ((ActBleTrigSceneSettingVM) this.mViewModel).controlDevice.setValue(Injection.repo().device().getDevice(((ActBleTrigSceneSettingVM) this.mViewModel).placeId, ((ActBleTrigSceneSettingVM) this.mViewModel).deviceId));
        }
        ((ActBleTrigSceneSettingVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.setting.ActBleTrigSceneSetting$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActBleTrigSceneSetting.this.lambda$startObserve$1((Device) obj);
            }
        });
        ((ActBleTrigSceneSettingVM) this.mViewModel).showControlModeDialogEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.setting.ActBleTrigSceneSetting.1
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActBleTrigSceneSetting.this.showModelDialog();
            }
        });
        ((ActBleTrigSceneSettingVM) this.mViewModel).showControlMode.observe(this, new Observer<Integer>() { // from class: com.ltech.smarthome.ui.device.setting.ActBleTrigSceneSetting.2
            @Override // androidx.lifecycle.Observer
            public void onChanged(Integer mode) {
                ActBleTrigSceneSetting actBleTrigSceneSetting;
                int i;
                AppCompatTextView appCompatTextView = ((ActBleTrigSceneSettingBinding) ActBleTrigSceneSetting.this.mViewBinding).tvControlModeName;
                if (mode.intValue() == 2) {
                    actBleTrigSceneSetting = ActBleTrigSceneSetting.this;
                    i = R.string.app_str_jog_mode;
                } else {
                    actBleTrigSceneSetting = ActBleTrigSceneSetting.this;
                    i = R.string.app_str_continuous_mode;
                }
                appCompatTextView.setText(actBleTrigSceneSetting.getString(i));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Device device) {
        ((ActBleTrigSceneSettingVM) this.mViewModel).controlDevice.setValue(device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device) {
        String floorName;
        Floor floor = Injection.repo().home().getFloor(device.getFloorId());
        Room room = Injection.repo().home().getRoom(device.getRoomId());
        AppCompatTextView appCompatTextView = ((ActBleTrigSceneSettingBinding) this.mViewBinding).tvRoomName;
        if (room != null) {
            floorName = floor.getFloorName() + " " + room.getRoomName();
        } else {
            floorName = floor != null ? floor.getFloorName() : "";
        }
        appCompatTextView.setText(floorName);
        ((ActBleTrigSceneSettingVM) this.mViewModel).roomPickHelper.startObserve(this, device.getPlaceId(), device.getRoomId());
        ((ActBleTrigSceneSettingVM) this.mViewModel).checkControlMode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showModelDialog() {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.app_str_continuous_mode));
        arrayList.add(getString(R.string.app_str_jog_mode));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.setting.ActBleTrigSceneSetting$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActBleTrigSceneSetting.this.lambda$showModelDialog$2((Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showModelDialog$2(Integer num) {
        if (num.intValue() == 0) {
            ((ActBleTrigSceneSettingBinding) this.mViewBinding).tvControlModeName.setText(getString(R.string.app_str_continuous_mode));
            ((ActBleTrigSceneSettingVM) this.mViewModel).setControlMode(1);
        } else if (num.intValue() == 1) {
            ((ActBleTrigSceneSettingBinding) this.mViewBinding).tvControlModeName.setText(getString(R.string.app_str_jog_mode));
            ((ActBleTrigSceneSettingVM) this.mViewModel).setControlMode(2);
        }
    }
}