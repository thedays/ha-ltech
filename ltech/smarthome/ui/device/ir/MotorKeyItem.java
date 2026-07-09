package com.ltech.smarthome.ui.device.ir;

import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.utils.IconRepository;

/* loaded from: classes4.dex */
public class MotorKeyItem {
    private String extraData;
    private int iconIndex;
    private int iconRes;
    private String key;
    private String name;

    public MotorKeyItem(int iconRes, int nameRes, String key) {
        this(iconRes, nameRes, key, 0, null);
    }

    public MotorKeyItem(int iconRes, int nameRes, String key, int iconIndex, String extraData) {
        this.iconRes = iconRes;
        this.name = ActivityUtils.getTopActivity().getString(nameRes);
        this.key = key;
        this.iconIndex = iconIndex;
        this.extraData = extraData;
    }

    public MotorKeyItem(int nameRes, String key, int iconIndex, String extraData) {
        this.iconRes = IconRepository.getSceneIcons(ActivityUtils.getTopActivity())[iconIndex];
        this.name = ActivityUtils.getTopActivity().getString(nameRes);
        this.key = key;
        this.iconIndex = iconIndex;
        this.extraData = extraData;
    }

    public MotorKeyItem(int iconRes, String nameRes, String key, String extraData) {
        this.iconRes = iconRes;
        this.name = nameRes;
        this.key = key;
        this.extraData = extraData;
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

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getIconIndex() {
        return this.iconIndex;
    }

    public void setIconIndex(int iconIndex) {
        this.iconIndex = iconIndex;
        this.iconRes = IconRepository.getSceneIcons(ActivityUtils.getTopActivity())[iconIndex];
    }

    public String getExtraData() {
        return this.extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }
}