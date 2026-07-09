package com.ltech.smarthome.net.response.device;

import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.model.bean.KValue;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class ListDeviceResponse {
    private List<RowsBean> rows;
    private int total;

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return this.rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public String toString() {
        return "ListDeviceResponse{total=" + this.total + ", rows=" + this.rows + '}';
    }

    public static class RowsBean {
        private String aipuducttype;
        private String bleversion;
        private String btmodule;
        private long createby;
        private String createtime;
        private long deviceid;
        private int deviceindex;
        private String devicename;
        private String devicesn;
        private int enable;
        private String firmwareversion;
        private long floorid;
        private String floorname;
        private int imgindex;
        private float latitude;
        private float longitude;
        private String mac;
        private String maccode;
        private long macdeviceid;
        private int macfalg;
        private int maxkelvin;
        private String mcuversion;
        private long memberid;
        private String membername;
        private int minkelvin;
        private int oauthtype;
        private int onlineflag;
        private String panelColor;
        private String param;
        private String paramext;
        private long placeid;
        private String placename;
        private String platformcode;
        private String platformdeviceid;
        private String platformname;
        private Map<String, KValue> presetKValues;
        private String productid;
        private String producttypename;
        private String reportinstruct;
        private long roomid;
        private String roomname;
        private int status = 999;
        private int subhide;
        private int virtual;
        private int writable;

        public int getSubhide() {
            return this.subhide;
        }

        public void setSubhide(int subhide) {
            this.subhide = subhide;
        }

        public String getBtmodule() {
            return this.btmodule;
        }

        public void setBtmodule(String btmodule) {
            this.btmodule = btmodule;
        }

        public String getBleversion() {
            return this.bleversion;
        }

        public void setBleversion(String bleversion) {
            this.bleversion = bleversion;
        }

        public int getDeviceindex() {
            return this.deviceindex;
        }

        public void setDeviceindex(int deviceindex) {
            this.deviceindex = deviceindex;
        }

        public String getRoomname() {
            return this.roomname;
        }

        public void setRoomname(String roomname) {
            this.roomname = roomname;
        }

        public String getCreatetime() {
            return this.createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getProductid() {
            return this.productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }

        public float getLatitude() {
            return this.latitude;
        }

        public void setLatitude(float latitude) {
            this.latitude = latitude;
        }

        public long getMacdeviceid() {
            return this.macdeviceid;
        }

        public void setMacdeviceid(long macdeviceid) {
            this.macdeviceid = macdeviceid;
        }

        public long getPlaceid() {
            return this.placeid;
        }

        public void setPlaceid(long placeid) {
            this.placeid = placeid;
        }

        public String getPlatformcode() {
            return this.platformcode;
        }

        public void setPlatformcode(String platformcode) {
            this.platformcode = platformcode;
        }

        public String getPlacename() {
            return this.placename;
        }

        public void setPlacename(String placename) {
            this.placename = placename;
        }

        public long getDeviceid() {
            return this.deviceid;
        }

        public void setDeviceid(long deviceid) {
            this.deviceid = deviceid;
        }

        public String getDevicesn() {
            return this.devicesn;
        }

        public void setDevicesn(String devicesn) {
            this.devicesn = devicesn;
        }

        public String getFloorname() {
            return this.floorname;
        }

        public void setFloorname(String floorname) {
            this.floorname = floorname;
        }

        public String getMac() {
            return this.mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public long getRoomid() {
            return this.roomid;
        }

        public void setRoomid(long roomid) {
            this.roomid = roomid;
        }

        public String getPlatformname() {
            return this.platformname;
        }

        public void setPlatformname(String platformname) {
            this.platformname = platformname;
        }

        public long getFloorid() {
            return this.floorid;
        }

        public void setFloorid(long floorid) {
            this.floorid = floorid;
        }

        public long getCreateby() {
            return this.createby;
        }

        public void setCreateby(long createby) {
            this.createby = createby;
        }

        public String getMaccode() {
            return this.maccode;
        }

        public void setMaccode(String maccode) {
            this.maccode = maccode;
        }

        public String getPlatformdeviceid() {
            return this.platformdeviceid;
        }

        public void setPlatformdeviceid(String platformdeviceid) {
            this.platformdeviceid = platformdeviceid;
        }

        public int getMacfalg() {
            return this.macfalg;
        }

        public void setMacfalg(int macfalg) {
            this.macfalg = macfalg;
        }

        public int getEnable() {
            return this.enable;
        }

        public void setEnable(int enable) {
            this.enable = enable;
        }

        public String getDevicename() {
            return this.devicename;
        }

        public void setDevicename(String devicename) {
            this.devicename = devicename;
        }

        public int getOnlineflag() {
            return this.onlineflag;
        }

        public void setOnlineflag(int onlineflag) {
            this.onlineflag = onlineflag;
        }

        public String getMembername() {
            return this.membername;
        }

        public void setMembername(String membername) {
            this.membername = membername;
        }

        public long getMemberid() {
            return this.memberid;
        }

        public void setMemberid(long memberid) {
            this.memberid = memberid;
        }

        public float getLongitude() {
            return this.longitude;
        }

        public void setLongitude(float longitude) {
            this.longitude = longitude;
        }

        public String getParam() {
            return this.param;
        }

        public <T> T getParam(Class<T> cls) {
            return (T) GsonUtils.getGson().fromJson(this.param, (Class) cls);
        }

        public void setParam(String param) {
            this.param = param;
        }

        public String getParamext() {
            return this.paramext;
        }

        public void setParamext(String paramext) {
            this.paramext = paramext;
        }

        public int getImgindex() {
            return this.imgindex;
        }

        public void setImgindex(int imgindex) {
            this.imgindex = imgindex;
        }

        public String getAipuducttype() {
            return this.aipuducttype;
        }

        public void setAipuducttype(String aipuducttype) {
            this.aipuducttype = aipuducttype;
        }

        public String getProducttypename() {
            return this.producttypename;
        }

        public void setProducttypename(String producttypename) {
            this.producttypename = producttypename;
        }

        public void setMcuversion(String mcuversion) {
            this.mcuversion = mcuversion;
        }

        public String getMcuversion() {
            return this.mcuversion;
        }

        public void setFirmwareversion(String firmwareversion) {
            this.firmwareversion = firmwareversion;
        }

        public String getFirmwareversion() {
            return this.firmwareversion;
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

        public int getOauthtype() {
            return this.oauthtype;
        }

        public void setOauthtype(int oauthtype) {
            this.oauthtype = oauthtype;
        }

        public String getPanelColor() {
            return this.panelColor;
        }

        public void setPanelColor(String panelColor) {
            this.panelColor = panelColor;
        }

        public String getReportinstruct() {
            return this.reportinstruct;
        }

        public void setReportinstruct(String reportinstruct) {
            this.reportinstruct = reportinstruct;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status) {
            this.status = status;
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

        public Map<String, KValue> getPresetKValues() {
            return this.presetKValues;
        }

        public void setPresetKValues(Map<String, KValue> presetKValues) {
            this.presetKValues = presetKValues;
        }

        public String toString() {
            return "RowsBean{createtime='" + this.createtime + "', productid='" + this.productid + "', latitude=" + this.latitude + ", macdeviceid=" + this.macdeviceid + ", placeid=" + this.placeid + ", platformcode='" + this.platformcode + "', placename='" + this.placename + "', deviceid=" + this.deviceid + ", devicesn='" + this.devicesn + "', floorname='" + this.floorname + "', mac='" + this.mac + "', roomid=" + this.roomid + ", platformname='" + this.platformname + "', floorid=" + this.floorid + ", createby=" + this.createby + ", maccode='" + this.maccode + "', platformdeviceid='" + this.platformdeviceid + "', macfalg=" + this.macfalg + ", enable=" + this.enable + ", devicename='" + this.devicename + "', onlineflag=" + this.onlineflag + ", membername='" + this.membername + "', memberid=" + this.memberid + ", longitude=" + this.longitude + ", imgindex=" + this.imgindex + ", param='" + this.param + "', paramext='" + this.paramext + "', aipuducttype='" + this.aipuducttype + "', producttypename='" + this.producttypename + "', firmwareversion='" + this.firmwareversion + "', mcuversion='" + this.mcuversion + "', minkelvin=" + this.minkelvin + ", maxkelvin=" + this.maxkelvin + ", bleversion='" + this.bleversion + "', oauthtype=" + this.oauthtype + ", btmodule='" + this.btmodule + "', subhide=" + this.subhide + ", deviceindex=" + this.deviceindex + ", roomname='" + this.roomname + "'}";
        }
    }
}