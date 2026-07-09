package com.ltech.smarthome.net.response.super_panel;

import java.util.List;

/* loaded from: classes4.dex */
public class QuerySuperPanelKeywordInfoResponse {
    private ContentBean content;

    public ContentBean getContent() {
        return this.content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
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
            private long actionid;
            private int actiontype;
            private Object begincreatetime;
            private Object beginupdatetime;
            private Object createby;
            private Object createbyname;
            private String createtime;
            private Object endcreatetime;
            private Object endupdatetime;
            private String executecommand;
            private int index;
            private int keywords;
            private String keywordsname;
            private int keywordstype;
            private int pagenum;
            private int pagesize;
            private long panelid;
            private long panelinfoid;
            private String timespace;
            private Object updateby;
            private Object updatebyname;
            private Object updatetime;

            public long getPanelinfoid() {
                return this.panelinfoid;
            }

            public void setPanelinfoid(long panelinfoid) {
                this.panelinfoid = panelinfoid;
            }

            public long getPanelid() {
                return this.panelid;
            }

            public void setPanelid(long panelid) {
                this.panelid = panelid;
            }

            public int getActiontype() {
                return this.actiontype;
            }

            public void setActiontype(int actiontype) {
                this.actiontype = actiontype;
            }

            public long getActionid() {
                return this.actionid;
            }

            public void setActionid(long actionid) {
                this.actionid = actionid;
            }

            public Object getCreateby() {
                return this.createby;
            }

            public void setCreateby(Object createby) {
                this.createby = createby;
            }

            public Object getCreatebyname() {
                return this.createbyname;
            }

            public void setCreatebyname(Object createbyname) {
                this.createbyname = createbyname;
            }

            public Object getUpdateby() {
                return this.updateby;
            }

            public void setUpdateby(Object updateby) {
                this.updateby = updateby;
            }

            public Object getUpdatebyname() {
                return this.updatebyname;
            }

            public void setUpdatebyname(Object updatebyname) {
                this.updatebyname = updatebyname;
            }

            public Object getUpdatetime() {
                return this.updatetime;
            }

            public void setUpdatetime(Object updatetime) {
                this.updatetime = updatetime;
            }

            public Object getBeginupdatetime() {
                return this.beginupdatetime;
            }

            public void setBeginupdatetime(Object beginupdatetime) {
                this.beginupdatetime = beginupdatetime;
            }

            public Object getEndupdatetime() {
                return this.endupdatetime;
            }

            public void setEndupdatetime(Object endupdatetime) {
                this.endupdatetime = endupdatetime;
            }

            public int getKeywords() {
                return this.keywords;
            }

            public void setKeywords(int keywords) {
                this.keywords = keywords;
            }

            public int getKeywordstype() {
                return this.keywordstype;
            }

            public void setKeywordstype(int keywordstype) {
                this.keywordstype = keywordstype;
            }

            public String getExecutecommand() {
                return this.executecommand;
            }

            public void setExecutecommand(String executecommand) {
                this.executecommand = executecommand;
            }

            public int getIndex() {
                return this.index;
            }

            public void setIndex(int index) {
                this.index = index;
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

            public Object getBegincreatetime() {
                return this.begincreatetime;
            }

            public void setBegincreatetime(Object begincreatetime) {
                this.begincreatetime = begincreatetime;
            }

            public Object getEndcreatetime() {
                return this.endcreatetime;
            }

            public void setEndcreatetime(Object endcreatetime) {
                this.endcreatetime = endcreatetime;
            }

            public String getKeywordsname() {
                return this.keywordsname;
            }

            public void setKeywordsname(String keywordsname) {
                this.keywordsname = keywordsname;
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
}