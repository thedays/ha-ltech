package com.ltech.smarthome.model.bean;

import com.huawei.hms.push.constant.RemoteMessageConst;
import com.justalk.cloud.lemon.MtcConf2Constants;
import com.ltech.smarthome.model.bean.DeviceCursor;
import com.ltech.smarthome.model.bean.DeviceState;
import com.ltech.smarthome.model.bean.KValue;
import com.ltech.smarthome.push.PushContentParamKey;
import com.ltech.smarthome.utils.Constants;
import io.objectbox.EntityInfo;
import io.objectbox.Property;
import io.objectbox.internal.CursorFactory;
import io.objectbox.internal.IdGetter;
import java.util.Map;

/* loaded from: classes4.dex */
public final class Device_ implements EntityInfo<Device> {
    public static final Property<Device>[] __ALL_PROPERTIES;
    public static final String __DB_NAME = "Device";
    public static final int __ENTITY_ID = 2;
    public static final String __ENTITY_NAME = "Device";
    public static final Property<Device> __ID_PROPERTY;
    public static final Device_ __INSTANCE;
    public static final Property<Device> bleversion;
    public static final Property<Device> btmodule;
    public static final Property<Device> checkTime;
    public static final Property<Device> createtime;
    public static final Property<Device> deviceId;
    public static final Property<Device> deviceName;
    public static final Property<Device> deviceState;
    public static final Property<Device> devicesn;
    public static final Property<Device> extParam;
    public static final Property<Device> firmwareversion;
    public static final Property<Device> floorId;
    public static final Property<Device> floorName;
    public static final Property<Device> hardwareId;
    public static final Property<Device> id;
    public static final Property<Device> imageIndex;
    public static final Property<Device> index;
    public static final Property<Device> iotDeviceName;
    public static final Property<Device> iotProductKey;
    public static final Property<Device> latitude;
    public static final Property<Device> longitude;
    public static final Property<Device> maccode;
    public static final Property<Device> macdeviceid;
    public static final Property<Device> macfalg;
    public static final Property<Device> mainProductId;
    public static final Property<Device> maxkelvin;
    public static final Property<Device> mcuversion;
    public static final Property<Device> minkelvin;
    public static final Property<Device> oauthtype;
    public static final Property<Device> onlineFlag;
    public static final Property<Device> panelColor;
    public static final Property<Device> param;
    public static final Property<Device> placeId;
    public static final Property<Device> platFormDeviceId;
    public static final Property<Device> presetKValues;
    public static final Property<Device> productId;
    public static final Property<Device> reportinstruct;
    public static final Property<Device> roomId;
    public static final Property<Device> roomName;
    public static final Property<Device> status;
    public static final Property<Device> subhide;
    public static final Property<Device> unicastAddress;
    public static final Property<Device> userId;
    public static final Property<Device> virtual;
    public static final Property<Device> wifiMac;
    public static final Property<Device> writable;
    public static final Class<Device> __ENTITY_CLASS = Device.class;
    public static final CursorFactory<Device> __CURSOR_FACTORY = new DeviceCursor.Factory();
    static final DeviceIdGetter __ID_GETTER = new DeviceIdGetter();

    @Override // io.objectbox.EntityInfo
    public int getEntityId() {
        return 2;
    }

    static {
        Device_ device_ = new Device_();
        __INSTANCE = device_;
        Property<Device> property = new Property<>(device_, 0, 1, Long.TYPE, "id", true, "id");
        id = property;
        Property<Device> property2 = new Property<>(device_, 1, 2, Long.TYPE, "deviceId");
        deviceId = property2;
        Property<Device> property3 = new Property<>(device_, 2, 21, String.class, "devicesn");
        devicesn = property3;
        Property<Device> property4 = new Property<>(device_, 3, 3, Long.TYPE, MtcConf2Constants.MtcConfThirdUserIdKey);
        userId = property4;
        Property<Device> property5 = new Property<>(device_, 4, 4, Long.TYPE, "floorId");
        floorId = property5;
        Property<Device> property6 = new Property<>(device_, 5, 5, Long.TYPE, "roomId");
        roomId = property6;
        Property<Device> property7 = new Property<>(device_, 6, 6, Long.TYPE, "placeId");
        placeId = property7;
        Property<Device> property8 = new Property<>(device_, 7, 7, String.class, "wifiMac");
        wifiMac = property8;
        Property<Device> property9 = new Property<>(device_, 8, 8, String.class, "productId");
        productId = property9;
        Property<Device> property10 = new Property<>(device_, 9, 9, String.class, "hardwareId");
        hardwareId = property10;
        Property<Device> property11 = new Property<>(device_, 10, 10, Integer.TYPE, "onlineFlag");
        onlineFlag = property11;
        Property<Device> property12 = new Property<>(device_, 11, 11, String.class, "deviceName");
        deviceName = property12;
        Property<Device> property13 = new Property<>(device_, 12, 12, Integer.TYPE, "macfalg");
        macfalg = property13;
        Property<Device> property14 = new Property<>(device_, 13, 13, Long.TYPE, "macdeviceid");
        macdeviceid = property14;
        Property<Device> property15 = new Property<>(device_, 14, 14, String.class, "deviceState", false, "deviceState", DeviceState.DeviceStateConverter.class, DeviceState.class);
        deviceState = property15;
        Property<Device> property16 = new Property<>(device_, 15, 15, String.class, "platFormDeviceId");
        platFormDeviceId = property16;
        Property<Device> property17 = new Property<>(device_, 16, 16, String.class, "iotDeviceName");
        iotDeviceName = property17;
        Property<Device> property18 = new Property<>(device_, 17, 17, String.class, "iotProductKey");
        iotProductKey = property18;
        Property<Device> property19 = new Property<>(device_, 18, 18, Integer.TYPE, "imageIndex");
        imageIndex = property19;
        Property<Device> property20 = new Property<>(device_, 19, 19, String.class, RemoteMessageConst.MessageBody.PARAM);
        param = property20;
        Property<Device> property21 = new Property<>(device_, 20, 20, String.class, "extParam");
        extParam = property21;
        Property<Device> property22 = new Property<>(device_, 21, 22, String.class, "roomName");
        roomName = property22;
        Property<Device> property23 = new Property<>(device_, 22, 23, String.class, "floorName");
        floorName = property23;
        Property<Device> property24 = new Property<>(device_, 23, 26, String.class, "createtime");
        createtime = property24;
        Property<Device> property25 = new Property<>(device_, 24, 27, Integer.TYPE, "unicastAddress");
        unicastAddress = property25;
        Property<Device> property26 = new Property<>(device_, 25, 28, Long.TYPE, "checkTime");
        checkTime = property26;
        Property<Device> property27 = new Property<>(device_, 26, 29, String.class, "firmwareversion");
        firmwareversion = property27;
        Property<Device> property28 = new Property<>(device_, 27, 30, String.class, "mcuversion");
        mcuversion = property28;
        Property<Device> property29 = new Property<>(device_, 28, 31, String.class, "bleversion");
        bleversion = property29;
        Property<Device> property30 = new Property<>(device_, 29, 32, Integer.TYPE, "minkelvin");
        minkelvin = property30;
        Property<Device> property31 = new Property<>(device_, 30, 33, Integer.TYPE, "maxkelvin");
        maxkelvin = property31;
        Property<Device> property32 = new Property<>(device_, 31, 34, String.class, "maccode");
        maccode = property32;
        Property<Device> property33 = new Property<>(device_, 32, 35, Double.TYPE, Constants.LATITUDE);
        latitude = property33;
        Property<Device> property34 = new Property<>(device_, 33, 36, Double.TYPE, Constants.LONGITUDE);
        longitude = property34;
        Property<Device> property35 = new Property<>(device_, 34, 37, Integer.TYPE, "oauthtype");
        oauthtype = property35;
        Property<Device> property36 = new Property<>(device_, 35, 38, String.class, "mainProductId");
        mainProductId = property36;
        Property<Device> property37 = new Property<>(device_, 36, 39, Integer.TYPE, "subhide");
        subhide = property37;
        Property<Device> property38 = new Property<>(device_, 37, 40, String.class, "btmodule");
        btmodule = property38;
        Property<Device> property39 = new Property<>(device_, 38, 41, String.class, "panelColor");
        panelColor = property39;
        Property<Device> property40 = new Property<>(device_, 39, 42, String.class, PushContentParamKey.REPORT_INSTRUCT);
        reportinstruct = property40;
        Property<Device> property41 = new Property<>(device_, 40, 43, Integer.TYPE, "status");
        status = property41;
        Property<Device> property42 = new Property<>(device_, 41, 44, Integer.TYPE, "virtual");
        virtual = property42;
        Property<Device> property43 = new Property<>(device_, 42, 45, Integer.TYPE, "writable");
        writable = property43;
        Property<Device> property44 = new Property<>(device_, 43, 46, String.class, "presetKValues", false, "presetKValues", KValue.Converter.class, Map.class);
        presetKValues = property44;
        Property<Device> property45 = new Property<>(device_, 44, 25, Integer.TYPE, "index");
        index = property45;
        __ALL_PROPERTIES = new Property[]{property, property2, property3, property4, property5, property6, property7, property8, property9, property10, property11, property12, property13, property14, property15, property16, property17, property18, property19, property20, property21, property22, property23, property24, property25, property26, property27, property28, property29, property30, property31, property32, property33, property34, property35, property36, property37, property38, property39, property40, property41, property42, property43, property44, property45};
        __ID_PROPERTY = property;
    }

    @Override // io.objectbox.EntityInfo
    public String getEntityName() {
        return "Device";
    }

    @Override // io.objectbox.EntityInfo
    public Class<Device> getEntityClass() {
        return __ENTITY_CLASS;
    }

    @Override // io.objectbox.EntityInfo
    public String getDbName() {
        return "Device";
    }

    @Override // io.objectbox.EntityInfo
    public Property<Device>[] getAllProperties() {
        return __ALL_PROPERTIES;
    }

    @Override // io.objectbox.EntityInfo
    public Property<Device> getIdProperty() {
        return __ID_PROPERTY;
    }

    @Override // io.objectbox.EntityInfo
    public IdGetter<Device> getIdGetter() {
        return __ID_GETTER;
    }

    @Override // io.objectbox.EntityInfo
    public CursorFactory<Device> getCursorFactory() {
        return __CURSOR_FACTORY;
    }

    static final class DeviceIdGetter implements IdGetter<Device> {
        DeviceIdGetter() {
        }

        @Override // io.objectbox.internal.IdGetter
        public long getId(Device object) {
            return object.getId();
        }
    }
}