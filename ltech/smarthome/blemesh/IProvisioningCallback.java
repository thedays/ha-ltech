package com.ltech.smarthome.blemesh;

import com.feasycom.feasymesh.library.transport.ProvisionedMeshNode;

/* loaded from: classes3.dex */
public interface IProvisioningCallback {
    void provisioningFailed(String errorMsg);

    void provisioningSuccess(ProvisionedMeshNode node);

    void provisioningTestSeq();
}