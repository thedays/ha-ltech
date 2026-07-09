package com.ltech.smarthome.model.bean;

import com.github.mikephil.charting.utils.Utils;
import com.ltech.smarthome.model.bean.DeviceState;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Group_;
import com.ltech.smarthome.model.bean.KValue;
import io.objectbox.BoxStore;
import io.objectbox.Cursor;
import io.objectbox.Transaction;
import io.objectbox.internal.CursorFactory;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public final class GroupCursor extends Cursor<Group> {
    private final Group.ListConverter deviceIdsConverter;
    private final DeviceState.DeviceStateConverter groupStateConverter;
    private final KValue.Converter presetKValuesConverter;
    private static final Group_.GroupIdGetter ID_GETTER = Group_.__ID_GETTER;
    private static final int __ID_groupId = Group_.groupId.id;
    private static final int __ID_userId = Group_.userId.id;
    private static final int __ID_placeId = Group_.placeId.id;
    private static final int __ID_groupName = Group_.groupName.id;
    private static final int __ID_moduleType = Group_.moduleType.id;
    private static final int __ID_controlType = Group_.controlType.id;
    private static final int __ID_groupAddress = Group_.groupAddress.id;
    private static final int __ID_groupIndex = Group_.groupIndex.id;
    private static final int __ID_param = Group_.param.id;
    private static final int __ID_extParam = Group_.extParam.id;
    private static final int __ID_floorId = Group_.floorId.id;
    private static final int __ID_roomId = Group_.roomId.id;
    private static final int __ID_createtime = Group_.createtime.id;
    private static final int __ID_deviceIds = Group_.deviceIds.id;
    private static final int __ID_groupState = Group_.groupState.id;
    private static final int __ID_floorName = Group_.floorName.id;
    private static final int __ID_roomName = Group_.roomName.id;
    private static final int __ID_memorizePowerOff = Group_.memorizePowerOff.id;
    private static final int __ID_setting = Group_.setting.id;
    private static final int __ID_firstDevUniAddr = Group_.firstDevUniAddr.id;
    private static final int __ID_imgindex = Group_.imgindex.id;
    private static final int __ID_minkelvin = Group_.minkelvin.id;
    private static final int __ID_maxkelvin = Group_.maxkelvin.id;
    private static final int __ID_lightPlanData = Group_.lightPlanData.id;
    private static final int __ID_macdeviceid = Group_.macdeviceid.id;
    private static final int __ID_subkey = Group_.subkey.id;
    private static final int __ID_maingroupid = Group_.maingroupid.id;
    private static final int __ID_mainModuleType = Group_.mainModuleType.id;
    private static final int __ID_mainControlType = Group_.mainControlType.id;
    private static final int __ID_subhide = Group_.subhide.id;
    private static final int __ID_screenSetting = Group_.screenSetting.id;
    private static final int __ID_panelSettingState = Group_.panelSettingState.id;
    private static final int __ID_editTime = Group_.editTime.id;
    private static final int __ID_colorPaletteUrl = Group_.colorPaletteUrl.id;
    private static final int __ID_checkTime = Group_.checkTime.id;
    private static final int __ID_reportinstruct = Group_.reportinstruct.id;
    private static final int __ID_leaderSup = Group_.leaderSup.id;
    private static final int __ID_presetKValues = Group_.presetKValues.id;

    static final class Factory implements CursorFactory<Group> {
        Factory() {
        }

        @Override // io.objectbox.internal.CursorFactory
        public Cursor<Group> createCursor(Transaction tx, long cursorHandle, BoxStore boxStoreForEntities) {
            return new GroupCursor(tx, cursorHandle, boxStoreForEntities);
        }
    }

    public GroupCursor(Transaction tx, long cursor, BoxStore boxStore) {
        super(tx, cursor, Group_.__INSTANCE, boxStore);
        this.deviceIdsConverter = new Group.ListConverter();
        this.groupStateConverter = new DeviceState.DeviceStateConverter();
        this.presetKValuesConverter = new KValue.Converter();
    }

    @Override // io.objectbox.Cursor
    public long getId(Group entity) {
        return ID_GETTER.getId(entity);
    }

    @Override // io.objectbox.Cursor
    public long put(Group entity) {
        String groupName = entity.getGroupName();
        int i = groupName != null ? __ID_groupName : 0;
        String moduleType = entity.getModuleType();
        int i2 = moduleType != null ? __ID_moduleType : 0;
        String controlType = entity.getControlType();
        int i3 = controlType != null ? __ID_controlType : 0;
        String param = entity.getParam();
        collect400000(this.cursor, 0L, 1, i, groupName, i2, moduleType, i3, controlType, param != null ? __ID_param : 0, param);
        String extParam = entity.getExtParam();
        int i4 = extParam != null ? __ID_extParam : 0;
        String createtime = entity.getCreatetime();
        int i5 = createtime != null ? __ID_createtime : 0;
        List<Long> deviceIds = entity.getDeviceIds();
        int i6 = deviceIds != null ? __ID_deviceIds : 0;
        DeviceState groupState = entity.getGroupState();
        int i7 = groupState != null ? __ID_groupState : 0;
        collect400000(this.cursor, 0L, 0, i4, extParam, i5, createtime, i6, i6 != 0 ? this.deviceIdsConverter.convertToDatabaseValue(deviceIds) : null, i7, i7 != 0 ? this.groupStateConverter.convertToDatabaseValue(groupState) : null);
        String floorName = entity.getFloorName();
        int i8 = floorName != null ? __ID_floorName : 0;
        String roomName = entity.getRoomName();
        int i9 = roomName != null ? __ID_roomName : 0;
        String setting = entity.getSetting();
        int i10 = setting != null ? __ID_setting : 0;
        String lightPlanData = entity.getLightPlanData();
        collect400000(this.cursor, 0L, 0, i8, floorName, i9, roomName, i10, setting, lightPlanData != null ? __ID_lightPlanData : 0, lightPlanData);
        String mainModuleType = entity.getMainModuleType();
        int i11 = mainModuleType != null ? __ID_mainModuleType : 0;
        String mainControlType = entity.getMainControlType();
        int i12 = mainControlType != null ? __ID_mainControlType : 0;
        String screenSetting = entity.getScreenSetting();
        int i13 = screenSetting != null ? __ID_screenSetting : 0;
        String panelSettingState = entity.getPanelSettingState();
        collect400000(this.cursor, 0L, 0, i11, mainModuleType, i12, mainControlType, i13, screenSetting, panelSettingState != null ? __ID_panelSettingState : 0, panelSettingState);
        String colorPaletteUrl = entity.getColorPaletteUrl();
        int i14 = colorPaletteUrl != null ? __ID_colorPaletteUrl : 0;
        String reportinstruct = entity.getReportinstruct();
        int i15 = reportinstruct != null ? __ID_reportinstruct : 0;
        Map<String, KValue> presetKValues = entity.getPresetKValues();
        int i16 = presetKValues != null ? __ID_presetKValues : 0;
        collect313311(this.cursor, 0L, 0, i14, colorPaletteUrl, i15, reportinstruct, i16, i16 != 0 ? this.presetKValuesConverter.convertToDatabaseValue(presetKValues) : null, 0, null, __ID_groupId, entity.getGroupId(), __ID_userId, entity.getUserId(), __ID_placeId, entity.getPlaceId(), __ID_groupAddress, entity.getGroupAddress(), __ID_groupIndex, entity.getGroupIndex(), __ID_memorizePowerOff, entity.getMemorizePowerOff(), 0, 0.0f, 0, Utils.DOUBLE_EPSILON);
        collect313311(this.cursor, 0L, 0, 0, null, 0, null, 0, null, 0, null, __ID_floorId, entity.getFloorId(), __ID_roomId, entity.getRoomId(), __ID_macdeviceid, entity.getMacdeviceid(), __ID_firstDevUniAddr, entity.getFirstDevUniAddr(), __ID_imgindex, entity.getImgindex(), __ID_minkelvin, entity.getMinkelvin(), 0, 0.0f, 0, Utils.DOUBLE_EPSILON);
        collect004000(this.cursor, 0L, 0, __ID_maingroupid, entity.getMaingroupid(), __ID_editTime, entity.getEditTime(), __ID_checkTime, entity.getCheckTime(), __ID_maxkelvin, entity.getMaxkelvin());
        long collect004000 = collect004000(this.cursor, entity.getId(), 2, __ID_subkey, entity.getSubkey(), __ID_subhide, entity.getSubhide(), __ID_leaderSup, entity.getLeaderSup(), 0, 0L);
        entity.setId(collect004000);
        return collect004000;
    }
}