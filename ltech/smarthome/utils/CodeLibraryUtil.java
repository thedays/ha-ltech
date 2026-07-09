package com.ltech.smarthome.utils;

import android.util.ArrayMap;
import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.Gson;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.response.CharSwitchBean;
import com.ltech.smarthome.net.response.CodeLibraryBean;
import com.smart.message.base.BaseCmd;
import com.smart.message.utils.StringUtils;
import com.smart.product_agreement.blegateway.CmdMeshGateway;
import com.smart.product_agreement.productBle.CmdBleFactory;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class CodeLibraryUtil {
    private static ArrayMap<String, String> codeLibraryParamMap = new ArrayMap<>();

    public static String generateAcCentralCodeLibrary(int address, int outAddr, int inAddr) {
        if (!codeLibraryParamMap.isEmpty()) {
            codeLibraryParamMap.clear();
        }
        int[] iArr = {1, 2, 4, 8};
        int[] iArr2 = {1, 2, 4};
        for (int i = 16; i <= 30; i++) {
            codeLibraryParamMap.put("温度" + i, GsonUtils.toJson(new CharSwitchBean(getAcCentralTemperatureCmdCode(address, outAddr, inAddr, i))));
        }
        codeLibraryParamMap.put("开", GsonUtils.toJson(new CharSwitchBean(getAcCentralOpenOrCloseCmdCode(address, outAddr, inAddr, true))));
        codeLibraryParamMap.put("关", GsonUtils.toJson(new CharSwitchBean(getAcCentralOpenOrCloseCmdCode(address, outAddr, inAddr, false))));
        codeLibraryParamMap.put("制冷", GsonUtils.toJson(new CharSwitchBean(getAcCentralModeCmdCode(address, outAddr, inAddr, iArr[0]))));
        codeLibraryParamMap.put("抽湿", GsonUtils.toJson(new CharSwitchBean(getAcCentralModeCmdCode(address, outAddr, inAddr, iArr[1]))));
        codeLibraryParamMap.put("送风", GsonUtils.toJson(new CharSwitchBean(getAcCentralModeCmdCode(address, outAddr, inAddr, iArr[2]))));
        codeLibraryParamMap.put("制热", GsonUtils.toJson(new CharSwitchBean(getAcCentralModeCmdCode(address, outAddr, inAddr, iArr[3]))));
        codeLibraryParamMap.put("大风", GsonUtils.toJson(new CharSwitchBean(getAcCentralWindCmdCode(address, outAddr, inAddr, iArr2[0]))));
        codeLibraryParamMap.put("中风", GsonUtils.toJson(new CharSwitchBean(getAcCentralWindCmdCode(address, outAddr, inAddr, iArr2[1]))));
        codeLibraryParamMap.put("小风", GsonUtils.toJson(new CharSwitchBean(getAcCentralWindCmdCode(address, outAddr, inAddr, iArr2[2]))));
        return GsonUtils.toJson(codeLibraryParamMap);
    }

    public static String generateFloorHeatCodeLibrary(int address, int outAddr, int inAddr) {
        if (!codeLibraryParamMap.isEmpty()) {
            codeLibraryParamMap.clear();
        }
        for (int i = 5; i <= 90; i++) {
            codeLibraryParamMap.put("温度" + i, GsonUtils.toJson(new CharSwitchBean(getFloorHeatTemperatureCmdCode(address, outAddr, inAddr, i))));
        }
        codeLibraryParamMap.put("开", GsonUtils.toJson(new CharSwitchBean(getFloorHeatOpenOrCloseCmdCode(address, outAddr, inAddr, true))));
        codeLibraryParamMap.put("关", GsonUtils.toJson(new CharSwitchBean(getFloorHeatOpenOrCloseCmdCode(address, outAddr, inAddr, false))));
        return GsonUtils.toJson(codeLibraryParamMap);
    }

    public static String generateFreshAirCodeLibrary(int address, int outAddr, int inAddr) {
        if (!codeLibraryParamMap.isEmpty()) {
            codeLibraryParamMap.clear();
        }
        int[] iArr = {0, 1, 2, 4};
        codeLibraryParamMap.put("开", GsonUtils.toJson(new CharSwitchBean(getFreshAirOpenOrCloseCmdCode(address, outAddr, inAddr, true))));
        codeLibraryParamMap.put("关", GsonUtils.toJson(new CharSwitchBean(getFreshAirOpenOrCloseCmdCode(address, outAddr, inAddr, false))));
        codeLibraryParamMap.put("自动风", GsonUtils.toJson(new CharSwitchBean(getFreshAirWindCmdCode(address, outAddr, inAddr, iArr[0]))));
        codeLibraryParamMap.put("大风", GsonUtils.toJson(new CharSwitchBean(getFreshAirWindCmdCode(address, outAddr, inAddr, iArr[1]))));
        codeLibraryParamMap.put("中风", GsonUtils.toJson(new CharSwitchBean(getFreshAirWindCmdCode(address, outAddr, inAddr, iArr[2]))));
        codeLibraryParamMap.put("小风", GsonUtils.toJson(new CharSwitchBean(getFreshAirWindCmdCode(address, outAddr, inAddr, iArr[3]))));
        return GsonUtils.toJson(codeLibraryParamMap);
    }

    public static String generateG4teCodeLibrary(int address, int subType) {
        if (!codeLibraryParamMap.isEmpty()) {
            codeLibraryParamMap.clear();
        }
        int[] iArr = {1, 2, 4, 8};
        int[] iArr2 = {1, 2, 4};
        if (subType == 1 || subType == 3) {
            for (int i = 16; i <= 30; i++) {
                codeLibraryParamMap.put("空调温度" + i, GsonUtils.toJson(new CharSwitchBean(getAcCentralTemperatureCmdCode(address, 0, 0, i))));
            }
            codeLibraryParamMap.put("空调开", GsonUtils.toJson(new CharSwitchBean(getAcCentralOpenOrCloseCmdCode(address, 0, 0, true))));
            codeLibraryParamMap.put("空调关", GsonUtils.toJson(new CharSwitchBean(getAcCentralOpenOrCloseCmdCode(address, 0, 0, false))));
            codeLibraryParamMap.put("空调制冷", GsonUtils.toJson(new CharSwitchBean(getAcCentralModeCmdCode(address, 0, 0, iArr[0]))));
            codeLibraryParamMap.put("空调抽湿", GsonUtils.toJson(new CharSwitchBean(getAcCentralModeCmdCode(address, 0, 0, iArr[1]))));
            codeLibraryParamMap.put("空调送风", GsonUtils.toJson(new CharSwitchBean(getAcCentralModeCmdCode(address, 0, 0, iArr[2]))));
            codeLibraryParamMap.put("空调制热", GsonUtils.toJson(new CharSwitchBean(getAcCentralModeCmdCode(address, 0, 0, iArr[3]))));
            codeLibraryParamMap.put("空调大风", GsonUtils.toJson(new CharSwitchBean(getAcCentralWindCmdCode(address, 0, 0, iArr2[0]))));
            codeLibraryParamMap.put("空调中风", GsonUtils.toJson(new CharSwitchBean(getAcCentralWindCmdCode(address, 0, 0, iArr2[1]))));
            codeLibraryParamMap.put("空调小风", GsonUtils.toJson(new CharSwitchBean(getAcCentralWindCmdCode(address, 0, 0, iArr2[2]))));
        }
        if (subType == 2 || subType == 3) {
            int[] iArr3 = {0, 1, 2, 4};
            codeLibraryParamMap.put("新风开", GsonUtils.toJson(new CharSwitchBean(getFreshAirOpenOrCloseCmdCode(address, 0, 0, true))));
            codeLibraryParamMap.put("新风关", GsonUtils.toJson(new CharSwitchBean(getFreshAirOpenOrCloseCmdCode(address, 0, 0, false))));
            codeLibraryParamMap.put("新风自动风", GsonUtils.toJson(new CharSwitchBean(getFreshAirWindCmdCode(address, 0, 0, iArr3[0]))));
            codeLibraryParamMap.put("新风大风", GsonUtils.toJson(new CharSwitchBean(getFreshAirWindCmdCode(address, 0, 0, iArr3[1]))));
            codeLibraryParamMap.put("新风中风", GsonUtils.toJson(new CharSwitchBean(getFreshAirWindCmdCode(address, 0, 0, iArr3[2]))));
            codeLibraryParamMap.put("新风小风", GsonUtils.toJson(new CharSwitchBean(getFreshAirWindCmdCode(address, 0, 0, iArr3[3]))));
        }
        return GsonUtils.toJson(codeLibraryParamMap);
    }

    private static String getAcCentralWindCmdCode(int address, int outAddr, int inAddr, int windSpeed) {
        BaseCmd changeWindSpeed = CmdBleFactory.changeWindSpeed(windSpeed, outAddr, inAddr);
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(changeWindSpeed.getFunCode()));
        for (byte b2 : changeWindSpeed.value(new Object[0])) {
            arrayList.add(Integer.valueOf(b2));
        }
        return StringUtils.byte2Str(Injection.iot().connectAndSendData3(address, new CmdMeshGateway(243, arrayList).value(new Object[0])));
    }

    private static String getFreshAirWindCmdCode(int address, int outAddr, int inAddr, int windSpeed) {
        BaseCmd freshAirChangeWindSpeed = CmdBleFactory.freshAirChangeWindSpeed(windSpeed, outAddr, inAddr);
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(freshAirChangeWindSpeed.getFunCode()));
        for (byte b2 : freshAirChangeWindSpeed.value(new Object[0])) {
            arrayList.add(Integer.valueOf(b2));
        }
        return StringUtils.byte2Str(Injection.iot().connectAndSendData3(address, new CmdMeshGateway(243, arrayList).value(new Object[0])));
    }

    private static String getAcCentralModeCmdCode(int address, int outAddr, int inAddr, int mode) {
        BaseCmd changeMode = CmdBleFactory.changeMode(mode, outAddr, inAddr);
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(changeMode.getFunCode()));
        for (byte b2 : changeMode.value(new Object[0])) {
            arrayList.add(Integer.valueOf(b2));
        }
        return StringUtils.byte2Str(Injection.iot().connectAndSendData3(address, new CmdMeshGateway(243, arrayList).value(new Object[0])));
    }

    private static String getAcCentralOpenOrCloseCmdCode(int address, int outAddr, int inAddr, boolean open) {
        BaseCmd openOrCloseCentralAir = CmdBleFactory.openOrCloseCentralAir(open, outAddr, inAddr);
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(openOrCloseCentralAir.getFunCode()));
        for (byte b2 : openOrCloseCentralAir.value(new Object[0])) {
            arrayList.add(Integer.valueOf(b2));
        }
        return StringUtils.byte2Str(Injection.iot().connectAndSendData3(address, new CmdMeshGateway(243, arrayList).value(new Object[0])));
    }

    private static String getFreshAirOpenOrCloseCmdCode(int address, int outAddr, int inAddr, boolean open) {
        BaseCmd openOrCloseFreshAir = CmdBleFactory.openOrCloseFreshAir(open, outAddr, inAddr);
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(openOrCloseFreshAir.getFunCode()));
        for (byte b2 : openOrCloseFreshAir.value(new Object[0])) {
            arrayList.add(Integer.valueOf(b2));
        }
        return StringUtils.byte2Str(Injection.iot().connectAndSendData3(address, new CmdMeshGateway(243, arrayList).value(new Object[0])));
    }

    private static String getFloorHeatOpenOrCloseCmdCode(int address, int outAddr, int inAddr, boolean open) {
        BaseCmd openOrCloseFloorHeat = CmdBleFactory.openOrCloseFloorHeat(open, outAddr, inAddr);
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(openOrCloseFloorHeat.getFunCode()));
        for (byte b2 : openOrCloseFloorHeat.value(new Object[0])) {
            arrayList.add(Integer.valueOf(b2));
        }
        return StringUtils.byte2Str(Injection.iot().connectAndSendData3(address, new CmdMeshGateway(243, arrayList).value(new Object[0])));
    }

    private static String getAcCentralTemperatureCmdCode(int address, int outAddr, int inAddr, int temperature) {
        BaseCmd changeTemperature = CmdBleFactory.changeTemperature(temperature, outAddr, inAddr);
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(changeTemperature.getFunCode()));
        for (byte b2 : changeTemperature.value(new Object[0])) {
            arrayList.add(Integer.valueOf(b2));
        }
        return StringUtils.byte2Str(Injection.iot().connectAndSendData3(address, new CmdMeshGateway(243, arrayList).value(new Object[0])));
    }

    private static String getFloorHeatTemperatureCmdCode(int address, int outAddr, int inAddr, int temperature) {
        BaseCmd floorHeatChangeTemp = CmdBleFactory.floorHeatChangeTemp(temperature, outAddr, inAddr);
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(floorHeatChangeTemp.getFunCode()));
        for (byte b2 : floorHeatChangeTemp.value(new Object[0])) {
            arrayList.add(Integer.valueOf(b2));
        }
        return StringUtils.byte2Str(Injection.iot().connectAndSendData3(address, new CmdMeshGateway(243, arrayList).value(new Object[0])));
    }

    public static String generateCurtainCodeLibrary(int address) {
        if (!codeLibraryParamMap.isEmpty()) {
            codeLibraryParamMap.clear();
        }
        for (int i = 0; i <= 100; i += 10) {
            codeLibraryParamMap.put("位置" + i, GsonUtils.toJson(new CharSwitchBean(getCurtainPercentCommandCode(i, address))));
        }
        codeLibraryParamMap.put("开", GsonUtils.toJson(new CharSwitchBean(getCurtainOpenCommandCode(address))));
        codeLibraryParamMap.put("关", GsonUtils.toJson(new CharSwitchBean(getCurtainCloseCommandCode(address))));
        codeLibraryParamMap.put("停止", GsonUtils.toJson(new CharSwitchBean(getCurtainStopCommandCode(address))));
        return GsonUtils.toJson(codeLibraryParamMap);
    }

    public static String generateDefaultTrigCurCodeLibrary(int address, int subType) {
        if (!codeLibraryParamMap.isEmpty()) {
            codeLibraryParamMap.clear();
        }
        if (subType == 0) {
            codeLibraryParamMap.put("开", GsonUtils.toJson(new CharSwitchBean(getTrigCurCommand(1, address))));
            codeLibraryParamMap.put("关", GsonUtils.toJson(new CharSwitchBean(getTrigCurCommand(4, address))));
            codeLibraryParamMap.put("停止", GsonUtils.toJson(new CharSwitchBean(getTrigCurCommand(2, address))));
        } else {
            codeLibraryParamMap.put("开", GsonUtils.toJson(new CharSwitchBean(getTrigCurCommand(1, address))));
            codeLibraryParamMap.put("关", GsonUtils.toJson(new CharSwitchBean(getTrigCurCommand(2, address))));
            codeLibraryParamMap.put("停止", GsonUtils.toJson(new CharSwitchBean(getTrigCurCommand(3, address))));
            codeLibraryParamMap.put("左转向", GsonUtils.toJson(new CharSwitchBean(getTrigCurCommand(4, address))));
            codeLibraryParamMap.put("暂停转向", GsonUtils.toJson(new CharSwitchBean(getTrigCurCommand(12, address))));
            codeLibraryParamMap.put("右转向", GsonUtils.toJson(new CharSwitchBean(getTrigCurCommand(8, address))));
        }
        return GsonUtils.toJson(codeLibraryParamMap);
    }

    public static String generateTrigCurCodeLibrary(int[] channels, int address) {
        if (!codeLibraryParamMap.isEmpty()) {
            codeLibraryParamMap.clear();
        }
        codeLibraryParamMap.put("开", GsonUtils.toJson(new CharSwitchBean(getTrigCurCommand(channels[0], address))));
        codeLibraryParamMap.put("关", GsonUtils.toJson(new CharSwitchBean(getTrigCurCommand(channels[1], address))));
        codeLibraryParamMap.put("停止", GsonUtils.toJson(new CharSwitchBean(getTrigCurCommand(channels[2], address))));
        return GsonUtils.toJson(codeLibraryParamMap);
    }

    public static String generateBleSwitchtCodeLibrary(int address) {
        if (!codeLibraryParamMap.isEmpty()) {
            codeLibraryParamMap.clear();
        }
        codeLibraryParamMap.put("开", GsonUtils.toJson(new CharSwitchBean(getOnoff(true, address))));
        codeLibraryParamMap.put("关", GsonUtils.toJson(new CharSwitchBean(getOnoff(false, address))));
        return GsonUtils.toJson(codeLibraryParamMap);
    }

    public static String getTrigCurCommand(int channel, int address) {
        BaseCmd trigOpenCloseValue = CmdBleFactory.setTrigOpenCloseValue(1, channel);
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(trigOpenCloseValue.getFunCode()));
        for (byte b2 : trigOpenCloseValue.value(Integer.valueOf(address))) {
            arrayList.add(Integer.valueOf(b2));
        }
        return StringUtils.byte2Str(Injection.iot().connectAndSendData3(address, new CmdMeshGateway(243, arrayList).value(new Object[0])));
    }

    public static String getCurtainPercentCommandCode(int progress, int address) {
        BaseCmd controlPercent = CmdBleFactory.controlPercent(1, progress);
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(controlPercent.getFunCode()));
        for (byte b2 : controlPercent.value(Integer.valueOf(address))) {
            arrayList.add(Integer.valueOf(b2));
        }
        return StringUtils.byte2Str(Injection.iot().connectAndSendData3(address, new CmdMeshGateway(243, arrayList).value(new Object[0])));
    }

    public static String getCurtainOpenCommandCode(int address) {
        BaseCmd openCurtain = CmdBleFactory.openCurtain(1);
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(openCurtain.getFunCode()));
        for (byte b2 : openCurtain.value(Integer.valueOf(address))) {
            arrayList.add(Integer.valueOf(b2));
        }
        return StringUtils.byte2Str(Injection.iot().connectAndSendData3(address, new CmdMeshGateway(243, arrayList).value(new Object[0])));
    }

    public static String getCurtainCloseCommandCode(int address) {
        BaseCmd closeCurtain = CmdBleFactory.closeCurtain(1);
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(closeCurtain.getFunCode()));
        for (byte b2 : closeCurtain.value(Integer.valueOf(address))) {
            arrayList.add(Integer.valueOf(b2));
        }
        return StringUtils.byte2Str(Injection.iot().connectAndSendData3(address, new CmdMeshGateway(243, arrayList).value(new Object[0])));
    }

    public static String getCurtainStopCommandCode(int address) {
        BaseCmd stopCurtain = CmdBleFactory.stopCurtain(1);
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(stopCurtain.getFunCode()));
        for (byte b2 : stopCurtain.value(Integer.valueOf(address))) {
            arrayList.add(Integer.valueOf(b2));
        }
        return StringUtils.byte2Str(Injection.iot().connectAndSendData3(address, new CmdMeshGateway(243, arrayList).value(new Object[0])));
    }

    public static String getSmartPanelCodeLibrary(int unicastAddress, String productId) {
        int i;
        productId.hashCode();
        i = 4;
        switch (productId) {
            case "122041818260301":
            case "221042516351701":
                i = 1;
                break;
            case "122041818283501":
            case "3895993722014848":
            case "123050811353501":
            case "122042815485901":
            case "122080911090801":
            case "121052512023201":
            case "121042516345401":
                i = 2;
                break;
            case "122041818304701":
            case "3683369128495808":
            case "4249823578721536":
            case "121042516340801":
                i = 3;
                break;
            case "123050811340901":
            case "3683388245101248":
            case "3701704216101056":
            case "123072510445601":
            case "221030816330401":
            case "3701703750123712":
            case "3721596935046208":
            case "3959367613661440":
                break;
            default:
                i = 0;
                break;
        }
        return generateDefaultSwitchPanelCodeLibrary(unicastAddress, i);
    }

    public static String generateDefaultSwitchPanelCodeLibrary(int address, int switchNum) {
        if (switchNum == 0) {
            return "{}";
        }
        if (!codeLibraryParamMap.isEmpty()) {
            codeLibraryParamMap.clear();
        }
        int i = 0;
        while (i < switchNum) {
            int i2 = i + 1;
            codeLibraryParamMap.put("打开开关" + i2, GsonUtils.toJson(new CharSwitchBean(getOnoff(true, address, i))));
            codeLibraryParamMap.put("关闭开关" + i2, GsonUtils.toJson(new CharSwitchBean(getOnoff(false, address, i))));
            i = i2;
        }
        return GsonUtils.toJson(codeLibraryParamMap);
    }

    private static String getOnoff(boolean onoff, int address, int pos) {
        BaseCmd onOff;
        if (pos == -1) {
            onOff = CmdBleFactory.setOnOff(1, onoff);
        } else {
            onOff = CmdBleFactory.setOnOff(1 << pos, onoff);
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(onOff.getFunCode()));
        for (byte b2 : onOff.value(Integer.valueOf(address))) {
            arrayList.add(Integer.valueOf(b2));
        }
        return StringUtils.byte2Str(Injection.iot().connectAndSendData3(address, new CmdMeshGateway(243, arrayList).value(new Object[0])));
    }

    private static String getOnoff(boolean onoff, int address) {
        return getOnoff(onoff, address, -1);
    }

    public static String generateLightcodeLibraryData(int address, int DeviceType) {
        Gson gson = new Gson();
        CodeLibraryBean codeLibraryBean = new CodeLibraryBean();
        if (DeviceType == 1) {
            codeLibraryBean.m607set(gson.toJson(new CharSwitchBean(getOnoff(false, address))));
            codeLibraryBean.m608set(gson.toJson(new CharSwitchBean(getOnoff(true, address))));
            codeLibraryBean.m596set0(gson.toJson(new CharSwitchBean(getbrightnessE0(1, address))));
            codeLibraryBean.m597set10(gson.toJson(new CharSwitchBean(getbrightnessE0(LightUtils.progress2BrtHasBelowOne(10), address))));
            codeLibraryBean.m599set20(gson.toJson(new CharSwitchBean(getbrightnessE0(LightUtils.progress2BrtHasBelowOne(20), address))));
            codeLibraryBean.m600set30(gson.toJson(new CharSwitchBean(getbrightnessE0(LightUtils.progress2BrtHasBelowOne(30), address))));
            codeLibraryBean.m601set40(gson.toJson(new CharSwitchBean(getbrightnessE0(LightUtils.progress2BrtHasBelowOne(40), address))));
            codeLibraryBean.m602set50(gson.toJson(new CharSwitchBean(getbrightnessE0(LightUtils.progress2BrtHasBelowOne(50), address))));
            codeLibraryBean.m603set60(gson.toJson(new CharSwitchBean(getbrightnessE0(LightUtils.progress2BrtHasBelowOne(60), address))));
            codeLibraryBean.m604set70(gson.toJson(new CharSwitchBean(getbrightnessE0(LightUtils.progress2BrtHasBelowOne(70), address))));
            codeLibraryBean.m605set80(gson.toJson(new CharSwitchBean(getbrightnessE0(LightUtils.progress2BrtHasBelowOne(80), address))));
            codeLibraryBean.m606set90(gson.toJson(new CharSwitchBean(getbrightnessE0(LightUtils.progress2BrtHasBelowOne(90), address))));
            codeLibraryBean.m598set100(gson.toJson(new CharSwitchBean(getbrightnessE0(LightUtils.progress2BrtHasBelowOne(100), address))));
        } else if (DeviceType == 2) {
            codeLibraryBean.m607set(gson.toJson(new CharSwitchBean(getOnoff(false, address))));
            codeLibraryBean.m608set(gson.toJson(new CharSwitchBean(getOnoff(true, address))));
            codeLibraryBean.m596set0(gson.toJson(new CharSwitchBean(getbrightnessDF(1, address))));
            codeLibraryBean.m597set10(gson.toJson(new CharSwitchBean(getbrightnessDF(LightUtils.progress2BrtHasBelowOne(10), address))));
            codeLibraryBean.m599set20(gson.toJson(new CharSwitchBean(getbrightnessDF(LightUtils.progress2BrtHasBelowOne(20), address))));
            codeLibraryBean.m600set30(gson.toJson(new CharSwitchBean(getbrightnessDF(LightUtils.progress2BrtHasBelowOne(30), address))));
            codeLibraryBean.m601set40(gson.toJson(new CharSwitchBean(getbrightnessDF(LightUtils.progress2BrtHasBelowOne(40), address))));
            codeLibraryBean.m602set50(gson.toJson(new CharSwitchBean(getbrightnessDF(LightUtils.progress2BrtHasBelowOne(50), address))));
            codeLibraryBean.m603set60(gson.toJson(new CharSwitchBean(getbrightnessDF(LightUtils.progress2BrtHasBelowOne(60), address))));
            codeLibraryBean.m604set70(gson.toJson(new CharSwitchBean(getbrightnessDF(LightUtils.progress2BrtHasBelowOne(70), address))));
            codeLibraryBean.m605set80(gson.toJson(new CharSwitchBean(getbrightnessDF(LightUtils.progress2BrtHasBelowOne(80), address))));
            codeLibraryBean.m606set90(gson.toJson(new CharSwitchBean(getbrightnessDF(LightUtils.progress2BrtHasBelowOne(90), address))));
            codeLibraryBean.m598set100(gson.toJson(new CharSwitchBean(getbrightnessDF(LightUtils.progress2BrtHasBelowOne(100), address))));
            codeLibraryBean.m610set(gson.toJson(new CharSwitchBean(getColorDE(255, 0, address))));
            codeLibraryBean.m616set(gson.toJson(new CharSwitchBean(getColorDE(0, 255, address))));
        } else if (DeviceType == 3 || DeviceType == 4 || DeviceType == 5 || DeviceType == 17 || DeviceType == 20) {
            codeLibraryBean.m607set(gson.toJson(new CharSwitchBean(getOnoff(false, address))));
            codeLibraryBean.m608set(gson.toJson(new CharSwitchBean(getOnoff(true, address))));
            codeLibraryBean.m596set0(gson.toJson(new CharSwitchBean(getbrightness(1, address))));
            codeLibraryBean.m597set10(gson.toJson(new CharSwitchBean(getbrightness(LightUtils.progress2BrtHasBelowOne(10), address))));
            codeLibraryBean.m599set20(gson.toJson(new CharSwitchBean(getbrightness(LightUtils.progress2BrtHasBelowOne(20), address))));
            codeLibraryBean.m600set30(gson.toJson(new CharSwitchBean(getbrightness(LightUtils.progress2BrtHasBelowOne(30), address))));
            codeLibraryBean.m601set40(gson.toJson(new CharSwitchBean(getbrightness(LightUtils.progress2BrtHasBelowOne(40), address))));
            codeLibraryBean.m602set50(gson.toJson(new CharSwitchBean(getbrightness(LightUtils.progress2BrtHasBelowOne(50), address))));
            codeLibraryBean.m603set60(gson.toJson(new CharSwitchBean(getbrightness(LightUtils.progress2BrtHasBelowOne(60), address))));
            codeLibraryBean.m604set70(gson.toJson(new CharSwitchBean(getbrightness(LightUtils.progress2BrtHasBelowOne(70), address))));
            codeLibraryBean.m605set80(gson.toJson(new CharSwitchBean(getbrightness(LightUtils.progress2BrtHasBelowOne(80), address))));
            codeLibraryBean.m606set90(gson.toJson(new CharSwitchBean(getbrightness(LightUtils.progress2BrtHasBelowOne(90), address))));
            codeLibraryBean.m598set100(gson.toJson(new CharSwitchBean(getbrightness(LightUtils.progress2BrtHasBelowOne(100), address))));
            codeLibraryBean.m612set(gson.toJson(new CharSwitchBean(getColor(255, 0, 0, address))));
            codeLibraryBean.m609set(gson.toJson(new CharSwitchBean(getColor(255, 80, 0, address))));
            codeLibraryBean.m616set(gson.toJson(new CharSwitchBean(getColor(255, 255, 0, address))));
            codeLibraryBean.m613set(gson.toJson(new CharSwitchBean(getColor(0, 255, 0, address))));
            codeLibraryBean.m615set(gson.toJson(new CharSwitchBean(getColor(0, 255, 255, address))));
            codeLibraryBean.m614set(gson.toJson(new CharSwitchBean(getColor(0, 0, 255, address))));
            codeLibraryBean.m611set(gson.toJson(new CharSwitchBean(getColor(160, 32, 240, address))));
            codeLibraryBean.m610set(gson.toJson(new CharSwitchBean(getColor(255, 255, 255, address))));
        }
        return gson.toJson(codeLibraryBean);
    }

    private static String getbrightness(int brig, int address) {
        BaseCmd wdd = CmdBleFactory.setWDD(1, brig);
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(wdd.getFunCode()));
        for (byte b2 : wdd.value(Integer.valueOf(address))) {
            arrayList.add(Integer.valueOf(b2));
        }
        return StringUtils.byte2Str(Injection.iot().connectAndSendData3(address, new CmdMeshGateway(243, arrayList).value(new Object[0])));
    }

    private static String getColor(int R, int G, int B, int address) {
        BaseCmd wdc = CmdBleFactory.setWDC(1, R, G, B);
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(wdc.getFunCode()));
        for (byte b2 : wdc.value(Integer.valueOf(address))) {
            arrayList.add(Integer.valueOf(b2));
        }
        return StringUtils.byte2Str(Injection.iot().connectAndSendData3(address, new CmdMeshGateway(243, arrayList).value(new Object[0])));
    }

    private static String getColorDE(int CW, int TY, int address) {
        BaseCmd wde = CmdBleFactory.setWDE(1, CW, TY);
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(wde.getFunCode()));
        for (byte b2 : wde.value(Integer.valueOf(address))) {
            arrayList.add(Integer.valueOf(b2));
        }
        return StringUtils.byte2Str(Injection.iot().connectAndSendData3(address, new CmdMeshGateway(243, arrayList).value(new Object[0])));
    }

    private static String getbrightnessDF(int brig, int address) {
        BaseCmd wdf = CmdBleFactory.setWDF(1, brig);
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(wdf.getFunCode()));
        for (byte b2 : wdf.value(Integer.valueOf(address))) {
            arrayList.add(Integer.valueOf(b2));
        }
        return StringUtils.byte2Str(Injection.iot().connectAndSendData3(address, new CmdMeshGateway(243, arrayList).value(new Object[0])));
    }

    private static String getbrightnessE0(int brig, int address) {
        BaseCmd we0 = CmdBleFactory.setWE0(1, brig);
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(we0.getFunCode()));
        for (byte b2 : we0.value(Integer.valueOf(address))) {
            arrayList.add(Integer.valueOf(b2));
        }
        return StringUtils.byte2Str(Injection.iot().connectAndSendData3(address, new CmdMeshGateway(243, arrayList).value(new Object[0])));
    }
}