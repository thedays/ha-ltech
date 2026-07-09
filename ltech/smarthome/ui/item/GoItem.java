package com.ltech.smarthome.ui.item;

import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes4.dex */
public class GoItem {
    private BindingCommand action;
    private int color = -5592406;
    private boolean enable = true;
    private int goRes;
    private int imageRes;
    private Object imageUrl;
    private String mainText;
    private boolean select;
    private int subImageRes;
    private String subText;

    public int getColor() {
        return this.color;
    }

    public GoItem setColor(int color) {
        this.color = color;
        return this;
    }

    public boolean isEnable() {
        return this.enable;
    }

    public GoItem setEnable(boolean enable) {
        this.enable = enable;
        return this;
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

    public GoItem setMainText(String mainText) {
        this.mainText = mainText;
        return this;
    }

    public GoItem setSubText(String subText) {
        this.subText = subText;
        return this;
    }

    public GoItem setSubImageRes(int imageRes) {
        this.subImageRes = imageRes;
        return this;
    }

    public GoItem setImageUrl(Object imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public GoItem setImageRes(int imageRes) {
        this.imageRes = imageRes;
        return this;
    }

    public GoItem setGoRes(int goRes) {
        this.goRes = goRes;
        return this;
    }

    public GoItem setAction(BindingCommand action) {
        this.action = action;
        return this;
    }

    public boolean isSelect() {
        return this.select;
    }

    public GoItem setSelect(boolean select) {
        this.select = select;
        return this;
    }
}