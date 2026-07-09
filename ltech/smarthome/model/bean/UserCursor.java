package com.ltech.smarthome.model.bean;

import com.github.mikephil.charting.utils.Utils;
import com.ltech.smarthome.model.bean.User_;
import io.objectbox.BoxStore;
import io.objectbox.Cursor;
import io.objectbox.Transaction;
import io.objectbox.internal.CursorFactory;

/* loaded from: classes4.dex */
public final class UserCursor extends Cursor<User> {
    private static final User_.UserIdGetter ID_GETTER = User_.__ID_GETTER;
    private static final int __ID_appId = User_.appId.id;
    private static final int __ID_userName = User_.userName.id;
    private static final int __ID_session = User_.session.id;
    private static final int __ID_secretKey = User_.secretKey.id;
    private static final int __ID_productKey = User_.productKey.id;
    private static final int __ID_deviceSecret = User_.deviceSecret.id;
    private static final int __ID_iotId = User_.iotId.id;
    private static final int __ID_deviceName = User_.deviceName.id;
    private static final int __ID_email = User_.email.id;
    private static final int __ID_mobilePhone = User_.mobilePhone.id;
    private static final int __ID_headUrl = User_.headUrl.id;
    private static final int __ID_placetotal = User_.placetotal.id;
    private static final int __ID_devicetotal = User_.devicetotal.id;

    static final class Factory implements CursorFactory<User> {
        Factory() {
        }

        @Override // io.objectbox.internal.CursorFactory
        public Cursor<User> createCursor(Transaction tx, long cursorHandle, BoxStore boxStoreForEntities) {
            return new UserCursor(tx, cursorHandle, boxStoreForEntities);
        }
    }

    public UserCursor(Transaction tx, long cursor, BoxStore boxStore) {
        super(tx, cursor, User_.__INSTANCE, boxStore);
    }

    @Override // io.objectbox.Cursor
    public long getId(User entity) {
        return ID_GETTER.getId(entity);
    }

    @Override // io.objectbox.Cursor
    public long put(User entity) {
        String userName = entity.getUserName();
        int i = userName != null ? __ID_userName : 0;
        String session = entity.getSession();
        int i2 = session != null ? __ID_session : 0;
        String secretKey = entity.getSecretKey();
        int i3 = secretKey != null ? __ID_secretKey : 0;
        String productKey = entity.getProductKey();
        collect400000(this.cursor, 0L, 1, i, userName, i2, session, i3, secretKey, productKey != null ? __ID_productKey : 0, productKey);
        String deviceSecret = entity.getDeviceSecret();
        int i4 = deviceSecret != null ? __ID_deviceSecret : 0;
        String iotId = entity.getIotId();
        int i5 = iotId != null ? __ID_iotId : 0;
        String deviceName = entity.getDeviceName();
        int i6 = deviceName != null ? __ID_deviceName : 0;
        String email = entity.getEmail();
        collect400000(this.cursor, 0L, 0, i4, deviceSecret, i5, iotId, i6, deviceName, email != null ? __ID_email : 0, email);
        String mobilePhone = entity.getMobilePhone();
        int i7 = mobilePhone != null ? __ID_mobilePhone : 0;
        String headUrl = entity.getHeadUrl();
        long collect313311 = collect313311(this.cursor, entity.getUserId(), 2, i7, mobilePhone, headUrl != null ? __ID_headUrl : 0, headUrl, 0, null, 0, null, __ID_appId, entity.getAppId(), __ID_placetotal, entity.getPlacetotal(), __ID_devicetotal, entity.getDevicetotal(), 0, 0, 0, 0, 0, 0, 0, 0.0f, 0, Utils.DOUBLE_EPSILON);
        entity.setUserId(collect313311);
        return collect313311;
    }
}