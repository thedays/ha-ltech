package com.ltech.smarthome.ui.device.setting;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.rs8.Rs8CodeLibResponse;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActRs8SubDeviceSettingVM extends BaseDeviceSetViewModel {
    private Rs8CodeLibResponse.CodeLib curLib;
    private ArrayList<Rs8CodeLibResponse.CodeLib.Action> defaultActions;
    public Device device;
    public List<Rs8CodeLibResponse.CodeLib.Action> moreActions;
    private ArrayList<Rs8CodeLibResponse.CodeLib.Action> settingActions;
    public SingleLiveEvent<Void> showActionsDialogEvent = new SingleLiveEvent<>();
    public MutableLiveData<Boolean> showMore = new MutableLiveData<>(false);
    public MutableLiveData<List<Rs8CodeLibResponse.CodeLib.Action>> moreActionEvent = new MutableLiveData<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.setting.ActRs8SubDeviceSettingVM$$ExternalSyntheticLambda3
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActRs8SubDeviceSettingVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.layout_change_icon /* 2131297387 */:
                this.showSelectDeviceIconDialogEvent.call();
                break;
            case R.id.layout_change_room /* 2131297392 */:
                this.showEditRoomDialogEvent.call();
                break;
            case R.id.layout_device_name /* 2131297433 */:
                this.showEditNameDialogEvent.call();
                break;
            case R.id.layout_more /* 2131297542 */:
                this.showActionsDialogEvent.call();
                break;
            case R.id.tv_delete_device /* 2131298576 */:
                this.showDeleteDialogEvent.call();
                break;
        }
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel
    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    public void loadCodeLib() {
        Device device = this.device;
        if (device == null) {
            finishActivity();
            return;
        }
        Rs8CodeLibResponse.CodeLib codeLib = (Rs8CodeLibResponse.CodeLib) device.getExtParam(Rs8CodeLibResponse.CodeLib.class);
        this.curLib = codeLib;
        if (codeLib != null) {
            final List<Rs8CodeLibResponse.CodeLib.Action> actionlist = codeLib.getActionlist();
            ((ObservableSubscribeProxy) Injection.net().queryRs8CodeLibByLibId(this.curLib.getCodelibid(), this.curLib.getBrandid(), this.curLib.getAddress()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.setting.ActRs8SubDeviceSettingVM$$ExternalSyntheticLambda4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActRs8SubDeviceSettingVM.this.lambda$loadCodeLib$1((Disposable) obj);
                }
            }).compose(RxUtils.io_main()).doFinally(new ActRs8SubDeviceSettingVM$$ExternalSyntheticLambda1(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<Rs8CodeLibResponse>() { // from class: com.ltech.smarthome.ui.device.setting.ActRs8SubDeviceSettingVM.1
                @Override // io.reactivex.functions.Consumer
                public void accept(Rs8CodeLibResponse response) throws Exception {
                    if (response == null || response.getRows() == null || response.getRows().isEmpty()) {
                        return;
                    }
                    Rs8CodeLibResponse.CodeLib codeLib2 = response.getRows().get(0);
                    ActRs8SubDeviceSettingVM.this.moreActions = new ArrayList();
                    ActRs8SubDeviceSettingVM.this.defaultActions = new ArrayList();
                    ActRs8SubDeviceSettingVM.this.settingActions = new ArrayList();
                    for (Rs8CodeLibResponse.CodeLib.Action action : codeLib2.getActionlist()) {
                        if (action.getPosition() == 0) {
                            Iterator it = actionlist.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                if (action.getActionid() == ((Rs8CodeLibResponse.CodeLib.Action) it.next()).getActionid()) {
                                    action.setSel(true);
                                    break;
                                }
                            }
                            ActRs8SubDeviceSettingVM.this.moreActions.add(action);
                        } else if (action.getPosition() == 1) {
                            ActRs8SubDeviceSettingVM.this.defaultActions.add(action);
                        } else if (action.getPosition() == 2) {
                            ActRs8SubDeviceSettingVM.this.settingActions.add(action);
                        }
                    }
                    ActRs8SubDeviceSettingVM.this.showMore.setValue(Boolean.valueOf(!ActRs8SubDeviceSettingVM.this.moreActions.isEmpty()));
                    ActRs8SubDeviceSettingVM.this.moreActionEvent.setValue(ActRs8SubDeviceSettingVM.this.settingActions);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadCodeLib$1(Disposable disposable) throws Exception {
        showLoadingDialog(getContext().getString(R.string.loading));
    }

    public void save(List<Integer> selectPositions) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.moreActions.size(); i++) {
            Rs8CodeLibResponse.CodeLib.Action action = this.moreActions.get(i);
            if (selectPositions.contains(Integer.valueOf(i))) {
                action.setSel(true);
                arrayList.add(action);
            } else {
                action.setSel(false);
            }
        }
        arrayList.addAll(0, this.defaultActions);
        this.curLib.setActionlist(arrayList);
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(this.device.getDeviceId(), GsonUtils.toJson(this.curLib)).delaySubscription(200L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.setting.ActRs8SubDeviceSettingVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActRs8SubDeviceSettingVM.this.lambda$save$2((Disposable) obj);
            }
        }).observeOn(Schedulers.single()).compose(RxUtils.io_main()).doFinally(new ActRs8SubDeviceSettingVM$$ExternalSyntheticLambda1(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.setting.ActRs8SubDeviceSettingVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActRs8SubDeviceSettingVM.this.lambda$save$3(obj);
            }
        }, new SmartErrorComsumer() { // from class: com.ltech.smarthome.ui.device.setting.ActRs8SubDeviceSettingVM.2
            @Override // com.ltech.smarthome.net.SmartErrorComsumer
            protected void action(Throwable throwable) {
                ActRs8SubDeviceSettingVM.this.dismissLoadingDialog();
                ActRs8SubDeviceSettingVM actRs8SubDeviceSettingVM = ActRs8SubDeviceSettingVM.this;
                actRs8SubDeviceSettingVM.showErrorTipDialog(actRs8SubDeviceSettingVM.getContext().getString(R.string.save_fail));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$save$2(Disposable disposable) throws Exception {
        showLoadingDialog(getContext().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$save$3(Object obj) throws Exception {
        this.device.setExtParam(this.curLib);
        Injection.repo().device().saveDevice(this.device);
    }

    public void send(String instruct) {
        CmdAssistant.getDeviceAssistant(this.device, new int[0]).runRs485Data(getContext(), instruct, 1, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.device.setting.ActRs8SubDeviceSettingVM.3
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    ActRs8SubDeviceSettingVM actRs8SubDeviceSettingVM = ActRs8SubDeviceSettingVM.this;
                    actRs8SubDeviceSettingVM.showSuccessTipDialog(actRs8SubDeviceSettingVM.getContext().getString(R.string.send_to_device_success));
                }
            }
        });
    }
}