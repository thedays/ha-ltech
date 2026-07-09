package com.ltech.smarthome.model.bean;

/* loaded from: classes4.dex */
public class SongInfo {
    private String author;
    private long deviceId;
    private long id;
    private String name;
    private int num;
    private int state;
    private int totalCount;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNum() {
        return this.num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public long getDeviceId() {
        return this.deviceId;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String toString() {
        return "SongInfo{id=" + this.id + ", name='" + this.name + "', author='" + this.author + "', num=" + this.num + ", totalCount=" + this.totalCount + ", deviceId=" + this.deviceId + ", state=" + this.state + '}';
    }
}