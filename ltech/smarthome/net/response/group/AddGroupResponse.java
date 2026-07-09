package com.ltech.smarthome.net.response.group;

import java.util.List;

/* loaded from: classes4.dex */
public class AddGroupResponse {
    private Object begincreatetime;
    private String colortype;
    private int createby;
    private Object createbyname;
    private String createtime;
    private List<DevicesBean> devices;
    private Object endcreatetime;
    private long groupid;
    private int groupindex;
    private String groupname;
    private String index;
    private int pagenum;
    private int pagesize;
    private String param;
    private String paramext;
    private long placeid;
    private String type;
    private int updateby;
    private Object updatebyname;

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
        private String createtime;
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
        private String mac;
        private Object macaddress;
        private String maccode;
        private int macdeviceid;
        private int macfalg;
        private long memberid;
        private String membername;
        private int onlineflag;
        private int pagenum;
        private int pagesize;
        private Object param;
        private Object paramext;
        private String platformcode;
        private String platformdeviceid;
        private String platformname;
        private long productid;
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

        public String getMac() {
            return this.mac;
        }

        public void setMac(String mac) {
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

        public String getPlatformdeviceid() {
            return this.platformdeviceid;
        }

        public void setPlatformdeviceid(String platformdeviceid) {
            this.platformdeviceid = platformdeviceid;
        }

        public String getPlatformcode() {
            return this.platformcode;
        }

        public void setPlatformcode(String platformcode) {
            this.platformcode = platformcode;
        }

        public String getPlatformname() {
            return this.platformname;
        }

        public void setPlatformname(String platformname) {
            this.platformname = platformname;
        }

        public long getMemberid() {
            return this.memberid;
        }

        public void setMemberid(long memberid) {
            this.memberid = memberid;
        }

        public String getMembername() {
            return this.membername;
        }

        public void setMembername(String membername) {
            this.membername = membername;
        }

        public Object getAliasname() {
            return this.aliasname;
        }

        public void setAliasname(Object aliasname) {
            this.aliasname = aliasname;
        }

        public long getProductid() {
            return this.productid;
        }

        public void setProductid(long productid) {
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

        public String getMaccode() {
            return this.maccode;
        }

        public void setMaccode(String maccode) {
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
    }
}