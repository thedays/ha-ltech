package com.ltech.smarthome.model.bean;

import com.justalk.cloud.lemon.MtcConfConstants;
import com.ltech.smarthome.model.bean.PlayListInfo;
import com.ltech.smarthome.model.bean.PlayListInfoCursor;
import io.objectbox.EntityInfo;
import io.objectbox.Property;
import io.objectbox.internal.CursorFactory;
import io.objectbox.internal.IdGetter;
import java.util.List;

/* loaded from: classes4.dex */
public final class PlayListInfo_ implements EntityInfo<PlayListInfo> {
    public static final Property<PlayListInfo>[] __ALL_PROPERTIES;
    public static final String __DB_NAME = "PlayListInfo";
    public static final int __ENTITY_ID = 11;
    public static final String __ENTITY_NAME = "PlayListInfo";
    public static final Property<PlayListInfo> __ID_PROPERTY;
    public static final PlayListInfo_ __INSTANCE;
    public static final Property<PlayListInfo> deviceId;
    public static final Property<PlayListInfo> id;
    public static final Property<PlayListInfo> list;
    public static final Property<PlayListInfo> name;
    public static final Property<PlayListInfo> num;
    public static final Property<PlayListInfo> songCount;
    public static final Property<PlayListInfo> totalCount;
    public static final Class<PlayListInfo> __ENTITY_CLASS = PlayListInfo.class;
    public static final CursorFactory<PlayListInfo> __CURSOR_FACTORY = new PlayListInfoCursor.Factory();
    static final PlayListInfoIdGetter __ID_GETTER = new PlayListInfoIdGetter();

    @Override // io.objectbox.EntityInfo
    public int getEntityId() {
        return 11;
    }

    static {
        PlayListInfo_ playListInfo_ = new PlayListInfo_();
        __INSTANCE = playListInfo_;
        Property<PlayListInfo> property = new Property<>(playListInfo_, 0, 1, Long.TYPE, "id", true, "id");
        id = property;
        Property<PlayListInfo> property2 = new Property<>(playListInfo_, 1, 2, Integer.TYPE, "num");
        num = property2;
        Property<PlayListInfo> property3 = new Property<>(playListInfo_, 2, 3, Integer.TYPE, "songCount");
        songCount = property3;
        Property<PlayListInfo> property4 = new Property<>(playListInfo_, 3, 4, String.class, "name");
        name = property4;
        Property<PlayListInfo> property5 = new Property<>(playListInfo_, 4, 5, Integer.TYPE, "totalCount");
        totalCount = property5;
        Property<PlayListInfo> property6 = new Property<>(playListInfo_, 5, 6, String.class, MtcConfConstants.MtcConfRecordListKey, false, MtcConfConstants.MtcConfRecordListKey, PlayListInfo.ListConverter.class, List.class);
        list = property6;
        Property<PlayListInfo> property7 = new Property<>(playListInfo_, 6, 7, Long.TYPE, "deviceId");
        deviceId = property7;
        __ALL_PROPERTIES = new Property[]{property, property2, property3, property4, property5, property6, property7};
        __ID_PROPERTY = property;
    }

    @Override // io.objectbox.EntityInfo
    public String getEntityName() {
        return "PlayListInfo";
    }

    @Override // io.objectbox.EntityInfo
    public Class<PlayListInfo> getEntityClass() {
        return __ENTITY_CLASS;
    }

    @Override // io.objectbox.EntityInfo
    public String getDbName() {
        return "PlayListInfo";
    }

    @Override // io.objectbox.EntityInfo
    public Property<PlayListInfo>[] getAllProperties() {
        return __ALL_PROPERTIES;
    }

    @Override // io.objectbox.EntityInfo
    public Property<PlayListInfo> getIdProperty() {
        return __ID_PROPERTY;
    }

    @Override // io.objectbox.EntityInfo
    public IdGetter<PlayListInfo> getIdGetter() {
        return __ID_GETTER;
    }

    @Override // io.objectbox.EntityInfo
    public CursorFactory<PlayListInfo> getCursorFactory() {
        return __CURSOR_FACTORY;
    }

    static final class PlayListInfoIdGetter implements IdGetter<PlayListInfo> {
        PlayListInfoIdGetter() {
        }

        @Override // io.objectbox.internal.IdGetter
        public long getId(PlayListInfo object) {
            return object.getId();
        }
    }
}