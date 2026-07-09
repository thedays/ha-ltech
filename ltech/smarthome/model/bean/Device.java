package com.ltech.smarthome.model.bean;

import android.text.TextUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.base.BaseExtParam;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.device_param.CgdProLightExtParam;
import com.ltech.smarthome.model.product.ProductId;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class Device implements Role {
    public static final String DEFAULT_PLAT_ID = "0_0";
    private String bleversion;
    private String btmodule;
    private long checkTime;
    private String createtime;
    private long deviceId;
    private String deviceName;
    private DeviceState deviceState;
    private String devicesn;
    private String extParam;
    private String firmwareversion;
    private long floorId;
    private String floorName;
    private String hardwareId;
    private long id;
    private int imageIndex;
    private int index;
    private String iotDeviceName;
    private String iotProductKey;
    private double latitude;
    private double longitude;
    private String maccode;
    private long macdeviceid;
    private int macfalg;
    private String mainProductId;
    private int maxkelvin;
    private String mcuversion;
    private int minkelvin;
    private int oauthtype;
    private int onlineFlag;
    private String panelColor;
    private String param;
    private long placeId;
    private String platFormDeviceId;
    private Map<String, KValue> presetKValues = new HashMap();
    private String productId;
    private String reportinstruct;
    private long roomId;
    private String roomName;
    private int status;
    private int subhide;
    private int unicastAddress;
    private long userId;
    private int virtual;
    private String wifiMac;
    private int writable;

    @Override // com.ltech.smarthome.model.bean.Role
    public int getIconPos() {
        return 0;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public int getSceneType() {
        return 0;
    }

    public String getPanelColor() {
        String str = this.panelColor;
        return str == null ? "7" : str;
    }

    public void setPanelColor(String panelColor) {
        this.panelColor = panelColor;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public String getCreatetime() {
        String str = this.createtime;
        return str == null ? "" : str;
    }

    public long getCheckTime() {
        return this.checkTime;
    }

    public void setCheckTime(long checkTime) {
        this.checkTime = checkTime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public String getName() {
        return getDeviceName();
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public int getIndex() {
        return this.index;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public long getObjectId() {
        return getDeviceId();
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public String getRoomName() {
        String str = this.roomName;
        return str == null ? "" : str;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public int getHideDevice() {
        BaseExtParam baseExtParam;
        try {
            if (isSubDevice() && getSubhide() == 1) {
                return 1;
            }
            if (getExtParam() == null || (baseExtParam = (BaseExtParam) GsonUtils.getGson().fromJson(getExtParam(), BaseExtParam.class)) == null) {
                return 0;
            }
            return baseExtParam.getHideDevice();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public int getDaliHidden() {
        CgdProLightExtParam cgdProLightExtParam;
        try {
            if (getExtParam() == null || (cgdProLightExtParam = (CgdProLightExtParam) GsonUtils.getGson().fromJson(getExtParam(), CgdProLightExtParam.class)) == null) {
                return 0;
            }
            return cgdProLightExtParam.getDaliHidden();
        } catch (Exception unused) {
            return 0;
        }
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public String getFloorName() {
        String str = this.floorName;
        return str == null ? "" : str;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDevicesn() {
        return this.devicesn;
    }

    public void setDevicesn(String devicesn) {
        this.devicesn = devicesn;
    }

    public long getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public long getFloorId() {
        return this.floorId;
    }

    public void setFloorId(long floorId) {
        this.floorId = floorId;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public long getRoomId() {
        return this.roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public long getPlaceId() {
        return this.placeId;
    }

    public void setPlaceId(long placeId) {
        this.placeId = placeId;
    }

    public String getWifiMac() {
        return this.wifiMac;
    }

    public void setWifiMac(String wifiMac) {
        this.wifiMac = wifiMac;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getHardwareId() {
        return TextUtils.isEmpty(this.hardwareId) ? "0" : this.hardwareId;
    }

    public void setHardwareId(String hardwareId) {
        this.hardwareId = hardwareId;
    }

    public int getOnlineFlag() {
        return this.onlineFlag;
    }

    public void setOnlineFlag(int onlineFlag) {
        this.onlineFlag = onlineFlag;
    }

    public int getImageIndex() {
        return this.imageIndex;
    }

    public void setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
    }

    public int getMacfalg() {
        return this.macfalg;
    }

    public void setMacfalg(int macfalg) {
        this.macfalg = macfalg;
    }

    public long getMacdeviceid() {
        return this.macdeviceid;
    }

    public void setMacdeviceid(long macdeviceid) {
        this.macdeviceid = macdeviceid;
    }

    public boolean isOnline() {
        return this.onlineFlag != 2;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPlatFormDeviceId() {
        return this.platFormDeviceId;
    }

    public void setPlatFormDeviceId(String platFormDeviceId) {
        this.platFormDeviceId = platFormDeviceId;
        if (TextUtils.isEmpty(platFormDeviceId)) {
            return;
        }
        setIotProductKey(platFormDeviceId.split(ProductId.SPLIT)[0]);
        setIotDeviceName(platFormDeviceId.split(ProductId.SPLIT)[1]);
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getIotDeviceName() {
        return this.iotDeviceName;
    }

    public void setIotDeviceName(String iotDeviceName) {
        this.iotDeviceName = iotDeviceName;
    }

    public String getIotProductKey() {
        return this.iotProductKey;
    }

    public void setIotProductKey(String iotProductKey) {
        this.iotProductKey = iotProductKey;
    }

    public String getParam() {
        return this.param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public String getExtParam() {
        return this.extParam;
    }

    public void setExtParam(String extParam) {
        this.extParam = extParam;
    }

    public void setParam(Object object) {
        this.param = GsonUtils.toJson(object);
    }

    public <T> T getParam(Class<T> cls) {
        return (T) GsonUtils.getGson().fromJson(this.param, (Class) cls);
    }

    public void setExtParam(Object object) {
        this.extParam = GsonUtils.toJson(object);
    }

    public <T> T getExtParam(Class<T> cls) {
        return (T) GsonUtils.getGson().fromJson(this.extParam, (Class) cls);
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public DeviceState getDeviceState() {
        if (this.deviceState == null) {
            this.deviceState = new DeviceState();
        }
        return this.deviceState;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public void setDeviceState(DeviceState deviceState) {
        this.deviceState = deviceState;
    }

    public boolean isSubDevice() {
        return 2 == this.macfalg;
    }

    public boolean hasIotFun() {
        return (TextUtils.isEmpty(this.platFormDeviceId) || DEFAULT_PLAT_ID.equals(this.platFormDeviceId)) ? false : true;
    }

    public void setUnicastAddress(int unicastAddress) {
        this.unicastAddress = unicastAddress;
    }

    public int getUnicastAddress() {
        return this.unicastAddress;
    }

    public String getFirmwareversion() {
        return this.firmwareversion;
    }

    public void setFirmwareversion(String firmwareversion) {
        this.firmwareversion = firmwareversion;
    }

    public String getMcuversion() {
        return this.mcuversion;
    }

    public void setMcuversion(String mcuversion) {
        this.mcuversion = mcuversion;
    }

    public String getBleversion() {
        String str = this.bleversion;
        return str == null ? "" : str;
    }

    public void setBleversion(String bleversion) {
        this.bleversion = bleversion;
    }

    public int getMinkelvin() {
        return this.minkelvin;
    }

    public void setMinkelvin(int minkelvin) {
        this.minkelvin = minkelvin;
    }

    public int getMaxkelvin() {
        return this.maxkelvin;
    }

    public void setMaxkelvin(int maxkelvin) {
        this.maxkelvin = maxkelvin;
    }

    public String getMaccode() {
        return this.maccode;
    }

    public void setMaccode(String maccode) {
        this.maccode = maccode;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getOauthtype() {
        return this.oauthtype;
    }

    public void setOauthtype(int oauthtype) {
        this.oauthtype = oauthtype;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean equals(Object var1) {
        if (var1 instanceof Device) {
            Device device = (Device) var1;
            return device.getDeviceId() == getDeviceId() && device.getPlaceId() == getPlaceId() && device.getUserId() == getUserId();
        }
        return super.equals(var1);
    }

    public int hashCode() {
        return super.hashCode();
    }

    public void setMainProductId(String mainProductId) {
        this.mainProductId = mainProductId;
    }

    public String getMainProductId() {
        return this.mainProductId;
    }

    public int getSubhide() {
        return this.subhide;
    }

    public void setSubhide(int subhide) {
        this.subhide = subhide;
    }

    public String getBtmodule() {
        String str = this.btmodule;
        return str == null ? "" : str;
    }

    public void setBtmodule(String btmodule) {
        this.btmodule = btmodule;
    }

    public String getReportinstruct() {
        String str = this.reportinstruct;
        return str == null ? "" : str;
    }

    public void setReportinstruct(String reportinstruct) {
        this.reportinstruct = reportinstruct;
    }

    public int getVirtual() {
        return this.virtual;
    }

    public void setVirtual(int virtual) {
        this.virtual = virtual;
    }

    public int getWritable() {
        return this.writable;
    }

    public void setWritable(int writable) {
        this.writable = writable;
    }

    @Override // com.ltech.smarthome.model.bean.Role
    public boolean isVirtual() {
        if (!isSubDevice()) {
            return this.virtual == 1;
        }
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(this.macdeviceid);
        return deviceByDeviceId != null && deviceByDeviceId.virtual == 1;
    }

    public Map<String, KValue> getPresetKValues() {
        if (this.presetKValues == null) {
            this.presetKValues = new HashMap();
        }
        return this.presetKValues;
    }

    public void setPresetKValues(Map<String, KValue> presetKValues) {
        this.presetKValues = presetKValues;
    }

    public String toString() {
        return "Device{id=" + this.id + ", deviceId=" + this.deviceId + ", devicesn='" + this.devicesn + "', userId=" + this.userId + ", floorId=" + this.floorId + ", roomId=" + this.roomId + ", placeId=" + this.placeId + ", wifiMac='" + this.wifiMac + "', productId='" + this.productId + "', hardwareId='" + this.hardwareId + "', onlineFlag=" + this.onlineFlag + ", deviceName='" + this.deviceName + "', macfalg=" + this.macfalg + ", macdeviceid=" + this.macdeviceid + ", deviceState=" + this.deviceState + ", platFormDeviceId='" + this.platFormDeviceId + "', iotDeviceName='" + this.iotDeviceName + "', iotProductKey='" + this.iotProductKey + "', imageIndex=" + this.imageIndex + ", param='" + this.param + "', extParam='" + this.extParam + "', roomName='" + this.roomName + "', floorName='" + this.floorName + "', createtime='" + this.createtime + "', unicastAddress=" + this.unicastAddress + ", checkTime=" + this.checkTime + ", firmwareversion='" + this.firmwareversion + "', mcuversion='" + this.mcuversion + "', bleversion='" + this.bleversion + "', minkelvin=" + this.minkelvin + ", maxkelvin=" + this.maxkelvin + ", maccode='" + this.maccode + "', latitude=" + this.latitude + ", longitude=" + this.longitude + ", oauthtype=" + this.oauthtype + ", mainProductId='" + this.mainProductId + "', subhide=" + this.subhide + ", btmodule='" + this.btmodule + "', panelColor='" + this.panelColor + "', reportinstruct='" + this.reportinstruct + "', index=" + this.index + '}';
    }
}