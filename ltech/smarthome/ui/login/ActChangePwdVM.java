package com.ltech.smarthome.ui.login;

import android.text.TextUtils;
import android.view.View;
import androidx.databinding.ObservableField;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActChangePwdVM extends BaseViewModel {
    private SmartErrorComsumer errorComsumer;
    public SingleLiveEvent<Boolean> pOldPwdSwitchEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> pNewPwdSwitchEvent = new SingleLiveEvent<>();
    public ObservableField<String> oldPwd = new ObservableField<>("");
    public ObservableField<String> newPwd = new ObservableField<>("");
    public ObservableField<String> error_tips = new ObservableField<>("");
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.login.ActChangePwdVM$$ExternalSyntheticLambda3
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActChangePwdVM.this.lambda$new$2((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(View view) {
        switch (view.getId()) {
            case R.id.bt_forget /* 2131296474 */:
                navigation(NavUtils.destination(ActFindPwd.class).withDefaultRequestCode());
                break;
            case R.id.bt_register /* 2131296480 */:
                if (TextUtils.isEmpty(this.oldPwd.get()) || TextUtils.isEmpty(this.newPwd.get())) {
                    this.error_tips.set(ActivityUtils.getTopActivity().getString(R.string.tip_input_password));
                    break;
                } else if (this.oldPwd.get().length() < 6 || this.newPwd.get().length() < 6) {
                    this.error_tips.set(ActivityUtils.getTopActivity().getString(R.string.tip_input_six_digit_password));
                    break;
                } else {
                    SmartErrorComsumer smartErrorComsumer = new SmartErrorComsumer();
                    this.errorComsumer = smartErrorComsumer;
                    smartErrorComsumer.setCallBackMsg(new SmartErrorComsumer.CallBackMsg() { // from class: com.ltech.smarthome.ui.login.ActChangePwdVM.1
                        @Override // com.ltech.smarthome.net.SmartErrorComsumer.CallBackMsg
                        public void handleMsg(String msg) {
                            ActChangePwdVM.this.error_tips.set(msg);
                        }
                    });
                    ((ObservableSubscribeProxy) Injection.net().changePwd(this.oldPwd.get(), this.newPwd.get()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.login.ActChangePwdVM$$ExternalSyntheticLambda0
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj) {
                            ActChangePwdVM.this.lambda$new$0((Disposable) obj);
                        }
                    }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.login.ActChangePwdVM$$ExternalSyntheticLambda1
                        @Override // io.reactivex.functions.Action
                        public final void run() {
                            ActChangePwdVM.this.dismissLoadingDialog();
                        }
                    }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.login.ActChangePwdVM$$ExternalSyntheticLambda2
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj) {
                            ActChangePwdVM.lambda$new$1(obj);
                        }
                    }, this.errorComsumer);
                    break;
                }
                break;
            case R.id.iv_new_password /* 2131297153 */:
                SingleLiveEvent<Boolean> singleLiveEvent = this.pNewPwdSwitchEvent;
                singleLiveEvent.setValue(Boolean.valueOf(singleLiveEvent.getValue() == null || !this.pNewPwdSwitchEvent.getValue().booleanValue()));
                break;
            case R.id.iv_old_password /* 2131297162 */:
                SingleLiveEvent<Boolean> singleLiveEvent2 = this.pOldPwdSwitchEvent;
                singleLiveEvent2.setValue(Boolean.valueOf(singleLiveEvent2.getValue() == null || !this.pOldPwdSwitchEvent.getValue().booleanValue()));
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    static /* synthetic */ void lambda$new$1(Object obj) throws Exception {
        SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.find_pwd_success));
        Injection.logout();
    }
}