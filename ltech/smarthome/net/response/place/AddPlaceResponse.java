package com.ltech.smarthome.net.response.place;

import java.util.List;

/* loaded from: classes4.dex */
public class AddPlaceResponse {
    private String applicationkey;
    private String createtime;
    private FloorsBean floors;
    private double latitude;
    private double longitude;
    private String meshuuid;
    private String netkey;
    private int pagenum;
    private int pagesize;
    private long placeid;
    private String placename;
    private String provisioneraddress;
    private String qrcode;
    private RoomsBean rooms;
    private long userid;

    public String getProvisioneraddress() {
        return this.provisioneraddress;
    }

    public void setProvisioneraddress(String provisioneraddress) {
        this.provisioneraddress = provisioneraddress;
    }

    public String getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public RoomsBean getRooms() {
        return this.rooms;
    }

    public void setRooms(RoomsBean rooms) {
        this.rooms = rooms;
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

    public String getApplicationkey() {
        return this.applicationkey;
    }

    public void setApplicationkey(String applicationkey) {
        this.applicationkey = applicationkey;
    }

    public long getPlaceid() {
        return this.placeid;
    }

    public void setPlaceid(long placeid) {
        this.placeid = placeid;
    }

    public String getMeshuuid() {
        return this.meshuuid;
    }

    public void setMeshuuid(String meshuuid) {
        this.meshuuid = meshuuid;
    }

    public int getPagenum() {
        return this.pagenum;
    }

    public void setPagenum(int pagenum) {
        this.pagenum = pagenum;
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

    public FloorsBean getFloors() {
        return this.floors;
    }

    public void setFloors(FloorsBean floors) {
        this.floors = floors;
    }

    public String getNetkey() {
        return this.netkey;
    }

    public void setNetkey(String netkey) {
        this.netkey = netkey;
    }

    public int getPagesize() {
        return this.pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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
            private Object begincreatetime;
            private String createtime;
            private Object endcreatetime;
            private long floorid;
            private int pagenum;
            private int pagesize;
            private long roomid;
            private int roomindex;
            private String roomname;

            public long getRoomid() {
                return this.roomid;
            }

            public void setRoomid(long roomid) {
                this.roomid = roomid;
            }

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

            public int getRoomindex() {
                return this.roomindex;
            }

            public void setRoomindex(int roomindex) {
                this.roomindex = roomindex;
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
}