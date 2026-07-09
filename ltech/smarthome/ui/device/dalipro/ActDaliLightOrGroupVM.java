package com.ltech.smarthome.ui.device.dalipro;

import android.content.Context;
import android.view.View;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.google.common.primitives.Ints;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.microwave_sensor.WaveSensorHelper;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.LightAssistant;
import com.smart.product_agreement.param.LightCmdParam;
import java.util.List;

/* loaded from: classes4.dex */
public class ActDaliLightOrGroupVM extends BaseViewModel {
    public int blue;
    public long controlId;
    public long deviceId;
    public int green;
    public boolean groupControl;
    public boolean isBroadcast;
    public boolean isLocalScene;
    public boolean isWaveSensorAction;
    public int lightType;
    public int red;
    public boolean selectAction;
    public MutableLiveData<Object> controlObject = new MutableLiveData<>();
    public MutableLiveData<Boolean> isOpenLight = new MutableLiveData<>();
    public SingleLiveEvent<Void> openOrCloseEvent = new SingleLiveEvent<>();
    public MutableLiveData<Boolean> stateOn = new MutableLiveData<>();
    public MutableLiveData<Integer> brtProgress = new MutableLiveData<>();
    public int wy = 0;
    public int brt = 0;
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightOrGroupVM$$ExternalSyntheticLambda1
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActDaliLightOrGroupVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        if (view.getId() != R.id.layout_open_close) {
            return;
        }
        this.openOrCloseEvent.call();
    }

    public List<Integer> getColorList(Context context) {
        return Ints.asList(context.getResources().getIntArray(R.array.static_default_color));
    }

    public LightAssistant getLightCmdHelper() {
        return CmdAssistant.getLightCmdAssistant(this.controlObject.getValue(), new int[0]);
    }

    public void saveData(int type) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(getZoneNum());
        lightCmdParam.setLightType(type);
        lightCmdParam.setCmdType(43);
        lightCmdParam.setRed(this.red);
        lightCmdParam.setGreen(this.green);
        lightCmdParam.setBlue(this.blue);
        lightCmdParam.setRgbBrt(this.brt);
        lightCmdParam.setWy(this.wy);
        lightCmdParam.setWyBrt(this.brt);
        lightCmdParam.addExtParam(SceneHelper.OPTION, "0");
        if (this.isWaveSensorAction) {
            WaveSensorHelper.instance().cmdParam = lightCmdParam;
            WaveSensorHelper.instance().setSensorRelateBleParam();
            if (WaveSensorHelper.instance().isChangeAll) {
                int i = this.brt;
                lightCmdParam.setRgbBrt(i == 0 ? 0 : Math.max(i / 2, 1));
                int i2 = this.brt;
                lightCmdParam.setWyBrt(i2 == 0 ? 0 : Math.max(i2 / 2, 1));
                WaveSensorHelper.instance().setDataType = 2;
                WaveSensorHelper.instance().setSensorRelateBleParam();
                WaveSensorHelper.instance().setDataType = 3;
                LightCmdParam lightCmdParam2 = new LightCmdParam();
                lightCmdParam2.setZoneNum(getZoneNum());
                lightCmdParam2.setCmdType(1);
                lightCmdParam2.setOn(false);
                lightCmdParam2.addExtParam(SceneHelper.OPTION, "4");
                WaveSensorHelper.instance().cmdParam = lightCmdParam2;
                WaveSensorHelper.instance().setSensorRelateBleParam();
                return;
            }
            return;
        }
        lightCmdParam.addExtParam(SceneHelper.OPTION_VALUE, ActivityUtils.getTopActivity().getString(R.string.static_color));
        SceneHelper.instance().cmdParam = lightCmdParam;
        if (this.isLocalScene) {
            finishActivity(3001, null);
        } else {
            SceneHelper.instance().saveSelectResult(getLifecycleOwner(), new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightOrGroupVM$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActDaliLightOrGroupVM.this.lambda$saveData$1((Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveData$1(Boolean bool) {
        finishActivity(3001, null);
    }

    public void saveDataWithType(int lightType) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(getZoneNum());
        lightCmdParam.setCmdType(64);
        lightCmdParam.setLightType(lightType * 16);
        lightCmdParam.setRed(this.red);
        lightCmdParam.setGreen(this.green);
        lightCmdParam.setBlue(this.blue);
        lightCmdParam.setRgbBrt(this.brt);
        lightCmdParam.setWy(this.wy);
        lightCmdParam.setWyBrt(this.brt);
        lightCmdParam.addExtParam(SceneHelper.OPTION, "0");
        if (this.isWaveSensorAction) {
            WaveSensorHelper.instance().cmdParam = lightCmdParam;
            WaveSensorHelper.instance().setSensorRelateBleParam();
            if (WaveSensorHelper.instance().isChangeAll) {
                int i = this.brt;
                lightCmdParam.setRgbBrt(i == 0 ? 0 : Math.max(i / 2, 1));
                int i2 = this.brt;
                lightCmdParam.setWyBrt(i2 == 0 ? 0 : Math.max(i2 / 2, 1));
                WaveSensorHelper.instance().setDataType = 2;
                WaveSensorHelper.instance().setSensorRelateBleParam();
                WaveSensorHelper.instance().setDataType = 3;
                LightCmdParam lightCmdParam2 = new LightCmdParam();
                lightCmdParam2.setZoneNum(getZoneNum());
                lightCmdParam2.setCmdType(1);
                lightCmdParam2.setOn(false);
                lightCmdParam2.addExtParam(SceneHelper.OPTION, "4");
                WaveSensorHelper.instance().cmdParam = lightCmdParam2;
                WaveSensorHelper.instance().setSensorRelateBleParam();
                return;
            }
            return;
        }
        if (SceneHelper.instance().bindingType == 2 && this.isBroadcast) {
            lightCmdParam.addExtParam(SceneHelper.OPTION_VALUE, SceneHelper.getLightStaticActionWithType(getContext(), SceneHelper.instance().getConvertCmd(lightCmdParam).substring(4), SceneHelper.instance().controlObject));
        } else {
            lightCmdParam.addExtParam(SceneHelper.OPTION_VALUE, ActivityUtils.getTopActivity().getString(R.string.static_color));
        }
        SceneHelper.instance().cmdParam = lightCmdParam;
        if (this.isLocalScene) {
            finishActivity(3001, null);
        } else {
            SceneHelper.instance().saveSelectResult(getLifecycleOwner(), new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightOrGroupVM$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActDaliLightOrGroupVM.this.lambda$saveDataWithType$2((Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveDataWithType$2(Boolean bool) {
        finishActivity(3001, null);
    }

    public int getZoneNum() {
        if (this.lightType == 10009) {
            return DaliProHelper.BROADCAST_ADD;
        }
        if (ProductRepository.isDaliLightGroup(getControlObject())) {
            return DaliProHelper.getZoneNum(getControlObject());
        }
        return 1;
    }

    public Object getControlObject() {
        if (this.isWaveSensorAction) {
            return WaveSensorHelper.instance().relateObject;
        }
        return SceneHelper.instance().controlObject;
    }
}