package com.ltech.smarthome.ui.login;

import android.text.TextUtils;
import android.view.View;
import androidx.databinding.ObservableField;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.SmartHomeRetrofit;
import com.ltech.smarthome.utils.EventBusUtils;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.LiveBusHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class FtPhoneRegVM extends BaseRegisterViewModel {
    public ObservableField<String> country = new ObservableField<>("中国");
    public ObservableField<String> countryCode = new ObservableField<>("China");
    public SingleLiveEvent<Void> selectAreaEvent = new SingleLiveEvent<>();
    public MutableLiveData<String> countryStr = new MutableLiveData<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.login.FtPhoneRegVM$$ExternalSyntheticLambda4
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            FtPhoneRegVM.this.lambda$new$0((View) obj);
        }
    });

    protected int getSmsCodeType() {
        return 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.bt_register /* 2131296480 */:
                if (TextUtils.isEmpty(this.countryCode.get())) {
                    this.error_tips.set(ActivityUtils.getTopActivity().getString(R.string.select_your_region));
                    break;
                } else if (TextUtils.isEmpty(this.account.get())) {
                    this.error_tips.set(ActivityUtils.getTopActivity().getString(R.string.tip_input_right_phone_mail));
                    break;
                } else if (RegexUtils.isMobileSimple(this.account.get()) && !this.countryCode.get().equals("China")) {
                    SmartToast.showShort(R.string.tip_unsupport_phone_login_register);
                    break;
                } else {
                    if ("China".equals(this.countryCode.get())) {
                        if (!RegexUtils.isMobileExact(this.account.get()) && !RegexUtils.isEmail(this.account.get())) {
                            this.error_tips.set(ActivityUtils.getTopActivity().getString(R.string.tip_input_right_phone_mail));
                            break;
                        }
                    } else if (!RegexUtils.isEmail(this.account.get())) {
                        this.error_tips.set(ActivityUtils.getTopActivity().getString(R.string.tip_input_mail));
                        break;
                    }
                    if (TextUtils.isEmpty(this.verificationCode.get())) {
                        this.error_tips.set(ActivityUtils.getTopActivity().getString(R.string.tip_input_verification_code));
                        break;
                    } else if (TextUtils.isEmpty(this.password.get())) {
                        this.error_tips.set(ActivityUtils.getTopActivity().getString(R.string.tip_input_password));
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
                }
                break;
            case R.id.et_country /* 2131296708 */:
            case R.id.iv_rigth /* 2131297218 */:
            case R.id.til_country /* 2131298365 */:
                LHomeLog.i(getClass(), "to selectArea");
                this.selectAreaEvent.call();
                break;
            case R.id.iv_password /* 2131297168 */:
                this.pSwitchEvent.setValue(Boolean.valueOf(this.pSwitchEvent.getValue() == null || !this.pSwitchEvent.getValue().booleanValue()));
                break;
            case R.id.tv_change /* 2131298507 */:
                EventBusUtils.post(new LiveBusHelper(2));
                break;
            case R.id.tv_verification /* 2131299092 */:
                getSmsCode(getSmsCodeType());
                break;
        }
    }

    protected void requestApi() {
        String str = "1";
        if (!RegexUtils.isMobileExact(this.account.get()) && RegexUtils.isEmail(this.account.get())) {
            str = "2";
        }
        ((ObservableSubscribeProxy) Injection.net().register(this.account.get(), this.verificationCode.get(), this.password.get(), str, this.countryCode.get()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.login.FtPhoneRegVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtPhoneRegVM.this.lambda$requestApi$1((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.login.FtPhoneRegVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtPhoneRegVM.this.lambda$requestApi$2(obj);
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

    protected void getSmsCode(int type) {
        Observable<Object> sendEmailCode;
        if (this.isCountdown) {
            return;
        }
        if (TextUtils.isEmpty(this.countryCode.get())) {
            this.error_tips.set(ActivityUtils.getTopActivity().getString(R.string.select_your_region));
            return;
        }
        if (TextUtils.isEmpty(this.account.get())) {
            this.error_tips.set(ActivityUtils.getTopActivity().getString(R.string.tip_input_right_phone_mail));
            return;
        }
        LHomeLog.i(getClass(), "account.get() = " + this.account.get() + "__countryCode.get()=" + this.countryCode.get());
        if ("China".equals(this.countryCode.get())) {
            if (!RegexUtils.isMobileExact(this.account.get()) && !RegexUtils.isEmail(this.account.get())) {
                if (ActivityUtils.getTopActivity() instanceof ActChangePhone) {
                    this.error_tips.set(ActivityUtils.getTopActivity().getString(R.string.tip_input_right_phone));
                    return;
                } else {
                    this.error_tips.set(ActivityUtils.getTopActivity().getString(R.string.tip_input_right_phone_mail));
                    return;
                }
            }
        } else if (!RegexUtils.isEmail(this.account.get())) {
            SmartToast.showShort(R.string.tip_input_mail);
            this.error_tips.set(ActivityUtils.getTopActivity().getString(R.string.tip_input_mail));
            return;
        }
        SmartHomeRetrofit net = Injection.net();
        if (RegexUtils.isMobileExact(this.account.get())) {
            sendEmailCode = net.sendSmsCode(this.account.get(), type);
        } else {
            sendEmailCode = net.sendEmailCode(this.account.get(), getSmsCodeType());
        }
        ((ObservableSubscribeProxy) sendEmailCode.doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.login.FtPhoneRegVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtPhoneRegVM.this.lambda$getSmsCode$3((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.login.FtPhoneRegVM$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtPhoneRegVM.this.lambda$getSmsCode$4(obj);
            }
        }, this.errorConsumer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getSmsCode$3(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.verification_code_sending));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getSmsCode$4(Object obj) throws Exception {
        dismissLoadingDialog();
        this.showVerificationCodeSendEvent.setValue(ActivityUtils.getTopActivity().getString(R.string.verification_code_sent, new Object[]{this.account.get()}));
        this.countDownTimer.start();
        this.isCountdown = true;
    }

    public void loadCountry() {
        String str = this.countryCode.get() == null ? "China" : this.countryCode.get();
        MutableLiveData<String> mutableLiveData = this.countryStr;
        if (LanguageUtils.isChinese(ActivityUtils.getTopActivity())) {
            str = this.country.get();
        }
        mutableLiveData.setValue(str);
    }
}