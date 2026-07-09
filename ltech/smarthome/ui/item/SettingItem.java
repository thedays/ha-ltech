package com.ltech.smarthome.ui.item;

import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes4.dex */
public class SettingItem {
    private BindingCommand action;
    private int goRes;
    private int imageRes;
    private Object imageUrl;
    private String mainText;
    private boolean select;
    private int subImageRes;
    private String subText;
    private boolean mainResShow = true;
    private boolean mainTextShow = true;
    private boolean subResShow = true;
    private boolean subTextShow = true;
    private int color = -13487566;
    private boolean enable = true;

    public int getColor() {
        return this.color;
    }

    public SettingItem setColor(int color) {
        this.color = color;
        return this;
    }

    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getImageRes() {
        return this.imageRes;
    }

    public int getSubImageRes() {
        return this.subImageRes;
    }

    public Object getImageUrl() {
        return this.imageUrl;
    }

    public BindingCommand getAction() {
        return this.action;
    }

    public String getMainText() {
        return this.mainText;
    }

    public String getSubText() {
        return this.subText;
    }

    public int getGoRes() {
        return this.goRes;
    }

    public SettingItem setMainText(String mainText) {
        this.mainText = mainText;
        return this;
    }

    public SettingItem setSubText(String subText) {
        this.subText = subText;
        return this;
    }

    public SettingItem setSubImageRes(int imageRes) {
        this.subImageRes = imageRes;
        return this;
    }

    public SettingItem setImageUrl(Object imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public SettingItem setImageRes(int imageRes) {
        this.imageRes = imageRes;
        return this;
    }

    public SettingItem setGoRes(int goRes) {
        this.goRes = goRes;
        return this;
    }

    public SettingItem setAction(BindingCommand action) {
        this.action = action;
        return this;
    }

    public boolean isSelect() {
        return this.select;
    }

    public SettingItem setSelect(boolean select) {
        this.select = select;
        return this;
    }

    public boolean isMainResShow() {
        return this.mainResShow;
    }

    public boolean isMainTextShow() {
        return this.mainTextShow;
    }

    public boolean isSubResShow() {
        return this.subResShow;
    }

    public boolean isSubTextShow() {
        return this.subTextShow;
    }

    public SettingItem setMainResShow(boolean mainResShow) {
        this.mainResShow = mainResShow;
        return this;
    }

    public SettingItem setMainTextShow(boolean mainTextShow) {
        this.mainTextShow = mainTextShow;
        return this;
    }

    public SettingItem setSubResShow(boolean subResShow) {
        this.subResShow = subResShow;
        return this;
    }

    public SettingItem setSubTextShow(boolean subTextShow) {
        this.subTextShow = subTextShow;
        return this;
    }
}