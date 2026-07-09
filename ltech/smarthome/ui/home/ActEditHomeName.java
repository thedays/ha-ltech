package com.ltech.smarthome.ui.home;

import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActEditNameBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActEditHomeName extends BaseNormalActivity<ActEditNameBinding> {
    private long mPlaceId;
    private MutableLiveData<String> mPlaceName = new MutableLiveData<>();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_edit_name;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        this.mPlaceId = getIntent().getLongExtra(Constants.PLACE_ID, 0L);
        this.mPlaceName.setValue(getIntent().getStringExtra(Constants.PLACE_NAME));
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.change_name));
        setEditString(getString(R.string.save));
        ((ActEditNameBinding) this.mViewBinding).setNameTip(getString(R.string.home_name));
        ((ActEditNameBinding) this.mViewBinding).setName(this.mPlaceName);
        ((ActEditNameBinding) this.mViewBinding).etName.setHint(R.string.input_home_name);
        ((ActEditNameBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.home.ActEditHomeName$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActEditHomeName.this.lambda$initView$0((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        if (view.getId() != R.id.iv_clear) {
            return;
        }
        this.mPlaceName.setValue("");
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        if (TextUtils.isEmpty(this.mPlaceName.getValue())) {
            SmartToast.showShort(getString(R.string.input_name));
        } else {
            ((ObservableSubscribeProxy) Injection.net().updatePlaceName(this.mPlaceId, this.mPlaceName.getValue()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.home.ActEditHomeName$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActEditHomeName.this.lambda$edit$1((Disposable) obj);
                }
            }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.home.ActEditHomeName$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ActEditHomeName.this.dismissLoadingDialog();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.home.ActEditHomeName$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActEditHomeName.this.lambda$edit$2(obj);
                }
            }, new SmartErrorComsumer());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$1(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$2(Object obj) throws Exception {
        Injection.repo().home().updatePlaceName(this.mPlaceId, this.mPlaceName.getValue());
        finishActivity();
    }
}