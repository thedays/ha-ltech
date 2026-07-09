package com.ltech.smarthome.ui.device.ir;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.device_param.DiyIrParam;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.utils.StringUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActIrDiyVM extends BaseIrVM {
    private byte[] byteCmd;
    public int cmdIndex;
    public SingleLiveEvent<Void> learnIrEvent = new SingleLiveEvent<>();
    public StringBuilder cmdBuilder = new StringBuilder();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.ir.ActIrDiyVM$$ExternalSyntheticLambda3
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActIrDiyVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        if (view.getId() != R.id.tv_add) {
            return;
        }
        this.learnIrEvent.call();
    }

    public void sendCmd(DiyIrParam.DiyIrKey diyIrKey) {
        if (diyIrKey.getKeyData() != null) {
            this.byteCmd = StringUtils.hexStringToByte(diyIrKey.getKeyData());
        } else {
            this.byteCmd = new byte[0];
        }
        this.cmdName = diyIrKey.getKeyName();
        this.mIrCmdParam = CmdAssistant.getGatewayAssistant(this.controlDevice.getValue(), new int[0]).sendDiyControl(ActivityUtils.getTopActivity(), this.byteCmd, !this.selectAction);
    }

    public void changeKeyData(int position, String keyData) {
        final DiyIrParam diyIrParam = (DiyIrParam) this.controlDevice.getValue().getParam(DiyIrParam.class);
        if (diyIrParam != null) {
            diyIrParam.changeKeyData(position, keyData);
            ((ObservableSubscribeProxy) Injection.net().updateParam(this.controlDevice.getValue().getDeviceId(), GsonUtils.toJson(diyIrParam)).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.ir.ActIrDiyVM$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActIrDiyVM.this.lambda$changeKeyData$1((Disposable) obj);
                }
            }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.ir.ActIrDiyVM$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ActIrDiyVM.this.dismissLoadingDialog();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.ir.ActIrDiyVM$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActIrDiyVM.this.lambda$changeKeyData$2(diyIrParam, obj);
                }
            }, new SmartErrorComsumer());
        } else {
            SmartToast.showShort(R.string.save_success);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeKeyData$1(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeKeyData$2(DiyIrParam diyIrParam, Object obj) throws Exception {
        this.controlDevice.getValue().setParam(diyIrParam);
        Injection.repo().device().saveDevice(this.controlDevice.getValue());
        SmartToast.showShort(R.string.save_success);
    }
}