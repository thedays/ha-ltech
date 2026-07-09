package com.ltech.smarthome.model.bean;

import com.justalk.cloud.lemon.MtcConf2Constants;
import com.ltech.smarthome.model.bean.RoomCursor;
import io.objectbox.EntityInfo;
import io.objectbox.Property;
import io.objectbox.internal.CursorFactory;
import io.objectbox.internal.IdGetter;

/* loaded from: classes4.dex */
public final class Room_ implements EntityInfo<Room> {
    public static final Property<Room>[] __ALL_PROPERTIES;
    public static final String __DB_NAME = "Room";
    public static final int __ENTITY_ID = 7;
    public static final String __ENTITY_NAME = "Room";
    public static final Property<Room> __ID_PROPERTY;
    public static final Room_ __INSTANCE;
    public static final Property<Room> floorId;
    public static final Property<Room> id;
    public static final Property<Room> index;
    public static final Property<Room> placeId;
    public static final Property<Room> roomId;
    public static final Property<Room> roomName;
    public static final Property<Room> userId;
    public static final Class<Room> __ENTITY_CLASS = Room.class;
    public static final CursorFactory<Room> __CURSOR_FACTORY = new RoomCursor.Factory();
    static final RoomIdGetter __ID_GETTER = new RoomIdGetter();

    @Override // io.objectbox.EntityInfo
    public int getEntityId() {
        return 7;
    }

    static {
        Room_ room_ = new Room_();
        __INSTANCE = room_;
        Property<Room> property = new Property<>(room_, 0, 1, Long.TYPE, "id", true, "id");
        id = property;
        Property<Room> property2 = new Property<>(room_, 1, 2, Long.TYPE, "roomId");
        roomId = property2;
        Property<Room> property3 = new Property<>(room_, 2, 3, Long.TYPE, MtcConf2Constants.MtcConfThirdUserIdKey);
        userId = property3;
        Property<Room> property4 = new Property<>(room_, 3, 4, String.class, "roomName");
        roomName = property4;
        Property<Room> property5 = new Property<>(room_, 4, 5, Long.TYPE, "floorId");
        floorId = property5;
        Property<Room> property6 = new Property<>(room_, 5, 6, Long.TYPE, "placeId");
        placeId = property6;
        Property<Room> property7 = new Property<>(room_, 6, 7, Integer.TYPE, "index");
        index = property7;
        __ALL_PROPERTIES = new Property[]{property, property2, property3, property4, property5, property6, property7};
        __ID_PROPERTY = property;
    }

    @Override // io.objectbox.EntityInfo
    public String getEntityName() {
        return "Room";
    }

    @Override // io.objectbox.EntityInfo
    public Class<Room> getEntityClass() {
        return __ENTITY_CLASS;
    }

    @Override // io.objectbox.EntityInfo
    public String getDbName() {
        return "Room";
    }

    @Override // io.objectbox.EntityInfo
    public Property<Room>[] getAllProperties() {
        return __ALL_PROPERTIES;
    }

    @Override // io.objectbox.EntityInfo
    public Property<Room> getIdProperty() {
        return __ID_PROPERTY;
    }

    @Override // io.objectbox.EntityInfo
    public IdGetter<Room> getIdGetter() {
        return __ID_GETTER;
    }

    @Override // io.objectbox.EntityInfo
    public CursorFactory<Room> getCursorFactory() {
        return __CURSOR_FACTORY;
    }

    static final class RoomIdGetter implements IdGetter<Room> {
        RoomIdGetter() {
        }

        @Override // io.objectbox.internal.IdGetter
        public long getId(Room object) {
            return object.getId();
        }
    }
}