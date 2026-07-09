package com.ltech.smarthome.net.request.rs8;

/* loaded from: classes4.dex */
public class Rs8Request {
    private String address;
    private long brandid;
    private long categoryid;
    private long codelibid;

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getCodelibid() {
        return this.codelibid;
    }

    public void setCodelibid(long codelibid) {
        this.codelibid = codelibid;
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

    public String toString() {
        return "Rs8Request{brandid=" + this.brandid + ", categoryid=" + this.categoryid + ", codelibid=" + this.codelibid + ", address='" + this.address + "'}";
    }
}