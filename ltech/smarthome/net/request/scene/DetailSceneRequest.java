package com.ltech.smarthome.net.request.scene;

/* loaded from: classes4.dex */
public class DetailSceneRequest {
    private int pagenum;
    private int pagesize;
    private long sceneid;

    public DetailSceneRequest(long sceneid) {
        this.sceneid = sceneid;
    }
}