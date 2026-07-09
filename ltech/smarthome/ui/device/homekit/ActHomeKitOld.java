package com.ltech.smarthome.ui.device.homekit;

import android.content.Intent;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActHomeKitOldBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;

/* loaded from: classes4.dex */
public class ActHomeKitOld extends BaseNormalActivity<ActHomeKitOldBinding> {
    private long controlId;
    private Device device;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_home_kit_old;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        this.controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        Device deviceById = Injection.repo().device().getDeviceById(this.controlId);
        this.device = deviceById;
        if (deviceById != null) {
            setTitle(deviceById.getDeviceName());
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        NavHelper.goSetting(this.device);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (5002 == resultCode || 5001 == resultCode) {
            finishActivity();
        }
    }
}