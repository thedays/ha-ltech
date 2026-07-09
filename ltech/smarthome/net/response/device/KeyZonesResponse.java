package com.ltech.smarthome.net.response.device;

import com.ltech.smarthome.model.key.KeyZone;
import java.util.List;

/* loaded from: classes4.dex */
public class KeyZonesResponse {
    private List<KeyZone> rows;
    private int total;

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<KeyZone> getRows() {
        return this.rows;
    }

    public void setRows(List<KeyZone> rows) {
        this.rows = rows;
    }
}