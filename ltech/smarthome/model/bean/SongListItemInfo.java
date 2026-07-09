package com.ltech.smarthome.model.bean;

import java.util.Arrays;

/* loaded from: classes4.dex */
public class SongListItemInfo {
    public String[] albumNames;
    private String highlightStr;
    public String isCollection;
    public boolean isLocal;
    private boolean isPlaying = false;
    private boolean isShowPlaying;
    public String length;
    public String name;
    public String picUrl;
    public String singer;
    public String songId;
    public long songid;
    private int sorting;

    public long getSongid() {
        return this.songid;
    }

    public void setSongid(long songid) {
        this.songid = songid;
    }

    public String[] getAlbumNames() {
        return this.albumNames;
    }

    public void setAlbumNames(String[] albumNames) {
        this.albumNames = albumNames;
    }

    public String getLength() {
        return this.length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public boolean isLocal() {
        return this.isLocal;
    }

    public void setLocal(boolean local) {
        this.isLocal = local;
    }

    public String getIsCollection() {
        return this.isCollection;
    }

    public void setIsCollection(String isCollection) {
        this.isCollection = isCollection;
    }

    public String getPicUrl() {
        return this.picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getSongId() {
        return this.songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return this.singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public boolean isCollection() {
        return "1".equalsIgnoreCase(this.isCollection);
    }

    public boolean isShowPlaying() {
        return this.isShowPlaying;
    }

    public void setShowPlaying(boolean showPlaying) {
        this.isShowPlaying = showPlaying;
    }

    public boolean isPlaying() {
        return this.isPlaying;
    }

    public void setPlaying(boolean playing) {
        this.isPlaying = playing;
    }

    public String getHighlightStr() {
        return this.highlightStr;
    }

    public void setHighlightStr(String highlightStr) {
        this.highlightStr = highlightStr;
    }

    public void setSorting(int sorting) {
        this.sorting = sorting;
    }

    public int getSorting() {
        return this.sorting;
    }

    public String toString() {
        return "SongListItemInfo{songId='" + this.songId + "', name='" + this.name + "', singer='" + this.singer + "', songid=" + this.songid + ", sorting=" + this.sorting + ", albumNames=" + Arrays.toString(this.albumNames) + ", length='" + this.length + "', isLocal=" + this.isLocal + ", isCollection='" + this.isCollection + "', picUrl='" + this.picUrl + "', isShowPlaying=" + this.isShowPlaying + ", isPlaying=" + this.isPlaying + ", highlightStr='" + this.highlightStr + "'}";
    }
}