package com.ltech.smarthome.model.bean;

import com.justalk.cloud.lemon.MtcConf2Constants;
import com.ltech.smarthome.model.bean.PlaceUserCursor;
import io.objectbox.EntityInfo;
import io.objectbox.Property;
import io.objectbox.internal.CursorFactory;
import io.objectbox.internal.IdGetter;

/* loaded from: classes4.dex */
public final class PlaceUser_ implements EntityInfo<PlaceUser> {
    public static final Property<PlaceUser>[] __ALL_PROPERTIES;
    public static final String __DB_NAME = "PlaceUser";
    public static final int __ENTITY_ID = 6;
    public static final String __ENTITY_NAME = "PlaceUser";
    public static final Property<PlaceUser> __ID_PROPERTY;
    public static final PlaceUser_ __INSTANCE;
    public static final Property<PlaceUser> currentUserId;
    public static final Property<PlaceUser> email;
    public static final Property<PlaceUser> headUrl;
    public static final Property<PlaceUser> mobilephone;
    public static final Property<PlaceUser> placeId;
    public static final Property<PlaceUser> placeUserId;
    public static final Property<PlaceUser> remark;
    public static final Property<PlaceUser> roleType;
    public static final Property<PlaceUser> userId;
    public static final Property<PlaceUser> userName;
    public static final Class<PlaceUser> __ENTITY_CLASS = PlaceUser.class;
    public static final CursorFactory<PlaceUser> __CURSOR_FACTORY = new PlaceUserCursor.Factory();
    static final PlaceUserIdGetter __ID_GETTER = new PlaceUserIdGetter();

    @Override // io.objectbox.EntityInfo
    public int getEntityId() {
        return 6;
    }

    static {
        PlaceUser_ placeUser_ = new PlaceUser_();
        __INSTANCE = placeUser_;
        Property<PlaceUser> property = new Property<>(placeUser_, 0, 1, Long.TYPE, "placeUserId", true, "placeUserId");
        placeUserId = property;
        Property<PlaceUser> property2 = new Property<>(placeUser_, 1, 2, Long.TYPE, "placeId");
        placeId = property2;
        Property<PlaceUser> property3 = new Property<>(placeUser_, 2, 3, Integer.TYPE, "roleType");
        roleType = property3;
        Property<PlaceUser> property4 = new Property<>(placeUser_, 3, 4, String.class, "userName");
        userName = property4;
        Property<PlaceUser> property5 = new Property<>(placeUser_, 4, 5, Long.TYPE, MtcConf2Constants.MtcConfThirdUserIdKey);
        userId = property5;
        Property<PlaceUser> property6 = new Property<>(placeUser_, 5, 6, String.class, "headUrl");
        headUrl = property6;
        Property<PlaceUser> property7 = new Property<>(placeUser_, 6, 7, Long.TYPE, "currentUserId");
        currentUserId = property7;
        Property<PlaceUser> property8 = new Property<>(placeUser_, 7, 8, String.class, "remark");
        remark = property8;
        Property<PlaceUser> property9 = new Property<>(placeUser_, 8, 9, String.class, "mobilephone");
        mobilephone = property9;
        Property<PlaceUser> property10 = new Property<>(placeUser_, 9, 10, String.class, "email");
        email = property10;
        __ALL_PROPERTIES = new Property[]{property, property2, property3, property4, property5, property6, property7, property8, property9, property10};
        __ID_PROPERTY = property;
    }

    @Override // io.objectbox.EntityInfo
    public String getEntityName() {
        return "PlaceUser";
    }

    @Override // io.objectbox.EntityInfo
    public Class<PlaceUser> getEntityClass() {
        return __ENTITY_CLASS;
    }

    @Override // io.objectbox.EntityInfo
    public String getDbName() {
        return "PlaceUser";
    }

    @Override // io.objectbox.EntityInfo
    public Property<PlaceUser>[] getAllProperties() {
        return __ALL_PROPERTIES;
    }

    @Override // io.objectbox.EntityInfo
    public Property<PlaceUser> getIdProperty() {
        return __ID_PROPERTY;
    }

    @Override // io.objectbox.EntityInfo
    public IdGetter<PlaceUser> getIdGetter() {
        return __ID_GETTER;
    }

    @Override // io.objectbox.EntityInfo
    public CursorFactory<PlaceUser> getCursorFactory() {
        return __CURSOR_FACTORY;
    }

    static final class PlaceUserIdGetter implements IdGetter<PlaceUser> {
        PlaceUserIdGetter() {
        }

        @Override // io.objectbox.internal.IdGetter
        public long getId(PlaceUser object) {
            return object.getPlaceUserId();
        }
    }
}