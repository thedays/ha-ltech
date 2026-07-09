package com.ltech.smarthome.model.bean;

/* loaded from: classes4.dex */
public class LocalSceneParam {
    private int address;
    private boolean curState;
    private long deviceId;
    private int index;
    private int infraredType;
    private String instruct;
    private int step;
    private int time;
    private int zoneNum;

    public LocalSceneParam(long deviceId, int time, int step, String instruct, int zoneNum, int address, boolean isCurState, int infraredType, int index) {
        this.deviceId = deviceId;
        this.time = time;
        this.step = step;
        this.instruct = instruct;
        this.zoneNum = zoneNum;
        this.address = address;
        this.curState = isCurState;
        this.infraredType = infraredType;
        this.index = index;
    }

    public LocalSceneParam(int time, int step, String instruct, int zoneNum, int address) {
        this.time = time;
        this.step = step;
        this.instruct = instruct;
        this.zoneNum = zoneNum;
        this.address = address;
        this.curState = false;
        this.index = 0;
    }

    public int getIndex() {
        return this.index;
    }

    public int getZoneNum() {
        return this.zoneNum;
    }

    public void setZoneNum(int zoneNum) {
        this.zoneNum = zoneNum;
    }

    public int getAddress() {
        return this.address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public String getInstruct() {
        return this.instruct;
    }

    public void setInstruct(String instruct) {
        this.instruct = instruct;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getStep() {
        return this.step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public boolean isCurState() {
        return this.curState;
    }

    public void setCurState(boolean curState) {
        this.curState = curState;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getInfraredType() {
        return this.infraredType;
    }

    public long getDeviceId() {
        return this.deviceId;
    }
}