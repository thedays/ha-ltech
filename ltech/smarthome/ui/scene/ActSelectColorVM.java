package com.ltech.smarthome.ui.scene;

import android.text.TextUtils;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.singleton.ComboCmdHelper;
import com.ltech.smarthome.ui.device.aspanel.AsHelper;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;
import com.ltech.smarthome.ui.device.e6knob.E6Helper;
import com.ltech.smarthome.ui.device.eurpanel.EurHelper;
import com.ltech.smarthome.ui.device.microwave_sensor.WaveSensorHelper;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.LightAssistant;
import com.smart.product_agreement.param.LightCmdParam;

/* loaded from: classes4.dex */
public class ActSelectColorVM extends BaseViewModel {
    private int blue;
    public boolean changeK;
    private int green;
    public boolean isE6;
    public boolean isGq;
    public boolean isLocalScene;
    public boolean isWaveSensorAction;
    public int lightType;
    public String productId;
    private int red;
    private int totalBrt;
    private int totalK;
    private int wy = 0;
    private int rgbBrt = 0;
    private int wyBrt = 0;

    public void setRed(int red) {
        this.red = red;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public void setWy(int wy) {
        this.wy = wy;
    }

    public void setWyBrt(int wyBrt) {
        this.wyBrt = wyBrt;
    }

    public void setRgbBrt(int rgbBrt) {
        this.rgbBrt = rgbBrt;
    }

    public int getRed() {
        return this.red;
    }

    public int getGreen() {
        return this.green;
    }

    public int getBlue() {
        return this.blue;
    }

    public int getWy() {
        return this.wy;
    }

    public int getRgbBrt() {
        return this.rgbBrt;
    }

    public int getWyBrt() {
        return this.wyBrt;
    }

    public int getTotalBrt() {
        return this.totalBrt;
    }

    public void setTotalBrt(int totalBrt) {
        this.totalBrt = totalBrt;
        this.rgbBrt = totalBrt;
        this.wyBrt = totalBrt;
    }

    public int getTotalK() {
        return this.totalK;
    }

    public void setTotalK(int totalK) {
        this.totalK = totalK;
    }

    public void initData() {
        String lightCmdData;
        if (this.isE6 && !TextUtils.isEmpty(E6Helper.instance().getActionInstruct())) {
            if (Integer.parseInt(E6Helper.instance().getActionInstruct().substring(0, 2), 16) == 28) {
                lightCmdData = E6Helper.instance().getActionInstruct().substring(2);
            }
            lightCmdData = "";
        } else {
            if (!this.isWaveSensorAction && !TextUtils.isEmpty(SceneHelper.instance().selectInstruct)) {
                lightCmdData = SceneHelper.getLightCmdData(SceneHelper.instance().selectInstruct);
            }
            lightCmdData = "";
        }
        if (lightCmdData.length() >= 12) {
            setRed(Integer.parseInt(lightCmdData.substring(0, 2), 16));
            setGreen(Integer.parseInt(lightCmdData.substring(2, 4), 16));
            setBlue(Integer.parseInt(lightCmdData.substring(4, 6), 16));
            setWyBrt(Integer.parseInt(lightCmdData.substring(6, 8), 16));
            setWy(Integer.parseInt(lightCmdData.substring(8, 10), 16));
            setRgbBrt(Integer.parseInt(lightCmdData.substring(10, 12), 16));
        }
    }

    public void saveData() {
        if (this.isGq) {
            finishActivity(3001, null);
            ComboCmdHelper.getInstance().selectColor(this.red, this.green, this.blue, this.rgbBrt, this.wy, this.wyBrt);
            return;
        }
        if (this.isE6) {
            E6Helper.instance().selectColor(this.red, this.green, this.blue, this.wyBrt, this.wy, this.rgbBrt);
            return;
        }
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(getZoneNum());
        if (!this.changeK) {
            lightCmdParam.setCmdType(43);
            lightCmdParam.setRed(this.red);
            lightCmdParam.setGreen(this.green);
            lightCmdParam.setBlue(this.blue);
            lightCmdParam.setRgbBrt(this.rgbBrt);
            lightCmdParam.setWy(this.wy);
            lightCmdParam.setWyBrt(this.wyBrt);
            lightCmdParam.addExtParam(SceneHelper.OPTION, "0");
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
                int i = this.rgbBrt;
                lightCmdParam.setRgbBrt(i == 0 ? 0 : Math.max(i / 2, 1));
                int i2 = this.wyBrt;
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
        if (SceneHelper.instance().isMultiZone) {
            SceneHelper.instance().goSelectZone(ActivityUtils.getTopActivity(), this.isLocalScene);
        } else if (this.isLocalScene) {
            finishActivity(3001, null);
        } else {
            SceneHelper.instance().saveSelectResult(getLifecycleOwner(), new IAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectColorVM$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSelectColorVM.this.lambda$saveData$0((Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveData$0(Boolean bool) {
        finishActivity(3001, null);
    }

    public int getZoneNum() {
        if (this.lightType == 10009) {
            return DaliProHelper.BROADCAST_ADD;
        }
        if (ProductRepository.isDaliLightGroup(getControlObject())) {
            return DaliProHelper.getZoneNum(getControlObject());
        }
        return (AsHelper.isNewUb(getControlObject()) || EurHelper.isEb125(getControlObject())) ? 15 : 1;
    }

    public Object getControlObject() {
        if (this.isE6) {
            return E6Helper.instance().getControlObject();
        }
        if (this.isWaveSensorAction) {
            return WaveSensorHelper.instance().relateObject;
        }
        return SceneHelper.instance().controlObject;
    }

    public LightAssistant getLightCmdHelper() {
        return CmdAssistant.getLightCmdAssistant(getControlObject(), getZoneNum());
    }
}