package com.ltech.smarthome.model.bean;

import com.justalk.cloud.lemon.MtcConf2Constants;
import com.ltech.smarthome.model.bean.PlaceCursor;
import com.ltech.smarthome.utils.Constants;
import io.objectbox.EntityInfo;
import io.objectbox.Property;
import io.objectbox.internal.CursorFactory;
import io.objectbox.internal.IdGetter;

/* loaded from: classes4.dex */
public final class Place_ implements EntityInfo<Place> {
    public static final Property<Place>[] __ALL_PROPERTIES;
    public static final String __DB_NAME = "Place";
    public static final int __ENTITY_ID = 5;
    public static final String __ENTITY_NAME = "Place";
    public static final Property<Place> __ID_PROPERTY;
    public static final Place_ __INSTANCE;
    public static final Property<Place> appKey;
    public static final Property<Place> appToken;
    public static final Property<Place> createdAt;
    public static final Property<Place> currentUserId;
    public static final Property<Place> devicetotal;
    public static final Property<Place> expiresIn;
    public static final Property<Place> floortotal;
    public static final Property<Place> id;
    public static final Property<Place> imageUrl;
    public static final Property<Place> ivindex;
    public static final Property<Place> latitude;
    public static final Property<Place> location;
    public static final Property<Place> longitude;
    public static final Property<Place> meshUUID;
    public static final Property<Place> netKey;
    public static final Property<Place> placeId;
    public static final Property<Place> placeName;
    public static final Property<Place> provisionerAddress;
    public static final Property<Place> qrCode;
    public static final Property<Place> roleType;
    public static final Property<Place> roomtotal;
    public static final Property<Place> userId;
    public static final Class<Place> __ENTITY_CLASS = Place.class;
    public static final CursorFactory<Place> __CURSOR_FACTORY = new PlaceCursor.Factory();
    static final PlaceIdGetter __ID_GETTER = new PlaceIdGetter();

    @Override // io.objectbox.EntityInfo
    public int getEntityId() {
        return 5;
    }

    static {
        Place_ place_ = new Place_();
        __INSTANCE = place_;
        Property<Place> property = new Property<>(place_, 0, 1, Long.TYPE, "id", true, "id");
        id = property;
        Property<Place> property2 = new Property<>(place_, 1, 2, Long.TYPE, "placeId");
        placeId = property2;
        Property<Place> property3 = new Property<>(place_, 2, 3, Long.TYPE, MtcConf2Constants.MtcConfThirdUserIdKey);
        userId = property3;
        Property<Place> property4 = new Property<>(place_, 3, 18, Long.TYPE, "currentUserId");
        currentUserId = property4;
        Property<Place> property5 = new Property<>(place_, 4, 4, String.class, "placeName");
        placeName = property5;
        Property<Place> property6 = new Property<>(place_, 5, 5, Integer.TYPE, "roleType");
        roleType = property6;
        Property<Place> property7 = new Property<>(place_, 6, 6, Double.TYPE, Constants.LATITUDE);
        latitude = property7;
        Property<Place> property8 = new Property<>(place_, 7, 7, Double.TYPE, Constants.LONGITUDE);
        longitude = property8;
        Property<Place> property9 = new Property<>(place_, 8, 8, String.class, "qrCode");
        qrCode = property9;
        Property<Place> property10 = new Property<>(place_, 9, 9, String.class, "location");
        location = property10;
        Property<Place> property11 = new Property<>(place_, 10, 10, String.class, "imageUrl");
        imageUrl = property11;
        Property<Place> property12 = new Property<>(place_, 11, 11, String.class, "netKey");
        netKey = property12;
        Property<Place> property13 = new Property<>(place_, 12, 12, String.class, "appKey");
        appKey = property13;
        Property<Place> property14 = new Property<>(place_, 13, 13, String.class, "meshUUID");
        meshUUID = property14;
        Property<Place> property15 = new Property<>(place_, 14, 20, Integer.TYPE, "floortotal");
        floortotal = property15;
        Property<Place> property16 = new Property<>(place_, 15, 21, Integer.TYPE, "roomtotal");
        roomtotal = property16;
        Property<Place> property17 = new Property<>(place_, 16, 22, Integer.TYPE, "devicetotal");
        devicetotal = property17;
        Property<Place> property18 = new Property<>(place_, 17, 17, String.class, "provisionerAddress");
        provisionerAddress = property18;
        Property<Place> property19 = new Property<>(place_, 18, 14, Integer.TYPE, "expiresIn");
        expiresIn = property19;
        Property<Place> property20 = new Property<>(place_, 19, 15, Integer.TYPE, "createdAt");
        createdAt = property20;
        Property<Place> property21 = new Property<>(place_, 20, 16, String.class, "appToken");
        appToken = property21;
        Property<Place> property22 = new Property<>(place_, 21, 19, Integer.TYPE, "ivindex");
        ivindex = property22;
        __ALL_PROPERTIES = new Property[]{property, property2, property3, property4, property5, property6, property7, property8, property9, property10, property11, property12, property13, property14, property15, property16, property17, property18, property19, property20, property21, property22};
        __ID_PROPERTY = property;
    }

    @Override // io.objectbox.EntityInfo
    public String getEntityName() {
        return "Place";
    }

    @Override // io.objectbox.EntityInfo
    public Class<Place> getEntityClass() {
        return __ENTITY_CLASS;
    }

    @Override // io.objectbox.EntityInfo
    public String getDbName() {
        return "Place";
    }

    @Override // io.objectbox.EntityInfo
    public Property<Place>[] getAllProperties() {
        return __ALL_PROPERTIES;
    }

    @Override // io.objectbox.EntityInfo
    public Property<Place> getIdProperty() {
        return __ID_PROPERTY;
    }

    @Override // io.objectbox.EntityInfo
    public IdGetter<Place> getIdGetter() {
        return __ID_GETTER;
    }

    @Override // io.objectbox.EntityInfo
    public CursorFactory<Place> getCursorFactory() {
        return __CURSOR_FACTORY;
    }

    static final class PlaceIdGetter implements IdGetter<Place> {
        PlaceIdGetter() {
        }

        @Override // io.objectbox.internal.IdGetter
        public long getId(Place object) {
            return object.getId();
        }
    }
}