package com.ltech.smarthome.model.bean;

import com.github.mikephil.charting.utils.Utils;
import com.ltech.smarthome.model.bean.PlaceUser_;
import io.objectbox.BoxStore;
import io.objectbox.Cursor;
import io.objectbox.Transaction;
import io.objectbox.internal.CursorFactory;

/* loaded from: classes4.dex */
public final class PlaceUserCursor extends Cursor<PlaceUser> {
    private static final PlaceUser_.PlaceUserIdGetter ID_GETTER = PlaceUser_.__ID_GETTER;
    private static final int __ID_placeId = PlaceUser_.placeId.id;
    private static final int __ID_roleType = PlaceUser_.roleType.id;
    private static final int __ID_userName = PlaceUser_.userName.id;
    private static final int __ID_userId = PlaceUser_.userId.id;
    private static final int __ID_headUrl = PlaceUser_.headUrl.id;
    private static final int __ID_currentUserId = PlaceUser_.currentUserId.id;
    private static final int __ID_remark = PlaceUser_.remark.id;
    private static final int __ID_mobilephone = PlaceUser_.mobilephone.id;
    private static final int __ID_email = PlaceUser_.email.id;

    static final class Factory implements CursorFactory<PlaceUser> {
        Factory() {
        }

        @Override // io.objectbox.internal.CursorFactory
        public Cursor<PlaceUser> createCursor(Transaction tx, long cursorHandle, BoxStore boxStoreForEntities) {
            return new PlaceUserCursor(tx, cursorHandle, boxStoreForEntities);
        }
    }

    public PlaceUserCursor(Transaction tx, long cursor, BoxStore boxStore) {
        super(tx, cursor, PlaceUser_.__INSTANCE, boxStore);
    }

    @Override // io.objectbox.Cursor
    public long getId(PlaceUser entity) {
        return ID_GETTER.getId(entity);
    }

    @Override // io.objectbox.Cursor
    public long put(PlaceUser entity) {
        String userName = entity.getUserName();
        int i = userName != null ? __ID_userName : 0;
        String headUrl = entity.getHeadUrl();
        int i2 = headUrl != null ? __ID_headUrl : 0;
        String remark = entity.getRemark();
        int i3 = remark != null ? __ID_remark : 0;
        String mobilephone = entity.getMobilephone();
        collect400000(this.cursor, 0L, 1, i, userName, i2, headUrl, i3, remark, mobilephone != null ? __ID_mobilephone : 0, mobilephone);
        String email = entity.getEmail();
        long collect313311 = collect313311(this.cursor, entity.getPlaceUserId(), 2, email != null ? __ID_email : 0, email, 0, null, 0, null, 0, null, __ID_placeId, entity.getPlaceId(), __ID_userId, entity.getUserId(), __ID_currentUserId, entity.getCurrentUserId(), __ID_roleType, entity.getRoleType(), 0, 0, 0, 0, 0, 0.0f, 0, Utils.DOUBLE_EPSILON);
        entity.setPlaceUserId(collect313311);
        return collect313311;
    }
}