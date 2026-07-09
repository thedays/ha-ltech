package com.ltech.smarthome.ui.login;

import android.view.View;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.user.CountryInfoResponse;
import com.ltech.smarthome.net.response.user.CountryListInfoResponse;
import com.ltech.smarthome.utils.RxUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSelectCountryVM extends BaseViewModel {
    public SingleLiveEvent<Void> showRegDialogEvent = new SingleLiveEvent<>();
    private ArrayList<CountryInfoResponse> countryInfoResponsesList = new ArrayList<>();
    public SingleLiveEvent<List<CountryInfoResponse>> showDataListEvent = new SingleLiveEvent<>();
    protected SmartErrorComsumer errorConsumer = new SmartErrorComsumer() { // from class: com.ltech.smarthome.ui.login.ActSelectCountryVM.1
        @Override // com.ltech.smarthome.net.SmartErrorComsumer
        protected void action(Throwable throwable) {
            super.action(throwable);
            ActSelectCountryVM.this.dismissLoadingDialog();
        }
    };
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.login.ActSelectCountryVM$$ExternalSyntheticLambda3
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ((View) obj).getId();
        }
    });

    protected void getCountry() {
        Injection.net().getCountryList().delaySubscription(500L, TimeUnit.MILLISECONDS).doFinally(new Action() { // from class: com.ltech.smarthome.ui.login.ActSelectCountryVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActSelectCountryVM.this.dismissLoadingDialog();
            }
        }).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.login.ActSelectCountryVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectCountryVM.this.lambda$getCountry$0((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.login.ActSelectCountryVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectCountryVM.this.lambda$getCountry$1((CountryListInfoResponse) obj);
            }
        }, this.errorConsumer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getCountry$0(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.get_data));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getCountry$1(CountryListInfoResponse countryListInfoResponse) throws Exception {
        dismissLoadingDialog();
        ArrayList<CountryInfoResponse> rows = countryListInfoResponse.getRows();
        this.countryInfoResponsesList = rows;
        this.showDataListEvent.setValue(rows);
    }
}