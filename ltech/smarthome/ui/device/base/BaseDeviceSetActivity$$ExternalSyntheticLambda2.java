package com.ltech.smarthome.ui.device.base;

import io.reactivex.functions.Action;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes4.dex */
public final /* synthetic */ class BaseDeviceSetActivity$$ExternalSyntheticLambda2 implements Action {
    public final /* synthetic */ BaseDeviceSetActivity f$0;

    public /* synthetic */ BaseDeviceSetActivity$$ExternalSyntheticLambda2(BaseDeviceSetActivity baseDeviceSetActivity) {
        this.f$0 = baseDeviceSetActivity;
    }

    @Override // io.reactivex.functions.Action
    public final void run() {
        this.f$0.dismissLoadingDialog();
    }
}