package com.ltech.smarthome.model.bean;

import java.util.ArrayList;

/* loaded from: classes4.dex */
public class RadiosInfo {
    public String radioId;
    public String radioName;
    public String radioPic;
    public ArrayList<String> songIds;

    public String getRadioId() {
        return this.radioId;
    }

    public void setRadioId(String radioId) {
        this.radioId = radioId;
    }

    public String getRadioName() {
        return this.radioName;
    }

    public void setRadioName(String radioName) {
        this.radioName = radioName;
    }

    public String getRadioPic() {
        return this.radioPic;
    }

    public void setRadioPic(String radioPic) {
        this.radioPic = radioPic;
    }

    public ArrayList<String> getSongIds() {
        return this.songIds;
    }

    public void setSongIds(ArrayList<String> songIds) {
        this.songIds = songIds;
    }
}