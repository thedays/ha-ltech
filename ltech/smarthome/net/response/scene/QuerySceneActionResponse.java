package com.ltech.smarthome.net.response.scene;

import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.model.scene_param.BaseLocalParam;
import java.util.List;

/* loaded from: classes4.dex */
public class QuerySceneActionResponse {
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
        private String actionid;
        private int actiontype;
        private String actiontypename;
        private String createtime;
        private String executecommand;
        private int index;
        private String option;
        private String optionvalue;
        private int pagenum;
        private int pagesize;
        private long scenecontentid;
        private long sceneid;
        private String scenename;
        private int scenenum;
        private int scenetype;
        private String timespace;

        public String getScenename() {
            return this.scenename;
        }

        public void setScenename(String scenename) {
            this.scenename = scenename;
        }

        public int getScenenum() {
            return this.scenenum;
        }

        public void setScenenum(int scenenum) {
            this.scenenum = scenenum;
        }

        public long getScenecontentid() {
            return this.scenecontentid;
        }

        public void setScenecontentid(long scenecontentid) {
            this.scenecontentid = scenecontentid;
        }

        public String getAction() {
            return this.action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getActionid() {
            return this.actionid;
        }

        public void setActionid(String actionid) {
            this.actionid = actionid;
        }

        public String getExecutecommand() {
            return this.executecommand;
        }

        public void setExecutecommand(String executecommand) {
            this.executecommand = executecommand;
        }

        public String getTimespace() {
            return this.timespace;
        }

        public void setTimespace(String timespace) {
            this.timespace = timespace;
        }

        public String getCreatetime() {
            return this.createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public long getSceneid() {
            return this.sceneid;
        }

        public void setSceneid(long sceneid) {
            this.sceneid = sceneid;
        }

        public int getActiontype() {
            return this.actiontype;
        }

        public void setActiontype(int actiontype) {
            this.actiontype = actiontype;
        }

        public String getActiontypename() {
            return this.actiontypename;
        }

        public void setActiontypename(String actiontypename) {
            this.actiontypename = actiontypename;
        }

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getOption() {
            return this.option;
        }

        public void setOption(String option) {
            this.option = option;
        }

        public String getOptionvalue() {
            return this.optionvalue;
        }

        public void setOptionvalue(String optionvalue) {
            this.optionvalue = optionvalue;
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

        public int getScenetype() {
            return this.scenetype;
        }

        public void setScenetype(int scenetype) {
            this.scenetype = scenetype;
        }

        public BaseLocalParam getExecutecommandObject() {
            return (BaseLocalParam) GsonUtils.fromJson(this.executecommand, BaseLocalParam.class);
        }
    }
}