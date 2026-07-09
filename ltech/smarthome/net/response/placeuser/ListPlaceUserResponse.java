package com.ltech.smarthome.net.response.placeuser;

import java.util.List;

/* loaded from: classes4.dex */
public class ListPlaceUserResponse {
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
        private String email;
        private String headurl;
        private double latitude;
        private double longitude;
        private String mobilephone;
        private long placeid;
        private String placename;
        private long placeuserid;
        private String remark;
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

        public int getRoletype() {
            return this.roletype;
        }

        public void setRoletype(int roletype) {
            this.roletype = roletype;
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

        public String getHeadurl() {
            return this.headurl;
        }

        public void setHeadurl(String headurl) {
            this.headurl = headurl;
        }

        public String getRemark() {
            return this.remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getMobilephone() {
            return this.mobilephone;
        }

        public void setMobilephone(String mobilephone) {
            this.mobilephone = mobilephone;
        }
    }
}