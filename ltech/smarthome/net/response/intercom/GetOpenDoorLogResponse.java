package com.ltech.smarthome.net.response.intercom;

import java.util.List;

/* loaded from: classes4.dex */
public class GetOpenDoorLogResponse {
    private List<OpenDoorBean> data;
    private String message;
    private int ret;

    public int getRet() {
        return this.ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public List<OpenDoorBean> getData() {
        return this.data;
    }

    public void setData(List<OpenDoorBean> data) {
        this.data = data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class OpenDoorBean {
        private String CaptureTime;
        private String CaptureType;
        private String Initiator;
        private String Location;
        private String PicUrl;
        private String Response;
        private String SPicUrl;
        private String Status;

        public String getInitiator() {
            return this.Initiator;
        }

        public void setInitiator(String Initiator) {
            this.Initiator = Initiator;
        }

        public String getPicUrl() {
            return this.PicUrl;
        }

        public void setPicUrl(String PicUrl) {
            this.PicUrl = PicUrl;
        }

        public String getStatus() {
            return this.Status;
        }

        public void setStatus(String Status) {
            this.Status = Status;
        }

        public String getCaptureType() {
            return this.CaptureType;
        }

        public void setCaptureType(String CaptureType) {
            this.CaptureType = CaptureType;
        }

        public String getSPicUrl() {
            return this.SPicUrl;
        }

        public void setSPicUrl(String SPicUrl) {
            this.SPicUrl = SPicUrl;
        }

        public String getResponse() {
            return this.Response;
        }

        public void setResponse(String Response) {
            this.Response = Response;
        }

        public String getCaptureTime() {
            return this.CaptureTime;
        }

        public void setCaptureTime(String CaptureTime) {
            this.CaptureTime = CaptureTime;
        }

        public String getLocation() {
            return this.Location;
        }

        public void setLocation(String Location) {
            this.Location = Location;
        }

        public String toString() {
            return "OpenDoorBean{Initiator='" + this.Initiator + "', PicUrl='" + this.PicUrl + "', Status='" + this.Status + "', CaptureType='" + this.CaptureType + "', SPicUrl='" + this.SPicUrl + "', Response='" + this.Response + "', CaptureTime='" + this.CaptureTime + "', Location='" + this.Location + "'}";
        }
    }
}