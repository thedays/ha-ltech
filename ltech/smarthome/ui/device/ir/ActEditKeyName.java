package com.ltech.smarthome.ui.device.ir;

import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActEditNameBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.DiyIrParam;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActEditKeyName extends BaseNormalActivity<ActEditNameBinding> {
    private long controlId;
    private int keyPosition;
    private Device mDevice;
    private MutableLiveData<String> mKeyName = new MutableLiveData<>();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_edit_name;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.change_name));
        setEditString(getString(R.string.save));
        ((ActEditNameBinding) this.mViewBinding).setDeleteTip(getString(R.string.delete));
        ((ActEditNameBinding) this.mViewBinding).setNameTip(getString(R.string.key_name));
        ((ActEditNameBinding) this.mViewBinding).setName(this.mKeyName);
        ((ActEditNameBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.ir.ActEditKeyName$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActEditKeyName.this.lambda$initView$0((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        int id = view.getId();
        if (id == R.id.iv_clear) {
            this.mKeyName.setValue("");
        } else {
            if (id != R.id.tv_delete) {
                return;
            }
            deleteKey();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        this.controlId = getIntent().getLongExtra(Constants.CONTROL_ID, 0L);
        this.keyPosition = getIntent().getIntExtra(Constants.KEY_POSITION, -1);
        Injection.repo().device().getDeviceFromDb(this.controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.ir.ActEditKeyName$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEditKeyName.this.lambda$startObserve$1((Device) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device) {
        DiyIrParam diyIrParam;
        if (this.mDevice != null || device == null || (diyIrParam = (DiyIrParam) device.getParam(DiyIrParam.class)) == null) {
            return;
        }
        this.mDevice = device;
        this.mKeyName.setValue(diyIrParam.getDiyIrKeyList().get(this.keyPosition).getKeyName());
    }

    private void deleteKey() {
        final DiyIrParam diyIrParam = (DiyIrParam) this.mDevice.getParam(DiyIrParam.class);
        diyIrParam.deleteKey(this.keyPosition);
        ((ObservableSubscribeProxy) Injection.net().updateParam(this.mDevice.getDeviceId(), GsonUtils.toJson(diyIrParam)).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.ir.ActEditKeyName$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActEditKeyName.this.lambda$deleteKey$2((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActEditKeyName$$ExternalSyntheticLambda2(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.ir.ActEditKeyName$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActEditKeyName.this.lambda$deleteKey$3(diyIrParam, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteKey$2(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.removing));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteKey$3(DiyIrParam diyIrParam, Object obj) throws Exception {
        this.mDevice.setParam(diyIrParam);
        Injection.repo().device().saveDevice(this.mDevice);
        finishActivity();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        if (TextUtils.isEmpty(this.mKeyName.getValue())) {
            SmartToast.showShort(getString(R.string.input_name));
            return;
        }
        final DiyIrParam diyIrParam = (DiyIrParam) this.mDevice.getParam(DiyIrParam.class);
        diyIrParam.changeKeyName(this.keyPosition, this.mKeyName.getValue());
        ((ObservableSubscribeProxy) Injection.net().updateParam(this.mDevice.getDeviceId(), GsonUtils.toJson(diyIrParam)).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.ir.ActEditKeyName$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActEditKeyName.this.lambda$edit$4((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActEditKeyName$$ExternalSyntheticLambda2(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.ir.ActEditKeyName$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActEditKeyName.this.lambda$edit$5(diyIrParam, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$4(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$5(DiyIrParam diyIrParam, Object obj) throws Exception {
        this.mDevice.setParam(diyIrParam);
        Injection.repo().device().saveDevice(this.mDevice);
        SmartToast.showShort(R.string.save_success);
        finishActivity();
    }
}