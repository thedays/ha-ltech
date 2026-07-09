package com.ltech.smarthome.service.music;

import com.ltech.smarthome.preference_bean.MusicBean;

/* loaded from: classes4.dex */
public class PlayingMusic extends MusicBean {
    private String allTime;
    private int curPosition;
    private String nowTime;

    public PlayingMusic(String nowTime, String allTime) {
        this.nowTime = nowTime;
        this.allTime = allTime;
    }

    public String getNowTime() {
        return this.nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public String getAllTime() {
        return this.allTime;
    }

    public void setAllTime(String allTime) {
        this.allTime = allTime;
    }

    public int getCurPosition() {
        return this.curPosition;
    }

    public void setCurPosition(int curPosition) {
        this.curPosition = curPosition;
    }
}