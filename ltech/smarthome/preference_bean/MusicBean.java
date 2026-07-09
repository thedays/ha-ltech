package com.ltech.smarthome.preference_bean;

/* loaded from: classes4.dex */
public class MusicBean {
    private long duration;
    private long id;
    private String path;
    private String title = "";
    private String artist = "";

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return this.artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setBaseInfo(MusicBean musicBean) {
        setTitle(musicBean.getTitle());
        setArtist(musicBean.getArtist());
        setDuration(musicBean.getDuration());
        setId(musicBean.getId());
        setPath(musicBean.getPath());
    }

    public boolean equals(Object var1) {
        if (var1 instanceof MusicBean) {
            return ((MusicBean) var1).getPath().equals(getPath());
        }
        return super.equals(var1);
    }
}