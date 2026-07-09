package com.ltech.smarthome.blemesh;

/* loaded from: classes3.dex */
public interface ITestSequenceCallback {
    void onBleError(int code);

    void onTestFail();

    void onTestSuccess();

    void stopTestSequence();
}