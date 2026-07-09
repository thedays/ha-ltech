package com.ltech.smarthome.model.device_param;

import com.ltech.smarthome.base.BaseExtParam;

/* loaded from: classes4.dex */
public class ExtParam extends BaseExtParam {
    public int ACType;
    public String acGatewayFeature;

    public ExtParam(int ACType, String acGatewayFeature) {
        this.ACType = ACType;
        this.acGatewayFeature = acGatewayFeature;
    }

    public int getACType() {
        return this.ACType;
    }

    public void setACType(int ACType) {
        this.ACType = ACType;
    }

    public String getAcGatewayFeature() {
        return this.acGatewayFeature;
    }

    public void setAcGatewayFeature(String acGatewayFeature) {
        this.acGatewayFeature = acGatewayFeature;
    }
}