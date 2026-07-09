package com.ltech.smarthome.ui.login;

import android.text.TextUtils;
import android.view.View;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.smart.message.utils.LHomeLog;
import java.net.SocketTimeoutException;

/* loaded from: classes4.dex */
public class ActLoginVM extends BaseRegisterViewModel {
    public ObservableField<String> errorTips = new ObservableField<>("");
    public SingleLiveEvent<Void> showRegDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showFindPwdDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> pSwitchEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> pErrorEvent = new SingleLiveEvent<>();
    public ObservableField<String> country = new ObservableField<>("中国");
    public ObservableField<String> countryCode = new ObservableField<>("China");
    public MutableLiveData<String> countryStr = new MutableLiveData<>();
    public SingleLiveEvent<Void> selectAreaEvent = new SingleLiveEvent<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.login.ActLoginVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActLoginVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.bt_login /* 2131296476 */:
                if (TextUtils.isEmpty(this.account.get())) {
                    this.errorTips.set(ActivityUtils.getTopActivity().getString(this.countryCode.get().equals("China") ? R.string.tip_input_phone_mail : R.string.tip_input_mail_empty));
                    break;
                } else if (!RegexUtils.isEmail(this.account.get()) && !RegexUtils.isMobileExact(this.account.get())) {
                    this.errorTips.set(ActivityUtils.getTopActivity().getString(this.countryCode.get().equals("China") ? R.string.tip_input_phone_mail_correct : R.string.tip_input_mail));
                    break;
                } else if (RegexUtils.isMobileSimple(this.account.get()) && !this.countryCode.get().equals("China")) {
                    SmartToast.showShort(R.string.tip_unsupport_phone_login_register);
                    break;
                } else if (TextUtils.isEmpty(this.password.get())) {
                    this.errorTips.set(ActivityUtils.getTopActivity().getString(R.string.tip_input_password));
                    break;
                } else {
                    SmartErrorComsumer smartErrorComsumer = new SmartErrorComsumer() { // from class: com.ltech.smarthome.ui.login.ActLoginVM.1
                        @Override // com.ltech.smarthome.net.SmartErrorComsumer
                        protected void action(Throwable throwable) {
                            ActLoginVM.this.dismissLoadingDialog();
                            if (throwable instanceof SocketTimeoutException) {
                                ActLoginVM.this.errorTips.set(ActivityUtils.getTopActivity().getString(R.string.network_timeout));
                                return;
                            }
                            if (this.errorCode == 404) {
                                ActLoginVM.this.errorTips.set(ActivityUtils.getTopActivity().getString(R.string.login_404));
                                return;
                            }
                            if (this.errorCode == 1405) {
                                ActLoginVM.this.errorTips.set(ActivityUtils.getTopActivity().getString(R.string.app_str_error_1405));
                            } else if (this.errorCode == 1404) {
                                ActLoginVM.this.errorTips.set(ActivityUtils.getTopActivity().getString(R.string.app_str_error_1404));
                            } else {
                                super.action(throwable);
                            }
                        }
                    };
                    smartErrorComsumer.setCallBackMsg(new SmartErrorComsumer.CallBackMsg() { // from class: com.ltech.smarthome.ui.login.ActLoginVM.2
                        @Override // com.ltech.smarthome.net.SmartErrorComsumer.CallBackMsg
                        public void handleMsg(String msg) {
                            ActLoginVM.this.errorTips.set(msg);
                        }
                    });
                    login(this.account.get(), this.password.get(), smartErrorComsumer);
                    break;
                }
                break;
            case R.id.et_country /* 2131296708 */:
            case R.id.iv_rigth /* 2131297218 */:
            case R.id.til_country /* 2131298365 */:
                LHomeLog.i(getClass(), "iv_rigth to selectArea");
                this.selectAreaEvent.call();
                LHomeLog.i(getClass(), "iv_rigth to selectArea....");
                break;
            case R.id.iv_password /* 2131297168 */:
                SingleLiveEvent<Boolean> singleLiveEvent = this.pSwitchEvent;
                singleLiveEvent.setValue(Boolean.valueOf(singleLiveEvent.getValue() == null || !this.pSwitchEvent.getValue().booleanValue()));
                break;
            case R.id.tv_find_back_pwd /* 2131298652 */:
                this.showFindPwdDialogEvent.call();
                break;
            case R.id.tv_register /* 2131298909 */:
                this.showRegDialogEvent.call();
                break;
        }
    }

    public void loadCountry() {
        String str = this.countryCode.get() == null ? "China" : this.countryCode.get();
        MutableLiveData<String> mutableLiveData = this.countryStr;
        if (LanguageUtils.isChinese(getContext())) {
            str = this.country.get();
        }
        mutableLiveData.setValue(str);
    }
}