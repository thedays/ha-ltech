package com.ltech.smarthome.ui.config;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import anetwork.channel.util.RequestConstant;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.placeuser.PlaceUserApplyRequest;
import com.ltech.smarthome.ui.control.ActHome;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActQrCodeScanResultVM extends BaseViewModel {
    public BindingCommand<View> clickCommand = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.config.ActQrCodeScanResultVM$$ExternalSyntheticLambda1
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActQrCodeScanResultVM.this.lambda$new$0((View) obj);
        }
    });
    public String qrCodeResult;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        if (view.getId() != R.id.bt_request_join) {
            return;
        }
        requestJoin();
    }

    private void requestJoin() {
        ((ObservableSubscribeProxy) Injection.net().applyJoinPlace(new PlaceUserApplyRequest(this.qrCodeResult, Injection.repo().user().getUserId())).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.config.ActQrCodeScanResultVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActQrCodeScanResultVM.this.lambda$requestJoin$1((Disposable) obj);
            }
        }).doFinally(new Action() { // from class: com.ltech.smarthome.ui.config.ActQrCodeScanResultVM$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActQrCodeScanResultVM.this.dismissLoadingDialog();
            }
        }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.config.ActQrCodeScanResultVM$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActQrCodeScanResultVM.this.lambda$requestJoin$2(obj);
            }
        }, new SmartErrorComsumer() { // from class: com.ltech.smarthome.ui.config.ActQrCodeScanResultVM.1
            @Override // com.ltech.smarthome.net.SmartErrorComsumer
            protected void action(Throwable throwable) {
                LHomeLog.i(RequestConstant.ENV_TEST, getClass(), "ActQrCodeScanResultVM--->" + this.errorMessage);
                SmartToast.showShort(this.errorMessage);
                ActQrCodeScanResultVM.this.dismissLoadingDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestJoin$1(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.requesting));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestJoin$2(Object obj) throws Exception {
        dismissLoadingDialog();
        showTipDialog();
    }

    private void showTipDialog() {
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), ActivityUtils.getTopActivity().getString(R.string.tips), ActivityUtils.getTopActivity().getString(R.string.app_dialog_str_join_tips)).setCancelable(false).setOkButton(ActivityUtils.getTopActivity().getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.config.ActQrCodeScanResultVM$$ExternalSyntheticLambda0
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return ActQrCodeScanResultVM.lambda$showTipDialog$3(baseDialog, view);
            }
        });
    }

    static /* synthetic */ boolean lambda$showTipDialog$3(BaseDialog baseDialog, View view) {
        NavUtils.destination(ActHome.class).navigation(ActivityUtils.getTopActivity());
        return false;
    }
}