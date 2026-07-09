package com.ltech.smarthome.ui.intercom;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.intercom.GetCallLogResponse;
import com.ltech.smarthome.net.response.intercom.GetOpenDoorLogResponse;
import com.ltech.smarthome.ui.item.CallLogItem;
import com.ltech.smarthome.ui.item.OpenDoorLogItem;
import com.ltech.smarthome.ui.newselect.BaseRoomPageVM;
import com.ltech.smarthome.utils.DateUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.xiaomi.mipush.sdk.Constants;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/* loaded from: classes4.dex */
public class FtLogVM extends BaseRoomPageVM {
    public static int POSITION_TYPE_END = 3;
    public static int POSITION_TYPE_FIRST = 1;
    public static int POSITION_TYPE_MIDDLE = 2;
    public MutableLiveData<List<CallLogItem>> callRefreshData = new MutableLiveData<>();
    public MutableLiveData<List<OpenDoorLogItem>> openDoorRefreshData = new MutableLiveData<>();

    public void getCallData() {
        ((ObservableSubscribeProxy) Injection.net().getCallLog().doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.FtLogVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtLogVM.this.lambda$getCallData$0((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.intercom.FtLogVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Action
            public final void run() {
                FtLogVM.this.lambda$getCallData$1();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.FtLogVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtLogVM.this.lambda$getCallData$2((GetCallLogResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getCallData$0(Disposable disposable) throws Exception {
        showLoadingDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getCallData$1() throws Exception {
        dismissLoadingDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getCallData$2(GetCallLogResponse getCallLogResponse) throws Exception {
        callLoadData(getCallLogResponse.getData());
    }

    private void callLoadData(List<GetCallLogResponse.LogBean> data) {
        String trim;
        ArrayList arrayList = new ArrayList();
        if (data != null && data.size() > 0) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (GetCallLogResponse.LogBean logBean : data) {
                if (logBean.getTime().size() == 1) {
                    trim = "today";
                } else {
                    trim = logBean.getTime().get(0).trim();
                }
                List list = (List) linkedHashMap.get(trim);
                if (list == null) {
                    list = new ArrayList();
                    linkedHashMap.put(trim, list);
                }
                list.add(logBean);
            }
            for (List list2 : new ArrayList(linkedHashMap.values())) {
                for (int i = 0; i < list2.size(); i++) {
                    if (i == 0) {
                        if (((GetCallLogResponse.LogBean) list2.get(i)).getTime().size() == 1) {
                            arrayList.add(new CallLogItem(0, DateUtils.getFormatToday("yyyy-MM-dd")));
                            arrayList.add(new CallLogItem(1, (GetCallLogResponse.LogBean) list2.get(i), POSITION_TYPE_FIRST));
                        } else if (((GetCallLogResponse.LogBean) list2.get(i)).getTime().size() > 1) {
                            if (((GetCallLogResponse.LogBean) list2.get(i)).getTime().get(0).length() > 6) {
                                arrayList.add(new CallLogItem(0, ((GetCallLogResponse.LogBean) list2.get(i)).getTime().get(0)));
                                arrayList.add(new CallLogItem(1, (GetCallLogResponse.LogBean) list2.get(i), POSITION_TYPE_FIRST));
                            } else {
                                arrayList.add(new CallLogItem(0, new SimpleDateFormat("yyyy").format(new Date()) + Constants.ACCEPT_TIME_SEPARATOR_SERVER + ((GetCallLogResponse.LogBean) list2.get(i)).getTime().get(0)));
                                arrayList.add(new CallLogItem(1, (GetCallLogResponse.LogBean) list2.get(i), POSITION_TYPE_FIRST));
                            }
                        }
                    } else if (i == list2.size() - 1) {
                        arrayList.add(new CallLogItem(1, (GetCallLogResponse.LogBean) list2.get(i), POSITION_TYPE_END));
                        arrayList.add(new CallLogItem(2));
                    } else {
                        arrayList.add(new CallLogItem(1, (GetCallLogResponse.LogBean) list2.get(i), POSITION_TYPE_MIDDLE));
                    }
                }
            }
        }
        this.callRefreshData.setValue(arrayList);
    }

    public void getOpenDoorData() {
        ((ObservableSubscribeProxy) Injection.net().getOpenDoorLog().doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.FtLogVM$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtLogVM.this.lambda$getOpenDoorData$3((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.intercom.FtLogVM$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Action
            public final void run() {
                FtLogVM.this.lambda$getOpenDoorData$4();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.FtLogVM$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtLogVM.this.lambda$getOpenDoorData$5((GetOpenDoorLogResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getOpenDoorData$3(Disposable disposable) throws Exception {
        showLoadingDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getOpenDoorData$4() throws Exception {
        dismissLoadingDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getOpenDoorData$5(GetOpenDoorLogResponse getOpenDoorLogResponse) throws Exception {
        Injection.intercom().setOpenDoorList(getOpenDoorLogResponse.getData());
        openDoorLoadData(getOpenDoorLogResponse.getData());
    }

    private void openDoorLoadData(List<GetOpenDoorLogResponse.OpenDoorBean> data) {
        ArrayList arrayList = new ArrayList();
        if (data != null && data.size() > 0) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (GetOpenDoorLogResponse.OpenDoorBean openDoorBean : data) {
                try {
                    String format = simpleDateFormat.format(simpleDateFormat.parse(openDoorBean.getCaptureTime()));
                    List list = (List) linkedHashMap.get(format);
                    if (list == null) {
                        list = new ArrayList();
                        linkedHashMap.put(format, list);
                    }
                    list.add(openDoorBean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (List list2 : new ArrayList(linkedHashMap.values())) {
                int i = 0;
                for (int i2 = 0; i2 < list2.size(); i2++) {
                    if (Integer.parseInt(((GetOpenDoorLogResponse.OpenDoorBean) list2.get(i2)).getCaptureType()) == 103) {
                        if (i2 == i) {
                            i++;
                        }
                        if (i2 == list2.size() - 1) {
                            ((OpenDoorLogItem) arrayList.get(arrayList.size() - 1)).setPositionType(POSITION_TYPE_END);
                            arrayList.add(new OpenDoorLogItem(2));
                        }
                    } else if (i2 == i) {
                        arrayList.add(new OpenDoorLogItem(0, ((GetOpenDoorLogResponse.OpenDoorBean) list2.get(i2)).getCaptureTime().split(" ")[0]));
                        arrayList.add(new OpenDoorLogItem(1, (GetOpenDoorLogResponse.OpenDoorBean) list2.get(i2), POSITION_TYPE_FIRST));
                    } else if (i2 == list2.size() - 1) {
                        arrayList.add(new OpenDoorLogItem(1, (GetOpenDoorLogResponse.OpenDoorBean) list2.get(i2), POSITION_TYPE_END));
                        arrayList.add(new OpenDoorLogItem(2));
                    } else {
                        arrayList.add(new OpenDoorLogItem(1, (GetOpenDoorLogResponse.OpenDoorBean) list2.get(i2), POSITION_TYPE_MIDDLE));
                    }
                }
            }
        }
        this.openDoorRefreshData.setValue(arrayList);
    }
}