package com.ltech.smarthome.model.bean;

import java.util.ArrayList;

/* loaded from: classes4.dex */
public class RadioInfo {
    public String columnId;
    public String columnName;
    public ArrayList<RadiosInfo> radios;

    public String getColumnId() {
        return this.columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public ArrayList<RadiosInfo> getRadios() {
        return this.radios;
    }

    public void setRadios(ArrayList<RadiosInfo> radios) {
        this.radios = radios;
    }
}