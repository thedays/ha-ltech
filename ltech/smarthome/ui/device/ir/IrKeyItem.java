package com.ltech.smarthome.ui.device.ir;

import com.blankj.utilcode.util.ActivityUtils;

/* loaded from: classes4.dex */
public class IrKeyItem {
    private boolean enable;
    private int fid;
    private int iconRes;
    private String name;

    public IrKeyItem(int iconRes, int nameRes, int fid) {
        this.iconRes = iconRes;
        this.name = ActivityUtils.getTopActivity().getString(nameRes);
        this.fid = fid;
    }

    public IrKeyItem(int iconRes, String name, int fid, boolean enable) {
        this.iconRes = iconRes;
        this.name = name;
        this.fid = fid;
        this.enable = enable;
    }

    public IrKeyItem(int iconRes, String name, int fid) {
        this.iconRes = iconRes;
        this.name = name;
        this.fid = fid;
    }

    public IrKeyItem(String name, int fid) {
        this.name = name;
        this.fid = fid;
    }

    public int getIconRes() {
        return this.iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnable() {
        return this.enable;
    }

    public IrKeyItem setEnable(boolean enable) {
        this.enable = enable;
        return this;
    }

    public int getFid() {
        return this.fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }
}