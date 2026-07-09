package com.ltech.smarthome.utils.cmd_assistant;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.message.CtrlPackage;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.dalipro.CgdProDataManager;
import com.ltech.smarthome.ui.device.light.PowerState;
import com.ltech.smarthome.utils.LightUtils;
import com.smart.message.DataPackage;
import com.smart.message.ResponseMsg;
import com.smart.message.base.BaseCmdParam;
import com.smart.message.base.BaseCtrlPackage;
import com.smart.message.base.IReceiveListener;
import com.smart.product_agreement.param.LightCmdParam;
import java.util.List;

/* loaded from: classes4.dex */
public class LightAssistant extends BaseAssistant {
    public void sendRgbBrt(Context context, int brt, boolean finish) {
        sendColorData(context, sendRgbBrt(brt), finish);
    }

    public LightCmdParam sendRgbBrt(int brt) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(36);
        setDeviceRGB(brt);
        lightCmdParam.setRgbBrt(LightUtils.progress2Brt(brt));
        return lightCmdParam;
    }

    public LightCmdParam sendRgbBrtToGq(int brt) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(36);
        setDeviceRGB(brt);
        lightCmdParam.setRgbBrt(brt);
        return lightCmdParam;
    }

    public void sendRgb(Context context, int color, boolean finish) {
        sendColorData(context, sendRgb(color), finish);
        setRGBState(true);
        setRGBColor(Color.red(color), Color.green(color), Color.blue(color));
    }

    public LightCmdParam sendRgb(int color) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(35);
        lightCmdParam.setRed(Color.red(color));
        lightCmdParam.setGreen(Color.green(color));
        lightCmdParam.setBlue(Color.blue(color));
        return lightCmdParam;
    }

    public LightCmdParam sendRgbToGq(int color) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(35);
        lightCmdParam.setRed(Color.red(color));
        lightCmdParam.setGreen(Color.green(color));
        lightCmdParam.setBlue(Color.blue(color));
        return lightCmdParam;
    }

    public void sendRgb(Context context, int red, int green, int blue, boolean finish) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(35);
        lightCmdParam.setRed(red);
        lightCmdParam.setGreen(green);
        lightCmdParam.setBlue(blue);
        sendColorData(context, lightCmdParam, finish);
        setRGBState(true);
        setRGBColor(red, green, blue);
    }

    public void sendRgbDC(Context context, int color, boolean finish) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(44);
        lightCmdParam.setRed(Color.red(color));
        lightCmdParam.setGreen(Color.green(color));
        lightCmdParam.setBlue(Color.blue(color));
        sendColorData(context, lightCmdParam, finish);
        setRGBState(true);
        setRGBColor(Color.red(color), Color.green(color), Color.blue(color));
    }

    public void sendRgbOnOff(Context context, boolean on) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(37);
        lightCmdParam.setOn(on);
        sendColorData(context, lightCmdParam, true);
        setRGBState(on);
    }

    public void sendRgbcw(Context context, int totalBrt, int red, int green, int blue, int c2, int w, boolean finish) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(72);
        lightCmdParam.setRed(red);
        lightCmdParam.setGreen(green);
        lightCmdParam.setBlue(blue);
        lightCmdParam.setC(c2);
        lightCmdParam.setW(w);
        lightCmdParam.setTotalBrt(totalBrt);
        sendColorData(context, lightCmdParam, finish);
        setRGBState(true);
        setRGBCWColor(totalBrt, red, green, blue, c2, w);
    }

    public void sendCCT(Context context, int k, int duv, int brt, boolean finish) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(75);
        lightCmdParam.setValue1(k);
        lightCmdParam.setValue2(duv);
        lightCmdParam.setValue3(brt);
        sendColorData(context, lightCmdParam, finish);
        setRGBState(true);
    }

    public void sendHSL(Context context, int h, int s, int l, boolean finish) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(76);
        lightCmdParam.setValue1(h);
        lightCmdParam.setValue2(s);
        lightCmdParam.setValue3(l);
        sendColorData(context, lightCmdParam, finish);
        setRGBState(true);
    }

    public void sendXYY(Context context, int x, int y, boolean finish) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(74);
        lightCmdParam.setValue1(x);
        lightCmdParam.setValue2(y);
        sendColorData(context, lightCmdParam, finish);
        setRGBState(true);
    }

    public void sendCtBrt(Context context, int brt, boolean finish) {
        sendColorData(context, sendCtBrt(brt), finish);
    }

    public LightCmdParam sendCtBrt(int brt) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(33);
        setDeviceWyRGB(brt);
        lightCmdParam.setWyBrt(LightUtils.progress2Brt(brt));
        return lightCmdParam;
    }

    public LightCmdParam sendCtBrtToGq(int brt) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(33);
        setDeviceWyRGB(brt);
        lightCmdParam.setWyBrt(brt);
        return lightCmdParam;
    }

    public void sendCW(Context context, int c2, boolean finish) {
        sendColorData(context, sendCW(c2), finish);
    }

    public LightCmdParam sendCW(int c2) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(32);
        setDeviceWy(255 - c2);
        lightCmdParam.setWy(c2);
        return lightCmdParam;
    }

    public LightCmdParam sendCWToGq(int c2) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(32);
        setDeviceWy(255 - c2);
        lightCmdParam.setWy(c2);
        return lightCmdParam;
    }

    public void sendCTDE(Context context, int c2, boolean finish) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(45);
        setDeviceWy(255 - c2);
        lightCmdParam.setWy(c2);
        sendColorData(context, lightCmdParam, finish);
    }

    public void sendDimBrt(Context context, int brt, boolean finish) {
        sendColorData(context, sendDimBrt(brt), finish);
    }

    public LightCmdParam sendDimBrt(int brt) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(30);
        setDeviceRGB(brt);
        lightCmdParam.setWyBrt(LightUtils.progress2BrtHasBelowOne(brt));
        return lightCmdParam;
    }

    public LightCmdParam sendDimBrtToGq(int brt) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(30);
        setDeviceRGB(brt);
        lightCmdParam.setWyBrt(brt);
        return lightCmdParam;
    }

    public LightCmdParam sendWy(Context context, int wy, boolean finish) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(40);
        lightCmdParam.setWy(wy);
        sendColorData(context, lightCmdParam, finish);
        setWState(true);
        setDeviceWy(wy);
        return lightCmdParam;
    }

    public void sendWyBrt(Context context, int brt, boolean finish) {
        sendColorData(context, sendWyBrt(brt), finish);
    }

    public LightCmdParam sendWyBrt(int brt) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(42);
        setDeviceWyRGB(brt);
        lightCmdParam.setWyBrt(LightUtils.progress2BrtHasBelowOne(brt));
        return lightCmdParam;
    }

    public LightCmdParam sendWyBrtToGq(int brt) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(42);
        setDeviceWyRGB(brt);
        lightCmdParam.setWyBrt(brt);
        return lightCmdParam;
    }

    public void sendRgbSaturation(Context context, int saturation, boolean finish) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(68);
        lightCmdParam.setRgbSaturation(saturation);
        sendColorData(context, lightCmdParam, finish);
    }

    public void sendWyOnOff(Context context, boolean on) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(41);
        lightCmdParam.setOn(on);
        sendColorData(context, lightCmdParam, true);
        setWState(on);
    }

    public void sendWOnOff(Context context, boolean on) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(39);
        lightCmdParam.setOn(on);
        sendColorData(context, lightCmdParam, true);
        setWState(on);
    }

    public void sendW(Context context, int brt, boolean finish) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(38);
        setDeviceWyRGB(brt);
        int progress2Brt = LightUtils.progress2Brt(brt);
        lightCmdParam.setWyBrt(progress2Brt);
        sendColorData(context, lightCmdParam, finish);
        setWState(true);
        setDeviceWy(progress2Brt);
    }

    public void sendWBrt(Context context, int brt, boolean finish) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(38);
        setDeviceWyRGB(brt);
        lightCmdParam.setWyBrt(LightUtils.progress2BrtHasBelowOne(brt));
        sendColorData(context, lightCmdParam, finish);
    }

    public void queryOnState(Context context, IAction<ResponseMsg> result) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(21);
        sendWithResult(context, lightCmdParam, result);
    }

    public void previewOnState(Context context, int rgbBrt, int wyBrt, boolean finish) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(19);
        lightCmdParam.setRgbBrt(rgbBrt);
        lightCmdParam.setWyBrt(wyBrt);
        sendWithoutResultInternal(context, lightCmdParam, finish);
    }

    public void setOnState(Context context, int state, int rgbBrt, int wyBrt, IAction<Boolean> result) {
        save(context, setOnState(state, rgbBrt, wyBrt), result);
    }

    public LightCmdParam setOnState(int state, int rgbBrt, int wyBrt) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(20);
        lightCmdParam.setOnState(state);
        lightCmdParam.setRgbBrt(rgbBrt);
        lightCmdParam.setWyBrt(wyBrt);
        return lightCmdParam;
    }

    public void setOnStateBatch(Context context, int state, int rgbBrt, int wyBrt, IAction<ResponseMsg> result) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(20);
        lightCmdParam.setOnState(state);
        lightCmdParam.setRgbBrt(rgbBrt);
        lightCmdParam.setWyBrt(wyBrt);
        sendWithResult(context, lightCmdParam, result);
    }

    public void setOnState(Context context, PowerState powerState, IAction<Boolean> result) {
        save(context, setOnState(powerState), result);
    }

    public LightCmdParam setOnState(PowerState powerState) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(90);
        lightCmdParam.setOnState(powerState.state);
        lightCmdParam.setValueList(powerState.getStateValues());
        return lightCmdParam;
    }

    public void setKRange(Context context, int min, int max, IAction<Boolean> result) {
        save(context, setKRange(min, max), result);
    }

    public LightCmdParam setKRange(int min, int max) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(46);
        lightCmdParam.setkMax(max);
        lightCmdParam.setkMin(min);
        return lightCmdParam;
    }

    public void setOnStateWithoutDialog(Context context, int state, int rgbBrt, int wyBrt, final IAction<Boolean> result) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(20);
        lightCmdParam.setOnState(state);
        lightCmdParam.setRgbBrt(rgbBrt);
        lightCmdParam.setWyBrt(wyBrt);
        sendWithResult(context, lightCmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.LightAssistant$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf((r2 == null || r2.getStateCode() == 3) ? false : true));
            }
        });
    }

    public void setOnOffTime(Context context, int onTime, int offTime, IAction<Boolean> result) {
        save(context, setOnOffTime(onTime, offTime), result);
    }

    public LightCmdParam setOnOffTime(int onTime, int offTime) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(22);
        lightCmdParam.setOnTime(onTime);
        lightCmdParam.setOffTime(offTime);
        return lightCmdParam;
    }

    public void setAutoNetTime(Context context, int time, IAction<Boolean> result) {
        save(context, setAutoNetTime(time), result);
    }

    public LightCmdParam setAutoNetTime(int time) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(47);
        lightCmdParam.setAutoNetTime(time);
        return lightCmdParam;
    }

    public void getAutoNetTime(Context context, IAction<ResponseMsg> result) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(48);
        sendWithResult(context, lightCmdParam, result);
    }

    public void setOnOffTime(Context context, int agreementId, int onTime, int offTime, IAction<Boolean> result) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(22);
        lightCmdParam.setOnTime(onTime);
        lightCmdParam.setOffTime(offTime);
        CtrlPackage ctrlPackage = new CtrlPackage(agreementId);
        ctrlPackage.setAddress(65535);
        save(context, ctrlPackage, lightCmdParam, result);
    }

    public void queryOnOffTime(Context context, IAction<ResponseMsg> result) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(23);
        sendWithResult(context, lightCmdParam, result);
    }

    public void setPowerOnTime(Context context, int powerOnTime, IAction<Boolean> result) {
        save(context, setPowerOnTime(powerOnTime), result);
    }

    public LightCmdParam setPowerOnTime(int powerOnTime) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(14);
        lightCmdParam.setPowerOnTime(powerOnTime);
        return lightCmdParam;
    }

    public void setPowerOnTime(Context context, int agreementId, int powerOnTime, IAction<Boolean> result) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(14);
        lightCmdParam.setPowerOnTime(powerOnTime);
        CtrlPackage ctrlPackage = new CtrlPackage(agreementId);
        ctrlPackage.setAddress(65535);
        save(context, ctrlPackage, lightCmdParam, result);
    }

    public void previewOnState(Context context, int agreementId, int rgbBrt, int wyBrt, boolean finish) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(19);
        lightCmdParam.setRgbBrt(rgbBrt);
        lightCmdParam.setWyBrt(wyBrt);
        CtrlPackage ctrlPackage = new CtrlPackage(agreementId);
        ctrlPackage.setAddress(65535);
        sendWithoutResult(context, ctrlPackage, lightCmdParam);
    }

    public void setOnState(Context context, int agreementId, int state, int rgbBrt, int wyBrt, IAction<Boolean> result) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(20);
        lightCmdParam.setOnState(state);
        lightCmdParam.setRgbBrt(rgbBrt);
        lightCmdParam.setWyBrt(wyBrt);
        CtrlPackage ctrlPackage = new CtrlPackage(agreementId);
        ctrlPackage.setAddress(65535);
        save(context, ctrlPackage, lightCmdParam, result);
    }

    public void setSceneOnTime(Context context, int sceneOnTime, IAction<Boolean> result) {
        save(context, setSceneOnTime(sceneOnTime), result);
    }

    public LightCmdParam setSceneOnTime(int sceneOnTime) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(6);
        lightCmdParam.setSceneOnTime(sceneOnTime);
        return lightCmdParam;
    }

    public void querySceneOnTime(Context context, IAction<ResponseMsg> result) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(84);
        sendWithResult(context, lightCmdParam, result);
    }

    public void queryPowerOnTime(Context context, IAction<ResponseMsg> result) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(15);
        sendWithResult(context, lightCmdParam, result);
    }

    public void queryBuzzerState(Context context, int subCmdType, IAction<ResponseMsg> result) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(69);
        lightCmdParam.setSubCmdType(subCmdType);
        sendWithResult(context, lightCmdParam, result);
    }

    public void setBuzzerState(Context context, int subCmdType, int state, IAction<ResponseMsg> result) {
        saveWithResponse(context, setBuzzerState(subCmdType, state), result);
    }

    public LightCmdParam setBuzzerState(int subCmdType, int state) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(70);
        lightCmdParam.setSubCmdType(subCmdType);
        lightCmdParam.setBuzzerState(state);
        return lightCmdParam;
    }

    public void setModeMemorize(Context context, int modeMemorize, IAction<Boolean> result) {
        save(context, setModeMemorize(modeMemorize), result);
    }

    public LightCmdParam setModeMemorize(int modeMemorize) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(71);
        lightCmdParam.setModeMemorize(modeMemorize);
        return lightCmdParam;
    }

    public void setCtLightMode(Context context, int mode, IAction<Boolean> result) {
        save(context, setCtLightMode(mode), result);
    }

    public LightCmdParam setCtLightMode(int mode) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(24);
        lightCmdParam.setCtLightMode(mode);
        return lightCmdParam;
    }

    public void queryCtLightMode(Context context, IAction<ResponseMsg> result) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(25);
        sendWithResult(context, lightCmdParam, result);
    }

    public void setDimDepth(Context context, int depth, IAction<Boolean> result) {
        save(context, setDimDepth(depth), result);
    }

    public LightCmdParam setDimDepth(int depth) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(28);
        lightCmdParam.setDimDepth(depth);
        return lightCmdParam;
    }

    public void queryDimDepth(Context context, IAction<ResponseMsg> result) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(27);
        sendWithResult(context, lightCmdParam, result);
    }

    public void setLineOrder(Context context, int lightType, int lineOrder, IAction<Boolean> result) {
        save(context, setLineOrder(lightType, lineOrder), result);
    }

    public LightCmdParam setLineOrder(int lightType, int lineOrder) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(66);
        lightCmdParam.setLightType(lightType);
        lightCmdParam.setLineOrder(lineOrder);
        return lightCmdParam;
    }

    public void queryLineOrder(Context context, IAction<ResponseMsg> result) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(65);
        sendWithResult(context, lightCmdParam, result);
    }

    public void setLineOrderMulti(Context context, int lightType, int lineOrder, IAction<Boolean> result) {
        save(context, setLineOrderMulti(lightType, lineOrder), result);
    }

    public LightCmdParam setLineOrderMulti(int lightType, int lineOrder) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(89);
        lightCmdParam.setLightType(lightType);
        lightCmdParam.setLineOrder(lineOrder);
        return lightCmdParam;
    }

    public void queryLineOrderMulti(Context context, IAction<ResponseMsg> result) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(88);
        sendWithResult(context, lightCmdParam, result);
    }

    public void setWhiteBalance(Context context, int r, int g, int b2, IAction<Boolean> result) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(77);
        lightCmdParam.setRed(r);
        lightCmdParam.setGreen(g);
        lightCmdParam.setBlue(b2);
        save(context, lightCmdParam, result);
    }

    public void queryWhiteBalance(Context context, IAction<ResponseMsg> result) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(78);
        sendWithResult(context, lightCmdParam, result);
    }

    public void previewWhiteBalance(Context context, int r, int g, int b2, boolean finish) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(79);
        lightCmdParam.setRed(r);
        lightCmdParam.setGreen(g);
        lightCmdParam.setBlue(b2);
        sendColorData(context, lightCmdParam, finish);
        setRGBState(true);
    }

    public void queryDuvSupport(Context context, IAction<ResponseMsg> result) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(80);
        sendWithResult(context, lightCmdParam, result);
    }

    public void queryDuvCalibration(Context context, IAction<ResponseMsg> result) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(82);
        sendWithResult(context, lightCmdParam, result);
    }

    public void setDuvCalibration(Context context, String duv, IAction<Boolean> result) {
        save(context, setDuvCalibration(duv), result);
    }

    public LightCmdParam setDuvCalibration(String duv) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(81);
        lightCmdParam.setValue(duv);
        return lightCmdParam;
    }

    private void sendColorData(Context context, BaseCmdParam cmdParam, boolean finish) {
        DataPackage.DataPacketBuilder intervalTime = Injection.message().create(context).cmdParam(cmdParam).timeOutTime(5000).resendTimes(0).intervalTime(100);
        if (ProductRepository.needPublishAddress(this.controlObject)) {
            CtrlPackage ctrlPackage = new CtrlPackage(2);
            ctrlPackage.setAddress(getPublishAddress());
            intervalTime.control((BaseCtrlPackage) ctrlPackage);
        } else {
            intervalTime.control(this.controlObject);
        }
        if (finish) {
            intervalTime.sendTimes(3).enqueue();
        } else {
            intervalTime.sendTimes(1).filterEnqueue(150);
        }
    }

    public boolean filterColorData(BaseCmdParam cmdParam) {
        int cmdType = cmdParam.getCmdType();
        return cmdType == 51 || cmdType == 68;
    }

    @SafeVarargs
    public final void sendOnOff(Context context, boolean on, IAction<Boolean>... actions) {
        sendOnOff(context, on);
    }

    public final void sendOnOff(Context context, boolean on) {
        LightCmdParam sendOnOff = sendOnOff(on);
        setState(on);
        sendOnOffWithoutResult(context, sendOnOff, new int[0]);
    }

    public final void sendOnOffEurPanel(Context context, boolean on) {
        LightCmdParam sendOnOff = sendOnOff(on);
        setState(on);
        sendWithoutResultByPublish(context, sendOnOff, 5, 100);
    }

    public final LightCmdParam sendOnOff(boolean on) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setCmdType(1);
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setOn(on);
        return lightCmdParam;
    }

    public final void sendSingleOnOff(Context context, boolean on) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setCmdType(1);
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setOn(on);
        sendOnOffWithoutResult(context, lightCmdParam, new int[0]);
    }

    public final void sendSingleOnOff(Context context, boolean on, IAction<Boolean> actions) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setCmdType(1);
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setOn(on);
        switchOnOff(context, lightCmdParam, actions);
    }

    public final void setState(boolean on) {
        if (this.controlObject instanceof Device) {
            ((Device) this.controlObject).getDeviceState().setOn(on);
            Injection.repo().device().saveDevice((Device) this.controlObject);
            return;
        }
        Object obj = this.controlObject;
        if (obj instanceof Group) {
            Group group = (Group) obj;
            ((Group) this.controlObject).getGroupState().setOn(on);
            if (!group.getDeviceIds().isEmpty()) {
                List<Device> devicesByIds = Injection.repo().device().getDevicesByIds(group.getDeviceIds());
                for (Device device : devicesByIds) {
                    if (device.getDeviceState() != null) {
                        device.getDeviceState().setOn(on);
                    }
                }
                Injection.repo().device().saveDevice(devicesByIds);
            }
            Injection.repo().group().saveGroup((Group) this.controlObject);
        }
    }

    public final void setWState(boolean won) {
        if (this.controlObject instanceof Device) {
            ((Device) this.controlObject).getDeviceState().setWOn(won);
            Injection.repo().device().saveDevice((Device) this.controlObject);
            return;
        }
        Object obj = this.controlObject;
        if (obj instanceof Group) {
            Group group = (Group) obj;
            ((Group) this.controlObject).getGroupState().setWOn(won);
            if (!group.getDeviceIds().isEmpty()) {
                List<Device> devicesByIds = Injection.repo().device().getDevicesByIds(group.getDeviceIds());
                for (Device device : devicesByIds) {
                    if (device.getDeviceState() != null) {
                        device.getDeviceState().setWOn(won);
                    }
                }
                Injection.repo().device().saveDevice(devicesByIds);
            }
            Injection.repo().group().saveGroup((Group) this.controlObject);
        }
    }

    public final void setRGBState(boolean rgbOn) {
        if (this.controlObject instanceof Device) {
            ((Device) this.controlObject).getDeviceState().setRGBOn(rgbOn);
            Injection.repo().device().saveDevice((Device) this.controlObject);
            return;
        }
        Object obj = this.controlObject;
        if (obj instanceof Group) {
            Group group = (Group) obj;
            ((Group) this.controlObject).getGroupState().setRGBOn(rgbOn);
            if (!group.getDeviceIds().isEmpty()) {
                List<Device> devicesByIds = Injection.repo().device().getDevicesByIds(group.getDeviceIds());
                for (Device device : devicesByIds) {
                    if (device.getDeviceState() != null) {
                        device.getDeviceState().setRGBOn(rgbOn);
                    }
                }
                Injection.repo().device().saveDevice(devicesByIds);
            }
            Injection.repo().group().saveGroup((Group) this.controlObject);
        }
    }

    public final void setDeviceWy(int wy) {
        if (this.controlObject instanceof Device) {
            ((Device) this.controlObject).getDeviceState().setWy(wy);
            ((Device) this.controlObject).getDeviceState().setWarm(wy);
            ((Device) this.controlObject).getDeviceState().setCold(255 - wy);
            Injection.repo().device().saveDevice((Device) this.controlObject);
            return;
        }
        Object obj = this.controlObject;
        if (obj instanceof Group) {
            Group group = (Group) obj;
            ((Group) this.controlObject).getGroupState().setWy(wy);
            ((Group) this.controlObject).getGroupState().setWarm(wy);
            int i = 255 - wy;
            ((Group) this.controlObject).getGroupState().setCold(i);
            if (!group.getDeviceIds().isEmpty()) {
                List<Device> devicesByIds = Injection.repo().device().getDevicesByIds(group.getDeviceIds());
                for (Device device : devicesByIds) {
                    if (device.getDeviceState() != null) {
                        device.getDeviceState().setWy(wy);
                        device.getDeviceState().setWarm(wy);
                        device.getDeviceState().setCold(i);
                    }
                }
                Injection.repo().device().saveDevice(devicesByIds);
            }
            Injection.repo().group().saveGroup((Group) this.controlObject);
        }
    }

    public final void setDeviceWyRGB(int wyRGB) {
        if (this.controlObject instanceof Device) {
            ((Device) this.controlObject).getDeviceState().setWyBrt(wyRGB);
            Injection.repo().device().saveDevice((Device) this.controlObject);
            return;
        }
        Object obj = this.controlObject;
        if (obj instanceof Group) {
            Group group = (Group) obj;
            ((Group) this.controlObject).getGroupState().setWyBrt(wyRGB);
            if (!group.getDeviceIds().isEmpty()) {
                List<Device> devicesByIds = Injection.repo().device().getDevicesByIds(group.getDeviceIds());
                for (Device device : devicesByIds) {
                    if (device.getDeviceState() != null) {
                        device.getDeviceState().setWyBrt(wyRGB);
                    }
                }
                Injection.repo().device().saveDevice(devicesByIds);
            }
            Injection.repo().group().saveGroup((Group) this.controlObject);
        }
    }

    public final void setDeviceRGB(int RGBBrt) {
        if (this.controlObject instanceof Device) {
            ((Device) this.controlObject).getDeviceState().setRgbBrt(RGBBrt);
            Injection.repo().device().saveDevice((Device) this.controlObject);
            return;
        }
        Object obj = this.controlObject;
        if (obj instanceof Group) {
            Group group = (Group) obj;
            ((Group) this.controlObject).getGroupState().setRgbBrt(RGBBrt);
            if (!group.getDeviceIds().isEmpty()) {
                List<Device> devicesByIds = Injection.repo().device().getDevicesByIds(group.getDeviceIds());
                for (Device device : devicesByIds) {
                    if (device.getDeviceState() != null) {
                        device.getDeviceState().setRgbBrt(RGBBrt);
                    }
                }
                Injection.repo().device().saveDevice(devicesByIds);
            }
            Injection.repo().group().saveGroup((Group) this.controlObject);
        }
    }

    public final void setRGBColor(int red, int green, int blue) {
        if (this.controlObject instanceof Device) {
            ((Device) this.controlObject).getDeviceState().setRed(red);
            ((Device) this.controlObject).getDeviceState().setGreen(green);
            ((Device) this.controlObject).getDeviceState().setBlue(blue);
            Injection.repo().device().saveDevice((Device) this.controlObject);
            return;
        }
        Object obj = this.controlObject;
        if (obj instanceof Group) {
            Group group = (Group) obj;
            ((Group) this.controlObject).getGroupState().setRed(red);
            ((Group) this.controlObject).getGroupState().setGreen(green);
            ((Group) this.controlObject).getGroupState().setBlue(blue);
            if (!group.getDeviceIds().isEmpty()) {
                List<Device> devicesByIds = Injection.repo().device().getDevicesByIds(group.getDeviceIds());
                for (Device device : devicesByIds) {
                    if (device.getDeviceState() != null) {
                        device.getDeviceState().setRed(red);
                        device.getDeviceState().setGreen(green);
                        device.getDeviceState().setBlue(blue);
                    }
                }
                Injection.repo().device().saveDevice(devicesByIds);
            }
            Injection.repo().group().saveGroup((Group) this.controlObject);
        }
    }

    public final void setRGBCWColor(int totalBrt, int red, int green, int blue, int c2, int w) {
        if (this.controlObject instanceof Device) {
            ((Device) this.controlObject).getDeviceState().setRed(red);
            ((Device) this.controlObject).getDeviceState().setGreen(green);
            ((Device) this.controlObject).getDeviceState().setBlue(blue);
            ((Device) this.controlObject).getDeviceState().setCold(c2);
            ((Device) this.controlObject).getDeviceState().setWarm(w);
            ((Device) this.controlObject).getDeviceState().setRgbBrt(totalBrt);
            ((Device) this.controlObject).getDeviceState().setWyBrt(totalBrt);
            Injection.repo().device().saveDevice((Device) this.controlObject);
            return;
        }
        Object obj = this.controlObject;
        if (obj instanceof Group) {
            Group group = (Group) obj;
            ((Group) this.controlObject).getGroupState().setRed(red);
            ((Group) this.controlObject).getGroupState().setGreen(green);
            ((Group) this.controlObject).getGroupState().setBlue(blue);
            ((Group) this.controlObject).getGroupState().setCold(c2);
            ((Group) this.controlObject).getGroupState().setWarm(w);
            ((Group) this.controlObject).getGroupState().setRgbBrt(totalBrt);
            ((Group) this.controlObject).getGroupState().setWyBrt(totalBrt);
            if (!group.getDeviceIds().isEmpty()) {
                List<Device> devicesByIds = Injection.repo().device().getDevicesByIds(group.getDeviceIds());
                for (Device device : devicesByIds) {
                    if (device.getDeviceState() != null) {
                        device.getDeviceState().setRed(red);
                        device.getDeviceState().setGreen(green);
                        device.getDeviceState().setBlue(blue);
                        device.getDeviceState().setCold(c2);
                        device.getDeviceState().setWarm(w);
                        device.getDeviceState().setRgbBrt(totalBrt);
                        device.getDeviceState().setWyBrt(totalBrt);
                    }
                }
                Injection.repo().device().saveDevice(devicesByIds);
            }
            Injection.repo().group().saveGroup((Group) this.controlObject);
        }
    }

    public void sendRgbBrtHas1to9(Context context, int brt, boolean finish) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(36);
        Log.i("onProgressChanged", "sendRgbBrtHas1to9=" + brt);
        setDeviceRGB(brt);
        lightCmdParam.setRgbBrt(LightUtils.progress2BrtHasBelowOne(brt));
        sendColorData(context, lightCmdParam, finish);
    }

    public void sendCtBrtHas1to9(Context context, int brt, boolean finish) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(33);
        setDeviceWyRGB(brt);
        lightCmdParam.setWyBrt(LightUtils.progress2BrtHasBelowOne(brt));
        sendColorData(context, lightCmdParam, finish);
    }

    public void sendCtBrtHas0to9(Context context, int brt, boolean finish) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(33);
        setDeviceWyRGB(brt);
        lightCmdParam.setWyBrt(LightUtils.progress2BrtIncludeZero(brt));
        sendColorData(context, lightCmdParam, finish);
    }

    public void sendDimBrtD1Has1to9(Context context, int brt, boolean finish) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(29);
        setDeviceRGB(brt);
        setDeviceWyRGB(brt);
        lightCmdParam.setWyBrt(LightUtils.progress2BrtHasBelowOne(brt));
        sendColorData(context, lightCmdParam, finish);
    }

    public LightCmdParam sendDimBrtD1Has0to255(Context context, int brt, boolean finish) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(29);
        setDeviceRGB(brt);
        setDeviceWyRGB(brt);
        lightCmdParam.setWyBrt(brt);
        sendColorData(context, lightCmdParam, finish);
        return lightCmdParam;
    }

    public void sendDimBrtHas1to9(Context context, int brt, boolean finish) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(30);
        Log.i("sendDimBrt", "sendDimBrtHas1to9=" + brt);
        setDeviceWyRGB(brt);
        int progress2BrtHasBelowOne = LightUtils.progress2BrtHasBelowOne(brt);
        Log.i("sendDimBrt", "sendDimBrtAfter=" + progress2BrtHasBelowOne);
        lightCmdParam.setWyBrt(progress2BrtHasBelowOne);
        sendColorData(context, lightCmdParam, finish);
    }

    public void sendWyBrtHas1to9(Context context, int brt, boolean finish) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(42);
        setDeviceWyRGB(brt);
        if (brt > 0) {
            brt = LightUtils.progress2BrtHasBelowOne(brt);
        }
        lightCmdParam.setWyBrt(brt);
        sendColorData(context, lightCmdParam, finish);
    }

    public void sendWHas1to9(Context context, int brt, boolean finish) {
        sendColorData(context, sendWHas1to9(brt), finish);
        setWState(true);
        setDeviceWy(brt);
    }

    public LightCmdParam sendWHas1to9(int brt) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(38);
        setDeviceWyRGB(brt);
        lightCmdParam.setWyBrt(LightUtils.progress2BrtHasBelowOne(brt));
        return lightCmdParam;
    }

    public LightCmdParam sendWHas1to9ToGq(int brt) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(38);
        setDeviceWyRGB(brt);
        lightCmdParam.setWyBrt(brt);
        return lightCmdParam;
    }

    public void sendCWHas1to9(Context context, int c2, boolean finish) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(32);
        setDeviceWy(c2);
        lightCmdParam.setWy(c2);
        sendColorData(context, lightCmdParam, finish);
    }

    public void sendTotalBrt(Context context, int brt, boolean finish) {
        sendColorData(context, sendTotalBrt(brt), finish);
    }

    public LightCmdParam sendTotalBrt(int brt) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(49);
        setDeviceTotalBrt(brt);
        lightCmdParam.setTotalBrt(LightUtils.progress2BrtHasBelowOne(brt));
        return lightCmdParam;
    }

    public LightCmdParam sendTotalBrtToGq(int brt) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(49);
        setDeviceTotalBrt(brt);
        lightCmdParam.setTotalBrt(brt);
        return lightCmdParam;
    }

    public LightCmdParam sendDaliTotalBrtToGq(int brt) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(29);
        lightCmdParam.setWyBrt(brt);
        return lightCmdParam;
    }

    public void sendTotalBrt(Context context, int wyBrt, int rgbBrt, boolean finish) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(83);
        setDeviceWyBrt(wyBrt);
        lightCmdParam.setWyBrt(wyBrt);
        setDeviceRGB(rgbBrt);
        lightCmdParam.setRgbBrt(rgbBrt);
        sendColorData(context, lightCmdParam, finish);
    }

    public void sendTotalK(Context context, int totalK, boolean finish) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(50);
        lightCmdParam.setTotalK(totalK);
        setDeviceTotalK(totalK);
        sendColorData(context, lightCmdParam, finish);
    }

    public void sendChannelValue(Context context, int num, int value, boolean finish) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(51);
        lightCmdParam.setChannelNum(num);
        lightCmdParam.setChannelValue(value);
        setDeviceChannel(num, value);
        sendColorData(context, lightCmdParam, finish);
    }

    public void sendChannelValue(Context context, int num, List<Integer> value, boolean finish) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(85);
        lightCmdParam.setChannelNum(num);
        lightCmdParam.setChannelValues(value);
        sendColorData(context, lightCmdParam, finish);
    }

    public void sendSingleChannelValue(Context context, int value, boolean finish) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(87);
        lightCmdParam.setChannelValue(value);
        sendColorData(context, lightCmdParam, finish);
    }

    public void setDmxChannelValue(Context context, int headAddress, int channelNum, final IAction<Boolean> iAction) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(86);
        lightCmdParam.setValue1(headAddress);
        lightCmdParam.setChannelNum(channelNum);
        sendWithResult(context, lightCmdParam, new IAction<ResponseMsg>(this) { // from class: com.ltech.smarthome.utils.cmd_assistant.LightAssistant.1
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg msg) {
                IAction iAction2 = iAction;
                if (iAction2 != null) {
                    iAction2.act(Boolean.valueOf(msg != null && msg.getStateCode() == 0));
                }
            }
        });
    }

    public void sendSwitchValue(Context context, int value, IAction<ResponseMsg> result) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(67);
        lightCmdParam.setSwitchData(value);
        sendWithResult(context, lightCmdParam, result);
    }

    public final void setDeviceWyBrt(int wyRGB) {
        if (this.controlObject instanceof Device) {
            ((Device) this.controlObject).getDeviceState().setWyBrt(wyRGB);
            Injection.repo().device().saveDevice((Device) this.controlObject);
        } else if (this.controlObject instanceof Group) {
            ((Group) this.controlObject).getGroupState().setWyBrt(wyRGB);
            Injection.repo().group().saveGroup((Group) this.controlObject);
        }
    }

    public final void setDeviceTotalBrt(int totalBrt) {
        if (this.controlObject instanceof Device) {
            ((Device) this.controlObject).getDeviceState().setTotalBrt(totalBrt);
            ((Device) this.controlObject).getDeviceState().setRgbBrt(totalBrt);
            ((Device) this.controlObject).getDeviceState().setWyBrt(totalBrt);
            Injection.repo().device().saveDevice((Device) this.controlObject);
            return;
        }
        if (this.controlObject instanceof Group) {
            ((Group) this.controlObject).getGroupState().setTotalBrt(totalBrt);
            ((Group) this.controlObject).getGroupState().setRgbBrt(totalBrt);
            ((Group) this.controlObject).getGroupState().setWyBrt(totalBrt);
            Injection.repo().group().saveGroup((Group) this.controlObject);
        }
    }

    public final void setDeviceTotalK(int totalK) {
        if (this.controlObject instanceof Device) {
            ((Device) this.controlObject).getDeviceState().setTotalK(totalK);
            Injection.repo().device().saveDevice((Device) this.controlObject);
        } else if (this.controlObject instanceof Group) {
            ((Group) this.controlObject).getGroupState().setTotalK(totalK);
            Injection.repo().group().saveGroup((Group) this.controlObject);
        }
    }

    public final void setDeviceChannel(int channel, int value) {
        if (this.controlObject instanceof Device) {
            if (channel == 1) {
                ((Device) this.controlObject).getDeviceState().setRed(value);
            } else if (channel == 2) {
                ((Device) this.controlObject).getDeviceState().setGreen(value);
            } else if (channel == 3) {
                ((Device) this.controlObject).getDeviceState().setBlue(value);
            } else if (channel == 4) {
                ((Device) this.controlObject).getDeviceState().setCold(value);
            } else if (channel == 5) {
                ((Device) this.controlObject).getDeviceState().setWarm(value);
            }
            Injection.repo().device().saveDevice((Device) this.controlObject);
            return;
        }
        if (this.controlObject instanceof Group) {
            if (channel == 1) {
                ((Group) this.controlObject).getGroupState().setRed(value);
            } else if (channel == 2) {
                ((Group) this.controlObject).getGroupState().setGreen(value);
            } else if (channel == 3) {
                ((Group) this.controlObject).getGroupState().setBlue(value);
            } else if (channel == 4) {
                ((Group) this.controlObject).getGroupState().setCold(value);
            } else if (channel == 5) {
                ((Group) this.controlObject).getGroupState().setWarm(value);
            }
            Injection.repo().group().saveGroup((Group) this.controlObject);
        }
    }

    public void setKRange(Context context, int min, int max, boolean isLast, IAction<Boolean> result) {
        sendEngineerMode(context, setKRange(min, max), isLast, result);
    }

    public void queryCgdLight(Context context, int curPackage, int cleanDataType, IAction<ResponseMsg> result, IAction<Integer> progress, IAction<Integer> getFirstPackage) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCleanData(cleanDataType);
        lightCmdParam.setCurPackage(curPackage);
        lightCmdParam.setCmdType(53);
        sendWithResultAndPackage(context, lightCmdParam, cleanDataType, curPackage, result, progress, getFirstPackage);
    }

    protected int sendWithResultAndPackage(final Context context, BaseCmdParam cmdParam, int cleanDataType, final int curPackage, final IAction<ResponseMsg> result, final IAction<Integer> progress, final IAction<Integer> getFirstPackage) {
        int i;
        int i2 = 3;
        if (cleanDataType == 3 && curPackage == 0) {
            i = 600000;
            i2 = 0;
        } else {
            i = 4000;
        }
        return Injection.message().create(context).cmdParam(cmdParam).timeOutTime(i).resendTimes(i2).control(this.controlObject).receiveListener(new IReceiveListener() { // from class: com.ltech.smarthome.utils.cmd_assistant.LightAssistant.2
            @Override // com.smart.message.base.IReceiveListener
            public void onSuccess(ResponseMsg msg) {
                if (msg != null && msg.getStateCode() == 32) {
                    LightAssistant.this.queryCgdLight(context, curPackage, 3, result, progress, getFirstPackage);
                    return;
                }
                if (msg != null && msg.getStateCode() == 21) {
                    result.act(msg);
                    return;
                }
                if (msg != null && msg.getResData().length() >= 20) {
                    int parseInt = Integer.parseInt(msg.getResData().substring(16, 18), 16);
                    int parseInt2 = Integer.parseInt(msg.getResData().substring(18, 20), 16);
                    if (parseInt2 == 0) {
                        getFirstPackage.act(0);
                    }
                    if (msg.getResData().length() >= 22) {
                        int parseInt3 = Integer.parseInt(msg.getResData().substring(20, 22), 16);
                        for (int i3 = 0; i3 < parseInt3; i3++) {
                            int i4 = i3 * 8;
                            CgdProDataManager.getInstance().addDeviceByteData(msg.getResData().substring(i4 + 22, i4 + 30));
                            progress.act(Integer.valueOf(CgdProDataManager.getInstance().getDeviceByteDataList().size()));
                        }
                    }
                    if (parseInt != parseInt2) {
                        LightAssistant.this.queryCgdLight(context, curPackage + 1, 3, result, progress, getFirstPackage);
                        return;
                    } else {
                        result.act(msg);
                        return;
                    }
                }
                result.act(null);
            }

            @Override // com.smart.message.base.IReceiveListener
            public void onTimeout() {
                result.act(null);
            }
        }).enqueue();
    }

    public void queryCgdScene(Context context, int sceneNumber, int curPackage, IAction<ResponseMsg> result) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCurPackage(curPackage);
        lightCmdParam.setSceneNum(sceneNumber);
        lightCmdParam.setCmdType(54);
        sceneSendWithResultAndPackage(context, lightCmdParam, sceneNumber, curPackage, result);
    }

    private int sceneSendWithResultAndPackage(final Context context, LightCmdParam cmdParam, final int sceneNumber, final int curPackage, final IAction<ResponseMsg> result) {
        return Injection.message().create(context).cmdParam(cmdParam).timeOutTime(30000).resendTimes(3).control(this.controlObject).receiveListener(new IReceiveListener() { // from class: com.ltech.smarthome.utils.cmd_assistant.LightAssistant.3
            @Override // com.smart.message.base.IReceiveListener
            public void onSuccess(ResponseMsg msg) {
                if (msg != null && msg.getStateCode() == 22) {
                    result.act(msg);
                    return;
                }
                if (msg != null && msg.getResData().length() >= 22) {
                    int parseInt = Integer.parseInt(msg.getResData().substring(18, 20), 16);
                    int parseInt2 = Integer.parseInt(msg.getResData().substring(20, 22), 16);
                    if (msg.getResData().length() >= 22) {
                        CgdProDataManager.getInstance().addSceneByteDataList(sceneNumber - 1, msg.getResData().substring(22));
                    }
                    if (parseInt - 1 != parseInt2) {
                        LightAssistant.this.queryCgdScene(context, sceneNumber, curPackage + 1, result);
                        return;
                    }
                    int i = sceneNumber;
                    if (i != 16) {
                        LightAssistant.this.queryCgdScene(context, i + 1, 0, result);
                        return;
                    } else {
                        result.act(msg);
                        return;
                    }
                }
                result.act(null);
            }

            @Override // com.smart.message.base.IReceiveListener
            public void onTimeout() {
                result.act(null);
            }
        }).enqueue();
    }

    public void queryDimCurve(Context context, IAction<ResponseMsg> result) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(55);
        sendWithResult(context, lightCmdParam, result);
    }

    public void queryDimRange(Context context, IAction<ResponseMsg> result) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(56);
        sendWithResult(context, lightCmdParam, result);
    }

    public void queryDimFadeTime(Context context, IAction<ResponseMsg> result) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(57);
        sendWithResult(context, lightCmdParam, result);
    }

    public void queryCtRange(Context context, IAction<ResponseMsg> result) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(58);
        sendWithResult(context, lightCmdParam, result);
    }

    public void queryFailState(Context context, IAction<ResponseMsg> result) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(59);
        sendWithResult(context, lightCmdParam, result);
    }

    public void setDimCurve(Context context, int curveType, IAction<ResponseMsg> result) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCurCurveType(curveType);
        lightCmdParam.setCmdType(60);
        sendWithResult(context, lightCmdParam, result);
    }

    public void setDimRange(Context context, int rgbLow, int rgbHigh, int ctLow, int ctHigh, IAction<ResponseMsg> result) {
        sendWithResult(context, setDimRange(rgbLow, rgbHigh, ctLow, ctHigh), result);
    }

    public LightCmdParam setDimRange(int rgbLow, int rgbHigh, int ctLow, int ctHigh) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setRgbLow(rgbLow);
        lightCmdParam.setRgbHigh(rgbHigh);
        lightCmdParam.setCtLow(ctLow);
        lightCmdParam.setCtHigh(ctHigh);
        lightCmdParam.setCmdType(61);
        return lightCmdParam;
    }

    public void setFadeTime(Context context, int fadeTimePosition, int fadeTime, int fadeTimeCount, IAction<ResponseMsg> result) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setFadeTimePosition(fadeTimePosition);
        lightCmdParam.setFadeTime(fadeTime);
        lightCmdParam.setFadeTimeCount(fadeTimeCount);
        lightCmdParam.setCmdType(62);
        sendWithResult(context, lightCmdParam, result);
    }

    public void setFailState(Context context, int state, int rgbBrt, int wyBrt, IAction<Boolean> result) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(63);
        lightCmdParam.setOnState(state);
        lightCmdParam.setRgbBrt(rgbBrt);
        lightCmdParam.setWyBrt(wyBrt);
        save(context, lightCmdParam, result);
    }

    public void setFailStateBatch(Context context, int state, int rgbBrt, int wyBrt, IAction<ResponseMsg> result) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(this.zoneNum);
        lightCmdParam.setCmdType(63);
        lightCmdParam.setOnState(state);
        lightCmdParam.setRgbBrt(rgbBrt);
        lightCmdParam.setWyBrt(wyBrt);
        sendWithResult(context, lightCmdParam, result);
    }

    public LightCmdParam sentColor(int red, int green, int blue, int rgbBrt, int wy, int wyBrt) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setCmdType(43);
        lightCmdParam.setRed(red);
        lightCmdParam.setGreen(green);
        lightCmdParam.setBlue(blue);
        lightCmdParam.setRgbBrt(rgbBrt);
        lightCmdParam.setWy(wy);
        lightCmdParam.setWyBrt(wyBrt);
        return lightCmdParam;
    }
}