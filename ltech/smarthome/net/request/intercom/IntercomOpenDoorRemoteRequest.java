package com.ltech.smarthome.net.request.intercom;

/* loaded from: classes4.dex */
public class IntercomOpenDoorRemoteRequest {
    private String mac;
    private long relay;

    public IntercomOpenDoorRemoteRequest(String mac, long relay) {
        this.mac = mac;
        this.relay = relay;
    }
}