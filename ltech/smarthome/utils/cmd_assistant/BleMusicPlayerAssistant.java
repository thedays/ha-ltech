package com.ltech.smarthome.utils.cmd_assistant;

import android.content.Context;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.bean.BleMusicPlayerState;
import com.smart.message.ResponseMsg;
import com.smart.product_agreement.param.BleMusicPlayerParam;
import com.smart.product_agreement.param.BleMusicPlayerPlaylistParam;
import java.util.List;

/* loaded from: classes4.dex */
public class BleMusicPlayerAssistant extends BaseAssistant {
    public void queryBleMusicSongsName(Context context, int num, IAction<ResponseMsg> result) {
        BleMusicPlayerParam bleMusicPlayerParam = new BleMusicPlayerParam();
        bleMusicPlayerParam.setZoneNum(this.zoneNum);
        bleMusicPlayerParam.setNum(num);
        bleMusicPlayerParam.setCmdType(12);
        sendWithResult(context, bleMusicPlayerParam, result);
    }

    public void play(Context context) {
        BleMusicPlayerParam bleMusicPlayerParam = new BleMusicPlayerParam();
        bleMusicPlayerParam.setZoneNum(this.zoneNum);
        bleMusicPlayerParam.setData(0);
        bleMusicPlayerParam.setCmdType(1);
        sendWithoutResult(context, bleMusicPlayerParam);
    }

    public BleMusicPlayerParam play() {
        BleMusicPlayerParam bleMusicPlayerParam = new BleMusicPlayerParam();
        bleMusicPlayerParam.setZoneNum(this.zoneNum);
        bleMusicPlayerParam.setData(0);
        bleMusicPlayerParam.setCmdType(1);
        return bleMusicPlayerParam;
    }

    public void stop(Context context) {
        BleMusicPlayerParam bleMusicPlayerParam = new BleMusicPlayerParam();
        bleMusicPlayerParam.setZoneNum(this.zoneNum);
        bleMusicPlayerParam.setData(0);
        bleMusicPlayerParam.setCmdType(2);
        sendWithoutResult(context, bleMusicPlayerParam);
    }

    public BleMusicPlayerParam stop() {
        BleMusicPlayerParam bleMusicPlayerParam = new BleMusicPlayerParam();
        bleMusicPlayerParam.setZoneNum(this.zoneNum);
        bleMusicPlayerParam.setData(0);
        bleMusicPlayerParam.setCmdType(2);
        return bleMusicPlayerParam;
    }

    public void last(Context context) {
        BleMusicPlayerParam bleMusicPlayerParam = new BleMusicPlayerParam();
        bleMusicPlayerParam.setZoneNum(this.zoneNum);
        bleMusicPlayerParam.setData(0);
        bleMusicPlayerParam.setCmdType(3);
        sendWithoutResult(context, bleMusicPlayerParam);
    }

    public BleMusicPlayerParam last() {
        BleMusicPlayerParam bleMusicPlayerParam = new BleMusicPlayerParam();
        bleMusicPlayerParam.setZoneNum(this.zoneNum);
        bleMusicPlayerParam.setData(0);
        bleMusicPlayerParam.setCmdType(3);
        return bleMusicPlayerParam;
    }

    public void next(Context context) {
        BleMusicPlayerParam bleMusicPlayerParam = new BleMusicPlayerParam();
        bleMusicPlayerParam.setZoneNum(this.zoneNum);
        bleMusicPlayerParam.setData(0);
        bleMusicPlayerParam.setCmdType(4);
        sendWithoutResult(context, bleMusicPlayerParam);
    }

    public BleMusicPlayerParam next() {
        BleMusicPlayerParam bleMusicPlayerParam = new BleMusicPlayerParam();
        bleMusicPlayerParam.setZoneNum(this.zoneNum);
        bleMusicPlayerParam.setData(0);
        bleMusicPlayerParam.setCmdType(4);
        return bleMusicPlayerParam;
    }

    public void setVolume(Context context, int volume) {
        BleMusicPlayerParam bleMusicPlayerParam = new BleMusicPlayerParam();
        bleMusicPlayerParam.setZoneNum(this.zoneNum);
        bleMusicPlayerParam.setData(volume);
        bleMusicPlayerParam.setCmdType(6);
        sendWithoutResult(context, bleMusicPlayerParam);
    }

    public BleMusicPlayerParam setVolume(int volume) {
        BleMusicPlayerParam bleMusicPlayerParam = new BleMusicPlayerParam();
        bleMusicPlayerParam.setZoneNum(this.zoneNum);
        bleMusicPlayerParam.setData(volume);
        bleMusicPlayerParam.setCmdType(6);
        return bleMusicPlayerParam;
    }

    public void setPlayMode(Context context, int playmode) {
        BleMusicPlayerParam bleMusicPlayerParam = new BleMusicPlayerParam();
        bleMusicPlayerParam.setZoneNum(this.zoneNum);
        bleMusicPlayerParam.setData(playmode);
        bleMusicPlayerParam.setCmdType(5);
        sendWithoutResult(context, bleMusicPlayerParam);
    }

    public void control(Context context, int scope, int dir, int mode, int sound, int volume) {
        BleMusicPlayerParam bleMusicPlayerParam = new BleMusicPlayerParam();
        bleMusicPlayerParam.setZoneNum(this.zoneNum);
        bleMusicPlayerParam.setScope(scope);
        bleMusicPlayerParam.setVolume(volume);
        bleMusicPlayerParam.setDir(dir);
        bleMusicPlayerParam.setMode(mode);
        bleMusicPlayerParam.setSound(sound);
        bleMusicPlayerParam.setCmdType(15);
        sendWithoutResult(context, bleMusicPlayerParam);
    }

    public void queryBleMusicPlayerPlayList(Context context, int num, IAction<ResponseMsg> result) {
        BleMusicPlayerParam bleMusicPlayerParam = new BleMusicPlayerParam();
        bleMusicPlayerParam.setZoneNum(this.zoneNum);
        bleMusicPlayerParam.setNum(num);
        bleMusicPlayerParam.setCmdType(13);
        sendWithResult(context, bleMusicPlayerParam, result);
    }

    public void createplayList(Context context, String name, IAction<ResponseMsg> result) {
        BleMusicPlayerPlaylistParam bleMusicPlayerPlaylistParam = new BleMusicPlayerPlaylistParam();
        bleMusicPlayerPlaylistParam.setZoneNum(this.zoneNum);
        bleMusicPlayerPlaylistParam.setName(name);
        bleMusicPlayerPlaylistParam.setCmdType(1);
        sendWithResult(context, bleMusicPlayerPlaylistParam, result);
    }

    public void editPlaylistName(Context context, int num, String name, IAction<ResponseMsg> result) {
        BleMusicPlayerPlaylistParam bleMusicPlayerPlaylistParam = new BleMusicPlayerPlaylistParam();
        bleMusicPlayerPlaylistParam.setZoneNum(this.zoneNum);
        bleMusicPlayerPlaylistParam.setNum(num);
        bleMusicPlayerPlaylistParam.setName(name);
        bleMusicPlayerPlaylistParam.setCmdType(2);
        sendWithResult(context, bleMusicPlayerPlaylistParam, result);
    }

    public void deletePlaylist(Context context, int num, String name, IAction<ResponseMsg> result) {
        BleMusicPlayerPlaylistParam bleMusicPlayerPlaylistParam = new BleMusicPlayerPlaylistParam();
        bleMusicPlayerPlaylistParam.setZoneNum(this.zoneNum);
        bleMusicPlayerPlaylistParam.setNum(num);
        bleMusicPlayerPlaylistParam.setName(name);
        bleMusicPlayerPlaylistParam.setCmdType(3);
        sendWithResult(context, bleMusicPlayerPlaylistParam, result);
    }

    public void addSongs2Playlist(Context context, int playlistId, List<Integer> songIds, IAction<ResponseMsg> result) {
        BleMusicPlayerPlaylistParam bleMusicPlayerPlaylistParam = new BleMusicPlayerPlaylistParam();
        bleMusicPlayerPlaylistParam.setZoneNum(this.zoneNum);
        bleMusicPlayerPlaylistParam.setNum(playlistId);
        bleMusicPlayerPlaylistParam.setSongIds(songIds);
        bleMusicPlayerPlaylistParam.setCmdType(4);
        sendWithResult(context, bleMusicPlayerPlaylistParam, result);
    }

    public void queryPlaylistSongs(Context context, int num, IAction<ResponseMsg> result) {
        BleMusicPlayerParam bleMusicPlayerParam = new BleMusicPlayerParam();
        bleMusicPlayerParam.setZoneNum(this.zoneNum);
        bleMusicPlayerParam.setNum(num);
        bleMusicPlayerParam.setCmdType(14);
        sendWithResult(context, bleMusicPlayerParam, result);
    }

    public void orderPlaylist(Context context, List<Integer> playlistIds, IAction<ResponseMsg> result) {
        BleMusicPlayerPlaylistParam bleMusicPlayerPlaylistParam = new BleMusicPlayerPlaylistParam();
        bleMusicPlayerPlaylistParam.setZoneNum(this.zoneNum);
        bleMusicPlayerPlaylistParam.setPlaylistIds(playlistIds);
        bleMusicPlayerPlaylistParam.setCmdType(5);
        sendWithResult(context, bleMusicPlayerPlaylistParam, result);
    }

    public void changeBluetoothModel(Context context, int model, IAction<ResponseMsg> result) {
        BleMusicPlayerPlaylistParam bleMusicPlayerPlaylistParam = new BleMusicPlayerPlaylistParam();
        bleMusicPlayerPlaylistParam.setZoneNum(this.zoneNum);
        bleMusicPlayerPlaylistParam.setBluetoothModel(model);
        bleMusicPlayerPlaylistParam.setCmdType(6);
        sendWithResult(context, bleMusicPlayerPlaylistParam, result);
    }

    public void changePowerOnModel(Context context, int model, IAction<ResponseMsg> result) {
        BleMusicPlayerPlaylistParam bleMusicPlayerPlaylistParam = new BleMusicPlayerPlaylistParam();
        bleMusicPlayerPlaylistParam.setZoneNum(this.zoneNum);
        bleMusicPlayerPlaylistParam.setPowerModel(model);
        bleMusicPlayerPlaylistParam.setCmdType(7);
        sendWithResult(context, bleMusicPlayerPlaylistParam, result);
    }

    public static BleMusicPlayerState coverToPlayerState(String resData) {
        BleMusicPlayerState bleMusicPlayerState = new BleMusicPlayerState();
        bleMusicPlayerState.setPlayScope(Integer.parseInt(resData.substring(12, 14), 16));
        bleMusicPlayerState.setPlayDir(Integer.parseInt(resData.substring(14, 16), 16));
        bleMusicPlayerState.setPlayMode(Integer.parseInt(resData.substring(16, 18), 16));
        bleMusicPlayerState.setPlayAction(Integer.parseInt(resData.substring(18, 20), 16));
        bleMusicPlayerState.setSongsId(Integer.parseInt(resData.substring(20, 24), 16));
        bleMusicPlayerState.setVolume(Integer.parseInt(resData.substring(24, 26), 16));
        bleMusicPlayerState.setBleState(Integer.parseInt(resData.substring(26, 28), 16));
        return bleMusicPlayerState;
    }
}