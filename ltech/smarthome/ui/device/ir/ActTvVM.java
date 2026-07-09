package com.ltech.smarthome.ui.device.ir;

import android.view.View;
import androidx.fragment.app.Fragment;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;

/* loaded from: classes4.dex */
public class ActTvVM extends BaseTvVM {
    private static final int[] existFun = {1, 136, 116, 111, 45, 43, 44, 50, 51, 42, 47, 48, 49, 46, 56, 61, 66, 71, 76, 81, 86, 91, 96, 101};

    @Override // com.ltech.smarthome.ui.device.ir.BaseTvVM
    protected Fragment createFunFragment() {
        return FtTvFun.newInstance(ActTvVM.class);
    }

    @Override // com.ltech.smarthome.ui.device.ir.BaseTvVM
    protected Fragment createNumFragment() {
        return FtTvNum.newInstance(ActTvVM.class);
    }

    @Override // com.ltech.smarthome.ui.device.ir.BaseTvVM
    protected Fragment createExtFunFragment() {
        return FtExtFun.newInstance(ActTvVM.class);
    }

    @Override // com.ltech.smarthome.ui.device.ir.BaseTvVM
    protected int[] getExistFun() {
        return existFun;
    }

    @Override // com.ltech.smarthome.ui.device.ir.BaseTvVM
    protected BindingCommand<View> getViewClick() {
        return new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.ir.ActTvVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActTvVM.this.lambda$getViewClick$0((View) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getViewClick$0(View view) {
        switch (view.getId()) {
            case R.id.iv_back /* 2131296947 */:
                sendIrControl(116);
                clickAnimate(view);
                break;
            case R.id.iv_home /* 2131297093 */:
                sendIrControl(136);
                clickAnimate(view);
                break;
            case R.id.iv_power /* 2131297184 */:
                sendIrControl(1);
                clickAnimate(view);
                break;
            case R.id.tv_more /* 2131298785 */:
                this.changeFragmentEvent.setValue(BaseTvVM.KEY_EXT_FUN);
                break;
            case R.id.tv_num /* 2131298821 */:
                this.changeFragmentEvent.setValue(BaseTvVM.KEY_NUM);
                break;
        }
    }
}