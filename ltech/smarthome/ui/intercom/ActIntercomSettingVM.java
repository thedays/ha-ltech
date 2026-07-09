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
import com.ltech.smarthome.net.response.intercom.GetFaceResponse;
import com.ltech.smarthome.net.response.intercom.GetPrivateKeyResponse;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActIntercomSettingVM extends BaseViewModel {
    public int faceStatus;
    public String key;
    public long placeId;
    public SingleLiveEvent<Boolean> isExistKey = new SingleLiveEvent<>(false);
    public SingleLiveEvent<Boolean> isExistFace = new SingleLiveEvent<>(false);
    public SingleLiveEvent<Boolean> isExistContact = new SingleLiveEvent<>();
    public SingleLiveEvent<Integer> faceStatusEvent = new SingleLiveEvent<>(0);
    public SingleLiveEvent<String> keyText = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> clickFaceLayoutEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> uploadSuccessEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> startRecordEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> deleteFaceInfoEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> writeAddressEvent = new SingleLiveEvent<>();
    public BindingCommand<View> commonClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomSettingVM$$ExternalSyntheticLambda3
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActIntercomSettingVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.layout_face /* 2131297471 */:
                this.clickFaceLayoutEvent.call();
                break;
            case R.id.layout_unlock /* 2131297686 */:
                NavUtils.destination(ActIntercomSetOpenKey.class).withString(Constants.INTERCOM_KEY, this.key).withLong(Constants.PLACE_ID, this.placeId).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
                break;
            case R.id.layout_write_address /* 2131297697 */:
                this.writeAddressEvent.call();
                break;
            case R.id.tv_delete_face /* 2131298577 */:
                this.deleteFaceInfoEvent.call();
                break;
            case R.id.tv_start_record /* 2131298989 */:
                this.startRecordEvent.call();
                break;
        }
    }

    public void deleteFaceInfo() {
        ((ObservableSubscribeProxy) Injection.net().deleteFace().compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomSettingVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActIntercomSettingVM.this.lambda$deleteFaceInfo$1(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteFaceInfo$1(Object obj) throws Exception {
        SmartToast.showCenterShort(ActivityUtils.getTopActivity().getString(R.string.delete_success));
        this.isExistFace.setValue(false);
    }

    public void getData() {
        ((ObservableSubscribeProxy) Injection.net().getPrivateKey(this.placeId).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomSettingVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActIntercomSettingVM.this.lambda$getData$2((GetPrivateKeyResponse) obj);
            }
        }, new SmartErrorComsumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomSettingVM.1
            @Override // com.ltech.smarthome.net.SmartErrorComsumer, io.reactivex.functions.Consumer
            public void accept(Throwable throwable) {
                ActIntercomSettingVM.this.keyText.setValue(ActivityUtils.getTopActivity().getString(R.string.super_key_no));
            }
        });
        ((ObservableSubscribeProxy) Injection.net().getFaceStatus().compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomSettingVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActIntercomSettingVM.this.lambda$getData$3((GetFaceResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getData$2(GetPrivateKeyResponse getPrivateKeyResponse) throws Exception {
        String key = getPrivateKeyResponse.getKey();
        this.key = key;
        this.keyText.setValue(key);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getData$3(GetFaceResponse getFaceResponse) throws Exception {
        int parseInt = Integer.parseInt(getFaceResponse.getStatus());
        this.faceStatus = parseInt;
        this.faceStatusEvent.setValue(Integer.valueOf(parseInt));
    }
}