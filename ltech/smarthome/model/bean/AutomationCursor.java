package com.ltech.smarthome.model.bean;

import com.github.mikephil.charting.utils.Utils;
import com.ltech.smarthome.model.bean.Automation;
import com.ltech.smarthome.model.bean.Automation_;
import io.objectbox.BoxStore;
import io.objectbox.Cursor;
import io.objectbox.Transaction;
import io.objectbox.internal.CursorFactory;
import java.util.List;

/* loaded from: classes4.dex */
public final class AutomationCursor extends Cursor<Automation> {
    private final Automation.Action.ActionConverter actionsConverter;
    private final Automation.Condition.ConditionConverter conditionsConverter;
    private static final Automation_.AutomationIdGetter ID_GETTER = Automation_.__ID_GETTER;
    private static final int __ID_automationId = Automation_.automationId.id;
    private static final int __ID_currentUserId = Automation_.currentUserId.id;
    private static final int __ID_userId = Automation_.userId.id;
    private static final int __ID_placeId = Automation_.placeId.id;
    private static final int __ID_name = Automation_.name.id;
    private static final int __ID_startTime = Automation_.startTime.id;
    private static final int __ID_endTime = Automation_.endTime.id;
    private static final int __ID_weeks = Automation_.weeks.id;
    private static final int __ID_timeZone = Automation_.timeZone.id;
    private static final int __ID_picIndex = Automation_.picIndex.id;
    private static final int __ID_enable = Automation_.enable.id;
    private static final int __ID_conditionType = Automation_.conditionType.id;
    private static final int __ID_index = Automation_.index.id;
    private static final int __ID_automationType = Automation_.automationType.id;
    private static final int __ID_gatewayDeviceId = Automation_.gatewayDeviceId.id;
    private static final int __ID_cycleindex = Automation_.cycleindex.id;
    private static final int __ID_intervaltime = Automation_.intervaltime.id;
    private static final int __ID_conditions = Automation_.conditions.id;
    private static final int __ID_actions = Automation_.actions.id;

    static final class Factory implements CursorFactory<Automation> {
        Factory() {
        }

        @Override // io.objectbox.internal.CursorFactory
        public Cursor<Automation> createCursor(Transaction tx, long cursorHandle, BoxStore boxStoreForEntities) {
            return new AutomationCursor(tx, cursorHandle, boxStoreForEntities);
        }
    }

    public AutomationCursor(Transaction tx, long cursor, BoxStore boxStore) {
        super(tx, cursor, Automation_.__INSTANCE, boxStore);
        this.conditionsConverter = new Automation.Condition.ConditionConverter();
        this.actionsConverter = new Automation.Action.ActionConverter();
    }

    @Override // io.objectbox.Cursor
    public long getId(Automation entity) {
        return ID_GETTER.getId(entity);
    }

    @Override // io.objectbox.Cursor
    public long put(Automation automation) {
        String name = automation.getName();
        int i = name != null ? __ID_name : 0;
        String startTime = automation.getStartTime();
        int i2 = startTime != null ? __ID_startTime : 0;
        String endTime = automation.getEndTime();
        int i3 = endTime != null ? __ID_endTime : 0;
        String weeks = automation.getWeeks();
        collect400000(this.cursor, 0L, 1, i, name, i2, startTime, i3, endTime, weeks != null ? __ID_weeks : 0, weeks);
        String timeZone = automation.getTimeZone();
        int i4 = timeZone != null ? __ID_timeZone : 0;
        List<Automation.Condition> conditions = automation.getConditions();
        int i5 = conditions != null ? __ID_conditions : 0;
        List<Automation.Action> actions = automation.getActions();
        int i6 = actions != null ? __ID_actions : 0;
        collect313311(this.cursor, 0L, 0, i4, timeZone, i5, i5 != 0 ? this.conditionsConverter.convertToDatabaseValue(conditions) : null, i6, i6 != 0 ? this.actionsConverter.convertToDatabaseValue(actions) : null, 0, null, __ID_automationId, automation.getAutomationId(), __ID_currentUserId, automation.getCurrentUserId(), __ID_userId, automation.getUserId(), __ID_picIndex, automation.getPicIndex(), __ID_conditionType, automation.getConditionType(), __ID_index, automation.getIndex(), 0, 0.0f, 0, Utils.DOUBLE_EPSILON);
        long collect313311 = collect313311(this.cursor, automation.getId(), 2, 0, null, 0, null, 0, null, 0, null, __ID_placeId, automation.getPlaceId(), __ID_gatewayDeviceId, automation.getGatewayDeviceId(), __ID_automationType, automation.getAutomationType(), __ID_cycleindex, automation.getCycleindex(), __ID_intervaltime, automation.getIntervaltime(), __ID_enable, automation.isEnable() ? 1 : 0, 0, 0.0f, 0, Utils.DOUBLE_EPSILON);
        automation.setId(collect313311);
        return collect313311;
    }
}