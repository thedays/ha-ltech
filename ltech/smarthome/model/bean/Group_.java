package com.ltech.smarthome.model.bean;

import com.huawei.hms.push.constant.RemoteMessageConst;
import com.justalk.cloud.lemon.MtcConf2Constants;
import com.ltech.smarthome.model.bean.DeviceState;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.GroupCursor;
import com.ltech.smarthome.model.bean.KValue;
import com.ltech.smarthome.push.PushContentParamKey;
import io.objectbox.EntityInfo;
import io.objectbox.Property;
import io.objectbox.internal.CursorFactory;
import io.objectbox.internal.IdGetter;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public final class Group_ implements EntityInfo<Group> {
    public static final Property<Group>[] __ALL_PROPERTIES;
    public static final String __DB_NAME = "Group";
    public static final int __ENTITY_ID = 4;
    public static final String __ENTITY_NAME = "Group";
    public static final Property<Group> __ID_PROPERTY;
    public static final Group_ __INSTANCE;
    public static final Property<Group> checkTime;
    public static final Property<Group> colorPaletteUrl;
    public static final Property<Group> controlType;
    public static final Property<Group> createtime;
    public static final Property<Group> deviceIds;
    public static final Property<Group> editTime;
    public static final Property<Group> extParam;
    public static final Property<Group> firstDevUniAddr;
    public static final Property<Group> floorId;
    public static final Property<Group> floorName;
    public static final Property<Group> groupAddress;
    public static final Property<Group> groupId;
    public static final Property<Group> groupIndex;
    public static final Property<Group> groupName;
    public static final Property<Group> groupState;
    public static final Property<Group> id;
    public static final Property<Group> imgindex;
    public static final Property<Group> leaderSup;
    public static final Property<Group> lightPlanData;
    public static final Property<Group> macdeviceid;
    public static final Property<Group> mainControlType;
    public static final Property<Group> mainModuleType;
    public static final Property<Group> maingroupid;
    public static final Property<Group> maxkelvin;
    public static final Property<Group> memorizePowerOff;
    public static final Property<Group> minkelvin;
    public static final Property<Group> moduleType;
    public static final Property<Group> panelSettingState;
    public static final Property<Group> param;
    public static final Property<Group> placeId;
    public static final Property<Group> presetKValues;
    public static final Property<Group> reportinstruct;
    public static final Property<Group> roomId;
    public static final Property<Group> roomName;
    public static final Property<Group> screenSetting;
    public static final Property<Group> setting;
    public static final Property<Group> subhide;
    public static final Property<Group> subkey;
    public static final Property<Group> userId;
    public static final Class<Group> __ENTITY_CLASS = Group.class;
    public static final CursorFactory<Group> __CURSOR_FACTORY = new GroupCursor.Factory();
    static final GroupIdGetter __ID_GETTER = new GroupIdGetter();

    @Override // io.objectbox.EntityInfo
    public int getEntityId() {
        return 4;
    }

    static {
        Group_ group_ = new Group_();
        __INSTANCE = group_;
        Property<Group> property = new Property<>(group_, 0, 1, Long.TYPE, "id", true, "id");
        id = property;
        Property<Group> property2 = new Property<>(group_, 1, 2, Long.TYPE, "groupId");
        groupId = property2;
        Property<Group> property3 = new Property<>(group_, 2, 3, Long.TYPE, MtcConf2Constants.MtcConfThirdUserIdKey);
        userId = property3;
        Property<Group> property4 = new Property<>(group_, 3, 4, Long.TYPE, "placeId");
        placeId = property4;
        Property<Group> property5 = new Property<>(group_, 4, 5, String.class, "groupName");
        groupName = property5;
        Property<Group> property6 = new Property<>(group_, 5, 6, String.class, "moduleType");
        moduleType = property6;
        Property<Group> property7 = new Property<>(group_, 6, 7, String.class, "controlType");
        controlType = property7;
        Property<Group> property8 = new Property<>(group_, 7, 8, Integer.TYPE, "groupAddress");
        groupAddress = property8;
        Property<Group> property9 = new Property<>(group_, 8, 11, Integer.TYPE, "groupIndex");
        groupIndex = property9;
        Property<Group> property10 = new Property<>(group_, 9, 23, String.class, RemoteMessageConst.MessageBody.PARAM);
        param = property10;
        Property<Group> property11 = new Property<>(group_, 10, 12, String.class, "extParam");
        extParam = property11;
        Property<Group> property12 = new Property<>(group_, 11, 13, Long.TYPE, "floorId");
        floorId = property12;
        Property<Group> property13 = new Property<>(group_, 12, 14, Long.TYPE, "roomId");
        roomId = property13;
        Property<Group> property14 = new Property<>(group_, 13, 15, String.class, "createtime");
        createtime = property14;
        Property<Group> property15 = new Property<>(group_, 14, 9, String.class, "deviceIds", false, "deviceIds", Group.ListConverter.class, List.class);
        deviceIds = property15;
        Property<Group> property16 = new Property<>(group_, 15, 10, String.class, "groupState", false, "groupState", DeviceState.DeviceStateConverter.class, DeviceState.class);
        groupState = property16;
        Property<Group> property17 = new Property<>(group_, 16, 16, String.class, "floorName");
        floorName = property17;
        Property<Group> property18 = new Property<>(group_, 17, 17, String.class, "roomName");
        roomName = property18;
        Property<Group> property19 = new Property<>(group_, 18, 18, Integer.TYPE, "memorizePowerOff");
        memorizePowerOff = property19;
        Property<Group> property20 = new Property<>(group_, 19, 19, String.class, "setting");
        setting = property20;
        Property<Group> property21 = new Property<>(group_, 20, 21, Integer.TYPE, "firstDevUniAddr");
        firstDevUniAddr = property21;
        Property<Group> property22 = new Property<>(group_, 21, 22, Integer.TYPE, "imgindex");
        imgindex = property22;
        Property<Group> property23 = new Property<>(group_, 22, 24, Integer.TYPE, "minkelvin");
        minkelvin = property23;
        Property<Group> property24 = new Property<>(group_, 23, 25, Integer.TYPE, "maxkelvin");
        maxkelvin = property24;
        Property<Group> property25 = new Property<>(group_, 24, 26, String.class, "lightPlanData");
        lightPlanData = property25;
        Property<Group> property26 = new Property<>(group_, 25, 27, Long.TYPE, "macdeviceid");
        macdeviceid = property26;
        Property<Group> property27 = new Property<>(group_, 26, 28, Integer.TYPE, "subkey");
        subkey = property27;
        Property<Group> property28 = new Property<>(group_, 27, 29, Long.TYPE, "maingroupid");
        maingroupid = property28;
        Property<Group> property29 = new Property<>(group_, 28, 30, String.class, "mainModuleType");
        mainModuleType = property29;
        Property<Group> property30 = new Property<>(group_, 29, 31, String.class, "mainControlType");
        mainControlType = property30;
        Property<Group> property31 = new Property<>(group_, 30, 32, Integer.TYPE, "subhide");
        subhide = property31;
        Property<Group> property32 = new Property<>(group_, 31, 33, String.class, "screenSetting");
        screenSetting = property32;
        Property<Group> property33 = new Property<>(group_, 32, 34, String.class, "panelSettingState");
        panelSettingState = property33;
        Property<Group> property34 = new Property<>(group_, 33, 35, Long.TYPE, "editTime");
        editTime = property34;
        Property<Group> property35 = new Property<>(group_, 34, 37, String.class, "colorPaletteUrl");
        colorPaletteUrl = property35;
        Property<Group> property36 = new Property<>(group_, 35, 39, Long.TYPE, "checkTime");
        checkTime = property36;
        Property<Group> property37 = new Property<>(group_, 36, 40, String.class, PushContentParamKey.REPORT_INSTRUCT);
        reportinstruct = property37;
        Property<Group> property38 = new Property<>(group_, 37, 41, Integer.TYPE, "leaderSup");
        leaderSup = property38;
        Property<Group> property39 = new Property<>(group_, 38, 36, String.class, "presetKValues", false, "presetKValues", KValue.Converter.class, Map.class);
        presetKValues = property39;
        __ALL_PROPERTIES = new Property[]{property, property2, property3, property4, property5, property6, property7, property8, property9, property10, property11, property12, property13, property14, property15, property16, property17, property18, property19, property20, property21, property22, property23, property24, property25, property26, property27, property28, property29, property30, property31, property32, property33, property34, property35, property36, property37, property38, property39};
        __ID_PROPERTY = property;
    }

    @Override // io.objectbox.EntityInfo
    public String getEntityName() {
        return "Group";
    }

    @Override // io.objectbox.EntityInfo
    public Class<Group> getEntityClass() {
        return __ENTITY_CLASS;
    }

    @Override // io.objectbox.EntityInfo
    public String getDbName() {
        return "Group";
    }

    @Override // io.objectbox.EntityInfo
    public Property<Group>[] getAllProperties() {
        return __ALL_PROPERTIES;
    }

    @Override // io.objectbox.EntityInfo
    public Property<Group> getIdProperty() {
        return __ID_PROPERTY;
    }

    @Override // io.objectbox.EntityInfo
    public IdGetter<Group> getIdGetter() {
        return __ID_GETTER;
    }

    @Override // io.objectbox.EntityInfo
    public CursorFactory<Group> getCursorFactory() {
        return __CURSOR_FACTORY;
    }

    static final class GroupIdGetter implements IdGetter<Group> {
        GroupIdGetter() {
        }

        @Override // io.objectbox.internal.IdGetter
        public long getId(Group object) {
            return object.getId();
        }
    }
}