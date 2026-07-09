package com.ltech.smarthome.net.request.automation;

/* loaded from: classes4.dex */
public class ReportAutomationRequest {
    private String params;
    private int type;

    public ReportAutomationRequest(int type, String params) {
        this.type = type;
        this.params = params;
    }
}