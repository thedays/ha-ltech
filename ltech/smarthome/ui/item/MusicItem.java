package com.ltech.smarthome.ui.item;

/* loaded from: classes4.dex */
public class MusicItem {
    private int imageRes;
    private boolean isMainArea;
    private boolean isSelect;
    private String mainText;
    private String subText;

    public String getMainText() {
        return this.mainText;
    }

    public MusicItem setMainText(String mainText) {
        this.mainText = mainText;
        return this;
    }

    public String getSubText() {
        return this.subText;
    }

    public void setSubText(String subText) {
        this.subText = subText;
    }

    public int getImageRes() {
        return this.imageRes;
    }

    public MusicItem setImageRes(int imageRes) {
        this.imageRes = imageRes;
        return this;
    }

    public boolean isSelect() {
        return this.isSelect;
    }

    public void setSelect(boolean select) {
        this.isSelect = select;
    }

    public boolean isMainArea() {
        return this.isMainArea;
    }

    public void setMainArea(boolean mainArea) {
        this.isMainArea = mainArea;
    }
}