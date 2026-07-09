package com.ltech.smarthome.net.response.intercom;

import java.util.List;

/* loaded from: classes4.dex */
public class GetCallLogResponse {
    private List<LogBean> data;
    private String message;
    private int ret;

    public int getRet() {
        return this.ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public List<LogBean> getData() {
        return this.data;
    }

    public void setData(List<LogBean> data) {
        this.data = data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class LogBean {
        private String CallTime;
        private String DevLocation;
        private String DevSipNum;
        private String ID;
        private int IsAnswer;
        private String Status;
        private List<String> Time;
        private int Type;

        public String getDevSipNum() {
            return this.DevSipNum;
        }

        public void setDevSipNum(String DevSipNum) {
            this.DevSipNum = DevSipNum;
        }

        public String getStatus() {
            return this.Status;
        }

        public void setStatus(String Status) {
            this.Status = Status;
        }

        public int getType() {
            return this.Type;
        }

        public void setType(int Type) {
            this.Type = Type;
        }

        public String getDevLocation() {
            return this.DevLocation;
        }

        public void setDevLocation(String DevLocation) {
            this.DevLocation = DevLocation;
        }

        public int getIsAnswer() {
            return this.IsAnswer;
        }

        public void setIsAnswer(int IsAnswer) {
            this.IsAnswer = IsAnswer;
        }

        public String getID() {
            return this.ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getCallTime() {
            return this.CallTime;
        }

        public void setCallTime(String CallTime) {
            this.CallTime = CallTime;
        }

        public List<String> getTime() {
            return this.Time;
        }

        public void setTime(List<String> Time) {
            this.Time = Time;
        }

        public String toString() {
            return "LogBean{DevSipNum='" + this.DevSipNum + "', Status='" + this.Status + "', Type=" + this.Type + ", DevLocation='" + this.DevLocation + "', IsAnswer=" + this.IsAnswer + ", ID='" + this.ID + "', CallTime='" + this.CallTime + "', Time=" + this.Time + '}';
        }
    }
}