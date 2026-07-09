package com.ltech.smarthome.net.response.user;

import java.util.ArrayList;

/* loaded from: classes4.dex */
public class CountryListInfoResponse {
    private ArrayList<CountryInfoResponse> rows;
    private int total;

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<CountryInfoResponse> getRows() {
        return this.rows;
    }

    public void setRows(ArrayList<CountryInfoResponse> rows) {
        this.rows = rows;
    }
}