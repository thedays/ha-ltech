package com.ltech.smarthome.model.bean;

/* loaded from: classes4.dex */
public class SunInfo {
    private SunBean data;
    private String message;
    private int ret;

    public int getRet() {
        return this.ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public SunBean getData() {
        return this.data;
    }

    public void setData(SunBean data) {
        this.data = data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class SunBean {
        private String sunriseTime;
        private String sunsetTime;

        public SunBean(String sunriseTime, String sunsetTime) {
            this.sunriseTime = sunriseTime;
            this.sunsetTime = sunsetTime;
        }

        public String getSunriseTime() {
            return this.sunriseTime;
        }

        public void setSunriseTime(String sunriseTime) {
            this.sunriseTime = sunriseTime;
        }

        public String getSunsetTime() {
            return this.sunsetTime;
        }

        public void setSunsetTime(String sunsetTime) {
            this.sunsetTime = sunsetTime;
        }
    }
}