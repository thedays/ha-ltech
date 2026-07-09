package com.ltech.smarthome.model.bean;

/* loaded from: classes4.dex */
public class MusicInfo {
    private String iscollection;
    private String islocal;
    private String lrcurl;
    private String musicid;
    private String musicname;
    private String picurl = "";
    private String singername;
    private long songid;
    private int sorting;

    public int getSorting() {
        return this.sorting;
    }

    public void setSorting(int sorting) {
        this.sorting = sorting;
    }

    public long getSongid() {
        return this.songid;
    }

    public void setSongid(long songid) {
        this.songid = songid;
    }

    public String getMusicid() {
        return this.musicid;
    }

    public void setMusicid(String musicid) {
        this.musicid = musicid;
    }

    public String getMusicname() {
        return this.musicname;
    }

    public void setMusicname(String musicname) {
        this.musicname = musicname;
    }

    public String getSingername() {
        return this.singername;
    }

    public void setSingername(String singername) {
        this.singername = singername;
    }

    public String getPicurl() {
        return this.picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getLrcurl() {
        return this.lrcurl;
    }

    public void setLrcurl(String lrcurl) {
        this.lrcurl = lrcurl;
    }

    public String getIscollection() {
        return this.iscollection;
    }

    public void setIscollection(String iscollection) {
        this.iscollection = iscollection;
    }

    public String getIslocal() {
        return this.islocal;
    }

    public void setIslocal(String islocal) {
        this.islocal = islocal;
    }

    public String toString() {
        return "MusicInfo{musicid='" + this.musicid + "', musicname='" + this.musicname + "', singername='" + this.singername + "', picurl='" + this.picurl + "', lrcurl='" + this.lrcurl + "', iscollection='" + this.iscollection + "', islocal='" + this.islocal + "'}";
    }
}