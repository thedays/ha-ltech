package com.ltech.smarthome.model.bean;

import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.reflect.TypeToken;
import io.objectbox.converter.PropertyConverter;
import java.util.List;

/* loaded from: classes4.dex */
public class Automation {
    public static final int ALL_CONDITION_TYPE = 2;
    public static final int CLOUD = 1;
    public static final int DISABLE = 2;
    public static final int ENABLE = 1;
    public static final int LOCAL = 2;
    public static final int ONE_CONDITION_TYPE = 1;
    private List<Action> actions;
    private long automationId;
    private int automationType;
    private int conditionType;
    private List<Condition> conditions;
    private long currentUserId;
    private int cycleindex;
    private boolean enable;
    private String endTime;
    private long gatewayDeviceId;
    private long id;
    private int index;
    private int intervaltime;
    private String name;
    private int picIndex;
    private long placeId;
    private String startTime;
    private String timeZone;
    private long userId;
    private String weeks;

    public static int getEnableValue(boolean enable) {
        return enable ? 1 : 2;
    }

    public int getCycleindex() {
        int i = this.cycleindex;
        if (i == 0) {
            return 1;
        }
        return i;
    }

    public void setCycleindex(int cycleindex) {
        this.cycleindex = cycleindex;
    }

    public int getIntervaltime() {
        int i = this.intervaltime;
        if (i == 0) {
            return 500;
        }
        return i;
    }

    public void setIntervaltime(int intervaltime) {
        this.intervaltime = intervaltime;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAutomationId() {
        return this.automationId;
    }

    public void setAutomationId(long automationId) {
        this.automationId = automationId;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPlaceId() {
        return this.placeId;
    }

    public void setPlaceId(long placeId) {
        this.placeId = placeId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getWeeks() {
        return this.weeks;
    }

    public void setWeeks(String weeks) {
        this.weeks = weeks;
    }

    public String getTimeZone() {
        return this.timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public int getPicIndex() {
        return this.picIndex;
    }

    public void setPicIndex(int picIndex) {
        this.picIndex = picIndex;
    }

    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setEnable(int enable) {
        this.enable = enable == 1;
    }

    public List<Condition> getConditions() {
        return this.conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public List<Action> getActions() {
        return this.actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public int getConditionType() {
        return this.conditionType;
    }

    public void setConditionType(int conditionType) {
        this.conditionType = conditionType;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getAutomationType() {
        return this.automationType;
    }

    public void setAutomationType(int automationType) {
        this.automationType = automationType;
    }

    public long getGatewayDeviceId() {
        return this.gatewayDeviceId;
    }

    public void setGatewayDeviceId(long gatewayDeviceId) {
        this.gatewayDeviceId = gatewayDeviceId;
    }

    public boolean equals(Object var1) {
        if (var1 instanceof Automation) {
            Automation automation = (Automation) var1;
            return automation.getAutomationId() == getAutomationId() && automation.getPlaceId() == getPlaceId() && automation.getUserId() == getUserId();
        }
        return super.equals(var1);
    }

    public int hashCode() {
        return super.hashCode();
    }

    public long getCurrentUserId() {
        return this.currentUserId;
    }

    public void setCurrentUserId(long currentUserId) {
        this.currentUserId = currentUserId;
    }

    public static class Condition {
        public static final int CONDITION_ENVIRONMENTAL_STATUS = 9;
        public static final int CONDITION_TYPE_DEVICE = 8;
        public static final int CONDITION_TYPE_HUMIDITY = 5;
        public static final int CONDITION_TYPE_LEAVE_OR_REACH = 1;
        public static final int CONDITION_TYPE_PM25 = 7;
        public static final int CONDITION_TYPE_SUN = 3;
        public static final int CONDITION_TYPE_TEMPERATURE = 4;
        public static final int CONDITION_TYPE_TIMER = 2;
        private int eventtype = 1;
        private String params;
        private int paramtype;

        public int getEventtype() {
            return this.eventtype;
        }

        public void setEventtype(int eventtype) {
            this.eventtype = eventtype;
        }

        public int getParamtype() {
            return this.paramtype;
        }

        public void setParamtype(int paramtype) {
            this.paramtype = paramtype;
        }

        public String getParams() {
            return this.params;
        }

        public void setParams(String params) {
            this.params = params;
        }

        public void setParams(Object object) {
            this.params = GsonUtils.toJson(object);
        }

        public <T> T getParams(Class<T> cls) {
            return (T) GsonUtils.getGson().fromJson(this.params, (Class) cls);
        }

        public static class ConditionConverter implements PropertyConverter<List<Condition>, String> {
            @Override // io.objectbox.converter.PropertyConverter
            public List<Condition> convertToEntityProperty(String databaseValue) {
                return (List) GsonUtils.fromJson(databaseValue, new TypeToken<List<Condition>>(this) { // from class: com.ltech.smarthome.model.bean.Automation.Condition.ConditionConverter.1
                }.getType());
            }

            @Override // io.objectbox.converter.PropertyConverter
            public String convertToDatabaseValue(List<Condition> entityProperty) {
                return GsonUtils.toJson(entityProperty, new TypeToken<List<Condition>>(this) { // from class: com.ltech.smarthome.model.bean.Automation.Condition.ConditionConverter.2
                }.getType());
            }
        }

        public static boolean isTimerCondition(Condition condition) {
            return condition.paramtype == 2;
        }

        public static boolean isSunCondition(Condition condition) {
            return condition.paramtype == 3;
        }

        public static boolean isHumidityCondition(Condition condition) {
            return condition.paramtype == 5;
        }

        public static boolean isTemperatureCondition(Condition condition) {
            return condition.paramtype == 4;
        }

        public static boolean isPm25Condition(Condition condition) {
            return condition.paramtype == 7;
        }

        public static boolean isEnv(Condition condition) {
            return condition.paramtype == 9;
        }

        public static boolean isAddressCondition(Condition condition) {
            return condition.paramtype == 1;
        }

        public static boolean isDeviceCondition(Condition condition) {
            return condition.paramtype == 8;
        }
    }

    public static class Action {
        private int actiondelays;
        private int actiontype;
        private String params;

        public int getActiontype() {
            return this.actiontype;
        }

        public void setActiontype(int actiontype) {
            this.actiontype = actiontype;
        }

        public int getActiondelays() {
            return this.actiondelays;
        }

        public void setActiondelays(int actiondelays) {
            this.actiondelays = actiondelays;
        }

        public String getParams() {
            return this.params;
        }

        public void setParams(String params) {
            this.params = params;
        }

        public void setParams(Object object) {
            this.params = GsonUtils.toJson(object);
        }

        public <T> T getParams(Class<T> cls) {
            return (T) GsonUtils.getGson().fromJson(this.params, (Class) cls);
        }

        public static class ActionConverter implements PropertyConverter<List<Action>, String> {
            @Override // io.objectbox.converter.PropertyConverter
            public List<Action> convertToEntityProperty(String databaseValue) {
                return (List) GsonUtils.fromJson(databaseValue, new TypeToken<List<Action>>(this) { // from class: com.ltech.smarthome.model.bean.Automation.Action.ActionConverter.1
                }.getType());
            }

            @Override // io.objectbox.converter.PropertyConverter
            public String convertToDatabaseValue(List<Action> entityProperty) {
                return GsonUtils.toJson(entityProperty, new TypeToken<List<Action>>(this) { // from class: com.ltech.smarthome.model.bean.Automation.Action.ActionConverter.2
                }.getType());
            }
        }
    }
}