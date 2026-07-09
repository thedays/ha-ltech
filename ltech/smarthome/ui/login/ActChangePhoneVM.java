package com.ltech.smarthome.ui.login;

import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.response.user.ChangPhoneResponse;
import com.ltech.smarthome.utils.RxUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActChangePhoneVM extends FtPhoneRegVM {
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.login.ActChangePhoneVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActChangePhoneVM.this.lambda$new$0((View) obj);
        }
    });

    @Override // com.ltech.smarthome.ui.login.FtPhoneRegVM
    protected int getSmsCodeType() {
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        int id = view.getId();
        if (id != R.id.bt_register) {
            if (id != R.id.tv_verification) {
                return;
            }
            getSmsCode(getSmsCodeType());
        } else {
            if (TextUtils.isEmpty(this.countryCode.get())) {
                this.error_tips.set(ActivityUtils.getTopActivity().getString(R.string.select_your_region));
                return;
            }
            if (!RegexUtils.isMobileExact(this.account.get())) {
                this.error_tips.set(ActivityUtils.getTopActivity().getString(R.string.tip_input_phone));
            } else {
                if (TextUtils.isEmpty(this.verificationCode.get())) {
                    this.error_tips.set(ActivityUtils.getTopActivity().getString(R.string.tip_input_verification_code));
                    return;
                }
                this.countDownTimer.cancel();
                this.countDownTimer.onFinish();
                requestApi();
            }
        }
    }

    @Override // com.ltech.smarthome.ui.login.FtPhoneRegVM
    protected void requestApi() {
        ((ObservableSubscribeProxy) Injection.net().changePhone(this.account.get(), this.verificationCode.get()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.login.ActChangePhoneVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActChangePhoneVM.this.lambda$requestApi$1((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.login.ActChangePhoneVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActChangePhoneVM.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.login.ActChangePhoneVM$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActChangePhoneVM.this.lambda$requestApi$2((ChangPhoneResponse) obj);
            }
        }, this.errorConsumer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestApi$1(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestApi$2(ChangPhoneResponse changPhoneResponse) throws Exception {
        Injection.repo().user().changePhone(changPhoneResponse.getMobilephone());
        finishActivity();
    }
}