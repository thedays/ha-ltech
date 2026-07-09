package com.ltech.smarthome.net.response.place;

import java.util.List;

/* loaded from: classes4.dex */
public class ListPlaceResponse {
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
        private String applicationkey;
        private String createtime;
        private int devicetotal;
        private String email;
        private int floortotal;
        private int ivindex;
        private double latitude;
        private String location;
        private double longitude;
        private String meshuuid;
        private String netkey;
        private long placeid;
        private String placename;
        private long placeuserid;
        private String provisioneraddress;
        private String qrcode;
        private int roletype;
        private String roletypename;
        private int roomtotal;
        private long userid;
        private String username;

        public int getFloortotal() {
            return this.floortotal;
        }

        public void setFloortotal(int floortotal) {
            this.floortotal = floortotal;
        }

        public int getRoomtotal() {
            return this.roomtotal;
        }

        public void setRoomtotal(int roomtotal) {
            this.roomtotal = roomtotal;
        }

        public int getDevicetotal() {
            return this.devicetotal;
        }

        public void setDevicetotal(int devicetotal) {
            this.devicetotal = devicetotal;
        }

        public String getProvisioneraddress() {
            return this.provisioneraddress;
        }

        public void setProvisioneraddress(String provisioneraddress) {
            this.provisioneraddress = provisioneraddress;
        }

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

        public String getLocation() {
            return this.location;
        }

        public void setLocation(String location) {
            this.location = location;
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

        public String getNetkey() {
            return this.netkey;
        }

        public void setNetkey(String netkey) {
            this.netkey = netkey;
        }

        public int getIvindex() {
            return this.ivindex;
        }

        public void setIvindex(int ivindex) {
            this.ivindex = ivindex;
        }

        public String toString() {
            return "RowsBean{placeuserid=" + this.placeuserid + ", createtime='" + this.createtime + "', qrcode='" + this.qrcode + "', latitude=" + this.latitude + ", roletypename='" + this.roletypename + "', placeid=" + this.placeid + ", placename='" + this.placename + "', userid=" + this.userid + ", location='" + this.location + "', roletype=" + this.roletype + ", email='" + this.email + "', longitude=" + this.longitude + ", username='" + this.username + "', applicationkey='" + this.applicationkey + "', meshuuid='" + this.meshuuid + "', netkey='" + this.netkey + "', ivindex=" + this.ivindex + ", provisioneraddress='" + this.provisioneraddress + "'}";
        }
    }
}