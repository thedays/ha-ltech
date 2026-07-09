package com.ltech.smarthome.utils.cmd_assistant;

import android.content.Context;
import android.text.TextUtils;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.message.CtrlPackage;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;
import com.smart.message.ResponseMsg;
import com.smart.message.base.BaseCmdParam;
import com.smart.message.base.BaseCtrlPackage;
import com.smart.product_agreement.param.SceneCmdParam;

/* loaded from: classes4.dex */
public class SceneAssistant extends BaseAssistant {
    public void executeScene(Context context, int sceneNum, IAction<Boolean> actions) {
        SceneCmdParam sceneCmdParam = new SceneCmdParam();
        sceneCmdParam.setCmdType(2);
        sceneCmdParam.setZoneNum(this.zoneNum);
        sceneCmdParam.setSceneNum(sceneNum);
        play(context, sceneCmdParam, actions);
    }

    public void saveScene(Context context, int sceneNum, IAction<Boolean> result) {
        SceneCmdParam sceneCmdParam = new SceneCmdParam();
        sceneCmdParam.setCmdType(1);
        sceneCmdParam.setZoneNum(this.zoneNum);
        sceneCmdParam.setSceneNum(sceneNum);
        save(context, sceneCmdParam, result);
    }

    public void executeLocalScene(Context context, int sceneNum) {
        SceneCmdParam executeLocalScene = executeLocalScene(sceneNum);
        CtrlPackage ctrlPackage = new CtrlPackage(2);
        ctrlPackage.setAddress(65025);
        Injection.message().create(context).cmdParam(executeLocalScene).sendTimes(3).control((BaseCtrlPackage) ctrlPackage).enqueue();
    }

    public SceneCmdParam executeLocalScene(int sceneNum) {
        SceneCmdParam sceneCmdParam = new SceneCmdParam();
        sceneCmdParam.setCmdType(7);
        sceneCmdParam.setZoneNum(this.zoneNum);
        sceneCmdParam.setSceneNum(sceneNum);
        return sceneCmdParam;
    }

    public void executeEurCurrentScene(Context context, int position, IAction<ResponseMsg> result) {
        SceneCmdParam sceneCmdParam = new SceneCmdParam();
        sceneCmdParam.setCmdType(13);
        sceneCmdParam.setZoneNum(this.zoneNum);
        sceneCmdParam.setPosition(position);
        sendWithResult(context, (BaseCmdParam) sceneCmdParam, result, true);
    }

    public void saveLocalScene(Context context, int sceneNum) {
        SceneCmdParam sceneCmdParam = new SceneCmdParam();
        sceneCmdParam.setCmdType(3);
        sceneCmdParam.setZoneNum(this.zoneNum);
        CtrlPackage ctrlPackage = new CtrlPackage(2);
        ctrlPackage.setAddress(65535);
        sceneCmdParam.setSceneNum(sceneNum);
        Injection.message().create(context).cmdParam(sceneCmdParam).sendTimes(10).control((BaseCtrlPackage) ctrlPackage).enqueue();
    }

    public void delLocalScene(Context context, int sceneNum) {
        SceneCmdParam sceneCmdParam = new SceneCmdParam();
        sceneCmdParam.setCmdType(6);
        sceneCmdParam.setZoneNum(this.zoneNum);
        CtrlPackage ctrlPackage = new CtrlPackage(2);
        ctrlPackage.setAddress(65025);
        sceneCmdParam.setSceneNum(sceneNum);
        Injection.message().create(context).cmdParam(sceneCmdParam).sendTimes(10).control((BaseCtrlPackage) ctrlPackage).enqueue();
    }

    public void delDeviceLocalScene(Context context, int sceneNum) {
        SceneCmdParam sceneCmdParam = new SceneCmdParam();
        sceneCmdParam.setCmdType(5);
        sceneCmdParam.setSceneNum(sceneNum);
        sceneCmdParam.setZoneNum(this.zoneNum);
        CtrlPackage ctrlPackage = new CtrlPackage(2);
        ctrlPackage.setAddress(65025);
        sceneCmdParam.setSceneNum(sceneNum);
        Injection.message().create(context).cmdParam(sceneCmdParam).sendTimes(5).control((BaseCtrlPackage) ctrlPackage).enqueue();
    }

    public void clearLocalSceneCache(Context context, int sceneNum) {
        SceneCmdParam sceneCmdParam = new SceneCmdParam();
        sceneCmdParam.setCmdType(8);
        sceneCmdParam.setZoneNum(this.zoneNum);
        CtrlPackage ctrlPackage = new CtrlPackage(2);
        ctrlPackage.setAddress(65535);
        sceneCmdParam.setSceneNum(sceneNum);
        Injection.message().create(context).cmdParam(sceneCmdParam).sendTimes(3).control((BaseCtrlPackage) ctrlPackage).enqueue();
    }

    public void syncLocalSceneAction(Context context, int sceneNum, String instruct, int step, int time, int zoneNum, int address, boolean isCurState, int infraredType, IAction<ResponseMsg> result) {
        if (TextUtils.isEmpty(instruct)) {
            result.act(null);
        } else {
            sendWithResult(context, syncLocalSceneAction(sceneNum, instruct, step, time, zoneNum, address, isCurState, infraredType), result);
        }
    }

    public SceneCmdParam syncLocalSceneAction(int sceneNum, String instruct, int step, int time, int zoneNum, int address, boolean isCurState, int infraredType) {
        SceneCmdParam sceneCmdParam = new SceneCmdParam();
        sceneCmdParam.setCmdType(9);
        sceneCmdParam.setSceneNum(sceneNum);
        sceneCmdParam.setStep(step);
        sceneCmdParam.setSceneOnTime(time);
        sceneCmdParam.setData(instruct);
        sceneCmdParam.setZoneNum(zoneNum);
        sceneCmdParam.setAddress(address);
        sceneCmdParam.setCurState(isCurState);
        sceneCmdParam.setInfraredType(infraredType);
        return sceneCmdParam;
    }

    public void saveLocalSceneAction(Context context, int sceneNum, String instruct, int step, int time, int zoneNum, int address, boolean isCurState, int infraredType, IAction<ResponseMsg> result) {
        if (!isCurState && TextUtils.isEmpty(instruct)) {
            result.act(null);
            return;
        }
        SceneCmdParam sceneCmdParam = new SceneCmdParam();
        sceneCmdParam.setCmdType(4);
        sceneCmdParam.setSceneNum(sceneNum);
        sceneCmdParam.setStep(step);
        sceneCmdParam.setSceneOnTime(time);
        sceneCmdParam.setData(instruct);
        sceneCmdParam.setZoneNum(zoneNum);
        sceneCmdParam.setAddress(address);
        sceneCmdParam.setCurState(isCurState);
        sceneCmdParam.setInfraredType(infraredType);
        sendWithResult(context, sceneCmdParam, result);
    }

    public void delDeviceLocalScene(Context context, int sceneNum, IAction<ResponseMsg> result) {
        SceneCmdParam sceneCmdParam = new SceneCmdParam();
        sceneCmdParam.setCmdType(5);
        sceneCmdParam.setSceneNum(sceneNum);
        sendWithResult(context, sceneCmdParam, result);
    }

    public void executeDaliScene(Context context, int sceneNum) {
        SceneCmdParam sceneCmdParam = new SceneCmdParam();
        sceneCmdParam.setCmdType(11);
        sceneCmdParam.setZoneNum(DaliProHelper.BROADCAST_ADD);
        sceneCmdParam.setSceneNum(sceneNum);
        Injection.message().create(context).cmdParam(sceneCmdParam).sendTimes(3).control(this.controlObject).enqueue();
    }

    public SceneCmdParam executeDaliScene(int sceneNum) {
        SceneCmdParam sceneCmdParam = new SceneCmdParam();
        sceneCmdParam.setCmdType(11);
        sceneCmdParam.setZoneNum(DaliProHelper.BROADCAST_ADD);
        sceneCmdParam.setSceneNum(sceneNum);
        return sceneCmdParam;
    }

    public void saveDaliSceneAction(Context context, int sceneNum, int zoneNum, String instruct, IAction<ResponseMsg> result) {
        if (TextUtils.isEmpty(instruct)) {
            result.act(null);
            return;
        }
        SceneCmdParam sceneCmdParam = new SceneCmdParam();
        sceneCmdParam.setCmdType(10);
        sceneCmdParam.setZoneNum(zoneNum);
        sceneCmdParam.setSceneNum(sceneNum);
        sceneCmdParam.setData(instruct);
        sendWithResult(context, sceneCmdParam, result);
    }

    public void delDaliScene(Context context, int sceneNum, IAction<ResponseMsg> result) {
        SceneCmdParam sceneCmdParam = new SceneCmdParam();
        sceneCmdParam.setCmdType(12);
        sceneCmdParam.setZoneNum(DaliProHelper.BROADCAST_ADD);
        sceneCmdParam.setSceneNum(sceneNum);
        sendWithResult(context, sceneCmdParam, result);
    }
}