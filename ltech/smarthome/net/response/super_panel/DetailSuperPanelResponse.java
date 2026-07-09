package com.ltech.smarthome.net.response.super_panel;

import java.util.List;

/* loaded from: classes4.dex */
public class DetailSuperPanelResponse {
    private ContentsBean contents;
    private DevicesBean devices;
    private GroupsBean groups;
    private InfoBean info;
    private ScenesBean scenes;

    public DevicesBean getDevices() {
        return this.devices;
    }

    public void setDevices(DevicesBean devices) {
        this.devices = devices;
    }

    public ContentsBean getContents() {
        return this.contents;
    }

    public void setContents(ContentsBean contents) {
        this.contents = contents;
    }

    public ScenesBean getScenes() {
        return this.scenes;
    }

    public void setScenes(ScenesBean scenes) {
        this.scenes = scenes;
    }

    public GroupsBean getGroups() {
        return this.groups;
    }

    public void setGroups(GroupsBean groups) {
        this.groups = groups;
    }

    public InfoBean getInfo() {
        return this.info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class DevicesBean {
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
            private String connecttype;
            private String connecttypename;
            private int createby;
            private String createtime;
            private long deviceid;
            private int deviceindex;
            private String devicename;
            private int enable;
            private int faultflag;
            private double latitude;
            private double longitude;
            private String mac;
            private String maccode;
            private long macdeviceid;
            private int macfalg;
            private int maxkelvin;
            private long memberid;
            private String membername;
            private int minkelvin;
            private int onlineflag;
            private long panelid;
            private String param;
            private String paramext;
            private String platformcode;
            private String platformdeviceid;
            private String platformname;
            private long productid;
            private String productname;
            private String producttype;
            private String producttypename;
            private int sorting;
            private String sysnodecode;
            private String sysnodename;
            private String url;

            public int getMaxkelvin() {
                return this.maxkelvin;
            }

            public void setMaxkelvin(int maxkelvin) {
                this.maxkelvin = maxkelvin;
            }

            public int getMinkelvin() {
                return this.minkelvin;
            }

            public void setMinkelvin(int minkelvin) {
                this.minkelvin = minkelvin;
            }

            public String getParamext() {
                return this.paramext;
            }

            public void setParamext(String paramext) {
                this.paramext = paramext;
            }

            public long getProductid() {
                return this.productid;
            }

            public void setProductid(long productid) {
                this.productid = productid;
            }

            public double getLatitude() {
                return this.latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public long getMacdeviceid() {
                return this.macdeviceid;
            }

            public void setMacdeviceid(long macdeviceid) {
                this.macdeviceid = macdeviceid;
            }

            public String getMac() {
                return this.mac;
            }

            public void setMac(String mac) {
                this.mac = mac;
            }

            public String getProducttype() {
                return this.producttype;
            }

            public void setProducttype(String producttype) {
                this.producttype = producttype;
            }

            public int getCreateby() {
                return this.createby;
            }

            public void setCreateby(int createby) {
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

            public String getSysnodecode() {
                return this.sysnodecode;
            }

            public void setSysnodecode(String sysnodecode) {
                this.sysnodecode = sysnodecode;
            }

            public String getParam() {
                return this.param;
            }

            public void setParam(String param) {
                this.param = param;
            }

            public String getProducttypename() {
                return this.producttypename;
            }

            public void setProducttypename(String producttypename) {
                this.producttypename = producttypename;
            }

            public int getEnable() {
                return this.enable;
            }

            public void setEnable(int enable) {
                this.enable = enable;
            }

            public String getProductname() {
                return this.productname;
            }

            public void setProductname(String productname) {
                this.productname = productname;
            }

            public int getOnlineflag() {
                return this.onlineflag;
            }

            public void setOnlineflag(int onlineflag) {
                this.onlineflag = onlineflag;
            }

            public double getLongitude() {
                return this.longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }

            public String getCreatetime() {
                return this.createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public long getPanelid() {
                return this.panelid;
            }

            public void setPanelid(long panelid) {
                this.panelid = panelid;
            }

            public int getFaultflag() {
                return this.faultflag;
            }

            public void setFaultflag(int faultflag) {
                this.faultflag = faultflag;
            }

            public String getConnecttypename() {
                return this.connecttypename;
            }

            public void setConnecttypename(String connecttypename) {
                this.connecttypename = connecttypename;
            }

            public String getPlatformcode() {
                return this.platformcode;
            }

            public void setPlatformcode(String platformcode) {
                this.platformcode = platformcode;
            }

            public long getDeviceid() {
                return this.deviceid;
            }

            public void setDeviceid(long deviceid) {
                this.deviceid = deviceid;
            }

            public String getUrl() {
                return this.url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getPlatformname() {
                return this.platformname;
            }

            public void setPlatformname(String platformname) {
                this.platformname = platformname;
            }

            public int getDeviceindex() {
                return this.deviceindex;
            }

            public void setDeviceindex(int deviceindex) {
                this.deviceindex = deviceindex;
            }

            public String getConnecttype() {
                return this.connecttype;
            }

            public void setConnecttype(String connecttype) {
                this.connecttype = connecttype;
            }

            public int getMacfalg() {
                return this.macfalg;
            }

            public void setMacfalg(int macfalg) {
                this.macfalg = macfalg;
            }

            public String getSysnodename() {
                return this.sysnodename;
            }

            public void setSysnodename(String sysnodename) {
                this.sysnodename = sysnodename;
            }

            public String getDevicename() {
                return this.devicename;
            }

            public void setDevicename(String devicename) {
                this.devicename = devicename;
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

            public int getSorting() {
                return this.sorting;
            }

            public void setSorting(int sorting) {
                this.sorting = sorting;
            }
        }
    }

    public static class ContentsBean {
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
            private long actionid;
            private int actiontype;
            private Object begincreatetime;
            private Object beginupdatetime;
            private Object createby;
            private Object createbyname;
            private String createtime;
            private Object endcreatetime;
            private Object endupdatetime;
            private String executecommand;
            private int index;
            private int keywords;
            private String keywordsname;
            private int keywordstype;
            private int pagenum;
            private int pagesize;
            private long panelid;
            private long panelinfoid;
            private String timespace;
            private Object updateby;
            private Object updatebyname;
            private Object updatetime;

            public long getPanelinfoid() {
                return this.panelinfoid;
            }

            public void setPanelinfoid(long panelinfoid) {
                this.panelinfoid = panelinfoid;
            }

            public long getPanelid() {
                return this.panelid;
            }

            public void setPanelid(long panelid) {
                this.panelid = panelid;
            }

            public int getActiontype() {
                return this.actiontype;
            }

            public void setActiontype(int actiontype) {
                this.actiontype = actiontype;
            }

            public long getActionid() {
                return this.actionid;
            }

            public void setActionid(long actionid) {
                this.actionid = actionid;
            }

            public Object getCreateby() {
                return this.createby;
            }

            public void setCreateby(Object createby) {
                this.createby = createby;
            }

            public Object getCreatebyname() {
                return this.createbyname;
            }

            public void setCreatebyname(Object createbyname) {
                this.createbyname = createbyname;
            }

            public Object getUpdateby() {
                return this.updateby;
            }

            public void setUpdateby(Object updateby) {
                this.updateby = updateby;
            }

            public Object getUpdatebyname() {
                return this.updatebyname;
            }

            public void setUpdatebyname(Object updatebyname) {
                this.updatebyname = updatebyname;
            }

            public Object getUpdatetime() {
                return this.updatetime;
            }

            public void setUpdatetime(Object updatetime) {
                this.updatetime = updatetime;
            }

            public Object getBeginupdatetime() {
                return this.beginupdatetime;
            }

            public void setBeginupdatetime(Object beginupdatetime) {
                this.beginupdatetime = beginupdatetime;
            }

            public Object getEndupdatetime() {
                return this.endupdatetime;
            }

            public void setEndupdatetime(Object endupdatetime) {
                this.endupdatetime = endupdatetime;
            }

            public int getKeywords() {
                return this.keywords;
            }

            public void setKeywords(int keywords) {
                this.keywords = keywords;
            }

            public int getKeywordstype() {
                return this.keywordstype;
            }

            public void setKeywordstype(int keywordstype) {
                this.keywordstype = keywordstype;
            }

            public String getExecutecommand() {
                return this.executecommand;
            }

            public void setExecutecommand(String executecommand) {
                this.executecommand = executecommand;
            }

            public int getIndex() {
                return this.index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public String getTimespace() {
                return this.timespace;
            }

            public void setTimespace(String timespace) {
                this.timespace = timespace;
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

            public String getKeywordsname() {
                return this.keywordsname;
            }

            public void setKeywordsname(String keywordsname) {
                this.keywordsname = keywordsname;
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

    public static class ScenesBean {
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
            private long actionid;
            private int actiontype;
            private String createtime;
            private String img;
            private String imgindex;
            private int index;
            private int keywords;
            private int keywordstype;
            private long panelid;
            private long panelinfoid;
            private long placeid;
            private long sceneid;
            private int sceneindex;
            private String scenename;
            private int scenenum;
            private int scenetype;
            private int sorting;

            public int getScenenum() {
                return this.scenenum;
            }

            public void setScenenum(int scenenum) {
                this.scenenum = scenenum;
            }

            public int getScenetype() {
                return this.scenetype;
            }

            public void setScenetype(int scenetype) {
                this.scenetype = scenetype;
            }

            public int getActiontype() {
                return this.actiontype;
            }

            public void setActiontype(int actiontype) {
                this.actiontype = actiontype;
            }

            public long getPanelinfoid() {
                return this.panelinfoid;
            }

            public void setPanelinfoid(long panelinfoid) {
                this.panelinfoid = panelinfoid;
            }

            public long getPanelid() {
                return this.panelid;
            }

            public void setPanelid(long panelid) {
                this.panelid = panelid;
            }

            public String getCreatetime() {
                return this.createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public String getImg() {
                return this.img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getKeywords() {
                return this.keywords;
            }

            public void setKeywords(int keywords) {
                this.keywords = keywords;
            }

            public long getPlaceid() {
                return this.placeid;
            }

            public void setPlaceid(long placeid) {
                this.placeid = placeid;
            }

            public String getScenename() {
                return this.scenename;
            }

            public void setScenename(String scenename) {
                this.scenename = scenename;
            }

            public int getIndex() {
                return this.index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public int getSceneindex() {
                return this.sceneindex;
            }

            public void setSceneindex(int sceneindex) {
                this.sceneindex = sceneindex;
            }

            public String getImgindex() {
                return this.imgindex;
            }

            public void setImgindex(String imgindex) {
                this.imgindex = imgindex;
            }

            public long getSceneid() {
                return this.sceneid;
            }

            public void setSceneid(long sceneid) {
                this.sceneid = sceneid;
            }

            public long getActionid() {
                return this.actionid;
            }

            public void setActionid(long actionid) {
                this.actionid = actionid;
            }

            public int getKeywordstype() {
                return this.keywordstype;
            }

            public void setKeywordstype(int keywordstype) {
                this.keywordstype = keywordstype;
            }

            public int getSorting() {
                return this.sorting;
            }

            public void setSorting(int sorting) {
                this.sorting = sorting;
            }
        }
    }

    public static class GroupsBean {
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
            private long actionid;
            private int actiontype;
            private int colortype;
            private String createtime;
            private int groupAddress;
            private long groupid;
            private String groupindex;
            private String groupname;
            private int index;
            private int keywords;
            private int keywordstype;
            private long maingroupid;
            private int maxkelvin;
            private int minkelvin;
            private long panelid;
            private long panelinfoid;
            private String paramext;
            private long placeid;
            private int sorting;
            private int subkey;

            public String getParamext() {
                return this.paramext;
            }

            public void setParamext(String paramext) {
                this.paramext = paramext;
            }

            public String getGroupindex() {
                return this.groupindex;
            }

            public void setGroupindex(String groupindex) {
                this.groupindex = groupindex;
            }

            public int getGroupAddress() {
                return this.groupAddress;
            }

            public void setGroupAddress(int groupAddress) {
                this.groupAddress = groupAddress;
            }

            public int getMaxkelvin() {
                int i = this.maxkelvin;
                if (i == 0) {
                    return 6500;
                }
                return i;
            }

            public void setMaxkelvin(int maxkelvin) {
                this.maxkelvin = maxkelvin;
            }

            public int getMinkelvin() {
                int i = this.minkelvin;
                if (i == 0) {
                    return 2700;
                }
                return i;
            }

            public void setMinkelvin(int minkelvin) {
                this.minkelvin = minkelvin;
            }

            public int getColortype() {
                return this.colortype;
            }

            public void setColortype(int colortype) {
                this.colortype = colortype;
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

            public int getActiontype() {
                return this.actiontype;
            }

            public void setActiontype(int actiontype) {
                this.actiontype = actiontype;
            }

            public long getPanelinfoid() {
                return this.panelinfoid;
            }

            public void setPanelinfoid(long panelinfoid) {
                this.panelinfoid = panelinfoid;
            }

            public long getPanelid() {
                return this.panelid;
            }

            public void setPanelid(long panelid) {
                this.panelid = panelid;
            }

            public String getCreatetime() {
                return this.createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public int getKeywords() {
                return this.keywords;
            }

            public void setKeywords(int keywords) {
                this.keywords = keywords;
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

            public long getActionid() {
                return this.actionid;
            }

            public void setActionid(long actionid) {
                this.actionid = actionid;
            }

            public int getIndex() {
                return this.index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public int getKeywordstype() {
                return this.keywordstype;
            }

            public void setKeywordstype(int keywordstype) {
                this.keywordstype = keywordstype;
            }

            public String getGroupname() {
                return this.groupname;
            }

            public void setGroupname(String groupname) {
                this.groupname = groupname;
            }

            public int getSorting() {
                return this.sorting;
            }

            public void setSorting(int sorting) {
                this.sorting = sorting;
            }
        }
    }

    public static class InfoBean {
        private String applicationkey;
        private String apppackage;
        private long areadeviceid;
        private String connecttype;
        private String connecttypename;
        private int createby;
        private String createtime;
        private long deviceid;
        private int deviceindex;
        private String devicename;
        private long deviceroleid;
        private int enable;
        private int faultflag;
        private long floorid;
        private String lastfirmwareversion;
        private String lastmcuversion;
        private double latitude;
        private double longitude;
        private String mac;
        private String maccode;
        private int macdeviceid;
        private int macfalg;
        private int memberid;
        private String membername;
        private String meshuuid;
        private String netkey;
        private int onlineflag;
        private long panelid;
        private String panelname;
        private long placeid;
        private String platformcode;
        private String platformdeviceid;
        private String platformname;
        private long productid;
        private String productname;
        private String producttype;
        private String producttypename;
        private String provisioner;
        private int roletype;
        private String roletypename;
        private long roomid;
        private String roomname;
        private String sysnodecode;
        private String sysnodename;
        private String url;
        private long userid;
        private int version;
        private String versionname;
        private int voiceactive;

        public String getLastfirmwareversion() {
            return this.lastfirmwareversion;
        }

        public void setLastfirmwareversion(String lastfirmwareversion) {
            this.lastfirmwareversion = lastfirmwareversion;
        }

        public String getLastmcuversion() {
            return this.lastmcuversion;
        }

        public void setLastmcuversion(String lastmcuversion) {
            this.lastmcuversion = lastmcuversion;
        }

        public long getProductid() {
            return this.productid;
        }

        public void setProductid(long productid) {
            this.productid = productid;
        }

        public double getLatitude() {
            return this.latitude;
        }

        public void setLatitude(int latitude) {
            this.latitude = latitude;
        }

        public int getMacdeviceid() {
            return this.macdeviceid;
        }

        public void setMacdeviceid(int macdeviceid) {
            this.macdeviceid = macdeviceid;
        }

        public String getRoletypename() {
            return this.roletypename;
        }

        public void setRoletypename(String roletypename) {
            this.roletypename = roletypename;
        }

        public String getApplicationkey() {
            return this.applicationkey;
        }

        public void setApplicationkey(String applicationkey) {
            this.applicationkey = applicationkey;
        }

        public String getMeshuuid() {
            return this.meshuuid;
        }

        public void setMeshuuid(String meshuuid) {
            this.meshuuid = meshuuid;
        }

        public long getUserid() {
            return this.userid;
        }

        public void setUserid(long userid) {
            this.userid = userid;
        }

        public String getMac() {
            return this.mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public String getProducttype() {
            return this.producttype;
        }

        public void setProducttype(String producttype) {
            this.producttype = producttype;
        }

        public String getRoomname() {
            return this.roomname;
        }

        public void setRoomname(String roomname) {
            this.roomname = roomname;
        }

        public int getCreateby() {
            return this.createby;
        }

        public void setCreateby(int createby) {
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

        public String getSysnodecode() {
            return this.sysnodecode;
        }

        public void setSysnodecode(String sysnodecode) {
            this.sysnodecode = sysnodecode;
        }

        public String getProducttypename() {
            return this.producttypename;
        }

        public void setProducttypename(String producttypename) {
            this.producttypename = producttypename;
        }

        public long getAreadeviceid() {
            return this.areadeviceid;
        }

        public void setAreadeviceid(long areadeviceid) {
            this.areadeviceid = areadeviceid;
        }

        public int getEnable() {
            return this.enable;
        }

        public void setEnable(int enable) {
            this.enable = enable;
        }

        public String getNetkey() {
            return this.netkey;
        }

        public void setNetkey(String netkey) {
            this.netkey = netkey;
        }

        public String getProductname() {
            return this.productname;
        }

        public void setProductname(String productname) {
            this.productname = productname;
        }

        public int getRoletype() {
            return this.roletype;
        }

        public void setRoletype(int roletype) {
            this.roletype = roletype;
        }

        public int getOnlineflag() {
            return this.onlineflag;
        }

        public void setOnlineflag(int onlineflag) {
            this.onlineflag = onlineflag;
        }

        public double getLongitude() {
            return this.longitude;
        }

        public void setLongitude(int longitude) {
            this.longitude = longitude;
        }

        public String getPanelname() {
            return this.panelname;
        }

        public void setPanelname(String panelname) {
            this.panelname = panelname;
        }

        public String getCreatetime() {
            return this.createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public long getPanelid() {
            return this.panelid;
        }

        public void setPanelid(long panelid) {
            this.panelid = panelid;
        }

        public String getProvisioner() {
            return this.provisioner;
        }

        public void setProvisioner(String provisioner) {
            this.provisioner = provisioner;
        }

        public int getFaultflag() {
            return this.faultflag;
        }

        public void setFaultflag(int faultflag) {
            this.faultflag = faultflag;
        }

        public String getApppackage() {
            return this.apppackage;
        }

        public void setApppackage(String apppackage) {
            this.apppackage = apppackage;
        }

        public long getPlaceid() {
            return this.placeid;
        }

        public void setPlaceid(long placeid) {
            this.placeid = placeid;
        }

        public String getConnecttypename() {
            return this.connecttypename;
        }

        public void setConnecttypename(String connecttypename) {
            this.connecttypename = connecttypename;
        }

        public String getPlatformcode() {
            return this.platformcode;
        }

        public void setPlatformcode(String platformcode) {
            this.platformcode = platformcode;
        }

        public long getDeviceroleid() {
            return this.deviceroleid;
        }

        public void setDeviceroleid(long deviceroleid) {
            this.deviceroleid = deviceroleid;
        }

        public long getDeviceid() {
            return this.deviceid;
        }

        public void setDeviceid(long deviceid) {
            this.deviceid = deviceid;
        }

        public int getVersion() {
            return this.version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
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

        public int getDeviceindex() {
            return this.deviceindex;
        }

        public void setDeviceindex(int deviceindex) {
            this.deviceindex = deviceindex;
        }

        public long getFloorid() {
            return this.floorid;
        }

        public void setFloorid(long floorid) {
            this.floorid = floorid;
        }

        public String getConnecttype() {
            return this.connecttype;
        }

        public void setConnecttype(String connecttype) {
            this.connecttype = connecttype;
        }

        public int getMacfalg() {
            return this.macfalg;
        }

        public void setMacfalg(int macfalg) {
            this.macfalg = macfalg;
        }

        public String getSysnodename() {
            return this.sysnodename;
        }

        public void setSysnodename(String sysnodename) {
            this.sysnodename = sysnodename;
        }

        public String getDevicename() {
            return this.devicename;
        }

        public void setDevicename(String devicename) {
            this.devicename = devicename;
        }

        public String getVersionname() {
            return this.versionname;
        }

        public void setVersionname(String versionname) {
            this.versionname = versionname;
        }

        public String getMembername() {
            return this.membername;
        }

        public void setMembername(String membername) {
            this.membername = membername;
        }

        public int getMemberid() {
            return this.memberid;
        }

        public void setMemberid(int memberid) {
            this.memberid = memberid;
        }

        public int getVoiceactive() {
            return this.voiceactive;
        }

        public void setVoiceactive(int voiceactive) {
            this.voiceactive = voiceactive;
        }
    }
}