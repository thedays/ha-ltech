package com.ltech.smarthome.model.device_param;

import com.ltech.smarthome.base.BaseExtParam;

/* loaded from: classes4.dex */
public class HsdExtParam extends BaseExtParam {
    private int sensorLux;
    private int sensorState;
    private int sensorTime;
    private String updateBatteryTime;
    private String updateSensorTime;
    private int battery = -1;
    private int timeNoMotion = 10;
    private int timeMotion = 10;
    private int indicatorStatus = 1;
    private int singleReport = 1;

    public int getSensorState() {
        return this.sensorState;
    }

    public void setSensorState(int sensorState) {
        this.sensorState = sensorState;
    }

    public int getSensorTime() {
        return this.sensorTime;
    }

    public void setSensorTime(int sensorTime) {
        this.sensorTime = sensorTime;
    }

    public String getUpdateSensorTime() {
        return this.updateSensorTime;
    }

    public void setUpdateSensorTime(String updateSensorTime) {
        this.updateSensorTime = updateSensorTime;
    }

    public int getBattery() {
        return this.battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public String getUpdateBatteryTime() {
        return this.updateBatteryTime;
    }

    public void setUpdateBatteryTime(String updateBatteryTime) {
        this.updateBatteryTime = updateBatteryTime;
    }

    public int getSensorLux() {
        return this.sensorLux;
    }

    public void setSensorLux(int sensorLux) {
        this.sensorLux = sensorLux;
    }

    public int getTimeNoMotion() {
        return this.timeNoMotion;
    }

    public void setTimeNoMotion(int timeNoMotion) {
        this.timeNoMotion = timeNoMotion;
    }

    public int getTimeMotion() {
        return this.timeMotion;
    }

    public void setTimeMotion(int timeMotion) {
        this.timeMotion = timeMotion;
    }

    public int getIndicatorStatus() {
        return this.indicatorStatus;
    }

    public void setIndicatorStatus(int indicatorStatus) {
        this.indicatorStatus = indicatorStatus;
    }

    public int getSingleReport() {
        return this.singleReport;
    }

    public void setSingleReport(int singleReport) {
        this.singleReport = singleReport;
    }
}