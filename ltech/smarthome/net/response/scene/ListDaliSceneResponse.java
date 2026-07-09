package com.ltech.smarthome.net.response.scene;

import java.util.List;

/* loaded from: classes4.dex */
public class ListDaliSceneResponse {
    private List<SceneInfo> data;
    private String message;
    private int ret;

    public static class SceneInfo {
        private long sceneid;
        private String scenename;

        public long getSceneid() {
            return this.sceneid;
        }

        public void setSceneid(long sceneid) {
            this.sceneid = sceneid;
        }

        public String getScenename() {
            return this.scenename;
        }

        public void setScenename(String scenename) {
            this.scenename = scenename;
        }

        public String toString() {
            return "SceneInfo{sceneid=" + this.sceneid + ", scenename='" + this.scenename + "'}";
        }
    }

    public int getRet() {
        return this.ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SceneInfo> getData() {
        return this.data;
    }

    public void setData(List<SceneInfo> data) {
        this.data = data;
    }

    public String toString() {
        return "ListDaliGroupResponse{ret=" + this.ret + ", message='" + this.message + "', data=" + this.data + '}';
    }
}