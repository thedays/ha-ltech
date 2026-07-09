package com.ltech.smarthome.utils.cmd_assistant;

import android.content.Context;
import com.ltech.smarthome.base.IAction;
import com.smart.message.ResponseMsg;
import com.smart.product_agreement.param.MusicCmdParam;

/* loaded from: classes4.dex */
public class MusicAssistant extends BaseAssistant {
    public void orderMusic(Context context, String musicId, int time, IAction<ResponseMsg> result) {
        MusicCmdParam musicCmdParam = new MusicCmdParam();
        musicCmdParam.setZoneNum(this.zoneNum);
        musicCmdParam.setCmdType(1);
        musicCmdParam.setMusicId(musicId);
        musicCmdParam.setTime(time);
        sendWithResult(context, musicCmdParam, result);
    }

    public void controlAction(Context context, int order, int data, IAction<ResponseMsg> result) {
        MusicCmdParam musicCmdParam = new MusicCmdParam();
        musicCmdParam.setZoneNum(this.zoneNum);
        musicCmdParam.setCmdType(2);
        musicCmdParam.setOrder(order);
        musicCmdParam.setData(data);
        sendWithResult(context, musicCmdParam, result);
    }

    public void controlAction(Context context, int order, int data, String songId, IAction<ResponseMsg> result) {
        MusicCmdParam musicCmdParam = new MusicCmdParam();
        musicCmdParam.setZoneNum(this.zoneNum);
        musicCmdParam.setCmdType(4);
        musicCmdParam.setOrder(order);
        musicCmdParam.setData(data);
        musicCmdParam.setMusicId(songId);
        sendWithResult(context, musicCmdParam, result);
    }

    public void getCurState(Context context, IAction<ResponseMsg> result) {
        MusicCmdParam musicCmdParam = new MusicCmdParam();
        musicCmdParam.setZoneNum(this.zoneNum);
        musicCmdParam.setCmdType(3);
        sendWithResult(context, musicCmdParam, result);
    }

    public void playList(Context context, IAction<ResponseMsg> result) {
        MusicCmdParam musicCmdParam = new MusicCmdParam();
        musicCmdParam.setZoneNum(this.zoneNum);
        musicCmdParam.setCmdType(6);
        sendWithResult(context, musicCmdParam, result);
    }

    public void playType(Context context, int type, IAction<ResponseMsg> result) {
        MusicCmdParam musicCmdParam = new MusicCmdParam();
        musicCmdParam.setZoneNum(this.zoneNum);
        musicCmdParam.setCmdType(5);
        musicCmdParam.setType(type);
        sendWithResult(context, musicCmdParam, result);
    }

    public void queryWifiTransferInfo(Context context, IAction<ResponseMsg> result) {
        MusicCmdParam musicCmdParam = new MusicCmdParam();
        musicCmdParam.setZoneNum(this.zoneNum);
        musicCmdParam.setCmdType(7);
        sendWithResult(context, musicCmdParam, result);
    }

    public void controlWifiTransfer(Context context, int type, IAction<ResponseMsg> result) {
        MusicCmdParam musicCmdParam = new MusicCmdParam();
        musicCmdParam.setZoneNum(this.zoneNum);
        musicCmdParam.setCmdType(8);
        musicCmdParam.setType(type);
        sendWithResult(context, musicCmdParam, result);
    }

    public void playListSort(Context context, IAction<ResponseMsg> result) {
        MusicCmdParam musicCmdParam = new MusicCmdParam();
        musicCmdParam.setZoneNum(this.zoneNum);
        musicCmdParam.setCmdType(9);
        sendWithResult(context, musicCmdParam, result);
    }
}