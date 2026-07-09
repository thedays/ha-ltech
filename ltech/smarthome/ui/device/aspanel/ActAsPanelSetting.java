package com.ltech.smarthome.ui.device.aspanel;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActAsPanelSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.utils.Constants;

/* loaded from: classes4.dex */
public class ActAsPanelSetting extends BaseDeviceSetActivity<ActAsPanelSettingBinding, ActAsPanelSettingVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_as_panel_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.setting));
        setBackImage(R.mipmap.icon_back);
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActAsPanelSettingVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActAsPanelSettingVM) this.mViewModel).ligth_type = getIntent().getIntExtra(Constants.LIGHT_TYPE, -1);
        Injection.repo().device().getDeviceFromDb(((ActAsPanelSettingVM) this.mViewModel).controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanelSetting$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAsPanelSetting.this.lambda$startObserve$0((Device) obj);
            }
        });
        ((ActAsPanelSettingVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanelSetting$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAsPanelSetting.this.lambda$startObserve$1((Device) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Device device) {
        ((ActAsPanelSettingVM) this.mViewModel).controlDevice.setValue(device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device) {
        String floorName;
        Floor floor = Injection.repo().home().getFloor(device.getFloorId());
        Room room = Injection.repo().home().getRoom(device.getRoomId());
        AppCompatTextView appCompatTextView = ((ActAsPanelSettingBinding) this.mViewBinding).tvRoomName;
        if (room != null) {
            floorName = floor.getFloorName() + " " + room.getRoomName();
        } else {
            floorName = floor != null ? floor.getFloorName() : "";
        }
        appCompatTextView.setText(floorName);
        ((ActAsPanelSettingVM) this.mViewModel).roomPickHelper.startObserve(this, device.getPlaceId(), device.getRoomId());
    }
}