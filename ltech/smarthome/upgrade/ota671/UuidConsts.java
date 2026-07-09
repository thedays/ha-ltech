package com.ltech.smarthome.upgrade.ota671;

import com.airoha.libfota.constant.AirohaOtaUUID;
import java.util.UUID;

/* loaded from: classes4.dex */
public class UuidConsts {
    public static UUID OTA_SERVICE = UUID.fromString(AirohaOtaUUID.DEFAULT_NEW_OTA_SERVICE_UUID);
    public static UUID OTA_CONTROL = UUID.fromString(AirohaOtaUUID.DEFAULT_NEW_OTA_NOTIFY_CHARC_UUID);
    public static UUID OTA_DATA = UUID.fromString(AirohaOtaUUID.DEFAULT_NEW_OTA_WRITE_CHARC_UUID);
    public static UUID CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
}