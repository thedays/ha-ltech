package com.ltech.smarthome.net.request.device;

import com.ltech.smarthome.model.key.KeyInfo;
import com.ltech.smarthome.model.key.KeyZone;
import java.util.List;

/* loaded from: classes4.dex */
public class KeyInfoRequest {
    private long deviceid;
    private List<KeyInfo> keyinfos;
    private KeyZone keyzone;
    private List<KeyZone> keyzones;
    private int zoneNum;

    public KeyZone getKeyzone() {
        return this.keyzone;
    }

    public void setKeyzone(KeyZone keyzone) {
        this.keyzone = keyzone;
    }

    public List<KeyZone> getKeyZones() {
        return this.keyzones;
    }

    public void setKeyZones(List<KeyZone> keyzones) {
        this.keyzones = keyzones;
    }

    public int getZoneNum() {
        return this.zoneNum;
    }

    public void setZoneNum(int zoneNum) {
        this.zoneNum = zoneNum;
    }

    public List<KeyInfo> getKeyinfos() {
        return this.keyinfos;
    }

    public void setKeyinfos(List<KeyInfo> keyinfos) {
        this.keyinfos = keyinfos;
    }

    public long getDeviceid() {
        return this.deviceid;
    }

    public void setDeviceid(long deviceid) {
        this.deviceid = deviceid;
    }
}