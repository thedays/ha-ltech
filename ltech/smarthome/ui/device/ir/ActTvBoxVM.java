package com.ltech.smarthome.ui.device.ir;

import android.view.View;
import androidx.fragment.app.Fragment;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;

/* loaded from: classes4.dex */
public class ActTvBoxVM extends BaseTvVM {
    private static final int[] existFun = {1, 116, 136, 45, 43, 44, 50, 51, 42, 47, 48, 49, 46, 56, 61, 66, 71, 76, 81, 86, 91, 96, 101};
    public SingleLiveEvent<Void> showRelateDialogEvent = new SingleLiveEvent<>();

    @Override // com.ltech.smarthome.ui.device.ir.BaseTvVM
    protected Fragment createFunFragment() {
        return FtTvFun.newInstance(ActTvBoxVM.class);
    }

    @Override // com.ltech.smarthome.ui.device.ir.BaseTvVM
    protected Fragment createNumFragment() {
        return FtTvNum.newInstance(ActTvBoxVM.class);
    }

    @Override // com.ltech.smarthome.ui.device.ir.BaseTvVM
    protected Fragment createExtFunFragment() {
        return FtExtFun.newInstance(ActTvBoxVM.class);
    }

    @Override // com.ltech.smarthome.ui.device.ir.BaseTvVM
    protected int[] getExistFun() {
        return existFun;
    }

    @Override // com.ltech.smarthome.ui.device.ir.BaseTvVM
    protected BindingCommand<View> getViewClick() {
        return new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.ir.ActTvBoxVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActTvBoxVM.this.lambda$getViewClick$0((View) obj);
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
                byte[] queryByteArrayValue = SharedPreferenceUtil.queryByteArrayValue(Constants.TV_POWER_KEY + this.controlDevice.getValue().getDeviceId());
                if (queryByteArrayValue == null) {
                    this.showRelateDialogEvent.call();
                    break;
                } else {
                    this.cmdName = ActivityUtils.getTopActivity().getString(R.string.ir_tv_power);
                    showLoadingDialog();
                    this.mIrCmdParam = CmdAssistant.getGatewayAssistant(this.controlDevice.getValue(), new int[0]).sendIrComboControl(ActivityUtils.getTopActivity(), this.mParams, queryByteArrayValue, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.device.ir.ActTvBoxVM.1
                        @Override // com.ltech.smarthome.base.IAction
                        public void act(Boolean aBoolean) {
                            ActTvBoxVM.this.dismissLoadingDialog();
                            if (aBoolean.booleanValue()) {
                                return;
                            }
                            ActTvBoxVM.this.showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.app_str_control_timeout));
                        }
                    }, !this.selectAction);
                    clickAnimate(view);
                    break;
                }
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