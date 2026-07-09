package com.ltech.smarthome.ui.device.setting;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActSonosSettingDefaultBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes4.dex */
public class ActSonosSettingDefault extends BaseDeviceSetActivity<ActSonosSettingDefaultBinding, ActSonosSettingDefaultVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_sonos_setting_default;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.setting));
        setBackImage(R.mipmap.icon_back);
        setEditString("             ");
        ((ActSonosSettingDefaultBinding) this.mViewBinding).sbCrossFade.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.setting.ActSonosSettingDefault.1
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (Boolean.FALSE.equals(((ActSonosSettingDefaultVM) ActSonosSettingDefault.this.mViewModel).canCrossfade.getValue())) {
                    ((ActSonosSettingDefaultBinding) ActSonosSettingDefault.this.mViewBinding).sbCrossFade.setCheckedNotByUser(!isChecked);
                    SmartToast.showShort(R.string.app_str_sonos_action_no_support);
                } else {
                    ((ActSonosSettingDefaultVM) ActSonosSettingDefault.this.mViewModel).isCrossFade.setValue(Boolean.valueOf(isChecked));
                    ((ActSonosSettingDefaultVM) ActSonosSettingDefault.this.mViewModel).setMode();
                }
            }
        });
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActSonosSettingDefaultVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActSonosSettingDefaultVM) this.mViewModel).samePlace = getIntent().getBooleanExtra(Constants.SAME_PLACE, true);
        ((ActSonosSettingDefaultVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActSonosSettingDefaultVM) this.mViewModel).deviceId = getIntent().getLongExtra("device_id", -1L);
        ((ActSonosSettingDefaultVM) this.mViewModel).isCrossFade.setValue(Boolean.valueOf(getIntent().getBooleanExtra("isCrossFade", false)));
        ((ActSonosSettingDefaultVM) this.mViewModel).canCrossfade.setValue(Boolean.valueOf(getIntent().getBooleanExtra("canCrossfade", false)));
        ((ActSonosSettingDefaultVM) this.mViewModel).playModeEvent.setValue(Integer.valueOf(getIntent().getIntExtra("playMode", 0)));
        if (((ActSonosSettingDefaultVM) this.mViewModel).samePlace) {
            Injection.repo().device().getDeviceFromDb(((ActSonosSettingDefaultVM) this.mViewModel).controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.setting.ActSonosSettingDefault$$ExternalSyntheticLambda0
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActSonosSettingDefault.this.lambda$startObserve$0((Device) obj);
                }
            });
        } else {
            ((ActSonosSettingDefaultVM) this.mViewModel).controlDevice.setValue(Injection.repo().device().getDevice(((ActSonosSettingDefaultVM) this.mViewModel).placeId, ((ActSonosSettingDefaultVM) this.mViewModel).deviceId));
        }
        ((ActSonosSettingDefaultVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.setting.ActSonosSettingDefault$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSonosSettingDefault.this.lambda$startObserve$1((Device) obj);
            }
        });
        ((ActSonosSettingDefaultVM) this.mViewModel).getSceneAutomationOver.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.setting.ActSonosSettingDefault$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSonosSettingDefault.this.lambda$startObserve$2((Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Device device) {
        ((ActSonosSettingDefaultVM) this.mViewModel).controlDevice.setValue(device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device) {
        String floorName;
        Floor floor = Injection.repo().home().getFloor(device.getFloorId());
        Room room = Injection.repo().home().getRoom(device.getRoomId());
        AppCompatTextView appCompatTextView = ((ActSonosSettingDefaultBinding) this.mViewBinding).tvRoomName;
        if (room != null) {
            floorName = floor.getFloorName() + " " + room.getRoomName();
        } else {
            floorName = floor != null ? floor.getFloorName() : "";
        }
        appCompatTextView.setText(floorName);
        ((ActSonosSettingDefaultVM) this.mViewModel).roomPickHelper.startObserve(this, device.getPlaceId(), device.getRoomId());
        ((ActSonosSettingDefaultVM) this.mViewModel).queryScene(device.getDeviceId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Integer num) {
        ((ActSonosSettingDefaultBinding) this.mViewBinding).tvRelatedNum.setText(String.valueOf(num));
    }
}