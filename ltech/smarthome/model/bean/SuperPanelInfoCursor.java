package com.ltech.smarthome.model.bean;

import com.github.mikephil.charting.utils.Utils;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.SuperPanelInfo;
import com.ltech.smarthome.model.bean.SuperPanelInfo_;
import com.ltech.smarthome.net.response.super_panel.DetailSuperPanelResponse;
import io.objectbox.BoxStore;
import io.objectbox.Cursor;
import io.objectbox.Transaction;
import io.objectbox.internal.CursorFactory;
import java.util.List;

/* loaded from: classes4.dex */
public final class SuperPanelInfoCursor extends Cursor<SuperPanelInfo> {
    private final Group.ListConverter deviceIdsConverter;
    private final SuperPanelInfo.DeviceInfoConverter devicesConverter;
    private final Group.ListConverter groupIdsConverter;
    private final SuperPanelInfo.GroupInfoConverter groupsConverter;
    private final SuperPanelInfo.PanelKeyInfo.PanelKeyInfoListConverter panelKeyInfoConverter;
    private final Group.ListConverter sceneIdsConverter;
    private final SuperPanelInfo.ScenesInfoConverter scenesConverter;
    private final SuperPanelInfo.SortInfoConverter sortRoleListConverter;
    private final SuperPanelInfo.SortInfoConverter sortSceneListConverter;
    private static final SuperPanelInfo_.SuperPanelInfoIdGetter ID_GETTER = SuperPanelInfo_.__ID_GETTER;
    private static final int __ID_deviceId = SuperPanelInfo_.deviceId.id;
    private static final int __ID_userId = SuperPanelInfo_.userId.id;
    private static final int __ID_deviceIds = SuperPanelInfo_.deviceIds.id;
    private static final int __ID_groupIds = SuperPanelInfo_.groupIds.id;
    private static final int __ID_sceneIds = SuperPanelInfo_.sceneIds.id;
    private static final int __ID_panelKeyInfo = SuperPanelInfo_.panelKeyInfo.id;
    private static final int __ID_sortRoleList = SuperPanelInfo_.sortRoleList.id;
    private static final int __ID_sortSceneList = SuperPanelInfo_.sortSceneList.id;
    private static final int __ID_devices = SuperPanelInfo_.devices.id;
    private static final int __ID_scenes = SuperPanelInfo_.scenes.id;
    private static final int __ID_groups = SuperPanelInfo_.groups.id;
    private static final int __ID_lastfirmwareversion = SuperPanelInfo_.lastfirmwareversion.id;
    private static final int __ID_lastmcuversion = SuperPanelInfo_.lastmcuversion.id;

    static final class Factory implements CursorFactory<SuperPanelInfo> {
        Factory() {
        }

        @Override // io.objectbox.internal.CursorFactory
        public Cursor<SuperPanelInfo> createCursor(Transaction tx, long cursorHandle, BoxStore boxStoreForEntities) {
            return new SuperPanelInfoCursor(tx, cursorHandle, boxStoreForEntities);
        }
    }

    public SuperPanelInfoCursor(Transaction tx, long cursor, BoxStore boxStore) {
        super(tx, cursor, SuperPanelInfo_.__INSTANCE, boxStore);
        this.deviceIdsConverter = new Group.ListConverter();
        this.groupIdsConverter = new Group.ListConverter();
        this.sceneIdsConverter = new Group.ListConverter();
        this.panelKeyInfoConverter = new SuperPanelInfo.PanelKeyInfo.PanelKeyInfoListConverter();
        this.sortRoleListConverter = new SuperPanelInfo.SortInfoConverter();
        this.sortSceneListConverter = new SuperPanelInfo.SortInfoConverter();
        this.devicesConverter = new SuperPanelInfo.DeviceInfoConverter();
        this.scenesConverter = new SuperPanelInfo.ScenesInfoConverter();
        this.groupsConverter = new SuperPanelInfo.GroupInfoConverter();
    }

    @Override // io.objectbox.Cursor
    public long getId(SuperPanelInfo entity) {
        return ID_GETTER.getId(entity);
    }

    @Override // io.objectbox.Cursor
    public long put(SuperPanelInfo entity) {
        List<Long> deviceIds = entity.getDeviceIds();
        int i = deviceIds != null ? __ID_deviceIds : 0;
        List<Long> groupIds = entity.getGroupIds();
        int i2 = groupIds != null ? __ID_groupIds : 0;
        List<Long> sceneIds = entity.getSceneIds();
        int i3 = sceneIds != null ? __ID_sceneIds : 0;
        List<SuperPanelInfo.PanelKeyInfo> panelKeyInfo = entity.getPanelKeyInfo();
        int i4 = panelKeyInfo != null ? __ID_panelKeyInfo : 0;
        collect400000(this.cursor, 0L, 1, i, i != 0 ? this.deviceIdsConverter.convertToDatabaseValue(deviceIds) : null, i2, i2 != 0 ? this.groupIdsConverter.convertToDatabaseValue(groupIds) : null, i3, i3 != 0 ? this.sceneIdsConverter.convertToDatabaseValue(sceneIds) : null, i4, i4 != 0 ? this.panelKeyInfoConverter.convertToDatabaseValue(panelKeyInfo) : null);
        List<SuperPanelInfo.SortInfo> sortRoleList = entity.getSortRoleList();
        int i5 = sortRoleList != null ? __ID_sortRoleList : 0;
        List<SuperPanelInfo.SortInfo> sortSceneList = entity.getSortSceneList();
        int i6 = sortSceneList != null ? __ID_sortSceneList : 0;
        List<DetailSuperPanelResponse.DevicesBean.RowsBean> devices = entity.getDevices();
        int i7 = devices != null ? __ID_devices : 0;
        List<DetailSuperPanelResponse.ScenesBean.RowsBean> scenes = entity.getScenes();
        int i8 = scenes != null ? __ID_scenes : 0;
        collect400000(this.cursor, 0L, 0, i5, i5 != 0 ? this.sortRoleListConverter.convertToDatabaseValue(sortRoleList) : null, i6, i6 != 0 ? this.sortSceneListConverter.convertToDatabaseValue(sortSceneList) : null, i7, i7 != 0 ? this.devicesConverter.convertToDatabaseValue(devices) : null, i8, i8 != 0 ? this.scenesConverter.convertToDatabaseValue(scenes) : null);
        List<DetailSuperPanelResponse.GroupsBean.RowsBean> groups = entity.getGroups();
        int i9 = groups != null ? __ID_groups : 0;
        String lastfirmwareversion = entity.getLastfirmwareversion();
        int i10 = lastfirmwareversion != null ? __ID_lastfirmwareversion : 0;
        String lastmcuversion = entity.getLastmcuversion();
        long collect313311 = collect313311(this.cursor, entity.getId(), 2, i9, i9 != 0 ? this.groupsConverter.convertToDatabaseValue(groups) : null, i10, lastfirmwareversion, lastmcuversion != null ? __ID_lastmcuversion : 0, lastmcuversion, 0, null, __ID_deviceId, entity.getDeviceId(), __ID_userId, entity.getUserId(), 0, 0L, 0, 0, 0, 0, 0, 0, 0, 0.0f, 0, Utils.DOUBLE_EPSILON);
        entity.setId(collect313311);
        return collect313311;
    }
}