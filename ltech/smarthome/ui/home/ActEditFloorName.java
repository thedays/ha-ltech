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
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActEditFloorName extends BaseNormalActivity<ActEditNameBinding> {
    private long mFloorId;
    private MutableLiveData<String> mFloorName = new MutableLiveData<>();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_edit_name;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        this.mFloorId = getIntent().getLongExtra(Constants.FLOOR_ID, 0L);
        this.mFloorName.setValue(getIntent().getStringExtra(Constants.FLOOR_NAME));
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.edit_floor));
        setEditString(getString(R.string.save));
        ((ActEditNameBinding) this.mViewBinding).setDeleteTip(getString(R.string.delete_floor));
        ((ActEditNameBinding) this.mViewBinding).setNameTip(getString(R.string.floor_name));
        ((ActEditNameBinding) this.mViewBinding).setName(this.mFloorName);
        ((ActEditNameBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.home.ActEditFloorName$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActEditFloorName.this.lambda$initView$1((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(View view) {
        int id = view.getId();
        if (id == R.id.iv_clear) {
            this.mFloorName.setValue("");
        } else {
            if (id != R.id.tv_delete) {
                return;
            }
            MessageDialog.show(this, getString(R.string.app_delete_floor_str), getString(R.string.app_delete_floor_tip)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.home.ActEditFloorName$$ExternalSyntheticLambda3
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view2) {
                    boolean lambda$initView$0;
                    lambda$initView$0 = ActEditFloorName.this.lambda$initView$0(baseDialog, view2);
                    return lambda$initView$0;
                }
            }).setCancelButton(getString(R.string.cancel));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initView$0(BaseDialog baseDialog, View view) {
        deleteFloor();
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        if (TextUtils.isEmpty(this.mFloorName.getValue())) {
            SmartToast.showShort(getString(R.string.input_name));
        } else {
            ((ObservableSubscribeProxy) Injection.net().updateFloor(this.mFloorId, this.mFloorName.getValue()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.home.ActEditFloorName$$ExternalSyntheticLambda4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActEditFloorName.this.lambda$edit$2((Disposable) obj);
                }
            }).compose(RxUtils.io_main()).doFinally(new ActEditFloorName$$ExternalSyntheticLambda1(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.home.ActEditFloorName$$ExternalSyntheticLambda5
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActEditFloorName.this.lambda$edit$3(obj);
                }
            }, new SmartErrorComsumer());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$2(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$3(Object obj) throws Exception {
        Injection.repo().home().updateFloorName(this.mFloorId, this.mFloorName.getValue());
        finishActivity();
    }

    private void deleteFloor() {
        ((ObservableSubscribeProxy) Injection.net().deleteFloor(this.mFloorId).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.home.ActEditFloorName$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActEditFloorName.this.lambda$deleteFloor$4((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActEditFloorName$$ExternalSyntheticLambda1(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.home.ActEditFloorName$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActEditFloorName.this.lambda$deleteFloor$5(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteFloor$4(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.removing));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteFloor$5(Object obj) throws Exception {
        Injection.repo().home().removeFloor(this.mFloorId);
        finishActivity();
    }
}