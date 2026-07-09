package com.ltech.smarthome.model.bean;

import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ResultRadioList {
    public ArrayList<RadioInfo> radioInfos;

    public ResultRadioList(ArrayList<RadioInfo> radioInfos) {
        new ArrayList();
        this.radioInfos = radioInfos;
    }

    public ArrayList<RadioInfo> getRadioInfos() {
        return this.radioInfos;
    }

    public void setRadioInfos(ArrayList<RadioInfo> radioInfos) {
        this.radioInfos = radioInfos;
    }
}