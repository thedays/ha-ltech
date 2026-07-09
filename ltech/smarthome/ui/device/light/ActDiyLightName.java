package com.ltech.smarthome.ui.device.light;

import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActDiyLightNameBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.utils.Constants;

/* loaded from: classes4.dex */
public class ActDiyLightName extends VMActivity<ActDiyLightNameBinding, ActDiyLightNameVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_diy_light_name;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        setEditString(getString(R.string.save));
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.edit_name));
        ((ActDiyLightNameVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActDiyLightNameVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        if (((ActDiyLightNameVM) this.mViewModel).groupControl) {
            Group groupById = Injection.repo().group().getGroupById(((ActDiyLightNameVM) this.mViewModel).controlId);
            ((ActDiyLightNameVM) this.mViewModel).controlObject = groupById;
            ((ActDiyLightNameBinding) this.mViewBinding).setNameText(getString(R.string.group_name));
            ((ActDiyLightNameVM) this.mViewModel).lightName.set(groupById.getGroupName());
            return;
        }
        Device deviceById = Injection.repo().device().getDeviceById(((ActDiyLightNameVM) this.mViewModel).controlId);
        ((ActDiyLightNameVM) this.mViewModel).controlObject = deviceById;
        ((ActDiyLightNameBinding) this.mViewBinding).setNameText(getString(R.string.device_name));
        ((ActDiyLightNameVM) this.mViewModel).lightName.set(deviceById.getDeviceName());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        ((ActDiyLightNameVM) this.mViewModel).changeLightName();
    }
}