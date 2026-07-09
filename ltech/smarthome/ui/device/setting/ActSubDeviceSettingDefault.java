package com.ltech.smarthome.ui.device.setting;

import android.text.TextUtils;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActSubDeviceSettingDefaultBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.utils.Constants;

/* loaded from: classes4.dex */
public class ActSubDeviceSettingDefault extends BaseDeviceSetActivity<ActSubDeviceSettingDefaultBinding, ActSubDeviceSettingDefaultVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_sub_device_setting_default;
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
        ((ActSubDeviceSettingDefaultVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        Injection.repo().device().getDeviceFromDb(((ActSubDeviceSettingDefaultVM) this.mViewModel).controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.setting.ActSubDeviceSettingDefault$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSubDeviceSettingDefault.this.lambda$startObserve$0((Device) obj);
            }
        });
        String stringExtra = getIntent().getStringExtra(Constants.BRAND_NAME);
        if (!TextUtils.isEmpty(stringExtra)) {
            ((ActSubDeviceSettingDefaultBinding) this.mViewBinding).layoutAcBrand.setVisibility(0);
            ((ActSubDeviceSettingDefaultBinding) this.mViewBinding).tvAcBrand.setText(stringExtra);
        }
        ((ActSubDeviceSettingDefaultVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.setting.ActSubDeviceSettingDefault$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSubDeviceSettingDefault.this.lambda$startObserve$1((Device) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Device device) {
        ((ActSubDeviceSettingDefaultVM) this.mViewModel).controlDevice.setValue(device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device) {
        ((ActSubDeviceSettingDefaultBinding) this.mViewBinding).tvDeviceName.setText(device.getDeviceName());
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(device.getMacdeviceid());
        String str = "";
        if (deviceByDeviceId == null) {
            ((ActSubDeviceSettingDefaultBinding) this.mViewBinding).tvSubordinateDevice.setText("");
            ((ActSubDeviceSettingDefaultBinding) this.mViewBinding).tvRoomName.setText("");
            return;
        }
        Floor floor = Injection.repo().home().getFloor(device.getFloorId());
        Room room = Injection.repo().home().getRoom(device.getRoomId());
        ((ActSubDeviceSettingDefaultBinding) this.mViewBinding).tvSubordinateDevice.setText(deviceByDeviceId.getDeviceName());
        AppCompatTextView appCompatTextView = ((ActSubDeviceSettingDefaultBinding) this.mViewBinding).tvRoomName;
        if (room != null) {
            str = floor.getFloorName() + " " + room.getRoomName();
        } else if (floor != null) {
            str = floor.getFloorName();
        }
        appCompatTextView.setText(str);
        ((ActSubDeviceSettingDefaultVM) this.mViewModel).roomPickHelper.startObserve(this, device.getPlaceId(), device.getRoomId());
        if (device.getProductId().equals(ProductId.ID_IR_CURTAIN)) {
            ((ActSubDeviceSettingDefaultBinding) this.mViewBinding).layoutChangeRoom.setEnabled(false);
            ((ActSubDeviceSettingDefaultBinding) this.mViewBinding).tvRoomName.setTextColor(getResources().getColor(R.color.color_light_gray));
        } else {
            ((ActSubDeviceSettingDefaultBinding) this.mViewBinding).ivRoomNameGo.setVisibility(0);
        }
    }
}