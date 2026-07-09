package com.ltech.smarthome.model.bean;

import com.justalk.cloud.lemon.MtcConf2Constants;
import com.ltech.smarthome.model.bean.Automation;
import com.ltech.smarthome.model.bean.AutomationCursor;
import com.ltech.smarthome.utils.Constants;
import io.objectbox.EntityInfo;
import io.objectbox.Property;
import io.objectbox.internal.CursorFactory;
import io.objectbox.internal.IdGetter;
import java.util.List;

/* loaded from: classes4.dex */
public final class Automation_ implements EntityInfo<Automation> {
    public static final Property<Automation>[] __ALL_PROPERTIES;
    public static final String __DB_NAME = "Automation";
    public static final int __ENTITY_ID = 1;
    public static final String __ENTITY_NAME = "Automation";
    public static final Property<Automation> __ID_PROPERTY;
    public static final Automation_ __INSTANCE;
    public static final Property<Automation> actions;
    public static final Property<Automation> automationId;
    public static final Property<Automation> automationType;
    public static final Property<Automation> conditionType;
    public static final Property<Automation> conditions;
    public static final Property<Automation> currentUserId;
    public static final Property<Automation> cycleindex;
    public static final Property<Automation> enable;
    public static final Property<Automation> endTime;
    public static final Property<Automation> gatewayDeviceId;
    public static final Property<Automation> id;
    public static final Property<Automation> index;
    public static final Property<Automation> intervaltime;
    public static final Property<Automation> name;
    public static final Property<Automation> picIndex;
    public static final Property<Automation> placeId;
    public static final Property<Automation> startTime;
    public static final Property<Automation> timeZone;
    public static final Property<Automation> userId;
    public static final Property<Automation> weeks;
    public static final Class<Automation> __ENTITY_CLASS = Automation.class;
    public static final CursorFactory<Automation> __CURSOR_FACTORY = new AutomationCursor.Factory();
    static final AutomationIdGetter __ID_GETTER = new AutomationIdGetter();

    @Override // io.objectbox.EntityInfo
    public int getEntityId() {
        return 1;
    }

    static {
        Automation_ automation_ = new Automation_();
        __INSTANCE = automation_;
        Property<Automation> property = new Property<>(automation_, 0, 1, Long.TYPE, "id", true, "id");
        id = property;
        Property<Automation> property2 = new Property<>(automation_, 1, 2, Long.TYPE, "automationId");
        automationId = property2;
        Property<Automation> property3 = new Property<>(automation_, 2, 16, Long.TYPE, "currentUserId");
        currentUserId = property3;
        Property<Automation> property4 = new Property<>(automation_, 3, 3, Long.TYPE, MtcConf2Constants.MtcConfThirdUserIdKey);
        userId = property4;
        Property<Automation> property5 = new Property<>(automation_, 4, 4, Long.TYPE, "placeId");
        placeId = property5;
        Property<Automation> property6 = new Property<>(automation_, 5, 5, String.class, "name");
        name = property6;
        Property<Automation> property7 = new Property<>(automation_, 6, 6, String.class, "startTime");
        startTime = property7;
        Property<Automation> property8 = new Property<>(automation_, 7, 7, String.class, "endTime");
        endTime = property8;
        Property<Automation> property9 = new Property<>(automation_, 8, 8, String.class, Constants.WEEKS);
        weeks = property9;
        Property<Automation> property10 = new Property<>(automation_, 9, 9, String.class, "timeZone");
        timeZone = property10;
        Property<Automation> property11 = new Property<>(automation_, 10, 10, Integer.TYPE, "picIndex");
        picIndex = property11;
        Property<Automation> property12 = new Property<>(automation_, 11, 11, Boolean.TYPE, "enable");
        enable = property12;
        Property<Automation> property13 = new Property<>(automation_, 12, 12, Integer.TYPE, "conditionType");
        conditionType = property13;
        Property<Automation> property14 = new Property<>(automation_, 13, 13, Integer.TYPE, "index");
        index = property14;
        Property<Automation> property15 = new Property<>(automation_, 14, 17, Integer.TYPE, "automationType");
        automationType = property15;
        Property<Automation> property16 = new Property<>(automation_, 15, 18, Long.TYPE, "gatewayDeviceId");
        gatewayDeviceId = property16;
        Property<Automation> property17 = new Property<>(automation_, 16, 19, Integer.TYPE, "cycleindex");
        cycleindex = property17;
        Property<Automation> property18 = new Property<>(automation_, 17, 20, Integer.TYPE, "intervaltime");
        intervaltime = property18;
        Property<Automation> property19 = new Property<>(automation_, 18, 14, String.class, "conditions", false, "conditions", Automation.Condition.ConditionConverter.class, List.class);
        conditions = property19;
        Property<Automation> property20 = new Property<>(automation_, 19, 15, String.class, "actions", false, "actions", Automation.Action.ActionConverter.class, List.class);
        actions = property20;
        __ALL_PROPERTIES = new Property[]{property, property2, property3, property4, property5, property6, property7, property8, property9, property10, property11, property12, property13, property14, property15, property16, property17, property18, property19, property20};
        __ID_PROPERTY = property;
    }

    @Override // io.objectbox.EntityInfo
    public String getEntityName() {
        return "Automation";
    }

    @Override // io.objectbox.EntityInfo
    public Class<Automation> getEntityClass() {
        return __ENTITY_CLASS;
    }

    @Override // io.objectbox.EntityInfo
    public String getDbName() {
        return "Automation";
    }

    @Override // io.objectbox.EntityInfo
    public Property<Automation>[] getAllProperties() {
        return __ALL_PROPERTIES;
    }

    @Override // io.objectbox.EntityInfo
    public Property<Automation> getIdProperty() {
        return __ID_PROPERTY;
    }

    @Override // io.objectbox.EntityInfo
    public IdGetter<Automation> getIdGetter() {
        return __ID_GETTER;
    }

    @Override // io.objectbox.EntityInfo
    public CursorFactory<Automation> getCursorFactory() {
        return __CURSOR_FACTORY;
    }

    static final class AutomationIdGetter implements IdGetter<Automation> {
        AutomationIdGetter() {
        }

        @Override // io.objectbox.internal.IdGetter
        public long getId(Automation object) {
            return object.getId();
        }
    }
}