package com.ltech.smarthome.net.response.floor;

import java.util.List;

/* loaded from: classes4.dex */
public class ListFloorResponse {
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