package com.ltech.smarthome.net.response.photo;

import java.util.List;

/* loaded from: classes4.dex */
public class ListPhotoResponse {
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
        private long panelid;
        private long pictureid;
        private long size;
        private int sorting;
        private String url;

        public long getPictureid() {
            return this.pictureid;
        }

        public void setPictureid(long pictureid) {
            this.pictureid = pictureid;
        }

        public long getPanelid() {
            return this.panelid;
        }

        public void setPanelid(long panelid) {
            this.panelid = panelid;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public long getSize() {
            return this.size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        public int getSorting() {
            return this.sorting;
        }

        public void setSorting(int sorting) {
            this.sorting = sorting;
        }
    }
}