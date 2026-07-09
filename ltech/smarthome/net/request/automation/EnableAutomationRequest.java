package com.ltech.smarthome.net.request.automation;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class EnableAutomationRequest {
    private int enable;
    private List<AutomationId> ids;

    public EnableAutomationRequest(long automationId, int enable) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new AutomationId(automationId));
        this.ids = arrayList;
        this.enable = enable;
    }

    public static class AutomationId {
        private long automationid;

        public AutomationId(long automationid) {
            this.automationid = automationid;
        }
    }
}