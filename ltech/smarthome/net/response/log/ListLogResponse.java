package com.ltech.smarthome.net.response.log;

import java.util.List;

/* loaded from: classes4.dex */
public class ListLogResponse {
    private List<DeviceLog> rows;
    private int total;

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DeviceLog> getRows() {
        return this.rows;
    }

    public void setRows(List<DeviceLog> rows) {
        this.rows = rows;
    }
}