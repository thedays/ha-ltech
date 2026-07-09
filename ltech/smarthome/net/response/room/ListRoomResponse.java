package com.ltech.smarthome.net.response.room;

import java.util.List;

/* loaded from: classes4.dex */
public class ListRoomResponse {
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

        public int getRoomindex() {
            return this.roomindex;
        }

        public void setRoomindex(int roomindex) {
            this.roomindex = roomindex;
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