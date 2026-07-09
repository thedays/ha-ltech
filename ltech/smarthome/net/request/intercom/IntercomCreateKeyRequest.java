package com.ltech.smarthome.net.request.intercom;

/* loaded from: classes4.dex */
public class IntercomCreateKeyRequest {
    private String begin_time;
    private int counts;
    private String end_time;

    public IntercomCreateKeyRequest(String begin_time, String end_time, int counts) {
        this.begin_time = begin_time;
        this.end_time = end_time;
        this.counts = counts;
    }
}