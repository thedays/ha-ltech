package com.ltech.smarthome.model.bean;

import com.ltech.smarthome.model.bean.SongInfoCursor;
import io.objectbox.EntityInfo;
import io.objectbox.Property;
import io.objectbox.internal.CursorFactory;
import io.objectbox.internal.IdGetter;

/* loaded from: classes4.dex */
public final class SongInfo_ implements EntityInfo<SongInfo> {
    public static final Property<SongInfo>[] __ALL_PROPERTIES;
    public static final String __DB_NAME = "SongInfo";
    public static final int __ENTITY_ID = 12;
    public static final String __ENTITY_NAME = "SongInfo";
    public static final Property<SongInfo> __ID_PROPERTY;
    public static final SongInfo_ __INSTANCE;
    public static final Property<SongInfo> author;
    public static final Property<SongInfo> deviceId;
    public static final Property<SongInfo> id;
    public static final Property<SongInfo> name;
    public static final Property<SongInfo> num;
    public static final Property<SongInfo> state;
    public static final Property<SongInfo> totalCount;
    public static final Class<SongInfo> __ENTITY_CLASS = SongInfo.class;
    public static final CursorFactory<SongInfo> __CURSOR_FACTORY = new SongInfoCursor.Factory();
    static final SongInfoIdGetter __ID_GETTER = new SongInfoIdGetter();

    @Override // io.objectbox.EntityInfo
    public int getEntityId() {
        return 12;
    }

    static {
        SongInfo_ songInfo_ = new SongInfo_();
        __INSTANCE = songInfo_;
        Property<SongInfo> property = new Property<>(songInfo_, 0, 1, Long.TYPE, "id", true, "id");
        id = property;
        Property<SongInfo> property2 = new Property<>(songInfo_, 1, 2, String.class, "name");
        name = property2;
        Property<SongInfo> property3 = new Property<>(songInfo_, 2, 3, String.class, "author");
        author = property3;
        Property<SongInfo> property4 = new Property<>(songInfo_, 3, 4, Integer.TYPE, "num");
        num = property4;
        Property<SongInfo> property5 = new Property<>(songInfo_, 4, 5, Integer.TYPE, "totalCount");
        totalCount = property5;
        Property<SongInfo> property6 = new Property<>(songInfo_, 5, 6, Long.TYPE, "deviceId");
        deviceId = property6;
        Property<SongInfo> property7 = new Property<>(songInfo_, 6, 7, Integer.TYPE, "state");
        state = property7;
        __ALL_PROPERTIES = new Property[]{property, property2, property3, property4, property5, property6, property7};
        __ID_PROPERTY = property;
    }

    @Override // io.objectbox.EntityInfo
    public String getEntityName() {
        return "SongInfo";
    }

    @Override // io.objectbox.EntityInfo
    public Class<SongInfo> getEntityClass() {
        return __ENTITY_CLASS;
    }

    @Override // io.objectbox.EntityInfo
    public String getDbName() {
        return "SongInfo";
    }

    @Override // io.objectbox.EntityInfo
    public Property<SongInfo>[] getAllProperties() {
        return __ALL_PROPERTIES;
    }

    @Override // io.objectbox.EntityInfo
    public Property<SongInfo> getIdProperty() {
        return __ID_PROPERTY;
    }

    @Override // io.objectbox.EntityInfo
    public IdGetter<SongInfo> getIdGetter() {
        return __ID_GETTER;
    }

    @Override // io.objectbox.EntityInfo
    public CursorFactory<SongInfo> getCursorFactory() {
        return __CURSOR_FACTORY;
    }

    static final class SongInfoIdGetter implements IdGetter<SongInfo> {
        SongInfoIdGetter() {
        }

        @Override // io.objectbox.internal.IdGetter
        public long getId(SongInfo object) {
            return object.getId();
        }
    }
}