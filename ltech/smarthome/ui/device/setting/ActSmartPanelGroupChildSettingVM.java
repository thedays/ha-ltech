package com.ltech.smarthome.ui.device.setting;

import android.view.View;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.device.setting.ActSmartPanelGroupChildSettingVM;
import com.ltech.smarthome.ui.group.BaseGroupSettingVM;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.SelectDeviceIconDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSmartPanelGroupChildSettingVM extends BaseGroupSettingVM {
    public MutableLiveData<Boolean> isAddToRoom = new MutableLiveData<>();
    public boolean isFirst = true;
    public BindingCommand<View> commonClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelGroupChildSettingVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActSmartPanelGroupChildSettingVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        int id = view.getId();
        if (id == R.id.layout_change_icon) {
            showSelectDeviceIconDialog();
        } else if (id == R.id.layout_change_room) {
            showEditRoomDialog();
        } else {
            if (id != R.id.layout_group_name) {
                return;
            }
            showEditNameDialog();
        }
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    public void changeHideGroup(final boolean isChecked) {
        final Group group = this.controlGroup;
        if (group != null) {
            ((ObservableSubscribeProxy) Injection.net().updateSubGroupHide(group.getGroupId(), isChecked).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelGroupChildSettingVM$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActSmartPanelGroupChildSettingVM.this.lambda$changeHideGroup$1((Disposable) obj);
                }
            }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelGroupChildSettingVM$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ActSmartPanelGroupChildSettingVM.this.dismissLoadingDialog();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelGroupChildSettingVM$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActSmartPanelGroupChildSettingVM.lambda$changeHideGroup$2(Group.this, isChecked, obj);
                }
            }, new SmartErrorComsumer());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeHideGroup$1(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    static /* synthetic */ void lambda$changeHideGroup$2(Group group, boolean z, Object obj) throws Exception {
        group.setSubhide(!z ? 1 : 0);
        Injection.repo().group().saveGroup(group);
        SmartToast.showShort(R.string.save_success);
    }

    private void showSelectDeviceIconDialog() {
        SelectDeviceIconDialog.asDefault().setGroupTag(true).imageIndex(this.controlGroup.getImgindex()).setOnSaveListener(new AnonymousClass1()).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
    }

    /* renamed from: com.ltech.smarthome.ui.device.setting.ActSmartPanelGroupChildSettingVM$1, reason: invalid class name */
    class AnonymousClass1 implements SelectDeviceIconDialog.OnSaveListener {
        @Override // com.ltech.smarthome.view.dialog.SelectDeviceIconDialog.OnSaveListener
        public void cancel() {
        }

        AnonymousClass1() {
        }

        @Override // com.ltech.smarthome.view.dialog.SelectDeviceIconDialog.OnSaveListener
        public boolean onSave(final int selectPos) {
            ((ObservableSubscribeProxy) Injection.net().updateGroupDeviceIcon(ActSmartPanelGroupChildSettingVM.this.controlGroup.getGroupId(), selectPos).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelGroupChildSettingVM$1$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActSmartPanelGroupChildSettingVM.AnonymousClass1.this.lambda$onSave$0((Disposable) obj);
                }
            }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelGroupChildSettingVM$1$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ActSmartPanelGroupChildSettingVM.AnonymousClass1.this.lambda$onSave$1();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(ActSmartPanelGroupChildSettingVM.this.getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelGroupChildSettingVM$1$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActSmartPanelGroupChildSettingVM.AnonymousClass1.this.lambda$onSave$2(selectPos, obj);
                }
            }, new SmartErrorComsumer());
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSave$0(Disposable disposable) throws Exception {
            ActSmartPanelGroupChildSettingVM actSmartPanelGroupChildSettingVM = ActSmartPanelGroupChildSettingVM.this;
            actSmartPanelGroupChildSettingVM.showLoadingDialog(actSmartPanelGroupChildSettingVM.getContext().getString(R.string.saving));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSave$1() throws Exception {
            ActSmartPanelGroupChildSettingVM.this.dismissLoadingDialog();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSave$2(int i, Object obj) throws Exception {
            SmartToast.showShort(ActSmartPanelGroupChildSettingVM.this.getContext().getString(R.string.save_success));
            ActSmartPanelGroupChildSettingVM.this.controlGroup.setImgindex(i);
            Injection.repo().group().saveGroup(ActSmartPanelGroupChildSettingVM.this.controlGroup);
        }
    }
}