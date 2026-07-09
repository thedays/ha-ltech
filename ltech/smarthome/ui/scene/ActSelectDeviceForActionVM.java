package com.ltech.smarthome.ui.scene;

import com.ltech.smarthome.base.BaseDeviceManageVM;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;

/* loaded from: classes4.dex */
public class ActSelectDeviceForActionVM extends BaseDeviceManageVM {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseDeviceManageVM
    /* renamed from: clickItem */
    public void lambda$getItemBinding$0(int position, Device device) {
        NavUtils.Builder goSelectAction = SceneHelper.instance().goSelectAction(device);
        if (goSelectAction != null) {
            navigation(goSelectAction.withLong(Constants.CONTROL_ID, device.getId()).withDefaultRequestCode());
        }
    }
}