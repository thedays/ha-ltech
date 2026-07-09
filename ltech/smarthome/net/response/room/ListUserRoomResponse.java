package com.ltech.smarthome.net.response.room;

import java.util.List;

/* loaded from: classes4.dex */
public class ListUserRoomResponse {
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