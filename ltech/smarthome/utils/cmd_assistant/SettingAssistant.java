package com.ltech.smarthome.utils.cmd_assistant;

import android.content.Context;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.message.CtrlPackage;
import com.ltech.smarthome.message.CtrlPackager;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.smart.message.ResponseMsg;
import com.smart.message.base.BaseCmdParam;
import com.smart.message.base.BaseCtrlPackage;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.param.BleNetworkCmdParam;
import com.smart.product_agreement.param.DeviceCmdParam;
import com.smart.product_agreement.param.SettingCmdParam;
import com.smart.product_agreement.param.SmartPanelParam;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public class SettingAssistant extends BaseAssistant {
    public void setDeviceType(Context context, CtrlPackage ctrlPackage, int deviceType, final IAction<Boolean> result) {
        sendWithResult(context, ctrlPackage, setDeviceType(deviceType), new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda39
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null && r1.getStateCode() == 0));
            }
        });
    }

    public SettingCmdParam setDeviceType(int deviceType) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setZoneNum(this.zoneNum);
        settingCmdParam.setCmdType(1);
        settingCmdParam.setDeviceType(deviceType);
        return settingCmdParam;
    }

    public void setOutputType(Context context, CtrlPackage ctrlPackage, int outputType, final IAction<Boolean> result) {
        sendWithResult(context, ctrlPackage, setOutputType(outputType), new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda34
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null));
            }
        });
    }

    public SettingCmdParam setOutputType(int outputType) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setZoneNum(this.zoneNum);
        settingCmdParam.setCmdType(4);
        settingCmdParam.setOutputType(outputType);
        return settingCmdParam;
    }

    public void subscribe(Context context, int dstAddress, int subscribeAddress, int agreementId, int subscribeZone, int extData, final IAction<Boolean> result) {
        BleNetworkCmdParam bleNetworkCmdParam = new BleNetworkCmdParam();
        bleNetworkCmdParam.setZoneNum(this.zoneNum);
        bleNetworkCmdParam.setCmdType(1);
        bleNetworkCmdParam.setSubscribeAddress(subscribeAddress);
        bleNetworkCmdParam.setSubscribeZone(subscribeZone);
        bleNetworkCmdParam.setSubscribeExtData(extData);
        CtrlPackage ctrlPackage = new CtrlPackage(agreementId);
        ctrlPackage.setAddress(dstAddress);
        sendWithResult(context, ctrlPackage, bleNetworkCmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null));
            }
        });
    }

    public void subscribe1(Context context, int dstAddress, int subscribeAddress, int agreementId, int destZone, int subscribeZone, int extData, final IAction<Boolean> result) {
        BleNetworkCmdParam bleNetworkCmdParam = new BleNetworkCmdParam();
        bleNetworkCmdParam.setZoneNum(this.zoneNum);
        bleNetworkCmdParam.setDestZone(destZone);
        bleNetworkCmdParam.setCmdType(9);
        bleNetworkCmdParam.setSubscribeAddress(subscribeAddress);
        bleNetworkCmdParam.setSubscribeZone(subscribeZone);
        bleNetworkCmdParam.setSubscribeExtData(extData);
        CtrlPackage ctrlPackage = new CtrlPackage(agreementId);
        ctrlPackage.setAddress(dstAddress);
        sendWithResult(context, ctrlPackage, bleNetworkCmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda17
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null));
            }
        });
    }

    public void subscribeInSwitchPanel(Context context, int destZone, int dstAddress, int subscribeAddress, int agreementId, int subscribeZone, int extData, int relateType, int relateLightType, final IAction<Boolean> result) {
        BaseCmdParam subscribeInSwitchPanel = subscribeInSwitchPanel(destZone, subscribeAddress, subscribeZone, extData, relateType, relateLightType);
        CtrlPackage ctrlPackage = new CtrlPackage(agreementId);
        ctrlPackage.setAddress(dstAddress);
        sendWithResult(context, ctrlPackage, subscribeInSwitchPanel, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda32
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                SettingAssistant.lambda$subscribeInSwitchPanel$4(IAction.this, (ResponseMsg) obj);
            }
        });
    }

    static /* synthetic */ void lambda$subscribeInSwitchPanel$4(IAction iAction, ResponseMsg responseMsg) {
        iAction.act(Boolean.valueOf(responseMsg != null && responseMsg.getStateCode() == 0));
        if (responseMsg == null || responseMsg.getResData().length() < 18) {
            return;
        }
        RelateInfoUtils.lastBindTime = System.currentTimeMillis();
        RelateInfoUtils.timeToLowPower = Integer.parseInt(responseMsg.getResData().substring(16, 18), 16);
    }

    public BleNetworkCmdParam subscribeInSwitchPanel(int destZone, int subscribeAddress, int subscribeZone, int extData, int relateType, int relateLightType) {
        BleNetworkCmdParam bleNetworkCmdParam = new BleNetworkCmdParam();
        bleNetworkCmdParam.setZoneNum(destZone);
        bleNetworkCmdParam.setCmdType(7);
        bleNetworkCmdParam.setSubscribeAddress(subscribeAddress);
        bleNetworkCmdParam.setSubscribeZone(subscribeZone);
        bleNetworkCmdParam.setSubscribeExtData(extData);
        bleNetworkCmdParam.setRelateType(relateType);
        bleNetworkCmdParam.setBindData(Collections.singletonList(Integer.valueOf(relateLightType)));
        return bleNetworkCmdParam;
    }

    public void subscribeInSwitchPanel(Context context, int destZone, int dstAddress, int subscribeAddress, int agreementId, int subscribeZone, int extData, int relateType, List<Integer> bindData, final IAction<Boolean> result) {
        BaseCmdParam subscribeInSwitchPanel = subscribeInSwitchPanel(destZone, subscribeAddress, subscribeZone, extData, relateType, bindData);
        CtrlPackage ctrlPackage = new CtrlPackage(agreementId);
        ctrlPackage.setAddress(dstAddress);
        sendWithResult(context, ctrlPackage, subscribeInSwitchPanel, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda20
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                SettingAssistant.lambda$subscribeInSwitchPanel$5(IAction.this, (ResponseMsg) obj);
            }
        });
    }

    static /* synthetic */ void lambda$subscribeInSwitchPanel$5(IAction iAction, ResponseMsg responseMsg) {
        iAction.act(Boolean.valueOf(responseMsg != null && responseMsg.getStateCode() == 0));
        if (responseMsg == null || responseMsg.getResData().length() < 18) {
            return;
        }
        RelateInfoUtils.lastBindTime = System.currentTimeMillis();
        RelateInfoUtils.timeToLowPower = Integer.parseInt(responseMsg.getResData().substring(16, 18), 16);
    }

    public BleNetworkCmdParam subscribeInSwitchPanel(int destZone, int subscribeAddress, int subscribeZone, int extData, int relateType, List<Integer> bindData) {
        BleNetworkCmdParam bleNetworkCmdParam = new BleNetworkCmdParam();
        bleNetworkCmdParam.setZoneNum(destZone);
        bleNetworkCmdParam.setCmdType(7);
        bleNetworkCmdParam.setSubscribeAddress(subscribeAddress);
        bleNetworkCmdParam.setSubscribeZone(subscribeZone);
        bleNetworkCmdParam.setSubscribeExtData(extData);
        bleNetworkCmdParam.setRelateType(relateType);
        bleNetworkCmdParam.setBindData(bindData);
        return bleNetworkCmdParam;
    }

    public void subscribeInSwitchPanelScene(Context context, int destZone, int dstAddress, int subscribeAddress, int agreementId, int subscribeZone, int extData, int relateType, int relateLightType, final IAction<ResponseMsg> result) {
        BaseCmdParam subscribeInSwitchPanelScene = subscribeInSwitchPanelScene(destZone, subscribeAddress, subscribeZone, extData, relateType, relateLightType);
        CtrlPackage ctrlPackage = new CtrlPackage(agreementId);
        ctrlPackage.setAddress(dstAddress);
        sendWithResult(context, ctrlPackage, subscribeInSwitchPanelScene, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda14
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                SettingAssistant.lambda$subscribeInSwitchPanelScene$6(IAction.this, (ResponseMsg) obj);
            }
        });
    }

    static /* synthetic */ void lambda$subscribeInSwitchPanelScene$6(IAction iAction, ResponseMsg responseMsg) {
        iAction.act(responseMsg);
        if (responseMsg == null || responseMsg.getResData().length() < 18) {
            return;
        }
        RelateInfoUtils.lastBindTime = System.currentTimeMillis();
        RelateInfoUtils.timeToLowPower = Integer.parseInt(responseMsg.getResData().substring(16, 18), 16);
    }

    public BleNetworkCmdParam subscribeInSwitchPanelScene(int destZone, int subscribeAddress, int subscribeZone, int extData, int relateType, int relateLightType) {
        BleNetworkCmdParam bleNetworkCmdParam = new BleNetworkCmdParam();
        bleNetworkCmdParam.setZoneNum(destZone);
        bleNetworkCmdParam.setCmdType(7);
        bleNetworkCmdParam.setSubscribeAddress(subscribeAddress);
        bleNetworkCmdParam.setSubscribeZone(subscribeZone);
        bleNetworkCmdParam.setSubscribeExtData(extData);
        bleNetworkCmdParam.setRelateType(relateType);
        bleNetworkCmdParam.setBindData(Collections.singletonList(Integer.valueOf(relateLightType)));
        return bleNetworkCmdParam;
    }

    public void subscribeInSwitchPanelComboCmd(Context context, int destZone, List<BleNetworkCmdParam.ComboCmdAction> extData, final IAction<ResponseMsg> result) {
        sendWithResult(context, subscribeInSwitchPanelComboCmd(destZone, extData), new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda47
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act((ResponseMsg) obj);
            }
        });
    }

    public BleNetworkCmdParam subscribeInSwitchPanelComboCmd(int destZone, List<BleNetworkCmdParam.ComboCmdAction> extData) {
        BleNetworkCmdParam bleNetworkCmdParam = new BleNetworkCmdParam();
        bleNetworkCmdParam.setZoneNum(destZone);
        bleNetworkCmdParam.setCmdType(14);
        bleNetworkCmdParam.setComboCmdActions(extData);
        return bleNetworkCmdParam;
    }

    public void unsubscribeInSwitchPanelComboCmd(Context context, int destZone, int subCmdType, final IAction<ResponseMsg> result) {
        BleNetworkCmdParam bleNetworkCmdParam = new BleNetworkCmdParam();
        bleNetworkCmdParam.setZoneNum(destZone);
        bleNetworkCmdParam.setCmdType(15);
        bleNetworkCmdParam.setSubCmdType(subCmdType);
        sendWithResult(context, bleNetworkCmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda44
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act((ResponseMsg) obj);
            }
        });
    }

    public void subscribeInEurPanel(Context context, int destZone, int dstAddress, int subscribeAddress, int agreementId, int subscribeZone, int extData, int relateType, List<Integer> bindData, final IAction<ResponseMsg> result) {
        BaseCmdParam subscribeInEurPanel = subscribeInEurPanel(destZone, subscribeAddress, subscribeZone, extData, relateType, bindData);
        CtrlPackage ctrlPackage = new CtrlPackage(agreementId);
        ctrlPackage.setAddress(dstAddress);
        sendWithResult(context, ctrlPackage, subscribeInEurPanel, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda21
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act((ResponseMsg) obj);
            }
        });
    }

    public BleNetworkCmdParam subscribeInEurPanel(int destZone, int subscribeAddress, int subscribeZone, int extData, int relateType, List<Integer> bindData) {
        BleNetworkCmdParam bleNetworkCmdParam = new BleNetworkCmdParam();
        bleNetworkCmdParam.setZoneNum(destZone);
        bleNetworkCmdParam.setCmdType(7);
        bleNetworkCmdParam.setSubscribeAddress(subscribeAddress);
        bleNetworkCmdParam.setSubscribeZone(subscribeZone);
        bleNetworkCmdParam.setSubscribeExtData(extData);
        bleNetworkCmdParam.setRelateType(relateType);
        bleNetworkCmdParam.setBindData(bindData);
        return bleNetworkCmdParam;
    }

    public void subscribeBleCurtain(Context context, int dstAddress, int subscribeAddress, int agreementId, int subscribeZone, int extData, int dataArea, final IAction<Boolean> result) {
        BleNetworkCmdParam bleNetworkCmdParam = new BleNetworkCmdParam();
        bleNetworkCmdParam.setZoneNum(this.zoneNum);
        bleNetworkCmdParam.setCmdType(11);
        bleNetworkCmdParam.setSubscribeAddress(subscribeAddress);
        bleNetworkCmdParam.setSubscribeZone(subscribeZone);
        bleNetworkCmdParam.setSubscribeExtData(extData);
        bleNetworkCmdParam.setDataArea(dataArea);
        CtrlPackage ctrlPackage = new CtrlPackage(agreementId);
        ctrlPackage.setAddress(dstAddress);
        sendWithResult(context, ctrlPackage, bleNetworkCmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda43
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null));
            }
        });
    }

    public void unSubscribe(Context context, int dstAddress, int subscribeAddress, int agreementId, int subscribeZone, final IAction<Boolean> result) {
        BleNetworkCmdParam bleNetworkCmdParam = new BleNetworkCmdParam();
        bleNetworkCmdParam.setZoneNum(this.zoneNum);
        bleNetworkCmdParam.setCmdType(2);
        bleNetworkCmdParam.setSubscribeAddress(subscribeAddress);
        bleNetworkCmdParam.setSubscribeZone(subscribeZone);
        CtrlPackage ctrlPackage = new CtrlPackage(agreementId);
        ctrlPackage.setAddress(dstAddress);
        sendWithResult(context, ctrlPackage, bleNetworkCmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null));
            }
        });
    }

    public void unSubscribeInSwitchPanel(Context context, int dstAddress, int subscribeZone, int agreementId, final IAction<Boolean> result) {
        BaseCmdParam bleNetworkCmdParam = new BleNetworkCmdParam();
        bleNetworkCmdParam.setZoneNum(subscribeZone);
        bleNetworkCmdParam.setCmdType(8);
        CtrlPackage ctrlPackage = new CtrlPackage(agreementId);
        ctrlPackage.setAddress(dstAddress);
        sendWithResult(context, ctrlPackage, bleNetworkCmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda45
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                SettingAssistant.lambda$unSubscribeInSwitchPanel$12(IAction.this, (ResponseMsg) obj);
            }
        });
    }

    static /* synthetic */ void lambda$unSubscribeInSwitchPanel$12(IAction iAction, ResponseMsg responseMsg) {
        iAction.act(Boolean.valueOf(responseMsg != null));
        if (responseMsg == null || responseMsg.getResData().length() < 18) {
            return;
        }
        RelateInfoUtils.lastBindTime = System.currentTimeMillis();
        RelateInfoUtils.timeToLowPower = Integer.parseInt(responseMsg.getResData().substring(16, 18), 16);
    }

    public void unSubscribeInEurPanel(Context context, int dstAddress, int subscribeZone, int agreementId, int relateType, final IAction<Boolean> result) {
        BleNetworkCmdParam bleNetworkCmdParam = new BleNetworkCmdParam();
        bleNetworkCmdParam.setZoneNum(subscribeZone);
        bleNetworkCmdParam.setRelateType(relateType);
        bleNetworkCmdParam.setCmdType(13);
        CtrlPackage ctrlPackage = new CtrlPackage(agreementId);
        ctrlPackage.setAddress(dstAddress);
        sendWithResult(context, ctrlPackage, bleNetworkCmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda9
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null));
            }
        });
    }

    public void unSubscribePublishAddress(Context context, int publishAddress, int agreementId, int... subscribeZone) {
        if (publishAddress > 0) {
            BleNetworkCmdParam bleNetworkCmdParam = new BleNetworkCmdParam();
            bleNetworkCmdParam.setZoneNum(65535);
            bleNetworkCmdParam.setCmdType(10);
            bleNetworkCmdParam.setSubscribeAddress(publishAddress);
            if (subscribeZone.length > 0) {
                bleNetworkCmdParam.setSubscribeZone(subscribeZone[0]);
            } else {
                bleNetworkCmdParam.setSubscribeZone(65535);
            }
            CtrlPackage ctrlPackage = new CtrlPackage(agreementId);
            ctrlPackage.setAddress(65535);
            Injection.message().create(context).cmdParam(bleNetworkCmdParam).control((BaseCtrlPackage) ctrlPackage).resendTimes(4).enqueue();
        }
    }

    public void inGroup(Context context, int groupAddress, final IAction<Boolean> result) {
        BleNetworkCmdParam bleNetworkCmdParam = new BleNetworkCmdParam();
        bleNetworkCmdParam.setZoneNum(this.zoneNum);
        bleNetworkCmdParam.setCmdType(5);
        bleNetworkCmdParam.setGroupAddress(groupAddress);
        sendWithResult(context, bleNetworkCmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda19
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null && r1.getStateCode() == 0));
            }
        });
    }

    public void inGroupByPublish(Context context, int groupAddress, int publishAddress, final IAction<Boolean> result) {
        BleNetworkCmdParam bleNetworkCmdParam = new BleNetworkCmdParam();
        bleNetworkCmdParam.setZoneNum(this.zoneNum);
        bleNetworkCmdParam.setCmdType(16);
        bleNetworkCmdParam.setGroupAddress(groupAddress);
        bleNetworkCmdParam.setPublishAddress(publishAddress);
        sendWithResult(context, bleNetworkCmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda25
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null));
            }
        });
    }

    public void unBindEurPanel(Context context, final IAction<Boolean> result) {
        BleNetworkCmdParam bleNetworkCmdParam = new BleNetworkCmdParam();
        bleNetworkCmdParam.setZoneNum(this.zoneNum);
        bleNetworkCmdParam.setCmdType(17);
        sendWithResult(context, bleNetworkCmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda15
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null && r1.getStateCode() == 0));
            }
        });
    }

    public void outGroup(Context context, int groupAddress, final IAction<Boolean> result) {
        BleNetworkCmdParam bleNetworkCmdParam = new BleNetworkCmdParam();
        bleNetworkCmdParam.setZoneNum(this.zoneNum);
        bleNetworkCmdParam.setCmdType(6);
        bleNetworkCmdParam.setGroupAddress(groupAddress);
        sendWithResult(context, bleNetworkCmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda46
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null && r1.getStateCode() == 0));
            }
        });
    }

    public void daliInGroup(Context context, int groupAddress, List<Integer> lightList, final IAction<Boolean> result) {
        BleNetworkCmdParam bleNetworkCmdParam = new BleNetworkCmdParam();
        bleNetworkCmdParam.setZoneNum(1);
        bleNetworkCmdParam.setCmdType(12);
        bleNetworkCmdParam.setSubCmdType(1);
        bleNetworkCmdParam.setGroupAddress(groupAddress);
        bleNetworkCmdParam.setDaliLightList(lightList);
        sendWithResult15SecondTimeout(context, bleNetworkCmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda22
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null));
            }
        });
    }

    public void daliOutGroup(Context context, int groupAddress, List<Integer> lightList, final IAction<Boolean> result) {
        BleNetworkCmdParam bleNetworkCmdParam = new BleNetworkCmdParam();
        bleNetworkCmdParam.setZoneNum(1);
        bleNetworkCmdParam.setCmdType(12);
        bleNetworkCmdParam.setSubCmdType(2);
        bleNetworkCmdParam.setGroupAddress(groupAddress);
        bleNetworkCmdParam.setDaliLightList(lightList);
        sendWithResult15SecondTimeout(context, bleNetworkCmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda13
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null));
            }
        });
    }

    public void resetNode(Context context, final IAction<Boolean> result) {
        BleNetworkCmdParam bleNetworkCmdParam = new BleNetworkCmdParam();
        bleNetworkCmdParam.setZoneNum(this.zoneNum);
        bleNetworkCmdParam.setCmdType(3);
        if (this.controlObject instanceof Device) {
            sendWithResult(context, CtrlPackager.getDeviceCtrlPackageByUnicastAdd((Device) this.controlObject), bleNetworkCmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    IAction.this.act(Boolean.valueOf(r1 != null));
                }
            });
        } else {
            sendWithResult(context, bleNetworkCmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    IAction.this.act(Boolean.valueOf(r1 != null));
                }
            });
        }
    }

    public void setPublishAddress(Context context, int publishAddress, final IAction<Boolean> result) {
        BleNetworkCmdParam bleNetworkCmdParam = new BleNetworkCmdParam();
        bleNetworkCmdParam.setZoneNum(this.zoneNum);
        bleNetworkCmdParam.setCmdType(4);
        bleNetworkCmdParam.setPublishAddress(publishAddress);
        sendWithResult(context, bleNetworkCmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda40
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null));
            }
        });
    }

    public void setPublishAddress(Context context, CtrlPackage ctrlPackage, int publishAddress, final IAction<Boolean> result) {
        BleNetworkCmdParam bleNetworkCmdParam = new BleNetworkCmdParam();
        bleNetworkCmdParam.setZoneNum(this.zoneNum);
        bleNetworkCmdParam.setCmdType(4);
        bleNetworkCmdParam.setPublishAddress(publishAddress);
        sendWithResult(context, ctrlPackage, bleNetworkCmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda28
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null));
            }
        });
    }

    public void testDeviceLocation(Context context, CtrlPackage ctrlPackage) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setZoneNum(this.zoneNum);
        LHomeLog.i(getClass(), "testDeviceLocation1  ctrlPackage =" + ctrlPackage.toString());
        settingCmdParam.setCmdType(3);
        LHomeLog.i(getClass(), "testDeviceLocation2");
        Injection.message().create(context).cmdParam(settingCmdParam).control((BaseCtrlPackage) ctrlPackage).enqueue();
    }

    public void testDeviceLocation(Context context) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setZoneNum(this.zoneNum);
        settingCmdParam.setCmdType(3);
        Injection.message().create(context).cmdParam(settingCmdParam).sendTimes(3).control(this.controlObject).enqueue();
    }

    public void setGroupRelateInfo(Context context, int zoneCount, List<SettingCmdParam.RelateInfo> relateInfoList, final IAction<Boolean> result) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setZoneNum(this.zoneNum);
        settingCmdParam.setCmdType(9);
        settingCmdParam.setZoneCount(zoneCount);
        settingCmdParam.setRelateInfoList(relateInfoList);
        sendWithResult(context, settingCmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda11
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null && r1.getStateCode() == 0));
            }
        });
    }

    public void setServer(Context context, int type, final IAction<Boolean> result) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setZoneNum(this.zoneNum);
        settingCmdParam.setCmdType(30);
        settingCmdParam.setValue(type);
        sendWithResult(context, settingCmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda31
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null && r1.getStateCode() == 0));
            }
        });
    }

    public void setKInfo(Context context, int zoneNumber, List<SettingCmdParam.KInfo> kInfoList, final IAction<Boolean> result) {
        sendWithResult(context, setKInfo(zoneNumber, kInfoList), new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda16
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null));
            }
        });
    }

    public SettingCmdParam setKInfo(int zoneNumber, List<SettingCmdParam.KInfo> kInfoList) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setZoneNum(zoneNumber);
        settingCmdParam.setCmdType(15);
        settingCmdParam.setKInfoList(kInfoList);
        return settingCmdParam;
    }

    public void changeCurtainMotorDirection(Context context, int direction, final IAction<Boolean> result) {
        DeviceCmdParam deviceCmdParam = new DeviceCmdParam();
        deviceCmdParam.setZoneNum(this.zoneNum);
        deviceCmdParam.setCmdType(8);
        deviceCmdParam.setMotorDirection(direction);
        sendWithResult(context, deviceCmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null));
            }
        });
    }

    public DeviceCmdParam changeCurtainMotorDirection(int direction) {
        DeviceCmdParam deviceCmdParam = new DeviceCmdParam();
        deviceCmdParam.setZoneNum(this.zoneNum);
        deviceCmdParam.setCmdType(8);
        deviceCmdParam.setMotorDirection(direction);
        return deviceCmdParam;
    }

    public void setCurtainMotorMode(Context context, int modeNum, int iconIndex, int curtainPercent, final IAction<Boolean> result) {
        DeviceCmdParam deviceCmdParam = new DeviceCmdParam();
        deviceCmdParam.setZoneNum(this.zoneNum);
        deviceCmdParam.setCmdType(9);
        deviceCmdParam.setModeNum(modeNum);
        deviceCmdParam.setIconIndex(iconIndex);
        deviceCmdParam.setModeCurtainPercent(curtainPercent);
        sendWithResult(context, deviceCmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null));
            }
        });
    }

    public DeviceCmdParam setCurtainMotorMode(int modeNum, int iconIndex, int curtainPercent) {
        DeviceCmdParam deviceCmdParam = new DeviceCmdParam();
        deviceCmdParam.setZoneNum(this.zoneNum);
        deviceCmdParam.setCmdType(9);
        deviceCmdParam.setModeNum(modeNum);
        deviceCmdParam.setIconIndex(iconIndex);
        deviceCmdParam.setModeCurtainPercent(curtainPercent);
        return deviceCmdParam;
    }

    public void setCurtainMotorSetting(Context context, int manuallyPull, int motorDirection, int softStart, int limitPosition, int whenMotorStop, int memorizePosition, int syncManual, int speed, final IAction<Boolean> result) {
        DeviceCmdParam deviceCmdParam = new DeviceCmdParam();
        deviceCmdParam.setZoneNum(this.zoneNum);
        deviceCmdParam.setCmdType(14);
        deviceCmdParam.setManuallyPull(manuallyPull);
        deviceCmdParam.setMotorDirection(motorDirection);
        deviceCmdParam.setSoftStart(softStart);
        deviceCmdParam.setLimitPosition(limitPosition);
        deviceCmdParam.setWhenMotorStop(whenMotorStop);
        deviceCmdParam.setSyncManual(syncManual);
        deviceCmdParam.setMotorSpeed(speed);
        deviceCmdParam.setMemorizePosition(memorizePosition);
        sendWithResult(context, deviceCmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda36
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null));
            }
        });
    }

    public DeviceCmdParam setCurtainMotorSetting(int manuallyPull, int motorDirection, int softStart, int limitPosition, int whenMotorStop, int memorizePosition, int syncManual, int speed) {
        DeviceCmdParam deviceCmdParam = new DeviceCmdParam();
        deviceCmdParam.setZoneNum(this.zoneNum);
        deviceCmdParam.setCmdType(14);
        deviceCmdParam.setManuallyPull(manuallyPull);
        deviceCmdParam.setMotorDirection(motorDirection);
        deviceCmdParam.setSoftStart(softStart);
        deviceCmdParam.setLimitPosition(limitPosition);
        deviceCmdParam.setWhenMotorStop(whenMotorStop);
        deviceCmdParam.setSyncManual(syncManual);
        deviceCmdParam.setMotorSpeed(speed);
        deviceCmdParam.setMemorizePosition(memorizePosition);
        return deviceCmdParam;
    }

    public void setCurtainMotorSetting(Context context, int manuallyPull, int motorDirection, int softStart, int limitPosition, int whenMotorStop, int memorizePosition, int syncManual, final IAction<Boolean> result) {
        DeviceCmdParam deviceCmdParam = new DeviceCmdParam();
        deviceCmdParam.setZoneNum(this.zoneNum);
        deviceCmdParam.setCmdType(11);
        deviceCmdParam.setManuallyPull(manuallyPull);
        deviceCmdParam.setMotorDirection(motorDirection);
        deviceCmdParam.setSoftStart(softStart);
        deviceCmdParam.setLimitPosition(limitPosition);
        deviceCmdParam.setWhenMotorStop(whenMotorStop);
        deviceCmdParam.setSyncManual(syncManual);
        deviceCmdParam.setMemorizePosition(memorizePosition);
        sendWithResult(context, deviceCmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda26
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null));
            }
        });
    }

    public DeviceCmdParam setCurtainMotorSetting(int manuallyPull, int motorDirection, int softStart, int limitPosition, int whenMotorStop, int memorizePosition, int syncManual) {
        DeviceCmdParam deviceCmdParam = new DeviceCmdParam();
        deviceCmdParam.setZoneNum(this.zoneNum);
        deviceCmdParam.setCmdType(11);
        deviceCmdParam.setManuallyPull(manuallyPull);
        deviceCmdParam.setMotorDirection(motorDirection);
        deviceCmdParam.setSoftStart(softStart);
        deviceCmdParam.setLimitPosition(limitPosition);
        deviceCmdParam.setWhenMotorStop(whenMotorStop);
        deviceCmdParam.setSyncManual(syncManual);
        deviceCmdParam.setMemorizePosition(memorizePosition);
        return deviceCmdParam;
    }

    public void adjustLimitPosition(Context context, final IAction<Boolean> result) {
        DeviceCmdParam deviceCmdParam = new DeviceCmdParam();
        deviceCmdParam.setZoneNum(this.zoneNum);
        deviceCmdParam.setCmdType(12);
        sendWithResult(context, deviceCmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null));
            }
        });
    }

    public void setSmartPanelMode(Context context, int doubleLight, int reverseLight, int nightMode, int engravedTextMode, int startH, int startM, int endH, int endM, final IAction<Boolean> result) {
        sendWithResult(context, setSmartPanelMode(doubleLight, reverseLight, nightMode, engravedTextMode, startH, startM, endH, endM), new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda10
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null));
            }
        });
    }

    public SmartPanelParam setSmartPanelMode(int doubleLight, int reverseLight, int nightMode, int engravedTextMode, int startH, int startM, int endH, int endM) {
        SmartPanelParam smartPanelParam = new SmartPanelParam();
        smartPanelParam.setZoneNum(this.zoneNum);
        smartPanelParam.setCmdType(1);
        smartPanelParam.setDoubleLight(doubleLight);
        smartPanelParam.setReverseLight(reverseLight);
        smartPanelParam.setNightMode(nightMode);
        smartPanelParam.setEngravedTextMode(engravedTextMode);
        smartPanelParam.setStartH(startH);
        smartPanelParam.setStartM(startM);
        smartPanelParam.setEndH(endH);
        smartPanelParam.setEndM(endM);
        return smartPanelParam;
    }

    public void starUpgrade(Context context) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setZoneNum(this.zoneNum);
        settingCmdParam.setCmdType(10);
        sendWithoutResult(context, settingCmdParam);
    }

    public void starUpgrade(Context context, final IAction<Boolean> iAction) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setZoneNum(this.zoneNum);
        settingCmdParam.setCmdType(10);
        sendWithResult(context, settingCmdParam, new IAction<ResponseMsg>(this) { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant.1
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg msg) {
                IAction iAction2 = iAction;
                if (iAction2 != null) {
                    iAction2.act(Boolean.valueOf(msg != null));
                }
            }
        });
    }

    public void setDeviceTTlFF(Context context, int ttl) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setCmdType(11);
        settingCmdParam.setZoneNum(this.zoneNum);
        CtrlPackage ctrlPackage = new CtrlPackage(2);
        ctrlPackage.setAddress(65535);
        settingCmdParam.setTtl(ttl);
        sendWithoutResult(context, ctrlPackage, settingCmdParam);
    }

    public void setDeviceFrequencyFF(Context context, int frequency, int interval) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setCmdType(12);
        settingCmdParam.setZoneNum(this.zoneNum);
        CtrlPackage ctrlPackage = new CtrlPackage(2);
        ctrlPackage.setAddress(65535);
        settingCmdParam.setFrequency(frequency);
        settingCmdParam.setInterval(interval);
        sendWithoutResult(context, ctrlPackage, settingCmdParam);
    }

    public void setDeviceTTl(Context context, int ttl, final IAction<Boolean> result) {
        sendWithResult(context, setDeviceTTl(ttl), new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda7
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null));
            }
        });
    }

    public SettingCmdParam setDeviceTTl(int ttl) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setCmdType(11);
        settingCmdParam.setZoneNum(this.zoneNum);
        settingCmdParam.setTtl(ttl);
        return settingCmdParam;
    }

    public void cleanDeviceCache(Context context, final IAction<Boolean> result) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setCmdType(18);
        settingCmdParam.setZoneNum(this.zoneNum);
        sendWithResult(context, settingCmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda27
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null));
            }
        });
    }

    public void setDeviceFrequency(Context context, int frequency, int interval, final IAction<Boolean> result) {
        sendWithResult(context, setDeviceFrequency(frequency, interval), new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda41
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null));
            }
        });
    }

    public SettingCmdParam setDeviceFrequency(int frequency, int interval) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setCmdType(12);
        settingCmdParam.setZoneNum(this.zoneNum);
        settingCmdParam.setFrequency(frequency);
        settingCmdParam.setInterval(interval);
        return settingCmdParam;
    }

    public void setTrigType(Context context, CtrlPackage ctrlPackage, int deviceType, final IAction<Boolean> result) {
        sendWithResult(context, ctrlPackage, setTrigType(deviceType), new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda42
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null && r1.getStateCode() == 0));
            }
        });
    }

    public void setTrigType(Context context, int deviceType, final IAction<Boolean> result) {
        sendWithResult(context, setTrigType(deviceType), new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda12
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null && r1.getStateCode() == 0));
            }
        });
    }

    public SettingCmdParam setTrigType(int deviceType) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setZoneNum(this.zoneNum);
        settingCmdParam.setCmdType(13);
        settingCmdParam.setDeviceType(deviceType);
        return settingCmdParam;
    }

    public void setTbType(Context context, CtrlPackage ctrlPackage, int deviceType, final IAction<Boolean> result) {
        sendWithResult(context, ctrlPackage, setTbType(deviceType), new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda24
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null && r1.getStateCode() == 0));
            }
        });
    }

    public void setTbType(Context context, int deviceType, final IAction<Boolean> result) {
        sendWithResult(context, setTbType(deviceType), new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda35
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null && r1.getStateCode() == 0));
            }
        });
    }

    public SettingCmdParam setTbType(int deviceType) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setZoneNum(this.zoneNum);
        settingCmdParam.setCmdType(14);
        settingCmdParam.setDeviceType(deviceType);
        return settingCmdParam;
    }

    public void sendEurDeviceType(Context context, CtrlPackage ctrlPackage, int zoneControl, int controlType, final IAction<Boolean> result) {
        sendWithResult(context, ctrlPackage, sendEurDeviceType(zoneControl, controlType), new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda29
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null));
            }
        });
    }

    public SettingCmdParam sendEurDeviceType(int zoneControl, int controlType) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setZoneNum(this.zoneNum);
        settingCmdParam.setCmdType(21);
        settingCmdParam.setZoneControl(zoneControl);
        settingCmdParam.setControlType(controlType);
        settingCmdParam.setButtonFunction(controlType <= 2 ? 1 : 2);
        return settingCmdParam;
    }

    public void setSpiLoadParam(Context context, int lineOrder, int icType, int pixel, final IAction<Boolean> result) {
        sendWithResultInterval(context, setSpiLoadParam(lineOrder, icType, pixel), 1, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda18
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null && r1.getStateCode() == 0));
            }
        });
    }

    public SettingCmdParam setSpiLoadParam(int lineOrder, int icType, int pixel) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setZoneNum(this.zoneNum);
        settingCmdParam.setCmdType(16);
        settingCmdParam.setLineOrder(lineOrder);
        settingCmdParam.setIcType(icType);
        settingCmdParam.setPixel(pixel);
        return settingCmdParam;
    }

    public void setConstantPower(Context context, int lineOrder, final IAction<Boolean> result) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setZoneNum(this.zoneNum);
        settingCmdParam.setCmdType(17);
        settingCmdParam.setIsOpen(lineOrder);
        sendWithResult(context, settingCmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null));
            }
        });
    }

    public void setDeviceNDID(Context context, int address, final IAction<Boolean> result) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setZoneNum(this.zoneNum);
        settingCmdParam.setCmdType(19);
        settingCmdParam.setValue(address);
        sendWithResult(context, settingCmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda37
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null && r1.getStateCode() == 0));
            }
        });
    }

    public void restoreDeviceData(Context context, final IAction<Boolean> result) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setCmdType(20);
        settingCmdParam.setZoneNum(this.zoneNum);
        sendWithResult(context, settingCmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda30
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null));
            }
        });
    }

    public void setBrtButton(Context context, int brt, IAction<Boolean> result) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setZoneNum(this.zoneNum);
        settingCmdParam.setCmdType(22);
        settingCmdParam.setValue(brt);
        save(context, settingCmdParam, result);
    }

    public SettingCmdParam setBrtButton(int brt) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setZoneNum(this.zoneNum);
        settingCmdParam.setCmdType(22);
        settingCmdParam.setValue(brt);
        return settingCmdParam;
    }

    public void setSensorSensitivity(Context context, int v, IAction<Boolean> result) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setZoneNum(this.zoneNum);
        settingCmdParam.setCmdType(23);
        settingCmdParam.setValue(v);
        save(context, settingCmdParam, result);
    }

    public void setSensorReportInterval(Context context, int v, IAction<Boolean> result) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setZoneNum(this.zoneNum);
        settingCmdParam.setCmdType(24);
        settingCmdParam.setValue(v);
        save(context, settingCmdParam, result);
    }

    public void setSmartPanelNightUpMode(Context context, int cmd, List<Integer> data, final IAction<ResponseMsg> result) {
        SmartPanelParam smartPanelParam = new SmartPanelParam();
        smartPanelParam.setZoneNum(this.zoneNum);
        smartPanelParam.setCmdType(17);
        smartPanelParam.setCmd(cmd);
        smartPanelParam.setData(data);
        saveWithResponse(context, smartPanelParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda23
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act((ResponseMsg) obj);
            }
        });
    }

    public SmartPanelParam setSmartPanelNightUpMode(int cmd, List<Integer> data) {
        SmartPanelParam smartPanelParam = new SmartPanelParam();
        smartPanelParam.setZoneNum(this.zoneNum);
        smartPanelParam.setCmdType(17);
        smartPanelParam.setCmd(cmd);
        smartPanelParam.setData(data);
        return smartPanelParam;
    }

    public void setControlType(Context context, int type, final IAction<Boolean> result) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setZoneNum(this.zoneNum);
        settingCmdParam.setCmdType(25);
        settingCmdParam.setControlType(type);
        sendWithResult(context, (BaseCmdParam) settingCmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda38
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null));
            }
        }, true);
    }

    public SettingCmdParam setControlType(int type) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setZoneNum(this.zoneNum);
        settingCmdParam.setCmdType(25);
        settingCmdParam.setControlType(type);
        return settingCmdParam;
    }

    public void setDeviceKey(Context context, byte[] deviceKey, final IAction<Boolean> result) {
        ArrayList arrayList = new ArrayList();
        for (byte b2 : deviceKey) {
            arrayList.add(Integer.valueOf(b2));
        }
        BleNetworkCmdParam bleNetworkCmdParam = new BleNetworkCmdParam();
        bleNetworkCmdParam.setZoneNum(this.zoneNum);
        bleNetworkCmdParam.setBindData(arrayList);
        bleNetworkCmdParam.setCmdType(18);
        sendWithResult(context, bleNetworkCmdParam, new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda33
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null && r1.getStateCode() == 0));
            }
        });
    }

    public void queryMatterCode(Context context, IAction<ResponseMsg> result) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setCmdType(26);
        settingCmdParam.setZoneNum(this.zoneNum);
        sendWithResultWithTimeout(context, settingCmdParam, result, 10000);
    }

    public void queryMatterFabric(Context context, IAction<ResponseMsg> result) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setCmdType(27);
        settingCmdParam.setZoneNum(this.zoneNum);
        sendWithResultWithTimeout(context, settingCmdParam, result, 6000);
    }

    public void removeMatterFabric(Context context, int pos, IAction<ResponseMsg> result) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setCmdType(28);
        settingCmdParam.setZoneNum(this.zoneNum);
        settingCmdParam.setValue(pos);
        sendWithResultWithTimeout(context, settingCmdParam, result, 6000);
    }

    public void setDaliBusPower(Context context, int state, IAction<Boolean> result) {
        save(context, setDaliBusPower(state), result);
    }

    public SettingCmdParam setDaliBusPower(int state) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setZoneNum(this.zoneNum);
        settingCmdParam.setCmdType(29);
        settingCmdParam.setValue(state);
        return settingCmdParam;
    }

    public void setRgbInterface(Context context, int rgbInterface, IAction<Boolean> result) {
        save(context, setRgbInterface(rgbInterface), result);
    }

    public SettingCmdParam setRgbInterface(int rgbInterface) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setZoneNum(this.zoneNum);
        settingCmdParam.setCmdType(34);
        settingCmdParam.setValue(rgbInterface);
        return settingCmdParam;
    }

    public void quitMatter(Context context, IAction<ResponseMsg> result) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setCmdType(31);
        settingCmdParam.setZoneNum(this.zoneNum);
        sendWithResult(context, settingCmdParam, result);
    }

    public void syncMatter(Context context, IAction<ResponseMsg> result) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setCmdType(32);
        settingCmdParam.setZoneNum(this.zoneNum);
        sendWithResult(context, settingCmdParam, result);
    }

    public void sceneSyncMatter(Context context, IAction<ResponseMsg> result) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setCmdType(39);
        settingCmdParam.setZoneNum(this.zoneNum);
        sendWithResult(context, settingCmdParam, result);
    }

    public void setDimSignal(Context context, int dimSignal, IAction<Boolean> result) {
        save(context, setDimSignal(dimSignal), result);
    }

    public SettingCmdParam setDimSignal(int dimSignal) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setZoneNum(this.zoneNum);
        settingCmdParam.setCmdType(33);
        settingCmdParam.setValue(dimSignal);
        return settingCmdParam;
    }

    public void setKeySave(Context context, boolean enable, final IAction<Boolean> result) {
        sendWithResult(context, setKeySave(enable), new IAction() { // from class: com.ltech.smarthome.utils.cmd_assistant.SettingAssistant$$ExternalSyntheticLambda48
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(Boolean.valueOf(r1 != null && r1.getStateCode() == 0));
            }
        });
    }

    public SettingCmdParam setKeySave(boolean z) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setZoneNum(this.zoneNum);
        settingCmdParam.setCmdType(35);
        settingCmdParam.setValue(z ? 1 : 0);
        return settingCmdParam;
    }

    public void setNoMotionTime(Context context, int time, IAction<Boolean> result) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setZoneNum(this.zoneNum);
        settingCmdParam.setCmdType(36);
        settingCmdParam.setValue(time);
        save(context, settingCmdParam, result);
    }

    public void setMotionTime(Context context, int time, IAction<Boolean> result) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setZoneNum(this.zoneNum);
        settingCmdParam.setCmdType(37);
        settingCmdParam.setValue(time);
        save(context, settingCmdParam, result);
    }

    public void setSingleReport(Context context, boolean z, IAction<Boolean> iAction) {
        SettingCmdParam settingCmdParam = new SettingCmdParam();
        settingCmdParam.setZoneNum(this.zoneNum);
        settingCmdParam.setCmdType(38);
        settingCmdParam.setValue(z ? 1 : 0);
        save(context, settingCmdParam, iAction);
    }
}