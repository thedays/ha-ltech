package com.ltech.smarthome.blemesh;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes3.dex */
public final /* synthetic */ class ConnectAgent$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ ConnectAgent f$0;

    public /* synthetic */ ConnectAgent$$ExternalSyntheticLambda2(ConnectAgent connectAgent) {
        this.f$0 = connectAgent;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.stopScanAndConnect();
    }
}