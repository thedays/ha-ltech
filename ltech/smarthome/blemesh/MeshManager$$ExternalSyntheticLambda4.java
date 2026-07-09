package com.ltech.smarthome.blemesh;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes3.dex */
public final /* synthetic */ class MeshManager$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ IGroupCallback f$0;

    public /* synthetic */ MeshManager$$ExternalSyntheticLambda4(IGroupCallback iGroupCallback) {
        this.f$0 = iGroupCallback;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.configFail();
    }
}