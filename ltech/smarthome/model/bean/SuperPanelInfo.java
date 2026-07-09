package com.ltech.smarthome.model.bean;

import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.reflect.TypeToken;
import com.ltech.smarthome.net.response.super_panel.DetailSuperPanelResponse;
import io.objectbox.converter.PropertyConverter;
import java.util.List;

/* loaded from: classes4.dex */
public class SuperPanelInfo {
    private long deviceId;
    private List<Long> deviceIds;
    private List<DetailSuperPanelResponse.DevicesBean.RowsBean> devices;
    private List<Long> groupIds;
    private List<DetailSuperPanelResponse.GroupsBean.RowsBean> groups;
    private long id;
    private String lastfirmwareversion;
    private String lastmcuversion;
    private List<PanelKeyInfo> panelKeyInfo;
    private List<Long> sceneIds;
    private List<DetailSuperPanelResponse.ScenesBean.RowsBean> scenes;
    private List<SortInfo> sortRoleList;
    private List<SortInfo> sortSceneList;
    private long userId;

    public static class PanelKeyLight {
        public int actioncode;
        public int address;
        public String dataExtra;
        public int devicetype;
        public String wholeDataExtra;
        public int zone;
    }

    public String getLastfirmwareversion() {
        return this.lastfirmwareversion;
    }

    public void setLastfirmwareversion(String lastfirmwareversion) {
        this.lastfirmwareversion = lastfirmwareversion;
    }

    public String getLastmcuversion() {
        return this.lastmcuversion;
    }

    public void setLastmcuversion(String lastmcuversion) {
        this.lastmcuversion = lastmcuversion;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<Long> getDeviceIds() {
        return this.deviceIds;
    }

    public void setDeviceIds(List<Long> deviceIds) {
        this.deviceIds = deviceIds;
    }

    public List<Long> getGroupIds() {
        return this.groupIds;
    }

    public void setGroupIds(List<Long> groupIds) {
        this.groupIds = groupIds;
    }

    public List<Long> getSceneIds() {
        return this.sceneIds;
    }

    public void setSceneIds(List<Long> sceneIds) {
        this.sceneIds = sceneIds;
    }

    public List<PanelKeyInfo> getPanelKeyInfo() {
        return this.panelKeyInfo;
    }

    public void setPanelKeyInfo(List<PanelKeyInfo> panelKeyInfo) {
        this.panelKeyInfo = panelKeyInfo;
    }

    public List<SortInfo> getSortRoleList() {
        return this.sortRoleList;
    }

    public void setSortRoleList(List<SortInfo> sortRoleList) {
        this.sortRoleList = sortRoleList;
    }

    public List<SortInfo> getSortSceneList() {
        return this.sortSceneList;
    }

    public void setSortSceneList(List<SortInfo> sortSceneList) {
        this.sortSceneList = sortSceneList;
    }

    public void setDevices(List<DetailSuperPanelResponse.DevicesBean.RowsBean> devices) {
        this.devices = devices;
    }

    public List<DetailSuperPanelResponse.DevicesBean.RowsBean> getDevices() {
        return this.devices;
    }

    public void setScenes(List<DetailSuperPanelResponse.ScenesBean.RowsBean> scenes) {
        this.scenes = scenes;
    }

    public List<DetailSuperPanelResponse.ScenesBean.RowsBean> getScenes() {
        return this.scenes;
    }

    public void setGroups(List<DetailSuperPanelResponse.GroupsBean.RowsBean> groups) {
        this.groups = groups;
    }

    public List<DetailSuperPanelResponse.GroupsBean.RowsBean> getGroups() {
        return this.groups;
    }

    public static class SortInfo {
        private int objectType;
        private int sort;
        private long sortId;

        public int getObjectType() {
            return this.objectType;
        }

        public void setObjectType(int objectType) {
            this.objectType = objectType;
        }

        public long getSortId() {
            return this.sortId;
        }

        public void setSortId(long sortId) {
            this.sortId = sortId;
        }

        public int getSort() {
            return this.sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
    }

    public static class SortInfoConverter implements PropertyConverter<List<SortInfo>, String> {
        @Override // io.objectbox.converter.PropertyConverter
        public List<SortInfo> convertToEntityProperty(String databaseValue) {
            return (List) GsonUtils.fromJson(databaseValue, new TypeToken<List<SortInfo>>(this) { // from class: com.ltech.smarthome.model.bean.SuperPanelInfo.SortInfoConverter.1
            }.getType());
        }

        @Override // io.objectbox.converter.PropertyConverter
        public String convertToDatabaseValue(List<SortInfo> entityProperty) {
            return GsonUtils.toJson(entityProperty, new TypeToken<List<SortInfo>>(this) { // from class: com.ltech.smarthome.model.bean.SuperPanelInfo.SortInfoConverter.2
            }.getType());
        }
    }

    public static class DeviceInfoConverter implements PropertyConverter<List<DetailSuperPanelResponse.DevicesBean.RowsBean>, String> {
        @Override // io.objectbox.converter.PropertyConverter
        public List<DetailSuperPanelResponse.DevicesBean.RowsBean> convertToEntityProperty(String databaseValue) {
            return (List) GsonUtils.fromJson(databaseValue, new TypeToken<List<DetailSuperPanelResponse.DevicesBean.RowsBean>>(this) { // from class: com.ltech.smarthome.model.bean.SuperPanelInfo.DeviceInfoConverter.1
            }.getType());
        }

        @Override // io.objectbox.converter.PropertyConverter
        public String convertToDatabaseValue(List<DetailSuperPanelResponse.DevicesBean.RowsBean> entityProperty) {
            return GsonUtils.toJson(entityProperty, new TypeToken<List<DetailSuperPanelResponse.DevicesBean.RowsBean>>(this) { // from class: com.ltech.smarthome.model.bean.SuperPanelInfo.DeviceInfoConverter.2
            }.getType());
        }
    }

    public static class GroupInfoConverter implements PropertyConverter<List<DetailSuperPanelResponse.GroupsBean.RowsBean>, String> {
        @Override // io.objectbox.converter.PropertyConverter
        public List<DetailSuperPanelResponse.GroupsBean.RowsBean> convertToEntityProperty(String databaseValue) {
            return (List) GsonUtils.fromJson(databaseValue, new TypeToken<List<DetailSuperPanelResponse.GroupsBean.RowsBean>>(this) { // from class: com.ltech.smarthome.model.bean.SuperPanelInfo.GroupInfoConverter.1
            }.getType());
        }

        @Override // io.objectbox.converter.PropertyConverter
        public String convertToDatabaseValue(List<DetailSuperPanelResponse.GroupsBean.RowsBean> entityProperty) {
            return GsonUtils.toJson(entityProperty, new TypeToken<List<DetailSuperPanelResponse.GroupsBean.RowsBean>>(this) { // from class: com.ltech.smarthome.model.bean.SuperPanelInfo.GroupInfoConverter.2
            }.getType());
        }
    }

    public static class ScenesInfoConverter implements PropertyConverter<List<DetailSuperPanelResponse.ScenesBean.RowsBean>, String> {
        @Override // io.objectbox.converter.PropertyConverter
        public List<DetailSuperPanelResponse.ScenesBean.RowsBean> convertToEntityProperty(String databaseValue) {
            return (List) GsonUtils.fromJson(databaseValue, new TypeToken<List<DetailSuperPanelResponse.ScenesBean.RowsBean>>(this) { // from class: com.ltech.smarthome.model.bean.SuperPanelInfo.ScenesInfoConverter.1
            }.getType());
        }

        @Override // io.objectbox.converter.PropertyConverter
        public String convertToDatabaseValue(List<DetailSuperPanelResponse.ScenesBean.RowsBean> entityProperty) {
            return GsonUtils.toJson(entityProperty, new TypeToken<List<DetailSuperPanelResponse.ScenesBean.RowsBean>>(this) { // from class: com.ltech.smarthome.model.bean.SuperPanelInfo.ScenesInfoConverter.2
            }.getType());
        }
    }

    public static class PanelKeyInfo {
        private String action;
        private int actionType;
        private String actionTypeName;
        private String executecommand;
        private int keywords;
        private String keywordsname;
        private long panelinfoid;
        private int timeSpace;

        public long getPanelinfoid() {
            return this.panelinfoid;
        }

        public void setPanelinfoid(long panelinfoid) {
            this.panelinfoid = panelinfoid;
        }

        public String getExecutecommand() {
            return this.executecommand;
        }

        public void setExecutecommand(String executecommand) {
            this.executecommand = executecommand;
        }

        public void setExecutecommand(Object object) {
            this.executecommand = GsonUtils.toJson(object);
        }

        public <T> T getExecutecommand(Class<T> cls) {
            return (T) GsonUtils.getGson().fromJson(this.executecommand, (Class) cls);
        }

        public int getActionType() {
            return this.actionType;
        }

        public void setActionType(int actionType) {
            this.actionType = actionType;
        }

        public int getKeywords() {
            return this.keywords;
        }

        public void setKeywords(int keywords) {
            this.keywords = keywords;
        }

        public String getKeywordsname() {
            return this.keywordsname;
        }

        public void setKeywordsname(String keywordsname) {
            this.keywordsname = keywordsname;
        }

        public String getAction() {
            return this.action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public int getTimeSpace() {
            return this.timeSpace;
        }

        public void setTimeSpace(int timeSpace) {
            this.timeSpace = timeSpace;
        }

        public String getActionTypeName() {
            return this.actionTypeName;
        }

        public void setActionTypeName(String actionTypeName) {
            this.actionTypeName = actionTypeName;
        }

        public boolean equals(Object var1) {
            if (var1 instanceof PanelKeyInfo) {
                return ((PanelKeyInfo) var1).getKeywords() == getKeywords();
            }
            return super.equals(var1);
        }

        public static class PanelKeyInfoListConverter implements PropertyConverter<List<PanelKeyInfo>, String> {
            @Override // io.objectbox.converter.PropertyConverter
            public List<PanelKeyInfo> convertToEntityProperty(String databaseValue) {
                return (List) GsonUtils.fromJson(databaseValue, new TypeToken<List<PanelKeyInfo>>(this) { // from class: com.ltech.smarthome.model.bean.SuperPanelInfo.PanelKeyInfo.PanelKeyInfoListConverter.1
                }.getType());
            }

            @Override // io.objectbox.converter.PropertyConverter
            public String convertToDatabaseValue(List<PanelKeyInfo> entityProperty) {
                return GsonUtils.toJson(entityProperty, new TypeToken<List<PanelKeyInfo>>(this) { // from class: com.ltech.smarthome.model.bean.SuperPanelInfo.PanelKeyInfo.PanelKeyInfoListConverter.2
                }.getType());
            }
        }
    }
}