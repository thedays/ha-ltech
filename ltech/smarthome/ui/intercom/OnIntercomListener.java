package com.ltech.smarthome.ui.intercom;

/* loaded from: classes4.dex */
public interface OnIntercomListener {
    void finishedCall();

    void monitorHandleReject();

    void onCallWhenMonitor();

    void showDialog();

    void showSipMonitor();

    void sipMessageEstablishedCall();

    void sipReconnect();

    void sipReconnectFinish();

    void sipStopMonitor();
}