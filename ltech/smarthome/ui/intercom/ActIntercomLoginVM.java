package com.ltech.smarthome.ui.intercom;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.ResultBean;
import com.ltech.smarthome.net.response.intercom.CommunityInfoResponse;
import com.ltech.smarthome.net.response.intercom.IntercomLoginResponse;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.List;

/* loaded from: classes4.dex */
public class ActIntercomLoginVM extends BaseViewModel {
    public long placeId;
    public SingleLiveEvent<Void> clickToLoginEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<List<CommunityInfoResponse.CommunityInfo>> showCommunityInfoListEvent = new SingleLiveEvent<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomLoginVM$$ExternalSyntheticLambda3
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActIntercomLoginVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        if (view.getId() != R.id.tv_login) {
            return;
        }
        this.clickToLoginEvent.call();
    }

    public void login(String account, String password) {
        if (account.isEmpty() || password.isEmpty()) {
            return;
        }
        ((ObservableSubscribeProxy) Injection.net().intercomLogin(account, password).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomLoginVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActIntercomLoginVM.this.lambda$login$1((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomLoginVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActIntercomLoginVM.this.lambda$login$2((IntercomLoginResponse) obj);
            }
        }, new SmartErrorComsumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomLoginVM.1
            @Override // com.ltech.smarthome.net.SmartErrorComsumer
            protected void action(Throwable throwable) {
                ActIntercomLoginVM.this.dismissLoadingDialog();
                if (this.errorCode == 1700 || this.errorCode == 1070) {
                    SmartToast.showCenterShort(ActivityUtils.getTopActivity().getString(R.string.app_str_error_1405));
                } else {
                    super.action(throwable);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$login$1(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.loggining));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$login$2(IntercomLoginResponse intercomLoginResponse) throws Exception {
        dismissLoadingDialog();
        NavUtils.destination(ActIntercomSelectCommunity.class).withLong(Constants.PLACE_ID, this.placeId).navigation(ActivityUtils.getTopActivity());
        finishActivity();
    }

    public void initData() {
        ((ObservableSubscribeProxy) Injection.net().getCommunityInfo().compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomLoginVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActIntercomLoginVM.this.lambda$initData$3((CommunityInfoResponse) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initData$3(CommunityInfoResponse communityInfoResponse) throws Exception {
        this.showCommunityInfoListEvent.setValue(communityInfoResponse.getData());
    }

    public void selectCommunity(long id) {
        ((ObservableSubscribeProxy) Injection.net().importCommunity(id, this.placeId).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomLoginVM$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActIntercomLoginVM.this.lambda$selectCommunity$4((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomLoginVM$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActIntercomLoginVM.this.lambda$selectCommunity$5((ResultBean) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$selectCommunity$4(Disposable disposable) throws Exception {
        showLoadingDialog("");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$selectCommunity$5(ResultBean resultBean) throws Exception {
        if (resultBean.getRet() == 0) {
            Injection.intercom().login();
        } else {
            dismissLoadingDialog();
            SmartToast.showCenterShort(resultBean.getMessage());
        }
    }
}