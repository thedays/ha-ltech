package com.ltech.smarthome.ui.login;

import android.text.TextUtils;
import android.view.View;
import androidx.databinding.ObservableField;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.utils.EventBusUtils;
import com.ltech.smarthome.utils.LiveBusHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class FtMailRegVM extends BaseRegisterViewModel {
    public ObservableField<String> country = new ObservableField<>("中国");
    public ObservableField<String> countryCode = new ObservableField<>("China");
    public SingleLiveEvent<Void> selectAreaEvent = new SingleLiveEvent<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.login.FtMailRegVM$$ExternalSyntheticLambda4
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            FtMailRegVM.this.lambda$new$0((View) obj);
        }
    });

    protected int getVerificationCodeType() {
        return 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        boolean z = true;
        switch (view.getId()) {
            case R.id.bt_register /* 2131296480 */:
                if (!RegexUtils.isEmail(this.account.get())) {
                    this.error_tips.set(ActivityUtils.getTopActivity().getString(R.string.tip_input_mail));
                    break;
                } else if (TextUtils.isEmpty(this.password.get())) {
                    this.error_tips.set(ActivityUtils.getTopActivity().getString(R.string.tip_input_password));
                    break;
                } else if (TextUtils.isEmpty(this.verificationCode.get())) {
                    this.error_tips.set(ActivityUtils.getTopActivity().getString(R.string.tip_input_verification_code));
                    break;
                } else if (this.password.get().length() < 6) {
                    this.error_tips.set(ActivityUtils.getTopActivity().getString(R.string.tip_input_six_digit_password));
                    break;
                } else if (!this.checkedProtocol.get().booleanValue()) {
                    SmartToast.showShort(R.string.please_select_check_box);
                    break;
                } else {
                    this.countDownTimer.cancel();
                    this.countDownTimer.onFinish();
                    requestApi();
                    break;
                }
            case R.id.iv_password /* 2131297168 */:
                SingleLiveEvent<Boolean> singleLiveEvent = this.pSwitchEvent;
                if (this.pSwitchEvent.getValue() != null && this.pSwitchEvent.getValue().booleanValue()) {
                    z = false;
                }
                singleLiveEvent.setValue(Boolean.valueOf(z));
                break;
            case R.id.tv_change /* 2131298507 */:
                EventBusUtils.post(new LiveBusHelper(1));
                break;
            case R.id.tv_verification /* 2131299092 */:
                sendVerificationCode(getVerificationCodeType());
                break;
        }
    }

    protected void requestApi() {
        ((ObservableSubscribeProxy) Injection.net().register(this.account.get(), this.verificationCode.get(), this.password.get(), "2", this.countryCode.get()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.login.FtMailRegVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtMailRegVM.this.lambda$requestApi$1((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.login.FtMailRegVM$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtMailRegVM.this.lambda$requestApi$2(obj);
            }
        }, this.errorConsumer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestApi$1(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.requesting));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestApi$2(Object obj) throws Exception {
        setResult(2001);
        login(this.account.get(), this.password.get());
    }

    protected void sendVerificationCode(int actionType) {
        if (this.isCountdown) {
            return;
        }
        if (TextUtils.isEmpty(this.account.get()) || !RegexUtils.isEmail(this.account.get())) {
            this.error_tips.set(ActivityUtils.getTopActivity().getString(R.string.tip_input_mail));
        } else {
            ((ObservableSubscribeProxy) Injection.net().sendEmailCode(this.account.get(), actionType).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.login.FtMailRegVM$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    FtMailRegVM.this.lambda$sendVerificationCode$3((Disposable) obj);
                }
            }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.login.FtMailRegVM$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    FtMailRegVM.this.lambda$sendVerificationCode$4(obj);
                }
            }, this.errorConsumer);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendVerificationCode$3(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.verification_code_sending));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendVerificationCode$4(Object obj) throws Exception {
        dismissLoadingDialog();
        this.showVerificationCodeSendEvent.setValue(ActivityUtils.getTopActivity().getString(R.string.verification_code_sent, new Object[]{this.account.get()}));
        this.countDownTimer.start();
        this.isCountdown = true;
    }
}