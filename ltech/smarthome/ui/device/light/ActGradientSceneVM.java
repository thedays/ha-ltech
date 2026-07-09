package com.ltech.smarthome.ui.device.light;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.message.CtrlPackage;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.GradientScene;
import com.ltech.smarthome.model.bean.GradientSceneAction;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.group.GradientSceneResponse;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.smart.message.base.BaseCtrlPackage;
import com.smart.product_agreement.param.LightCmdParam;
import com.smart.product_agreement.productBle.CmdBle;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActGradientSceneVM extends BaseViewModel {
    public long controlId;
    public MutableLiveData<Group> controlObject = new MutableLiveData<>();
    public MutableLiveData<List<GradientScene>> data = new MutableLiveData<>();
    public boolean isLocal;
    public int selPos;

    public void loadScene() {
        ((ObservableSubscribeProxy) Injection.net().queryGroupGradientScene(this.controlObject.getValue().getGroupId()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.light.ActGradientSceneVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActGradientSceneVM.this.lambda$loadScene$0((GradientSceneResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadScene$0(GradientSceneResponse gradientSceneResponse) throws Exception {
        this.data.setValue(gradientSceneResponse.getRows());
    }

    public void apply(GradientScene data) {
        Group value = this.controlObject.getValue();
        if (data.getGroupsceneinfos() != null && value != null) {
            for (int i = 0; i < 5; i++) {
                for (GradientScene.GroupSceneInfos groupSceneInfos : data.getGroupsceneinfos()) {
                    long deviceid = groupSceneInfos.getDeviceid();
                    Iterator<Long> it = value.getDeviceIds().iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (it.next().longValue() == deviceid) {
                                sendCmd(groupSceneInfos.getUnicastAddress(), groupSceneInfos.getColor());
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
            SmartToast.showCenterShort(getContext().getString(R.string.execute_success));
            return;
        }
        SmartToast.showCenterShort(getContext().getString(R.string.execute_fail));
    }

    private void sendCmd(int address, String cmd) {
        CmdBle cmdBle = new CmdBle(cmd);
        CtrlPackage ctrlPackage = new CtrlPackage(2);
        ctrlPackage.setAddress(address);
        ctrlPackage.setControlType(2);
        Injection.message().create(getContext()).cmd(cmdBle).control((BaseCtrlPackage) ctrlPackage).intervalTime(100).sendTimes(1).enqueue();
    }

    public void delete(long gsid) {
        ((ObservableSubscribeProxy) Injection.net().delGroupGradientScene(gsid).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.light.ActGradientSceneVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActGradientSceneVM.this.lambda$delete$1(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$delete$1(Object obj) throws Exception {
        SmartToast.showCenterShort(getContext().getString(R.string.delete_success));
    }

    public void editName(String s, GradientScene data) {
        ((ObservableSubscribeProxy) Injection.net().editGroupGradientSceneName(data.getGsid(), s).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.light.ActGradientSceneVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActGradientSceneVM.this.lambda$editName$2(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$editName$2(Object obj) throws Exception {
        SmartToast.showCenterShort(getContext().getString(R.string.save_success));
    }

    public void selectGradientScene(GradientScene data) {
        Group value = this.controlObject.getValue();
        if (data.getGroupsceneinfos() == null || value == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (GradientScene.GroupSceneInfos groupSceneInfos : data.getGroupsceneinfos()) {
            long deviceid = groupSceneInfos.getDeviceid();
            Iterator<Long> it = value.getDeviceIds().iterator();
            while (true) {
                if (it.hasNext()) {
                    long longValue = it.next().longValue();
                    if (longValue == deviceid) {
                        arrayList.add(new GradientSceneAction(longValue, groupSceneInfos.getColor()));
                        break;
                    }
                }
            }
        }
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.addExtParam(SceneHelper.OPTION, "13");
        lightCmdParam.addExtParam(SceneHelper.OPTION_VALUE, ActivityUtils.getTopActivity().getString(R.string.function_open));
        lightCmdParam.addExtParam(SceneHelper.GS_Id, Long.valueOf(data.getGsid()));
        lightCmdParam.addExtParam(SceneHelper.GS_NAME, data.getGsname());
        lightCmdParam.addExtParam(SceneHelper.GS_DATA, GsonUtils.toJson(arrayList));
        SceneHelper.instance().cmdParam = lightCmdParam;
    }
}