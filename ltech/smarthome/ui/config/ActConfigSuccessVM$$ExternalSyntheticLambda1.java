package com.ltech.smarthome.ui.config;

import io.reactivex.functions.Action;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes4.dex */
public final /* synthetic */ class ActConfigSuccessVM$$ExternalSyntheticLambda1 implements Action {
    public final /* synthetic */ ActConfigSuccessVM f$0;

    public /* synthetic */ ActConfigSuccessVM$$ExternalSyntheticLambda1(ActConfigSuccessVM actConfigSuccessVM) {
        this.f$0 = actConfigSuccessVM;
    }

    @Override // io.reactivex.functions.Action
    public final void run() {
        this.f$0.dismissLoadingDialog();
    }
}