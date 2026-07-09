package com.ltech.smarthome.model.key;

import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Scene;

/* loaded from: classes4.dex */
public class KeyInfo {
    public static final int TYPE_DEVICE = 1;
    public static final int TYPE_GROUP = 2;
    public static final int TYPE_SCENE = 3;
    private int actionType;
    private int iconIndex;
    private String instruction;
    private long keyinfoid;
    private String name;
    private long objectId;
    private int objectType;
    private String option;
    private String optionValue;
    private String screenStr;
    private int screenType;
    private int sensitivity;
    private int zoneNum;

    public KeyInfo(Object o) {
        if (o instanceof Device) {
            this.objectType = 1;
            this.objectId = ((Device) o).getDeviceId();
        } else if (o instanceof Group) {
            this.objectType = 2;
            this.objectId = ((Group) o).getGroupId();
        } else if (o instanceof Scene) {
            this.objectType = 3;
            this.objectId = ((Scene) o).getSceneId();
        }
    }

    public Object getRelateObject() {
        int i = this.objectType;
        if (i == 1) {
            return Injection.repo().device().getDeviceByDeviceId(this.objectId);
        }
        if (i == 2) {
            return Injection.repo().group().getGroupByGroupId(this.objectId);
        }
        if (i == 3) {
            return Injection.repo().scene().getSceneBySceneId(this.objectId);
        }
        return null;
    }

    public long getKeyinfoid() {
        return this.keyinfoid;
    }

    public void setKeyinfoid(long keyinfoid) {
        this.keyinfoid = keyinfoid;
    }

    public int getZoneNum() {
        return this.zoneNum;
    }

    public void setZoneNum(int zoneNum) {
        this.zoneNum = zoneNum;
    }

    public int getActionType() {
        return this.actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public int getObjectType() {
        return this.objectType;
    }

    public void setObjectType(int objectType) {
        this.objectType = objectType;
    }

    public long getObjectId() {
        return this.objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstruction() {
        return this.instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getScreenStr() {
        return this.screenStr;
    }

    public void setScreenStr(String screenStr) {
        this.screenStr = screenStr;
    }

    public int getScreenType() {
        return this.screenType;
    }

    public void setScreenType(int screenType) {
        this.screenType = screenType;
    }

    public int getIconIndex() {
        return this.iconIndex;
    }

    public void setIconIndex(int iconIndex) {
        this.iconIndex = iconIndex;
    }

    public String getOptionValue() {
        return this.optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    public String getOption() {
        return this.option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public int getSensitivity() {
        return this.sensitivity;
    }

    public void setSensitivity(int sensitivity) {
        this.sensitivity = sensitivity;
    }

    public String toString() {
        return "KeyInfo{keyinfoid=" + this.keyinfoid + ", zoneNum=" + this.zoneNum + ", actionType=" + this.actionType + ", objectType=" + this.objectType + ", objectId=" + this.objectId + ", name='" + this.name + "', instruction='" + this.instruction + "', screenStr='" + this.screenStr + "', screenType=" + this.screenType + ", iconIndex=" + this.iconIndex + ", optionValue='" + this.optionValue + "', option='" + this.option + "', sensitivity=" + this.sensitivity + '}';
    }
}