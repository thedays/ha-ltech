package com.ltech.smarthome.ui.login;

import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.response.user.LoginResponse;
import com.ltech.smarthome.utils.RxUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/* loaded from: classes4.dex */
public class FtBindPhoneVM extends FtPhoneRegVM {
    private String openid;
    private String source;

    @Override // com.ltech.smarthome.ui.login.FtPhoneRegVM
    protected int getSmsCodeType() {
        return 3;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override // com.ltech.smarthome.ui.login.FtPhoneRegVM
    public void requestApi() {
        ((ObservableSubscribeProxy) Injection.net().oauthBind(this.openid, this.verificationCode.get(), this.source, this.account.get(), true, this.password.get()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.login.FtBindPhoneVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtBindPhoneVM.this.lambda$requestApi$0((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.login.FtBindPhoneVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtBindPhoneVM.this.lambda$requestApi$1((LoginResponse) obj);
            }
        }, this.errorConsumer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestApi$0(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.requesting));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestApi$1(LoginResponse loginResponse) throws Exception {
        setResult(2002);
        parseLoginResult(loginResponse);
        bindUser();
    }
}