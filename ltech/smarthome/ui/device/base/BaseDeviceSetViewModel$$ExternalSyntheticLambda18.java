package com.ltech.smarthome.ui.device.base;

import io.reactivex.functions.Action;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes4.dex */
public final /* synthetic */ class BaseDeviceSetViewModel$$ExternalSyntheticLambda18 implements Action {
    public final /* synthetic */ BaseDeviceSetViewModel f$0;

    public /* synthetic */ BaseDeviceSetViewModel$$ExternalSyntheticLambda18(BaseDeviceSetViewModel baseDeviceSetViewModel) {
        this.f$0 = baseDeviceSetViewModel;
    }

    @Override // io.reactivex.functions.Action
    public final void run() {
        this.f$0.dismissLoadingDialog();
    }
}