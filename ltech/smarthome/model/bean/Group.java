package com.ltech.smarthome.model.bean;

import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.reflect.TypeToken;
import com.ltech.smarthome.base.BaseExtParam;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.device_param.CgdProGroupExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import io.objectbox.converter.PropertyConverter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class Group implements Role {
    private long checkTime;
    private String colorPaletteUrl;
    private String controlType;
    private String createtime;
    private List<Long> deviceIds;
    private long editTime;
    private String extParam;
    private int firstDevUniAddr;
    private long floorId;
    private String floorName;
    private int groupAddress;
    private long groupId;
    private int groupIndex;
    private String groupName;
    private DeviceState groupState;
    private long id;
    private int imgindex;
    private int leaderSup;
    private String lightPlanData;
    private long macdeviceid;
    private String mainControlType;
    private String mainModuleType;
    private long maingroupid;
    private int maxkelvin;
    private int memorizePowerOff;
    private int minkelvin;
    private String moduleType;
    private String panelSettingState;
    private String param;
    private long placeId;
    private Map<String, KValue> presetKValues = new HashMap();
    private String reportinstruct;
    private long roomId;
    private String roomName;
    private String screenSetting;
    private String setting;
    private int subhide;
    private int subkey;
    private long userId;

    @Override // com.ltech.smarthome.model.bean.Role
    public int getIconPos() {
        return 0;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public int getSceneType() {
        return 0;
    }

    public int getLeaderSup() {
        return this.leaderSup;
    }

    public void setLeaderSup(int leaderSup) {
        this.leaderSup = leaderSup;
    }

    public String getColorPaletteUrl() {
        return this.colorPaletteUrl;
    }

    public void setColorPaletteUrl(String colorPaletteUrl) {
        this.colorPaletteUrl = colorPaletteUrl;
    }

    public long getEditTime() {
        return this.editTime;
    }

    public void setEditTime(long editTime) {
        this.editTime = editTime;
    }

    public int getSubhide() {
        return this.subhide;
    }

    public void setSubhide(int subhide) {
        this.subhide = subhide;
    }

    public int getSubkey() {
        return this.subkey;
    }

    public void setSubkey(int subkey) {
        this.subkey = subkey;
    }

    public long getMaingroupid() {
        return this.maingroupid;
    }

    public void setMaingroupid(long maingroupid) {
        this.maingroupid = maingroupid;
    }

    public String getLightPlanData() {
        return this.lightPlanData;
    }

    public void setLightPlanData(String lightPlanData) {
        this.lightPlanData = lightPlanData;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGroupId() {
        return this.groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public long getPlaceId() {
        return this.placeId;
    }

    public void setPlaceId(long placeId) {
        this.placeId = placeId;
    }

    public String getModuleType() {
        return this.moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public String getControlType() {
        return this.controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    public int getGroupAddress() {
        return this.groupAddress;
    }

    public void setGroupAddress(int groupAddress) {
        this.groupAddress = groupAddress;
    }

    public List<Long> getDeviceIds() {
        return this.deviceIds;
    }

    public void setDeviceIds(List<Long> deviceIds) {
        this.deviceIds = deviceIds;
    }

    public DeviceState getGroupState() {
        if (this.groupState == null) {
            this.groupState = new DeviceState();
            if (getMaingroupid() > 0) {
                this.groupState.setOn(false);
            } else {
                int zoneCount = ProductRepository.getZoneCount(ProductRepository.getLightColorType((Object) this));
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < zoneCount; i++) {
                    arrayList.add(false);
                }
                this.groupState.setOnOffStates(arrayList);
            }
        }
        return this.groupState;
    }

    public void setGroupState(DeviceState groupState) {
        this.groupState = groupState;
    }

    public int getGroupIndex() {
        return this.groupIndex;
    }

    public void setGroupIndex(int groupIndex) {
        this.groupIndex = groupIndex;
    }

    public String getParam() {
        return this.param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public void setParam(Object object) {
        this.param = GsonUtils.toJson(object);
    }

    public <T> T getParam(Class<T> cls) {
        return (T) GsonUtils.getGson().fromJson(this.param, (Class) cls);
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public String getExtParam() {
        return this.extParam;
    }

    public void setExtParam(String extParam) {
        this.extParam = extParam;
    }

    public void setExtParam(Object object) {
        this.extParam = GsonUtils.toJson(object);
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public long getFloorId() {
        return this.floorId;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public DeviceState getDeviceState() {
        return getGroupState();
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public String getFloorName() {
        String str = this.floorName;
        return str == null ? "" : str;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public String getRoomName() {
        String str = this.roomName;
        return str == null ? "" : str;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public int getHideDevice() {
        BaseExtParam baseExtParam;
        try {
            if (this.maingroupid > 0 && getSubhide() == 1) {
                return 1;
            }
            if (getExtParam() == null || (baseExtParam = (BaseExtParam) GsonUtils.getGson().fromJson(getExtParam(), BaseExtParam.class)) == null) {
                return 0;
            }
            return baseExtParam.getHideDevice();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public int getDaliHidden() {
        CgdProGroupExtParam cgdProGroupExtParam;
        try {
            if (getExtParam() == null || (cgdProGroupExtParam = (CgdProGroupExtParam) GsonUtils.getGson().fromJson(getExtParam(), CgdProGroupExtParam.class)) == null) {
                return 0;
            }
            return cgdProGroupExtParam.getDaliHidden();
        } catch (Exception unused) {
            return 0;
        }
    }

    public void setFloorId(long floorId) {
        this.floorId = floorId;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public long getRoomId() {
        return this.roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public <T> T getExtParam(Class<T> cls) {
        return (T) GsonUtils.getGson().fromJson(this.extParam, (Class) cls);
    }

    public boolean equals(Object var1) {
        if (var1 instanceof Group) {
            Group group = (Group) var1;
            return group.getGroupId() == getGroupId() && group.getPlaceId() == getPlaceId() && group.getUserId() == getUserId();
        }
        return super.equals(var1);
    }

    public int hashCode() {
        return super.hashCode();
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public String getName() {
        return getGroupName();
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public int getIndex() {
        return getGroupIndex();
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public long getObjectId() {
        return getGroupId();
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setFirstDevUniAddr(int firstDevUniAddr) {
        this.firstDevUniAddr = firstDevUniAddr;
    }

    public int getFirstDevUniAddr() {
        return this.firstDevUniAddr;
    }

    public void setMainModuleType(String mainModuleType) {
        this.mainModuleType = mainModuleType;
    }

    public String getMainModuleType() {
        return this.mainModuleType;
    }

    public void setMainControlType(String mainControlType) {
        this.mainControlType = mainControlType;
    }

    public String getMainControlType() {
        return this.mainControlType;
    }

    public void setScreenSetting(String screenSetting) {
        this.screenSetting = screenSetting;
    }

    public String getScreenSetting() {
        return this.screenSetting;
    }

    public void setPanelSettingState(String panelSettingState) {
        this.panelSettingState = panelSettingState;
    }

    public String getPanelSettingState() {
        return this.panelSettingState;
    }

    public long getCheckTime() {
        return this.checkTime;
    }

    public void setCheckTime(long checkTime) {
        this.checkTime = checkTime;
    }

    public void setReportinstruct(String reportinstruct) {
        this.reportinstruct = reportinstruct;
    }

    public String getReportinstruct() {
        return this.reportinstruct;
    }

    public static class ListConverter implements PropertyConverter<List<Long>, String> {
        @Override // io.objectbox.converter.PropertyConverter
        public List<Long> convertToEntityProperty(String databaseValue) {
            return (List) GsonUtils.fromJson(databaseValue, new TypeToken<List<Long>>(this) { // from class: com.ltech.smarthome.model.bean.Group.ListConverter.1
            }.getType());
        }

        @Override // io.objectbox.converter.PropertyConverter
        public String convertToDatabaseValue(List<Long> entityProperty) {
            return GsonUtils.toJson(entityProperty, new TypeToken<List<Long>>(this) { // from class: com.ltech.smarthome.model.bean.Group.ListConverter.2
            }.getType());
        }
    }

    public String getGroupKey() {
        return ProductId.CC.createGroupKey(this.moduleType, this.controlType);
    }

    public String getMainGroupKey() {
        if (this.maingroupid <= 0) {
            return "";
        }
        return ProductId.CC.createGroupKey(this.mainModuleType, this.mainControlType);
    }

    public int getMemorizePowerOff() {
        return this.memorizePowerOff;
    }

    public void setMemorizePowerOff(int memorizePowerOff) {
        this.memorizePowerOff = memorizePowerOff;
    }

    public String getSetting() {
        return this.setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public String getCreatetime() {
        String str = this.createtime;
        return str == null ? "" : str;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public void setDeviceState(DeviceState deviceState) {
        setGroupState(deviceState);
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getMinkelvin() {
        return this.minkelvin;
    }

    public void setMinkelvin(int minkelvin) {
        this.minkelvin = minkelvin;
    }

    public int getMaxkelvin() {
        return this.maxkelvin;
    }

    public void setMaxkelvin(int maxkelvin) {
        this.maxkelvin = maxkelvin;
    }

    public long getMacdeviceid() {
        return this.macdeviceid;
    }

    public void setMacdeviceid(long macdeviceid) {
        this.macdeviceid = macdeviceid;
    }

    public int getImgindex() {
        return this.imgindex;
    }

    public void setImgindex(int imgindex) {
        this.imgindex = imgindex;
    }

    public Map<String, KValue> getPresetKValues() {
        if (this.presetKValues == null) {
            this.presetKValues = new HashMap();
        }
        return this.presetKValues;
    }

    public void setPresetKValues(Map<String, KValue> presetKValues) {
        this.presetKValues = presetKValues;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public boolean isVirtual() {
        if (this.deviceIds.isEmpty()) {
            return false;
        }
        Iterator<Long> it = this.deviceIds.iterator();
        while (it.hasNext()) {
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(it.next().longValue());
            if (deviceByDeviceId == null || !deviceByDeviceId.isVirtual()) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        return "Group{id=" + this.id + ", groupId=" + this.groupId + ", userId=" + this.userId + ", placeId=" + this.placeId + ", groupName='" + this.groupName + "', moduleType='" + this.moduleType + "', controlType='" + this.controlType + "', groupAddress=" + this.groupAddress + ", groupIndex=" + this.groupIndex + ", param='" + this.param + "', extParam='" + this.extParam + "', floorId=" + this.floorId + ", roomId=" + this.roomId + ", createtime='" + this.createtime + "', deviceIds=" + this.deviceIds + ", groupState=" + this.groupState + ", floorName='" + this.floorName + "', roomName='" + this.roomName + "', memorizePowerOff=" + this.memorizePowerOff + ", setting='" + this.setting + "', firstDevUniAddr=" + this.firstDevUniAddr + ", imgindex=" + this.imgindex + ", minkelvin=" + this.minkelvin + ", maxkelvin=" + this.maxkelvin + ", lightPlanData='" + this.lightPlanData + "', macdeviceid=" + this.macdeviceid + ", subkey=" + this.subkey + ", maingroupid=" + this.maingroupid + ", mainModuleType='" + this.mainModuleType + "', mainControlType='" + this.mainControlType + "', subhide=" + this.subhide + ", screenSetting='" + this.screenSetting + "', panelSettingState='" + this.panelSettingState + "'}";
    }
}