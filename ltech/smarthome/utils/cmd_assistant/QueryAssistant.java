package com.ltech.smarthome.utils.cmd_assistant;

import android.content.Context;
import com.ltech.smarthome.base.IAction;
import com.smart.message.ResponseMsg;
import com.smart.product_agreement.param.DeviceCmdParam;
import com.smart.product_agreement.param.QueryCmdParam;

/* loaded from: classes4.dex */
public class QueryAssistant extends BaseAssistant {
    public void queryPanelState(Context context, IAction<ResponseMsg> result, int type) {
        QueryCmdParam queryCmdParam = new QueryCmdParam();
        queryCmdParam.setCmdType(1);
        queryCmdParam.setZoneNum(this.zoneNum);
        sendWithResult(context, queryCmdParam, result);
    }

    public void queryPanelState(Context context, IAction<ResponseMsg> result) {
        QueryCmdParam queryCmdParam = new QueryCmdParam();
        queryCmdParam.setCmdType(1);
        queryCmdParam.setZoneNum(this.zoneNum);
        sendWithResult(context, queryCmdParam, result);
    }

    public void queryPanelUpNightState(Context context, IAction<ResponseMsg> result) {
        QueryCmdParam queryCmdParam = new QueryCmdParam();
        queryCmdParam.setCmdType(14);
        queryCmdParam.setZoneNum(this.zoneNum);
        sendWithResult(context, queryCmdParam, result);
    }

    public void queryPanelScreenState(Context context, IAction<ResponseMsg> result) {
        QueryCmdParam queryCmdParam = new QueryCmdParam();
        queryCmdParam.setCmdType(13);
        queryCmdParam.setZoneNum(this.zoneNum);
        sendWithResult(context, queryCmdParam, result);
    }

    public void queryProductVersion(Context context, IAction<ResponseMsg> result) {
        QueryCmdParam queryCmdParam = new QueryCmdParam();
        queryCmdParam.setCmdType(2);
        queryCmdParam.setZoneNum(this.zoneNum);
        sendWithResult(context, queryCmdParam, result);
    }

    public void queryMotorVersion(Context context, IAction<ResponseMsg> result) {
        QueryCmdParam queryCmdParam = new QueryCmdParam();
        queryCmdParam.setCmdType(7);
        queryCmdParam.setZoneNum(this.zoneNum);
        sendWithResult(context, queryCmdParam, result);
    }

    public void queryCurtainMotorState(Context context) {
        QueryCmdParam queryCmdParam = new QueryCmdParam();
        queryCmdParam.setCmdType(1);
        queryCmdParam.setZoneNum(this.zoneNum);
        sendWithoutResult(context, queryCmdParam);
    }

    public void queryPanelDeviceMotorState(Context context) {
        QueryCmdParam queryCmdParam = new QueryCmdParam();
        queryCmdParam.setCmdType(1);
        queryCmdParam.setZoneNum(this.zoneNum);
        sendWithoutResult(context, queryCmdParam);
    }

    public void queryLightState(Context context) {
        QueryCmdParam queryCmdParam = new QueryCmdParam();
        queryCmdParam.setCmdType(1);
        queryCmdParam.setZoneNum(this.zoneNum);
        sendWithoutResult(context, queryCmdParam);
    }

    public void queryPanelState(Context context) {
        QueryCmdParam queryCmdParam = new QueryCmdParam();
        queryCmdParam.setCmdType(1);
        queryCmdParam.setZoneNum(this.zoneNum);
        sendWithoutResult(context, queryCmdParam);
    }

    public void queryLightState(Context context, int zoneNum) {
        QueryCmdParam queryCmdParam = new QueryCmdParam();
        queryCmdParam.setCmdType(1);
        queryCmdParam.setZoneNum(zoneNum);
        sendWithoutResult(context, queryCmdParam);
    }

    public void queryWaveSensorState(Context context) {
        QueryCmdParam queryCmdParam = new QueryCmdParam();
        queryCmdParam.setCmdType(1);
        queryCmdParam.setZoneNum(this.zoneNum);
        sendWithoutResult(context, queryCmdParam);
    }

    public void queryCtRange(Context context, IAction<ResponseMsg> result) {
        QueryCmdParam queryCmdParam = new QueryCmdParam();
        queryCmdParam.setCmdType(6);
        queryCmdParam.setZoneNum(this.zoneNum);
        sendWithResult(context, queryCmdParam, result);
    }

    public void queryCurtainMotorSetting(Context context, IAction<ResponseMsg> result) {
        DeviceCmdParam deviceCmdParam = new DeviceCmdParam();
        deviceCmdParam.setZoneNum(this.zoneNum);
        deviceCmdParam.setCmdType(10);
        sendWithResult(context, deviceCmdParam, result);
    }

    public void querySmartPanelSetting(Context context, IAction<ResponseMsg> result) {
        QueryCmdParam queryCmdParam = new QueryCmdParam();
        queryCmdParam.setZoneNum(this.zoneNum);
        queryCmdParam.setCmdType(3);
        sendWithResult(context, queryCmdParam, result);
    }

    public void queryKnobPanelSetting(Context context, IAction<ResponseMsg> result) {
        QueryCmdParam queryCmdParam = new QueryCmdParam();
        queryCmdParam.setZoneNum(this.zoneNum);
        queryCmdParam.setCmdType(4);
        sendWithResult(context, queryCmdParam, result);
    }

    public void queryScreenPanelIconStart(Context context, IAction<ResponseMsg> result) {
        QueryCmdParam queryCmdParam = new QueryCmdParam();
        queryCmdParam.setZoneNum(this.zoneNum);
        queryCmdParam.setCmdType(5);
        sendWithResult(context, queryCmdParam, result);
    }

    public void queryWaveSensorSetting(Context context, boolean is24G, IAction<ResponseMsg> result) {
        QueryCmdParam queryCmdParam = new QueryCmdParam();
        if (is24G) {
            queryCmdParam.setCmdType(15);
        } else {
            queryCmdParam.setCmdType(8);
        }
        queryCmdParam.setZoneNum(this.zoneNum);
        sendWithResult(context, queryCmdParam, result);
    }

    public void querySupportBleUpgrade(Context context, IAction<ResponseMsg> result) {
        QueryCmdParam queryCmdParam = new QueryCmdParam();
        queryCmdParam.setCmdType(9);
        queryCmdParam.setZoneNum(this.zoneNum);
        sendWithResult(context, queryCmdParam, result);
    }

    public void queryConstantPower(Context context, IAction<ResponseMsg> result) {
        QueryCmdParam queryCmdParam = new QueryCmdParam();
        queryCmdParam.setCmdType(10);
        queryCmdParam.setZoneNum(this.zoneNum);
        sendWithResult(context, queryCmdParam, result);
    }

    public void querySupportReplace(Context context, IAction<ResponseMsg> result) {
        QueryCmdParam queryCmdParam = new QueryCmdParam();
        queryCmdParam.setCmdType(11);
        queryCmdParam.setZoneNum(this.zoneNum);
        sendWithResult(context, queryCmdParam, result);
    }

    public void queryCg485Setting(Context context, IAction<ResponseMsg> result) {
        QueryCmdParam queryCmdParam = new QueryCmdParam();
        queryCmdParam.setCmdType(12);
        queryCmdParam.setZoneNum(this.zoneNum);
        sendWithResult(context, queryCmdParam, result);
    }

    public void queryControlType(Context context, IAction<ResponseMsg> result) {
        QueryCmdParam queryCmdParam = new QueryCmdParam();
        queryCmdParam.setCmdType(16);
        queryCmdParam.setZoneNum(this.zoneNum);
        sendWithResult(context, queryCmdParam, result);
    }

    public void queryE6BindParam(Context context, IAction<ResponseMsg> result) {
        QueryCmdParam queryCmdParam = new QueryCmdParam();
        queryCmdParam.setCmdType(18);
        queryCmdParam.setZoneNum(this.zoneNum);
        sendWithResult(context, queryCmdParam, result);
    }

    public void queryE6Setting(Context context, IAction<ResponseMsg> result) {
        QueryCmdParam queryCmdParam = new QueryCmdParam();
        queryCmdParam.setCmdType(17);
        queryCmdParam.setZoneNum(this.zoneNum);
        sendWithResult(context, queryCmdParam, result);
    }

    public void queryDeviceLog(Context context, int sequence, IAction<ResponseMsg> result) {
        QueryCmdParam queryCmdParam = new QueryCmdParam();
        queryCmdParam.setCmdType(19);
        queryCmdParam.setZoneNum(this.zoneNum);
        queryCmdParam.setValue(sequence);
        sendWithResult(context, queryCmdParam, result);
    }

    public void queryOutPutType(Context context, IAction<ResponseMsg> result) {
        QueryCmdParam queryCmdParam = new QueryCmdParam();
        queryCmdParam.setCmdType(20);
        queryCmdParam.setZoneNum(this.zoneNum);
        sendWithResult(context, queryCmdParam, result);
    }

    public void queryKeySave(Context context, IAction<ResponseMsg> result) {
        QueryCmdParam queryCmdParam = new QueryCmdParam();
        queryCmdParam.setCmdType(21);
        queryCmdParam.setZoneNum(this.zoneNum);
        sendWithResult(context, queryCmdParam, result);
    }

    public void queryPowerOnOffScene(Context context, IAction<ResponseMsg> result) {
        QueryCmdParam queryCmdParam = new QueryCmdParam();
        queryCmdParam.setCmdType(22);
        queryCmdParam.setZoneNum(this.zoneNum);
        sendWithResult(context, queryCmdParam, result);
    }
}