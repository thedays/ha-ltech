package com.ltech.smarthome.net.response.group;

import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.reflect.TypeToken;
import com.ltech.smarthome.model.bean.KValue;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class ListGroupResponse {
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

    public static class RowsBean {
        private Object begincreatetime;
        private int colorPaletteType;
        private String colorPaletteUrl;
        private String colortype;
        private int createby;
        private Object createbyname;
        private String createtime;
        private List<DevicesBean> devices;
        private Object endcreatetime;
        private long floorid;
        private long groupid;
        private int groupindex;
        private String groupname;
        private int imgindex;
        private String index;
        private String instruct;
        private int leadersup;
        private long macdeviceid;
        private long maingroupid;
        private int maxkelvin;
        private int minkelvin;
        private int pagenum;
        private int pagesize;
        private String param;
        private String paramext;
        private long placeid;
        private Map<String, KValue> presetKValues;
        private String reportinstruct;
        private long roomid;
        private int subhide;
        private int subkey;
        private String type;
        private int updateby;
        private Object updatebyname;

        public int getLeaderSup() {
            return this.leadersup;
        }

        public void setLeaderSup(int leadersup) {
            this.leadersup = leadersup;
        }

        public String getReportinstruct() {
            return this.reportinstruct;
        }

        public void setReportinstruct(String reportinstruct) {
            this.reportinstruct = reportinstruct;
        }

        public String getColorPaletteUrl() {
            return this.colorPaletteUrl;
        }

        public void setColorPaletteUrl(String colorPaletteUrl) {
            this.colorPaletteUrl = colorPaletteUrl;
        }

        public int getColorPaletteType() {
            return this.colorPaletteType;
        }

        public void setColorPaletteType(int colorPaletteType) {
            this.colorPaletteType = colorPaletteType;
        }

        public int getSubhide() {
            return this.subhide;
        }

        public void setSubhide(int subhide) {
            this.subhide = subhide;
        }

        public int getSubkey() {
            return this.subkey;
        }

        public void setSubkey(int subkey) {
            this.subkey = subkey;
        }

        public long getMaingroupid() {
            return this.maingroupid;
        }

        public void setMaingroupid(long maingroupid) {
            this.maingroupid = maingroupid;
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

        public String getInstruct() {
            return this.instruct;
        }

        public void setInstruct(String instruct) {
            this.instruct = instruct;
        }

        public long getFloorid() {
            return this.floorid;
        }

        public void setFloorid(long floorid) {
            this.floorid = floorid;
        }

        public long getRoomid() {
            return this.roomid;
        }

        public void setRoomid(long roomid) {
            this.roomid = roomid;
        }

        public long getGroupid() {
            return this.groupid;
        }

        public void setGroupid(long groupid) {
            this.groupid = groupid;
        }

        public long getPlaceid() {
            return this.placeid;
        }

        public void setPlaceid(long placeid) {
            this.placeid = placeid;
        }

        public String getGroupname() {
            return this.groupname;
        }

        public void setGroupname(String groupname) {
            this.groupname = groupname;
        }

        public String getCreatetime() {
            return this.createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public Object getBegincreatetime() {
            return this.begincreatetime;
        }

        public void setBegincreatetime(Object begincreatetime) {
            this.begincreatetime = begincreatetime;
        }

        public Object getEndcreatetime() {
            return this.endcreatetime;
        }

        public void setEndcreatetime(Object endcreatetime) {
            this.endcreatetime = endcreatetime;
        }

        public int getCreateby() {
            return this.createby;
        }

        public void setCreateby(int createby) {
            this.createby = createby;
        }

        public Object getCreatebyname() {
            return this.createbyname;
        }

        public void setCreatebyname(Object createbyname) {
            this.createbyname = createbyname;
        }

        public int getUpdateby() {
            return this.updateby;
        }

        public void setUpdateby(int updateby) {
            this.updateby = updateby;
        }

        public Object getUpdatebyname() {
            return this.updatebyname;
        }

        public void setUpdatebyname(Object updatebyname) {
            this.updatebyname = updatebyname;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIndex() {
            return this.index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public String getColortype() {
            return this.colortype;
        }

        public void setColortype(String colortype) {
            this.colortype = colortype;
        }

        public int getPagesize() {
            return this.pagesize;
        }

        public void setPagesize(int pagesize) {
            this.pagesize = pagesize;
        }

        public int getPagenum() {
            return this.pagenum;
        }

        public void setPagenum(int pagenum) {
            this.pagenum = pagenum;
        }

        public List<DevicesBean> getDevices() {
            return this.devices;
        }

        public void setDevices(List<DevicesBean> devices) {
            this.devices = devices;
        }

        public int getGroupindex() {
            return this.groupindex;
        }

        public void setGroupindex(int groupindex) {
            this.groupindex = groupindex;
        }

        public String getParam() {
            return this.param;
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

        public long getMacdeviceid() {
            return this.macdeviceid;
        }

        public void setMacdeviceid(long macdeviceid) {
            this.macdeviceid = macdeviceid;
        }

        public Map<String, KValue> getPresetKValues() {
            return this.presetKValues;
        }

        public void setPresetKValues(Map<String, KValue> presetKValues) {
            this.presetKValues = presetKValues;
        }

        public void setPresetKValues(String presetKValues) {
            if (presetKValues != null) {
                this.presetKValues = (Map) GsonUtils.fromJson(presetKValues, new TypeToken<Map<String, KValue>>(this) { // from class: com.ltech.smarthome.net.response.group.ListGroupResponse.RowsBean.1
                }.getType());
            }
        }

        public static class DevicesBean {
            private Object aliasname;
            private Object area;
            private Object areaid;
            private Object begincreatetime;
            private Object beginenabletime;
            private Object beginfaulttime;
            private Object beginlastonlinetime;
            private Object city;
            private Object cityid;
            private Object country;
            private int createby;
            private Object createbyname;
            private Object createtime;
            private long deviceid;
            private int deviceindex;
            private String devicename;
            private Object devicesn;
            private int enable;
            private Object enabletime;
            private Object endcreatetime;
            private Object endenabletime;
            private Object endfaulttime;
            private Object endlastonlinetime;
            private Object faultcode;
            private int faultflag;
            private Object faultremark;
            private Object faulttime;
            private Object firmwareversion;
            private Object icon;
            private Object img;
            private Object imgindex;
            private Object lastonlinetime;
            private double latitude;
            private Object location;
            private double longitude;
            private Object mac;
            private Object macaddress;
            private Object maccode;
            private int macdeviceid;
            private int macfalg;
            private int memberid;
            private Object membername;
            private int onlineflag;
            private int pagenum;
            private int pagesize;
            private Object param;
            private Object paramext;
            private Object platformcode;
            private Object platformdeviceid;
            private Object platformname;
            private int productid;
            private Object province;
            private Object provinceid;
            private Object sysnodecode;
            private Object sysnodename;
            private Object url;

            public long getDeviceid() {
                return this.deviceid;
            }

            public void setDeviceid(long deviceid) {
                this.deviceid = deviceid;
            }

            public Object getDevicesn() {
                return this.devicesn;
            }

            public void setDevicesn(Object devicesn) {
                this.devicesn = devicesn;
            }

            public Object getMac() {
                return this.mac;
            }

            public void setMac(Object mac) {
                this.mac = mac;
            }

            public int getEnable() {
                return this.enable;
            }

            public void setEnable(int enable) {
                this.enable = enable;
            }

            public Object getEnabletime() {
                return this.enabletime;
            }

            public void setEnabletime(Object enabletime) {
                this.enabletime = enabletime;
            }

            public Object getBeginenabletime() {
                return this.beginenabletime;
            }

            public void setBeginenabletime(Object beginenabletime) {
                this.beginenabletime = beginenabletime;
            }

            public Object getEndenabletime() {
                return this.endenabletime;
            }

            public void setEndenabletime(Object endenabletime) {
                this.endenabletime = endenabletime;
            }

            public Object getCreatetime() {
                return this.createtime;
            }

            public void setCreatetime(Object createtime) {
                this.createtime = createtime;
            }

            public Object getBegincreatetime() {
                return this.begincreatetime;
            }

            public void setBegincreatetime(Object begincreatetime) {
                this.begincreatetime = begincreatetime;
            }

            public Object getEndcreatetime() {
                return this.endcreatetime;
            }

            public void setEndcreatetime(Object endcreatetime) {
                this.endcreatetime = endcreatetime;
            }

            public int getCreateby() {
                return this.createby;
            }

            public void setCreateby(int createby) {
                this.createby = createby;
            }

            public Object getCreatebyname() {
                return this.createbyname;
            }

            public void setCreatebyname(Object createbyname) {
                this.createbyname = createbyname;
            }

            public int getOnlineflag() {
                return this.onlineflag;
            }

            public void setOnlineflag(int onlineflag) {
                this.onlineflag = onlineflag;
            }

            public String getDevicename() {
                return this.devicename;
            }

            public void setDevicename(String devicename) {
                this.devicename = devicename;
            }

            public Object getPlatformdeviceid() {
                return this.platformdeviceid;
            }

            public void setPlatformdeviceid(Object platformdeviceid) {
                this.platformdeviceid = platformdeviceid;
            }

            public Object getPlatformcode() {
                return this.platformcode;
            }

            public void setPlatformcode(Object platformcode) {
                this.platformcode = platformcode;
            }

            public Object getPlatformname() {
                return this.platformname;
            }

            public void setPlatformname(Object platformname) {
                this.platformname = platformname;
            }

            public int getMemberid() {
                return this.memberid;
            }

            public void setMemberid(int memberid) {
                this.memberid = memberid;
            }

            public Object getMembername() {
                return this.membername;
            }

            public void setMembername(Object membername) {
                this.membername = membername;
            }

            public Object getAliasname() {
                return this.aliasname;
            }

            public void setAliasname(Object aliasname) {
                this.aliasname = aliasname;
            }

            public int getProductid() {
                return this.productid;
            }

            public void setProductid(int productid) {
                this.productid = productid;
            }

            public Object getParam() {
                return this.param;
            }

            public void setParam(Object param) {
                this.param = param;
            }

            public Object getMacaddress() {
                return this.macaddress;
            }

            public void setMacaddress(Object macaddress) {
                this.macaddress = macaddress;
            }

            public Object getMaccode() {
                return this.maccode;
            }

            public void setMaccode(Object maccode) {
                this.maccode = maccode;
            }

            public Object getLocation() {
                return this.location;
            }

            public void setLocation(Object location) {
                this.location = location;
            }

            public double getLatitude() {
                return this.latitude;
            }

            public void setLatitude(int latitude) {
                this.latitude = latitude;
            }

            public double getLongitude() {
                return this.longitude;
            }

            public void setLongitude(int longitude) {
                this.longitude = longitude;
            }

            public Object getIcon() {
                return this.icon;
            }

            public void setIcon(Object icon) {
                this.icon = icon;
            }

            public int getMacfalg() {
                return this.macfalg;
            }

            public void setMacfalg(int macfalg) {
                this.macfalg = macfalg;
            }

            public int getMacdeviceid() {
                return this.macdeviceid;
            }

            public void setMacdeviceid(int macdeviceid) {
                this.macdeviceid = macdeviceid;
            }

            public Object getProvinceid() {
                return this.provinceid;
            }

            public void setProvinceid(Object provinceid) {
                this.provinceid = provinceid;
            }

            public Object getProvince() {
                return this.province;
            }

            public void setProvince(Object province) {
                this.province = province;
            }

            public Object getCityid() {
                return this.cityid;
            }

            public void setCityid(Object cityid) {
                this.cityid = cityid;
            }

            public Object getCity() {
                return this.city;
            }

            public void setCity(Object city) {
                this.city = city;
            }

            public Object getAreaid() {
                return this.areaid;
            }

            public void setAreaid(Object areaid) {
                this.areaid = areaid;
            }

            public Object getArea() {
                return this.area;
            }

            public void setArea(Object area) {
                this.area = area;
            }

            public Object getLastonlinetime() {
                return this.lastonlinetime;
            }

            public void setLastonlinetime(Object lastonlinetime) {
                this.lastonlinetime = lastonlinetime;
            }

            public Object getBeginlastonlinetime() {
                return this.beginlastonlinetime;
            }

            public void setBeginlastonlinetime(Object beginlastonlinetime) {
                this.beginlastonlinetime = beginlastonlinetime;
            }

            public Object getEndlastonlinetime() {
                return this.endlastonlinetime;
            }

            public void setEndlastonlinetime(Object endlastonlinetime) {
                this.endlastonlinetime = endlastonlinetime;
            }

            public Object getUrl() {
                return this.url;
            }

            public void setUrl(Object url) {
                this.url = url;
            }

            public Object getFirmwareversion() {
                return this.firmwareversion;
            }

            public void setFirmwareversion(Object firmwareversion) {
                this.firmwareversion = firmwareversion;
            }

            public int getFaultflag() {
                return this.faultflag;
            }

            public void setFaultflag(int faultflag) {
                this.faultflag = faultflag;
            }

            public Object getFaultcode() {
                return this.faultcode;
            }

            public void setFaultcode(Object faultcode) {
                this.faultcode = faultcode;
            }

            public Object getFaultremark() {
                return this.faultremark;
            }

            public void setFaultremark(Object faultremark) {
                this.faultremark = faultremark;
            }

            public Object getFaulttime() {
                return this.faulttime;
            }

            public void setFaulttime(Object faulttime) {
                this.faulttime = faulttime;
            }

            public Object getBeginfaulttime() {
                return this.beginfaulttime;
            }

            public void setBeginfaulttime(Object beginfaulttime) {
                this.beginfaulttime = beginfaulttime;
            }

            public Object getEndfaulttime() {
                return this.endfaulttime;
            }

            public void setEndfaulttime(Object endfaulttime) {
                this.endfaulttime = endfaulttime;
            }

            public Object getCountry() {
                return this.country;
            }

            public void setCountry(Object country) {
                this.country = country;
            }

            public Object getSysnodecode() {
                return this.sysnodecode;
            }

            public void setSysnodecode(Object sysnodecode) {
                this.sysnodecode = sysnodecode;
            }

            public Object getSysnodename() {
                return this.sysnodename;
            }

            public void setSysnodename(Object sysnodename) {
                this.sysnodename = sysnodename;
            }

            public Object getImgindex() {
                return this.imgindex;
            }

            public void setImgindex(Object imgindex) {
                this.imgindex = imgindex;
            }

            public Object getImg() {
                return this.img;
            }

            public void setImg(Object img) {
                this.img = img;
            }

            public int getDeviceindex() {
                return this.deviceindex;
            }

            public void setDeviceindex(int deviceindex) {
                this.deviceindex = deviceindex;
            }

            public Object getParamext() {
                return this.paramext;
            }

            public void setParamext(Object paramext) {
                this.paramext = paramext;
            }

            public int getPagesize() {
                return this.pagesize;
            }

            public void setPagesize(int pagesize) {
                this.pagesize = pagesize;
            }

            public int getPagenum() {
                return this.pagenum;
            }

            public void setPagenum(int pagenum) {
                this.pagenum = pagenum;
            }

            public String toString() {
                return "DevicesBean{deviceid=" + this.deviceid + ", devicesn=" + this.devicesn + ", mac=" + this.mac + ", enable=" + this.enable + ", enabletime=" + this.enabletime + ", beginenabletime=" + this.beginenabletime + ", endenabletime=" + this.endenabletime + ", createtime=" + this.createtime + ", begincreatetime=" + this.begincreatetime + ", endcreatetime=" + this.endcreatetime + ", createby=" + this.createby + ", createbyname=" + this.createbyname + ", onlineflag=" + this.onlineflag + ", devicename='" + this.devicename + "', platformdeviceid=" + this.platformdeviceid + ", platformcode=" + this.platformcode + ", platformname=" + this.platformname + ", memberid=" + this.memberid + ", membername=" + this.membername + ", aliasname=" + this.aliasname + ", productid=" + this.productid + ", param=" + this.param + ", macaddress=" + this.macaddress + ", maccode=" + this.maccode + ", location=" + this.location + ", latitude=" + this.latitude + ", longitude=" + this.longitude + ", icon=" + this.icon + ", macfalg=" + this.macfalg + ", macdeviceid=" + this.macdeviceid + ", provinceid=" + this.provinceid + ", province=" + this.province + ", cityid=" + this.cityid + ", city=" + this.city + ", areaid=" + this.areaid + ", area=" + this.area + ", lastonlinetime=" + this.lastonlinetime + ", beginlastonlinetime=" + this.beginlastonlinetime + ", endlastonlinetime=" + this.endlastonlinetime + ", url=" + this.url + ", firmwareversion=" + this.firmwareversion + ", faultflag=" + this.faultflag + ", faultcode=" + this.faultcode + ", faultremark=" + this.faultremark + ", faulttime=" + this.faulttime + ", beginfaulttime=" + this.beginfaulttime + ", endfaulttime=" + this.endfaulttime + ", country=" + this.country + ", sysnodecode=" + this.sysnodecode + ", sysnodename=" + this.sysnodename + ", imgindex=" + this.imgindex + ", img=" + this.img + ", deviceindex=" + this.deviceindex + ", paramext=" + this.paramext + ", pagesize=" + this.pagesize + ", pagenum=" + this.pagenum + '}';
            }
        }

        public String toString() {
            return "RowsBean{groupid=" + this.groupid + ", placeid=" + this.placeid + ", groupname='" + this.groupname + "', createtime='" + this.createtime + "', begincreatetime=" + this.begincreatetime + ", endcreatetime=" + this.endcreatetime + ", createby=" + this.createby + ", createbyname=" + this.createbyname + ", updateby=" + this.updateby + ", updatebyname=" + this.updatebyname + ", type='" + this.type + "', index='" + this.index + "', colortype='" + this.colortype + "', groupindex=" + this.groupindex + ", pagesize=" + this.pagesize + ", pagenum=" + this.pagenum + ", devices=" + this.devices + ", paramext='" + this.paramext + "', floorid=" + this.floorid + ", roomid=" + this.roomid + ", instruct='" + this.instruct + "'}";
        }
    }

    public String toString() {
        return "ListGroupResponse{total=" + this.total + ", rows=" + this.rows + '}';
    }
}