package com.ltech.smarthome.model.bean;

import com.ltech.smarthome.model.bean.McuVersionCursor;
import io.objectbox.EntityInfo;
import io.objectbox.Property;
import io.objectbox.internal.CursorFactory;
import io.objectbox.internal.IdGetter;

/* loaded from: classes4.dex */
public final class McuVersion_ implements EntityInfo<McuVersion> {
    public static final Property<McuVersion>[] __ALL_PROPERTIES;
    public static final String __DB_NAME = "McuVersion";
    public static final int __ENTITY_ID = 13;
    public static final String __ENTITY_NAME = "McuVersion";
    public static final Property<McuVersion> __ID_PROPERTY;
    public static final McuVersion_ __INSTANCE;
    public static final Property<McuVersion> begincreatetime;
    public static final Property<McuVersion> createtime;
    public static final Property<McuVersion> deviceversionid;
    public static final Property<McuVersion> enable;
    public static final Property<McuVersion> endcreatetime;
    public static final Property<McuVersion> endhour;
    public static final Property<McuVersion> endtime;
    public static final Property<McuVersion> filedir;
    public static final Property<McuVersion> filename;
    public static final Property<McuVersion> firmwareData;
    public static final Property<McuVersion> firmwaretypecode;
    public static final Property<McuVersion> id;
    public static final Property<McuVersion> meshUpdate;
    public static final Property<McuVersion> pagenum;
    public static final Property<McuVersion> pagesize;
    public static final Property<McuVersion> productid;
    public static final Property<McuVersion> productname;
    public static final Property<McuVersion> remark;
    public static final Property<McuVersion> starthour;
    public static final Property<McuVersion> starttime;
    public static final Property<McuVersion> status;
    public static final Property<McuVersion> timetype;
    public static final Property<McuVersion> type;
    public static final Property<McuVersion> url;
    public static final Property<McuVersion> versionname;
    public static final Property<McuVersion> versionnumber;
    public static final Class<McuVersion> __ENTITY_CLASS = McuVersion.class;
    public static final CursorFactory<McuVersion> __CURSOR_FACTORY = new McuVersionCursor.Factory();
    static final McuVersionIdGetter __ID_GETTER = new McuVersionIdGetter();

    @Override // io.objectbox.EntityInfo
    public int getEntityId() {
        return 13;
    }

    static {
        McuVersion_ mcuVersion_ = new McuVersion_();
        __INSTANCE = mcuVersion_;
        Property<McuVersion> property = new Property<>(mcuVersion_, 0, 1, Long.TYPE, "id", true, "id");
        id = property;
        Property<McuVersion> property2 = new Property<>(mcuVersion_, 1, 2, Long.TYPE, "deviceversionid");
        deviceversionid = property2;
        Property<McuVersion> property3 = new Property<>(mcuVersion_, 2, 3, Long.TYPE, "productid");
        productid = property3;
        Property<McuVersion> property4 = new Property<>(mcuVersion_, 3, 4, String.class, "versionname");
        versionname = property4;
        Property<McuVersion> property5 = new Property<>(mcuVersion_, 4, 5, Long.TYPE, "versionnumber");
        versionnumber = property5;
        Property<McuVersion> property6 = new Property<>(mcuVersion_, 5, 6, String.class, "filedir");
        filedir = property6;
        Property<McuVersion> property7 = new Property<>(mcuVersion_, 6, 7, String.class, "filename");
        filename = property7;
        Property<McuVersion> property8 = new Property<>(mcuVersion_, 7, 8, String.class, "url");
        url = property8;
        Property<McuVersion> property9 = new Property<>(mcuVersion_, 8, 9, Integer.TYPE, "enable");
        enable = property9;
        Property<McuVersion> property10 = new Property<>(mcuVersion_, 9, 10, String.class, "createtime");
        createtime = property10;
        Property<McuVersion> property11 = new Property<>(mcuVersion_, 10, 11, String.class, "begincreatetime");
        begincreatetime = property11;
        Property<McuVersion> property12 = new Property<>(mcuVersion_, 11, 12, String.class, "endcreatetime");
        endcreatetime = property12;
        Property<McuVersion> property13 = new Property<>(mcuVersion_, 12, 13, Integer.TYPE, "type");
        type = property13;
        Property<McuVersion> property14 = new Property<>(mcuVersion_, 13, 14, Integer.TYPE, "pagesize");
        pagesize = property14;
        Property<McuVersion> property15 = new Property<>(mcuVersion_, 14, 15, Integer.TYPE, "pagenum");
        pagenum = property15;
        Property<McuVersion> property16 = new Property<>(mcuVersion_, 15, 16, String.class, "productname");
        productname = property16;
        Property<McuVersion> property17 = new Property<>(mcuVersion_, 16, 17, String.class, "remark");
        remark = property17;
        Property<McuVersion> property18 = new Property<>(mcuVersion_, 17, 18, String.class, "firmwaretypecode");
        firmwaretypecode = property18;
        Property<McuVersion> property19 = new Property<>(mcuVersion_, 18, 19, String.class, "starttime");
        starttime = property19;
        Property<McuVersion> property20 = new Property<>(mcuVersion_, 19, 20, String.class, "endtime");
        endtime = property20;
        Property<McuVersion> property21 = new Property<>(mcuVersion_, 20, 21, Integer.TYPE, "timetype");
        timetype = property21;
        Property<McuVersion> property22 = new Property<>(mcuVersion_, 21, 22, String.class, "starthour");
        starthour = property22;
        Property<McuVersion> property23 = new Property<>(mcuVersion_, 22, 23, String.class, "endhour");
        endhour = property23;
        Property<McuVersion> property24 = new Property<>(mcuVersion_, 23, 24, Integer.TYPE, "status");
        status = property24;
        Property<McuVersion> property25 = new Property<>(mcuVersion_, 24, 25, byte[].class, "firmwareData");
        firmwareData = property25;
        Property<McuVersion> property26 = new Property<>(mcuVersion_, 25, 26, Boolean.TYPE, "meshUpdate");
        meshUpdate = property26;
        __ALL_PROPERTIES = new Property[]{property, property2, property3, property4, property5, property6, property7, property8, property9, property10, property11, property12, property13, property14, property15, property16, property17, property18, property19, property20, property21, property22, property23, property24, property25, property26};
        __ID_PROPERTY = property;
    }

    @Override // io.objectbox.EntityInfo
    public String getEntityName() {
        return "McuVersion";
    }

    @Override // io.objectbox.EntityInfo
    public Class<McuVersion> getEntityClass() {
        return __ENTITY_CLASS;
    }

    @Override // io.objectbox.EntityInfo
    public String getDbName() {
        return "McuVersion";
    }

    @Override // io.objectbox.EntityInfo
    public Property<McuVersion>[] getAllProperties() {
        return __ALL_PROPERTIES;
    }

    @Override // io.objectbox.EntityInfo
    public Property<McuVersion> getIdProperty() {
        return __ID_PROPERTY;
    }

    @Override // io.objectbox.EntityInfo
    public IdGetter<McuVersion> getIdGetter() {
        return __ID_GETTER;
    }

    @Override // io.objectbox.EntityInfo
    public CursorFactory<McuVersion> getCursorFactory() {
        return __CURSOR_FACTORY;
    }

    static final class McuVersionIdGetter implements IdGetter<McuVersion> {
        McuVersionIdGetter() {
        }

        @Override // io.objectbox.internal.IdGetter
        public long getId(McuVersion object) {
            return object.getId();
        }
    }
}