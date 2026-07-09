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
import com.ltech.smarthome.net.response.intercom.CreateKeyResponse;
import com.ltech.smarthome.net.response.intercom.GetBleCodeResponse;
import com.ltech.smarthome.net.response.intercom.KeyListResponse;
import com.ltech.smarthome.net.response.intercom.UserInfoResponse;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/* loaded from: classes4.dex */
public class ActIntercomVM extends BaseViewModel {
    public String bleCode;
    private List<Long> needDeleteKeyList;
    public long placeId;
    public SingleLiveEvent<KeyListResponse.OpenDoorTempKey> loadQrcodeEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<List<UserInfoResponse.DevInfo>> ftDevInfoListEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> foldQrcodeEvent = new SingleLiveEvent<>(false);
    public SingleLiveEvent<String> bleOpenDoorEvent = new SingleLiveEvent<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomVM$$ExternalSyntheticLambda2
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActIntercomVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.intercom_qrcode /* 2131296898 */:
                refreshQrcode();
                break;
            case R.id.iv_qrcode_go /* 2131297198 */:
            case R.id.tv_qrcode /* 2131298898 */:
                this.foldQrcodeEvent.setValue(Boolean.valueOf(Boolean.FALSE.equals(this.foldQrcodeEvent.getValue())));
                break;
            case R.id.layout_bluetooth /* 2131297372 */:
                getBleCodeAndOpen();
                break;
            case R.id.layout_record /* 2131297590 */:
                NavUtils.destination(ActIntercomRecord.class).navigation(ActivityUtils.getTopActivity());
                break;
            case R.id.layout_temp_key /* 2131297677 */:
                NavUtils.destination(ActIntercomTempKey.class).navigation(ActivityUtils.getTopActivity());
                break;
        }
    }

    private void getBleCodeAndOpen() {
        ((ObservableSubscribeProxy) Injection.net().getBleCode().compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomVM$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActIntercomVM.this.lambda$getBleCodeAndOpen$2((GetBleCodeResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getBleCodeAndOpen$2(GetBleCodeResponse getBleCodeResponse) throws Exception {
        if (getBleCodeResponse.getEnable() == 1) {
            String blecode = getBleCodeResponse.getBlecode();
            this.bleCode = blecode;
            this.bleOpenDoorEvent.setValue(blecode);
            return;
        }
        ((ObservableSubscribeProxy) Injection.net().setBleCode().compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomVM$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActIntercomVM.this.lambda$getBleCodeAndOpen$1((GetBleCodeResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getBleCodeAndOpen$1(GetBleCodeResponse getBleCodeResponse) throws Exception {
        String blecode = getBleCodeResponse.getBlecode();
        this.bleCode = blecode;
        this.bleOpenDoorEvent.setValue(blecode);
    }

    private void refreshQrcode() {
        if (this.loadQrcodeEvent.getValue() != null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(Long.valueOf(this.loadQrcodeEvent.getValue().getTemp_key()));
            ((ObservableSubscribeProxy) Injection.net().deleteKeyList(arrayList).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomVM$$ExternalSyntheticLambda7
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActIntercomVM.this.lambda$refreshQrcode$3(obj);
                }
            }, new SmartErrorComsumer());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshQrcode$3(Object obj) throws Exception {
        Injection.intercom().deleteTempKey(this.loadQrcodeEvent.getValue().getTemp_key());
        createNewKey();
    }

    public void getQrcodeData() {
        ((ObservableSubscribeProxy) Injection.net().getKeyList().compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActIntercomVM.this.lambda$getQrcodeData$4((ResultBean) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getQrcodeData$4(ResultBean resultBean) throws Exception {
        if (resultBean.getRet() == 0) {
            if (isNeedDeleteKey(((KeyListResponse) resultBean.getData()).getKey_list())) {
                deleteKeyList();
                return;
            } else {
                Injection.intercom().setTempKeyList(((KeyListResponse) resultBean.getData()).getKey_list());
                searchQrCode(((KeyListResponse) resultBean.getData()).getKey_list());
                return;
            }
        }
        createNewKey();
    }

    private void searchQrCode(List<KeyListResponse.OpenDoorTempKey> keyList) {
        for (KeyListResponse.OpenDoorTempKey openDoorTempKey : keyList) {
            try {
                if (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(openDoorTempKey.getEnd_time()).getSeconds() == 59) {
                    this.loadQrcodeEvent.setValue(openDoorTempKey);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteKeyList() {
        ((ObservableSubscribeProxy) Injection.net().deleteKeyList(this.needDeleteKeyList).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomVM$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActIntercomVM.this.lambda$deleteKeyList$5(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteKeyList$5(Object obj) throws Exception {
        for (int i = 0; i < this.needDeleteKeyList.size(); i++) {
            Injection.intercom().deleteTempKey(this.needDeleteKeyList.get(i).longValue());
        }
        createNewKey();
    }

    private void createNewKey() {
        ((ObservableSubscribeProxy) Injection.net().createOpenDoorKey(getBeginTime(), getEndTime(), 100).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActIntercomVM.this.lambda$createNewKey$6((CreateKeyResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createNewKey$6(CreateKeyResponse createKeyResponse) throws Exception {
        Injection.intercom().addTempKey(createKeyResponse.getData());
        this.loadQrcodeEvent.setValue(createKeyResponse.getData());
    }

    private String getBeginTime() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }

    private String getEndTime() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }

    private boolean isNeedDeleteKey(List<KeyListResponse.OpenDoorTempKey> keyList) {
        Date date;
        this.needDeleteKeyList = new ArrayList();
        Date date2 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (KeyListResponse.OpenDoorTempKey openDoorTempKey : keyList) {
            try {
                date = simpleDateFormat.parse(openDoorTempKey.getEnd_time());
            } catch (Exception e) {
                e.printStackTrace();
                date = null;
            }
            if (date2.after(date)) {
                this.needDeleteKeyList.add(Long.valueOf(openDoorTempKey.getTemp_key()));
            }
        }
        return this.needDeleteKeyList.size() > 0;
    }

    public void initAccessControlFtList() {
        ((ObservableSubscribeProxy) Injection.net().getIntercomUserInfo().compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomVM$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActIntercomVM.this.lambda$initAccessControlFtList$7((UserInfoResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAccessControlFtList$7(UserInfoResponse userInfoResponse) throws Exception {
        ArrayList arrayList = new ArrayList();
        for (UserInfoResponse.DevInfo devInfo : userInfoResponse.getDev_list()) {
            if (!devInfo.getMac().startsWith("DC")) {
                arrayList.add(devInfo);
            }
        }
        Injection.intercom().setIntercomDevList(arrayList);
        this.ftDevInfoListEvent.setValue(arrayList);
    }
}