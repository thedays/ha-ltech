package com.ltech.smarthome.net.response.device;

/* loaded from: classes4.dex */
public class AddDeviceResponse {
    private Object aliasname;
    private Object begincreatetime;
    private Object beginenabletime;
    private int createby;
    private Object createbyname;
    private String createtime;
    private long deviceid;
    private String devicename;
    private String devicesn;
    private int enable;
    private Object enabletime;
    private Object endcreatetime;
    private Object endenabletime;
    private Object icon;
    private int imgindex;
    private double latitude;
    private Object location;
    private double longitude;
    private String mac;
    private Object macaddress;
    private String maccode;
    private long macdeviceid;
    private int macfalg;
    private long memberid;
    private String membername;
    private int onlineflag;
    private int pagenum;
    private int pagesize;
    private String param;
    private String paramext;
    private String platformcode;
    private String platformdeviceid;
    private String platformname;
    private String productid;

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

    public String getProductid() {
        return this.productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getParam() {
        return this.param;
    }

    public void setParam(String param) {
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

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
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

    public long getMacdeviceid() {
        return this.macdeviceid;
    }

    public void setMacdeviceid(long macdeviceid) {
        this.macdeviceid = macdeviceid;
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

    public String toString() {
        return "AddDeviceResponse{deviceid=" + this.deviceid + ", devicesn='" + this.devicesn + "', mac='" + this.mac + "', enable=" + this.enable + ", enabletime=" + this.enabletime + ", beginenabletime=" + this.beginenabletime + ", endenabletime=" + this.endenabletime + ", createtime='" + this.createtime + "', begincreatetime=" + this.begincreatetime + ", endcreatetime=" + this.endcreatetime + ", createby=" + this.createby + ", createbyname=" + this.createbyname + ", onlineflag=" + this.onlineflag + ", devicename='" + this.devicename + "', platformdeviceid='" + this.platformdeviceid + "', platformcode='" + this.platformcode + "', platformname='" + this.platformname + "', memberid=" + this.memberid + ", membername='" + this.membername + "', aliasname=" + this.aliasname + ", productid='" + this.productid + "', param='" + this.param + "', macaddress=" + this.macaddress + ", maccode='" + this.maccode + "', location=" + this.location + ", latitude=" + this.latitude + ", longitude=" + this.longitude + ", icon=" + this.icon + ", macfalg=" + this.macfalg + ", macdeviceid=" + this.macdeviceid + ", pagesize=" + this.pagesize + ", pagenum=" + this.pagenum + ", extParam='" + this.paramext + "'}";
    }
}