package com.ltech.smarthome.model.bean;

/* loaded from: classes4.dex */
public class BleMusicPlayerState {
    private int bleState;
    private int playAction;
    private int playDir;
    private int playMode;
    private int playScope;
    private int songsId;
    private int volume;

    public int getVolume() {
        return this.volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getBleState() {
        return this.bleState;
    }

    public void setBleState(int bleState) {
        this.bleState = bleState;
    }

    public int getSongsId() {
        return this.songsId;
    }

    public void setSongsId(int songsId) {
        this.songsId = songsId;
    }

    public int getPlayAction() {
        return this.playAction;
    }

    public void setPlayAction(int playAction) {
        this.playAction = playAction;
    }

    public int getPlayMode() {
        return this.playMode;
    }

    public void setPlayMode(int playMode) {
        this.playMode = playMode;
    }

    public int getPlayDir() {
        return this.playDir;
    }

    public void setPlayDir(int playDir) {
        this.playDir = playDir;
    }

    public int getPlayScope() {
        return this.playScope;
    }

    public void setPlayScope(int playScope) {
        this.playScope = playScope;
    }
}