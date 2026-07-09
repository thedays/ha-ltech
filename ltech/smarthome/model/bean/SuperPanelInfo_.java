package com.ltech.smarthome.model.bean;

import com.justalk.cloud.lemon.MtcConf2Constants;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.SuperPanelInfo;
import com.ltech.smarthome.model.bean.SuperPanelInfoCursor;
import io.objectbox.EntityInfo;
import io.objectbox.Property;
import io.objectbox.internal.CursorFactory;
import io.objectbox.internal.IdGetter;
import java.util.List;

/* loaded from: classes4.dex */
public final class SuperPanelInfo_ implements EntityInfo<SuperPanelInfo> {
    public static final Property<SuperPanelInfo>[] __ALL_PROPERTIES;
    public static final String __DB_NAME = "SuperPanelInfo";
    public static final int __ENTITY_ID = 9;
    public static final String __ENTITY_NAME = "SuperPanelInfo";
    public static final Property<SuperPanelInfo> __ID_PROPERTY;
    public static final SuperPanelInfo_ __INSTANCE;
    public static final Property<SuperPanelInfo> deviceId;
    public static final Property<SuperPanelInfo> deviceIds;
    public static final Property<SuperPanelInfo> devices;
    public static final Property<SuperPanelInfo> groupIds;
    public static final Property<SuperPanelInfo> groups;
    public static final Property<SuperPanelInfo> id;
    public static final Property<SuperPanelInfo> lastfirmwareversion;
    public static final Property<SuperPanelInfo> lastmcuversion;
    public static final Property<SuperPanelInfo> panelKeyInfo;
    public static final Property<SuperPanelInfo> sceneIds;
    public static final Property<SuperPanelInfo> scenes;
    public static final Property<SuperPanelInfo> sortRoleList;
    public static final Property<SuperPanelInfo> sortSceneList;
    public static final Property<SuperPanelInfo> userId;
    public static final Class<SuperPanelInfo> __ENTITY_CLASS = SuperPanelInfo.class;
    public static final CursorFactory<SuperPanelInfo> __CURSOR_FACTORY = new SuperPanelInfoCursor.Factory();
    static final SuperPanelInfoIdGetter __ID_GETTER = new SuperPanelInfoIdGetter();

    @Override // io.objectbox.EntityInfo
    public int getEntityId() {
        return 9;
    }

    static {
        SuperPanelInfo_ superPanelInfo_ = new SuperPanelInfo_();
        __INSTANCE = superPanelInfo_;
        Property<SuperPanelInfo> property = new Property<>(superPanelInfo_, 0, 1, Long.TYPE, "id", true, "id");
        id = property;
        Property<SuperPanelInfo> property2 = new Property<>(superPanelInfo_, 1, 2, Long.TYPE, "deviceId");
        deviceId = property2;
        Property<SuperPanelInfo> property3 = new Property<>(superPanelInfo_, 2, 3, Long.TYPE, MtcConf2Constants.MtcConfThirdUserIdKey);
        userId = property3;
        Property<SuperPanelInfo> property4 = new Property<>(superPanelInfo_, 3, 4, String.class, "deviceIds", false, "deviceIds", Group.ListConverter.class, List.class);
        deviceIds = property4;
        Property<SuperPanelInfo> property5 = new Property<>(superPanelInfo_, 4, 5, String.class, "groupIds", false, "groupIds", Group.ListConverter.class, List.class);
        groupIds = property5;
        Property<SuperPanelInfo> property6 = new Property<>(superPanelInfo_, 5, 6, String.class, "sceneIds", false, "sceneIds", Group.ListConverter.class, List.class);
        sceneIds = property6;
        Property<SuperPanelInfo> property7 = new Property<>(superPanelInfo_, 6, 7, String.class, "panelKeyInfo", false, "panelKeyInfo", SuperPanelInfo.PanelKeyInfo.PanelKeyInfoListConverter.class, List.class);
        panelKeyInfo = property7;
        Property<SuperPanelInfo> property8 = new Property<>(superPanelInfo_, 7, 8, String.class, "sortRoleList", false, "sortRoleList", SuperPanelInfo.SortInfoConverter.class, List.class);
        sortRoleList = property8;
        Property<SuperPanelInfo> property9 = new Property<>(superPanelInfo_, 8, 9, String.class, "sortSceneList", false, "sortSceneList", SuperPanelInfo.SortInfoConverter.class, List.class);
        sortSceneList = property9;
        Property<SuperPanelInfo> property10 = new Property<>(superPanelInfo_, 9, 10, String.class, "devices", false, "devices", SuperPanelInfo.DeviceInfoConverter.class, List.class);
        devices = property10;
        Property<SuperPanelInfo> property11 = new Property<>(superPanelInfo_, 10, 11, String.class, "scenes", false, "scenes", SuperPanelInfo.ScenesInfoConverter.class, List.class);
        scenes = property11;
        Property<SuperPanelInfo> property12 = new Property<>(superPanelInfo_, 11, 12, String.class, "groups", false, "groups", SuperPanelInfo.GroupInfoConverter.class, List.class);
        groups = property12;
        Property<SuperPanelInfo> property13 = new Property<>(superPanelInfo_, 12, 13, String.class, "lastfirmwareversion");
        lastfirmwareversion = property13;
        Property<SuperPanelInfo> property14 = new Property<>(superPanelInfo_, 13, 14, String.class, "lastmcuversion");
        lastmcuversion = property14;
        __ALL_PROPERTIES = new Property[]{property, property2, property3, property4, property5, property6, property7, property8, property9, property10, property11, property12, property13, property14};
        __ID_PROPERTY = property;
    }

    @Override // io.objectbox.EntityInfo
    public String getEntityName() {
        return "SuperPanelInfo";
    }

    @Override // io.objectbox.EntityInfo
    public Class<SuperPanelInfo> getEntityClass() {
        return __ENTITY_CLASS;
    }

    @Override // io.objectbox.EntityInfo
    public String getDbName() {
        return "SuperPanelInfo";
    }

    @Override // io.objectbox.EntityInfo
    public Property<SuperPanelInfo>[] getAllProperties() {
        return __ALL_PROPERTIES;
    }

    @Override // io.objectbox.EntityInfo
    public Property<SuperPanelInfo> getIdProperty() {
        return __ID_PROPERTY;
    }

    @Override // io.objectbox.EntityInfo
    public IdGetter<SuperPanelInfo> getIdGetter() {
        return __ID_GETTER;
    }

    @Override // io.objectbox.EntityInfo
    public CursorFactory<SuperPanelInfo> getCursorFactory() {
        return __CURSOR_FACTORY;
    }

    static final class SuperPanelInfoIdGetter implements IdGetter<SuperPanelInfo> {
        SuperPanelInfoIdGetter() {
        }

        @Override // io.objectbox.internal.IdGetter
        public long getId(SuperPanelInfo object) {
            return object.getId();
        }
    }
}