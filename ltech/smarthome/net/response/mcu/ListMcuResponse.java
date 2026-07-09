package com.ltech.smarthome.net.response.mcu;

import java.util.List;

/* loaded from: classes4.dex */
public class ListMcuResponse {
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
        private String begincreatetime;
        private String createtime;
        private long deviceversionid;
        private int enable;
        private String endcreatetime;
        private String endhour;
        private String endtime;
        private String filedir;
        private String filename;
        private String firmwaretypecode;
        private int meshupdate;
        private int pagenum;
        private int pagesize;
        private long productid;
        private String productname;
        private String remark;
        private String starthour;
        private String starttime;
        private int status;
        private int timetype;
        private int type;
        private String url;
        private String versionname;
        private long versionnumber;

        public int getMeshupdate() {
            return this.meshupdate;
        }

        public void setMeshupdate(int meshupdate) {
            this.meshupdate = meshupdate;
        }

        public long getDeviceversionid() {
            return this.deviceversionid;
        }

        public void setDeviceversionid(long deviceversionid) {
            this.deviceversionid = deviceversionid;
        }

        public long getProductid() {
            return this.productid;
        }

        public void setProductid(long productid) {
            this.productid = productid;
        }

        public String getVersionname() {
            return this.versionname;
        }

        public void setVersionname(String versionname) {
            this.versionname = versionname;
        }

        public long getVersionnumber() {
            return this.versionnumber;
        }

        public void setVersionnumber(long versionnumber) {
            this.versionnumber = versionnumber;
        }

        public String getFiledir() {
            return this.filedir;
        }

        public void setFiledir(String filedir) {
            this.filedir = filedir;
        }

        public String getFilename() {
            return this.filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getEnable() {
            return this.enable;
        }

        public void setEnable(int enable) {
            this.enable = enable;
        }

        public String getCreatetime() {
            return this.createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getBegincreatetime() {
            return this.begincreatetime;
        }

        public void setBegincreatetime(String begincreatetime) {
            this.begincreatetime = begincreatetime;
        }

        public String getEndcreatetime() {
            return this.endcreatetime;
        }

        public void setEndcreatetime(String endcreatetime) {
            this.endcreatetime = endcreatetime;
        }

        public int getType() {
            return this.type;
        }

        public void setType(int type) {
            this.type = type;
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

        public String getProductname() {
            return this.productname;
        }

        public void setProductname(String productname) {
            this.productname = productname;
        }

        public String getRemark() {
            return this.remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getFirmwaretypecode() {
            return this.firmwaretypecode;
        }

        public void setFirmwaretypecode(String firmwaretypecode) {
            this.firmwaretypecode = firmwaretypecode;
        }

        public String getStarttime() {
            return this.starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getEndtime() {
            return this.endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public int getTimetype() {
            return this.timetype;
        }

        public void setTimetype(int timetype) {
            this.timetype = timetype;
        }

        public String getStarthour() {
            return this.starthour;
        }

        public void setStarthour(String starthour) {
            this.starthour = starthour;
        }

        public String getEndhour() {
            return this.endhour;
        }

        public void setEndhour(String endhour) {
            this.endhour = endhour;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}