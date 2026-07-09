package com.ltech.smarthome.model.bean;

import com.ltech.smarthome.model.bean.DeviceState;
import com.ltech.smarthome.model.bean.Device_;
import com.ltech.smarthome.model.bean.KValue;
import io.objectbox.BoxStore;
import io.objectbox.Cursor;
import io.objectbox.Transaction;
import io.objectbox.internal.CursorFactory;
import java.util.Map;

/* loaded from: classes4.dex */
public final class DeviceCursor extends Cursor<Device> {
    private final DeviceState.DeviceStateConverter deviceStateConverter;
    private final KValue.Converter presetKValuesConverter;
    private static final Device_.DeviceIdGetter ID_GETTER = Device_.__ID_GETTER;
    private static final int __ID_deviceId = Device_.deviceId.id;
    private static final int __ID_devicesn = Device_.devicesn.id;
    private static final int __ID_userId = Device_.userId.id;
    private static final int __ID_floorId = Device_.floorId.id;
    private static final int __ID_roomId = Device_.roomId.id;
    private static final int __ID_placeId = Device_.placeId.id;
    private static final int __ID_wifiMac = Device_.wifiMac.id;
    private static final int __ID_productId = Device_.productId.id;
    private static final int __ID_hardwareId = Device_.hardwareId.id;
    private static final int __ID_onlineFlag = Device_.onlineFlag.id;
    private static final int __ID_deviceName = Device_.deviceName.id;
    private static final int __ID_macfalg = Device_.macfalg.id;
    private static final int __ID_macdeviceid = Device_.macdeviceid.id;
    private static final int __ID_deviceState = Device_.deviceState.id;
    private static final int __ID_platFormDeviceId = Device_.platFormDeviceId.id;
    private static final int __ID_iotDeviceName = Device_.iotDeviceName.id;
    private static final int __ID_iotProductKey = Device_.iotProductKey.id;
    private static final int __ID_imageIndex = Device_.imageIndex.id;
    private static final int __ID_param = Device_.param.id;
    private static final int __ID_extParam = Device_.extParam.id;
    private static final int __ID_roomName = Device_.roomName.id;
    private static final int __ID_floorName = Device_.floorName.id;
    private static final int __ID_createtime = Device_.createtime.id;
    private static final int __ID_unicastAddress = Device_.unicastAddress.id;
    private static final int __ID_checkTime = Device_.checkTime.id;
    private static final int __ID_firmwareversion = Device_.firmwareversion.id;
    private static final int __ID_mcuversion = Device_.mcuversion.id;
    private static final int __ID_bleversion = Device_.bleversion.id;
    private static final int __ID_minkelvin = Device_.minkelvin.id;
    private static final int __ID_maxkelvin = Device_.maxkelvin.id;
    private static final int __ID_maccode = Device_.maccode.id;
    private static final int __ID_latitude = Device_.latitude.id;
    private static final int __ID_longitude = Device_.longitude.id;
    private static final int __ID_oauthtype = Device_.oauthtype.id;
    private static final int __ID_mainProductId = Device_.mainProductId.id;
    private static final int __ID_subhide = Device_.subhide.id;
    private static final int __ID_btmodule = Device_.btmodule.id;
    private static final int __ID_panelColor = Device_.panelColor.id;
    private static final int __ID_reportinstruct = Device_.reportinstruct.id;
    private static final int __ID_status = Device_.status.id;
    private static final int __ID_virtual = Device_.virtual.id;
    private static final int __ID_writable = Device_.writable.id;
    private static final int __ID_presetKValues = Device_.presetKValues.id;
    private static final int __ID_index = Device_.index.id;

    static final class Factory implements CursorFactory<Device> {
        Factory() {
        }

        @Override // io.objectbox.internal.CursorFactory
        public Cursor<Device> createCursor(Transaction tx, long cursorHandle, BoxStore boxStoreForEntities) {
            return new DeviceCursor(tx, cursorHandle, boxStoreForEntities);
        }
    }

    public DeviceCursor(Transaction tx, long cursor, BoxStore boxStore) {
        super(tx, cursor, Device_.__INSTANCE, boxStore);
        this.deviceStateConverter = new DeviceState.DeviceStateConverter();
        this.presetKValuesConverter = new KValue.Converter();
    }

    @Override // io.objectbox.Cursor
    public long getId(Device entity) {
        return ID_GETTER.getId(entity);
    }

    @Override // io.objectbox.Cursor
    public long put(Device entity) {
        String devicesn = entity.getDevicesn();
        int i = devicesn != null ? __ID_devicesn : 0;
        String wifiMac = entity.getWifiMac();
        int i2 = wifiMac != null ? __ID_wifiMac : 0;
        String productId = entity.getProductId();
        int i3 = productId != null ? __ID_productId : 0;
        String hardwareId = entity.getHardwareId();
        collect400000(this.cursor, 0L, 1, i, devicesn, i2, wifiMac, i3, productId, hardwareId != null ? __ID_hardwareId : 0, hardwareId);
        String deviceName = entity.getDeviceName();
        int i4 = deviceName != null ? __ID_deviceName : 0;
        DeviceState deviceState = entity.getDeviceState();
        int i5 = deviceState != null ? __ID_deviceState : 0;
        String platFormDeviceId = entity.getPlatFormDeviceId();
        int i6 = platFormDeviceId != null ? __ID_platFormDeviceId : 0;
        String iotDeviceName = entity.getIotDeviceName();
        collect400000(this.cursor, 0L, 0, i4, deviceName, i5, i5 != 0 ? this.deviceStateConverter.convertToDatabaseValue(deviceState) : null, i6, platFormDeviceId, iotDeviceName != null ? __ID_iotDeviceName : 0, iotDeviceName);
        String iotProductKey = entity.getIotProductKey();
        int i7 = iotProductKey != null ? __ID_iotProductKey : 0;
        String param = entity.getParam();
        int i8 = param != null ? __ID_param : 0;
        String extParam = entity.getExtParam();
        int i9 = extParam != null ? __ID_extParam : 0;
        String roomName = entity.getRoomName();
        collect400000(this.cursor, 0L, 0, i7, iotProductKey, i8, param, i9, extParam, roomName != null ? __ID_roomName : 0, roomName);
        String floorName = entity.getFloorName();
        int i10 = floorName != null ? __ID_floorName : 0;
        String createtime = entity.getCreatetime();
        int i11 = createtime != null ? __ID_createtime : 0;
        String firmwareversion = entity.getFirmwareversion();
        int i12 = firmwareversion != null ? __ID_firmwareversion : 0;
        String mcuversion = entity.getMcuversion();
        collect400000(this.cursor, 0L, 0, i10, floorName, i11, createtime, i12, firmwareversion, mcuversion != null ? __ID_mcuversion : 0, mcuversion);
        String bleversion = entity.getBleversion();
        int i13 = bleversion != null ? __ID_bleversion : 0;
        String maccode = entity.getMaccode();
        int i14 = maccode != null ? __ID_maccode : 0;
        String mainProductId = entity.getMainProductId();
        int i15 = mainProductId != null ? __ID_mainProductId : 0;
        String btmodule = entity.getBtmodule();
        collect400000(this.cursor, 0L, 0, i13, bleversion, i14, maccode, i15, mainProductId, btmodule != null ? __ID_btmodule : 0, btmodule);
        String panelColor = entity.getPanelColor();
        int i16 = panelColor != null ? __ID_panelColor : 0;
        String reportinstruct = entity.getReportinstruct();
        int i17 = reportinstruct != null ? __ID_reportinstruct : 0;
        Map<String, KValue> presetKValues = entity.getPresetKValues();
        int i18 = presetKValues != null ? __ID_presetKValues : 0;
        collect313311(this.cursor, 0L, 0, i16, panelColor, i17, reportinstruct, i18, i18 != 0 ? this.presetKValuesConverter.convertToDatabaseValue(presetKValues) : null, 0, null, __ID_deviceId, entity.getDeviceId(), __ID_userId, entity.getUserId(), __ID_floorId, entity.getFloorId(), __ID_onlineFlag, entity.getOnlineFlag(), __ID_macfalg, entity.getMacfalg(), __ID_imageIndex, entity.getImageIndex(), 0, 0.0f, __ID_latitude, entity.getLatitude());
        collect313311(this.cursor, 0L, 0, 0, null, 0, null, 0, null, 0, null, __ID_roomId, entity.getRoomId(), __ID_placeId, entity.getPlaceId(), __ID_macdeviceid, entity.getMacdeviceid(), __ID_unicastAddress, entity.getUnicastAddress(), __ID_minkelvin, entity.getMinkelvin(), __ID_maxkelvin, entity.getMaxkelvin(), 0, 0.0f, __ID_longitude, entity.getLongitude());
        collect004000(this.cursor, 0L, 0, __ID_checkTime, entity.getCheckTime(), __ID_oauthtype, entity.getOauthtype(), __ID_subhide, entity.getSubhide(), __ID_status, entity.getStatus());
        long collect004000 = collect004000(this.cursor, entity.getId(), 2, __ID_virtual, entity.getVirtual(), __ID_writable, entity.getWritable(), __ID_index, entity.getIndex(), 0, 0L);
        entity.setId(collect004000);
        return collect004000;
    }
}