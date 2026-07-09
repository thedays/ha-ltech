package com.ltech.smarthome.net.response.rs8;

import java.util.List;

/* loaded from: classes4.dex */
public class Rs8BrandResponse {
    private List<Brand> rows;
    private int total;

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Brand> getRows() {
        return this.rows;
    }

    public void setRows(List<Brand> rows) {
        this.rows = rows;
    }

    public static class Brand {
        private long brandid;
        private long categoryid;
        private String categoryname;
        private String cname;
        private String cndesc;
        private String ename;
        private String endesc;
        private String initial;
        private long sorting;
        private long status;

        public String getCndesc() {
            return this.cndesc;
        }

        public void setCndesc(String cndesc) {
            this.cndesc = cndesc;
        }

        public String getEndesc() {
            return this.endesc;
        }

        public void setEndesc(String endesc) {
            this.endesc = endesc;
        }

        public long getBrandid() {
            return this.brandid;
        }

        public void setBrandid(long brandid) {
            this.brandid = brandid;
        }

        public long getCategoryid() {
            return this.categoryid;
        }

        public void setCategoryid(long categoryid) {
            this.categoryid = categoryid;
        }

        public String getCategoryname() {
            return this.categoryname;
        }

        public void setCategoryname(String categoryname) {
            this.categoryname = categoryname;
        }

        public String getInitial() {
            return this.initial;
        }

        public void setInitial(String initial) {
            this.initial = initial;
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
            return "Brand{brandid=" + this.brandid + ", categoryid=" + this.categoryid + ", categoryname='" + this.categoryname + "', initial='" + this.initial + "', cname='" + this.cname + "', ename='" + this.ename + "', status=" + this.status + ", sorting=" + this.sorting + ", cndesc='" + this.cndesc + "', endesc='" + this.endesc + "'}";
        }
    }

    public String toString() {
        return "Rs8BrandResponse{total=" + this.total + ", rows=" + this.rows + '}';
    }
}