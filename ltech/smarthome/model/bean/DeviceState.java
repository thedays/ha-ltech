package com.ltech.smarthome.model.bean;

import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.reflect.TypeToken;
import com.smart.product_agreement.bean.CurtainMotorState;
import com.smart.product_agreement.bean.WaveSensorState;
import io.objectbox.converter.PropertyConverter;
import java.util.List;

/* loaded from: classes4.dex */
public class DeviceState {
    private String acState;
    private int blue;
    private int cold;
    private CurtainMotorState curtainMotorState;
    private int green;
    private long id;
    private boolean isFavorite;
    private List<Boolean> onOffStates;
    private int red;
    private boolean rhythmPlay;
    private int warm;
    private WaveSensorState waveSensorState;
    private boolean on = true;
    private boolean won = true;
    private boolean rgbon = true;
    private int rgbBrt = 100;
    private int wy = 100;
    private int wyBrt = 100;
    private int totalBrt = 100;
    private int totalK = 1000;
    private int onlineState = 1;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isWOn() {
        return this.won;
    }

    public void setWOn(boolean won) {
        this.won = won;
    }

    public boolean isRGBOn() {
        return this.rgbon;
    }

    public void setRGBOn(boolean rgbon) {
        this.rgbon = rgbon;
    }

    public boolean isOn() {
        return this.on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public boolean isFavorite() {
        return this.isFavorite;
    }

    public String getAcState() {
        return this.acState;
    }

    public void setAcState(String acState) {
        this.acState = acState;
    }

    public void setFavorite(boolean favorite) {
        this.isFavorite = favorite;
    }

    public int getRed() {
        return this.red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return this.green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return this.blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public int getRgbBrt() {
        return this.rgbBrt;
    }

    public void setRgbBrt(int rgbBrt) {
        this.rgbBrt = rgbBrt;
    }

    public int getWy() {
        return this.wy;
    }

    public void setWy(int wy) {
        this.wy = wy;
    }

    public int getWyBrt() {
        return this.wyBrt;
    }

    public void setWyBrt(int wyBrt) {
        this.wyBrt = wyBrt;
    }

    public int getCold() {
        return this.cold;
    }

    public void setCold(int cold) {
        this.cold = cold;
    }

    public int getWarm() {
        return this.warm;
    }

    public void setWarm(int warm) {
        this.warm = warm;
    }

    public int getTotalBrt() {
        return this.totalBrt;
    }

    public void setTotalBrt(int totalBrt) {
        this.totalBrt = totalBrt;
    }

    public int getTotalK() {
        return this.totalK;
    }

    public void setTotalK(int totalK) {
        this.totalK = totalK;
    }

    public boolean isRhythmPlay() {
        return this.rhythmPlay;
    }

    public void setRhythmPlay(boolean rhythmPlay) {
        this.rhythmPlay = rhythmPlay;
    }

    public int getOnlineState() {
        return this.onlineState;
    }

    public void setOnlineState(int onlineState) {
        this.onlineState = onlineState;
    }

    public boolean isOnline() {
        return this.onlineState != 2;
    }

    public List<Boolean> getOnOffStates() {
        return this.onOffStates;
    }

    public void setOnOffStates(List<Boolean> onOffStates) {
        this.onOffStates = onOffStates;
    }

    public CurtainMotorState getCurtainMotorState() {
        return this.curtainMotorState;
    }

    public void setCurtainMotorState(CurtainMotorState curtainMotorState) {
        this.curtainMotorState = curtainMotorState;
    }

    public WaveSensorState getWaveSensorState() {
        if (this.waveSensorState == null) {
            this.waveSensorState = new WaveSensorState();
        }
        return this.waveSensorState;
    }

    public void setWaveSensorState(WaveSensorState waveSensorState) {
        this.waveSensorState = waveSensorState;
    }

    public static class DeviceStateConverter implements PropertyConverter<DeviceState, String> {
        @Override // io.objectbox.converter.PropertyConverter
        public DeviceState convertToEntityProperty(String databaseValue) {
            return (DeviceState) GsonUtils.fromJson(databaseValue, new TypeToken<DeviceState>(this) { // from class: com.ltech.smarthome.model.bean.DeviceState.DeviceStateConverter.1
            }.getType());
        }

        @Override // io.objectbox.converter.PropertyConverter
        public String convertToDatabaseValue(DeviceState entityProperty) {
            return GsonUtils.toJson(entityProperty, new TypeToken<DeviceState>(this) { // from class: com.ltech.smarthome.model.bean.DeviceState.DeviceStateConverter.2
            }.getType());
        }
    }

    public String toString() {
        return "DeviceState{id=" + this.id + ", onOffStates=" + this.onOffStates + ", curtainMotorState=" + this.curtainMotorState + ", on=" + this.on + ", won=" + this.won + ", rgbon=" + this.rgbon + ", red=" + this.red + ", green=" + this.green + ", blue=" + this.blue + ", rgbBrt=" + this.rgbBrt + ", wy=" + this.wy + ", wyBrt=" + this.wyBrt + ", isFavorite=" + this.isFavorite + ", onlineState=" + this.onlineState + ", onOffStates=" + this.onOffStates + ", acState='" + this.acState + "'}";
    }
}