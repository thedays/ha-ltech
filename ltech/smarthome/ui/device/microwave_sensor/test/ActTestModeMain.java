package com.ltech.smarthome.ui.device.microwave_sensor.test;

import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActTestModeMainBinding;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;

/* loaded from: classes4.dex */
public class ActTestModeMain extends BaseNormalActivity<ActTestModeMainBinding> {
    private int testMode = 1;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_test_mode_main;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.test_mode));
        setBackImage(R.mipmap.icon_back);
        ((ActTestModeMainBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.test.ActTestModeMain$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActTestModeMain.this.lambda$initView$0((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        int id = view.getId();
        if (id == R.id.tv_test_mode_1) {
            this.testMode = 1;
        } else if (id == R.id.tv_test_mode_2) {
            this.testMode = 2;
        } else if (id == R.id.tv_test_mode_3) {
            this.testMode = 3;
        }
        NavUtils.destination(ActTestPrepare.class).withInt(Constants.TEST_MODE, this.testMode).withInt(Constants.SELECT_POSITION, getIntent().getIntExtra(Constants.SELECT_POSITION, -1)).navigation(this);
    }
}