package com.ltech.smarthome.utils.cmd_assistant;

import android.content.Context;
import com.ltech.smarthome.base.IAction;
import com.smart.message.ResponseMsg;
import com.smart.product_agreement.bean.RhythmsPlanInfo;
import com.smart.product_agreement.param.RhythmsCmdParam;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class RhythmsAssistant extends BaseAssistant {
    public void queryRhythmsSetting(Context context, IAction<ResponseMsg> result) {
        RhythmsCmdParam rhythmsCmdParam = new RhythmsCmdParam();
        rhythmsCmdParam.setZoneNum(this.zoneNum);
        rhythmsCmdParam.setCmdType(1);
        sendWithResult(context, rhythmsCmdParam, result);
    }

    public void queryDeviceSupport(Context context, IAction<ResponseMsg> result) {
        RhythmsCmdParam rhythmsCmdParam = new RhythmsCmdParam();
        rhythmsCmdParam.setZoneNum(this.zoneNum);
        rhythmsCmdParam.setCmdType(5);
        sendWithResult(context, rhythmsCmdParam, result);
    }

    public void queryRhythmsMode(Context context, int modeNum, IAction<ResponseMsg> result) {
        RhythmsCmdParam rhythmsCmdParam = new RhythmsCmdParam();
        rhythmsCmdParam.setZoneNum(this.zoneNum);
        rhythmsCmdParam.setModeNum(modeNum);
        rhythmsCmdParam.setCmdType(6);
        sendWithResult(context, rhythmsCmdParam, result);
    }

    public void controlRhythms(Context context, int code, List<Integer> data, IAction<ResponseMsg> result) {
        RhythmsCmdParam rhythmsCmdParam = new RhythmsCmdParam();
        rhythmsCmdParam.setZoneNum(this.zoneNum);
        rhythmsCmdParam.setRhythmsCode(code);
        rhythmsCmdParam.setRhythmsData(data);
        rhythmsCmdParam.setCmdType(2);
        sendWithWaitDialog(context, rhythmsCmdParam, result);
    }

    public void onOffRhythms(Context context, int state, IAction<ResponseMsg> result) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(state));
        controlRhythms(context, 1, arrayList, result);
    }

    public void onOffGroupRhythms(Context context, int state) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(state));
        RhythmsCmdParam rhythmsCmdParam = new RhythmsCmdParam();
        rhythmsCmdParam.setZoneNum(this.zoneNum);
        rhythmsCmdParam.setRhythmsCode(1);
        rhythmsCmdParam.setRhythmsData(arrayList);
        rhythmsCmdParam.setCmdType(2);
        sendOnOffWithoutResult(context, rhythmsCmdParam, 3);
    }

    public void playRhythms(Context context, int isPlay, IAction<ResponseMsg> result) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(isPlay));
        controlRhythms(context, 2, arrayList, result);
    }

    public void setRhythmsMode(Context context, int mode, IAction<ResponseMsg> result) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(mode));
        controlRhythms(context, 3, arrayList, result);
    }

    public void setRhythmsPlanMode(Context context, int mode, int plan, IAction<ResponseMsg> result) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(mode));
        arrayList.add(Integer.valueOf(plan));
        controlRhythms(context, 3, arrayList, result);
    }

    public void setRhythmsTime(Context context, int startH, int startM, int endH, int endM, IAction<ResponseMsg> result) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(startH));
        arrayList.add(Integer.valueOf(startM));
        arrayList.add(Integer.valueOf(endH));
        arrayList.add(Integer.valueOf(endM));
        controlRhythms(context, 4, arrayList, result);
    }

    public void setRhythmsWeek(Context context, int week, IAction<ResponseMsg> result) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(week));
        controlRhythms(context, 5, arrayList, result);
    }

    public void saveRhythmsPlanInfo(Context context, int modeNum, boolean isOn, int repeatTimes, int gradientMode, int playTime, List<RhythmsPlanInfo.Item> contentItemList, IAction<Boolean> result) {
        RhythmsCmdParam rhythmsCmdParam = new RhythmsCmdParam();
        rhythmsCmdParam.setZoneNum(this.zoneNum);
        rhythmsCmdParam.setCmdType(3);
        rhythmsCmdParam.setPlayType(4);
        rhythmsCmdParam.setModeNum(modeNum);
        rhythmsCmdParam.setGradientTime(playTime);
        rhythmsCmdParam.setOn(isOn);
        rhythmsCmdParam.setRepeatTimes(repeatTimes);
        rhythmsCmdParam.setGradientMode(gradientMode);
        rhythmsCmdParam.setContentList(contentItemList);
        sendMode(context, rhythmsCmdParam, result);
    }

    public void saveAndPlayRhythmsPlanInfo(Context context, int modeNum, boolean isOn, int repeatTimes, int gradientMode, int playTime, List<RhythmsPlanInfo.Item> contentItemList, IAction<Boolean> result) {
        RhythmsCmdParam rhythmsCmdParam = new RhythmsCmdParam();
        rhythmsCmdParam.setZoneNum(this.zoneNum);
        rhythmsCmdParam.setCmdType(3);
        rhythmsCmdParam.setPlayType(2);
        rhythmsCmdParam.setModeNum(modeNum);
        rhythmsCmdParam.setGradientTime(playTime);
        rhythmsCmdParam.setGradientMode(gradientMode);
        rhythmsCmdParam.setOn(isOn);
        rhythmsCmdParam.setRepeatTimes(repeatTimes);
        rhythmsCmdParam.setContentList(contentItemList);
        sendModeWithWaitDialog(context, rhythmsCmdParam, result);
    }

    public void previewRhythmsPlanInfo(Context context, int modeNum, boolean isOn, int repeatTimes, int gradientMode, int playTime, List<RhythmsPlanInfo.Item> contentItemList, IAction<Boolean> result) {
        RhythmsCmdParam rhythmsCmdParam = new RhythmsCmdParam();
        rhythmsCmdParam.setZoneNum(this.zoneNum);
        rhythmsCmdParam.setCmdType(3);
        rhythmsCmdParam.setPlayType(3);
        rhythmsCmdParam.setModeNum(modeNum);
        rhythmsCmdParam.setGradientTime(playTime);
        rhythmsCmdParam.setOn(isOn);
        rhythmsCmdParam.setRepeatTimes(repeatTimes);
        rhythmsCmdParam.setGradientMode(gradientMode);
        rhythmsCmdParam.setContentList(contentItemList);
        sendModeWithWaitDialog(context, rhythmsCmdParam, result);
    }

    public void batchSaveRhythmsPlanInfo(Context context, int modeType, boolean isOn, int repeatTimes, String content, boolean isLast, IAction<Boolean> result) {
        sendModeInterval(context, saveRhythmsPlanInfo(modeType, isOn, repeatTimes, content), isLast, result);
    }

    public RhythmsCmdParam saveRhythmsPlanInfo(int modeType, boolean isOn, int repeatTimes, String content) {
        RhythmsCmdParam rhythmsCmdParam = new RhythmsCmdParam();
        rhythmsCmdParam.setZoneNum(this.zoneNum);
        rhythmsCmdParam.setCmdType(4);
        rhythmsCmdParam.setPlayType(isOn ? 2 : 4);
        rhythmsCmdParam.setModeType(modeType);
        rhythmsCmdParam.setOn(isOn);
        rhythmsCmdParam.setRepeatTimes(repeatTimes);
        rhythmsCmdParam.setContentStr(content);
        return rhythmsCmdParam;
    }

    public void previewRhythmsPlanInfo(Context context, int modeType, boolean isOn, int repeatTimes, String content, IAction<Boolean> result) {
        sendMode(context, previewRhythmsPlanInfo(modeType, isOn, repeatTimes, content), result);
    }

    public RhythmsCmdParam previewRhythmsPlanInfo(int modeType, boolean isOn, int repeatTimes, String content) {
        RhythmsCmdParam rhythmsCmdParam = new RhythmsCmdParam();
        rhythmsCmdParam.setZoneNum(this.zoneNum);
        rhythmsCmdParam.setCmdType(4);
        rhythmsCmdParam.setPlayType(3);
        rhythmsCmdParam.setModeType(modeType);
        rhythmsCmdParam.setOn(isOn);
        rhythmsCmdParam.setRepeatTimes(repeatTimes);
        rhythmsCmdParam.setContentStr(content);
        return rhythmsCmdParam;
    }
}