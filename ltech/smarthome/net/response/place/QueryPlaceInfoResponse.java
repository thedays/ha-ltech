package com.ltech.smarthome.net.response.place;

import com.ltech.smarthome.net.response.device.ListDeviceResponse;
import java.util.List;

/* loaded from: classes4.dex */
public class QueryPlaceInfoResponse {
    private ListDeviceResponse devices;
    private FloorsBean floors;
    private GroupsBean groups;
    private InfoBean info;
    private RoomsBean rooms;
    private UsersBean users;

    public RoomsBean getRooms() {
        return this.rooms;
    }

    public void setRooms(RoomsBean rooms) {
        this.rooms = rooms;
    }

    public FloorsBean getFloors() {
        return this.floors;
    }

    public void setFloors(FloorsBean floors) {
        this.floors = floors;
    }

    public ListDeviceResponse getDevices() {
        return this.devices;
    }

    public void setDevices(ListDeviceResponse devices) {
        this.devices = devices;
    }

    public GroupsBean getGroups() {
        return this.groups;
    }

    public void setGroups(GroupsBean groups) {
        this.groups = groups;
    }

    public UsersBean getUsers() {
        return this.users;
    }

    public void setUsers(UsersBean users) {
        this.users = users;
    }

    public InfoBean getInfo() {
        return this.info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class RoomsBean {
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
            private String createtime;
            private long floorid;
            private String floorname;
            private long roomid;
            private int roomindex;
            private String roomname;
            private long roomroleid;
            private long userid;

            public long getFloorid() {
                return this.floorid;
            }

            public void setFloorid(long floorid) {
                this.floorid = floorid;
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

            public int getRoomindex() {
                return this.roomindex;
            }

            public void setRoomindex(int roomindex) {
                this.roomindex = roomindex;
            }

            public String getFloorname() {
                return this.floorname;
            }

            public void setFloorname(String floorname) {
                this.floorname = floorname;
            }

            public long getUserid() {
                return this.userid;
            }

            public void setUserid(long userid) {
                this.userid = userid;
            }

            public long getRoomroleid() {
                return this.roomroleid;
            }

            public void setRoomroleid(long roomroleid) {
                this.roomroleid = roomroleid;
            }

            public long getRoomid() {
                return this.roomid;
            }

            public void setRoomid(long roomid) {
                this.roomid = roomid;
            }
        }
    }

    public static class FloorsBean {
        private List<RowsBeanX> rows;
        private int total;

        public int getTotal() {
            return this.total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<RowsBeanX> getRows() {
            return this.rows;
        }

        public void setRows(List<RowsBeanX> rows) {
            this.rows = rows;
        }

        public static class RowsBeanX {
            private Object begincreatetime;
            private String createtime;
            private Object endcreatetime;
            private long floorid;
            private int floorindex;
            private String floorname;
            private int pagenum;
            private int pagesize;
            private long placeid;

            public long getFloorid() {
                return this.floorid;
            }

            public void setFloorid(long floorid) {
                this.floorid = floorid;
            }

            public long getPlaceid() {
                return this.placeid;
            }

            public void setPlaceid(long placeid) {
                this.placeid = placeid;
            }

            public String getFloorname() {
                return this.floorname;
            }

            public void setFloorname(String floorname) {
                this.floorname = floorname;
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

            public int getFloorindex() {
                return this.floorindex;
            }

            public void setFloorindex(int floorindex) {
                this.floorindex = floorindex;
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

    public static class GroupsBean {
        private List<RowsBeanXXX> rows;
        private int total;

        public int getTotal() {
            return this.total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<RowsBeanXXX> getRows() {
            return this.rows;
        }

        public void setRows(List<RowsBeanXXX> rows) {
            this.rows = rows;
        }

        public static class RowsBeanXXX {
            private Object begincreatetime;
            private String colortype;
            private int createby;
            private Object createbyname;
            private String createtime;
            private Object devices;
            private Object endcreatetime;
            private long groupid;
            private String groupname;
            private String index;
            private int pagenum;
            private int pagesize;
            private long placeid;
            private int type;
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

            public int getType() {
                return this.type;
            }

            public void setType(int type) {
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

            public Object getDevices() {
                return this.devices;
            }

            public void setDevices(Object devices) {
                this.devices = devices;
            }
        }
    }

    public static class UsersBean {
        private List<RowsBeanXXXX> rows;
        private int total;

        public int getTotal() {
            return this.total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<RowsBeanXXXX> getRows() {
            return this.rows;
        }

        public void setRows(List<RowsBeanXXXX> rows) {
            this.rows = rows;
        }

        public static class RowsBeanXXXX {
            private String applicationkey;
            private String createtime;
            private String email;
            private double latitude;
            private double longitude;
            private String meshuuid;
            private String netkey;
            private long placeid;
            private String placename;
            private long placeuserid;
            private String qrcode;
            private int roletype;
            private String roletypename;
            private long userid;
            private String username;

            public long getPlaceuserid() {
                return this.placeuserid;
            }

            public void setPlaceuserid(long placeuserid) {
                this.placeuserid = placeuserid;
            }

            public String getCreatetime() {
                return this.createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public String getQrcode() {
                return this.qrcode;
            }

            public void setQrcode(String qrcode) {
                this.qrcode = qrcode;
            }

            public double getLatitude() {
                return this.latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public String getRoletypename() {
                return this.roletypename;
            }

            public void setRoletypename(String roletypename) {
                this.roletypename = roletypename;
            }

            public long getPlaceid() {
                return this.placeid;
            }

            public void setPlaceid(long placeid) {
                this.placeid = placeid;
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

            public String getPlacename() {
                return this.placename;
            }

            public void setPlacename(String placename) {
                this.placename = placename;
            }

            public long getUserid() {
                return this.userid;
            }

            public void setUserid(long userid) {
                this.userid = userid;
            }

            public String getNetkey() {
                return this.netkey;
            }

            public void setNetkey(String netkey) {
                this.netkey = netkey;
            }

            public int getRoletype() {
                return this.roletype;
            }

            public void setRoletype(int roletype) {
                this.roletype = roletype;
            }

            public String getEmail() {
                return this.email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public double getLongitude() {
                return this.longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }

            public String getUsername() {
                return this.username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }
    }

    public static class InfoBean {
        private String applicationkey;
        private String createtime;
        private String email;
        private double latitude;
        private double longitude;
        private String meshuuid;
        private String netkey;
        private long placeid;
        private String placename;
        private long placeuserid;
        private String qrcode;
        private int roletype;
        private String roletypename;
        private long userid;
        private String username;

        public long getPlaceuserid() {
            return this.placeuserid;
        }

        public void setPlaceuserid(long placeuserid) {
            this.placeuserid = placeuserid;
        }

        public String getCreatetime() {
            return this.createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getQrcode() {
            return this.qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }

        public double getLatitude() {
            return this.latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public String getRoletypename() {
            return this.roletypename;
        }

        public void setRoletypename(String roletypename) {
            this.roletypename = roletypename;
        }

        public long getPlaceid() {
            return this.placeid;
        }

        public void setPlaceid(long placeid) {
            this.placeid = placeid;
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

        public String getPlacename() {
            return this.placename;
        }

        public void setPlacename(String placename) {
            this.placename = placename;
        }

        public long getUserid() {
            return this.userid;
        }

        public void setUserid(long userid) {
            this.userid = userid;
        }

        public String getNetkey() {
            return this.netkey;
        }

        public void setNetkey(String netkey) {
            this.netkey = netkey;
        }

        public int getRoletype() {
            return this.roletype;
        }

        public void setRoletype(int roletype) {
            this.roletype = roletype;
        }

        public String getEmail() {
            return this.email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public double getLongitude() {
            return this.longitude;
        }

        public void setLongitude(int longitude) {
            this.longitude = longitude;
        }

        public String getUsername() {
            return this.username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}