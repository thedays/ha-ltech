package com.ltech.smarthome.nfc;

import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActNfcRestoreBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.utils.Constants;

/* loaded from: classes4.dex */
public class ActNfcRestore extends BaseNfcActivity<ActNfcRestoreBinding, BaseViewModel> {
    private Device device;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_nfc_restore;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.restore_factory));
        setBackImage(R.mipmap.icon_back);
        long longExtra = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        if (longExtra != -1) {
            this.device = Injection.repo().device().getDeviceById(longExtra);
        }
        ((ActNfcRestoreBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.nfc.ActNfcRestore$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActNfcRestore.this.lambda$initView$0((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        if (this.device == null) {
            this.batchWrite = true;
        }
        WriteVirtualHelper.instance().init(this, this.device, false);
        showNfcDialog();
    }
}