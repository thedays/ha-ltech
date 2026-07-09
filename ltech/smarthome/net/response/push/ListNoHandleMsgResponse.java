package com.ltech.smarthome.net.response.push;

import java.util.List;

/* loaded from: classes4.dex */
public class ListNoHandleMsgResponse {
    private List<RowData> rows;

    public List<RowData> getRows() {
        return this.rows;
    }

    public void setRows(List<RowData> rows) {
        this.rows = rows;
    }

    public static class RowData {
        private String begincreatetime;
        private String beginusebegintime;
        private String beginuseendtime;
        private String content;
        private String createtime;
        private String endcreatetime;
        private String endusebegintime;
        private String enduseendtime;
        private long fromuserid;
        private String fromusername;
        private long messageid;
        private int messagetype;
        private int pagenum;
        private int pagesize;
        private String param;
        private String paramlike;
        private int pushtype;
        private int status;
        private String statuslist;
        private String title;
        private long touserid;
        private String tousername;
        private String usebegintime;
        private String useendtime;
        private String userlike;

        public long getMessageid() {
            return this.messageid;
        }

        public void setMessageid(long messageid) {
            this.messageid = messageid;
        }

        public long getFromuserid() {
            return this.fromuserid;
        }

        public void setFromuserid(long fromuserid) {
            this.fromuserid = fromuserid;
        }

        public String getFromusername() {
            return this.fromusername;
        }

        public void setFromusername(String fromusername) {
            this.fromusername = fromusername;
        }

        public long getTouserid() {
            return this.touserid;
        }

        public void setTouserid(long touserid) {
            this.touserid = touserid;
        }

        public String getTousername() {
            return this.tousername;
        }

        public void setTousername(String tousername) {
            this.tousername = tousername;
        }

        public String getContent() {
            return this.content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreatetime() {
            return this.createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public int getMessagetype() {
            return this.messagetype;
        }

        public void setMessagetype(int messagetype) {
            this.messagetype = messagetype;
        }

        public String getParam() {
            return this.param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBegincreatetime() {
            return this.begincreatetime;
        }

        public void setBegincreatetime(String begincreatetime) {
            this.begincreatetime = begincreatetime;
        }

        public String getEndcreatetime() {
            return this.endcreatetime;
        }

        public void setEndcreatetime(String endcreatetime) {
            this.endcreatetime = endcreatetime;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getParamlike() {
            return this.paramlike;
        }

        public void setParamlike(String paramlike) {
            this.paramlike = paramlike;
        }

        public String getUserlike() {
            return this.userlike;
        }

        public void setUserlike(String userlike) {
            this.userlike = userlike;
        }

        public String getUsebegintime() {
            return this.usebegintime;
        }

        public void setUsebegintime(String usebegintime) {
            this.usebegintime = usebegintime;
        }

        public String getUseendtime() {
            return this.useendtime;
        }

        public void setUseendtime(String useendtime) {
            this.useendtime = useendtime;
        }

        public String getBeginusebegintime() {
            return this.beginusebegintime;
        }

        public void setBeginusebegintime(String beginusebegintime) {
            this.beginusebegintime = beginusebegintime;
        }

        public String getEndusebegintime() {
            return this.endusebegintime;
        }

        public void setEndusebegintime(String endusebegintime) {
            this.endusebegintime = endusebegintime;
        }

        public String getBeginuseendtime() {
            return this.beginuseendtime;
        }

        public void setBeginuseendtime(String beginuseendtime) {
            this.beginuseendtime = beginuseendtime;
        }

        public String getEnduseendtime() {
            return this.enduseendtime;
        }

        public void setEnduseendtime(String enduseendtime) {
            this.enduseendtime = enduseendtime;
        }

        public int getPushtype() {
            return this.pushtype;
        }

        public void setPushtype(int pushtype) {
            this.pushtype = pushtype;
        }

        public String getStatuslist() {
            return this.statuslist;
        }

        public void setStatuslist(String statuslist) {
            this.statuslist = statuslist;
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
    }
}