package com.ltech.smarthome.ui.control;

import android.text.TextUtils;
import android.view.View;
import androidx.databinding.ObservableField;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.xiaomi.mipush.sdk.Constants;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActFeedBackVM extends BaseViewModel {
    public ObservableField<String> opinion = new ObservableField<>("");
    public ObservableField<String> contact_way = new ObservableField<>("");
    protected SmartErrorComsumer errorConsumer = new SmartErrorComsumer() { // from class: com.ltech.smarthome.ui.control.ActFeedBackVM.1
        @Override // com.ltech.smarthome.net.SmartErrorComsumer
        protected void action(Throwable throwable) {
            super.action(throwable);
            ActFeedBackVM.this.dismissLoadingDialog();
        }
    };
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.control.ActFeedBackVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActFeedBackVM.this.lambda$new$2((View) obj);
        }
    });

    protected void commit(String opinion, String contact_way) {
        Injection.net().addFeedback(contact_way + Constants.ACCEPT_TIME_SEPARATOR_SP + opinion).delaySubscription(100L, TimeUnit.MILLISECONDS).doFinally(new Action() { // from class: com.ltech.smarthome.ui.control.ActFeedBackVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActFeedBackVM.this.dismissLoadingDialog();
            }
        }).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.ActFeedBackVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActFeedBackVM.this.lambda$commit$0((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.ActFeedBackVM$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActFeedBackVM.this.lambda$commit$1(obj);
            }
        }, this.errorConsumer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$commit$0(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.committing));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$commit$1(Object obj) throws Exception {
        dismissLoadingDialog();
        SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_submit_success));
        this.opinion.set("");
        this.contact_way.set("");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(View view) {
        if (view.getId() != R.id.bt_commit) {
            return;
        }
        if (TextUtils.isEmpty(this.opinion.get())) {
            SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.opinion_input_null));
        } else {
            commit(this.opinion.get(), this.contact_way.get());
        }
    }
}