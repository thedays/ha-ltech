package com.ltech.smarthome.model.bean;

import com.justalk.cloud.lemon.MtcConf2Constants;
import com.ltech.smarthome.model.bean.FloorCursor;
import io.objectbox.EntityInfo;
import io.objectbox.Property;
import io.objectbox.internal.CursorFactory;
import io.objectbox.internal.IdGetter;

/* loaded from: classes4.dex */
public final class Floor_ implements EntityInfo<Floor> {
    public static final Property<Floor>[] __ALL_PROPERTIES;
    public static final String __DB_NAME = "Floor";
    public static final int __ENTITY_ID = 3;
    public static final String __ENTITY_NAME = "Floor";
    public static final Property<Floor> __ID_PROPERTY;
    public static final Floor_ __INSTANCE;
    public static final Property<Floor> floorId;
    public static final Property<Floor> floorName;
    public static final Property<Floor> id;
    public static final Property<Floor> index;
    public static final Property<Floor> placeId;
    public static final Property<Floor> userId;
    public static final Class<Floor> __ENTITY_CLASS = Floor.class;
    public static final CursorFactory<Floor> __CURSOR_FACTORY = new FloorCursor.Factory();
    static final FloorIdGetter __ID_GETTER = new FloorIdGetter();

    @Override // io.objectbox.EntityInfo
    public int getEntityId() {
        return 3;
    }

    static {
        Floor_ floor_ = new Floor_();
        __INSTANCE = floor_;
        Property<Floor> property = new Property<>(floor_, 0, 1, Long.TYPE, "id", true, "id");
        id = property;
        Property<Floor> property2 = new Property<>(floor_, 1, 2, Long.TYPE, "floorId");
        floorId = property2;
        Property<Floor> property3 = new Property<>(floor_, 2, 3, Long.TYPE, MtcConf2Constants.MtcConfThirdUserIdKey);
        userId = property3;
        Property<Floor> property4 = new Property<>(floor_, 3, 4, String.class, "floorName");
        floorName = property4;
        Property<Floor> property5 = new Property<>(floor_, 4, 5, Long.TYPE, "placeId");
        placeId = property5;
        Property<Floor> property6 = new Property<>(floor_, 5, 6, Integer.TYPE, "index");
        index = property6;
        __ALL_PROPERTIES = new Property[]{property, property2, property3, property4, property5, property6};
        __ID_PROPERTY = property;
    }

    @Override // io.objectbox.EntityInfo
    public String getEntityName() {
        return "Floor";
    }

    @Override // io.objectbox.EntityInfo
    public Class<Floor> getEntityClass() {
        return __ENTITY_CLASS;
    }

    @Override // io.objectbox.EntityInfo
    public String getDbName() {
        return "Floor";
    }

    @Override // io.objectbox.EntityInfo
    public Property<Floor>[] getAllProperties() {
        return __ALL_PROPERTIES;
    }

    @Override // io.objectbox.EntityInfo
    public Property<Floor> getIdProperty() {
        return __ID_PROPERTY;
    }

    @Override // io.objectbox.EntityInfo
    public IdGetter<Floor> getIdGetter() {
        return __ID_GETTER;
    }

    @Override // io.objectbox.EntityInfo
    public CursorFactory<Floor> getCursorFactory() {
        return __CURSOR_FACTORY;
    }

    static final class FloorIdGetter implements IdGetter<Floor> {
        FloorIdGetter() {
        }

        @Override // io.objectbox.internal.IdGetter
        public long getId(Floor object) {
            return object.getId();
        }
    }
}