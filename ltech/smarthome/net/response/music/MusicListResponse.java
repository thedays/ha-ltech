package com.ltech.smarthome.net.response.music;

import com.ltech.smarthome.model.bean.MusicInfo;
import java.util.List;

/* loaded from: classes4.dex */
public class MusicListResponse {
    private List<MusicInfo> rows;
    private int total;

    public List<MusicInfo> getRows() {
        return this.rows;
    }

    public void setRows(List<MusicInfo> rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}