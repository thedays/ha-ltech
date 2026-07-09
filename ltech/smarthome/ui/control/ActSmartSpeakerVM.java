package com.ltech.smarthome.ui.control;

import android.view.View;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.ui.device.sonos.ActSonosPlayControl;
import com.ltech.smarthome.ui.device.sonos.SonosResponse;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSmartSpeakerVM extends BaseViewModel {
    public Listing<Place> mPlaceListing;
    public SingleLiveEvent<Void> saveEvent = new SingleLiveEvent<>();
    public BindingCommand<View> clickCommand = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.control.ActSmartSpeakerVM$$ExternalSyntheticLambda4
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActSmartSpeakerVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        if (view.getId() != R.id.layout_save) {
            return;
        }
        this.saveEvent.call();
    }

    public void checkSonosSpeaker() {
        ((ObservableSubscribeProxy) Injection.net().getSonosHouseholds().delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.ActSmartSpeakerVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartSpeakerVM.this.lambda$checkSonosSpeaker$1((SonosResponse) obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.control.ActSmartSpeakerVM$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartSpeakerVM.this.lambda$checkSonosSpeaker$2((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkSonosSpeaker$1(SonosResponse sonosResponse) throws Exception {
        dismissLoadingDialog();
        if (sonosResponse != null && !sonosResponse.getHouseholds().isEmpty()) {
            checkGroups(sonosResponse.getHouseholds().get(0));
        } else {
            SmartToast.showShort("请检查设备是否激活状态");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkSonosSpeaker$2(Throwable th) throws Exception {
        SmartToast.showShort("授权失败");
        dismissLoadingDialog();
    }

    public void checkGroups(SonosResponse.Households households) {
        ((ObservableSubscribeProxy) Injection.net().getSonosGroups(households.getId()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.ActSmartSpeakerVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartSpeakerVM.this.lambda$checkGroups$3((SonosResponse) obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.control.ActSmartSpeakerVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartSpeakerVM.this.lambda$checkGroups$4((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkGroups$3(SonosResponse sonosResponse) throws Exception {
        dismissLoadingDialog();
        if (sonosResponse == null || sonosResponse.getHouseholds().isEmpty()) {
            return;
        }
        showGroupDialog(sonosResponse.getGroups());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkGroups$4(Throwable th) throws Exception {
        SmartToast.showShort("授权失败");
        dismissLoadingDialog();
    }

    private void showGroupDialog(final List<SonosResponse.Groups> groups) {
        if (groups == null) {
            SmartToast.showShort("没有设备可以选择");
            return;
        }
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle("选择Sonos设备").setCancelString("取消").setSelectPosition(-1);
        Iterator<SonosResponse.Groups> it = groups.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getName());
        }
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.control.ActSmartSpeakerVM$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                NavUtils.destination(ActSonosPlayControl.class).withString(Constants.GROUP_ID, ((SonosResponse.Groups) groups.get(((Integer) obj).intValue())).getId()).navigation(ActivityUtils.getTopActivity());
            }
        }).setSelectList(arrayList);
        selectPosition.setOutCancel(false);
        selectPosition.showDialog((FragmentActivity) ActivityUtils.getTopActivity());
    }

    public static class SpeakerInfo {
        public int iconRes;
        public String name;
        public Place place;
        public int type;

        public SpeakerInfo(String name, int iconRes, int type) {
            this.name = name;
            this.iconRes = iconRes;
            this.type = type;
        }
    }
}