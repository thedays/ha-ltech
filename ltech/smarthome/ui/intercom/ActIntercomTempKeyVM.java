package com.ltech.smarthome.ui.intercom;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.ResultBean;
import com.ltech.smarthome.net.response.intercom.CreateKeyResponse;
import com.ltech.smarthome.net.response.intercom.KeyListResponse;
import com.ltech.smarthome.utils.RxUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActIntercomTempKeyVM extends BaseViewModel {
    public SingleLiveEvent<Void> showCountDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showEffectiveDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showExpirationDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> clickMessageEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> clickWechatEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isCreateKeyMode = new SingleLiveEvent<>(true);
    public MutableLiveData<KeyListResponse.OpenDoorTempKey> tempKey = new MutableLiveData<>();
    public boolean isCreateKeyModeWhenInit = true;
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomTempKeyVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActIntercomTempKeyVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.iv_message /* 2131297134 */:
                this.clickMessageEvent.call();
                break;
            case R.id.iv_wechat /* 2131297319 */:
                this.clickWechatEvent.call();
                break;
            case R.id.layout_effective_time /* 2131297458 */:
                this.showEffectiveDialogEvent.call();
                break;
            case R.id.layout_expiration_time /* 2131297469 */:
                this.showExpirationDialogEvent.call();
                break;
            case R.id.layout_key_count /* 2131297508 */:
                this.showCountDialogEvent.call();
                break;
        }
    }

    public void getTempKey() {
        ((ObservableSubscribeProxy) Injection.net().getKeyList().compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomTempKeyVM$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActIntercomTempKeyVM.this.lambda$getTempKey$1((ResultBean) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getTempKey$1(ResultBean resultBean) throws Exception {
        Injection.intercom().setTempKeyList(((KeyListResponse) resultBean.getData()).getKey_list());
        searchTempKey();
    }

    public void searchTempKey() {
        List<KeyListResponse.OpenDoorTempKey> tempKeyList = Injection.intercom().getTempKeyList();
        this.isCreateKeyMode.setValue(true);
        if (tempKeyList.size() > 0) {
            for (KeyListResponse.OpenDoorTempKey openDoorTempKey : tempKeyList) {
                try {
                    if (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(openDoorTempKey.getEnd_time()).getSeconds() == 0 && openDoorTempKey.getCounts() > 0) {
                        this.tempKey.setValue(openDoorTempKey);
                        this.isCreateKeyModeWhenInit = false;
                        this.isCreateKeyMode.setValue(false);
                    }
                } catch (Exception e) {
                    this.isCreateKeyMode.setValue(true);
                    e.printStackTrace();
                }
            }
        }
    }

    public void deleteAndCreateTempKey(final String beginTime, final String endTime, final int count) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Long.valueOf(this.tempKey.getValue().getTemp_key()));
        ((ObservableSubscribeProxy) Injection.net().deleteKeyList(arrayList).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomTempKeyVM$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActIntercomTempKeyVM.this.lambda$deleteAndCreateTempKey$2((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomTempKeyVM$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActIntercomTempKeyVM.this.lambda$deleteAndCreateTempKey$3(beginTime, endTime, count, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteAndCreateTempKey$2(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteAndCreateTempKey$3(String str, String str2, int i, Object obj) throws Exception {
        this.tempKey.setValue(null);
        createNewKey(str, str2, i);
    }

    public void createNewKey(String beginTime, String endTime, int count) {
        ((ObservableSubscribeProxy) Injection.net().createOpenDoorKey(beginTime, endTime, count).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomTempKeyVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActIntercomTempKeyVM.this.lambda$createNewKey$4((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomTempKeyVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActIntercomTempKeyVM.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomTempKeyVM$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActIntercomTempKeyVM.this.lambda$createNewKey$5((CreateKeyResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createNewKey$4(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createNewKey$5(CreateKeyResponse createKeyResponse) throws Exception {
        dismissLoadingDialog();
        this.tempKey.setValue(createKeyResponse.getData());
        this.isCreateKeyMode.setValue(false);
        updateKeyList();
    }

    private void updateKeyList() {
        ((ObservableSubscribeProxy) Injection.net().getKeyList().compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomTempKeyVM$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                Injection.intercom().setTempKeyList(((KeyListResponse) ((ResultBean) obj).getData()).getKey_list());
            }
        }, new SmartErrorComsumer());
    }
}