package com.ltech.smarthome.utils.cmd_assistant;

import android.content.Context;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.bean.AdvancedModeInfo;
import com.smart.product_agreement.bean.GeneralModeInfo;
import com.smart.product_agreement.param.ModeCmdParam;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public class ModeAssistant extends BaseAssistant {
    public void playDefaultMode(Context context, int position) {
        LHomeLog.i(getClass(), "playDefaultMode: zoneNum: " + this.zoneNum);
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(this.zoneNum);
        modeCmdParam.setCmdType(1);
        modeCmdParam.setPlayModeList(Collections.singletonList(Integer.valueOf(position)));
        if (position == 0 || position == 1) {
            playDefaultShowTip(context, modeCmdParam, position, new IAction[0]);
        } else {
            playMode(context, modeCmdParam, new IAction[0]);
        }
    }

    public ModeCmdParam playDefaultMode(int position) {
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(this.zoneNum);
        modeCmdParam.setCmdType(1);
        modeCmdParam.setPlayModeList(Collections.singletonList(Integer.valueOf(position)));
        return modeCmdParam;
    }

    public void previewDefaultThemeMode(Context context, int position) {
        LHomeLog.i(getClass(), "playDefaultMode: zoneNum: " + this.zoneNum);
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(this.zoneNum);
        modeCmdParam.setCmdType(1);
        modeCmdParam.setPlayModeList(Collections.singletonList(Integer.valueOf(position)));
        sendWithoutResultByPublish(context, modeCmdParam, 3, new int[0]);
    }

    public void previewDefaultGeneralMode(Context context, int position) {
        LHomeLog.i(getClass(), "playDefaultMode: zoneNum: " + this.zoneNum);
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(this.zoneNum);
        modeCmdParam.setCmdType(7);
        modeCmdParam.setPlayType(3);
        modeCmdParam.setPlayModeList(Collections.singletonList(Integer.valueOf(position)));
        sendWithoutResultByPublish(context, modeCmdParam, 3, new int[0]);
    }

    public void previewDefaultAdvancedMode(Context context, int position) {
        LHomeLog.i(getClass(), "playDefaultMode: zoneNum: " + this.zoneNum);
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(this.zoneNum);
        modeCmdParam.setCmdType(14);
        modeCmdParam.setPlayType(3);
        modeCmdParam.setPlayModeList(Collections.singletonList(Integer.valueOf(position)));
        sendWithoutResultByPublish(context, modeCmdParam, 3, new int[0]);
    }

    public void queryGeneralModeList(Context context, IAction<ResponseMsg> result) {
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(this.zoneNum);
        modeCmdParam.setCmdType(2);
        setSingleAddress(modeCmdParam);
        sendWithResult(context, modeCmdParam, result);
    }

    public void changeGeneralModeName(Context context, int modeNum, String modeName, IAction<Boolean> result) {
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(this.zoneNum);
        modeCmdParam.setCmdType(4);
        modeCmdParam.setModeNum(modeNum);
        modeCmdParam.setModeName(modeName);
        save(context, modeCmdParam, result);
    }

    public void changeGeneralModeIcon(Context context, int modeNum, int picIndex, IAction<Boolean> result) {
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(this.zoneNum);
        modeCmdParam.setCmdType(5);
        modeCmdParam.setModeNum(modeNum);
        modeCmdParam.setPicIndex(picIndex);
        save(context, modeCmdParam, result);
    }

    public void resetGeneralMode(Context context, int modeNum) {
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(this.zoneNum);
        modeCmdParam.setCmdType(6);
        modeCmdParam.setModeNum(modeNum);
        save(context, modeCmdParam, new IAction[0]);
    }

    public void playGeneralMode(Context context, List<Integer> playList, int playTimes, boolean ListPlay) {
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(this.zoneNum);
        modeCmdParam.setCmdType(7);
        modeCmdParam.setPlayModeList(playList);
        modeCmdParam.setPlayType(1);
        modeCmdParam.setPlayTimes(playTimes);
        modeCmdParam.setListPlay(ListPlay);
        playMode(context, modeCmdParam, new IAction[0]);
    }

    public void playGeneralMode(Context context, int position, int playTimes, boolean ListPlay) {
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(this.zoneNum);
        modeCmdParam.setCmdType(7);
        modeCmdParam.setPlayModeList(Collections.singletonList(Integer.valueOf(position)));
        modeCmdParam.setPlayType(1);
        modeCmdParam.setPlayTimes(playTimes);
        modeCmdParam.setListPlay(ListPlay);
        playMode(context, modeCmdParam, new IAction[0]);
    }

    public ModeCmdParam playGeneralMode(int position, int playTimes, boolean ListPlay) {
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(this.zoneNum);
        modeCmdParam.setCmdType(7);
        modeCmdParam.setPlayModeList(Collections.singletonList(Integer.valueOf(position)));
        modeCmdParam.setPlayType(1);
        modeCmdParam.setPlayTimes(playTimes);
        modeCmdParam.setListPlay(ListPlay);
        return modeCmdParam;
    }

    public void queryGeneralModeInfo(Context context, int modeNum, IAction<ResponseMsg> result) {
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(this.zoneNum);
        modeCmdParam.setCmdType(3);
        modeCmdParam.setModeNum(modeNum);
        sendWithResult(context, modeCmdParam, result);
    }

    public void previewGeneralModeInfo(Context context, int modeNum, GeneralModeInfo modeInfo) {
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(this.zoneNum);
        modeCmdParam.setCmdType(8);
        modeCmdParam.setPlayType(3);
        modeCmdParam.setModeNum(modeNum);
        modeCmdParam.setColorNum(modeInfo.getColorNum());
        modeCmdParam.setPlayTimes(modeInfo.getPlayTimes());
        modeCmdParam.setModeType(modeInfo.getModeType());
        modeCmdParam.setSpeed(modeInfo.getSpeed());
        modeCmdParam.setRgbBrt(modeInfo.getRgbBrt());
        modeCmdParam.setwOrWy(modeInfo.getwOrwy());
        modeCmdParam.setWyBrt(modeInfo.getWyBrt());
        modeCmdParam.setColorList(modeInfo.getColorList());
        Injection.message().create(context).cmdParam(modeCmdParam).control(this.controlObject).intervalTime(100).sendTimes(3).enqueue();
    }

    public void pauseGeneralModeInfo(Context context, int modeNum, GeneralModeInfo... modeInfo) {
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(this.zoneNum);
        modeCmdParam.setCmdType(8);
        modeCmdParam.setPlayType(0);
        modeCmdParam.setModeNum(modeNum);
        if (modeInfo.length > 0) {
            modeCmdParam.setModeName(modeInfo[0].getModeName());
            modeCmdParam.setModeType(modeInfo[0].getModeType());
            modeCmdParam.setPlayTimes(modeInfo[0].getPlayTimes());
            modeCmdParam.setSpeed(modeInfo[0].getSpeed());
            modeCmdParam.setRgbBrt(modeInfo[0].getRgbBrt());
            modeCmdParam.setwOrWy(modeInfo[0].getwOrwy());
            modeCmdParam.setWyBrt(modeInfo[0].getWyBrt());
            modeCmdParam.setColorList(modeInfo[0].getColorList());
        }
        Injection.message().create(context).cmdParam(modeCmdParam).control(this.controlObject).intervalTime(100).sendTimes(3).enqueue();
    }

    public void saveGeneralModeInfo(Context context, int modeNum, GeneralModeInfo modeInfo, IAction<Boolean> result) {
        sendMode(context, saveGeneralModeInfo(modeNum, modeInfo), result);
    }

    public ModeCmdParam saveGeneralModeInfo(int modeNum, GeneralModeInfo modeInfo) {
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(this.zoneNum);
        modeCmdParam.setCmdType(8);
        modeCmdParam.setPlayType(4);
        modeCmdParam.setModeNum(modeNum);
        modeCmdParam.setColorNum(modeInfo.getColorNum());
        modeCmdParam.setPlayTimes(modeInfo.getPlayTimes());
        modeCmdParam.setModeType(modeInfo.getModeType());
        modeCmdParam.setSpeed(modeInfo.getSpeed());
        modeCmdParam.setRgbBrt(modeInfo.getRgbBrt());
        modeCmdParam.setwOrWy(modeInfo.getwOrwy());
        modeCmdParam.setWyBrt(modeInfo.getWyBrt());
        modeCmdParam.setColorList(modeInfo.getColorList());
        return modeCmdParam;
    }

    public void queryAdvancedModeList(Context context, IAction<ResponseMsg> result) {
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(this.zoneNum);
        modeCmdParam.setCmdType(9);
        setSingleAddress(modeCmdParam);
        sendWithResult(context, modeCmdParam, result);
    }

    public void changeAdvancedModeName(Context context, int modeNum, String modeName, IAction<Boolean> result) {
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(this.zoneNum);
        modeCmdParam.setCmdType(11);
        modeCmdParam.setModeNum(modeNum);
        modeCmdParam.setModeName(modeName);
        save(context, modeCmdParam, result);
    }

    public void changeAdvancedModeIcon(Context context, int modeNum, int picIndex, IAction<Boolean> result) {
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(this.zoneNum);
        modeCmdParam.setCmdType(12);
        modeCmdParam.setModeNum(modeNum);
        modeCmdParam.setPicIndex(picIndex);
        save(context, modeCmdParam, result);
    }

    public void resetAdvancedMode(Context context, int modeNum) {
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(this.zoneNum);
        modeCmdParam.setCmdType(13);
        modeCmdParam.setModeNum(modeNum);
        save(context, modeCmdParam, new IAction[0]);
    }

    public void playAdvancedMode(Context context, List<Integer> playList, int playTimes, boolean ListPlay) {
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(this.zoneNum);
        modeCmdParam.setCmdType(14);
        modeCmdParam.setPlayModeList(playList);
        modeCmdParam.setPlayTimes(playTimes);
        modeCmdParam.setPlayType(1);
        modeCmdParam.setListPlay(ListPlay);
        playMode(context, modeCmdParam, new IAction[0]);
    }

    public ModeCmdParam playAdvancedMode(int position, int playTimes, boolean ListPlay) {
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(this.zoneNum);
        modeCmdParam.setCmdType(14);
        modeCmdParam.setPlayModeList(Collections.singletonList(Integer.valueOf(position)));
        modeCmdParam.setPlayTimes(playTimes);
        modeCmdParam.setPlayType(1);
        modeCmdParam.setListPlay(ListPlay);
        return modeCmdParam;
    }

    public void queryAdvancedModeInfo(Context context, int modeNum, IAction<ResponseMsg> result) {
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(this.zoneNum);
        modeCmdParam.setCmdType(10);
        modeCmdParam.setModeNum(modeNum);
        sendWithResult(context, modeCmdParam, result);
    }

    public void saveAdvancedModeInfo(Context context, int modeNum, int playTime, List<AdvancedModeInfo.ContentItem> contentItemList, IAction<Boolean> result) {
        sendMode(context, saveAdvancedModeInfo(modeNum, playTime, contentItemList), result);
    }

    public ModeCmdParam saveAdvancedModeInfo(int modeNum, int playTime, List<AdvancedModeInfo.ContentItem> contentItemList) {
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(this.zoneNum);
        modeCmdParam.setCmdType(15);
        modeCmdParam.setPlayType(4);
        modeCmdParam.setModeNum(modeNum);
        modeCmdParam.setPlayTimes(playTime);
        modeCmdParam.setAdvancedContentList(contentItemList);
        return modeCmdParam;
    }

    public void previewAdvancedModeInfo(Context context, int modeNum, int playTime, List<AdvancedModeInfo.ContentItem> contentItemList) {
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(this.zoneNum);
        modeCmdParam.setCmdType(15);
        modeCmdParam.setPlayType(3);
        modeCmdParam.setModeNum(modeNum);
        modeCmdParam.setPlayTimes(playTime);
        modeCmdParam.setAdvancedContentList(contentItemList);
        Injection.message().create(context).cmdParam(modeCmdParam).control(this.controlObject).intervalTime(100).sendTimes(3).enqueue();
    }

    public void pauseAdvancedModeInfo(Context context, int modeNum, List<AdvancedModeInfo.ContentItem> contentItemList) {
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(this.zoneNum);
        modeCmdParam.setCmdType(15);
        modeCmdParam.setPlayType(0);
        modeCmdParam.setModeNum(modeNum);
        modeCmdParam.setAdvancedContentList(contentItemList);
        Injection.message().create(context).cmdParam(modeCmdParam).control(this.controlObject).intervalTime(100).sendTimes(3).enqueue();
    }

    public void saveGeneralModeInfoInterval(Context context, int modeNum, GeneralModeInfo modeInfo, boolean isLast, IAction<Boolean> result) {
        sendModeInterval(context, saveGeneralModeInfo(modeNum, modeInfo), isLast, result);
    }

    public void saveAdvancedModeInfoInterval(Context context, int modeNum, int playTime, List<AdvancedModeInfo.ContentItem> contentItemList, boolean isLast, IAction<Boolean> result) {
        sendModeInterval(context, saveAdvancedModeInfo(modeNum, playTime, contentItemList), isLast, result);
    }

    public void playSpiMode(Context context, int modeNum, int playTimes, int direction, int speed, int rgbBrt, boolean listPlay, IAction<ResponseMsg> result) {
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(this.zoneNum);
        modeCmdParam.setCmdType(16);
        modeCmdParam.setModeNum(modeNum);
        modeCmdParam.setListPlay(listPlay);
        if (listPlay) {
            modeCmdParam.setPlayType(1);
        } else {
            modeCmdParam.setPlayType(2);
        }
        modeCmdParam.setPlayTimes(playTimes);
        modeCmdParam.setDirection(direction);
        modeCmdParam.setSpeed(speed);
        modeCmdParam.setRgbBrt(rgbBrt);
        sendWithWaitDialog(context, modeCmdParam, result);
    }

    public void playSpiModeWithoutParam(Context context, int modeNum, boolean listPlay) {
        sendWithoutResult(context, playSpiModeWithoutParam(modeNum, listPlay));
    }

    public ModeCmdParam playSpiModeWithoutParam(int modeNum, boolean listPlay) {
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(this.zoneNum);
        modeCmdParam.setCmdType(16);
        modeCmdParam.setModeNum(modeNum);
        modeCmdParam.setPlayType(5);
        modeCmdParam.setListPlay(listPlay);
        return modeCmdParam;
    }

    public void pauseSpiMode(Context context, IAction<ResponseMsg> result) {
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(this.zoneNum);
        modeCmdParam.setCmdType(16);
        modeCmdParam.setPlayType(0);
        sendWithWaitDialog(context, modeCmdParam, result);
    }

    public void setSpiModeList(Context context, List<Integer> playList, int playListNum, int playTimes, IAction<Boolean> result) {
        save(context, setSpiModeList(playList, playListNum, playTimes), result);
    }

    public ModeCmdParam setSpiModeList(List<Integer> playList, int playListNum, int playTimes) {
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(this.zoneNum);
        modeCmdParam.setCmdType(17);
        modeCmdParam.setPlayListNum(playListNum);
        modeCmdParam.setPlayModeList(playList);
        modeCmdParam.setPlayTimes(playTimes);
        return modeCmdParam;
    }

    public void querySpiModeState(Context context, int modeNum, IAction<ResponseMsg> result) {
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(this.zoneNum);
        modeCmdParam.setCmdType(18);
        modeCmdParam.setModeNum(modeNum);
        sendWithResult(context, modeCmdParam, result);
    }

    public void querySpiPlayListState(Context context, int listNum, IAction<ResponseMsg> result) {
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(this.zoneNum);
        modeCmdParam.setCmdType(19);
        modeCmdParam.setModeNum(listNum);
        sendWithResult(context, modeCmdParam, result);
    }
}