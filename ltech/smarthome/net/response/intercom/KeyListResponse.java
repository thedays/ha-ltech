package com.ltech.smarthome.net.response.intercom;

import java.util.List;

/* loaded from: classes4.dex */
public class KeyListResponse {
    private List<OpenDoorTempKey> key_list;

    public List<OpenDoorTempKey> getKey_list() {
        return this.key_list;
    }

    public void setKey_list(List<OpenDoorTempKey> key_list) {
        this.key_list = key_list;
    }

    public String toString() {
        return "KeyListResponse{key_list=" + this.key_list + '}';
    }

    public static class OpenDoorTempKey {
        private String begin_time;
        private int counts;
        private String end_time;
        private String qrcode_url;
        private long temp_key;

        public long getTemp_key() {
            return this.temp_key;
        }

        public void setTemp_key(long temp_key) {
            this.temp_key = temp_key;
        }

        public String getBegin_time() {
            return this.begin_time;
        }

        public void setBegin_time(String begin_time) {
            this.begin_time = begin_time;
        }

        public String getEnd_time() {
            return this.end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public int getCounts() {
            return this.counts;
        }

        public void setCounts(int counts) {
            this.counts = counts;
        }

        public String getQrcode_url() {
            return this.qrcode_url;
        }

        public void setQrcode_url(String qrcode_url) {
            this.qrcode_url = qrcode_url;
        }

        public String toString() {
            return "OpenDoorTempKey{temp_key=" + this.temp_key + ", begin_time='" + this.begin_time + "', end_time='" + this.end_time + "', counts=" + this.counts + ", qrcode_url='" + this.qrcode_url + "'}";
        }
    }
}