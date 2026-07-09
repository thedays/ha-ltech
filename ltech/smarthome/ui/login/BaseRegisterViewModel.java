package com.ltech.smarthome.ui.login;

import android.os.CountDownTimer;
import androidx.databinding.ObservableField;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.user.BindUserResponse;
import com.ltech.smarthome.net.response.user.LoginResponse;
import com.ltech.smarthome.ui.control.ActHome;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.SmartToast;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class BaseRegisterViewModel extends BaseViewModel {
    public ObservableField<Boolean> checkedProtocol;
    public boolean isCountdown;
    public SingleLiveEvent<Boolean> pSwitchEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<String> showVerificationCodeSendEvent = new SingleLiveEvent<>();
    public ObservableField<String> account = new ObservableField<>("");
    public ObservableField<String> password = new ObservableField<>("");
    public ObservableField<String> verificationCode = new ObservableField<>("");
    public ObservableField<String> error_tips = new ObservableField<>("");
    public ObservableField<String> getVerificationCountdown = new ObservableField<>(ActivityUtils.getTopActivity().getString(R.string.get_verification_code));
    public ObservableField<Integer> verificationTextBg = new ObservableField<>(Integer.valueOf(R.drawable.shape_blue_bt));
    public CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) { // from class: com.ltech.smarthome.ui.login.BaseRegisterViewModel.1
        @Override // android.os.CountDownTimer
        public void onTick(long millisUntilFinished) {
            BaseRegisterViewModel.this.verificationTextBg.set(Integer.valueOf(R.drawable.shape_gray_bt));
            BaseRegisterViewModel.this.getVerificationCountdown.set(StringUtils.getString(R.string.regain) + "(" + (millisUntilFinished / 1000) + "s)");
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            BaseRegisterViewModel.this.isCountdown = false;
            BaseRegisterViewModel.this.getVerificationCountdown.set(StringUtils.getString(R.string.get_verification_code));
            BaseRegisterViewModel.this.verificationTextBg.set(Integer.valueOf(R.drawable.shape_blue_bt));
        }
    };
    protected SmartErrorComsumer errorConsumer = new SmartErrorComsumer() { // from class: com.ltech.smarthome.ui.login.BaseRegisterViewModel.2
        @Override // com.ltech.smarthome.net.SmartErrorComsumer
        protected void action(Throwable throwable) {
            BaseRegisterViewModel.this.dismissLoadingDialog();
            if (this.errorCode == 2001) {
                SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_error_2001));
                return;
            }
            if (this.errorCode == 3006) {
                SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_error_3006));
                return;
            }
            if (this.errorCode == 819) {
                SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_error_819));
                return;
            }
            if (this.errorCode == 8404) {
                SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_error_8404));
                return;
            }
            if (this.errorCode == 719) {
                SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_error_719));
                return;
            }
            if (this.errorCode == 7966) {
                SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_error_7966));
                return;
            }
            if (this.errorCode == 63006) {
                SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_error_63006));
                return;
            }
            if (this.errorCode == 63007) {
                SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_error_63007));
                return;
            }
            if (this.errorCode == 522) {
                SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_error_522));
                return;
            }
            if (this.errorCode == 5404) {
                SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_error_5404));
                return;
            }
            if (this.errorCode == 1023) {
                SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_error_1023));
                return;
            }
            if (this.errorCode == 1018) {
                SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_error_1018));
            } else if (this.errorCode == 1022) {
                SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_error_1022));
            } else {
                super.action(throwable);
            }
        }
    };

    protected void login(String account, String password) {
        login(account, password, this.errorConsumer);
    }

    protected void login(final String account, final String password, SmartErrorComsumer errorConsumer) {
        ((ObservableSubscribeProxy) Injection.net().login(account, password).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.login.BaseRegisterViewModel$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseRegisterViewModel.this.lambda$login$0((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.login.BaseRegisterViewModel$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseRegisterViewModel.this.lambda$login$1(account, password, (LoginResponse) obj);
            }
        }, errorConsumer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$login$0(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.loggining));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$login$1(String str, String str2, LoginResponse loginResponse) throws Exception {
        SharedPreferenceUtil.edit().keepShared(Constants.USER_ACCOUNT, str).commit();
        SharedPreferenceUtil.edit().keepShared(Constants.USER_PWD, str2).commit();
        SharedPreferenceUtil.queryBooleanValue(Constants.DEVICE_CHANGED);
        parseLoginResult(loginResponse);
        bindUser();
        pushBind();
    }

    private void pushBind() {
        Injection.push().pushBind(getLifecycleOwner(), Injection.push().getPushId());
    }

    protected void bindUser() {
        ((ObservableSubscribeProxy) Injection.net().bindUser().compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.login.BaseRegisterViewModel$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseRegisterViewModel.this.lambda$bindUser$2((BindUserResponse) obj);
            }
        }, this.errorConsumer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bindUser$2(BindUserResponse bindUserResponse) throws Exception {
        Injection.repo().user().parseBindUserResult((BindUserResponse.ParamBean) GsonUtils.getGson().fromJson(bindUserResponse.getParam(), BindUserResponse.ParamBean.class));
        navigation(NavUtils.destination(ActHome.class));
        finishActivity();
    }

    protected void parseLoginResult(LoginResponse response) {
        Injection.repo().user().parseLoginResult(response);
    }
}