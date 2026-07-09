package com.ltech.smarthome.model.bean;

import com.github.mikephil.charting.utils.Utils;
import com.ltech.smarthome.model.bean.Room_;
import io.objectbox.BoxStore;
import io.objectbox.Cursor;
import io.objectbox.Transaction;
import io.objectbox.internal.CursorFactory;

/* loaded from: classes4.dex */
public final class RoomCursor extends Cursor<Room> {
    private static final Room_.RoomIdGetter ID_GETTER = Room_.__ID_GETTER;
    private static final int __ID_roomId = Room_.roomId.id;
    private static final int __ID_userId = Room_.userId.id;
    private static final int __ID_roomName = Room_.roomName.id;
    private static final int __ID_floorId = Room_.floorId.id;
    private static final int __ID_placeId = Room_.placeId.id;
    private static final int __ID_index = Room_.index.id;

    static final class Factory implements CursorFactory<Room> {
        Factory() {
        }

        @Override // io.objectbox.internal.CursorFactory
        public Cursor<Room> createCursor(Transaction tx, long cursorHandle, BoxStore boxStoreForEntities) {
            return new RoomCursor(tx, cursorHandle, boxStoreForEntities);
        }
    }

    public RoomCursor(Transaction tx, long cursor, BoxStore boxStore) {
        super(tx, cursor, Room_.__INSTANCE, boxStore);
    }

    @Override // io.objectbox.Cursor
    public long getId(Room entity) {
        return ID_GETTER.getId(entity);
    }

    @Override // io.objectbox.Cursor
    public long put(Room entity) {
        String roomName = entity.getRoomName();
        collect313311(this.cursor, 0L, 1, roomName != null ? __ID_roomName : 0, roomName, 0, null, 0, null, 0, null, __ID_roomId, entity.getRoomId(), __ID_userId, entity.getUserId(), __ID_floorId, entity.getFloorId(), __ID_index, entity.getIndex(), 0, 0, 0, 0, 0, 0.0f, 0, Utils.DOUBLE_EPSILON);
        long collect004000 = collect004000(this.cursor, entity.getId(), 2, __ID_placeId, entity.getPlaceId(), 0, 0L, 0, 0L, 0, 0L);
        entity.setId(collect004000);
        return collect004000;
    }
}