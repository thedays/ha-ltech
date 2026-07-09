package com.ltech.smarthome.model.bean;

import com.ltech.smarthome.model.bean.Role;

/* loaded from: classes4.dex */
public class ItemHomePageLabel implements Role {
    private int index;
    private String name;
    private boolean show;
    private int type;

    @Override // com.ltech.smarthome.model.bean.Role
    public String getCreatetime() {
        return null;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public int getDaliHidden() {
        return 0;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public DeviceState getDeviceState() {
        return null;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public String getExtParam() {
        return null;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public long getFloorId() {
        return 0L;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public String getFloorName() {
        return null;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public int getIconPos() {
        return 0;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public long getId() {
        return 0L;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public long getObjectId() {
        return 0L;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public long getPlaceId() {
        return 0L;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public long getRoomId() {
        return 0L;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public String getRoomName() {
        return null;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public int getSceneType() {
        return 0;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public /* synthetic */ boolean isVirtual() {
        return Role.CC.$default$isVirtual(this);
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public void setDeviceState(DeviceState deviceState) {
    }

    public ItemHomePageLabel() {
    }

    public ItemHomePageLabel(int type, String name, boolean show) {
        this.type = type;
        this.name = name;
        this.show = show;
    }

    public ItemHomePageLabel(int type, String name, boolean show, int index) {
        this.type = type;
        this.name = name;
        this.show = show;
        this.index = index;
    }

    public int getType() {
        return this.type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public String getName() {
        return this.name;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public int getIndex() {
        return this.index;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public int getHideDevice() {
        return this.show ? 1 : 0;
    }
}