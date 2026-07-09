package com.ltech.smarthome.model.bean;

import com.ltech.smarthome.model.bean.Place_;
import io.objectbox.BoxStore;
import io.objectbox.Cursor;
import io.objectbox.Transaction;
import io.objectbox.internal.CursorFactory;

/* loaded from: classes4.dex */
public final class PlaceCursor extends Cursor<Place> {
    private static final Place_.PlaceIdGetter ID_GETTER = Place_.__ID_GETTER;
    private static final int __ID_placeId = Place_.placeId.id;
    private static final int __ID_userId = Place_.userId.id;
    private static final int __ID_currentUserId = Place_.currentUserId.id;
    private static final int __ID_placeName = Place_.placeName.id;
    private static final int __ID_roleType = Place_.roleType.id;
    private static final int __ID_latitude = Place_.latitude.id;
    private static final int __ID_longitude = Place_.longitude.id;
    private static final int __ID_qrCode = Place_.qrCode.id;
    private static final int __ID_location = Place_.location.id;
    private static final int __ID_imageUrl = Place_.imageUrl.id;
    private static final int __ID_netKey = Place_.netKey.id;
    private static final int __ID_appKey = Place_.appKey.id;
    private static final int __ID_meshUUID = Place_.meshUUID.id;
    private static final int __ID_floortotal = Place_.floortotal.id;
    private static final int __ID_roomtotal = Place_.roomtotal.id;
    private static final int __ID_devicetotal = Place_.devicetotal.id;
    private static final int __ID_provisionerAddress = Place_.provisionerAddress.id;
    private static final int __ID_expiresIn = Place_.expiresIn.id;
    private static final int __ID_createdAt = Place_.createdAt.id;
    private static final int __ID_appToken = Place_.appToken.id;
    private static final int __ID_ivindex = Place_.ivindex.id;

    static final class Factory implements CursorFactory<Place> {
        Factory() {
        }

        @Override // io.objectbox.internal.CursorFactory
        public Cursor<Place> createCursor(Transaction tx, long cursorHandle, BoxStore boxStoreForEntities) {
            return new PlaceCursor(tx, cursorHandle, boxStoreForEntities);
        }
    }

    public PlaceCursor(Transaction tx, long cursor, BoxStore boxStore) {
        super(tx, cursor, Place_.__INSTANCE, boxStore);
    }

    @Override // io.objectbox.Cursor
    public long getId(Place entity) {
        return ID_GETTER.getId(entity);
    }

    @Override // io.objectbox.Cursor
    public long put(Place entity) {
        String placeName = entity.getPlaceName();
        int i = placeName != null ? __ID_placeName : 0;
        String qrCode = entity.getQrCode();
        int i2 = qrCode != null ? __ID_qrCode : 0;
        String location = entity.getLocation();
        int i3 = location != null ? __ID_location : 0;
        String imageUrl = entity.getImageUrl();
        collect400000(this.cursor, 0L, 1, i, placeName, i2, qrCode, i3, location, imageUrl != null ? __ID_imageUrl : 0, imageUrl);
        String netKey = entity.getNetKey();
        int i4 = netKey != null ? __ID_netKey : 0;
        String appKey = entity.getAppKey();
        int i5 = appKey != null ? __ID_appKey : 0;
        String meshUUID = entity.getMeshUUID();
        int i6 = meshUUID != null ? __ID_meshUUID : 0;
        String provisionerAddress = entity.getProvisionerAddress();
        collect400000(this.cursor, 0L, 0, i4, netKey, i5, appKey, i6, meshUUID, provisionerAddress != null ? __ID_provisionerAddress : 0, provisionerAddress);
        String appToken = entity.getAppToken();
        collect313311(this.cursor, 0L, 0, appToken != null ? __ID_appToken : 0, appToken, 0, null, 0, null, 0, null, __ID_placeId, entity.getPlaceId(), __ID_userId, entity.getUserId(), __ID_currentUserId, entity.getCurrentUserId(), __ID_roleType, entity.getRoleType(), __ID_floortotal, entity.getFloortotal(), __ID_roomtotal, entity.getRoomtotal(), 0, 0.0f, __ID_latitude, entity.getLatitude());
        long collect313311 = collect313311(this.cursor, entity.getId(), 2, 0, null, 0, null, 0, null, 0, null, __ID_devicetotal, entity.getDevicetotal(), __ID_expiresIn, entity.getExpiresIn(), __ID_createdAt, entity.getCreatedAt(), __ID_ivindex, entity.getIvindex(), 0, 0, 0, 0, 0, 0.0f, __ID_longitude, entity.getLongitude());
        entity.setId(collect313311);
        return collect313311;
    }
}