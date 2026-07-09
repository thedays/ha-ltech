package com.ltech.smarthome.net.response.push;

import java.util.List;

/* loaded from: classes4.dex */
public class PlaceMsgListResponse {
    private List<RowData> rows;
    private long total;

    public static class RowData {
        private String content;
        private String createtime;
        private long fromuserid;
        private String fromusername;
        private long messageid;
        private String messagetype;
        private String param;
        private String title;
        private long touserid;
        private String tousername;

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

        public String getMessagetype() {
            return this.messagetype;
        }

        public void setMessagetype(String messagetype) {
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
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<RowData> getRows() {
        return this.rows;
    }

    public void setRows(List<RowData> rows) {
        this.rows = rows;
    }
}