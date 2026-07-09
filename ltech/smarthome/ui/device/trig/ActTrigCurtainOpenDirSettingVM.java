package com.ltech.smarthome.ui.device.trig;

import androidx.lifecycle.Lifecycle;
import anetwork.channel.util.RequestConstant;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.device_param.TrigExtParam;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/* loaded from: classes4.dex */
public class ActTrigCurtainOpenDirSettingVM extends BaseViewModel {
    public long controlId;
    public long deviceId;
    TrigExtParam extParam;
    public long placeId;
    public String productId;
    public boolean samePlace;

    public void generateExtParam(Device device, int curtainDir) {
        this.extParam = new TrigExtParam();
        if (device.getExtParam() != null) {
            this.extParam.fillMapWithString(device.getExtParam());
        }
        this.extParam.getInfoMap().put(TrigExtParam.CURTAIN_TYPE, Integer.valueOf(curtainDir));
    }

    public void generateExtParam(Group group, int curtainDir) {
        this.extParam = new TrigExtParam();
        if (group.getExtParam() != null) {
            this.extParam.fillMapWithString(group.getExtParam());
        }
        this.extParam.getInfoMap().put(TrigExtParam.CURTAIN_TYPE, Integer.valueOf(curtainDir));
    }

    public void updateExtParam(final Device device, int curtainDir) {
        generateExtParam(device, curtainDir);
        LHomeLog.i(RequestConstant.ENV_TEST, getClass(), this.extParam.getParamMapString());
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(device.getDeviceId(), this.extParam.getParamMapString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigCurtainOpenDirSettingVM$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActTrigCurtainOpenDirSettingVM.this.lambda$updateExtParam$0((Disposable) obj);
            }
        }).doFinally(new ActTrigCurtainOpenDirSettingVM$$ExternalSyntheticLambda1(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigCurtainOpenDirSettingVM$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActTrigCurtainOpenDirSettingVM.this.lambda$updateExtParam$1(device, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateExtParam$0(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateExtParam$1(Device device, Object obj) throws Exception {
        device.setExtParam(this.extParam.getParamMapString());
        Injection.repo().device().saveDevice(device);
        SmartToast.showShort(R.string.save_success);
    }

    public void updateExtParam(final Group group, int curtainDir) {
        generateExtParam(group, curtainDir);
        LHomeLog.i(RequestConstant.ENV_TEST, getClass(), this.extParam.getParamMapString());
        ((ObservableSubscribeProxy) Injection.net().updateGroupParamExt(group.getGroupId(), this.extParam.getParamMapString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigCurtainOpenDirSettingVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActTrigCurtainOpenDirSettingVM.this.lambda$updateExtParam$2((Disposable) obj);
            }
        }).doFinally(new ActTrigCurtainOpenDirSettingVM$$ExternalSyntheticLambda1(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigCurtainOpenDirSettingVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActTrigCurtainOpenDirSettingVM.this.lambda$updateExtParam$3(group, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateExtParam$2(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateExtParam$3(Group group, Object obj) throws Exception {
        group.setExtParam(this.extParam.getParamMapString());
        Injection.repo().group().saveGroup(group);
        SmartToast.showShort(R.string.save_success);
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }
}