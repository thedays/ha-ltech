package com.ltech.smarthome.model.device_param;

import com.smart.message.utils.LHomeLog;

/* loaded from: classes4.dex */
public class FreshAirSubDeviceParam {
    public int errorCode;
    public int inAddr;
    public int mode;
    public String name;
    public int on;
    public int outAddr;
    public int pmValue;
    public String position;
    public int roomTemp;
    public int speed;
    public int temperature;
    public int unicastAddress;
    public int vocValue;

    public FreshAirSubDeviceParam() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public FreshAirSubDeviceParam(int unicastAddress, String data, String name, String position) {
        LHomeLog.i(FreshAirSubDeviceParam.class, "data=" + data + "__length=" + data.length());
        this.unicastAddress = unicastAddress;
        this.name = name;
        this.position = position;
        if (data.length() >= 16) {
            this.outAddr = Integer.valueOf(data.substring(0, 2), 16).intValue();
            this.inAddr = Integer.valueOf(data.substring(2, 4), 16).intValue();
            this.on = Integer.valueOf(data.substring(4, 6), 16).intValue();
            this.temperature = Integer.valueOf(data.substring(6, 8), 16).intValue();
            this.mode = Integer.valueOf(data.substring(8, 10), 16).intValue();
            this.speed = Integer.valueOf(data.substring(10, 12), 16).intValue();
            this.roomTemp = Integer.valueOf(data.substring(12, 14), 16).intValue();
            this.errorCode = Integer.valueOf(data.substring(14, 16), 16).intValue();
            LHomeLog.i(FreshAirSubDeviceParam.class, "finalData=" + data + "__length=" + data.length());
            if (data.length() >= 20) {
                this.pmValue = Integer.valueOf(data.substring(16, 18), 16).intValue();
                this.vocValue = Integer.valueOf(data.substring(18, 20), 16).intValue();
            }
        }
    }

    public int getUnicastAddress() {
        return this.unicastAddress;
    }

    public void setUnicastAddress(int unicastAddress) {
        this.unicastAddress = unicastAddress;
    }

    public int getOutAddr() {
        return this.outAddr;
    }

    public void setOutAddr(int outAddr) {
        this.outAddr = outAddr;
    }

    public int getInAddr() {
        return this.inAddr;
    }

    public void setInAddr(int inAddr) {
        this.inAddr = inAddr;
    }

    public int getOn() {
        return this.on;
    }

    public void setOn(int on) {
        this.on = on;
    }

    public int getTemperature() {
        return this.temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getRoomTemp() {
        return this.roomTemp;
    }

    public void setRoomTemp(int roomTemp) {
        this.roomTemp = roomTemp;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getPmValue() {
        return this.pmValue;
    }

    public void setPmValue(int pmValue) {
        this.pmValue = pmValue;
    }

    public int getVocValue() {
        return this.vocValue;
    }

    public void setVocValue(int vocValue) {
        this.vocValue = vocValue;
    }

    public String toString() {
        return "NewAirSubDeviceParam{unicastAddress=" + this.unicastAddress + ", outAddr=" + this.outAddr + ", inAddr=" + this.inAddr + ", on=" + this.on + ", temperature=" + this.temperature + ", mode=" + this.mode + ", speed=" + this.speed + ", roomTemp=" + this.roomTemp + ", errorCode=" + this.errorCode + ", pmValue=" + this.pmValue + ", vocValue=" + this.vocValue + ", name='" + this.name + "', position='" + this.position + "'}";
    }
}