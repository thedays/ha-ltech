package com.ltech.smarthome.net.response.device;

import com.ltech.smarthome.model.key.KeyInfo;
import java.util.List;

/* loaded from: classes4.dex */
public class KeyInfoResponse {
    private List<KeyInfo> rows;
    private int total;

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<KeyInfo> getRows() {
        return this.rows;
    }

    public void setRows(List<KeyInfo> rows) {
        this.rows = rows;
    }
}