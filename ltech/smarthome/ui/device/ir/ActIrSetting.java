package com.ltech.smarthome.ui.device.ir;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActIrSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.utils.Constants;

/* loaded from: classes4.dex */
public class ActIrSetting extends BaseDeviceSetActivity<ActIrSettingBinding, ActIrSettingVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_ir_setting;
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
        ((ActIrSettingVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        Injection.repo().device().getDeviceFromDb(((ActIrSettingVM) this.mViewModel).controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.ir.ActIrSetting$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActIrSetting.this.lambda$startObserve$0((Device) obj);
            }
        });
        ((ActIrSettingVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.ir.ActIrSetting$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActIrSetting.this.lambda$startObserve$1((Device) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Device device) {
        ((ActIrSettingVM) this.mViewModel).controlDevice.setValue(device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device) {
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(device.getMacdeviceid());
        String str = "";
        if (deviceByDeviceId == null) {
            ((ActIrSettingBinding) this.mViewBinding).tvSubordinateDevice.setText("");
            ((ActIrSettingBinding) this.mViewBinding).tvRoomName.setText("");
            return;
        }
        Floor floor = Injection.repo().home().getFloor(device.getFloorId());
        Room room = Injection.repo().home().getRoom(device.getRoomId());
        ((ActIrSettingBinding) this.mViewBinding).tvSubordinateDevice.setText(deviceByDeviceId.getDeviceName());
        AppCompatTextView appCompatTextView = ((ActIrSettingBinding) this.mViewBinding).tvRoomName;
        if (room != null) {
            str = floor.getFloorName() + " " + room.getRoomName();
        } else if (floor != null) {
            str = floor.getFloorName();
        }
        appCompatTextView.setText(str);
    }
}