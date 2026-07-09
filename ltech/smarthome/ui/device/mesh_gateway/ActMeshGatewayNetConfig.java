package com.ltech.smarthome.ui.device.mesh_gateway;

import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActNetConfigBinding;
import com.ltech.smarthome.ui.config.ActNetConfig;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;

/* loaded from: classes4.dex */
public class ActMeshGatewayNetConfig extends ActNetConfig {
    @Override // com.ltech.smarthome.ui.config.ActNetConfig, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        ((ActNetConfigBinding) this.mViewBinding).btNext.setText(getString(R.string.bind));
        ((ActNetConfigBinding) this.mViewBinding).tvQuestion.setVisibility(0);
        ((ActNetConfigBinding) this.mViewBinding).tvQuestion.setText(getString(R.string.gateway_config_tip));
    }

    @Override // com.ltech.smarthome.ui.config.ActNetConfig
    protected void next() {
        if (!validSSID()) {
            SmartToast.showShort(R.string.please_connect_wifi);
        } else {
            NavUtils.destination(ActMeshGatewayConnectNet.class).withLong(Constants.CONTROL_ID, getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).withString(Constants.SSID, this.ssidLiveData.getValue()).withString(Constants.PWD, this.pwdLiveData.getValue()).navigation(this);
        }
    }
}