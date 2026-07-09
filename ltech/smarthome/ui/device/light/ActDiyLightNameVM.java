package com.ltech.smarthome.ui.device.light;

import android.text.TextUtils;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.group.UpdateGroupResponse;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

/* loaded from: classes4.dex */
public class ActDiyLightNameVM extends BaseViewModel {
    public Object controlObject;
    public FlexboxLayoutManager flexboxLayoutManager;
    public boolean groupControl;
    public ObservableArrayList<String> recommendList;
    public ObservableField<String> lightName = new ObservableField<>();
    public long controlId = -1;
    public ItemBinding<String> itemBinding = ItemBinding.of(new OnItemBind() { // from class: com.ltech.smarthome.ui.device.light.ActDiyLightNameVM$$ExternalSyntheticLambda1
        @Override // me.tatarka.bindingcollectionadapter2.OnItemBind
        public final void onItemBind(ItemBinding itemBinding, int i, Object obj) {
            ActDiyLightNameVM.this.lambda$new$1(itemBinding, i, (String) obj);
        }
    });

    public ActDiyLightNameVM() {
        ObservableArrayList<String> observableArrayList = new ObservableArrayList<>();
        this.recommendList = observableArrayList;
        observableArrayList.add(ActivityUtils.getTopActivity().getString(R.string.light_icon_name_1));
        this.recommendList.add(ActivityUtils.getTopActivity().getString(R.string.light_icon_name_2));
        this.recommendList.add(ActivityUtils.getTopActivity().getString(R.string.light_icon_name_3));
        this.recommendList.add(ActivityUtils.getTopActivity().getString(R.string.light_icon_name_4));
        this.recommendList.add(ActivityUtils.getTopActivity().getString(R.string.light_icon_name_5));
        this.recommendList.add(ActivityUtils.getTopActivity().getString(R.string.light_icon_name_6));
        this.recommendList.add(ActivityUtils.getTopActivity().getString(R.string.light_icon_name_7));
        this.recommendList.add(ActivityUtils.getTopActivity().getString(R.string.light_icon_name_8));
        this.recommendList.add(ActivityUtils.getTopActivity().getString(R.string.light_icon_name_9));
        this.recommendList.add(ActivityUtils.getTopActivity().getString(R.string.light_icon_name_10));
        this.recommendList.add(ActivityUtils.getTopActivity().getString(R.string.light_icon_name_11));
        this.recommendList.add(ActivityUtils.getTopActivity().getString(R.string.light_icon_name_12));
        this.recommendList.add(ActivityUtils.getTopActivity().getString(R.string.light_icon_name_13));
        this.recommendList.add(ActivityUtils.getTopActivity().getString(R.string.light_icon_name_14));
        this.recommendList.add(ActivityUtils.getTopActivity().getString(R.string.light_icon_name_15));
        this.recommendList.add(ActivityUtils.getTopActivity().getString(R.string.light_icon_name_16));
        this.flexboxLayoutManager = new FlexboxLayoutManager(ActivityUtils.getTopActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(ItemBinding itemBinding, int i, final String str) {
        itemBinding.clearExtras().set(40, R.layout.item_room_name).bindExtra(10, new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.light.ActDiyLightNameVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActDiyLightNameVM.this.lambda$new$0(str);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(String str) {
        this.lightName.set(str);
    }

    public void changeLightName() {
        final String str = this.lightName.get();
        if (TextUtils.isEmpty(str)) {
            SmartToast.showShort(R.string.input_name);
        } else if (!this.groupControl) {
            final Device device = (Device) this.controlObject;
            ((ObservableSubscribeProxy) Injection.net().updateDeviceName(device.getDeviceId(), str).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.light.ActDiyLightNameVM$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActDiyLightNameVM.this.lambda$changeLightName$2((Disposable) obj);
                }
            }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.light.ActDiyLightNameVM$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ActDiyLightNameVM.this.dismissLoadingDialog();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.light.ActDiyLightNameVM$$ExternalSyntheticLambda4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActDiyLightNameVM.this.lambda$changeLightName$3(device, str, obj);
                }
            }, new SmartErrorComsumer());
        } else {
            final Group group = (Group) this.controlObject;
            ((ObservableSubscribeProxy) Injection.net().updateGroupName(group.getGroupId(), str).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.light.ActDiyLightNameVM$$ExternalSyntheticLambda5
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActDiyLightNameVM.this.lambda$changeLightName$4((Disposable) obj);
                }
            }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.light.ActDiyLightNameVM$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ActDiyLightNameVM.this.dismissLoadingDialog();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.light.ActDiyLightNameVM$$ExternalSyntheticLambda6
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActDiyLightNameVM.this.lambda$changeLightName$5(group, str, (UpdateGroupResponse) obj);
                }
            }, new SmartErrorComsumer());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeLightName$2(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeLightName$3(Device device, String str, Object obj) throws Exception {
        device.setDeviceName(str);
        Injection.repo().device().saveDevice(device);
        SmartToast.showShort(R.string.save_success);
        finishActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeLightName$4(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeLightName$5(Group group, String str, UpdateGroupResponse updateGroupResponse) throws Exception {
        group.setGroupName(str);
        Injection.repo().group().saveGroup(group);
        SmartToast.showShort(R.string.save_success);
        finishActivity();
    }
}