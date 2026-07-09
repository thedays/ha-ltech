package com.ltech.smarthome.model.bean;

import com.justalk.cloud.lemon.MtcConf2Constants;
import com.ltech.smarthome.model.bean.ModeContentCursor;
import io.objectbox.EntityInfo;
import io.objectbox.Property;
import io.objectbox.internal.CursorFactory;
import io.objectbox.internal.IdGetter;

/* loaded from: classes4.dex */
public final class ModeContent_ implements EntityInfo<ModeContent> {
    public static final Property<ModeContent> Content;
    public static final Property<ModeContent>[] __ALL_PROPERTIES;
    public static final String __DB_NAME = "ModeContent";
    public static final int __ENTITY_ID = 14;
    public static final String __ENTITY_NAME = "ModeContent";
    public static final Property<ModeContent> __ID_PROPERTY;
    public static final ModeContent_ __INSTANCE;
    public static final Property<ModeContent> controlType;
    public static final Property<ModeContent> deviceType;
    public static final Property<ModeContent> lightModeId;
    public static final Property<ModeContent> modeIndex;
    public static final Property<ModeContent> modeName;
    public static final Property<ModeContent> modeType;
    public static final Property<ModeContent> moduleType;
    public static final Property<ModeContent> picIndex;
    public static final Property<ModeContent> placeId;
    public static final Property<ModeContent> playTimes;
    public static final Property<ModeContent> userId;
    public static final Class<ModeContent> __ENTITY_CLASS = ModeContent.class;
    public static final CursorFactory<ModeContent> __CURSOR_FACTORY = new ModeContentCursor.Factory();
    static final ModeContentIdGetter __ID_GETTER = new ModeContentIdGetter();

    @Override // io.objectbox.EntityInfo
    public int getEntityId() {
        return 14;
    }

    static {
        ModeContent_ modeContent_ = new ModeContent_();
        __INSTANCE = modeContent_;
        Property<ModeContent> property = new Property<>(modeContent_, 0, 1, Long.TYPE, "lightModeId", true, "lightModeId");
        lightModeId = property;
        Property<ModeContent> property2 = new Property<>(modeContent_, 1, 2, Long.TYPE, "placeId");
        placeId = property2;
        Property<ModeContent> property3 = new Property<>(modeContent_, 2, 3, Long.TYPE, MtcConf2Constants.MtcConfThirdUserIdKey);
        userId = property3;
        Property<ModeContent> property4 = new Property<>(modeContent_, 3, 4, String.class, "modeName");
        modeName = property4;
        Property<ModeContent> property5 = new Property<>(modeContent_, 4, 5, String.class, "Content");
        Content = property5;
        Property<ModeContent> property6 = new Property<>(modeContent_, 5, 6, Integer.TYPE, "modeType");
        modeType = property6;
        Property<ModeContent> property7 = new Property<>(modeContent_, 6, 7, Integer.TYPE, "modeIndex");
        modeIndex = property7;
        Property<ModeContent> property8 = new Property<>(modeContent_, 7, 8, Integer.TYPE, "deviceType");
        deviceType = property8;
        Property<ModeContent> property9 = new Property<>(modeContent_, 8, 9, String.class, "moduleType");
        moduleType = property9;
        Property<ModeContent> property10 = new Property<>(modeContent_, 9, 10, String.class, "controlType");
        controlType = property10;
        Property<ModeContent> property11 = new Property<>(modeContent_, 10, 11, Integer.TYPE, "picIndex");
        picIndex = property11;
        Property<ModeContent> property12 = new Property<>(modeContent_, 11, 12, Integer.TYPE, "playTimes");
        playTimes = property12;
        __ALL_PROPERTIES = new Property[]{property, property2, property3, property4, property5, property6, property7, property8, property9, property10, property11, property12};
        __ID_PROPERTY = property;
    }

    @Override // io.objectbox.EntityInfo
    public String getEntityName() {
        return "ModeContent";
    }

    @Override // io.objectbox.EntityInfo
    public Class<ModeContent> getEntityClass() {
        return __ENTITY_CLASS;
    }

    @Override // io.objectbox.EntityInfo
    public String getDbName() {
        return "ModeContent";
    }

    @Override // io.objectbox.EntityInfo
    public Property<ModeContent>[] getAllProperties() {
        return __ALL_PROPERTIES;
    }

    @Override // io.objectbox.EntityInfo
    public Property<ModeContent> getIdProperty() {
        return __ID_PROPERTY;
    }

    @Override // io.objectbox.EntityInfo
    public IdGetter<ModeContent> getIdGetter() {
        return __ID_GETTER;
    }

    @Override // io.objectbox.EntityInfo
    public CursorFactory<ModeContent> getCursorFactory() {
        return __CURSOR_FACTORY;
    }

    static final class ModeContentIdGetter implements IdGetter<ModeContent> {
        ModeContentIdGetter() {
        }

        @Override // io.objectbox.internal.IdGetter
        public long getId(ModeContent object) {
            return object.getLightModeId();
        }
    }
}