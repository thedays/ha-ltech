package com.ltech.smarthome.ui.config;

import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActQrCodeScanResultBinding;
import com.ltech.smarthome.ui.control.ActHome;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;

/* loaded from: classes4.dex */
public class ActQrCodeScanResult extends VMActivity<ActQrCodeScanResultBinding, ActQrCodeScanResultVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_qr_code_scan_result;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.app_str_qr_scan_result));
        ((ActQrCodeScanResultVM) this.mViewModel).qrCodeResult = getIntent().getStringExtra(Constants.QR_CODE_INFO);
        ((ActQrCodeScanResultBinding) this.mViewBinding).homeName.setText(getIntent().getStringExtra(Constants.PLACE_NAME));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        NavUtils.destination(ActHome.class).navigation(this);
    }
}