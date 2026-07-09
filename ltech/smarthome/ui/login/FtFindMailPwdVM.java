package com.ltech.smarthome.ui.login;

import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class FtFindMailPwdVM extends FtMailRegVM {
    @Override // com.ltech.smarthome.ui.login.FtMailRegVM
    protected int getVerificationCodeType() {
        return 4;
    }

    @Override // com.ltech.smarthome.ui.login.FtMailRegVM
    public void requestApi() {
        ((ObservableSubscribeProxy) Injection.net().setPassword(this.account.get(), this.password.get(), this.verificationCode.get(), "2").delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.login.FtFindMailPwdVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtFindMailPwdVM.this.lambda$requestApi$0((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.login.FtFindMailPwdVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtFindMailPwdVM.this.lambda$requestApi$1(obj);
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
        setResult(2003);
        finishActivity();
    }
}