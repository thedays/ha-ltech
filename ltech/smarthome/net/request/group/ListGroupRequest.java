package com.ltech.smarthome.net.request.group;

import com.ltech.smarthome.model.product.ProductId;

/* loaded from: classes4.dex */
public class ListGroupRequest {
    private long placeid;
    private String[] statuslist = {"1", "2", "3", "4", "5", "7", "8", "9", "10", "11", "12", "13", "14", "15", ProductId.BLE_SMART_PANEL_SUB_TYPE_SQ_PRO, ProductId.BLE_SMART_PANEL_SUB_TYPE_S4, ProductId.BLE_SMART_PANEL_SUB_TYPE_G4PRO, "101", ProductId.BLE_EUR_PANEL_EB1, "22", "23", "24", "21", ProductId.BLE_KNOB_PANEL_E6M, ProductId.BLE_KNOB_PANEL_E6D, ProductId.BLE_AS_PANEL_UB8, ProductId.BLE_SMART_PANEL_SUB_TYPE_SEPT_S1, ProductId.BLE_SMART_PANEL_SUB_TYPE_SEPT_S2, ProductId.BLE_KNOB_PANEL_E6T};
    private long userid;

    public ListGroupRequest(long placeid) {
        this.placeid = placeid;
    }

    public ListGroupRequest(long placeid, long userid) {
        this.placeid = placeid;
        this.userid = userid;
    }
}