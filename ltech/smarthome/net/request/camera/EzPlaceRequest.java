package com.ltech.smarthome.net.request.camera;

/* loaded from: classes4.dex */
public class EzPlaceRequest {
    private static int FORCE_REFLUSH = 1;
    private long placeid;
    private int reflush;

    public EzPlaceRequest(long placeid, boolean forceFlush) {
        this.reflush = forceFlush ? FORCE_REFLUSH : 0;
        this.placeid = placeid;
    }
}