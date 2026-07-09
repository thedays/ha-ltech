package com.ltech.smarthome.ui.home;

import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.base.BaseManageVM;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.ui.device.setting.ActDeviceSettingDefault;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;

/* loaded from: classes4.dex */
public class ActDeviceManageVM extends BaseManageVM {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseManageVM
    /* renamed from: clickItem */
    public void lambda$getItemBinding$1(int position, Role role) {
        Place value = Injection.repo().home().getSelectPlace().getValue();
        if (value != null && this.placeId != value.getPlaceId()) {
            if (role instanceof Device) {
                Device device = (Device) role;
                NavUtils.destination(ActDeviceSettingDefault.class).withLong(Constants.PLACE_ID, this.placeId).withLong(Constants.CONTROL_ID, device.getId()).withLong("device_id", device.getDeviceId()).withBoolean(Constants.SAME_PLACE, true).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
                return;
            }
            return;
        }
        NavHelper.goSetting(role);
    }
}