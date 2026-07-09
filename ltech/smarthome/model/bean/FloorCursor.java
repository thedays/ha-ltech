package com.ltech.smarthome.model.bean;

import com.github.mikephil.charting.utils.Utils;
import com.ltech.smarthome.model.bean.Floor_;
import io.objectbox.BoxStore;
import io.objectbox.Cursor;
import io.objectbox.Transaction;
import io.objectbox.internal.CursorFactory;

/* loaded from: classes4.dex */
public final class FloorCursor extends Cursor<Floor> {
    private static final Floor_.FloorIdGetter ID_GETTER = Floor_.__ID_GETTER;
    private static final int __ID_floorId = Floor_.floorId.id;
    private static final int __ID_userId = Floor_.userId.id;
    private static final int __ID_floorName = Floor_.floorName.id;
    private static final int __ID_placeId = Floor_.placeId.id;
    private static final int __ID_index = Floor_.index.id;

    static final class Factory implements CursorFactory<Floor> {
        Factory() {
        }

        @Override // io.objectbox.internal.CursorFactory
        public Cursor<Floor> createCursor(Transaction tx, long cursorHandle, BoxStore boxStoreForEntities) {
            return new FloorCursor(tx, cursorHandle, boxStoreForEntities);
        }
    }

    public FloorCursor(Transaction tx, long cursor, BoxStore boxStore) {
        super(tx, cursor, Floor_.__INSTANCE, boxStore);
    }

    @Override // io.objectbox.Cursor
    public long getId(Floor entity) {
        return ID_GETTER.getId(entity);
    }

    @Override // io.objectbox.Cursor
    public long put(Floor entity) {
        String floorName = entity.getFloorName();
        long collect313311 = collect313311(this.cursor, entity.getId(), 3, floorName != null ? __ID_floorName : 0, floorName, 0, null, 0, null, 0, null, __ID_floorId, entity.getFloorId(), __ID_userId, entity.getUserId(), __ID_placeId, entity.getPlaceId(), __ID_index, entity.getIndex(), 0, 0, 0, 0, 0, 0.0f, 0, Utils.DOUBLE_EPSILON);
        entity.setId(collect313311);
        return collect313311;
    }
}