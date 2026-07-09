package com.ltech.smarthome.net.response.rs8;

import java.util.List;

/* loaded from: classes4.dex */
public class Rs8CategoryResponse {
    private List<Category> rows;
    private long total;

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<Category> getRows() {
        return this.rows;
    }

    public void setRows(List<Category> rows) {
        this.rows = rows;
    }

    public static class Category {
        private long categoryid;
        private String cname;
        private String ename;
        private String icon = "";
        private long sorting;
        private long status;

        public Category() {
        }

        public Category(long categoryid) {
            this.categoryid = categoryid;
        }

        public long getCategoryid() {
            return this.categoryid;
        }

        public void setCategoryid(long categoryid) {
            this.categoryid = categoryid;
        }

        public String getCname() {
            return this.cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getEname() {
            return this.ename;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public String getIcon() {
            return this.icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public long getStatus() {
            return this.status;
        }

        public void setStatus(long status) {
            this.status = status;
        }

        public long getSorting() {
            return this.sorting;
        }

        public void setSorting(long sorting) {
            this.sorting = sorting;
        }

        public String toString() {
            return "Category{categoryid=" + this.categoryid + ", cname='" + this.cname + "', ename='" + this.ename + "', icon='" + this.icon + "', status=" + this.status + ", sorting=" + this.sorting + '}';
        }
    }

    public String toString() {
        return "Rs8CategoryResponse{total=" + this.total + ", rows=" + this.rows + '}';
    }
}