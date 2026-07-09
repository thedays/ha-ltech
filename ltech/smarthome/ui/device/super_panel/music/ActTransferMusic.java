package com.ltech.smarthome.ui.device.super_panel.music;

import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActTransferMusicBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.utils.Constants;

/* loaded from: classes4.dex */
public class ActTransferMusic extends VMActivity<ActTransferMusicBinding, ActDcaMusicListVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_transfer_music;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.app_str_wifi_transfer_music));
        ((ActDcaMusicListVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActDcaMusicListVM) this.mViewModel).controlDevice = Injection.repo().device().getDeviceById(((ActDcaMusicListVM) this.mViewModel).controlId);
        ((ActDcaMusicListVM) this.mViewModel).deviceMac = getIntent().getStringExtra(Constants.MAC_ADDRESS);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActDcaMusicListVM) this.mViewModel).controlWifiTransfer(true);
    }

    @Override // com.ltech.smarthome.base.VMActivity, com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        ((ActDcaMusicListVM) this.mViewModel).controlWifiTransfer(false);
        super.onDestroy();
    }
}