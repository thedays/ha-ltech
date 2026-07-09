package com.ltech.smarthome.net.response.scene;

import java.util.List;

/* loaded from: classes4.dex */
public class DetailSceneResponse {
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
        private String action;
        private int actiontype;
        private String actiontypename;
        private String createby;
        private String createbyname;
        private String createtime;
        private String executecommand;
        private int index;
        private long placeid;
        private long scenecontentid;
        private long sceneid;
        private int sceneindex;
        private String scenename;
        private int timespace;

        public int getActiontype() {
            return this.actiontype;
        }

        public void setActiontype(int actiontype) {
            this.actiontype = actiontype;
        }

        public String getCreatetime() {
            return this.createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getActiontypename() {
            return this.actiontypename;
        }

        public void setActiontypename(String actiontypename) {
            this.actiontypename = actiontypename;
        }

        public String getScenename() {
            return this.scenename;
        }

        public void setScenename(String scenename) {
            this.scenename = scenename;
        }

        public long getPlaceid() {
            return this.placeid;
        }

        public void setPlaceid(long placeid) {
            this.placeid = placeid;
        }

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getSceneindex() {
            return this.sceneindex;
        }

        public void setSceneindex(int sceneindex) {
            this.sceneindex = sceneindex;
        }

        public String getCreateby() {
            return this.createby;
        }

        public void setCreateby(String createby) {
            this.createby = createby;
        }

        public String getExecutecommand() {
            return this.executecommand;
        }

        public void setExecutecommand(String executecommand) {
            this.executecommand = executecommand;
        }

        public long getScenecontentid() {
            return this.scenecontentid;
        }

        public void setScenecontentid(long scenecontentid) {
            this.scenecontentid = scenecontentid;
        }

        public long getSceneid() {
            return this.sceneid;
        }

        public void setSceneid(long sceneid) {
            this.sceneid = sceneid;
        }

        public String getAction() {
            return this.action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getCreatebyname() {
            return this.createbyname;
        }

        public void setCreatebyname(String createbyname) {
            this.createbyname = createbyname;
        }

        public int getTimespace() {
            return this.timespace;
        }

        public void setTimespace(int timespace) {
            this.timespace = timespace;
        }
    }
}