package com.ltech.smarthome.ui.login;

import android.content.Intent;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.videogo.openapi.model.req.GetSmsCodeResetReq;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class FtFindPhonePwdVM extends FtPhoneRegVM {
    @Override // com.ltech.smarthome.ui.login.FtPhoneRegVM
    protected int getSmsCodeType() {
        return 4;
    }

    @Override // com.ltech.smarthome.ui.login.FtPhoneRegVM
    public void requestApi() {
        String str;
        if (!RegexUtils.isEmail(this.account.get())) {
            str = "1";
        } else {
            str = "2";
        }
        ((ObservableSubscribeProxy) Injection.net().setPassword(this.account.get(), this.password.get(), this.verificationCode.get(), str).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.login.FtFindPhonePwdVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtFindPhonePwdVM.this.lambda$requestApi$0((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.login.FtFindPhonePwdVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Action
            public final void run() {
                FtFindPhonePwdVM.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.login.FtFindPhonePwdVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtFindPhonePwdVM.this.lambda$requestApi$1(obj);
            }
        }, this.errorConsumer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestApi$0(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.requesting));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestApi$1(Object obj) throws Exception {
        SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.find_pwd_success));
        ActivityUtils.getTopActivity().setResult(2003, setData());
        ActivityUtils.getTopActivity().finish();
    }

    private Intent setData() {
        Intent intent = new Intent();
        intent.putExtra("psw", this.password.get());
        intent.putExtra(GetSmsCodeResetReq.ACCOUNT, this.account.get());
        intent.putExtra(ActSelectCountry.SELECT_COUNTRY_KEY, this.country.get());
        intent.putExtra(ActSelectCountry.SELECT_COUNTRY_EN_KEY, this.countryCode.get());
        return intent;
    }
}