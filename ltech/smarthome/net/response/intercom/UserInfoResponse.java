package com.ltech.smarthome.net.response.intercom;

import java.util.List;

/* loaded from: classes4.dex */
public class UserInfoResponse {
    private List<DevInfo> dev_list;
    private User user;

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<DevInfo> getDev_list() {
        return this.dev_list;
    }

    public void setDev_list(List<DevInfo> dev_list) {
        this.dev_list = dev_list;
    }

    public String toString() {
        return "UserInfoResponse{user=" + this.user + ", dev_list=" + this.dev_list + '}';
    }

    public static class User {
        private String display_name;
        private String rtsp_server;
        private String rtsp_server_ipv6;
        private String sip_passwd;
        private String sip_server;
        private String sip_server_ipv6;
        private int trans_type;
        private String user_sip;

        public String getUser_sip() {
            return this.user_sip;
        }

        public void setUser_sip(String user_sip) {
            this.user_sip = user_sip;
        }

        public String getDisplay_name() {
            return this.display_name;
        }

        public void setDisplay_name(String display_name) {
            this.display_name = display_name;
        }

        public String getSip_server() {
            return this.sip_server;
        }

        public void setSip_server(String sip_server) {
            this.sip_server = sip_server;
        }

        public String getSip_server_ipv6() {
            return this.sip_server_ipv6;
        }

        public void setSip_server_ipv6(String sip_server_ipv6) {
            this.sip_server_ipv6 = sip_server_ipv6;
        }

        public String getSip_passwd() {
            return this.sip_passwd;
        }

        public void setSip_passwd(String sip_passwd) {
            this.sip_passwd = sip_passwd;
        }

        public String getRtsp_server() {
            return this.rtsp_server;
        }

        public void setRtsp_server(String rtsp_server) {
            this.rtsp_server = rtsp_server;
        }

        public String getRtsp_server_ipv6() {
            return this.rtsp_server_ipv6;
        }

        public void setRtsp_server_ipv6(String rtsp_server_ipv6) {
            this.rtsp_server_ipv6 = rtsp_server_ipv6;
        }

        public int getTrans_type() {
            return this.trans_type;
        }

        public void setTrans_type(int trans_type) {
            this.trans_type = trans_type;
        }

        public String toString() {
            return "User{user_sip='" + this.user_sip + "', display_name='" + this.display_name + "', sip_server='" + this.sip_server + "', sip_server_ipv6='" + this.sip_server_ipv6 + "', sip_passwd='" + this.sip_passwd + "', rtsp_server='" + this.rtsp_server + "', rtsp_server_ipv6='" + this.rtsp_server_ipv6 + "', trans_type=" + this.trans_type + '}';
        }
    }

    public static class DevInfo {
        private int dev_type;
        private int is_public;
        private String location;
        private String mac;
        private List<Relay> relay;
        private String rtsp_pwd;
        private String sip;
        private int status;
        private String uid;

        public String getMac() {
            return this.mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public String getSip() {
            return this.sip;
        }

        public void setSip(String sip) {
            this.sip = sip;
        }

        public String getUid() {
            return this.uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getLocation() {
            return this.location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getRtsp_pwd() {
            return this.rtsp_pwd;
        }

        public void setRtsp_pwd(String rtsp_pwd) {
            this.rtsp_pwd = rtsp_pwd;
        }

        public int getDev_type() {
            return this.dev_type;
        }

        public void setDev_type(int dev_type) {
            this.dev_type = dev_type;
        }

        public int getIs_public() {
            return this.is_public;
        }

        public void setIs_public(int is_public) {
            this.is_public = is_public;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<Relay> getRelay() {
            return this.relay;
        }

        public void setRelay(List<Relay> relay) {
            this.relay = relay;
        }

        public String toString() {
            return "DevInfo{mac='" + this.mac + "', sip='" + this.sip + "', uid='" + this.uid + "', location='" + this.location + "', rtsp_pwd='" + this.rtsp_pwd + "', dev_type=" + this.dev_type + ", is_public=" + this.is_public + ", status=" + this.status + ", relay=" + this.relay + '}';
        }
    }

    public static class Relay {
        private String door_name;
        private String dtmf;
        private long relay_id;

        public long getRelay_id() {
            return this.relay_id;
        }

        public void setRelay_id(long relay_id) {
            this.relay_id = relay_id;
        }

        public String getDtmf() {
            return this.dtmf;
        }

        public void setDtmf(String dtmf) {
            this.dtmf = dtmf;
        }

        public String getDoor_name() {
            return this.door_name;
        }

        public void setDoor_name(String door_name) {
            this.door_name = door_name;
        }

        public String toString() {
            return "Relay{relay_id=" + this.relay_id + ", dtmf='" + this.dtmf + "', door_name='" + this.door_name + "'}";
        }
    }
}