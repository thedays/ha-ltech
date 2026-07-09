package com.ltech.smarthome.net.response.group;

import com.ltech.smarthome.model.bean.GradientScene;
import java.util.List;

/* loaded from: classes4.dex */
public class GradientSceneResponse {
    private List<GradientScene> rows;
    private int total;

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<GradientScene> getRows() {
        return this.rows;
    }

    public void setRows(List<GradientScene> rows) {
        this.rows = rows;
    }
}