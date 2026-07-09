package com.ltech.smarthome.upgrade.ota671;

/* loaded from: classes4.dex */
public interface DfuCallback {
    void DfuFail(String var1);

    void DfuProgress(double var1);

    void DfuStart();

    void DfuSuccess();

    void Msg(String var1);
}