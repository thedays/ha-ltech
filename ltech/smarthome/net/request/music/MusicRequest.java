package com.ltech.smarthome.net.request.music;

import com.ltech.smarthome.model.bean.MusicInfo;
import java.util.List;

/* loaded from: classes4.dex */
public class MusicRequest {
    private String mac;
    private List<MusicInfo> musicinfos;

    public MusicRequest(String mac, List<MusicInfo> musicInfoList) {
        this.mac = mac;
        this.musicinfos = musicInfoList;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public List<MusicInfo> getMusicInfoList() {
        return this.musicinfos;
    }

    public void setMusicInfoList(List<MusicInfo> musicInfoList) {
        this.musicinfos = musicInfoList;
    }
}