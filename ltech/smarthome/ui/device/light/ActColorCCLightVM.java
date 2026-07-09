package com.ltech.smarthome.ui.device.light;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.DeviceState;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.W5bExtParam;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.device.light.ActAddDuv;
import com.ltech.smarthome.ui.device.microwave_sensor.WaveSensorHelper;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.LightAssistant;
import com.smart.product_agreement.param.LightCmdParam;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActColorCCLightVM extends ActColorLightVM {
    public int blue;
    public boolean changeK;
    public double duv;
    public W5bExtParam extParam;
    public int green;
    public int hValue;
    public boolean isLocalScene;
    public boolean isWaveSensorAction;
    public int lValue;
    public String productId;
    public int red;
    public int sValue;
    public String sceneInstruct;
    public boolean selectAction;
    public int w;
    public int wyBrt;
    public float xValue;
    public float yValue;
    public MutableLiveData<Boolean> refreshObject = new MutableLiveData<>(false);
    public SingleLiveEvent<Object> updateUIEvent = new SingleLiveEvent<>();
    public MutableLiveData<long[]> shortDevices = new MutableLiveData<>();

    /* renamed from: c, reason: collision with root package name */
    public int f6271c = 255;
    public int rgbBrt = 255;
    public int totalBrt = 255;
    public int totalK = 1000;
    public DeviceState deviceState = new DeviceState();
    public int selectK = 1000;
    public int bri = 255;
    public int selectColorPoint = -1;

    public void saveData(int tabIndex) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        if (!this.changeK) {
            lightCmdParam.addExtParam(SceneHelper.OPTION, "0");
            if (tabIndex == 0) {
                lightCmdParam.setCmdType(73);
                lightCmdParam.setRgbBrt(this.rgbBrt);
                lightCmdParam.setWyBrt(this.wyBrt);
                lightCmdParam.setRed(this.red);
                lightCmdParam.setGreen(this.green);
                lightCmdParam.setBlue(this.blue);
                lightCmdParam.setC(this.f6271c);
                lightCmdParam.setW(this.w);
            } else if (tabIndex == 2) {
                lightCmdParam.setCmdType(75);
                lightCmdParam.setValue1(this.selectK);
                lightCmdParam.setValue2((int) (this.duv * 10000.0d));
                lightCmdParam.setValue3(this.bri);
                if (this.selectColorPoint != -1) {
                    lightCmdParam.addExtParam(SceneHelper.OPTION, "11");
                    lightCmdParam.addExtParam(SceneHelper.OPTION_VALUE, this.extParam.getPointList(this.selectK).get(this.selectColorPoint).getName());
                }
            } else if (tabIndex == 3) {
                lightCmdParam.setCmdType(76);
                lightCmdParam.setValue1(this.hValue);
                lightCmdParam.setValue2(this.sValue);
                lightCmdParam.setValue3(this.lValue);
            } else if (tabIndex == 4) {
                lightCmdParam.setCmdType(74);
                lightCmdParam.setValue1((int) (this.xValue * 10000.0f));
                lightCmdParam.setValue2((int) (this.yValue * 10000.0f));
            } else {
                lightCmdParam.setCmdType(72);
                lightCmdParam.setTotalBrt(this.totalBrt);
                lightCmdParam.setRed(this.red);
                lightCmdParam.setGreen(this.green);
                lightCmdParam.setBlue(this.blue);
                lightCmdParam.setC(this.f6271c);
                lightCmdParam.setW(this.w);
            }
        } else {
            lightCmdParam.setCmdType(52);
            lightCmdParam.setTotalBrt(this.totalBrt);
            lightCmdParam.setTotalK(this.totalK);
            lightCmdParam.addExtParam(SceneHelper.OPTION, "0");
        }
        if (this.isWaveSensorAction) {
            WaveSensorHelper.instance().cmdParam = lightCmdParam;
            WaveSensorHelper.instance().setSensorRelateBleParam();
            if (WaveSensorHelper.instance().isChangeAll) {
                int i = this.totalBrt;
                lightCmdParam.setTotalBrt(i == 0 ? 0 : Math.max(i / 2, 1));
                int i2 = this.rgbBrt;
                lightCmdParam.setRgbBrt(i2 == 0 ? 0 : Math.max(i2 / 2, 1));
                int i3 = this.wyBrt;
                lightCmdParam.setWyBrt(i3 == 0 ? 0 : Math.max(i3 / 2, 1));
                WaveSensorHelper.instance().setDataType = 2;
                WaveSensorHelper.instance().setSensorRelateBleParam();
                WaveSensorHelper.instance().setDataType = 3;
                LightCmdParam lightCmdParam2 = new LightCmdParam();
                lightCmdParam2.setCmdType(1);
                lightCmdParam2.setOn(false);
                lightCmdParam2.addExtParam(SceneHelper.OPTION, "4");
                WaveSensorHelper.instance().cmdParam = lightCmdParam2;
                WaveSensorHelper.instance().setSensorRelateBleParam();
                WaveSensorHelper.instance().setDataType = 1;
                return;
            }
            return;
        }
        SceneHelper.instance().cmdParam = lightCmdParam;
        if (SceneHelper.instance().isMultiZone) {
            SceneHelper.instance().goSelectZone(ActivityUtils.getTopActivity(), this.isLocalScene);
        } else if (this.isLocalScene) {
            finishActivity(3001, null);
        } else {
            SceneHelper.instance().saveSelectResult(getLifecycleOwner(), new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActColorCCLightVM$$ExternalSyntheticLambda5
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActColorCCLightVM.this.lambda$saveData$0((Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveData$0(Boolean bool) {
        finishActivity(3001, null);
    }

    public void setTotalBrt(int brt) {
        this.totalBrt = brt;
        this.rgbBrt = brt;
        this.wyBrt = brt;
    }

    public Object getControlObject() {
        if (this.selectAction) {
            if (this.isWaveSensorAction) {
                return WaveSensorHelper.instance().relateObject;
            }
            return SceneHelper.instance().controlObject;
        }
        return this.controlObject.getValue();
    }

    public W5bExtParam getExtParam() {
        W5bExtParam w5bExtParam;
        Object controlObject = getControlObject();
        if (controlObject instanceof Device) {
            w5bExtParam = (W5bExtParam) ((Device) controlObject).getExtParam(W5bExtParam.class);
        } else {
            w5bExtParam = controlObject instanceof Group ? (W5bExtParam) ((Group) controlObject).getExtParam(W5bExtParam.class) : null;
        }
        this.extParam = w5bExtParam;
        return w5bExtParam == null ? new W5bExtParam() : w5bExtParam;
    }

    public void uploadData(final W5bExtParam extParam, final boolean isAdd) {
        if (this.groupControl) {
            final Group group = (Group) this.controlObject.getValue();
            ((ObservableSubscribeProxy) Injection.net().updateGroupParamExt(group.getGroupId(), GsonUtils.toJson(extParam)).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.light.ActColorCCLightVM$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActColorCCLightVM.this.lambda$uploadData$1(isAdd, (Disposable) obj);
                }
            }).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.light.ActColorCCLightVM$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ActColorCCLightVM.this.dismissLoadingDialog();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.light.ActColorCCLightVM$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActColorCCLightVM.this.lambda$uploadData$2(group, extParam, isAdd, obj);
                }
            }, new SmartErrorComsumer());
        } else {
            final Device device = (Device) this.controlObject.getValue();
            ((ObservableSubscribeProxy) Injection.net().updateParamExt(device.getDeviceId(), GsonUtils.toJson(extParam)).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.light.ActColorCCLightVM$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActColorCCLightVM.this.lambda$uploadData$3(isAdd, (Disposable) obj);
                }
            }).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.light.ActColorCCLightVM$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ActColorCCLightVM.this.dismissLoadingDialog();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.light.ActColorCCLightVM$$ExternalSyntheticLambda4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActColorCCLightVM.this.lambda$uploadData$4(device, extParam, isAdd, obj);
                }
            }, new SmartErrorComsumer());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$1(boolean z, Disposable disposable) throws Exception {
        showLoadingDialog(getContext().getString(z ? R.string.saving : R.string.deleting));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$2(Group group, W5bExtParam w5bExtParam, boolean z, Object obj) throws Exception {
        group.setExtParam(GsonUtils.toJson(w5bExtParam));
        Injection.repo().group().saveGroup(group);
        showSuccessTipDialog(getContext().getString(z ? R.string.save_success : R.string.delete_success));
        this.updateUIEvent.call();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$3(boolean z, Disposable disposable) throws Exception {
        showLoadingDialog(getContext().getString(z ? R.string.saving : R.string.deleting));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$4(Device device, W5bExtParam w5bExtParam, boolean z, Object obj) throws Exception {
        device.setExtParam(GsonUtils.toJson(w5bExtParam));
        Injection.repo().device().saveDevice(device);
        showSuccessTipDialog(getContext().getString(z ? R.string.save_success : R.string.delete_success));
        this.updateUIEvent.call();
    }

    public List<ActAddDuv.ColorPoint> getColorList(String mode) {
        ArrayList arrayList = new ArrayList();
        String substring = mode.substring(4);
        int i = 0;
        while (i < substring.length() / 8) {
            int i2 = i * 8;
            int i3 = i2 + 4;
            int parseInt = Integer.parseInt(substring.substring(i2, i3), 16);
            int parseInt2 = Integer.parseInt(substring.substring(i3, i2 + 8), 16);
            if (parseInt2 > 200) {
                parseInt2 -= 65536;
            }
            i++;
            arrayList.add(new ActAddDuv.ColorPoint(i, parseInt, parseInt2 / 10000.0d));
        }
        return arrayList;
    }

    @Override // com.ltech.smarthome.ui.device.light.ActColorLightVM
    public LightAssistant getLightCmdHelper() {
        this.changeK = false;
        return CmdAssistant.getLightCmdAssistant(getControlObject(), new int[0]);
    }

    public LightAssistant getLightCmdHelper(boolean changeK) {
        this.changeK = changeK;
        return CmdAssistant.getLightCmdAssistant(getControlObject(), new int[0]);
    }
}