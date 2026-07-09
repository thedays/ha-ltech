package com.ltech.smarthome.utils.cmd_assistant;

import android.content.Context;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.smart.message.ResponseMsg;
import com.smart.message.base.BaseCmd;
import com.smart.message.base.BaseCtrlPackage;
import com.smart.message.base.IReceiveListener;
import com.smart.product_agreement.param.BleNetworkCmdParam;
import com.smart.product_agreement.param.DeviceCmdParam;
import com.smart.product_agreement.param.MicrowaveParam;
import com.smart.product_agreement.param.Rs485CmdParam;
import com.smart.product_agreement.param.SmartPanelParam;
import com.smart.product_agreement.param.SuperPanelCmdParam;
import com.smart.product_agreement.param.TrigCmdParam;
import com.smart.product_agreement.param.TrigToBleCmdParam;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class DeviceAssistant extends BaseAssistant {
    public void controlCurtain(Context context, int cmdKey) {
        DeviceCmdParam deviceCmdParam = new DeviceCmdParam();
        deviceCmdParam.setZoneNum(this.zoneNum);
        deviceCmdParam.setCmdType(cmdKey);
        sendOnOffWithoutResult(context, deviceCmdParam, new int[0]);
    }

    public void sendCurtainMode(Context context, int cmdKey, int modeKey) {
        DeviceCmdParam deviceCmdParam = new DeviceCmdParam();
        deviceCmdParam.setZoneNum(this.zoneNum);
        deviceCmdParam.setCmdType(cmdKey);
        deviceCmdParam.setCurtainModeType(modeKey);
        sendOnOffWithoutResult(context, deviceCmdParam, new int[0]);
    }

    public void controlCurtainWithPercent(Context context, int cmdKey, int progress) {
        DeviceCmdParam deviceCmdParam = new DeviceCmdParam();
        deviceCmdParam.setZoneNum(this.zoneNum);
        deviceCmdParam.setCmdType(cmdKey);
        deviceCmdParam.setCurtainProgress(progress);
        sendWithoutResult(context, deviceCmdParam);
    }

    public void setOpenCloseVar(Context context, int openCloseValue) {
        sendWithoutResult(context, setOpenCloseVar(openCloseValue));
    }

    public TrigCmdParam setOpenCloseVar(int openCloseValue) {
        TrigCmdParam trigCmdParam = new TrigCmdParam();
        trigCmdParam.setZoneNum(this.zoneNum);
        trigCmdParam.setCmdType(1);
        trigCmdParam.setOpenCloseVar(openCloseValue);
        return trigCmdParam;
    }

    public void remoteControlLearning(Context context, final IAction<Boolean> result) {
        DeviceCmdParam deviceCmdParam = new DeviceCmdParam();
        deviceCmdParam.setZoneNum(this.zoneNum);
        deviceCmdParam.setCmdType(13);
        Injection.message().create(context).cmdParam(deviceCmdParam).timeOutTime(10000).resendTimes(0).control(this.controlObject).receiveListener(new IReceiveListener(this) { // from class: com.ltech.smarthome.utils.cmd_assistant.DeviceAssistant.1
            @Override // com.smart.message.base.IReceiveListener
            public void onSuccess(ResponseMsg msg) {
                result.act(Boolean.valueOf(msg != null));
            }

            @Override // com.smart.message.base.IReceiveListener
            public void onTimeout() {
                result.act(false);
            }
        }).enqueue();
    }

    public void channelMapping(Context context, int action, int channel, IAction<ResponseMsg> result) {
        sendWithResult(context, channelMapping(action, channel), result);
    }

    public TrigCmdParam channelMapping(int action, int channel) {
        TrigCmdParam trigCmdParam = new TrigCmdParam();
        trigCmdParam.setZoneNum(this.zoneNum);
        trigCmdParam.setCmdType(2);
        trigCmdParam.setAction(action);
        trigCmdParam.setChannel(channel);
        return trigCmdParam;
    }

    public void queryChannelMapping(Context context, IAction<ResponseMsg> result) {
        TrigCmdParam trigCmdParam = new TrigCmdParam();
        trigCmdParam.setZoneNum(this.zoneNum);
        trigCmdParam.setCmdType(3);
        sendWithResult(context, trigCmdParam, result);
    }

    public void channelControlMode(Context context, int mode, IAction<ResponseMsg> result) {
        sendWithResult(context, channelControlMode(mode), result);
    }

    public TrigCmdParam channelControlMode(int mode) {
        TrigCmdParam trigCmdParam = new TrigCmdParam();
        trigCmdParam.setZoneNum(this.zoneNum);
        trigCmdParam.setCmdType(4);
        trigCmdParam.setControlMode(mode);
        return trigCmdParam;
    }

    public void queryControlMode(Context context, IAction<ResponseMsg> result) {
        TrigCmdParam trigCmdParam = new TrigCmdParam();
        trigCmdParam.setZoneNum(this.zoneNum);
        trigCmdParam.setCmdType(5);
        sendWithResult(context, trigCmdParam, result);
    }

    public void setTrigToBleData(Context context, int index, String data, int address, boolean isCancel, int zoom, IAction<ResponseMsg> result) {
        sendWithResult(context, setTrigToBleData(index, data, address, isCancel, zoom), result);
    }

    public TrigToBleCmdParam setTrigToBleData(int index, String data, int address, boolean isCancel, int zone) {
        TrigToBleCmdParam trigToBleCmdParam = new TrigToBleCmdParam();
        trigToBleCmdParam.setZoneNum(this.zoneNum);
        trigToBleCmdParam.setCmdType(1);
        trigToBleCmdParam.setChannel(index);
        trigToBleCmdParam.setAction(data);
        trigToBleCmdParam.setAddress(address);
        trigToBleCmdParam.setCancel(isCancel);
        trigToBleCmdParam.setZone(zone);
        return trigToBleCmdParam;
    }

    public void setPanelScreenData(Context context, int position, int firstType, byte[] firstData, int secondType, byte[] secondData, IAction<ResponseMsg> result) {
        sendWithResult(context, setPanelScreenData(position, firstType, firstData, secondType, secondData), result);
    }

    public SmartPanelParam setPanelScreenData(int position, int firstType, byte[] firstData, int secondType, byte[] secondData) {
        SmartPanelParam smartPanelParam = new SmartPanelParam();
        smartPanelParam.setZoneNum(1 << position);
        smartPanelParam.setCmdType(2);
        smartPanelParam.setDisplayParam(new SmartPanelParam.DisplayParam());
        smartPanelParam.getDisplayParam().setFirstType(firstType);
        smartPanelParam.getDisplayParam().setFirstData(firstData);
        if (secondData == null && (ProductRepository.getLightColorType(this.controlObject) == 19 || ProductRepository.getLightColorType(this.controlObject) == 21)) {
            smartPanelParam.getDisplayParam().setSecondType(0);
        } else {
            smartPanelParam.getDisplayParam().setSecondType(secondType);
        }
        smartPanelParam.getDisplayParam().setSecondData(secondData);
        return smartPanelParam;
    }

    public void setPanelScreenData(Context context, int position, String content, IAction<ResponseMsg> result) {
        sendWithResult(context, setPanelScreenData(position, content), result);
    }

    public void setPanelScreenLongData(Context context, int position, String content, IAction<ResponseMsg> result) {
        UnsupportedEncodingException unsupportedEncodingException;
        byte[] bArr;
        String[] split = content.split("\n");
        byte[] bArr2 = null;
        try {
            bArr = split[0].getBytes("gb2312");
        } catch (UnsupportedEncodingException e) {
            unsupportedEncodingException = e;
            bArr = null;
        }
        try {
            if (split.length > 1) {
                bArr2 = split[1].getBytes("gb2312");
            }
        } catch (UnsupportedEncodingException e2) {
            unsupportedEncodingException = e2;
            unsupportedEncodingException.printStackTrace();
            sendWithResult(context, setPanelScreenData(position, 3, bArr, 3, bArr2), result);
        }
        sendWithResult(context, setPanelScreenData(position, 3, bArr, 3, bArr2), result);
    }

    public SmartPanelParam setPanelScreenLongData(int position, String content) {
        UnsupportedEncodingException unsupportedEncodingException;
        byte[] bArr;
        String[] split = content.split("\n");
        byte[] bArr2 = null;
        try {
            bArr = split[0].getBytes("gb2312");
        } catch (UnsupportedEncodingException e) {
            unsupportedEncodingException = e;
            bArr = null;
        }
        try {
            if (split.length > 1) {
                bArr2 = split[1].getBytes("gb2312");
            }
        } catch (UnsupportedEncodingException e2) {
            unsupportedEncodingException = e2;
            unsupportedEncodingException.printStackTrace();
            return setPanelScreenData(position, 3, bArr, 3, bArr2);
        }
        return setPanelScreenData(position, 3, bArr, 3, bArr2);
    }

    public SmartPanelParam setPanelScreenData(int position, String content) {
        byte[] bArr;
        SmartPanelParam smartPanelParam;
        String[] split = content.split("\n");
        byte[] bArr2 = null;
        try {
            bArr = split[0].getBytes("gb2312");
        } catch (UnsupportedEncodingException e) {
            e = e;
            bArr = null;
        }
        try {
            if (split.length > 1) {
                bArr2 = split[1].getBytes("gb2312");
            }
        } catch (UnsupportedEncodingException e2) {
            e = e2;
            e.printStackTrace();
            smartPanelParam = new SmartPanelParam();
            smartPanelParam.setZoneNum(1 << position);
            smartPanelParam.setCmdType(2);
            smartPanelParam.setDisplayParam(new SmartPanelParam.DisplayParam());
            smartPanelParam.getDisplayParam().setFirstType(1);
            smartPanelParam.getDisplayParam().setFirstData(bArr);
            if (bArr2 != null) {
            }
            smartPanelParam.getDisplayParam().setSecondType(1);
            smartPanelParam.getDisplayParam().setSecondData(bArr2);
            return smartPanelParam;
        }
        smartPanelParam = new SmartPanelParam();
        smartPanelParam.setZoneNum(1 << position);
        smartPanelParam.setCmdType(2);
        smartPanelParam.setDisplayParam(new SmartPanelParam.DisplayParam());
        smartPanelParam.getDisplayParam().setFirstType(1);
        smartPanelParam.getDisplayParam().setFirstData(bArr);
        if ((bArr2 != null && ProductRepository.getLightColorType(this.controlObject) == 19) || ProductRepository.getLightColorType(this.controlObject) == 21) {
            smartPanelParam.getDisplayParam().setSecondType(0);
        } else {
            smartPanelParam.getDisplayParam().setSecondType(1);
        }
        smartPanelParam.getDisplayParam().setSecondData(bArr2);
        return smartPanelParam;
    }

    public void setPanelScreenData(Context context, List<SmartPanelParam.DisplayParam> displayParamList, IAction<ResponseMsg> result) {
        SmartPanelParam smartPanelParam = new SmartPanelParam();
        smartPanelParam.setCmdType(7);
        smartPanelParam.setDisplayParamList(displayParamList);
        sendWithResult(context, smartPanelParam, result);
    }

    public void setPanelScreenLongClickData(Context context, List<SmartPanelParam.DisplayParam> displayParamList, IAction<ResponseMsg> result) {
        SmartPanelParam smartPanelParam = new SmartPanelParam();
        smartPanelParam.setCmdType(21);
        smartPanelParam.setDisplayParamList(displayParamList);
        sendWithResult(context, smartPanelParam, result);
    }

    public void setPanelScreenElderlyMode(Context context, int displayType, IAction<ResponseMsg> result) {
        sendWithResult(context, setPanelScreenElderlyMode(displayType), result);
    }

    public SmartPanelParam setPanelScreenElderlyMode(int displayType) {
        SmartPanelParam smartPanelParam = new SmartPanelParam();
        smartPanelParam.setCmdType(6);
        smartPanelParam.setDisplayModeType(displayType);
        return smartPanelParam;
    }

    public void setPanelScreenLanguage(Context context, int language, IAction<ResponseMsg> result) {
        sendWithResult(context, setPanelScreenLanguage(language), result);
    }

    public void panelBindCmdControl(Context context) {
        SmartPanelParam smartPanelParam = new SmartPanelParam();
        smartPanelParam.setCmdType(9);
        smartPanelParam.setZoneNum(this.zoneNum);
        sendOnOffWithoutResult(context, smartPanelParam, new int[0]);
    }

    public SmartPanelParam setPanelScreenLanguage(int language) {
        SmartPanelParam smartPanelParam = new SmartPanelParam();
        smartPanelParam.setCmdType(8);
        smartPanelParam.setLanguage(language);
        return smartPanelParam;
    }

    public void setSmartPanelScreenTheme(Context context, int zoneNum, int value, final IAction<ResponseMsg> result) {
        SmartPanelParam smartPanelParam = new SmartPanelParam();
        smartPanelParam.setZoneNum(zoneNum);
        smartPanelParam.setCmdType(10);
        smartPanelParam.setTheme(value);
        sendWithResult(context, smartPanelParam, new IAction<ResponseMsg>(this) { // from class: com.ltech.smarthome.utils.cmd_assistant.DeviceAssistant.2
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                IAction iAction = result;
                if (iAction != null) {
                    iAction.act(responseMsg);
                }
            }
        });
    }

    public void setSmartPanelScreensaverTime(Context context, int value, final IAction<Boolean> result) {
        SmartPanelParam smartPanelParam = new SmartPanelParam();
        smartPanelParam.setCmdType(11);
        smartPanelParam.setScreensaverTime(value);
        sendWithResult(context, smartPanelParam, new IAction<ResponseMsg>(this) { // from class: com.ltech.smarthome.utils.cmd_assistant.DeviceAssistant.3
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                IAction iAction = result;
                if (iAction != null) {
                    iAction.act(Boolean.valueOf(responseMsg != null));
                }
            }
        });
    }

    public SmartPanelParam setSmartPanelScreensaverTime(int value) {
        SmartPanelParam smartPanelParam = new SmartPanelParam();
        smartPanelParam.setCmdType(11);
        smartPanelParam.setScreensaverTime(value);
        return smartPanelParam;
    }

    public void syncSmartPanelGroupScreen(Context context, List<Integer> datas, final IAction<Boolean> result) {
        sendWithResult(context, syncSmartPanelGroupScreen(datas), new IAction<ResponseMsg>(this) { // from class: com.ltech.smarthome.utils.cmd_assistant.DeviceAssistant.4
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                IAction iAction = result;
                if (iAction != null) {
                    iAction.act(Boolean.valueOf(responseMsg != null));
                }
            }
        });
    }

    public SmartPanelParam syncSmartPanelGroupScreen(List<Integer> datas) {
        SmartPanelParam smartPanelParam = new SmartPanelParam();
        smartPanelParam.setCmdType(19);
        smartPanelParam.setData(datas);
        return smartPanelParam;
    }

    public void setSmartPanelScreensaverMode(Context context, int value, final IAction<Boolean> result) {
        SmartPanelParam smartPanelParam = new SmartPanelParam();
        smartPanelParam.setCmdType(12);
        smartPanelParam.setScreensaverMode(value);
        sendWithResult(context, smartPanelParam, new IAction<ResponseMsg>(this) { // from class: com.ltech.smarthome.utils.cmd_assistant.DeviceAssistant.5
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                IAction iAction = result;
                if (iAction != null) {
                    iAction.act(Boolean.valueOf(responseMsg != null));
                }
            }
        });
    }

    public SmartPanelParam setSmartPanelScreensaverMode(int value) {
        SmartPanelParam smartPanelParam = new SmartPanelParam();
        smartPanelParam.setCmdType(12);
        smartPanelParam.setScreensaverMode(value);
        return smartPanelParam;
    }

    public void setSmartPanelShowScreen(Context context, int i, boolean z, final IAction<Boolean> iAction) {
        SmartPanelParam smartPanelParam = new SmartPanelParam();
        smartPanelParam.setCmdType(13);
        smartPanelParam.setZoneNum(i);
        smartPanelParam.setShowScreen(z ? 1 : 0);
        sendWithResult(context, smartPanelParam, new IAction<ResponseMsg>(this) { // from class: com.ltech.smarthome.utils.cmd_assistant.DeviceAssistant.6
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                IAction iAction2 = iAction;
                if (iAction2 != null) {
                    iAction2.act(Boolean.valueOf(responseMsg != null));
                }
            }
        });
    }

    public SmartPanelParam setSmartPanelShowScreen(int i, boolean z) {
        SmartPanelParam smartPanelParam = new SmartPanelParam();
        smartPanelParam.setCmdType(13);
        smartPanelParam.setZoneNum(i);
        smartPanelParam.setShowScreen(z ? 1 : 0);
        return smartPanelParam;
    }

    public void setScreenShowTime(Context context, int zoneNum, int startM, int startS, int endM, int endS, final IAction<Boolean> result) {
        SmartPanelParam smartPanelParam = new SmartPanelParam();
        smartPanelParam.setCmdType(14);
        smartPanelParam.setZoneNum(zoneNum);
        smartPanelParam.setStartM(startM);
        smartPanelParam.setStartS(startS);
        smartPanelParam.setEndM(endM);
        smartPanelParam.setEndS(endS);
        sendWithResult(context, smartPanelParam, new IAction<ResponseMsg>(this) { // from class: com.ltech.smarthome.utils.cmd_assistant.DeviceAssistant.7
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                IAction iAction = result;
                if (iAction != null) {
                    iAction.act(Boolean.valueOf(responseMsg != null));
                }
            }
        });
    }

    public void setChangeCurScreen(Context context, int pos) {
        SmartPanelParam smartPanelParam = new SmartPanelParam();
        smartPanelParam.setCmdType(18);
        smartPanelParam.setShowScreen(pos);
        sendWithoutResult(context, smartPanelParam);
    }

    public SmartPanelParam setScreenShowTime(int zoneNum, int startM, int startS, int endM, int endS) {
        SmartPanelParam smartPanelParam = new SmartPanelParam();
        smartPanelParam.setCmdType(14);
        smartPanelParam.setZoneNum(zoneNum);
        smartPanelParam.setStartM(startM);
        smartPanelParam.setStartS(startS);
        smartPanelParam.setEndM(endM);
        smartPanelParam.setEndS(endS);
        return smartPanelParam;
    }

    public void setSmartPanelShowTimeClose(Context context, int i, boolean z, final IAction<Boolean> iAction) {
        SmartPanelParam smartPanelParam = new SmartPanelParam();
        smartPanelParam.setCmdType(15);
        smartPanelParam.setZoneNum(i);
        smartPanelParam.setScreenShowTimeClose(!z ? 1 : 0);
        sendWithResult(context, smartPanelParam, new IAction<ResponseMsg>(this) { // from class: com.ltech.smarthome.utils.cmd_assistant.DeviceAssistant.8
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                IAction iAction2 = iAction;
                if (iAction2 != null) {
                    iAction2.act(Boolean.valueOf(responseMsg != null));
                }
            }
        });
    }

    public SmartPanelParam setSmartPanelShowTimeClose(int i, boolean z) {
        SmartPanelParam smartPanelParam = new SmartPanelParam();
        smartPanelParam.setCmdType(15);
        smartPanelParam.setZoneNum(i);
        smartPanelParam.setScreenShowTimeClose(!z ? 1 : 0);
        return smartPanelParam;
    }

    public void setSmartPanelGetUpAtNightTimes(Context context, int i, final IAction<Boolean> result) {
        SmartPanelParam smartPanelParam = new SmartPanelParam();
        smartPanelParam.setCmdType(16);
        smartPanelParam.setZoneNum(1);
        smartPanelParam.setGetUpAtNightTimes(i);
        sendWithResult(context, smartPanelParam, new IAction<ResponseMsg>(this) { // from class: com.ltech.smarthome.utils.cmd_assistant.DeviceAssistant.9
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                IAction iAction = result;
                if (iAction != null) {
                    iAction.act(Boolean.valueOf(responseMsg != null));
                }
            }
        });
    }

    public void setKnobSensitivity(Context context, int sensitivity, IAction<Boolean> result) {
        save(context, setKnobSensitivity(sensitivity), result);
    }

    public SmartPanelParam setKnobSensitivity(int sensitivity) {
        SmartPanelParam smartPanelParam = new SmartPanelParam();
        smartPanelParam.setZoneNum(this.zoneNum);
        smartPanelParam.setCmdType(3);
        smartPanelParam.setSensitivity(sensitivity);
        return smartPanelParam;
    }

    public void setKnobSensitivity(Context context, int zoneNum, int sensitivity, IAction<Boolean> result) {
        SmartPanelParam smartPanelParam = new SmartPanelParam();
        smartPanelParam.setZoneNum(zoneNum);
        smartPanelParam.setCmdType(3);
        smartPanelParam.setSensitivity(sensitivity);
        save(context, smartPanelParam, result);
    }

    public void setKnobOrder(Context context, int[] orderArray, IAction<Boolean> result) {
        save(context, setKnobOrder(orderArray), result);
    }

    public SmartPanelParam setKnobOrder(int[] orderArray) {
        SmartPanelParam smartPanelParam = new SmartPanelParam();
        smartPanelParam.setZoneNum(this.zoneNum);
        smartPanelParam.setCmdType(4);
        smartPanelParam.setOrderArray(orderArray);
        return smartPanelParam;
    }

    public void setKnobOrderWithoutDialog(Context context, int[] orderArray, IAction<Boolean> result) {
        saveWithoutDialog(context, setKnobOrder(orderArray), result);
    }

    public void setIconData(Context context, int displayType, int iconIndex, byte[] iconData, final IAction<ResponseMsg> result) {
        SmartPanelParam smartPanelParam = new SmartPanelParam();
        smartPanelParam.setZoneNum(this.zoneNum);
        smartPanelParam.setCmdType(5);
        smartPanelParam.setDisplayType(displayType);
        smartPanelParam.setIconIndex(iconIndex);
        smartPanelParam.setIconData(iconData);
        Injection.message().create(context).cmdParam(smartPanelParam).resendTimes(2).control(this.controlObject).receiveListener(new IReceiveListener(this) { // from class: com.ltech.smarthome.utils.cmd_assistant.DeviceAssistant.10
            @Override // com.smart.message.base.IReceiveListener
            public void onSuccess(ResponseMsg msg) {
                result.act(msg);
            }

            @Override // com.smart.message.base.IReceiveListener
            public void onTimeout() {
                result.act(null);
            }
        }).enqueue();
    }

    public void setWaveEnable(Context context, boolean enable, IAction<ResponseMsg> result) {
        MicrowaveParam microwaveParam = new MicrowaveParam();
        microwaveParam.setZoneNum(this.zoneNum);
        microwaveParam.setEnable(enable);
        microwaveParam.setCmdType(1);
        sendWithResult(context, microwaveParam, result);
    }

    public MicrowaveParam setWaveEnable(boolean enable) {
        MicrowaveParam microwaveParam = new MicrowaveParam();
        microwaveParam.setZoneNum(this.zoneNum);
        microwaveParam.setEnable(enable);
        microwaveParam.setCmdType(1);
        return microwaveParam;
    }

    public void setIllumincance(Context context, boolean is24G, int illuminance, final IAction<Boolean> result) {
        MicrowaveParam microwaveParam = new MicrowaveParam();
        microwaveParam.setZoneNum(this.zoneNum);
        microwaveParam.setIlluminance(illuminance);
        if (is24G) {
            microwaveParam.setCmdType(22);
        } else {
            microwaveParam.setCmdType(2);
        }
        sendWithResult(context, microwaveParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.DeviceAssistant$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null));
            }
        });
    }

    public void setSensitivity(Context context, int sensitivity, final IAction<Boolean> result) {
        MicrowaveParam microwaveParam = new MicrowaveParam();
        microwaveParam.setZoneNum(this.zoneNum);
        microwaveParam.setSensitivity(sensitivity);
        microwaveParam.setCmdType(3);
        sendWithResult(context, microwaveParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.DeviceAssistant$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null));
            }
        });
    }

    public MicrowaveParam setSensitivity(int sensitivity) {
        MicrowaveParam microwaveParam = new MicrowaveParam();
        microwaveParam.setZoneNum(this.zoneNum);
        microwaveParam.setSensitivity(sensitivity);
        microwaveParam.setCmdType(3);
        return microwaveParam;
    }

    public void setIndicatorState(Context context, int indicatorLight, IAction<Boolean> result) {
        MicrowaveParam microwaveParam = new MicrowaveParam();
        microwaveParam.setZoneNum(this.zoneNum);
        microwaveParam.setIndicatorLight(indicatorLight);
        microwaveParam.setCmdType(7);
        save(context, microwaveParam, result);
    }

    public void setTimeToSensor(Context context) {
        MicrowaveParam microwaveParam = new MicrowaveParam();
        microwaveParam.setZoneNum(this.zoneNum);
        microwaveParam.setCmdType(10);
        sendWithoutResult(context, microwaveParam);
    }

    public void setRelayState(Context context, boolean on, IAction<Boolean> result) {
        MicrowaveParam microwaveParam = new MicrowaveParam();
        microwaveParam.setZoneNum(this.zoneNum);
        microwaveParam.setRelayPowerOn(on);
        microwaveParam.setCmdType(14);
        save(context, microwaveParam, result);
    }

    public void setDelayTime(Context context, int delaytime, IAction<Boolean> result) {
        MicrowaveParam microwaveParam = new MicrowaveParam();
        microwaveParam.setZoneNum(this.zoneNum);
        microwaveParam.setDelayTime(delaytime);
        microwaveParam.setCmdType(4);
        save(context, microwaveParam, result);
    }

    public MicrowaveParam setDelayTime(int delaytime) {
        MicrowaveParam microwaveParam = new MicrowaveParam();
        microwaveParam.setZoneNum(this.zoneNum);
        microwaveParam.setDelayTime(delaytime);
        microwaveParam.setCmdType(4);
        return microwaveParam;
    }

    public void setStayTime(Context context, int stayTime, IAction<Boolean> result) {
        MicrowaveParam microwaveParam = new MicrowaveParam();
        microwaveParam.setZoneNum(this.zoneNum);
        microwaveParam.setStayTime(stayTime);
        microwaveParam.setCmdType(5);
        save(context, microwaveParam, result);
    }

    public void setValidTime(Context context, int startH, int startM, int endH, int endM, int repeat, IAction<ResponseMsg> result) {
        MicrowaveParam microwaveParam = new MicrowaveParam();
        microwaveParam.setZoneNum(this.zoneNum);
        microwaveParam.setStartTimeH(startH);
        microwaveParam.setStartTimeM(startM);
        microwaveParam.setEndTimeH(endH);
        microwaveParam.setEndTimeM(endM);
        microwaveParam.setRepeat(repeat);
        microwaveParam.setCmdType(6);
        sendWithResult(context, microwaveParam, result);
    }

    public void setPeriodEnable(Context context, int index, boolean enable, IAction<ResponseMsg> result) {
        MicrowaveParam microwaveParam = new MicrowaveParam();
        microwaveParam.setZoneNum(this.zoneNum);
        microwaveParam.setIndex(index);
        microwaveParam.setPeriodEnable(enable);
        microwaveParam.setCmdType(16);
        sendWithResult(context, microwaveParam, result);
    }

    public void setAutomationDelayTime(Context context, int delayTime, IAction<Boolean> result) {
        MicrowaveParam microwaveParam = new MicrowaveParam();
        microwaveParam.setZoneNum(this.zoneNum);
        microwaveParam.setAutomationDelay(delayTime);
        microwaveParam.setCmdType(17);
        save(context, microwaveParam, result);
    }

    public void setLuxCompensation(Context context, int lux, IAction<Boolean> result) {
        MicrowaveParam microwaveParam = new MicrowaveParam();
        microwaveParam.setZoneNum(this.zoneNum);
        microwaveParam.setLuxCompensation(lux);
        microwaveParam.setCmdType(20);
        save(context, microwaveParam, result);
    }

    public void setCtCompensation(Context context, int ct, IAction<Boolean> result) {
        MicrowaveParam microwaveParam = new MicrowaveParam();
        microwaveParam.setZoneNum(this.zoneNum);
        microwaveParam.setCtCompensation(ct);
        microwaveParam.setCmdType(21);
        save(context, microwaveParam, result);
    }

    public void setRelateParam(Context context, int destAdd, int trigType, int dataType, byte[] instruct, IAction<Boolean> result) {
        MicrowaveParam microwaveParam = new MicrowaveParam();
        microwaveParam.setZoneNum(this.zoneNum);
        microwaveParam.setDestAdd(destAdd);
        microwaveParam.setTrigType(trigType);
        microwaveParam.setDataType(dataType);
        microwaveParam.setInstruct(instruct);
        microwaveParam.setCmdType(8);
        save(context, microwaveParam, result);
    }

    public void setRelateParamAll(Context context, int destAdd, int trigType, byte[] delayInstruct, byte[] stayInstruct, byte[] exitInstruct, IAction<Boolean> result) {
        MicrowaveParam microwaveParam = new MicrowaveParam();
        microwaveParam.setZoneNum(this.zoneNum);
        microwaveParam.setDestAdd(destAdd);
        microwaveParam.setTrigType(trigType);
        microwaveParam.setDelayInstruct(delayInstruct);
        microwaveParam.setStayInstruct(stayInstruct);
        microwaveParam.setExitInstruct(exitInstruct);
        microwaveParam.setCmdType(9);
        save(context, microwaveParam, result);
    }

    public void setGroupRelateParam(Context context, MicrowaveParam cmdParam, IAction<Boolean> result) {
        cmdParam.setZoneNum(this.zoneNum);
        cmdParam.setCmdType(11);
        save(context, cmdParam, result);
    }

    public void setGroupRelateParamWithoutDialog(Context context, boolean is24G, MicrowaveParam cmdParam, final IAction<Boolean> result) {
        cmdParam.setZoneNum(this.zoneNum);
        if (is24G) {
            cmdParam.setCmdType(23);
        } else {
            cmdParam.setCmdType(11);
        }
        sendWithResult(context, cmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.DeviceAssistant$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf((r2 == null || r2.getStateCode() == 3) ? false : true));
            }
        });
    }

    public void querySensorParams(Context context, IAction<ResponseMsg> result) {
        MicrowaveParam microwaveParam = new MicrowaveParam();
        microwaveParam.setZoneNum(this.zoneNum);
        microwaveParam.setCmdType(12);
        sendWithResult(context, microwaveParam, result);
    }

    public void setTestMode(Context context, int testState, int testMode, int sensitivity, final IAction<Boolean> result) {
        MicrowaveParam microwaveParam = new MicrowaveParam();
        microwaveParam.setZoneNum(this.zoneNum);
        microwaveParam.setTestState(testState);
        microwaveParam.setTestMode(testMode);
        microwaveParam.setSensitivity(sensitivity);
        microwaveParam.setCmdType(18);
        sendWithResult(context, microwaveParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.DeviceAssistant$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null && r1.getStateCode() == 0));
            }
        });
    }

    public void setRs485Data(Context context, int editType, int index, String btData, String rsData, int dataFormat, IAction<ResponseMsg> result) {
        Rs485CmdParam rs485CmdParam = new Rs485CmdParam();
        rs485CmdParam.setZoneNum(this.zoneNum);
        rs485CmdParam.setCmdType(1);
        rs485CmdParam.setEditType(editType);
        rs485CmdParam.setCmdIndex(index);
        rs485CmdParam.setBtData(btData);
        rs485CmdParam.setRsData(rsData);
        rs485CmdParam.setDataFormat(dataFormat);
        sendWithResult(context, rs485CmdParam, result);
    }

    public Rs485CmdParam setRs485Data(int editType, int index, String btData, String rsData, int dataFormat) {
        Rs485CmdParam rs485CmdParam = new Rs485CmdParam();
        rs485CmdParam.setZoneNum(this.zoneNum);
        rs485CmdParam.setCmdType(1);
        rs485CmdParam.setEditType(editType);
        rs485CmdParam.setCmdIndex(index);
        rs485CmdParam.setBtData(btData);
        rs485CmdParam.setRsData(rsData);
        rs485CmdParam.setDataFormat(dataFormat);
        return rs485CmdParam;
    }

    public void deleteRs485Data(Context context, List<Integer> cmdList, final IAction<Boolean> result) {
        Rs485CmdParam rs485CmdParam = new Rs485CmdParam();
        rs485CmdParam.setZoneNum(this.zoneNum);
        rs485CmdParam.setCmdType(2);
        rs485CmdParam.setCmdList(cmdList);
        sendWithResult(context, rs485CmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.DeviceAssistant$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null && r1.getStateCode() == 0));
            }
        });
    }

    public void learnRs485Data(Context context, final IAction<ResponseMsg> result) {
        Rs485CmdParam rs485CmdParam = new Rs485CmdParam();
        rs485CmdParam.setZoneNum(this.zoneNum);
        rs485CmdParam.setCmdType(3);
        Injection.message().create(context).cmdParam(rs485CmdParam).timeOutTime(30000).resendTimes(0).control(this.controlObject).receiveListener(new IReceiveListener(this) { // from class: com.ltech.smarthome.utils.cmd_assistant.DeviceAssistant.11
            @Override // com.smart.message.base.IReceiveListener
            public void onSuccess(ResponseMsg msg) {
                result.act(msg);
            }

            @Override // com.smart.message.base.IReceiveListener
            public void onTimeout() {
                result.act(null);
            }
        }).enqueue();
    }

    public void runRs485Data(Context context, String rsData, int dataFormat, final IAction<Boolean> result) {
        Rs485CmdParam rs485CmdParam = new Rs485CmdParam();
        rs485CmdParam.setZoneNum(this.zoneNum);
        rs485CmdParam.setCmdType(4);
        rs485CmdParam.setRsData(rsData);
        rs485CmdParam.setDataFormat(dataFormat);
        sendWithResult(context, rs485CmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.DeviceAssistant$$ExternalSyntheticLambda7
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null && r1.getStateCode() == 0));
            }
        });
    }

    public void runRs485DataWithReply(Context context, String rsData, String reReplayData, int dataFormat, final IAction<Boolean> result) {
        Rs485CmdParam rs485CmdParam = new Rs485CmdParam();
        rs485CmdParam.setZoneNum(this.zoneNum);
        rs485CmdParam.setCmdType(4);
        rs485CmdParam.setRsData(rsData);
        rs485CmdParam.setRsReplyData(reReplayData);
        rs485CmdParam.setDataFormat(dataFormat);
        sendWithResult(context, rs485CmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.DeviceAssistant$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null && r1.getStateCode() == 0));
            }
        });
    }

    public void setRs485Serial(Context context, int type, int value, IAction<Boolean> result) {
        Rs485CmdParam rs485CmdParam = new Rs485CmdParam();
        rs485CmdParam.setZoneNum(this.zoneNum);
        rs485CmdParam.setCmdType(5);
        rs485CmdParam.setType(type);
        rs485CmdParam.setValue(value);
        save(context, rs485CmdParam, result);
    }

    public Rs485CmdParam setRs485Serial(int type, int value) {
        Rs485CmdParam rs485CmdParam = new Rs485CmdParam();
        rs485CmdParam.setZoneNum(this.zoneNum);
        rs485CmdParam.setCmdType(5);
        rs485CmdParam.setType(type);
        rs485CmdParam.setValue(value);
        return rs485CmdParam;
    }

    public void setEurOpenState(Context context, int zoneNum, int openState) {
        MicrowaveParam microwaveParam = new MicrowaveParam();
        microwaveParam.setZoneNum(zoneNum);
        microwaveParam.setCmdType(19);
        microwaveParam.setOpenState(openState);
        sendWithoutResultByPublish(context, microwaveParam, 3, new int[0]);
    }

    public void relayMapping(Context context, int[] zones, final IAction<Boolean> result) {
        sendWithResult(context, relayMapping(zones), new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.DeviceAssistant$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null && r1.getStateCode() == 0));
            }
        });
    }

    public SmartPanelParam relayMapping(int[] zones) {
        ArrayList arrayList = new ArrayList();
        for (int i : zones) {
            arrayList.add(Integer.valueOf(i));
        }
        SmartPanelParam smartPanelParam = new SmartPanelParam();
        smartPanelParam.setZoneNum(this.zoneNum);
        smartPanelParam.setCmdType(20);
        smartPanelParam.setData(arrayList);
        return smartPanelParam;
    }

    public void querySuperPanelIsUpgrade(Context context, final IAction<ResponseMsg> iAction) {
        SuperPanelCmdParam superPanelCmdParam = new SuperPanelCmdParam();
        superPanelCmdParam.setZoneNum(this.zoneNum);
        superPanelCmdParam.setCmdType(9);
        sendWithResult(context, superPanelCmdParam, new IAction<ResponseMsg>(this) { // from class: com.ltech.smarthome.utils.cmd_assistant.DeviceAssistant.12
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                IAction iAction2 = iAction;
                if (iAction2 != null) {
                    iAction2.act(responseMsg);
                }
            }
        });
    }

    public void queryChildMcuIsUpgrade(Context context, final IAction<ResponseMsg> iAction) {
        SuperPanelCmdParam superPanelCmdParam = new SuperPanelCmdParam();
        superPanelCmdParam.setZoneNum(this.zoneNum);
        superPanelCmdParam.setCmdType(7);
        sendWithResult(context, superPanelCmdParam, new IAction<ResponseMsg>(this) { // from class: com.ltech.smarthome.utils.cmd_assistant.DeviceAssistant.13
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                IAction iAction2 = iAction;
                if (iAction2 != null) {
                    iAction2.act(responseMsg);
                }
            }
        });
    }

    public void stopChildMcuUpgrade(Context context, final IAction<Boolean> iAction) {
        SuperPanelCmdParam superPanelCmdParam = new SuperPanelCmdParam();
        superPanelCmdParam.setZoneNum(this.zoneNum);
        superPanelCmdParam.setCmdType(8);
        sendWithResult(context, superPanelCmdParam, new IAction<ResponseMsg>(this) { // from class: com.ltech.smarthome.utils.cmd_assistant.DeviceAssistant.14
            /* JADX WARN: Code restructure failed: missing block: B:11:0x002d, code lost:
            
                if (java.lang.Integer.parseInt(r4.getResData().substring(16, 18), 16) == 1) goto L15;
             */
            @Override // com.ltech.smarthome.base.IAction
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void act(com.smart.message.ResponseMsg r4) {
                /*
                    r3 = this;
                    com.ltech.smarthome.base.IAction r0 = r2
                    if (r0 == 0) goto L38
                    if (r4 == 0) goto L30
                    int r1 = r4.getStateCode()
                    if (r1 != 0) goto L30
                    java.lang.String r1 = r4.getResData()
                    if (r1 == 0) goto L30
                    java.lang.String r1 = r4.getResData()
                    int r1 = r1.length()
                    r2 = 16
                    if (r1 <= r2) goto L30
                    java.lang.String r4 = r4.getResData()
                    r1 = 18
                    java.lang.String r4 = r4.substring(r2, r1)
                    int r4 = java.lang.Integer.parseInt(r4, r2)
                    r1 = 1
                    if (r4 != r1) goto L30
                    goto L31
                L30:
                    r1 = 0
                L31:
                    java.lang.Boolean r4 = java.lang.Boolean.valueOf(r1)
                    r0.act(r4)
                L38:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.utils.cmd_assistant.DeviceAssistant.AnonymousClass14.act(com.smart.message.ResponseMsg):void");
            }
        });
    }

    public void sendIrControlCmd(Context context, long deviceId, List<Integer> values, final IAction<Boolean> result) {
        sendWithResult(context, sendIrControlCmd(deviceId, values), new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.DeviceAssistant$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null && r1.getStateCode() == 0));
            }
        });
    }

    public SuperPanelCmdParam sendIrControlCmd(long deviceId, List<Integer> values) {
        SuperPanelCmdParam superPanelCmdParam = new SuperPanelCmdParam();
        superPanelCmdParam.setZoneNum(this.zoneNum);
        superPanelCmdParam.setCmdType(10);
        superPanelCmdParam.setDeviceId(deviceId);
        superPanelCmdParam.setData(values);
        return superPanelCmdParam;
    }

    public void sendCmdWithResult(Context context, BaseCmd cmd, IAction<ResponseMsg> result) {
        sendWithResult(context, cmd, result);
    }

    public void sendOnOffWithoutResult(Context context, BaseCmd cmd, BaseCtrlPackage ctrlPacket, int... sendTime) {
        Injection.message().create(context).cmd(cmd).control(ctrlPacket).intervalTime(100).sendTimes(sendTime.length > 0 ? sendTime[0] : 5).enqueue();
    }

    public void setE6Object(Context context, String instruct, IAction<Boolean> result) {
        save(context, setE6Object(instruct), result);
    }

    public BleNetworkCmdParam setE6Object(String instruct) {
        BleNetworkCmdParam bleNetworkCmdParam = new BleNetworkCmdParam();
        bleNetworkCmdParam.setZoneNum(this.zoneNum);
        bleNetworkCmdParam.setCmdType(19);
        bleNetworkCmdParam.setInstruct(instruct);
        return bleNetworkCmdParam;
    }

    public void setE6Action(Context context, String instruct, IAction<Boolean> result) {
        save(context, setE6Action(instruct), result);
    }

    public BleNetworkCmdParam setE6Action(String instruct) {
        BleNetworkCmdParam bleNetworkCmdParam = new BleNetworkCmdParam();
        bleNetworkCmdParam.setZoneNum(this.zoneNum);
        bleNetworkCmdParam.setCmdType(20);
        bleNetworkCmdParam.setInstruct(instruct);
        return bleNetworkCmdParam;
    }
}