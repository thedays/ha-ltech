package com.ltech.smarthome.net.request.scene;

/* loaded from: classes4.dex */
public class CommonSceneRequest {
    private int common;
    private long sceneid;

    public CommonSceneRequest(long sceneid, boolean common) {
        this.sceneid = sceneid;
        this.common = common ? 1 : 2;
    }
}